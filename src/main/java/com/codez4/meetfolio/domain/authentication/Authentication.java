package com.codez4.meetfolio.domain.authentication;

import com.codez4.meetfolio.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Authentication extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authentication_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String code;

}
