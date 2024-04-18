package com.codez4.meetfolio.domain.comment.service;

import com.codez4.meetfolio.domain.board.dto.BoardQueryItem;
import com.codez4.meetfolio.domain.board.dto.BoardResponse;
import com.codez4.meetfolio.domain.comment.repository.CommentRepository;
import com.codez4.meetfolio.domain.like.service.LikeQueryService;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.response.SliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentQueryService {

    private final CommentRepository commentRepository;
    private final LikeQueryService likeQueryService;

    public SliceResponse<BoardResponse.BoardItem> findMyComments(Member member, Integer page) {

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("id").descending());
        Slice<BoardQueryItem> commentBoards = commentRepository.findByMemberFetchJoinBoard(member, pageRequest);
        // 중복된 게시글 제거하기
        List<BoardResponse.BoardItem> distinctBoards = getDistinctBoards(commentBoards.getContent());

        SliceImpl<BoardResponse.BoardItem> boards = new SliceImpl<>(distinctBoards, pageRequest,
                commentBoards.hasNext());

        return new SliceResponse<>(boards);
    }

    private List<BoardResponse.BoardItem> getDistinctBoards(List<BoardQueryItem> boards) {
        return boards.stream()
            .map(BoardResponse::toBoardItem)
            .distinct()
                .collect(Collectors.toList());

    }
}
