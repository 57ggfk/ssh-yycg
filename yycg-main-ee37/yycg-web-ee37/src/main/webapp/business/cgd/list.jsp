<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<html>
  <head>
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/common/common_css.jsp"%>
<%@ include file="/common/common_js.jsp"%>
<script type="text/javascript">
//格式化时间
function formatDate(value, row, index) {
	if (value) {
		var date = new Date(value);
		return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+
				date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
	}
	return "暂无";
}
//格式化单位名称显示
function formatMc(value, row, index) {
	if (value) {
		return value.mc;
	}
	return "";
}

//格式化显示采购单状态
function formatZt(value, row, index) {
	if (value) {
		return value.info;
	}
}
//定义列
var colArr = [[
            {field:"mc",title:"采购单名称",width:450},
            {field:"lxr",title:"联系人",width:120},
            {field:"lxdh",title:"联系电话",width:200},
            {field:"cjsj",title:"创建时间",width:260,formatter:formatDate},
            {field:"tjsj",title:"提交时间",width:260,formatter:formatDate},
            {field:"xgsj",title:"修改时间",width:100,formatter:formatDate},
            //{field:"dwWss",title:"卫生室名称",formatter:formatMc},
            {field:"dwGys",title:"供应商名称",width:260,formatter:formatMc},
            {field:"sysDictInfoByzt",title:"采购单状态",width:200,formatter:formatZt},
            {field:"bz",title:"备注",width:100},
            {field:"edit",title:"修改",width:80,formatter:function(value,row,index){
            	return "<a href='javascript:void(0)' onclick='yycgdedit("+row.id+")'>修改</a>"; //row.id是整型
            }},
            {field:"delete",title:"删除",width:80,formatter:function(value,row,index){
            	return "<a href='javascript:void(0)' onclick='yycgddel("+row.id+")'>删除</a>"; //row.id是整型
            }}
               ]]; 

$(function(){
	
	$("#yycgdlist").datagrid({
		url:"${baseurl }/cgd/list_result.action",
		title:"医药采购单列表",
		columns:colArr,
		fitColumns:true,
		//fixed:true,
		pagination:true,
		rownumbers:true,
		idFiled:"id",
		striped:true,
	});
	
});

//查询
function yycgdquery() {
	//jquerySubByFId('yycgdqueryForm',yycgdquery_callback);
	
	//表单参数序列化
	var params = $("#yycgdqueryForm").serializeJson();
	//刷新datagride
	$("#yycgdlist").datagrid('reload',params);
	
}

//修改
function yycgdedit(id) {
	//以选项卡方式打开编辑页面
	var title = id+"采购单编辑";
	//调用父页面函数
	parent.opentabwindow(title,"${baseurl}/cgd/edit.action?yycgdCustom.id="+id);
}

//删除
function yycgddel(id) {
	//提示信息
	_confirm("您确定要删除这个订单吗？", function() {
		//跳转页面并且刷新
		//jquerySubByFId('yycgddelForm', deleteyycgd_callback);
		//window.location = "${baseurl}/cgd/delete.action?yycgdCustom.id="+id;
		//刷新父页面datagrid
		//message_alert(data, yycgdquery)
		/* message_alert(data, function(){
			//成功后刷新页面
			location.reload();
		}) */
		$.post(
				//url地址
				"${baseurl}/cgd/delete.action", 
				//data:待发送请求   key/value
				{ "yycgdCustom.id": id },
				//发送成功的时候的回调函数
				   function(data){
				   //  _alert(data); // John
				   message_alert(data, yycgdquery)
				   }, 
				//type返回内容的格式   
				"json");
	})
}

</script>
<title>维护采购单</title>
</head>
<body>
<!-- 查询条件 -->
<form id="yycgdqueryForm" name="yycgdqueryForm" method="post" action="${baseurl }/cgd/list_result.action">
			<TABLE class="table_search">
				<TBODY>
					<TR>
						<TD class="left">采购单号</td>
						<td><INPUT type="text" name="yycgdQueryCustom.id" /></TD>
						<TD class="left"></TD>
						<td></TD>

						<TD class="left">采购单状态：</TD>
						<td>
							<select name="yycgdQueryCustom.sysDictInfoByzt.id">
								<option value="">请选择</option>
								<!--groupList就是模型对象中的属性 -->
								<c:forEach items="${yycgdZtList}" var="dictinfo">
								  <option value="${dictinfo.id}">${dictinfo.info}</option>
								</c:forEach>
								
							</select>
						</TD>
						<td >
						<a id="btn" href="javascript:void(0)" onclick="yycgdquery()"
							class="easyui-linkbutton" iconCls='icon-search'>查询</a>
							<input type="submit" style="display:none" onclick="yycgdquery();return false" />
							</td>
					</TR>


				</TBODY>
			</TABLE>
			
		</form>
<!-- datagrid -->
<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
			<TBODY>
				<TR>
					<TD>
						<table id="yycgdlist"></table>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
</body>
<!-- 删除页面 -->
<form id="yycgddelForm" action="${baseurl}/cgd/yycgdmxdel_submit.action" method="post" style="display:none">
	<!-- 采购单id -->
	<input type="text" name="yycgdCustom.id" value="${yycgdCustom.id }"/>
	
	<%-- 明细id 
		<input type="text" name="yycgdmxIds" value="mx001"/>
	--%>
</form>
</html>