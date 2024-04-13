package com.codez4.meetfolio.domain.board.controller;

import com.codez4.meetfolio.domain.board.dto.BoardResponse;
import com.codez4.meetfolio.domain.board.dto.BoardResponse.BoardInfo;
import com.codez4.meetfolio.domain.board.service.BoardQueryService;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.member.dto.MemberResponse.MemberInfo;
import com.codez4.meetfolio.global.annotation.AuthenticationMember;
import com.codez4.meetfolio.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "마이페이지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardQueryService boardQueryService;

    @Operation(summary = "내가 작성한 게시글 목록 조회", description = "내가 작성한 게시글 목록 조회 GET으로 보냅니다.")
    @Parameter(name = "page", description = "페이징 번호, page, Query String입니다.", example = "0", in = ParameterIn.QUERY)
    @GetMapping("/my-boards")
    public ApiResponse<BoardResponse.MyBoardResult> getMyBoards(@AuthenticationMember Member member,
        @RequestParam(name = "page") Integer page) {

        MemberInfo memberInfo = MemberResponse.toMemberInfo(member);
        BoardInfo boardInfo = boardQueryService.findMyBoards(member, page);

        return ApiResponse.onSuccess(BoardResponse.toMyBoardResult(memberInfo, boardInfo));
    }
}
