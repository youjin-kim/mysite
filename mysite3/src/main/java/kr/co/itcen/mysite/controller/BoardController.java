package kr.co.itcen.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.GuestbookVo;
import kr.co.itcen.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(@RequestParam(required = false, defaultValue = "1") int p,
			HttpSession session, Model model) {
//		String p = (String) session.getAttribute("p");
//
//		int curPage = 0;
//		if (p == null) {
//			curPage = 1;
//		} else {
//			curPage = Integer.parseInt(p);
//		}
		
//		String kwd = (String) session.getAttribute("kwd");
//		if (kwd == null) {
//			kwd = "";
//		}
//
//		int listCount = boardService.getListCount(kwd);
//		Paging paging = new Paging(listCount, p);
//		List<BoardVo> list = boardService.getList(kwd, p);
		
//		UserVo userVo = (UserVo) session.getAttribute("authUser");
//		session.setAttribute("authUser", userVo);
		
		int listCount = boardService.getListCount();
		Paging paging = new Paging(listCount, p);
		List<BoardVo> list = boardService.getList(p, listCount);
		
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);

		return "board/list";
	}
	
	@RequestMapping(value = "/view/{no}/{p}", method = RequestMethod.GET)
	public String view(HttpSession session, @PathVariable("no") Long boardNo, @PathVariable("p") int p) {
		boardService.getUpdateHit(boardNo);
		BoardVo vo = boardService.get(boardNo);
		
		session.setAttribute("vo", vo);
		return "board/view";
	}
	
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String delete(@PathVariable("no") Long boardNo, Model model) {
		model.addAttribute("no", boardNo);
		return "board/delete";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(BoardVo vo) {
		boardService.delete(vo);
		return "redirect:/board/list";
		
	}
	
	@RequestMapping(value="/write/{userNO}/{p}", method=RequestMethod.GET)
	public String insert() {
		return "board/write";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String insert(@ModelAttribute BoardVo vo, @PathVariable("no") Long userNo) {
		boardService.insert(vo);
		
		return "redirect:/board/list";
	}
	
	
	
	
	
}
