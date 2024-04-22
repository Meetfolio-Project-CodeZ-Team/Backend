package com.codez4.meetfolio.domain.board.controller;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.board.EmploymentBoard;
import com.codez4.meetfolio.domain.board.GroupBoard;
import com.codez4.meetfolio.domain.board.dto.BoardRequest;
import com.codez4.meetfolio.domain.board.dto.BoardResponse;
import com.codez4.meetfolio.domain.board.service.BoardCommandService;
import com.codez4.meetfolio.domain.board.service.BoardQueryService;
import com.codez4.meetfolio.domain.enums.BoardType;
import com.codez4.meetfolio.domain.enums.GroupCategory;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.global.annotation.AuthenticationMember;
import com.codez4.meetfolio.global.annotation.EnumValid;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.ApiResponse;
import com.codez4.meetfolio.global.response.SliceResponse;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import com.codez4.meetfolio.global.utils.TimeUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.codez4.meetfolio.domain.board.dto.BoardResponse.*;

@Tag(name = "커뮤니티 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardQueryService boardQueryService;
    private final BoardCommandService boardCommandService;

    @Operation(summary = "취업 정보 게시판 조회", description = "커뮤니티 메뉴에서 취업 정보 게시판을 조회합니다.")
    @Parameter(name = "page", description = "페이징 번호, page, Query String입니다.", example = "0", in = ParameterIn.QUERY)
    @Parameter(name = "category", description = "직무 키워드, Query String입니다. BACKEND/WEB/APP/DESIGN/AI", example = "BACKEND", in = ParameterIn.QUERY)
    @GetMapping("/employment")
    public ApiResponse<BoardResponse.BoardListResult> getEmploymentBoards(@AuthenticationMember Member member,
                                                                             @RequestParam(name = "page") Integer page,
                                                                             @RequestParam(name = "category", required = false) String category) {
        MemberResponse.MemberInfo memberInfo = MemberResponse.toMemberInfo(member);
        if (category != null) {
            JobKeyword jobKeyword = JobKeyword.convert(category);
            return ApiResponse.onSuccess(toBoardListResult(memberInfo, boardQueryService.getEmploymentBoardsByJobKeyword(member, page, jobKeyword)));
        } else
            return ApiResponse.onSuccess(toBoardListResult(memberInfo, boardQueryService.getEmploymentBoards(member, page)));
    }

    @Operation(summary = "그룹원 모집 게시판 조회", description = "커뮤니티 메뉴에서 그룹원 모집 게시판을 조회합니다.")
    @Parameter(name = "page", description = "페이징 번호, page, Query String입니다.", example = "0", in = ParameterIn.QUERY)
    @Parameter(name = "category", description = "그룹 카테고리, Query String입니다. STUDY/CONTEST", example = "STUDY", in = ParameterIn.QUERY)
    @GetMapping("/group")
    public ApiResponse<BoardResponse.BoardListResult> getJobBoards(@AuthenticationMember Member member,
                                                                      @RequestParam(name = "page") Integer page,
                                                                      @RequestParam(name = "category", required = false) String category) {
        MemberResponse.MemberInfo memberInfo = MemberResponse.toMemberInfo(member);
        if (category != null) {
            GroupCategory groupCategory = GroupCategory.convert(category);
            return ApiResponse.onSuccess(toBoardListResult(memberInfo, boardQueryService.getGroupBoardsByGroupCategory(member, page, groupCategory)));
        } else
            return ApiResponse.onSuccess(toBoardListResult(memberInfo, boardQueryService.getGroupBoards(member, page)));
    }

    @Operation(summary = "게시물 검색", description = "검색어와 게시판 유형으로 게시물을 조회합니다.")
    @GetMapping
    @Parameter(name = "keyword", description = "검색어, Query String입니다.", in = ParameterIn.QUERY)
    @Parameter(name = "type", description = "게시물 유형, Query String입니다. EMPLOYMENT/GROUP", in = ParameterIn.QUERY)
    public ApiResponse<BoardResponse.BoardListResult> getBoardsByKeyword(@AuthenticationMember Member member,
                                                                         @RequestParam(name = "page") Integer page,
                                                                         @RequestParam(name = "type") @EnumValid (enumClass = BoardType.class) String type,
                                                                         @RequestParam(name = "keyword") String keyword){
        MemberResponse.MemberInfo memberInfo = MemberResponse.toMemberInfo(member);
        SliceResponse<BoardItem> boardInfo = boardQueryService.getBoardsByKeyword(member, keyword, BoardType.convert(type), page);
        return ApiResponse.onSuccess(toBoardListResult(memberInfo, boardInfo));
    }

    @Operation(summary = "취업 정보 게시물 작성")
    @PostMapping("/employment")
    public ApiResponse<BoardResponse.BoardProc> saveEmploymentBoard(@AuthenticationMember Member member,
                                                                    @Valid @RequestBody BoardRequest.EmploymentBoardRequest request) {
        EmploymentBoard employmentBoard = boardCommandService.saveEmploymentBoard(request, member);
        return ApiResponse.onSuccess(toBoardProc(employmentBoard, employmentBoard.getCreatedAt()));
    }

    @Operation(summary = "그룹원 모집 게시물 작성")
    @PostMapping("/group")
    public ApiResponse<BoardResponse.BoardProc> saveGroupBoard(@AuthenticationMember Member member,
                                                               @Valid @RequestBody BoardRequest.GroupBoardRequest request) {
        GroupBoard groupBoard = boardCommandService.saveGroupBoard(request, member);
        return ApiResponse.onSuccess(toBoardProc(groupBoard, groupBoard.getCreatedAt()));
    }

    @Operation(summary = "게시물 1개 조회")
    @Parameter(name = "boardId", description = "게시물 Id, Path Variable입니다.", in = ParameterIn.PATH)
    @PostMapping("/{boardId}")
    public ApiResponse<BoardResponse.BoardResult> getBoard(@AuthenticationMember Member member,
                                                           @PathVariable Long boardId) {
        BoardResponse.BoardItem boardItem = boardQueryService.getBoard(member, boardId);
        MemberResponse.MemberInfo memberInfo = MemberResponse.toMemberInfo(member);
        return ApiResponse.onSuccess(toBoardResult(memberInfo, boardItem));
    }
    @Operation(summary = "게시물 수정")
    @Parameter(name = "boardId", description = "게시물 Id, Path Variable입니다.", in = ParameterIn.PATH)
    @PatchMapping("/{boardId}")
    public ApiResponse<BoardResponse.BoardProc> updateBoard(@AuthenticationMember Member member,
                                                            @PathVariable Long boardId,
                                                            @Valid @RequestBody BoardRequest.BoardUpdate request) {
        if (boardQueryService.isBoardMember(boardId, member)) {
            Board board = boardQueryService.findById(boardId);
            boardCommandService.updateBoard(board, request);
            return ApiResponse.onSuccess(toBoardProc(board, TimeUtils.getCurrentTime()));
        } else throw new ApiException(ErrorStatus._FORBIDDEN);
    }

    @Operation(summary = "게시물 삭제")
    @Parameter(name = "boardId", description = "게시물 Id, Path Variable입니다.", in = ParameterIn.PATH)
    @DeleteMapping("/{boardId}")
    public ApiResponse<BoardResponse.BoardProc> deleteBoard(@AuthenticationMember Member member,
                                                            @PathVariable Long boardId) {
        if (boardQueryService.isBoardMember(boardId, member)) {
            Board board = boardQueryService.findById(boardId);
            boardCommandService.deleteBoard(board);
            return ApiResponse.onSuccess(toBoardProc(board, TimeUtils.getCurrentTime()));
        } else throw new ApiException(ErrorStatus._FORBIDDEN);
    }
}
