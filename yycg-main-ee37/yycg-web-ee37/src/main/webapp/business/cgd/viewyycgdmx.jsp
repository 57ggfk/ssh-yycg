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

var columns=[[
{
	field : 'bm',
	title : '流水号',
	width : 50,
	formatter: function(value,row,index){
		if (row.ypxx) {
			return row.ypxx.bm;
		}
	}
	
},{
	field : 'mc',
	title : '通用名',
	width : 100,
	formatter: function(value,row,index){
		return row.ypxx.mc;
	}
},{
	field : 'jx',
	title : '剂型',
	width : 70,
	formatter: function(value,row,index){
		return row.ypxx.jx;
	}
},{
	field : 'gg',
	title : '规格',
	width : 70,
	formatter: function(value,row,index){
		return row.ypxx.gg;
	}
},{
	field : 'zhxs',
	title : '转换系数',
	width : 80,
	formatter: function(value,row,index){
		return row.ypxx.zhxs;
	}
},{
	field : 'zbjg',
	title : '中标价',
	width : 50
},{
	field : 'sysDictInfoCgzt',
	title : '采购状态',
	width : 80,
	formatter: function(value,row,index){
		if (value) {
			return value.info;
		}
	}
},{
	field : 'cgl',
	title : '采购量',
	width : 50,
}
,{
	field : 'cgje',
	title : '采购金额',
	width : 80,
	formatter:function(value,row,index) {
		if (value) {
			return "￥"+value.toFixed(2);
		}
	}
}
     ]];

	$(function(){
		$("#yycgdmxlist").datagrid({
			url:"${baseurl}/cgd/yycgdmx_result.action",
			title:"采购单明细列表",
			queryParams:{//只在初始加载datagrid时使用
				'yycgdMxQueryCustom.yycgd.id':'${yycgdCustom.id}'
			},
			columns:columns,
			fitColumns:true,
			//fixed:true,
			pagination:true,
			rownumbers:true,
			idFiled:"id",
			striped:true,
		});
		
	})
</script>
</head>
<body>
	<table id="yycgdmxlist"></table>
</body>
</html>