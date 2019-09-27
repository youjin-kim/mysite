package kr.co.itcen.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpSession session) {
		String p = (String) session.getAttribute("p");
		int curPage = 0;
		if (p == null) {
			curPage = 1;
		} else {
			curPage = Integer.parseInt(p);
		}
		
		String kwd = (String) session.getAttribute("kwd");
		if (kwd == null) {
			kwd = "";
		}

		int listCount = boardService.getListCount(kwd);
		Paging paging = new Paging(listCount, curPage);
		List<BoardVo> list = boardService.getList(kwd, curPage);
		
		session.setAttribute("list", list);
		session.setAttribute("paging", paging);

		return "board/list";
	}
}
