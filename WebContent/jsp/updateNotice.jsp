<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<base href="${applicationScope.basePath }">
<meta charset="UTF-8">
        <title>链主后台管理</title>
        <meta name="renderer" content="webkit">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
        <link rel="stylesheet" href="./css/font.css">
        <link rel="stylesheet" href="./css/xadmin.css">
        <script type="text/javascript" src="./lib/layui/layui.js" charset="utf-8"></script>
        <script type="text/javascript" src="./js/xadmin.js"></script>
        <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
        <!--[if lt IE 9]>
            <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
            <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <body>
        <div class="layui-fluid">
            <div class="layui-row">
                <form class="layui-form" action="notice/updateNotice" method="post">
                	<input type="hidden" id="noticeid" value="${n.noticeid }"/>
                    <div class="layui-form-item">
                        <label for="L_email" class="layui-form-label">
                            <span class="x-red">*</span>标题</label>
                        <div class="layui-input-inline">
                            <input type="text" id="title" value="${n.title }" required="" lay-verify="totallimit" autocomplete="off" class="layui-input"></div>
                        <div class="layui-form-mid layui-word-aux">
                            <span class="x-red">*</span></div></div>
                    <div class="layui-form-item">
                        <label for="L_username" class="layui-form-label">
                            <span class="x-red">*</span>公告内容</label>
                        <div class="layui-input-inline">
                        	<textarea rows="10" cols="60" id="content">${n.content}</textarea>
                            </div>
                    	<div class="layui-form-mid layui-word-aux">
                            </div>
                    </div>
                    <div class="layui-form-item">
                        <label for="L_pass" class="layui-form-label">
                            <span class="x-red">*</span>状态</label>
                        <div class="layui-input-inline">
                            <select name="status" id="status">
								<option value="1" <c:if test="${n.status=='1' }">selected="selected"</c:if>>有效</option>
								<option value="2" <c:if test="${n.status=='2' }">selected="selected"</c:if>>无效</option>                            
                            </select></div>
                        <div class="layui-form-mid layui-word-aux"></div></div>
                    <div class="layui-form-item">
                        <label for="L_repass" class="layui-form-label"></label>
                        <button class="layui-btn" lay-filter="add" lay-submit="" type="submit">修改</button></div>
                </form>
            </div>
        </div>
    </body>
<script>layui.use(['form', 'layer','jquery'],
            function() {
                $ = layui.jquery;
                var form = layui.form,
                layer = layui.layer;

                //自定义验证规则
//                 form.verify({
//                 	totallimit: function(value) {
//                         if (isNaN(value)||value=='') {
//                             return '必须是数字';
//                         }
//                     },
//                     ratio: function(value) {
//                         if (isNaN(value)||value=='') {
//                             return '必须是数字';
//                         }
//                     },
//                     perlimit:function(value) {
//                         if (isNaN(value)||value=='') {
//                             return '必须是数字';
//                         }
//                     }
//                 });

                //监听提交
                form.on('submit(add)',
                function(data) {
                	var flag;
                    console.log(data);
                    $.post("notice/updateNotice", {noticeid:$("#noticeid").val() ,title:$("#title").val(),content:$("#content").val(),status:$("#status").val()}, function(data) {
                    	flag = data;
                    });
                    layer.alert("修改成功", {
                           icon: 6
                    },
                    //发异步，把数据提交给php
                    function() {
                        //关闭当前frame
                        xadmin.close();

                        // 可以对父窗口进行刷新 
                        xadmin.father_reload();
                    });
                    return false;
                });

            });</script>
</html>