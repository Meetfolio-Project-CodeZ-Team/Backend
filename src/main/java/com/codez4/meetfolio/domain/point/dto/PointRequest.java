package com.codez4.meetfolio.domain.point.dto;

import com.codez4.meetfolio.domain.coverLetter.CoverLetter;
import com.codez4.meetfolio.domain.enums.PointType;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.payment.Payment;
import com.codez4.meetfolio.domain.point.Point;
import com.codez4.meetfolio.global.annotation.EnumValid;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class PointRequest {

    @Schema(description = "포인트 사용 요청 dto")
    @Getter
    public static class PointUseRequest {

        @Schema(description = "포인트 사용 타입, USE_COVER_LETTER/USE_AI_ANALYSIS")
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
    public static class Post {

        int point;
        PointType pointType;
        int totalPoint;
        Member member;
        Payment payment;
        CoverLetter coverLetter;
    }

    public static Point toEntity(Post post) {
        return Point.builder()
                .point(post.getPoint())
                .pointType(post.getPointType())
                .totalPoint(post.getTotalPoint())
                .member(post.getMember())
                .payment(post.payment)
                .coverLetter(post.coverLetter)
                .build();
    }

    public static Point toSharePoint(int point, PointType pointType, int totalPoint, Member member,
            CoverLetter coverLetter) {

        return Point.builder()
                .point(point)
                .pointType(pointType)
                .totalPoint(totalPoint)
                .member(member)
                .coverLetter(coverLetter)
                .build();
    }
}
