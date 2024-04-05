package com.codez4.meetfolio.domain.payment;

import com.codez4.meetfolio.domain.point.Point;
import com.codez4.meetfolio.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "point_id", nullable = false)
    private Point point;

    @Column(nullable = false)
    private Integer payment;

}
