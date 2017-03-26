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
	var formatMoney = function (value) {
		return value+" 美元";
	}
	var formatStatus = function(value) {
		if (value=="P"||value=="p") {
			return "上架";
		} else {
			return "下架";
		}
	}
	
	var formatCgName = function (value) {
		if (value=="已审核"){
			return value;
		} else {
			return "未审核";
		}
	}
	var formatOperate = function(value,row,index) {
		return "<a href='#' class='easyui-linkbutton' onclick='delProduct(\""+row.productid+"\")'>删除</a>";
	}
	
	function delProduct(id){
		alert(id);
	}
	
	var columnArray = [[
	                {field:'productid',title:'商品编号',width:80},
	                {field:'productname',title:'商品名称',width:80},
	                {field:'unitcost',title:'单价',width:50,formatter:formatMoney},
	                {field:'status',title:'状态',width:40,formatter:formatStatus},
	                {field:'listprice',title:'小计',width:60,formatter:formatMoney},
	                {field:'attr1',title:'规格',width:60},
	                {field:'itemid',title:'分类',width:60},
	                {field:'cgName',title:'是否审核',width:60,formatter:formatCgName},
	                {field:'null',title:'操作',width:100,formatter:formatOperate},
	                ]];

	$(function(){
		$("#dg").datagrid({
			url:"datagrid_data.json",
			columns:columnArray,
			selectOnCheck:false,
			pagination:true,
			rownumbers:true,
		});
	});
</script>
</head>
<body>  

<table id="dg"></table>
</body>
</html>