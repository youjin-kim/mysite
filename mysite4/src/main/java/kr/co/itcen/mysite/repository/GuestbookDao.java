package kr.co.itcen.mysite.repository;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.exception.GuestbookDaoException;
import kr.co.itcen.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private DataSource dataSource;
	
	public Boolean insert(GuestbookVo vo) throws GuestbookDaoException {
		int count = sqlSession.insert("guestbook.insert", vo);
		return count == 1;
	}
	
	public List<GuestbookVo> getList() {
		List<GuestbookVo> result = sqlSession.selectList("guestbook.getList");		
		return result;
	}
	
	public void delete(GuestbookVo vo) {
		sqlSession.delete("guestbook.delete", vo);
	}
}
