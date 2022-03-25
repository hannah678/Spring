<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="https://cdn.ckeditor.com/4.17.2/standard/ckeditor.js"></script>
<script>
	$(function(){
		CKEDITOR.replace("content");
		
		$("#dataFrm").submit(function(){
			if($("#subject").val()==""){
				alert("제목을 입력하세요");
				return false;
			}
			if(CKEDITOR.instances.content.getData()==""){
				alert("글내용을 입력하세요");
				return false;
			}
			//첨부파일 선택 개수
			let fileCount=0;
			if($("#filename1").val()!=''){ //파일을 선택했을 경우
				fileCount++;
			}
			if($("#filename2").val()!=''){
				fileCount++;
			}
			if(fileCount<1){
				alert("첨부파일은 반드시 1개 이상이어야 합니다.");
				return false;
			}
		});
	});
</script>
<div class="container">
	<h1>자료실 글등록 폼</h1>
	<!-- 파일업로드 기능이 있는 폼은 반드시 enctype속성을 명시해야한다. -->
	<form id="dataFrm" method="post" action="/myapp/data/writeOk" enctype="multipart/form-data">
		<ul>
			<li>제목</li>
			<li><input type="text" name="subject" id="subject" width="99%"/></li>
			<li>글내용</li>
			<li><textarea name="content" id="content"></textarea></li>
			<li>첨부파일</li>
			<li>
				<input type="file" name="filename" id="filename1"/><br/>
				<input type="file" name="filename" id="filename2"/>
			</li>
			<li><input type="submit" value="자료실글등록"/></li>
		</ul>
	</form>
</div>	