<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<html>
<head>
<title>权限管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/common/common_css.jsp"%>
<%@ include file="/common/common_js.jsp"%>
<script type="text/javascript">
	$(function(){
		
		
		//普通列
		var colArr = [[
		               		
		               
			               {field:'name',title:'菜单',width:80,
			            	   formatter:function(value,row,index){
			            		   if(row.ismenu==1){
			            			   return value;
			            			   } 
			            		   return "";
			               }},
			               
			               {field:'menu',title:'操作',width:80,
			            	   formatter:function(value,row,index){
			            		   if(row.ismenu==0){
			            			   	            			  
			            				   return row.name;  
			            			   }
			            		   return "";
			            		  
			               }},
			               {field:'ismenu',title:'URL',width:120},
			               {field:'url',title:'URL',width:120},
			               {field:'plevel',title:'权限级别',width:120},
			               {field:'pcode',title:'PCODE',width:120},
			               {field:'edit',title:'编辑',width:120,
			            	   formatter:function(value,row,index){
			            		   return "<a  href='javascript:void(0)' onclick=\"editPerm('"+row.id+"')\" >编辑</a>";;
			            	   }
			               },
			               {field:'addsib',title:'添加兄弟权限',width:120,
			            	   formatter:function(value,row,index){
			            		   return "<a  href='javascript:void(0)' onclick=\"addSib('"+row.id+"')\" >添加</a>";
			            	   }
			               },
			               {field:'addchild',title:'添加孩子权限',width:120,
			            	   formatter:function(value,row,index){
			            		   if(row.ismenu==1){
			            		   return "<a  href='javascript:void(0)' onclick=\"addChild('"+row.id+"')\" >添加</a>";
			            		   }
			            	 }
			               },
			               {field:'dele',title:'删除权限',width:120,
			            	   formatter:function(value,row,index){
			            		   return "<a  href='javascript:void(0)' onclick=\"deletePerm('"+row.id+"')\" >删除</a>";
			            	   }
			               },
			              
			              
			          ]];
		
		
		//基本参数
		var options={
				columns:colArr,
				url:'${baseurl}/perm/allperm_result.action',
				title:'权限列表',
				idField:'id'		//唯一表示列
						
		};
		
		$("#sysPermList").datagrid(options);
	});
	
	//编辑当前权限
	function editPerm(permid){
		var sendUrl = "${baseurl}/perm/editPerm.action?sysPermissionCustom.id="+permid;
		parent.createmodalwindow("编辑权限", 800, 300, sendUrl);
	}
	
	//增加兄弟权限
	function addSib(permid){
		var sendUrl = "${baseurl}/perm/addSibling.action?sysPermissionCustom.id="+permid;
		parent.createmodalwindow("添加兄弟权限", 800, 300, sendUrl);
	}
	
	//增加孩子权限
	function addChild(permid){
	var sendUrl = "${baseurl}/perm/addChild.action?sysPermissionCustom.id="+permid;
	parent.createmodalwindow("添加孩子权限", 800, 300, sendUrl);
	}
	function deletePerm(permid){
			_confirm("确定要删除吗？",function(){
			$("#deletedPermId").val(permid);
			jquerySubByFId("sysuserdelForm",deletperm_callback);
		});
	}
	function deletperm_callback(data){
		message_alert(data,refreshForm);
	}
	
	function refreshForm(){
		alert("添加成功了1212。呵呵呵");
		//$("#listPermForm").datagrid("load");	
	}	
	function xx(){
		alert("xxx");
	}
</script>
</head>
<body>
<!-- 先写布局 -->
	
	<form id="listPermForm" name="sysuserqueryForm" method="post" action="">	
<!-- datagrid -->
<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
		<TBODY>
			<TR>
				<TD>
					<table id="sysPermList"></table>
				</TD>
			</TR>
		</TBODY>
</TABLE>
</form>
<!-- 此form用于删除 权限 -->
<form id="permdelForm" action="${baseurl }/perm/deletePerm_submit.action" method="post">
	<!-- form中包括要删除用户的id -->
	<input type="hidden"  id="deletedPermId" name="sysPermissionCustom.id"/>
</form>

</body>

</html>