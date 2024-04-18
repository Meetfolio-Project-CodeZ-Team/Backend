package com.codez4.meetfolio.domain.comment.controller;

import com.codez4.meetfolio.domain.comment.dto.CommentRequest;
import com.codez4.meetfolio.domain.comment.dto.CommentResponse;
import com.codez4.meetfolio.domain.comment.dto.CommentVO;
import com.codez4.meetfolio.domain.comment.service.CommentCommandService;
import com.codez4.meetfolio.domain.comment.service.CommentQueryService;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.annotation.AuthenticationMember;
import com.codez4.meetfolio.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board-comments")
public class CommentController {

    private final CommentCommandService commentCommandService;
    private final CommentQueryService commentQueryService;

    @Operation(summary = "댓글 목록 조회 요청", description = "댓글 목록 조회 요청을 GET로 보냅니다.")
    @Parameters({
        @Parameter(name = "boardId", description = "게시글 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH),
        @Parameter(name = "page", description = "페이징 번호, page, Query String입니다.", example = "0", in = ParameterIn.QUERY)
    })
    @GetMapping("/{boardId}")
    public ApiResponse<CommentResponse.CommentResult> findComments(
        @PathVariable(name = "boardId") Long boardId, @RequestParam(name = "page") int page) {

        return ApiResponse.onSuccess(commentQueryService.findCommentsByBoard(boardId, page));
    }

    @Operation(summary = "댓글 작성 요청", description = "댓글 작성 요청을 POST로 보냅니다.")
    @Parameter(name = "boardId", description = "게시글 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @PostMapping("/{boardId}")
    public ApiResponse<CommentResponse.CommentProc> writeComment(
        @AuthenticationMember Member member,
        @RequestBody CommentRequest.Post post, @PathVariable(name = "boardId") Long boardId) {

        CommentVO commentVO = CommentVO.of(member, post, boardId);

        return ApiResponse.onSuccess(commentCommandService.write(commentVO));
    }

    @Operation(summary = "댓글 수정 요청", description = "댓글 수정 요청을 PATCH로 보냅니다.")
    @Parameter(name = "commentId", description = "댓글 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @PatchMapping("/{commentId}")
    public ApiResponse<CommentResponse.CommentProc> updateComment(@RequestBody String content,
        @PathVariable(name = "commentId") Long commentId) {

        return ApiResponse.onSuccess(commentCommandService.update(content, commentId));
    }

    @Operation(summary = "댓글 삭제 요청", description = "댓글 삭제 요청을 DELETE로 보냅니다.")
    @Parameter(name = "commentId", description = "댓글 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @DeleteMapping("/{commentId}")
    public ApiResponse<CommentResponse.CommentProc> deleteComment(
        @PathVariable(name = "commentId") Long commentId) {

        return ApiResponse.onSuccess(commentCommandService.delete(commentId));
    }
}
