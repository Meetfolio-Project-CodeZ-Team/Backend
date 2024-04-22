package com.codez4.meetfolio.domain.member.controller;

import com.codez4.meetfolio.domain.board.dto.BoardResponse;
import com.codez4.meetfolio.domain.board.service.BoardQueryService;
import com.codez4.meetfolio.domain.comment.service.CommentQueryService;
import com.codez4.meetfolio.domain.enums.Grade;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.like.service.LikeQueryService;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.MemberRequest;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.member.dto.MemberResponse.MemberInfo;
import com.codez4.meetfolio.domain.member.service.MemberCommandService;
import com.codez4.meetfolio.domain.member.service.MemberQueryService;
import com.codez4.meetfolio.global.annotation.AuthenticationMember;
import com.codez4.meetfolio.global.response.ApiResponse;
import com.codez4.meetfolio.global.response.SliceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ApiResponse<BoardResponse.BoardListResult> getMyBoards(@AuthenticationMember Member member,
                                                                  @RequestParam(name = "page") Integer page) {

        MemberInfo memberInfo = MemberResponse.toMemberInfo(member);
        SliceResponse<BoardResponse.BoardItem> boardInfo = boardQueryService.getMyBoards(member, page);

        return ApiResponse.onSuccess(BoardResponse.toBoardListResult(memberInfo, boardInfo));
    }

    @Operation(summary = "내가 좋아요 한 게시글 목록 조회", description = "내가 좋아요 한 게시글 목록 조회 GET으로 보냅니다.")
    @Parameter(name = "page", description = "페이징 번호, page, Query String입니다.", example = "0", in = ParameterIn.QUERY)
    @GetMapping("/my-likes")
    public ApiResponse<BoardResponse.BoardListResult> getMyLikes(@AuthenticationMember Member member,
                                                                 @RequestParam(name = "page") Integer page) {

        MemberInfo memberInfo = MemberResponse.toMemberInfo(member);
        SliceResponse<BoardResponse.BoardItem> boardInfo = likeQueryService.findMyLikedBoards(member, page);

        return ApiResponse.onSuccess(BoardResponse.toBoardListResult(memberInfo, boardInfo));
    }

    @Operation(summary = "내가 작성한 댓글의 게시글 목록 조회", description = "내가 작성한 댓글의 게시글 목록 조회 GET으로 보냅니다.")
    @Parameter(name = "page", description = "페이징 번호, page, Query String입니다.", example = "0", in = ParameterIn.QUERY)
    @GetMapping("/my-comments")
    public ApiResponse<BoardResponse.BoardListResult> getMyComments(
            @AuthenticationMember Member member, @RequestParam(name = "page") Integer page) {

        MemberInfo memberInfo = MemberResponse.toMemberInfo(member);
        SliceResponse<BoardResponse.BoardItem> boardInfo = commentQueryService.findMyComments(member, page);

        return ApiResponse.onSuccess(BoardResponse.toBoardListResult(memberInfo, boardInfo));
    }

}
