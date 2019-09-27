package kr.co.itcen.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.itcen.mysite.vo.BoardVo;

public class BoardDao {
	
	private Connection getConnection() throws SQLException {
		Connection connection = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.1.85:3306/webdb?characterEncoding=utf8";
			connection = DriverManager.getConnection(url, "webdb", "webdb");

		} catch (ClassNotFoundException e) {
			System.out.println("Fail to Loading Driver: " + e);
		}

		return connection;
	}

	public Boolean insert(BoardVo vo) {
		Boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

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
			connection = getConnection();
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
			connection = getConnection();
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
			connection = getConnection();

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
			connection = getConnection();
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
			connection = getConnection();

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
			connection = getConnection();
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
			connection = getConnection();
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

	public int totalCount(String kwd) {
		int result = 0;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();

			String sql = "select count(*) from board where contents like ? or title like ?";
			pstmt = connection.prepareStatement(sql);
			kwd = "%"+kwd+"%";
			pstmt.setString(1, kwd);
			pstmt.setString(2, kwd);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println("error: "+e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection !=  null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public List<BoardVo> getList(String kwd, int startInex, int pageSize) {
		List<BoardVo> result = new ArrayList<BoardVo>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();

			String sql = "select b.no, b.title, b.contents, b.hit, b.reg_date, b.g_no, b.o_no, b.depth, u.no, u.name, b.status" + 
					"  from board b" + 
					"  join user u" +
					"  on b.user_no = u.no" +
					"  where b.contents like ? or b.title like ? " +
					"  order by g_no desc, o_no asc" + 
					"  limit ?, ?";
			kwd = "%"+kwd+"%";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, kwd);
			pstmt.setString(2, kwd);
			pstmt.setInt(3, startInex);
			pstmt.setInt(4, pageSize);
			
			rs = pstmt.executeQuery();

			while(rs.next()) {
				BoardVo vo = new BoardVo();
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				Long userNo = rs.getLong(9);
				String userName = rs.getString(10);
				int status = rs.getInt(11);
				
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				vo.setUserName(userName);
				vo.setStatus(status);
				
				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error: "+e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection !=  null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
}
