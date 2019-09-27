package kr.co.itcen.mysite.vo;

public class BoardVo {
	private Long no;
	private Long userNo;
	private String userName;
	private String title;
	private String contents;
	private int hit;
	private String regDate;
	private int depth;
	private int gNo;
	private int oNo;
	private int status;
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public Long getUserNo() {
		return userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getgNo() {
		return gNo;
	}
	public void setgNo(int gNo) {
		this.gNo = gNo;
	}
	public int getoNo() {
		return oNo;
	}
	public void setoNo(int oNo) {
		this.oNo = oNo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", userNo=" + userNo + ", userName=" + userName + ", title=" + title
				+ ", contents=" + contents + ", hit=" + hit + ", regDate=" + regDate + ", depth=" + depth + ", gNo="
				+ gNo + ", oNo=" + oNo + ", status=" + status + "]";
	}
	

}
