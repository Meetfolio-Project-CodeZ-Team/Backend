package com.codez4.meetfolio.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;

@Getter
@AllArgsConstructor
public class KakaoPayRequest {

        private String url;
        private LinkedMultiValueMap<String, String> map;

    public static KakaoPayRequest toReadyRequest(Long paymentId, PaymentRequest.ChargeRequest chargeRequest) {
        String MEETFOLIO_HOST = "http://34.64.177.41:9090";
        String KAKAO_PAY_READY_URL = "https://kapi.kakao.com/v1/payment/ready";

        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        String orderId = "point" + paymentId;
        // 가맹점 코드 테스트코드는 TC0ONETIME
        map.add("cid", "TC0ONETIME");

        map.add("partner_order_id", orderId);

        map.add("partner_user_id", "meetfolio");

        map.add("item_name", "Meetfolio 포인트 충전");

        map.add("quantity", "1");

        map.add("total_amount", chargeRequest.getPayment()+ "");

        map.add("tax_free_amount", "0");

        map.add("approval_url", MEETFOLIO_HOST + "/api/payments/success?paymentId=" + paymentId ) ; // 성공 시 redirect url
        map.add("cancel_url", MEETFOLIO_HOST + "/api/payments/cancel"); // 취소 시 redirect url
        map.add("fail_url", MEETFOLIO_HOST + "/api/payments/fail"); // 실패 시 redirect url

        return new KakaoPayRequest(KAKAO_PAY_READY_URL, map);
    }

    public static KakaoPayRequest toApproveRequest(String tid, Long paymentId, String pgToken) {
        String KAKAO_PAY_READY_URL = "https://kapi.kakao.com/v1/payment/approve";

        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        String orderId = "point" + paymentId;
        map.add("cid", "TC0ONETIME");

        map.add("tid", tid);
        map.add("partner_order_id", orderId);
        map.add("partner_user_id", "meetfolio");

        map.add("pg_token", pgToken);

        return new KakaoPayRequest(KAKAO_PAY_READY_URL, map);
    }
}

