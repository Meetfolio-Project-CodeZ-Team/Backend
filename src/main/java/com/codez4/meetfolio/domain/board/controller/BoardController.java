package com.codez4.meetfolio.domain.board.controller;

import com.codez4.meetfolio.domain.board.dto.BoardResponse;
import com.codez4.meetfolio.domain.board.service.BoardQueryService;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.annotation.AuthenticationMember;
import com.codez4.meetfolio.global.response.ApiResponse;
import com.codez4.meetfolio.global.response.SliceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "커뮤니티 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardQueryService boardQueryService;

    @Operation(summary = "취업 정보 게시판 조회", description = "커뮤니티 메뉴에서 취업 정보 게시판을 조회합니다.")
    @Parameter(name = "page", description = "페이징 번호, page, Query String입니다.", example = "0", in = ParameterIn.QUERY)
    @Parameter(name = "category", description = "직무 키워드, Query String입니다. BACKEND/WEB/APP/DESIGN/AI", example = "BACKEND", in = ParameterIn.QUERY)
    @GetMapping("/boards")
    public ApiResponse<SliceResponse<BoardResponse.BoardItem>> getJobBoardList(@AuthenticationMember Member member,
                                                                               @RequestParam(name = "page") Integer page,
                                                                               @RequestParam(name = "category", required = false) String category) {
        if (category != null) {
            JobKeyword jobKeyword = JobKeyword.convert(category);
            return ApiResponse.onSuccess(boardQueryService.getEmploymentBoardsByJobKeyword(member, page, jobKeyword));
        } else return ApiResponse.onSuccess(boardQueryService.getEmploymentBoards(member, page));
    }

}
