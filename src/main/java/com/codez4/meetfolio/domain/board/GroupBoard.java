package com.codez4.meetfolio.domain.board;

import com.codez4.meetfolio.domain.enums.GroupCategory;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("GROUP")
public class GroupBoard extends Board {

    @Column(name = "group_category")
    @Enumerated(value = EnumType.STRING)
    private GroupCategory groupCategory;

    @Column(name = "people_number")
    private Integer peopleNumber;

    @Column
    private String recruitment;
}
