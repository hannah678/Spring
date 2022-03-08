<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>t1매핑</h1>

<p>
	num: ${num}<br/>
	name:${name}
</p>
<h1>t3매핑(vo)</h1>
<p>
    num -->${vo.num }<br/> <!-- 캡슐화된 vo이기 때문에 getter 통해서 받아옴 -->
    name-->${vo.name }<br/>
</p>

</body>
</html>