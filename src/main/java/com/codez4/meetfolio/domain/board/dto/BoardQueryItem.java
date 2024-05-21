package com.codez4.meetfolio.domain.board.dto;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.board.EmploymentBoard;
import com.codez4.meetfolio.domain.board.GroupBoard;
import com.codez4.meetfolio.domain.enums.GroupCategory;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.enums.ProfileEmoji;
import com.codez4.meetfolio.domain.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardQueryItem {

    private Long id;

    private String email;

    private String profile;

    private String title;

    private String content;

    private Integer likeCount;

    private Status status;

    private Integer commentCount;

    private JobKeyword jobKeyword;

    private GroupCategory groupCategory;

    private String recruitment;

    private Integer peopleNumber;

    private LocalDateTime createdAt;

    public BoardQueryItem(Board board, String email, ProfileEmoji profile, Status status) {
        this.id = board.getId();
        this.email = email;
        this.profile = profile.name();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.likeCount = board.getLikeCount();
        this.status = status == null ? Status.INACTIVE : status ;
        this.commentCount = board.getCommentCount();
        this.createdAt = board.getCreatedAt();
        if (board instanceof EmploymentBoard employmentBoard){
            this.jobKeyword = employmentBoard.getJobKeyword();
            this.groupCategory = null;
            this.peopleNumber = null;
            this.recruitment = null;
        }
        else if (board instanceof GroupBoard groupBoard){
            this.jobKeyword = null;
            this.groupCategory = groupBoard.getGroupCategory();
            this.peopleNumber = groupBoard.getPeopleNumber();
            this.recruitment = groupBoard.getRecruitment();
        }

    }

}
