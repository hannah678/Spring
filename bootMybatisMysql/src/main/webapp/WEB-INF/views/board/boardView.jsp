<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
	function del(){
		if(confirm('삭제할까요?')){
			location.href='/board/boardDel?no=${vo.no}';
		}
	}
</script>
<div class="container">
	<h1>글내용보기</h1>
	번호: ${vo.no }<br/>
	글쓴이: ${vo.userid }<br/>
	등록일: ${vo.writedate }, 조회수: ${vo.hit }<br/>
	제목: ${vo.subject }<br/>
	글내용<br/>
	${vo.content }
	
	<br/><br/>
	
	<a href="/board/boardEdit?no=${vo.no }">수정</a>
	<a href="javascript:del()">삭제</a>
</div>