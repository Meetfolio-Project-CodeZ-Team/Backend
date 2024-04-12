package com.codez4.meetfolio.domain.payment.dto;

import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.payment.Payment;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

import static com.codez4.meetfolio.domain.member.dto.MemberResponse.toMemberInfo;

public class PaymentResponse {
    @Schema(description = "사용자 - 충전 내역 목록 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class PaymentResult {

        @Schema(description = "로그인 사용자 정보")
        private MemberResponse.MemberInfo memberInfo;

        @Schema(description = "충전 내역")
        private PaymentInfo paymentInfo;

    }

    @Schema(description = "사용자 - 충전 내역 목록 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class PaymentInfo {

        @Schema(description = "내 포인트")
        private int myPoint;

        @Schema(description = "충전 내역 목록")
        private List<PaymentItem> paymentList;

        @Schema(description = "페이징된 리스트의 항목 개수")
        private Integer listSize;

        @Schema(description = "총 페이징 수 ")
        private Integer totalPage;

        @Schema(description = "전체 데이터의 개수")
        private Long totalElements;

        @Schema(description = "첫 페이지의 여부")
        private Boolean isFirst;

        @Schema(description = "마지막 페이지의 여부")
        private Boolean isLast;
    }

    @Schema(description = "사용자 - 충전 내역 목록 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class PaymentItem {

        @Schema(description = "충전 일시")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;

        @Schema(description = "결제 금액")
        private int payment;

        @Schema(description = "층전 포인트")
        private int point;

        @Schema(description = "충전 후 포인트")
        private int totalPoint;
    }

    public static PaymentResult toPaymentResult(Member member,Page<Payment> payments ){
        return PaymentResult.builder()
                .memberInfo(toMemberInfo(member))
                .paymentInfo(toPaymentInfo(payments, member.getPoint()))
                .build();
    }

    public static PaymentInfo toPaymentInfo(Page<Payment> payments, int myPoint){
        List<PaymentItem> paymentList = payments.stream().map(PaymentResponse::toPaymentItem).toList();
        return PaymentInfo.builder()
                .myPoint(myPoint)
                .paymentList(paymentList)
                .listSize(paymentList.size())
                .totalPage(payments.getTotalPages())
                .totalElements(payments.getTotalElements())
                .isFirst(payments.isFirst())
                .isLast(payments.isLast())
                .build();
    }

    public static PaymentItem toPaymentItem(Payment payment){
        return PaymentItem.builder()
                .createdAt(payment.getCreatedAt())
                .payment(payment.getPayment())
                .point(payment.getPoint().getPoint())
                .totalPoint(payment.getPoint().getTotalPoint())
                .build();
    }

}
