package com.codez4.meetfolio.domain.payment.dto;

import com.codez4.meetfolio.domain.enums.PaymentStatus;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.payment.Payment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class PaymentRequest {

    @Schema(description = "카카오 페이 연결 정보 저장 dto")
    @Getter
    public static class ReadyRequest {

        @Schema(description = "충전할 포인트")
        private int point;

        @Schema(description = "결제할 금액")
        private int payment;

        @Schema(description = "카카오 페이 결제 id")
        private String tid;
    }

    @Schema(description = "포인트 충전 요청 dto")
    @Getter
    public static class ChargeRequest {

        @Schema(description = "충전할 포인트")
        private int point;

        @Schema(description = "결제할 금액")
        private int payment;
    }


    /*
        payment 생성 dto
     */
    @Getter
    @AllArgsConstructor
    @Builder
    public static class Post {
        private int payment;
        private int point;
        private Member member;
        private PaymentStatus paymentStatus;
        private String kakaoPayId;

    }

    public static Payment toEntity(Post post) {
        return Payment.builder()
                .payment(post.payment)
                .point(post.point)
                .member(post.member)
                .paymentStatus(post.paymentStatus)
                .kakaoPayId(post.kakaoPayId)
                .build();
    }

}
