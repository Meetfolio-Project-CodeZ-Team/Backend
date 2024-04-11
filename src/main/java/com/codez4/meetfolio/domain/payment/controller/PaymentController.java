package com.codez4.meetfolio.domain.payment.controller;

import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.payment.dto.PaymentResponse;
import com.codez4.meetfolio.domain.payment.service.PaymentQueryService;
import com.codez4.meetfolio.global.annotation.AuthenticationMember;
import com.codez4.meetfolio.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "결제 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentQueryService paymentQueryService;

    @Operation(summary = "마이페이지 - 포인트 충전 내역 조회" , description = "마이페이지의 포인트 충전 내역을 조회합니다.")
    @GetMapping("/my-payments")
    public ApiResponse<PaymentResponse.PaymentResult> getMyPaymentList(@AuthenticationMember Member member,
                                                                       @RequestParam(value = "page", defaultValue = "0") int page){
        return ApiResponse.onSuccess(paymentQueryService.getMyPaymentList(page,member));
    }
}
