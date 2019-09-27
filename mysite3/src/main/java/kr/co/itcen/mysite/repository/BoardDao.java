package kr.co.itcen.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
		Boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = dataSource.getConnection();

			String sql = "insert into board values(null, ?, ?, 0, now(), (select ifnull(max(g_no)+1, 1) from board b), 1, 0, 0, ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUserNo());

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
		BoardVo result = new BoardVo();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = dataSource.getConnection();
			String sql = "select b.no, b.title, b.contents, b.hit, date_format(b.reg_date, '%Y-%m-%d %h:%i:%s'), b.g_no, b.o_no, b.depth, u.name, u.no" +
						 " from board b, user u where b.user_no = u.no and b.no = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, boardNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				String userName = rs.getString(9);
				Long userNo = rs.getLong(10);
				
				result.setNo(no);
				result.setTitle(title);
				result.setContents(contents);
				result.setHit(hit);
				result.setRegDate(regDate);
				result.setgNo(gNo);
				result.setoNo(oNo);
				result.setDepth(depth);
				result.setUserName(userName);
				result.setUserNo(userNo);
				
			}
			
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

	public Boolean updateHit(Long no) {
		Boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = dataSource.getConnection();
			String sql = "update board set hit = hit + 1 where no = ?";
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			int count = pstmt.executeUpdate();
			result = (count == 1);
			
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

	public void delete(BoardVo vo) {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = dataSource.getConnection();

			String sql = "update board set title = ?, status = ? where no = ?";
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setString(1, "삭제된 글 입니다.");
			pstmt.setInt(2, vo.getStatus()+1);
			pstmt.setLong(3, vo.getNo());

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
	
	public int totalCount() {
		int totalCount = 0;
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = dataSource.getConnection();
			String sql = "select count(*) as totalcount from board";
			pstmt = connection.prepareStatement(sql);
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
		
		return totalCount;
	}

	public int getListCount(String kwd) {
		int result = sqlSession.selectOne("board.getListCount", kwd);
		return result;
	}
	
	public List<BoardVo> getList(String kwd, int startInex, int pageSize) {
		List<BoardVo> result = sqlSession.selectList("board.getList");
		return result;
	}
}
