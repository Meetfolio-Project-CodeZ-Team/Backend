package com.codez4.meetfolio.domain.board.dto;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.board.EmploymentBoard;
import com.codez4.meetfolio.domain.board.GroupBoard;
import com.codez4.meetfolio.domain.enums.GroupCategory;
import com.codez4.meetfolio.domain.enums.JobKeyword;
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

    public BoardQueryItem(Board board, String email, Status status) {
        if (board instanceof EmploymentBoard employmentBoard){
            this.id = employmentBoard.getId();
            this.email = email;
            this.title = employmentBoard.getTitle();
            this.content = employmentBoard.getContent();
            this.likeCount = employmentBoard.getLikeCount();
            this.status = status == null ? Status.INACTIVE : status ;
            this.commentCount = employmentBoard.getCommentCount();
            this.jobKeyword = employmentBoard.getJobKeyword();
            this.createdAt = employmentBoard.getCreatedAt();
            this.groupCategory = null;
            this.peopleNumber = null;
            this.recruitment = null;
        }
        else if (board instanceof GroupBoard groupBoard){
            this.id = groupBoard.getId();
            this.email = email;
            this.title = groupBoard.getTitle();
            this.content = groupBoard.getContent();
            this.likeCount = groupBoard.getLikeCount();
            this.status = status == null ? Status.INACTIVE : Status.ACTIVE ;
            this.commentCount = board.getCommentCount();
            this.jobKeyword = null;
            this.createdAt = groupBoard.getCreatedAt();
            this.groupCategory = groupBoard.getGroupCategory();
            this.peopleNumber = groupBoard.getPeopleNumber();
            this.recruitment = groupBoard.getRecruitment();
        }

    }

}
