package com.codez4.meetfolio.domain.payment.service;

import com.codez4.meetfolio.domain.enums.Authority;
import com.codez4.meetfolio.domain.payment.dto.PaymentResponse;
import com.codez4.meetfolio.domain.payment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.codez4.meetfolio.domain.payment.dto.PaymentResponse.toPaymentResult;

@Service
@AllArgsConstructor
public class PaymentQueryService {
    private final PaymentRepository paymentRepository;

    public PaymentResponse.PaymentResult getPaymentList(int page, int year, int month) {
        String requestMonth = Integer.toString(year) + '-' + month;
        int totalSales = paymentRepository.queryGetTotalSales(requestMonth);
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        String yearMonth = year + "년" + " " + month + "월";
        return toPaymentResult(yearMonth, totalSales, paymentRepository.findByMember_Authority(Authority.MEMBER, pageRequest));

    }
}
