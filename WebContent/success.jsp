<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${applicationScope.basePath }">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta name="format-detection" content="email=no">
<meta name="format-detection" content="address=no;">
<meta name="keywords">
<meta name="description">
<link rel="stylesheet" type="text/css"
	href="css/lawyerregister_7257b38.css" />
<script type="text/javascript" src="js/jquery-3.2.1.js"></script>
</head>
<%
	String code = request.getParameter("resultCode");
	String mess = "";
	if ("1001".equals(code)) {
		mess = "恭喜注册成功！请下载app登录.";
	} else if ("4002".equals(code)) {
		mess = "验证码错误或者超过三分钟！";
	} else if ("4003".equals(code)) {
		mess = "手机号被占用！";
	} else if ("4004".equals(code)) {
		mess = "注册失败，请联系管理员！";
	} else {
		mess = "注册失败，请联系管理员！";
	}
%>
<body class="body-box">
	<header class="mui-bar mui-bar-nav">
		<span class="mui-pull-left mui-pull-home">链主好友邀请注册</span>
	</header>
	<div class="mui-content">
		<div class="register">
			<h1 style="font-size: 30px"><%=mess %></h1>
		</div>
		<div align="center">
			<a href="https://dibaqu.com/6beq"><img src="images/ios.png"/></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="https://www.pgyer.com/UpMs"><img src="images/android.png"/></a>
		</div>
	</div>
</body>
</html>