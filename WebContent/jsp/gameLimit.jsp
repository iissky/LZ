<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<base href="${applicationScope.basePath }">
<title>链主后台管理</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
<link rel="stylesheet" href="./css/font.css">
<link rel="stylesheet" href="./css/xadmin.css">
<script src="./lib/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="./js/xadmin.js"></script>
<script type="text/javascript" src="js/jquery-3.2.1.js"></script>
<!--[if lt IE 9]>
          <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
          <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
</head>
<script type="text/javascript">
	function toPage(index) {
		window.location.href = "game/gameListPage?pageIndex=" + index;
	}
	
	function checkForm() {
		if(isNaN($("#limit").val())||$("#limit").val()==""){
			$("#mess").html("上限不能为空且必须是数字");
			return false;
		}
		return true;
	}
	function startGame() {
		window.location.href = "game/startGame";
	}
</script>
<body>
	<div class="x-nav">
		<span class="layui-breadcrumb"> <a href="javascript:">首页</a> <a
			href="javascript:">游戏管理</a> <a> <cite>游戏轮次上限设置</cite></a>
		</span> <a class="layui-btn layui-btn-small"
			style="line-height: 1.6em; margin-top: 3px; float: right"
			onclick="location.reload()" title="刷新"> <i
			class="layui-icon layui-icon-refresh" style="line-height: 30px"></i></a>
	</div>
	<div style="margin: 0px auto; width: 500px; padding-top: 100px;">
		<h3><span style="red">游戏为18分钟一轮，设置完游戏产币上限后，在下一轮自动生效</span></h3>
		<h1>
			当前游戏产币上限：<span style="color: red">${limit }</span>
		</h1>
		<br>
		<br>
		<form action="game/gameLimitSet" method="post"
			class="layui-form layui-col-space5" onsubmit="return checkForm();">
			<div class="layui-inline layui-show-xs-block">
				<input type="text" id="limit" name="limit" placeholder="请输入上限"
					autocomplete="off" class="layui-input">
			</div>
			<div class="layui-inline layui-show-xs-block">
				<button class="layui-btn" type="submit" lay-submit=""
					lay-filter="submit">设置</button>
				&nbsp;&nbsp;<button class="layui-btn" type="button" lay-submit="" onclick="startGame()"
					lay-filter="button">手动启动当前轮次</button>
			</div>
			<div id="mess" style="color: red" class="layui-inline layui-show-xs-block">
				
			</div>
		</form>
		
	</div>
	<!--         <div class="layui-fluid"> -->
	<!--             <div class="layui-row"> -->

	<!--             </div> -->
	<!--         </div> -->
</body>
${mess }
</html>