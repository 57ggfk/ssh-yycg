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
	
	    //删除权限及其子权限
		var authdelList = function(){
			_confirm('您确定要执行删除操作吗?',null,
			  function(){
				
				alert();
				var ids = [];
				var rows = $('#authlist').datagrid('getSelections');
				for(var i=0;i<rows.length;i++){
					ids.push(rows[i].id);
				}
				if(ids.length>0){
					$("#ypxxdelid").val(ids.join(','));
					//alert($("#ypxxdelid").val());
					jquerySubByFId('ypxxdelForm', ypxxdel_callback, null, "json");
				}else{
					alert_warn("请选择要删除的项目");
				}
				
			  }
			)
			
		};
		
		//删除单项权限
		var authdel = function(id){
			_confirm('您确定要执行删除操作吗?',
					 function(){
						//$("#authdelid").val(id);
						//jquerySubByFId('authdelForm', authdel_callback);
						
						
						var node = $('#permissionTree').tree("getSelected");
						var nodeId=node.id;
						alert(nodeId);

						var sendUrl = "${baseurl}/perm/deletePerm_submit.action?sysPermissionCustom.id="+nodeId;
						
						parent.closemodalwindow();  	
						
						
						
					} 
			)
		};
		function authdel_callback(data) {
			var result = getCallbackData(data);
			_alert(result);
			
		}
	
	
	
	//最新的
		
	function deletePerm(){
			_confirm("确定要删除吗？",function(){
				
			var node = $('#permissionTree').tree("getSelected");
			var nodeId=node.id;
			//alert(nodeId);
				
				
			$("#deletedPermId").val(nodeId);
			jquerySubByFId("permdelForm",deletperm_callback);
		});
	}
	function deletperm_callback(data){
		message_alert(data,permSave_message_collback);
		
	
	}
	function permSave_message_collback(){
		parent.closemodalwindow();
	}
	
	//角色授权提交
	function role_authorize_submit(){
		//删除之前的所有
		$("input[name='addPermIds']").remove();
		//获得所有选中
		var allcheck = $('#permissionTree').tree("getChecked", ['checked','indeterminate']);
		for(var i = 0 ; i < allcheck.length ; i ++){
			var node = allcheck[i];
			$("#role_authorize_form").append("<input type='hidden' name='addPermIds' value='"+node.id+"' />");
		}
		
		jquerySubByFId('role_authorize_form',role_authorize_submit_callback);
	}
	
	//选中之后删除权限
	function role_delete_submit(){
		//删除之前的所有
		$("input[name='addPermIds']").remove();
		//获得所有选中
		var allcheck = $('#permissionTree').tree("getChecked", ['checked','indeterminate']);
		for(var i = 0 ; i < allcheck.length ; i ++){
			var node = allcheck[i];
			alert(node);
			
			//$("#role_authorize_form").append("<input type='hidden' name='addPermIds' value='"+node.id+"' />");
		}
		
		jquerySubByFId('role_authorize_form',role_authorize_submit_callback);
	}
	//角色授权回调
	function role_authorize_submit_callback(data){
		message_alert(data,function(){
			parent.closemodalwindow();
		});
	}
	
	//添加兄弟权限
	function appSibling(){
		var node = $('#permissionTree').tree("getSelected");

		var nodeId=node.id;
		
		var sendUrl = "${baseurl}/perm/addSibling.action?sysPermissionCustom.id="+nodeId;
		
		parent.closemodalwindow();
		parent.createmodalwindow("添加兄弟权限", 800, 300, sendUrl);

	}
	//添加子权限
	function appChild(){
		var node = $('#permissionTree').tree("getSelected");
		var nodeId=node.id;
		
		var sendUrl = "${baseurl}/perm/addChild.action?sysPermissionCustom.id="+nodeId;
		
		parent.closemodalwindow();
		parent.createmodalwindow("添加孩子权限", 800, 300, sendUrl);
	}
	
	//修改权限
	function edit(){
		var node = $('#permissionTree').tree("getSelected");
		var nodeId=node.id;
		
		var sendUrl = "${baseurl}/perm/editPerm.action?sysPermissionCustom.id="+nodeId;
		
		parent.closemodalwindow();
		parent.createmodalwindow("修改权限", 800, 300, sendUrl);
	}
	
	//删除当前权限
	function removeCurrent(){
		
		var node = $('#permissionTree').tree("getSelected");
		var nodeId=node.id;
		alert(nodeId);

		var sendUrl = "${baseurl}/perm/deletePerm_submit.action?sysPermissionCustom.id="+nodeId;
		
		parent.closemodalwindow(); 
		
	}
	//删除当前权限及子权限
	function removeAll(){
		
		var node = $('#permissionTree').tree("getSelected");
		var nodeId=node.id;
		alert(nodeId);
		
		
        var sendUrl = "${baseurl}/perm/deletePerm_all_submit.action?sysPermissionCustom.id="+nodeId;
		
		parent.closemodalwindow(); 
	}
	

	
	
	$(function() {
		
		//按钮
		$('#submitbtn').linkbutton({   
    		iconCls: 'icon-ok'  
		});  
		$('#closebtn').linkbutton({   
    		iconCls: 'icon-cancel'  
		});
		
		// easyui tree
		$('#permissionTree').tree({    
		    url:'${baseurl}/perm/permission_result.action',
		    queryParams:{"sysRoleQueryCustom.id":"${sysRoleQueryCustom.id}"},
		    animate:true,		//展开或折叠的时候是否显示动画效果
		    lines:true,			//是否显示树控件上的虚线
		    checkbox:true,		//是否有复选框
		    onCheck:function(node, checked){

		    	//无论添加或删除，相同的数据只能出现一次
		    	
		    	$("input[value='"+node.id+"']").remove();

		    	if(checked){ //添加权限
		    		$("#role_authorize_form").append("<input type='hidden' name='addPermIds' value='"+node.id+"' />");
		    	} else { //取消权限
		    		$("#role_authorize_form").append("<input type='hidden' name='delPermIds' value='"+node.id+"' />");
		    	}

		    },
		    onContextMenu: function(e,node){
				e.preventDefault();	//阻止默认菜单
				$(this).tree('select',node.target);	//选中当前tree中
				$('#permOperate').menu('show',{	//给指定坐标，显示菜单
					left: e.pageX,
					top: e.pageY
				});
			}
		});  

		
	});

	</script>
 </HEAD>
