package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ReplyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");
		String p = request.getParameter("p");
		int gNo = Integer.parseInt(request.getParameter("gNo"));
		int oNo = Integer.parseInt(request.getParameter("oNo"));
		int depth = Integer.parseInt(request.getParameter("depth"));
		
		if(no != null) {
			BoardVo vo = new BoardDao().get(Long.parseLong(no));
			request.setAttribute("vo", vo);
		}
		
		request.setAttribute("p", p);
		request.setAttribute("gNo", gNo);
		request.setAttribute("oNo", oNo);
		request.setAttribute("depth", depth);
		
		WebUtils.forword(request, response, "/WEB-INF/views/board/replyform.jsp");

	}

}
