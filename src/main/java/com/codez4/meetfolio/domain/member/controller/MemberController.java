package com.codez4.meetfolio.domain.member.controller;

import com.codez4.meetfolio.domain.board.dto.BoardQueryItem;
import com.codez4.meetfolio.domain.board.dto.BoardResponse;
import com.codez4.meetfolio.domain.board.service.BoardQueryService;
import com.codez4.meetfolio.domain.comment.dto.CommentResponse;
import com.codez4.meetfolio.domain.comment.service.CommentQueryService;
import com.codez4.meetfolio.domain.enums.Grade;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.like.service.LikeQueryService;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.MemberRequest;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.member.dto.MemberResponse.MemberInfo;
import com.codez4.meetfolio.domain.member.dto.PasswordRequest;
import com.codez4.meetfolio.domain.member.service.MemberCommandService;
import com.codez4.meetfolio.domain.member.service.MemberQueryService;
import com.codez4.meetfolio.global.annotation.AuthenticationMember;
import com.codez4.meetfolio.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;
    private final BoardQueryService boardQueryService;
    private final LikeQueryService likeQueryService;
    private final CommentQueryService commentQueryService;

    @Operation(summary = "회원 가입", description = "회원 가입")
    @PostMapping("/signup")
    public ApiResponse<MemberResponse.MemberProc> signUp(
            @Valid @RequestBody MemberRequest.SignUpRequest request) {
        MemberRequest.Post post = MemberRequest.Post.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .grade(Grade.convert(request.getGrade()))
                .jobKeyword(JobKeyword.convert(request.getJobKeyword()))
                .major(request.getMajor())
                .build();
        return ApiResponse.onSuccess(memberCommandService.post(post));
    }

    @Operation(summary = "비밀번호 검증 요청", description = "개인정보 수정 단계에서 사용자의 비밀번호를 검증합니다.")
    @GetMapping("/mypage/check-password")
    public ApiResponse<String> checkPassword(
            @AuthenticationMember Member member, @Valid@RequestBody PasswordRequest request) {
        memberQueryService.comparePassword(request.getPassword(), member.getPassword());
        return ApiResponse.onSuccess("비밀번호 검증이 성공하였습니다.");
    }

    @Operation(summary = "마이페이지 수정 정보 조회", description = "사용자의 개인 정보를 조회합니다.")
    @GetMapping("/mypage")
    public ApiResponse<MemberResponse.MemberDetailInfo> myPage(
            @AuthenticationMember Member member) {

        return ApiResponse.onSuccess(memberQueryService.getMyPage(member));
    }

    @Operation(summary = "마이페이지 수정 요청", description = "사용자의 개인 정보를 수정합니다.")
    @PatchMapping("/mypage")
    public ApiResponse<MemberResponse.MemberProc> editMyPage(@AuthenticationMember Member member,
                                                             @RequestBody MemberRequest.Patch patch) {

        return ApiResponse.onSuccess(memberCommandService.update(member, patch));
    }

    @Operation(summary = "내가 작성한 게시글 목록 조회", description = "내가 작성한 게시글 목록 조회 GET으로 보냅니다.")
    @Parameter(name = "page", description = "페이징 번호, page, Query String입니다.", example = "0", in = ParameterIn.QUERY)
    @GetMapping("/my-boards")
    public ApiResponse<BoardResponse.BoardListResult> getMyBoards(
            @AuthenticationMember Member member,
            @RequestParam(name = "page") Integer page) {

        MemberInfo memberInfo = MemberResponse.toMemberInfo(member);
        Page<BoardQueryItem> boardInfo = boardQueryService.getMyBoards(member,
                page);

        return ApiResponse.onSuccess(BoardResponse.toBoardListResult(memberInfo, boardInfo));
    }

    @Operation(summary = "내가 좋아요 한 게시글 목록 조회", description = "내가 좋아요 한 게시글 목록 조회 GET으로 보냅니다.")
    @Parameter(name = "page", description = "페이징 번호, page, Query String입니다.", example = "0", in = ParameterIn.QUERY)
    @GetMapping("/my-likes")
    public ApiResponse<BoardResponse.BoardListResult> getMyLikes(
            @AuthenticationMember Member member,
            @RequestParam(name = "page") Integer page) {

        MemberInfo memberInfo = MemberResponse.toMemberInfo(member);
        Page<BoardQueryItem> boardInfo = likeQueryService.findMyLikedBoards(
                member, page);

        return ApiResponse.onSuccess(BoardResponse.toBoardListResult(memberInfo, boardInfo));
    }

    @Operation(summary = "내가 작성한 댓글 목록 조회", description = "내가 작성한 댓글 목록 조회 GET으로 보냅니다.")
    @Parameter(name = "page", description = "페이징 번호, page, Query String입니다.", example = "0", in = ParameterIn.QUERY)
    @GetMapping("/my-comments")
    public ApiResponse<CommentResponse.MyCommentResult> getMyComments(
            @AuthenticationMember Member member, @RequestParam(name = "page") Integer page) {

        MemberInfo memberInfo = MemberResponse.toMemberInfo(member);
        Page<CommentResponse.MyCommentItem> commentInfo = commentQueryService.findMyComments(
                member, page);
        return ApiResponse.onSuccess(CommentResponse.toMyCommentResult(memberInfo, commentInfo));
    }

    @Operation(summary = "Header에 필요한 로그인한 사용자의 정보 조회", description = "로그인한 사용자의 정보 조회 GET으로 보냅니다.")
    @GetMapping("/members")
    public ApiResponse<MemberResponse.MemberInfo> getMemberInfo(
            @AuthenticationMember Member member) {

        return ApiResponse.onSuccess(MemberResponse.toMemberInfo(member));
    }

}
