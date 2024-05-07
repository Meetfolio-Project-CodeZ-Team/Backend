package com.codez4.meetfolio.domain.payment.service;


import com.codez4.meetfolio.domain.enums.PaymentStatus;
import com.codez4.meetfolio.domain.enums.PointType;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.payment.Payment;
import com.codez4.meetfolio.domain.payment.dto.KakaoPayResponse;
import com.codez4.meetfolio.domain.payment.dto.PaymentRequest;
import com.codez4.meetfolio.domain.payment.dto.PaymentResponse;
import com.codez4.meetfolio.domain.payment.repository.PaymentRepository;
import com.codez4.meetfolio.domain.point.dto.PointRequest;
import com.codez4.meetfolio.domain.point.repository.PointRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.codez4.meetfolio.domain.payment.dto.PaymentRequest.toEntity;
import static com.codez4.meetfolio.domain.payment.dto.PaymentResponse.*;
import static com.codez4.meetfolio.domain.point.dto.PointRequest.toEntity;

@Service
@AllArgsConstructor
@Transactional
public class PaymentCommandService {

    private final PaymentRepository paymentRepository;
    private final PointRepository pointRepository;
    private final KakaoPayService kakaoPayService;

    private Payment post(PaymentRequest.Post paymentPost) {
        return paymentRepository.save(toEntity(paymentPost));
    }

    public PaymentProc saveReadyPayment(Member member, PaymentRequest.ReadyRequest request) {
        PaymentRequest.Post paymentPost = PaymentRequest.Post.builder()
                .point(request.getPoint())
                .payment(request.getPayment())
                .member(member)
                .paymentStatus(PaymentStatus.READY)
                .kakaoPayId(request.getTid())
                .build();
        Payment payment = post(paymentPost);
        return toPaymentProc(payment);
    }

    public PaymentProc saveApprovePayment(Member member) {
        Payment payment = paymentRepository.findTop1ByMemberOrderByIdDesc(member);
        payment.updateStatus(PaymentStatus.APPROVE);
        int totalPoint = member.getPoint() - payment.getPoint();
        PointRequest.Post pointPost = PointRequest.Post.builder()
                .payment(payment)
                .point(payment.getPoint())
                .pointType(PointType.CHARGE)
                .member(payment.getMember())
                .totalPoint(totalPoint)
                .build();
        pointRepository.save(toEntity(pointPost));
        member.setPoint(totalPoint);
        return toPaymentProc(payment);
    }

    public PaymentResponse.PaymentReady readyPayment(Member member, PaymentRequest.ChargeRequest request) throws Exception {
        PaymentRequest.Post paymentPost = PaymentRequest.Post.builder()
                .point(request.getPoint())
                .payment(request.getPayment())
                .member(member)
                .kakaoPayId(null)
                .paymentStatus(null)
                .build();
        Payment payment = post(paymentPost);
        KakaoPayResponse.Ready response = kakaoPayService.getRedirectUrl(payment.getId(), request);

        payment.updateKakaoPayId(response.getTid());
        payment.updateStatus(PaymentStatus.READY);
        return toPaymentReady(payment.getId(), response);
    }

    public PaymentResponse.PaymentApprove approvePayment(String pgToken, String tid, Long paymentId) throws Exception {
        KakaoPayResponse.Approve response = kakaoPayService.getApprove(pgToken, tid, paymentId);
        Payment payment = paymentRepository.findById(paymentId).get();
        payment.updateStatus(PaymentStatus.APPROVE);
        Member member = payment.getMember();
        int totalPoint = member.getPoint() - payment.getPoint();

        PointRequest.Post pointPost = PointRequest.Post.builder()
                .payment(payment)
                .point(payment.getPoint())
                .pointType(PointType.CHARGE)
                .member(payment.getMember())
                .totalPoint(payment.getMember().getPoint() - payment.getPoint())
                .build();
        pointRepository.save(toEntity(pointPost));
        member.setPoint(totalPoint);
        return toPaymentApprove(paymentId, response);
    }


}
