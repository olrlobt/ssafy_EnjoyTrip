package com.ssafy.ux.finalpj.board.model.service;

import com.ssafy.ux.finalpj.board.mapper.BoardMapper;
import com.ssafy.ux.finalpj.board.model.BoardDto;
import com.ssafy.ux.finalpj.board.model.BoardListDto;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl2 implements BoardService {


    private BoardMapper boardMapper;

    public BoardServiceImpl2(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @Override
    public void writeArticle(BoardDto boardDto) throws Exception {

        if (boardDto.getRef() == 0) { // 답글 아님
            boardMapper.writeArticle(boardDto);
            boardMapper.updateRef(boardDto.getArticleNo());
        } else { // 답글
            boardMapper.updateStep(boardDto);
            boardDto.setStep(boardDto.getStep() + 1);
            boardDto.setDepth(boardDto.getDepth() + 1);
            boardMapper.writeArticle(boardDto);
        }
    }

//    @Override
//    public List<BoardDto> listArticle(String type, int currentPage, int itemsPerPage) throws Exception {
//        int startIdx = (currentPage - 1) * itemsPerPage;
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("type", type);
//        parameters.put("startIdx", startIdx);
//        parameters.put("itemsPerPage", itemsPerPage);
//        return boardMapper.listArticle(parameters);
//    }
    public BoardListDto listArticle(String type, int currentPage, int itemsPerPage) throws Exception {
        int startIdx = currentPage * itemsPerPage - itemsPerPage;
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("type", type);
        parameters.put("startIdx", startIdx);
        parameters.put("itemsPerPage", itemsPerPage);
        List<BoardDto> list = boardMapper.listArticle(parameters);
//        System.out.println("itemsPerPage: "+ itemsPerPage);
//        System.out.println("가져온 리스트 사이즈"+list.size());

        int totalArticleCount = boardMapper.getTotalPage(parameters);
        System.out.println(totalArticleCount);
        int totalPageCount = (totalArticleCount - 1) / itemsPerPage + 1;

        BoardListDto boardListDto = new BoardListDto();
        boardListDto.setArticles(list);
        boardListDto.setCurrentPage(currentPage);
        boardListDto.setTotalPageCount(totalPageCount);
        return boardListDto;
    }

    @Override
    public BoardDto getArticle(int articleNo) throws Exception {
        return boardMapper.getArticle(articleNo);
    }

    @Override
    public void updateHit(int articleNo) throws Exception {
        boardMapper.updateHit(articleNo);
    }

    @Override
    public void modifyArticle(BoardDto boardDto) throws Exception {
        boardMapper.modifyArticle(boardDto);
    }

    @Override
    public void deleteArticle(int articleNo) throws Exception {
        boardMapper.deleteArticle(articleNo);
    }

    @Override
    public int getTotalPage(String type, int itemsPerPage) throws SQLException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("type", type);
        parameters.put("itemsPerPage", itemsPerPage);
        return boardMapper.getTotalPage(parameters);
    }

    @Override
    public BoardDto getArticleDetail(int articleNo) throws SQLException {
        return boardMapper.getArticleDetail(articleNo);
    }
}
