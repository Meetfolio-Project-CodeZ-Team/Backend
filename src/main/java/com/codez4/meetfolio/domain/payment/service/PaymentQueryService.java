package com.codez4.meetfolio.domain.payment.service;

import com.codez4.meetfolio.domain.admin.dto.PaymentAdminResponse;
import com.codez4.meetfolio.domain.enums.Authority;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.codez4.meetfolio.domain.admin.dto.PaymentAdminResponse.toPaymentAdminItem;
import static com.codez4.meetfolio.domain.payment.dto.PaymentResponse.toPaymentItem;
import static com.codez4.meetfolio.domain.payment.dto.PaymentResponse.toPaymentResult;

@Service
@AllArgsConstructor
public class PaymentQueryService {
    private final PointRepository pointRepository;
    private final PaymentRepository paymentRepository;

    public Payment findById(Long paymentId){
       return paymentRepository.findById(paymentId).orElseThrow(()-> new ApiException(ErrorStatus._PAYMENT_NOT_FOUND));
    }

    public PaymentResponse.PaymentResult getMyPaymentList(int page, Member member) {
        PageRequest pageRequest = PageRequest.of(page, 9, Sort.by("createdAt").descending());
        Page<Point> points = pointRepository.findByMemberAndPointType(member, PointType.CHARGE, pageRequest);
        List<PaymentResponse.PaymentItem> paymentList = points.stream().map(point -> {
            if (point.getPayment() == null) throw new ApiException(ErrorStatus._BAD_REQUEST);
            else return toPaymentItem(point.getPayment(), point);
        }).toList();
        return toPaymentResult(member, points, paymentList);
    }

    public PaymentAdminResponse.PaymentResult getPaymentList(int page, int year, int month) {
        String requestMonth = Integer.toString(year) + '-' + month;
        int totalSales = paymentRepository.queryGetTotalSales(requestMonth);
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        String yearMonth = year + "년" + " " + month + "월";

        Page<Payment> payments = paymentRepository.findByMember_Authority(Authority.MEMBER, pageRequest);
        List<PaymentAdminResponse.PaymentItem> paymentList = payments.stream().map(payment -> {
            Point point = pointRepository.getPointByPayment(payment).orElseThrow(() -> new ApiException(ErrorStatus._BAD_REQUEST));
            return toPaymentAdminItem(payment, point);
        }).toList();
        return PaymentAdminResponse.toPaymentResult(yearMonth, totalSales, payments, paymentList);

    }
}
