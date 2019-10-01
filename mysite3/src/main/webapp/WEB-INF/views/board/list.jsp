<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
				<form id="search_form"
					action="${pageContext.servletContext.contextPath }/board/list"
					method="post">
					<input type="text" id="kwd" name="kwd" value=""> <input
						type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="count" value="${fn:length(list) }" />
					<c:forEach items="${list }" var="vo" varStatus="status">
						<tr>
							<td>[${paging.listCnt-((paging.curPage-1)*paging.pageSize)-status.index }]</td>
							<td style="text-align: left;"><c:if test="${vo.depth > 0 }">
									<c:forEach begin='1' end='${vo.depth }'>&nbsp;&nbsp;
								</c:forEach>
									<img
										src="${pageContext.servletContext.contextPath }/assets/images/reply.png" />
								</c:if> <c:if test="${vo.status%2 != 0 }">${vo.title }</c:if> <c:if
									test="${vo.status%2 == 0 }">
									<a
										href="${pageContext.servletContext.contextPath }/board/view/${vo.no }/${paging.curPage }">${vo.title }</a>
								</c:if>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<c:choose>
								<c:when
									test="${!empty authUser and vo.userNo eq authUser.getNo() and vo.status ne 1 }">
									<td><a
										href="${pageContext.servletContext.contextPath }/board/delete/${vo.no }/${paging.curPage }"
										class="del">삭제</a></td>
								</c:when>
								<c:otherwise>
									<td><a></a></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</table>

				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${paging.curRange ne 1}">
							<li><a
								href="${pageContext.servletContext.contextPath }/board/${paging.prevRange }">[◀]</a></li>
						</c:if>

						<c:forEach var="pageNum" begin="${paging.startPage }"
							end="${paging.endPage }">
							<c:choose>
								<c:when test="${pageNum eq  paging.curPage}">
									<li class="selected">${pageNum }</li>
								</c:when>
								<c:otherwise>
									<li><a
										href="${pageContext.servletContext.contextPath }/board/${pageNum }">${pageNum }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>

						<c:if
							test="${paging.curRange ne paging.rangeCnt and paging.rangeCnt > 1}">
							<li><a
								href="${pageContext.servletContext.contextPath }/board/${paging.nextRange }">[▶]</a></li>
						</c:if>
					</ul>
				</div>

				<c:choose>
					<c:when test="${!empty authUser }">
						<div class="bottom">
							<a
								href="${pageContext.servletContext.contextPath }/board/write/${authUser.no }/${paging.curPage }"
								id="new-book">글쓰기</a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="bottom">
							<a
								href="${pageContext.servletContext.contextPath }/user/login"
								id="new-book">로그인</a>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>