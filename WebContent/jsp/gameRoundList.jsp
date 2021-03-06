<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<base href="${applicationScope.basePath }">
<title>链主后台管理</title>
        <meta name="renderer" content="webkit">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
        <link rel="stylesheet" href="./css/font.css">
        <link rel="stylesheet" href="./css/xadmin.css">
        <script src="./lib/layui/layui.js" charset="utf-8"></script>
        <script type="text/javascript" src="./js/xadmin.js"></script>
        <!--[if lt IE 9]>
          <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
          <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <script type="text/javascript">
    	function toPage(index) {
			window.location.href = "game/gameListPage?pageIndex="+index;
		}
    </script>
    <body>
        <div class="x-nav">
          <span class="layui-breadcrumb">
            <a href="javascript:">首页</a>
            <a href="javascript:">游戏管理</a>
            <a>
              <cite>游戏轮次查看</cite></a>
          </span>
          <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" onclick="location.reload()" title="刷新">
            <i class="layui-icon layui-icon-refresh" style="line-height:30px"></i></a>
        </div>
        <div class="layui-fluid">
            <div class="layui-row layui-col-space15">
                <div class="layui-col-md12">
                    <div class="layui-card">
                    
                    <!-- 
                        <div class="layui-card-body ">
                            <form action="" method="post" class="layui-form layui-col-space5">
                                <div class="layui-inline layui-show-xs-block">
                                    <input class="layui-input"  autocomplete="off" placeholder="开始日" name="start" id="start">
                                </div>
                                <div class="layui-inline layui-show-xs-block">
                                    <input class="layui-input"  autocomplete="off" placeholder="截止日" name="end" id="end">
                                </div>
                                <div class="layui-inline layui-show-xs-block">
                                    <input type="text" name="username"  placeholder="请输入用户名" autocomplete="off" class="layui-input">
                                </div>
                                <div class="layui-inline layui-show-xs-block">
                                    <button class="layui-btn" type="submit"  lay-submit="" lay-filter="sreach"><i class="layui-icon">&#xe615;</i></button>
                                </div>
                            </form>
                        </div>
                         -->
                        
                        <div class="layui-card-header">
                            <button class="layui-btn" onclick="xadmin.open('添加用户','./member-add.html',600,400)"><i class="layui-icon"></i>添加</button>
                        </div>
                        <div class="layui-card-body layui-table-body layui-table-main">
                            <table class="layui-table layui-form">
                                <thead>
                                  <tr>
                                    
                                    <th>ID</th>
                                    <th>产生总金额</th>
                                    <th>全网钱币数</th>
                                    <th>全网权重数</th>
                                    <th>产生时间</th>
                                    <th>状态</th>
<!--                                     <th>操作</th> -->
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${pb.list }" var="game">
                                  <tr>
                                    <td>${game.gameid }</td>
                                    <td>${game.ptotal }</td>
                                    <td>${game.baltotal }</td>
                                    <td>${game.weitotal }</td>
                                    <td><fmt:formatDate value="${game.createtime }" pattern="yyyy-mm-dd hh:mm:ss"/> </td>
                                    <td>
                                    	<c:if test="${game.status=='1'}">进行中</c:if>
                                    	<c:if test="${game.status=='2'}">已结束</c:if>
                                    </td>
                                    <!-- 
                                    <td class="td-manage">
                                      <a title="编辑"  onclick="xadmin.open('编辑','member-edit.html',600,400)" href="javascript:;">
                                        <i class="layui-icon">&#xe642;</i>
                                      </a>
                                      <a title="删除" onclick="member_del(this,'要删除的id')" href="javascript:;">
                                        <i class="layui-icon">&#xe640;</i>
                                      </a>
                                    </td>
                                     -->
                                  </tr>
                                  </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="layui-card-body ">
                            <div class="page">
                                <div>
                                <c:if test="${pb.pageIndex>1 }">
                                	<a class="prev" href="game/gameListPage?pageIndex=${pb.pageIndex-1 }">上一页</a>
                                </c:if>
                                <c:if test="${pb.pageIndex==1 }">
                                	<a class="prev" href="javascript:" >上一页</a>
                                </c:if>
                                  	
                                	当前页:${pb.pageIndex }
                                  	<select onchange="toPage(this.value)">
                                  		<c:forEach begin="${1}" end="${pb.totalPage}" var="i">
                                  			<option value="${i }" <c:if test="${pb.pageIndex==i }"> selected="selected"</c:if>>${i }</option>
                                  		</c:forEach>
                                  	</select>
                                  	共:${pb.totalPage }页
                                <c:if test="${pb.pageIndex==pb.totalPage}">
                                	<a class="next" href="javascript:">下一页</a>
                                </c:if>
                                  <c:if test="${pb.pageIndex<pb.totalPage}">
                                	<a class="next" href="game/gameListPage?pageIndex=${pb.pageIndex+1 }">下一页</a>
                                </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div> 
    </body>
    <script>
      layui.use(['laydate','form'], function(){
        var laydate = layui.laydate;
        var  form = layui.form;


        // 监听全选
        form.on('checkbox(checkall)', function(data){

          if(data.elem.checked){
            $('tbody input').prop('checked',true);
          }else{
            $('tbody input').prop('checked',false);
          }
          form.render('checkbox');
        }); 
        
        //执行一个laydate实例
        laydate.render({
          elem: '#start' //指定元素
        });

        //执行一个laydate实例
        laydate.render({
          elem: '#end' //指定元素
        });


      });

       /*用户-停用*/
      function member_stop(obj,id){
          layer.confirm('确认要停用吗？',function(index){

              if($(obj).attr('title')=='启用'){

                //发异步把用户状态进行更改
                $(obj).attr('title','停用')
                $(obj).find('i').html('&#xe62f;');

                $(obj).parents("tr").find(".td-status").find('span').addClass('layui-btn-disabled').html('已停用');
                layer.msg('已停用!',{icon: 5,time:1000});

              }else{
                $(obj).attr('title','启用')
                $(obj).find('i').html('&#xe601;');

                $(obj).parents("tr").find(".td-status").find('span').removeClass('layui-btn-disabled').html('已启用');
                layer.msg('已启用!',{icon: 5,time:1000});
              }
              
          });
      }

      /*用户-删除*/
      function member_del(obj,id){
          layer.confirm('确认要删除吗？',function(index){
              //发异步删除数据
              $(obj).parents("tr").remove();
              layer.msg('已删除!',{icon:1,time:1000});
          });
      }



      function delAll (argument) {
        var ids = [];

        // 获取选中的id 
        $('tbody input').each(function(index, el) {
            if($(this).prop('checked')){
               ids.push($(this).val())
            }
        });
  
        layer.confirm('确认要删除吗？'+ids.toString(),function(index){
            //捉到所有被选中的，发异步进行删除
            layer.msg('删除成功', {icon: 1});
            $(".layui-form-checked").not('.header').parents('tr').remove();
        });
      }
    </script>
</html>