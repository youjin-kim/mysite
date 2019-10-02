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
	
	public int getListCount() {
		return boardDao.getListCount();
	}

	public List<BoardVo> getList(String kwd, int listCount, int p) {
		Paging paging = new Paging(listCount, p);
		return boardDao.getList(kwd, paging.getStartIndex(), paging.getPageSize());
	}

	public List<BoardVo> getList(int p, int listCount) {
		Paging paging = new Paging(listCount, p);
		return boardDao.getList(paging.getStartIndex(), paging.getPageSize());
	}
	
	public BoardVo get(Long boardNo) {
		return boardDao.get(boardNo);
	}
	
	public void getUpdateHit(Long boardNo) {
		boardDao.updateHit(boardNo);
	}

	public void delete(BoardVo vo) {
		boardDao.delete(vo);
	}

	public void insert(BoardVo vo) {
		boardDao.insert(vo);
	}

	public void modify(BoardVo vo) {
		boardDao.update(vo);
	}

	public void oNoUpdate(BoardVo vo) {
		boardDao.oNoUpdate(vo);
	}

	public void insertReply(BoardVo vo) {
		boardDao.insertReply(vo);
	}

}
