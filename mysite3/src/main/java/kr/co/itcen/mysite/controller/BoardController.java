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
import kr.co.itcen.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(@RequestParam(value = "p", required = false, defaultValue = "1") int p,
			@RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
			Model model) {
		if(kwd != null) {
			int kwdListCount = boardService.getListCount(kwd);
			Paging paging = new Paging(kwdListCount, p);
			List<BoardVo> list = boardService.getList(kwd, kwdListCount, p);
			model.addAttribute("kwd", kwd);
			model.addAttribute("list", list);
			model.addAttribute("paging", paging);
			
			return "board/list";
		}
		
		kwd = null;
		int kwdListCount = boardService.getListCount(kwd);
		Paging paging = new Paging(kwdListCount, p);
		List<BoardVo> list = boardService.getList(kwd, kwdListCount, p);
		model.addAttribute("kwd", kwd);
		model.addAttribute("list", list);
		model.addAttribute("paging", paging); 

		return "board/list";
	}
	
	@RequestMapping(value = "/view/{no}/{p}", method = RequestMethod.GET)
	public String view(HttpSession session, @PathVariable("no") Long boardNo, @PathVariable("p") int p) {
		boardService.getUpdateHit(boardNo);
		BoardVo vo = boardService.get(boardNo);
		
		session.setAttribute("p", p);
		session.setAttribute("vo", vo);
		return "board/view";
	}
	
	@RequestMapping(value="/delete/{no}/{p}", method=RequestMethod.GET)
	public String delete(HttpSession session, @PathVariable("no") Long boardNo, @PathVariable("p") int p) {
		BoardVo vo = boardService.get(boardNo);
		session.setAttribute("p", p);
		session.setAttribute("vo", vo);
		return "board/delete";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(BoardVo vo) {
		boardService.delete(vo);
		return "redirect:/board/list";
		
	}
	
	@RequestMapping(value= "/write/{userNO}/{p}", method=RequestMethod.GET)
	public String insert() {
		return "board/write";
	}
	
	@RequestMapping(value= "/write/{p}/{gNo}/{oNo}/{depth}", method=RequestMethod.GET)
	public String insert(HttpSession session, @PathVariable("gNo") int gNo,
			@PathVariable("oNo") int oNo, @PathVariable("depth") int depth,
			@PathVariable("p") int p) {
		
		session.setAttribute("gNo", gNo);
		session.setAttribute("oNo", oNo);
		session.setAttribute("depth", depth);
		session.setAttribute("p", p);

		return "board/write";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String insert(HttpSession session, @ModelAttribute BoardVo vo) {
		UserVo userVo = (UserVo) session.getAttribute("authUser");
		vo.setUserNo(userVo.getNo());
		
		if(vo.getgNo() != 0) {
			vo.setgNo((int) session.getAttribute("gNo"));
			vo.setoNo((int) session.getAttribute("oNo") + 1);
			boardService.oNoUpdate(vo);
			vo.setDepth((int) session.getAttribute("depth") + 1);
			
			boardService.insertReply(vo);
			return "redirect:/board/list";
		}
		
		boardService.insert(vo);
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="/modify/{p}/{no}", method=RequestMethod.GET)
	public String modify(HttpSession session, @PathVariable("no") Long boardNo,@PathVariable("p") int p) {
		BoardVo vo = boardService.get(boardNo);
		session.setAttribute("p", p);
		session.setAttribute("vo", vo);
		return "board/modify";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(HttpSession session, @ModelAttribute BoardVo vo) {
		boardService.modify(vo);
		return "redirect:/board/list";
	}
	
}
