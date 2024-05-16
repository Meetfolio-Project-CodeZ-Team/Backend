package com.codez4.meetfolio.domain.payment.service;

import com.codez4.meetfolio.domain.enums.PaymentStatus;
import com.codez4.meetfolio.domain.enums.PointType;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.payment.Payment;
import com.codez4.meetfolio.domain.payment.dto.PaymentResponse;
import com.codez4.meetfolio.domain.payment.repository.PaymentRepository;
import com.codez4.meetfolio.domain.point.Point;
import com.codez4.meetfolio.domain.point.repository.PointRepository;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.codez4.meetfolio.domain.payment.dto.PaymentResponse.*;

@Slf4j
@Service
@AllArgsConstructor
public class PaymentQueryService {
    private final PointRepository pointRepository;
    private final PaymentRepository paymentRepository;

    public Payment findById(Long paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(() -> new ApiException(ErrorStatus._PAYMENT_NOT_FOUND));
    }

    public Payment getReadyPayment(Member member, LocalDateTime requestTime){
        LocalDateTime start = requestTime.minusMinutes(1);
        LocalDateTime end = requestTime;
        Payment payment = paymentRepository.findPaymentByMemberAndPaymentStatusAndCreatedAtIsBetweenOrderByIdDesc(member,PaymentStatus.READY,start,end).orElseThrow(() -> new ApiException(ErrorStatus._PAYMENT_NOT_FOUND));;
        return payment;

    }

    public Payment getApprovePayment(Member member, String tid){
        Payment payment = paymentRepository.findByMemberAndTidAndPaymentStatus(member, tid, PaymentStatus.READY).orElseThrow(() -> new ApiException(ErrorStatus._PAYMENT_NOT_FOUND));
        return payment;
    }


    public PaymentResponse.PaymentResult getMyPaymentList(int page, Member member) {
        PageRequest pageRequest = PageRequest.of(page, 9, Sort.by("id").descending());
        Page<Point> points = pointRepository.findByMemberAndPointType(member, PointType.CHARGE, pageRequest);
        List<PaymentResponse.PaymentItem> paymentList = points.stream().map(point -> {
            if (point.getPayment() == null) throw new ApiException(ErrorStatus._BAD_REQUEST);
            else return toPaymentItem(point.getPayment(), point);
        }).toList();
        return toPaymentResult(member, points, paymentList);
    }

}
