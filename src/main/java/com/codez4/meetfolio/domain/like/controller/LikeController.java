package com.codez4.meetfolio.domain.like.controller;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.board.service.BoardQueryService;
import com.codez4.meetfolio.domain.like.dto.LikeResponse;
import com.codez4.meetfolio.domain.like.service.LikeCommandService;
import com.codez4.meetfolio.domain.like.service.LikeQueryService;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.annotation.AuthenticationMember;
import com.codez4.meetfolio.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "좋아요 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final LikeCommandService likeCommandService;
    private final LikeQueryService likeQueryService;
    private final BoardQueryService boardQueryService;

    @Operation(summary = "게시글 좋아요 요청", description = "게시글 좋아요 요청을 POST로 보냅니다.")
    @Parameter(name = "boardId", description = "게시글 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @PostMapping("/board-likes/{boardId}")
    public ApiResponse<LikeResponse.LikeResult> likeProcess(@AuthenticationMember Member member,
                                                            @PathVariable(name = "boardId") Long boardId) {

        return ApiResponse.onSuccess(likeCommandService.changeLike(member, boardId));
    }

    @Operation(summary = "게시글 좋아요 조회", description = "게시글의 좋아요 수를 조회합니다.")
    @Parameter(name = "boardId", description = "게시글 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @GetMapping("/board-likes/{boardId}")
    public ApiResponse<LikeResponse.LikeCountResult> getLikeCount(@AuthenticationMember Member member,
                                                                  @PathVariable(name = "boardId") Long boardId){
        Board board  = boardQueryService.findById(boardId);
        return ApiResponse.onSuccess(likeQueryService.getLikeCount(board));
    }


}
