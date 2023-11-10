package com.ssafy.ux.finalpj.board.controller;



import com.ssafy.ux.finalpj.board.model.BoardDto;
import com.ssafy.ux.finalpj.board.model.service.BoardService;
import com.ssafy.ux.finalpj.board.model.service.BoardServiceImpl;
import com.ssafy.ux.finalpj.member.model.MemberDto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/article")
public class BoardController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private BoardService boardService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        boardService = BoardServiceImpl.getBoardService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        String path = "";
        if ("list".equals(action)) {
            path = list(request, response);
            forward(request, response, path);
        } else if ("writeForm".equals(action)) {
            path = writeForm(request, response);
            forward(request, response, path);
        } else if ("write".equals(action)) {
            path = write(request, response);
            forward(request, response, path);
        } else if ("view".equals(action)) {
            path = view(request, response);
            forward(request, response, path);
        }  else if ("mvModify".equals(action)) {
            path = mvModify(request, response);
            forward(request, response, path);
        } else if ("modify".equals(action)) {
            path = modify(request, response);
            redirect(request, response, path);
        }else if ("delete".equals(action)) {
            path = delete(request, response);
            redirect(request, response, path);
        }else {
            redirect(request, response, path);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        doGet(request, response);
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
        response.sendRedirect(request.getContextPath() + path);
    }

    private String view(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
        String articleNoParam = request.getParameter("article_no");
        String type = request.getParameter("type");
        int articleNo;

        try {
            articleNo = Integer.parseInt(articleNoParam);
        } catch (NumberFormatException e) {
            return "/error.jsp";
        }

        if (memberDto != null) {
            try {
                BoardDto boardDto = boardService.getArticleDetail(articleNo);
                request.setAttribute("article", boardDto);
                request.setAttribute("type", type);
                return "/article/detail.jsp";
            } catch (Exception e) {
                e.printStackTrace();
                return "/error.jsp";
            }
        }
        return "/user/login.jsp";
    }


    private String list(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
        String type = request.getParameter("type");
        String currentPageParam = request.getParameter("currentPage");
        int currentPage;

        if (currentPageParam == null || currentPageParam.trim().isEmpty()) {
            currentPage = 1;
        } else {
            try {
                currentPage = Integer.parseInt(currentPageParam);
                if (currentPage < 1) {
                    currentPage = 1;
                }
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        if (memberDto != null) {
            try {
                int totalPages = boardService.getTotalPage(type, 10);
                List<BoardDto> list = boardService.listArticle(type, currentPage, 10);
                request.setAttribute("articles", list);
                request.setAttribute("type", type);
                request.setAttribute("currentPage", currentPage);
                request.setAttribute("totalPages", totalPages);

                return "/article/list.jsp";
            } catch (Exception e) {
                e.printStackTrace();
                return "/temp_index.jsp";
            }
        }
        return "/user/login.jsp";
    }

    private String writeForm(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
        String type = request.getParameter("type");
        if (memberDto != null) {
            try {
                request.setAttribute("type", type);
                return "/article/write.jsp";
            } catch (Exception e) {
                e.printStackTrace();
                return "/temp_index.jsp";
            }
        }
        return "/user/login.jsp";
    }

    private String write(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
        String type = request.getParameter("type");
        if (memberDto != null) {
            BoardDto boardDto = new BoardDto();
            boardDto.setUserId(memberDto.getUserId());
            boardDto.setSubject(request.getParameter("title"));
            boardDto.setContent(request.getParameter("content"));
            boardDto.setType(type);

            try {
                boardService.writeArticle(boardDto);
                return "/article?action=list&type=" + type;
            } catch (Exception e) {
                e.printStackTrace();
                return "/temp_index.jsp";
            }
        }
        return "/user/login.jsp";
    }

    private String mvModify(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 1. 수정하고자하는 글의 글번호를 얻는다.
            String articleNoParam = request.getParameter("article_no");
            if(articleNoParam == null || articleNoParam.trim().isEmpty()) {
                request.setAttribute("errorMessage", "유효하지 않은 글 번호입니다.");
                return "/article/list.jsp";
            }

            int articleNo = Integer.parseInt(articleNoParam);
            BoardDto article = boardService.getArticleDetail(articleNo);

            if(article == null) {
                request.setAttribute("errorMessage", "존재하지 않는 글입니다.");
                return "/article/list.jsp";
            }
            request.setAttribute("article", article);
            request.setAttribute("type", request.getAttribute("type"));
            return "/article/modify.jsp";

        } catch(Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "글 정보 조회 중 오류가 발생했습니다.");
            return "/error.jsp";
        }
    }


    private String modify(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");
        try {
            String articleNoParam = request.getParameter("article_no");
            int articleNo = Integer.parseInt(articleNoParam);

            BoardDto articleDetail = new BoardDto();
            articleDetail.setArticleNo(articleNo);
            articleDetail.setUserId(articleDetail.getUserId());
            articleDetail.setSubject(request.getParameter("title"));
            articleDetail.setContent(request.getParameter("content"));
            articleDetail.setType(type);
            boardService.modifyArticle(articleDetail);

            return "/article?action=view&article_no=" + articleNo + "&type=" + type;
        } catch (Exception e) {
            e.printStackTrace();
            return "/error.jsp";
        }
    }


    private String delete(HttpServletRequest request, HttpServletResponse response) {
        try {
            String articleNoParam = request.getParameter("article_no");
            String type = request.getParameter("type");
            if(articleNoParam == null || articleNoParam.trim().isEmpty()) {
                request.setAttribute("errorMessage", "유효하지 않은 글 번호입니다.");
                return "/article/list.jsp";
            }
            int articleNo = Integer.parseInt(articleNoParam);
            boardService.deleteArticle(articleNo);

            return "/article?action=list&type=" + type;

        } catch(Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "글 삭제 중 오류가 발생했습니다.");
            return "/error.jsp";
        }
    }


}
