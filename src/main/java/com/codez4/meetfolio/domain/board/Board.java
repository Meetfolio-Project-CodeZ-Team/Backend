package com.codez4.meetfolio.domain.board;

import com.codez4.meetfolio.domain.comment.Comment;
import com.codez4.meetfolio.domain.common.BaseTimeEntity;
import com.codez4.meetfolio.domain.like.Like;
import com.codez4.meetfolio.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@DynamicInsert
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id",nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "like_count",nullable = false)
    @ColumnDefault("'0'")
    private Integer likeCount;

    @Column(name = "comment_count", nullable = false)
    @ColumnDefault("'0'")
    private Integer commentCount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
}
