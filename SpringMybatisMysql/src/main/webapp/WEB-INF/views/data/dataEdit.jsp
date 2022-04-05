<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="https://cdn.ckeditor.com/4.17.2/standard/ckeditor.js"></script>
<script>
	$(function(){
		CKEDITOR.replace("content");
		
		//파일개수 처리를 위한 변수 생성
		var fileCount = ${fileCount};
		
		//새로추가되는 첨부파일
		$("#dataFrm b").click(function(){ //b태그를 클릭하면 function
			$(this).parent().css("display", "none"); //파일명 숨기기
			$(this).parent().next().attr("name","delFile"); //삭제파일 정보 name을 delFile로 변경
			$(this).parent().next().next().attr("type", "file"); //새로운 파일을 선택할 수 있도록
			//파일의 개수가 줄어들어야한다.
			fileCount--;
		});
		

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
	<h1><span style="font-family:'Nanum Gothic Coding' !important;">자료실 글 수정</span></h1>
	<!-- 파일업로드 기능이 있는 폼은 반드시 enctype속성을 명시해야한다. -->
	<form id="dataFrm" method="post" action="/myapp/data/editOk" enctype="multipart/form-data">
		<input type="hidden" name="no" value="${vo.no}"/>	
		<ul>
			<li><span style="font-family:'Nanum Gothic Coding' !important;">제목</span></li>
			<li><input type="text" name="subject" id="subject" value="${vo.subject}"/></li>
			<li><span style="font-family:'Nanum Gothic Coding' !important;">글내용</span></li>
			<li><textarea name="content" id="content">${vo.content }</textarea></li>
			<li><span style="font-family:'Nanum Gothic Coding' !important;">첨부파일</span></li>
			<li>
				<!-- 첫번째 첨부파일 -->
				<div>${vo.filename1} &nbsp;&nbsp;<b>X</b></div>
				<!-- X를 누르면 삭제파일명이 있는 input의 name속성값을 delFile -->
				<input type="hidden" name="" value="${vo.filename1}"/>
				<!-- X를 누르면 파일이 삭제되고 새로운 파일을 선택가능하도록 input을 만들어준다 -->
			
				<input type="hidden" name="filename" id="filename1"/>
			</li>
			<li>
				<!-- 두번째 첨부파일이 있을 때 -->
				<c:if test="${vo.filename2 != null && vo.filename2 != ''}">
					<div>${vo.filename2} &nbsp;&nbsp;&nbsp; <b>X</b></div>
					<input type="hidden" name="" value="${vo.filename2}"/>
					<input type="hidden" name="filename" id="filename2"/>
				</c:if>
				<!-- 두번째 첨부파일 없을 때 -->
                 <c:if test="${vo.filename2 == null || vo.filename2 =='' }">
                    <input type="file" name="filename" id="filename2"/>
                 </c:if>
			</li>
			<li><span style="font-family:'Nanum Gothic Coding' !important;"><input type="submit" value="자료실글수정"/></span></li>
		</ul>
	</form>
</div>	