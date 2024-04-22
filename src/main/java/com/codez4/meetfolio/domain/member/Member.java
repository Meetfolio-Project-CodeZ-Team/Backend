package com.codez4.meetfolio.domain.member;

import com.codez4.meetfolio.domain.common.BaseTimeEntity;
import com.codez4.meetfolio.domain.enums.*;
import com.codez4.meetfolio.domain.member.dto.MemberRequest;
import com.codez4.meetfolio.global.security.Password;
import com.codez4.meetfolio.global.utils.TimeUtils;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

import static com.codez4.meetfolio.global.security.Password.ENCODER;

@DynamicInsert
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Embedded
    private Password password;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Column(nullable = false)
    @ColumnDefault("'0'")
    private Integer point;

    @Column(nullable = false)
    @ColumnDefault("'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private String profile;

    @Column(nullable = false)
    @ColumnDefault("'MEMBER'")
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Column
    private LocalDateTime inactiveDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JobKeyword jobKeyword;

    public void setInactive(){
        this.point = 0;
        this.inactiveDate = TimeUtils.getCurrentTime();
        this.status = Status.INACTIVE;
    }

    /**
     * update
     */
    public void update(MemberRequest.Patch request) {
        this.password = Password.encrypt(request.getPassword(), ENCODER);
        this.major = request.getMajor();
        this.grade = Grade.convert(request.getGrade());
        this.jobKeyword = JobKeyword.convert(request.getJobKeyword());
    }
      
    public void setPoint(int point){
        this.point = point;
    }
}
