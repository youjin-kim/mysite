package kr.co.itcen.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private DataSource dataSource;
	
	public Boolean insert(BoardVo vo) {
		int count = sqlSession.insert("board.insert", vo);
		return count == 1;
	}

	public BoardVo update(BoardVo vo) {
		BoardVo result = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = dataSource.getConnection();
			String sql = "update board set title = ?, contents = ?, status = ? where no = ?";
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setString(1, "(수정)" + vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getStatus()+2);
			pstmt.setLong(4, vo.getNo());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("error: " + e);
			}
		}

		return result;
	}

	public BoardVo get(Long boardNo) {
		BoardVo vo =sqlSession.selectOne("board.getBoardNo", boardNo);
		return vo;
	}

	public Boolean insertReply(BoardVo vo) {
		Boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = dataSource.getConnection();

			String sql = "insert into board values(null, ?, ?, 0, now(), ?, ?, ?, 0, ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getgNo());
			pstmt.setInt(4, vo.getoNo()+1);
			pstmt.setInt(5, vo.getDepth()+1);
			pstmt.setLong(6, vo.getUserNo());

			int count = pstmt.executeUpdate();
			result = (count == 1);

		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("error: " + e);
			}
		}

		return result;
		
	}

	public void updateHit(Long boardNo) {
		sqlSession.selectOne("board.updateHit", boardNo);
	}

	public void delete(BoardVo vo) {
		sqlSession.delete("board.delete", vo);
	}

	public BoardVo oNoUpdate(BoardVo vo) {
		BoardVo result = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = dataSource.getConnection();
			String sql = "update board set o_no = o_no + 1 where g_no = ? and o_no > ?";
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setInt(1, vo.getgNo());
			pstmt.setInt(2, vo.getoNo());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("error: " + e);
			}
		}
		return result;
	}
	
	public int getListCount(String kwd) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("kwd", kwd);
		
		int result = sqlSession.selectOne("board.getListCount", map);
		return result;
	}
	
	public int getListCount() {
		return sqlSession.selectOne("board.getListCount1");
	}
	
	public List<BoardVo> getList(String kwd, int startInex, int pageSize) {
		List<BoardVo> result = sqlSession.selectList("board.getList");
		return result;
	}

	public List<BoardVo> getList(int startIndex, int pageSize) {
		List<BoardVo> result = sqlSession.selectList("board.getList1");
		return result;
	}
}
