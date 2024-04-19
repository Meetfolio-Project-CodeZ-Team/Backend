package com.codez4.meetfolio.domain.like.dto;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.like.Like;
import com.codez4.meetfolio.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "좋아요 처리 응답 DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LikeResponse {

    @Schema(description = "좋아요 상태", example = "ACTIVE / INACTIVE")
    private String status;

    @Schema(description = "응답 생성 시간")
    private LocalDateTime createdAt;

    public static LikeResponse toLikeResponse(Like like) {

        return LikeResponse.builder()
            .status(like.getStatus().toString())
            .createdAt(LocalDateTime.now())
            .build();
    }

    public static Like toEntity(Member member, Board board) {
        return Like.builder()
            .member(member)
            .board(board)
            .build();
    }
}
