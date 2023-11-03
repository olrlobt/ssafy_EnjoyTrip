//package com.ssafy.ux.finalpj.controller;
//
//import com.ssafy.ux.finalpj.member.model.service.MemberService;
//import com.ssafy.ux.finalpj.member.model.service.MemberServiceImpl;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebServlet("/map")
//public class MapController extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    private MemberService memberService;
//
//    public void init() {
//        memberService = MemberServiceImpl.getMemberService();
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.getSession().removeAttribute("msg");
//        String action = request.getParameter("action");
//
//        String path = "";
//        if ("map".equals(action)) {
//            path = "/map/map.jsp";
//            forward(request,response,path);
////            redirect(request, response, path);
//        } else if ("join".equals(action)) {
////            path = join(request, response);
//            redirect(request, response, path);
//        } else if("idcheck".equals(action)) {
//            String checkid = request.getParameter("checkid");
//            System.out.println("checkid >>>>> " + checkid);
//            int cnt = 1;
//            try {
//                cnt = memberService.idCheck(checkid);
//            } catch (Exception e) {
//                e.printStackTrace();
//                cnt = 1;
//            }
////			CSV
//            response.setContentType("text/plain;charset=utf-8");
//            response.getWriter().print(checkid + "," + cnt);
//        }else {
//            redirect(request, response, path);
//        }
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("utf-8");
//        doGet(request, response);
//    }
//
//    private void forward(HttpServletRequest request, HttpServletResponse response, String path)
//            throws ServletException, IOException {
//        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
//        dispatcher.forward(request, response);
//    }
//
//    private void redirect(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
//        response.sendRedirect(request.getContextPath() + path);
//    }
//
//
//
//}
