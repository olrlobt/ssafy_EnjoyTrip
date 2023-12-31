package com.ssafy.ux.board.model.service;

import com.ssafy.ux.board.mapper.BoardMapper;
import com.ssafy.ux.board.model.BoardDto;
import com.ssafy.ux.board.model.BoardListDto;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService {


    private BoardMapper boardMapper;

    public BoardServiceImpl(BoardMapper boardMapper) {
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

    @Override
    public BoardListDto listArticle(Map<String, String> map) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("word", map.get("word") == null ? "" : map.get("word"));
//        System.out.println(map.get("boardType"));
        int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
        int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
        int start = currentPage * sizePerPage - sizePerPage;
        param.put("type", map.get("boardType"));
        param.put("start", start);
        param.put("listsize", sizePerPage);

        String key = map.get("key");
        param.put("key", key == null ? "" : key);
        if ("userid".equals(key))
            param.put("key", key == null ? "" : "b.userid");
        List<BoardDto> list = boardMapper.listArticle(param);

        if ("userid".equals(key))
            param.put("key", key == null ? "" : "userid");
        int totalArticleCount = boardMapper.getTotalPage(param);
        int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;

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
