package com.codez4.meetfolio.domain.payment.controller;

import com.codez4.meetfolio.domain.enums.PointType;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.payment.Payment;
import com.codez4.meetfolio.domain.payment.dto.PaymentRequest;
import com.codez4.meetfolio.domain.payment.dto.PaymentResponse;
import com.codez4.meetfolio.domain.payment.service.PaymentCommandService;
import com.codez4.meetfolio.domain.payment.service.PaymentQueryService;
import com.codez4.meetfolio.domain.point.dto.PointRequest;
import com.codez4.meetfolio.global.annotation.AuthenticationMember;
import com.codez4.meetfolio.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.codez4.meetfolio.domain.point.dto.PointRequest.toEntity;

@Tag(name = "결제 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentQueryService paymentQueryService;
    private final PaymentCommandService paymentCommandService;

    @Operation(summary = "포인트 충전 - 카카오 페이 연결", description = "포인트 충전 요청 시 카카오 페이 redirect url을 반환합니다.")
    @PostMapping("/payments/request")
    public ApiResponse<PaymentResponse.PaymentReady> requestPayment(@AuthenticationMember Member member,
                                                                    @Valid @RequestBody PaymentRequest.ChargeRequest request) throws Exception {
        return ApiResponse.onSuccess(paymentCommandService.readyPayment(member, request));

    }

    @Operation(summary = "포인트 충전 - 카카오 페이 승인", description = "카카오 페이 결제를 완료합니다.")
    @GetMapping("/payments/{paymentId}/success")
    public ApiResponse<PaymentResponse.PaymentApprove> successPayment(@AuthenticationMember Member member,
                                                                      @PathVariable("paymentId") Long paymentId,
                                                                      @RequestParam("pg_token") String pgToken) throws Exception {
        Payment payment = paymentQueryService.findById(paymentId);
        return ApiResponse.onSuccess(paymentCommandService.approvePayment(pgToken, payment.getKakaoPayId(), paymentId));
    }

    @Operation(summary = "마이페이지 - 포인트 충전 내역 조회", description = "마이페이지의 포인트 충전 내역을 조회합니다.")
    @GetMapping("/my-payments")
    public ApiResponse<PaymentResponse.PaymentResult> getMyPaymentList(@AuthenticationMember Member member,
                                                                       @RequestParam(value = "page", defaultValue = "0") int page) {
        return ApiResponse.onSuccess(paymentQueryService.getMyPaymentList(page, member));
    }
}
