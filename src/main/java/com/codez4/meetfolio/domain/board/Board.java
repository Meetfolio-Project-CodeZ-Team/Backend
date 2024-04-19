package com.codez4.meetfolio.domain.board;

import com.codez4.meetfolio.domain.comment.Comment;
import com.codez4.meetfolio.domain.common.BaseTimeEntity;
import com.codez4.meetfolio.domain.enums.Status;
import com.codez4.meetfolio.domain.like.Like;
import com.codez4.meetfolio.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@DynamicInsert
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "like_count")
    @ColumnDefault("'0'")
    private Integer likeCount;

    @Column(name = "comment_count")
    @ColumnDefault("'0'")
    private Integer commentCount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public Board(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * update
     */
    public void changeLike(Status status) {
        if (status.equals(Status.ACTIVE)) {
            this.likeCount -= 1;
        } else {
            this.likeCount += 1;
        }
        this.likeCount = this.likeCount < 0 ? 0 : this.likeCount;
    }

    public void changeComment(boolean isIncrease) {
        if (isIncrease) {
            this.commentCount += 1;
        } else {
            this.commentCount -= 1;
        }
    }
}
