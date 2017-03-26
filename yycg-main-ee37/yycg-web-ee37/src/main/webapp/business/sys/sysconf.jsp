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
//编辑
var editIndex = undefined;
//结束编辑状态
function endEditing(){
	//从来没有编辑
	if (editIndex == undefined){return true}
	//校验当前编辑行
	if ($('#dg').datagrid('validateRow', editIndex)){
		//将当前编辑行，结束编辑
		$('#dg').datagrid('endEdit', editIndex);
		//将当前编辑行设置没有数据
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
//点击行事件
function onClickRow(index){
	//之前编辑行，当前点击是否同一样
	if (editIndex != index){
		if (endEditing()){
			//开始编辑
			$('#dg').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			//并记录编辑行
			editIndex = index;
		} else {
			$('#dg').datagrid('selectRow', editIndex);
		}
	}
} 
var url = "${baseurl}/system/sysconf.action";

var columns=[[
	{field : 'name',title : '参数名称',width : 150},
	{field : 'value',title : '参数值',width : 500,
		editor:"text"
	},
	{field : 'type',title : '参数类型',width : 80},
	{field : 'isalive',title : '是否可用',width : 60,
		formatter:function(value,row,index){
			if(value==1){
				return "可用";
			}else{
				
			return "不可用";
			}
		}
	},
	{field : 'hf',title : '恢复',width : 80,
		formatter:function(value,row,index){
			return "<a href='javascript:void(0)' onclick='findrRecover(\""+row.id+"\")'>恢复</a>"
		}
	},
]]

var toolbar = [{
		id : 'yycgdmxaddshow',
		text : '保存',
		iconCls : 'icon-save',
		handler : systemSave
}	]
	
var options={
	title:"系统设置",
	url:url,
	columns:columns,
	pagination:true,
	striped:true,
	loadMsg:"修改系统有风险,请谨慎!",
	rownumbers:true,
	pageSize:5,
	pageList:[5,8,12,15,20],
	toolbar : toolbar,
	onClickRow: onClickRow			//点击行事件
}

$(function(){
	digrid()
	
})
function digrid(){
	$("#dg").datagrid(options)
}

//数据修改提交
function systemSave(){
	//结束编辑
	endEditing();
	//获得所有的修改内容
	var updatedRow = $("#dg").datagrid("getChanges","updated");
	//再将下次的数据添加到提交表单的时候先清除表单中的数据标签,防止数据的重复提交
	$("input[name='sysConfId']").remove();
	$("input[name='sysConfValue']").remove();
	//提交表单
	$.each(updatedRow,function(){
		$("#systemForm").append("<input type='hidden' name='sysConfId' value='"+this.id+"'></input>");
		$("#systemForm").append("<input  type='hidden' name='sysConfValue' value='"+this.value+"'></input>");
	})
	//提交表单
	jquerySubByFId("systemForm",systemSave_callback)
	
}

function systemSave_callback(data){
	message_alert(data,systemSave_message_callback);
}
function systemSave_message_callback(){
	digrid()
	//location.reload();
}
//数据恢复
function findrRecover(id){
	//将id值给input标签
	$("#sysconfId").val(id);
	_confirm("是否要恢复？",function(){
		//确定后的回调函数，提交表单
		jquerySubByFId("recoverForm",findrRecover_callback);
	});
}
//恢复回显
function findrRecover_callback(data){
	//恢复之后刷新页面
	//message_alert(data,findrRecover_message_callback);
	
	message_alert(data,findrRecover_message_callback)
}
function findrRecover_message_callback(){
	//location.reload();
	digrid()
}
</script>
<title>系统设置</title>
</head>
<!-- datagrid -->
<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
			<TBODY>
				<TR>
					<TD>
						<table id="dg"></table>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
</body>
<!-- 此form用于恢复用户 -->
<form id="recoverForm" action="${baseurl }/system/findRecover.action" method="post">
</form>
<%--系统管理 start --%>
<form id="systemForm" action="${baseurl}/system/systemSubmit_submit.action" method="post">
<!-- form中包括要恢复系统的id -->
	<input type='hidden' id="sysconfId" name="sysConfId"/>
</form>
<%--系统管理end --%>
</html>