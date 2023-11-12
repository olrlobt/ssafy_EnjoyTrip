package com.ssafy.ux.finalpj.board.model.service;

import com.ssafy.ux.finalpj.board.mapper.BoardMapper;
import com.ssafy.ux.finalpj.board.model.BoardDto;
import com.ssafy.ux.finalpj.board.model.dao.BoardDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class BoardServiceImpl2 implements BoardService {


    private BoardMapper boardMapper;

    public BoardServiceImpl2(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @Override
    public void writeArticle(BoardDto boardDto) throws Exception {
        boardMapper.writeArticle(boardDto);
        if (boardDto.getRef() == 0) {
            boardMapper.updateRef(boardDto.getArticleNo());
        }else {
            System.out.println("boardDto = " + boardDto.getArticleNo());
            boardMapper.updateStep(boardDto.getArticleNo());
        }
    }

    @Override
    public List<BoardDto> listArticle(String type, int currentPage, int itemsPerPage) throws Exception {
        return boardMapper.listArticle(type, currentPage, itemsPerPage);
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
    public int getTotalPage(String type) throws SQLException {
        return boardMapper.getTotalPage(type);
    }

    @Override
    public BoardDto getArticleDetail(int articleNo) throws SQLException {
        return boardMapper.getArticleDetail(articleNo);
    }
}