<BODY>
<form id="role_authorize_form" name="role_authorize_form" action="${baseurl}/perm/roleauthsubmit.action" method="post">
<input type="hidden" name="sysRoleCustom.id" value="${sysRoleQueryCustom.id}"/>

<%-- 
//添加
<input type="text" id="id01.01" name="addPermIds" value="01.01" />
//删除
<input type="text" id="id01.01" name="delPermIds" value="01.01" />
--%>

<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>
		<TBODY>
			<TR>
				<TD background="${baseurl }/images/r_0.gif" width="100%">
					<TABLE cellSpacing=0 cellPadding=0 width="100%">
						<TBODY>
							<TR>
								<TD>&nbsp;给【${sysRole.name}】分配权限信息</TD>
								<TD align=right>&nbsp;</TD>
							</TR>
						</TBODY>
					</TABLE>
				</TD>
			</TR>
			<TR>
				<TD>
					<TABLE class="toptable grid" border=1 cellSpacing=1 cellPadding=4
						align=center>
						<TBODY>
							<TR>
								<TD width="100%" align=left rolspan=2>
								<!-- 选中树的结点id，以逗号分隔
								以框中默认填充了角色下的权限，当进入此页面用户什么都不操作，直接点提交
								 -->
								<div id="permissionTreeContent" >
									<ul id="permissionTree" class="ztree"></ul>
								</div>
								</TD>
								
							</TR>
							
							
							<tr>
							  <td colspan=4 align=center class=category>
								<a id="submitbtn" href="#" onclick="role_authorize_submit()">提交</a>
								<a id="closebtn" href="#" onclick="parent.closemodalwindow()">关闭</a>
							  </td>
							</tr>
						
						</TBODY>
					</TABLE>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</form>

<%--菜单 --%>
<div id="permOperate" class="easyui-menu" style="width:120px;">
	<div onclick="appSibling()" data-options="iconCls:'icon-add'">添加兄弟权限</div>
	<div onclick="appChild()" data-options="iconCls:'icon-add'">添加子权限</div>	
	<div onclick="edit()" data-options="iconCls:'icon-edit'">修改权限</div>
	<div class="menu-sep"></div>
	<div onclick="deletePerm()" data-options="iconCls:'icon-remove'">删除权限</div>
	<!-- <div onclick="authdelList()" data-options="iconCls:'icon-remove'">删除当前权限及子权限</div> -->
</div>

<!-- 删除权限开始 -->

<form id="authdelForm" name="authdelForm" action="${baseurl}/perm/deletePerm_submit.action" method="post">
<input type="hidden" id="authdelid" name="authdelid"/>
</form>

<!-- 删除权限结束 -->




<!-- 此form用于删除 权限 -->
<form id="permdelForm" action="${baseurl }/perm/deletePerm_submit.action" method="post">
	<!-- form中包括要删除用户的id -->
	<input type="hidden"  id="deletedPermId" name="sysPermissionCustom.id"/>
</form>

</BODY>
</HTML>

