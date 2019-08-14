<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${applicationScope.basePath }">
<title>链主管理后台</title>
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="stylesheet" href="./css/font.css">
    <link rel="stylesheet" href="./css/login.css">
	  <link rel="stylesheet" href="./css/xadmin.css">
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="./lib/layui/layui.js" charset="utf-8"></script>
    <!--[if lt IE 9]>
      <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
      <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<%
	String mess = request.getParameter("mess");
	String error = "";
	if(mess!=null&&"error".equals(mess)){
		error = "用户名或密码错误";
	}
%>
<body class="login-bg">
    
    <div class="login layui-anim layui-anim-up">
        <div class="message">链主管理登录</div>
        <div id="darkbannerwrap"></div>
        
        <form method="post" action="managerLogin" class="layui-form" >
            <input name="username" placeholder="用户名"  type="text" lay-verify="required" class="layui-input" >
            <hr class="hr15">
            <input name="pwd" lay-verify="required" placeholder="密码"  type="password" class="layui-input"><span style="color: red"><%=error %></span>
            <hr class="hr15">
            <input value="登录" lay-submit lay-filter="login" style="width:100%;" type="submit">
            <hr class="hr20" >
        </form>
    </div>

    <script>
//         $(function  () {
//             layui.use('form', function(){
//               var form = layui.form;
//               // layer.msg('玩命卖萌中', function(){
//               //   //关闭后的操作
//               //   });
//               //监听提交
//               form.on('submit(login)', function(data){
//                 // alert(888)
//                 layer.msg(JSON.stringify(data.field),function(){
//                     location.href='jsp/index.jsp'
//                 });
//                 return false;
//               });
//             });
//         })
    </script>
    <!-- 底部结束 -->
</body>
</html>