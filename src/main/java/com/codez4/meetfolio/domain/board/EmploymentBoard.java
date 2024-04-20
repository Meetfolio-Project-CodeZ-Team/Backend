package com.codez4.meetfolio.domain.board;

import com.codez4.meetfolio.domain.board.dto.BoardRequest;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("EMPLOYMENT")
@DynamicInsert
public class EmploymentBoard extends Board{

    @Column(name = "job_category")
    @Enumerated(value = EnumType.STRING)
    private JobKeyword jobKeyword;

    @Builder
    public EmploymentBoard(String title, String content, JobKeyword jobKeyword, Member member){
        super(title, content, member);
        this.jobKeyword = jobKeyword;
    }

    public void update(BoardRequest.EmploymentBoardPatch patch) {
        super.update(patch.getTitle(), patch.getContent());
        this.jobKeyword = patch.getJobKeyword();
    }
}
