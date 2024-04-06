package com.codez4.meetfolio.domain.board;

import com.codez4.meetfolio.domain.enums.GroupCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("GROUP")
public class GroupBoard extends Board{

    @Column(name = "group_category",nullable = true)
    @Enumerated(value = EnumType.STRING)
    private GroupCategory groupCategory;

    @Column(name = "people_number",nullable = true)
    private Integer peopleNumber;
}
