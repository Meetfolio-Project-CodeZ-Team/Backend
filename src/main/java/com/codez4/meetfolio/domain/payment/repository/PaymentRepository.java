package com.codez4.meetfolio.domain.payment.repository;

import com.codez4.meetfolio.domain.enums.Authority;
import com.codez4.meetfolio.domain.enums.PointType;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.payment.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Page<Payment> findByMember_Authority(Authority authority, Pageable pageable);

    Page<Payment> findByMemberAndPoint_PointType(Member member, PointType point_pointType, Pageable pageable);

    @Query("SELECT IFNULL(MAX(PAYMENTSUM.POINT), 0) FROM (SELECT DATE_FORMAT(p.createdAt, '%Y-%c') AS MONTH, sum(p.payment) AS POINT FROM Payment p GROUP BY MONTH) AS PAYMENTSUM WHERE PAYMENTSUM.MONTH =:month")
    int queryGetTotalSales(String month);
}
