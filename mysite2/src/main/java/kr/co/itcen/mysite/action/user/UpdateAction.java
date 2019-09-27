package kr.co.itcen.mysite.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.UserDao;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 접근제어(ACL)
		HttpSession session = request.getSession(); // 없으면 null 주라
		if (session == null) {
			WebUtils.redirect(request, response, request.getContextPath());
			return;
		}

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			WebUtils.redirect(request, response, request.getContextPath());
			return;
		}
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		Long no = ((UserVo)session.getAttribute("authUser")).getNo();
		
		UserVo vo = new UserVo();
		vo.setName(name);
		vo.setPassword(password);
		vo.setGender(gender);
		vo.setNo(no);
		
		new UserDao().update(vo);
		session.setAttribute("authUser", vo);
		
		WebUtils.redirect(request, response, request.getContextPath());
	}

}
