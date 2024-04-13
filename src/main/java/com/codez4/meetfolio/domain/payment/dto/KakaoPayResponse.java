package com.codez4.meetfolio.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class KakaoPayResponse {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Ready {
        private String tid; // 결제 고유 번호
        private String next_redirect_pc_url; // pc 웹일 경우 받는 결제 페이지
        private String created_at;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Approve {
        private Amount amount; // 결제 금액 정보
        private String item_name; // 상품명
        private String created_at; // 결제 요청 시간
        private String approved_at; // 결제 승인 시간

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Amount {
        private int total; // 총 결제 금액
        private int tax_free; // 비과세 금액
        private int tax; // 부가세 금액
    }
}
