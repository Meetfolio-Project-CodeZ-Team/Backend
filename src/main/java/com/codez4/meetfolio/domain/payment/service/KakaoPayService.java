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

        /** 요청 헤더 */
        String auth = "KakaoAK " + adminKey;
        headers.set("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization",auth);

        /** 요청 Body */
        KakaoPayRequest payRequest= toReadyRequest(paymentId, request);

        /** Header와 Body 합쳐서 RestTemplate로 보내기 위한 밑작업 */
        HttpEntity<MultiValueMap<String, String>> urlRequest = new HttpEntity<>(payRequest.getMap(), headers);

        /** RestTemplate로 Response 받아와서 DTO로 변환후 return */
        RestTemplate rt = new RestTemplate();
        KakaoPayResponse.Ready response = rt.postForObject(payRequest.getUrl(), urlRequest, KakaoPayResponse.Ready.class);

        return response;
    }

    @Transactional
    public KakaoPayResponse.Approve getApprove(String pgToken, String tid, Long paymentId)throws Exception{

        HttpHeaders headers=new HttpHeaders();
        String auth = "KakaoAK " + adminKey;

        // 요청 헤더
        headers.set("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization",auth);

        //요청 Body
        KakaoPayRequest request = toApproveRequest(tid,paymentId,pgToken);

        //** Header와 Body 합쳐서 RestTemplate로 보내기 위한 밑작업 *//*
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(request.getMap(), headers);

        // 요청 보내기
        RestTemplate rt = new RestTemplate();
        KakaoPayResponse.Approve payApproveResDto = rt.postForObject(request.getUrl(), requestEntity, KakaoPayResponse.Approve.class);

        return payApproveResDto;


    }

}
