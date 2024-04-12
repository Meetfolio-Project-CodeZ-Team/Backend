package com.codez4.meetfolio.domain.point.dto;

import com.codez4.meetfolio.domain.enums.PointType;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.point.Point;
import com.codez4.meetfolio.global.annotation.EnumValid;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class PointRequest {
    @Schema(description = "포인트 사용 요청 dto")
    @Getter
    public static class PointUseRequest{

        @Schema(description = "포인트 사용 타입, USE_COVER_LETTER/USE_ANALYSIS")
        @EnumValid(enumClass = PointType.class)
        String type;

        @Schema(description = "사용할 포인트")
        int point;
    }

    /*
        포인트 생성 dto
     */
    @Getter
    @AllArgsConstructor
    @Builder
    public static class Post{
        int point;
        PointType pointType;
        int totalPoint;
        Member member;
    }

    public static Point toEntity(Post post){
        return Point.builder()
                .point(post.getPoint())
                .pointType(post.getPointType())
                .totalPoint(post.getTotalPoint())
                .member(post.getMember())
                .build();
    }
}
