package com.codez4.meetfolio.domain.like.controller;

import com.codez4.meetfolio.domain.like.dto.LikeResponse;
import com.codez4.meetfolio.domain.like.service.LikeCommandService;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.annotation.AuthenticationMember;
import com.codez4.meetfolio.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final LikeCommandService likeCommandService;

    @Operation(summary = "게시글 좋아요 요청", description = "게시글 좋아요 요청을 POST로 보냅니다.")
    @Parameter(name = "boardId", description = "게시글 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @PostMapping("/board-likes/{boardId}")
    public ApiResponse<LikeResponse> likeProcess(@AuthenticationMember Member member,
        @PathVariable(name = "boardId") Long boardId) {

        return ApiResponse.onSuccess(likeCommandService.changeLike(member, boardId));
    }
}
