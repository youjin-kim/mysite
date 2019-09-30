<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("newline", "\n");
%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.servletContext.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form class="board-form" method="post"
					action="${pageContext.servletContext.contextPath }/board">
					<input type="hidden" name="a" value="delete"> <input
						type="hidden" name="gNo" value="${vo.gNo }"> <input
						type="hidden" name="oNo" value="${vo.oNo }"> <input
						type="hidden" name="depth" value="${vo.depth }"> <input
						type="hidden" name="no" value="${vo.no }"> <input
						type="hidden" name="title" value="${vo.title }">
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글보기</th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td>${vo.getTitle() }</td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<div class="view-content">${fn:replace(vo.getContents(), newline, '<br>') }
								</div>
							</td>
						</tr>
					</table>
					<div class="bottom">
						<a href="${pageContext.servletContext.contextPath }/board/list">취소</a>
						<input type="submit" value="삭제">
					</div>
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>