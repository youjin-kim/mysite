package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String gNo = request.getParameter("gNo");
		String oNo = request.getParameter("oNo");
		String depth = request.getParameter("depth");
		
		request.setAttribute("gNo", gNo);
		request.setAttribute("oNo", oNo);
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setUserNo(authUser.getNo());
		
		if("".equals(gNo)) {
			new BoardDao().insert(vo);
		} else {
			vo.setgNo(Integer.parseInt(gNo));
			vo.setoNo(Integer.parseInt(oNo));
			vo.setDepth(Integer.parseInt(depth));
			new BoardDao().oNoUpdate(vo);
			new BoardDao().insertReply(vo); 
		}
		
		WebUtils.redirect(request, response, request.getContextPath() + "/board");
	}

}
