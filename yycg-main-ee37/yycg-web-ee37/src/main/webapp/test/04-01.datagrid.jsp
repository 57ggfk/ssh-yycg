<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DataGrid</title>
<%@include file="/common/common_css.jsp" %>
<%@include file="/common/common_js.jsp" %>
<script type="text/javascript">


	var formatOperate = function(value,row,index) {
		return "<a href='#' class='easyui-linkbutton' onclick='delProduct(\""+row.productid+"\")'>删除</a>";
	}
	
	function delProduct(id){
		alert(id);
	}
	
	var columnArray = [[
	                {field:'id',title:'编号',width:'30'},
	                {field:'usercode',title:'用户名',width:80},
	                {field:'pwd',title:'密码',width:280},
	                {field:'username',title:'姓名',width:150},
	                {field:'dwWss',title:'名称',width:180,formatter:function(value,row,index){
	                	//console.log(value);
	                	if (value) {
	                		return value.mc;
	                	}
	                	return "未知";
	                }},
	                {field:'xx',title:'区域',width:180,formatter:function(value,row,index){
	                	if (row.dwWss) {
	                		return row.dwWss.sysArea.areaname;
	                	}
	                }},
	                {field:'sysDictInfoByGroupid',title:'用户类型',width:80,formatter:function(value){
	                	return value.info;
	                }},
	                {field:'sysDictInfoByUserstate',title:'用户状态',width:60,formatter:function(value){
	                	return value.info;
	                }},
	                {field:'null',title:'操作',width:100,formatter:formatOperate},
	                ]];

	$(function(){
		$("#dg").datagrid({
			url:"/user/findAllforJson.action",
			columns:columnArray,
			selectOnCheck:false,
			pagination:true,
			rownumbers:true,
			collapsible:true
		});
	});
</script>
</head>
<body>  

<table id="dg"></table>
</body>
</html>