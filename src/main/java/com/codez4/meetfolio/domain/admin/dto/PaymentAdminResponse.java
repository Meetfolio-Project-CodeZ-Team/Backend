package com.codez4.meetfolio.domain.admin.dto;

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

public class PaymentAdminResponse {
    @Schema(description = "관리자 - 결제 내역 목록 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class PaymentAdminResult {

        @Schema(description = "요청 년/월")
        private String yearMonth;

        @Schema(description = "총 매출")
        private int totalSales;

        @Schema(description = "결제 내역")
        private PaymentAdminInfo paymentInfo;

    }

    @Schema(description = "관리자 - 결제 내역 목록 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class PaymentAdminInfo {

        @Schema(description = "결제 내역 목록")
        private List<PaymentAdminItem> paymentList;

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

    @Schema(description = "관리자 - 결제 내역 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class PaymentAdminItem {

        @Schema(description = "결제 일시")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;

        @Schema(description = "이메일")
        private String email;

        @Schema(description = "결제 금액")
        private int payment;

        @Schema(description = "충전 포인트")
        private int point;

    }

    public static PaymentAdminResult toPaymentAdminResult(String yearMonth, int totalSales, Page<Payment> payments) {
        return PaymentAdminResult.builder()
                .paymentInfo(toPaymentAdminList(payments))
                .yearMonth(yearMonth)
                .totalSales(totalSales)
                .build();
    }

    public static PaymentAdminInfo toPaymentAdminList(Page<Payment> payments) {
        List<PaymentAdminItem> paymentList = payments.stream()
                .map(PaymentAdminResponse::toPaymentAdminItem)
                .toList();
        return PaymentAdminInfo.builder()
                .paymentList(paymentList)
                .listSize(paymentList.size())
                .totalPage(payments.getTotalPages())
                .totalElements(payments.getTotalElements())
                .isFirst(payments.isFirst())
                .isLast(payments.isLast())
                .build();
    }

    public static PaymentAdminItem toPaymentAdminItem(Payment payment) {
        return PaymentAdminItem.builder()
                .createdAt(payment.getCreatedAt())
                .email(payment.getMember().getEmail())
                .payment(payment.getPayment())
                .point(payment.getPoint().getPoint())
                .build();
    }
}
