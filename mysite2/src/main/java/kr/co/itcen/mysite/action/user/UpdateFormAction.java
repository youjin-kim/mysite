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

public class UpdateFormAction implements Action {

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
		
		Long no = authUser.getNo();
		authUser = new UserDao().get(no);
		
		session.setAttribute("authUser", authUser);
		WebUtils.forword(request, response, "/WEB-INF/views/user/updateform.jsp");

	}

}
