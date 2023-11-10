//package com.ssafy.ux.finalpj.board.model.service;
//
//import com.ssafy.ux.finalpj.board.model.BoardDto;
//import com.ssafy.ux.finalpj.board.model.dao.BoardDao;
//import com.ssafy.ux.finalpj.board.model.dao.BoardDaoImpl;
//
//import java.sql.SQLException;
//import java.util.List;
//
//public class BoardServiceImpl implements BoardService {
//
//    private static BoardService boardService = new BoardServiceImpl();
//    private BoardDao boardDao;
//
//    private BoardServiceImpl() {
//        boardDao = BoardDaoImpl.getBoardDao();
//    }
//
//    public static BoardService getBoardService() {
//        return boardService;
//    }
//
//    public int getTotalPage(String type, int itemsPerPage) throws SQLException {
//        return boardDao.getTotalPage(type, itemsPerPage);
//    }
//
//    @Override
//    public BoardDto getArticleDetail(int articleNo) throws SQLException {
//        return boardDao.getArticleDetail(articleNo);
//    }
//
//    @Override
//    public void writeArticle(BoardDto boardDto) throws Exception {
//        boardDao.writeArticle(boardDto);
//    }
//
//    @Override
//    public List<BoardDto> listArticle(String type, int currentPage, int itemsPerPage, int totalPages) throws Exception {
//        return boardDao.listArticle(type, currentPage, itemsPerPage);
//    }
//
//    @Override
//    public BoardDto getArticle(int articleNo) throws Exception {
//        return boardDao.getArticle(articleNo);
//    }
//
//    @Override
//    public void updateHit(int articleNo) throws Exception {
//        boardDao.updateHit(articleNo);
//    }
//
//    @Override
//    public void modifyArticle(BoardDto boardDto) throws Exception {
//        boardDao.modifyArticle(boardDto);
//    }
//
//    @Override
//    public void deleteArticle(int articleNo) throws Exception {
//        boardDao.deleteArticle(articleNo);
//    }
//
//}
