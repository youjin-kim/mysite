package kr.co.itcen.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.co.itcen.mysite.vo.GuestbookVo;


public class GuestbookDao {
	
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
	
	public Boolean insert(GuestbookVo vo) {
		Boolean result = false;
		Connection connection = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "insert into guestbook values(null, ?, ?, ?, now())";
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getText());
			

			int count = pstmt.executeUpdate();
			result = (count == 1);

			stmt = connection.createStatement();
			rs = stmt.executeQuery("select last_insert_id()");
			if (rs.next()) {
				Long no = rs.getLong(1);
				vo.setNo(no);
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
	
	public List<GuestbookVo> getList() {
		List<GuestbookVo> result = new ArrayList<GuestbookVo>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "select no, name, text, date_format(reg_date, '%Y-%m-%d %h:%i:%s') from guestbook order by reg_date desc";
			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String text = rs.getString(3);
				String regDate = rs.getString(4);
				
				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setText(text);
				vo.setRegDate(regDate);
				
				result.add(vo);
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
	
	public void delete(GuestbookVo vo) {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = getConnection();

			String sql = "delete from guestbook where no = ? and password = ?";
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getPassword());

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

}
