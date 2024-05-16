package com.codez4.meetfolio.domain.payment.controller;

import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.payment.Payment;
import com.codez4.meetfolio.domain.payment.dto.PaymentRequest;
import com.codez4.meetfolio.domain.payment.dto.PaymentResponse;
import com.codez4.meetfolio.domain.payment.service.PaymentCommandService;
import com.codez4.meetfolio.domain.payment.service.PaymentQueryService;
import com.codez4.meetfolio.global.annotation.AuthenticationMember;
import com.codez4.meetfolio.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.codez4.meetfolio.domain.payment.dto.PaymentResponse.toPaymentProc;

@Tag(name = "충전 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentQueryService paymentQueryService;
    private final PaymentCommandService paymentCommandService;

    @Operation(summary = "카카오 페이 요청 정보 등록", description = "카카오 페이 결제 요청 정보를 저장합니다.")
    @PostMapping("/payments/ready")
    public ApiResponse<PaymentResponse.PaymentProc> savePayment(@AuthenticationMember Member member,
                                                                    @Valid @RequestBody PaymentRequest.ReadyRequest request){
        return ApiResponse.onSuccess(paymentCommandService.saveReadyPayment(member, request));

    }

    @Operation(summary = "카카오페이 tid 요청", description = "결제 대기중인 카카오페이 tid 값을 응답합니다.")
    @GetMapping("/payments/ready")
    public ApiResponse<PaymentResponse.PaymentProc> getReadyPayment(@AuthenticationMember Member member){
        LocalDateTime requestTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        return ApiResponse.onSuccess(toPaymentProc(paymentQueryService.getReadyPayment(member, requestTime)));

    }

    @Operation(summary = "카카오 페이 승인 정보 등록", description = "카카오 페이 승인 정보 저장 및 포인트를 충전합니다.")
    @PostMapping("/payments/approve")
    @Parameter(name = "paymentId", description = "결제 id, Path Variable 입니다.", required = true, in = ParameterIn.PATH)
    public ApiResponse<PaymentResponse.PaymentProc> saveApprovePayment(@AuthenticationMember Member member,
                                                                       @RequestBody PaymentRequest.ApproveRequest request) {
        Payment payment = paymentQueryService.getApprovePayment(member,request.getTid());
        return ApiResponse.onSuccess(paymentCommandService.saveApprovePayment(member, payment));

    }

    @Operation(summary = "마이페이지 - 포인트 충전 내역 조회", description = "마이페이지의 포인트 충전 내역을 조회합니다.")
    @GetMapping("/my-payments")
    public ApiResponse<PaymentResponse.PaymentResult> getMyPaymentList(@AuthenticationMember Member member,
                                                                       @RequestParam(value = "page", defaultValue = "0") int page) {
        return ApiResponse.onSuccess(paymentQueryService.getMyPaymentList(page, member));
    }
}
