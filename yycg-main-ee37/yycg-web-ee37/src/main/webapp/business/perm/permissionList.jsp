<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<html>
<head>
<title>权限查询</title>
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
	if ($('#rolelist').datagrid('validateRow', editIndex)){
		//将当前编辑行，结束编辑
		$('#rolelist').datagrid('endEdit', editIndex);
		updatedRow = null;
		//将当前编辑行设置没有数据
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
var clock = 0;
//点击添加事件
function addRow(){
	//先判断clock状态
	var index=$('#rolelist').datagrid('insertRow', {  
		index:0,
		row:{
	        //FlowTypeName: '好吗',
	        FlowTypeCode: 20 ,
	        id:'必填',
	        name:'必填',
	        parentid:'点击修改',
	        url:"添加完成后点击保存",
	        isused:"1",
	        ismenu:"0"
		}
		 }).datagrid('highlightRow').datagrid('getRows').length-3;
$('#rolelist').datagrid('beginEdit',index)
}

//点击行事件
function onClickRow(index){
	//之前编辑行，当前点击是否同一样
	if (editIndex != index){
		if (endEditing()){
			//开始编辑
			$('#rolelist').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			//并记录编辑行
			editIndex = index;
		} else {
			$('#rolelist').datagrid('selectRow', editIndex);
		}
	}
} 


var url = "${baseurl}/perm/permissionList_result.action";
var tobar =[{
	iconCls: 'icon-save',
	text:'保存',
	handler: savePermission,
},'-',{
	iconCls: 'icon-add',
	text:'添加',
	handler: addRole
},{
	iconCls:'icon-ok',
	text:'添加完成',
	handler:addAllData
},{
	text:'',
}
];

//表单的整体表单的修改
function savePermission(){                                                                                                                                                                                                                                                                                                                                                                                                                   
	//结束编辑
	endEditing();
	//获得所有的修改内容
	var updatedRow = $("#rolelist").datagrid("getChanges","updated");
	//再将下次的数据添加到提交表单的时候先清除表单中的数据标签,防止数据的重复提交
	var allData="";
	$.each(updatedRow,function(i,n){
		allData =allData+","+n.id+"A"+":"+n.name+"A"+":"+n.url+"A"+":"+n.icon+"A"+":"+n.showorder+"A"+":"+n.isused+"A"+":"+n.ismenu+"A"+":"+n.plevel+"A"+":"+n.pcode+"A"+":"+n.vchar1+"A";
		//<input name="list[0].id" />
	})	
		var allDataString = allData.toString();
	$("#allDataString").remove();
	$("#saveAllDataForm").append("<input type='hidden' name='allDataString' value='"+allDataString+"'></input>");
	//提交表单
	jquerySubByFId("saveAllDataForm",saveAllDataForm_callback)
};
function saveAllDataForm_callback(data){
	message_alert(data,saveAllDataForm_message_callback);
	
}
function saveAllDataForm_message_callback(){
	//location.reload();
	datagrid
}
//添加数据
function addRole(){
	if(clock==0){
	//首先加载添加一行空白行
	addRow();
	clock=1;
	}
};
//添加数据成功
function addAllData(){
	if(clock==1){
	//结束编辑
	endEditing();
	//定一个空串
	var insertAll = undefined;
	//获取修改行的状态
	var insertData = $("#rolelist").datagrid("getChanges","inserted")
	//先移除标签
	$("#insertData").remove();
	//拼串
	$.each(insertData,function(i,n){
	var	insertA = n.id+"A"+":"+n.name+"A"+":"+n.url+"A"+":"+n.icon+"A"+":"+n.showorder+"A"+":"+n.isused+"A"+":"+n.ismenu+"A"+":"+n.plevel+"A"+":"+n.pcode+"A"+":"+n.vchar1+"A";
	insertAll = insertA.toString();
	})
	//将数据添加到表单中
	$("#insertForm").append("<input type='hidden' name='allDataString' value='"+insertAll+"'></input>");
	//提交表单
	jquerySubByFId("insertForm",addAllData_callback);
	}
	clock=0;
}
//添加完成
/* function addAllData(){
	//结束编辑
	endEditing();
	//定一个空串
	var insertAll = undefined;
	//获取修改行的状态
	var insertData = $("#rolelist").datagrid("getChanges","inserted")
	//先移除标签
	$("#insertData").remove();
	//拼串
	$.each(insertData,function(i,n){
	var	insertA = n.id+"A"+":"+n.name+"A"+":"n.url+"A"+":"+n.icon+"A"+":"+n.showorder+"A"+":"+n.isused+"A"+":"+n.ismenu+"A"+":"+n.plevel+"A"+":"+n.pcode+"A"+":"+n.vchar1+"A";
	})
	//insertAll = insertA.toString();
	//将数据添加到表单中
	//$("#insertForm").append("<input type='hidden' name='allDataString' value='"+insertAll+"'></input>");
	//提交表单
	//jquerySubByFId("insertForm",addAllData_callback);
} */
//数据回显
function addAllData_callback(data){
	message_alert(data,addAllData_message_callback)
}
function addAllData_message_callback(){
	alert();
	datagrid();
}
var columns=[[
			{field : 'id',title : '权限id',width : 60,
				editor:"text"	
			},
			{field : 'name',title : '模块名称',width : 100,
				editor:"text"	
			},
			{field : 'parentid',title : '父模块id',width : 80},
			{field : 'url',title : '功能访问url',width :180,
				editor:"text"	
			},
			{field : 'icon',title : '模块图标',width : 80,
				editor:"text"	
			},
			{field : 'showorder',title : '显示顺序',width : 80,
				editor:"text"
			},
			{field : 'isused',title : 'isused',width : 40,
				editor:"text"	
			},
			{field : 'ismenu',title : 'ismenu',width : 40,
				editor:"text"	
			},
			{field : 'plevel',title : '层级',width : 60,
				editor:"text"	
			},
			{field : 'pcode',title : '权限标识码',width : 80,
				editor:"text"
			},
			{field : 'vchar1',title : '备注',width : 60,
				editor:"text"	
			},{field : 'delete',title : '删除',width : 60,
				formatter:function(value,row ,index){//"<a href='javascript:void(0)' onclick='deleteuser(\""+row.id+"\")'>删除</a>";
					return "<a href='javascript:void(0)' onclick='deletePermission(\""+row.id+"\")'>删除</a>";
				}
			},
			{field:'FlowTypeName',title:'' ,width:60,align:'center',
				editor:{
					type:'text',
					options:{required:true}
				}
			}
              ]]
var options = {
		title:"权限管理列表",
		columns:columns,
		striped:true,
		nowrap:true,
		idField:'id',
		url:url,
		loadMsg:"小崽子,千万别乱改哦!",
		pagination:true,
		rownumbers:true,
		ctrlSelect:true,
		checkOnSelect:true,
		pageSize:15,
		pageList:[10,15,30,40,50],
		scrollbarSize:10,
		toolbar:tobar,
		onClickRow: onClickRow,			//点击行事件
		insertRow:addRow
};

$(function(){
	datagrid();
})
function datagrid(){
	$("#rolelist").datagrid(options);
};
//删除页面
function deletePermission(id){
	$("#deleteData").val(id);
	jquerySubByFId("deleteForm",deletePermission_callback);
}
function deletePermission_callback(data){
	message_alert(data,deletePermission_message_callback)
}
function deletePermission_message_callback(){
	datagrid();
}
</script>
</head>
<body>
<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
		<TBODY>
			<TR>
				<TD>
					<table id="rolelist"></table>
				</TD>
			</TR>
		</TBODY>
</TABLE>
<!-- 此form用于数据提交到后台 -->
<form id="saveAllDataForm" action="${baseurl }/perm/updatePermission_submit.action" method="post">
	<!-- form中包括要删除用户的id -->
	<!-- <input type="hidden"  id="allDataString" name="allDataString"/> -->
</form>
<!-- 此form用于数据删除 -->
<form id="deleteForm" action="${baseurl }/perm/deletePermission.action" method="post">
	<!-- form中包括要删除用户的id -->
	<input type="hidden"  id="deleteData" name="sysPermCustom.id"/>
</form>
<!-- 此form用于添加表单的保存数据 -->
<form id="insertForm" action="${baseurl }/perm/insertPermission_submit.action" method="post">
	<!-- form中包括要删除用户的id -->
	<input type="hidden"  id="insertData" name="allDataString"/>
</form>


</body>

</html>