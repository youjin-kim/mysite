package kr.co.itcen.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.exception.BoardException;
import kr.co.itcen.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public Boolean insert(BoardVo vo) throws BoardException {
		int count = sqlSession.insert("board.insert", vo);
		Boolean result = (count == 1);
		return result;
	}
	
	public Boolean insertReply(BoardVo vo) {
		int count = sqlSession.insert("board.insertReply", vo);
		Boolean result = (count == 1);
		return result;
	}

	public Boolean update(BoardVo vo) {
		int count = sqlSession.update("board.update", vo);
		Boolean result = (count == 1);
		return result;
	}

	public BoardVo get(Long boardNo) {
		BoardVo vo =sqlSession.selectOne("board.getBoardNo", boardNo);
		return vo;
	}

	public void updateHit(Long boardNo) {
		sqlSession.selectOne("board.updateHit", boardNo);
	}

	public void delete(BoardVo vo) {
		sqlSession.delete("board.delete", vo);
	}

	public void oNoUpdate(BoardVo vo) {
		sqlSession.selectOne("board.oNoUpdate", vo);
	}
	
	public int getListCount() {
		return sqlSession.selectOne("board.getListCount1");
	}
	
	public List<BoardVo> getList(int startIndex, int pageSize) {
		List<BoardVo> result = sqlSession.selectList("board.getList1");
		return result;
	}
	
	//paging 처리... ㅠㅜ
	public int getListCount(String kwd) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("kwd", kwd);
		
		int result = sqlSession.selectOne("board.getListCount", map);
		return result;
	}
	
	public List<BoardVo> getList(String kwd, int startInex, int pageSize) {
		List<BoardVo> result = sqlSession.selectList("board.getList");
		return result;
	}

}
