package com.codez4.meetfolio.domain.payment.service;

import com.codez4.meetfolio.domain.payment.dto.KakaoPayRequest;
import com.codez4.meetfolio.domain.payment.dto.KakaoPayResponse;
import com.codez4.meetfolio.domain.payment.dto.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.codez4.meetfolio.domain.payment.dto.KakaoPayRequest.toApproveRequest;
import static com.codez4.meetfolio.domain.payment.dto.KakaoPayRequest.toReadyRequest;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoPayService {

    @Value("${pay.admin_key}")
    private String adminKey;

    public KakaoPayResponse.Ready getRedirectUrl(long paymentId, PaymentRequest.ChargeRequest request)throws Exception{
        HttpHeaders headers=new HttpHeaders();

        String auth = "KakaoAK " + adminKey;
        headers.set("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization",auth);

        KakaoPayRequest payRequest= toReadyRequest(paymentId, request);

        HttpEntity<MultiValueMap<String, String>> urlRequest = new HttpEntity<>(payRequest.getMap(), headers);

        RestTemplate rt = new RestTemplate();
        KakaoPayResponse.Ready response = rt.postForObject(payRequest.getUrl(), urlRequest, KakaoPayResponse.Ready.class);

        return response;
    }

    @Transactional
    public KakaoPayResponse.Approve getApprove(String pgToken, String tid, Long paymentId)throws Exception{

        HttpHeaders headers=new HttpHeaders();
        String auth = "KakaoAK " + adminKey;

        headers.set("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization",auth);

        KakaoPayRequest request = toApproveRequest(tid,paymentId,pgToken);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(request.getMap(), headers);

        RestTemplate rt = new RestTemplate();
        KakaoPayResponse.Approve payApproveResDto = rt.postForObject(request.getUrl(), requestEntity, KakaoPayResponse.Approve.class);

        return payApproveResDto;


    }

}
