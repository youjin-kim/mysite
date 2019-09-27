package kr.co.itcen.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.controller.Paging;
import kr.co.itcen.mysite.repository.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	public int getListCount(String kwd) {
		return boardDao.getListCount(kwd);
	}

	public List<BoardVo> getList(String kwd, int curPage) {
		Paging paging = new Paging(getListCount(kwd), curPage);
		return boardDao.getList(kwd, paging.getStartIndex(), paging.getPageSize());
	}

	
}
