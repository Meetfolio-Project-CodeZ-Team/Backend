package com.codez4.meetfolio.domain.payment;

import com.codez4.meetfolio.domain.common.BaseTimeEntity;
import com.codez4.meetfolio.domain.enums.PaymentStatus;
import com.codez4.meetfolio.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Payment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Integer point;

    @Column(nullable = false)
    private Integer payment;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "tid")
    private String tid;

    public void updateTid(String tid){
        this.tid = tid;
    }

    public void updateStatus (PaymentStatus paymentStatus){
        this.paymentStatus = paymentStatus;
    }
}
