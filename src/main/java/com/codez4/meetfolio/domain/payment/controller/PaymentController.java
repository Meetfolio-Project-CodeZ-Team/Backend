package com.codez4.meetfolio.domain.payment.controller;

import com.codez4.meetfolio.domain.enums.PointType;
import com.codez4.meetfolio.domain.member.Member;
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

    @PostMapping("/payments")
    public ApiResponse<PaymentResponse.PaymentProc> makePayment(@AuthenticationMember Member member,
                                                                @Valid @RequestBody PaymentRequest.ChargeRequest request) {
        PointRequest.Post pointPost = PointRequest.Post.builder()
                .point(request.getPoint())
                .pointType(PointType.CHARGE)
                .member(member)
                .totalPoint(member.getPoint() - request.getPoint())
                .build();
        PaymentRequest.Post paymentPost = PaymentRequest.Post.builder()
                .payment(request.getPayment())
                .member(member)
                .build();
        return ApiResponse.onSuccess(paymentCommandService.makePayment(member, pointPost, paymentPost));

    }

    @Operation(summary = "마이페이지 - 포인트 충전 내역 조회", description = "마이페이지의 포인트 충전 내역을 조회합니다.")
    @GetMapping("/my-payments")
    public ApiResponse<PaymentResponse.PaymentResult> getMyPaymentList(@AuthenticationMember Member member,
                                                                       @RequestParam(value = "page", defaultValue = "0") int page) {
        return ApiResponse.onSuccess(paymentQueryService.getMyPaymentList(page, member));
    }
}
