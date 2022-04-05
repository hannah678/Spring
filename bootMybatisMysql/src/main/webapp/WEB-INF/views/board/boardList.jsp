<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
	#bList{overflow:auto;}
	#bList>li{float:left; width:10%; border-bottom:solid 1px gray;}
	#bList>li:nth-child(5n+2){width:60%;}
</style>

<div class="container">
	<h1>게시판 글목록</h1>
	<div>
		<a href="/board/boardWrite">글쓰기</a>
	</div>
	<ul id="bList">
		<li>번호</li>
		<li>제목</li>
		<li>글쓴이</li>
		<li>조회수</li>
		<li>등록일</li>
		
		<c:forEach var="vo" items="${lst }">
			<li>${vo.no}</li>
			<li><a href="/board/boardView?no=${vo.no }">${vo.subject}</a></li>
			<li>${vo.userid}</li>
			<li>${vo.hit}</li>
			<li>${vo.writedate}</li>
		</c:forEach>
	</ul>
	
</div>