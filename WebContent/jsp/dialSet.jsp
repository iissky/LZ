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
		window.location.href = "gameListPage?pageIndex=" + index;
	}
	
	function checkForm() {
		var num = parseFloat($("input[name=odds1]").val())+parseFloat($("input[name=odds2]").val())+parseFloat($("input[name=odds3]").val())+parseFloat($("input[name=odds4]").val())+parseFloat($("input[name=odds5]").val())+parseFloat($("input[name=odds6]").val());
		if(num!=1){
			alert("中奖几率之和必须等于1，请检查");
			return false;
		}
		return true;
	}
</script>
<body>
	<div class="x-nav">
		<span class="layui-breadcrumb"> <a href="javascript:">首页</a> <a
			href="javascript:">转盘抽奖管理</a> <a> <cite>转盘奖区设置</cite></a>
		</span> <a class="layui-btn layui-btn-small"
			style="line-height: 1.6em; margin-top: 3px; float: right"
			onclick="location.reload()" title="刷新"> <i
			class="layui-icon layui-icon-refresh" style="line-height: 30px"></i></a>
	</div>
	<div class="layui-fluid">
            <div class="layui-row layui-col-space15">
                <div class="layui-col-md12">
                    <div class="layui-card">
                        <div class="layui-card-body layui-table-body layui-table-main">
                        <form action="dialSetUpdate" method="post" onsubmit="return checkForm();">
                            <table class="layui-table layui-form">
                                <tbody>
                               <tr>
                               		<td colspan="4" align="center">
                               			<span style="color: red">中奖几率：1表示100%，0.001表示千分之1，依次类推   要求六个奖区的几率加起来正好等于1</span>
                               		</td>
                               </tr>
                               <tr>
									<td>奖区1</td>
									<td>内容:<select name="content1" style="width: 100px"><option value="1" <c:if test="${ld.content1=='1'}">selected="selected"</c:if> >钱币</option><option value="2" <c:if test="${ld.content1=='2'}">selected="selected"</c:if>>权重</option> </select></td>
									<td>数量:<input name="count1" value="${ld.count1 }"/> </td>
									<td>中奖率:<input name="odds1" value="${ld.odds1 }"/></td>
								</tr>
								<tr>
									<td>奖区2</td>
									<td>内容:<select name="content2" style="width: 100px"><option value="1" <c:if test="${ld.content2=='1'}">selected="selected"</c:if> >钱币</option><option value="2" <c:if test="${ld.content2=='2'}">selected="selected"</c:if>>权重</option> </select></td>
									<td>数量:<input name="count2" value="${ld.count2 }"/> </td>
									<td>中奖率:<input name="odds2" value="${ld.odds2 }"/></td>
								</tr>
								<tr>
									<td>奖区3</td>
									<td>内容:<select name="content3" style="width: 100px"><option value="1" <c:if test="${ld.content3=='1'}">selected="selected"</c:if> >钱币</option><option value="2" <c:if test="${ld.content3=='2'}">selected="selected"</c:if>>权重</option> </select></td>
									<td>数量:<input name="count3" value="${ld.count3 }"/> </td>
									<td>中奖率:<input name="odds3" value="${ld.odds3 }"/></td>
								</tr>
								<tr>
									<td>奖区4</td>
									<td>内容:<select name="content4" style="width: 100px"><option value="1" <c:if test="${ld.content4=='1'}">selected="selected"</c:if> >钱币</option><option value="2" <c:if test="${ld.content4=='2'}">selected="selected"</c:if>>权重</option> </select></td>
									<td>数量:<input name="count4" value="${ld.count4 }"/> </td>
									<td>中奖率:<input name="odds4" value="${ld.odds4 }"/></td>
								</tr>
								<tr>
									<td>奖区5</td>
									<td>内容:<select name="content5" style="width: 100px"><option value="1" <c:if test="${ld.content5=='1'}">selected="selected"</c:if> >钱币</option><option value="2" <c:if test="${ld.content5=='2'}">selected="selected"</c:if>>权重</option> </select></td>
									<td>数量:<input name="count5" value="${ld.count5 }"/> </td>
									<td>中奖率:<input name="odds5" value="${ld.odds5 }"/></td>
								</tr>
								<tr>
									<td>奖区6</td>
									<td>内容:<select name="content6" style="width: 100px"><option value="1" <c:if test="${ld.content6=='1'}">selected="selected"</c:if> >钱币</option><option value="2" <c:if test="${ld.content6=='2'}">selected="selected"</c:if>>权重</option> </select></td>
									<td>数量:<input name="count6" value="${ld.count6 }"/> </td>
									<td>中奖率:<input name="odds6" value="${ld.odds6 }"/></td>
								</tr>
								
								<tr>
									<td colspan="4" style="height: 50px" align="center">
										<button class="layui-btn" type="submit" lay-submit="" lay-filter="submit">修改设置</button>
									</td>
								</tr>
                                </tbody>
                            </table>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div> 
</body>
${mess }
<script>
	layui.use([ 'laydate', 'form' ], function() {
		var laydate = layui.laydate;
		var form = layui.form;

		// 监听全选
		form.on('checkbox(checkall)', function(data) {

			if (data.elem.checked) {
				$('tbody input').prop('checked', true);
			} else {
				$('tbody input').prop('checked', false);
			}
			form.render('checkbox');
		});

		//执行一个laydate实例
		laydate.render({
			elem : '#start' //指定元素
		});

		//执行一个laydate实例
		laydate.render({
			elem : '#end' //指定元素
		});

	});

	/*用户-停用*/
	function member_stop(obj, id) {
		layer.confirm('确认要停用吗？', function(index) {

			if ($(obj).attr('title') == '启用') {

				//发异步把用户状态进行更改
				$(obj).attr('title', '停用')
				$(obj).find('i').html('&#xe62f;');

				$(obj).parents("tr").find(".td-status").find('span').addClass(
						'layui-btn-disabled').html('已停用');
				layer.msg('已停用!', {
					icon : 5,
					time : 1000
				});

			} else {
				$(obj).attr('title', '启用')
				$(obj).find('i').html('&#xe601;');

				$(obj).parents("tr").find(".td-status").find('span')
						.removeClass('layui-btn-disabled').html('已启用');
				layer.msg('已启用!', {
					icon : 5,
					time : 1000
				});
			}

		});
	}

	/*用户-删除*/
	function member_del(obj, id) {
		layer.confirm('确认要删除吗？', function(index) {
			//发异步删除数据
			$(obj).parents("tr").remove();
			layer.msg('已删除!', {
				icon : 1,
				time : 1000
			});
		});
	}

	function delAll(argument) {
		var ids = [];

		// 获取选中的id 
		$('tbody input').each(function(index, el) {
			if ($(this).prop('checked')) {
				ids.push($(this).val())
			}
		});

		layer.confirm('确认要删除吗？' + ids.toString(), function(index) {
			//捉到所有被选中的，发异步进行删除
			layer.msg('删除成功', {
				icon : 1
			});
			$(".layui-form-checked").not('.header').parents('tr').remove();
		});
	}
</script>
</html>