package com.codez4.meetfolio.domain.emailAuth;

import com.codez4.meetfolio.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "email_auth")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EmailAuth extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_auth_id",nullable = false)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String code;

}
