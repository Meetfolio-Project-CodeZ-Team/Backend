package com.codez4.meetfolio.domain.board;

import com.codez4.meetfolio.domain.board.dto.BoardRequest;
import com.codez4.meetfolio.domain.enums.GroupCategory;
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
@DiscriminatorValue("GROUP")
@DynamicInsert
public class GroupBoard extends Board {

    @Column(name = "group_category")
    @Enumerated(value = EnumType.STRING)
    private GroupCategory groupCategory;

    @Column(name = "people_number")
    private Integer peopleNumber;

    @Column
    private String recruitment;

    @Builder
    public GroupBoard(String title, String content, GroupCategory groupCategory, int peopleNumber, String recruitment, Member member) {
        super(title, content, member);
        this.groupCategory = groupCategory;
        this.peopleNumber = peopleNumber;
        this.recruitment = recruitment;
    }

    public void update(BoardRequest.GroupBoardPatch patch) {
        super.update(patch.getTitle(), patch.getContent());
        this.groupCategory = patch.getGroupCategory();
        this.peopleNumber = patch.getPeopleNumber();
        this.recruitment = patch.getRecruitment();
    }
}
