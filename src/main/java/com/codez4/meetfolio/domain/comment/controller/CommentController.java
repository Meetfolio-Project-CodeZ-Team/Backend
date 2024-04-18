package com.codez4.meetfolio.domain.comment.controller;

import com.codez4.meetfolio.domain.comment.dto.CommentRequest;
import com.codez4.meetfolio.domain.comment.dto.CommentResponse;
import com.codez4.meetfolio.domain.comment.dto.CommentVO;
import com.codez4.meetfolio.domain.comment.service.CommentCommandService;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.annotation.AuthenticationMember;
import com.codez4.meetfolio.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board-comments")
public class CommentController {

    private final CommentCommandService commentCommandService;

    @Operation(summary = "댓글 작성 요청", description = "댓글 작성 요청을 POST로 보냅니다.")
    @Parameter(name = "boardId", description = "게시글 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @PostMapping("/{boardId}")
    public ApiResponse<CommentResponse.CommentResult> writeComment(
        @AuthenticationMember Member member,
        @RequestBody CommentRequest.Post post, @PathVariable(name = "boardId") Long boardId) {

        CommentVO commentVO = CommentVO.of(member, post, boardId);

        return ApiResponse.onSuccess(commentCommandService.write(commentVO));
    }

    @Operation(summary = "댓글 수정 요청", description = "댓글 수정 요청을 PATCH로 보냅니다.")
    @Parameter(name = "commentId", description = "댓글 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @PatchMapping("/{commentId}")
    public ApiResponse<CommentResponse.CommentResult> updateComment(@RequestBody String content,
        @PathVariable(name = "commentId") Long commentId) {

        return ApiResponse.onSuccess(commentCommandService.update(content, commentId));
    }
}
