<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<html>
<head>
<title>系统用户信息查询</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/common/common_css.jsp"%>
<%@ include file="/common/common_js.jsp"%>
<script type="text/javascript">

//用户类别 数据字典 0:系统管理员,1：卫生局 2:卫生院 3：卫生室 4:供货商
// 	private DwWss dwWss;  //3：卫生室
// 	private DwGys dwGys;  //4:供货商
// 	private DwWsy dwWsy;  //1：卫生局 2:卫生院 
function formatMc(value,row,index){
	var mc = "";
	if (row.dwWsy){
		mc = row.dwWsy.mc;
	}
	if (row.dwWss) {
		mc = row.dwWss.mc
	}
	if (row.dwGys) {
		mc = row.dwGys.mc
	}
	return mc;
}

function formatArea(value,row,index) {
	var groupId = row.sysDictInfoByGroupid;
	var code = "";
	if (groupId){
		code = groupId.dictcode;
	}
	
	if (code=="1" || code=="2") {
		var dwWsy = row.dwWsy;
		if (dwWsy){
			var sysArea = dwWsy.sysArea;
			if (sysArea){
				return sysArea.areaname;
			}
		}
	}
	if (code=="3") {
		var dwWss = row.dwWss;
		if (dwWss){
			var sysArea = dwWss.sysArea;
			if (sysArea){
				return sysArea.areaname;
			}
		}
	}
	if (code=="4"){
		var dwGys = row.dwWsy;
		if (dwGys){
			var sysArea = dwGys.sysArea;
			if (sysArea){
				return sysArea.areaname;
			}
		}
	}
	
	function area(obj){
		if (obj){
			var sysArea = obj.sysArea;
			if (sysArea){
				return sysArea.areaname;
			}
		}
	}
}
var columnArr = [[
            {field:"usercode",title:'用户账号',width:80},
            {field:"username",title:'用户名称',width:80},
            {field:"userstate",title:'用户状态',width:80,formatter:function(value,row,index){
            	var userState = row.sysDictInfoByUserstate; 
            	if (userState) {
            		return userState.info
            	}
            }},
            {field:"usertype",title:'用户类型',width:80,formatter:function(value,row,index) {
            	var userType = row.sysDictInfoByGroupid;
            	if(userType){
            		return userType.info;
            	}
            }},
            {field:"mc",title:'所属单位',width:80,formatter:formatMc},
            {field:"area",title:'地址',width:80,formatter:formatArea},
            {field:"edit",title:'修改',width:80,formatter:function(value,row,index){
            	<shiro:hasPermission name="user:edit">
            		return "<a href='javascript:void(0)' onclick='edituser(\""+row.id+"\")'>修改</a>";
            	</shiro:hasPermission>
            }},
            {field:"delete",title:'删除',width:80,formatter:function(value,row,index){
            	<shiro:hasPermission name="user:delete">
            		return "<a href='javascript:void(0)' onclick='deleteuser(\""+row.id+"\")'>删除</a>";
            	</shiro:hasPermission>
            	
            }},
            
            ]];
var toolArr = [
			<shiro:hasPermission name="user:add">
               {
				iconCls: 'icon-add',
				handler: adduser,
				text:'添加用户'
               }
			</shiro:hasPermission>
       		];
$(function(){
	<shiro:hasPermission name="user:queryuser">
	//console.log("BaseUrl:"+BaseUrl);
	$("#sysuserlist").datagrid({
		url:"${baseurl}/user/queryuser_result.action",
		columns:columnArr,
		striped:true,
		fitColumns:true,
		pagination:true,
		rownumbers:true,
		striped:true,
		pageSize:15,
		pageList:[15,30,45,60],
		toolbar:toolArr
	});
	</shiro:hasPermission>
});

//执行用户搜索，也可以当刷新用
function sysuserquery() {
	var params = $("#sysuserqueryForm").serializeJson();
	$("#sysuserlist").datagrid('reload',params);
	return false;
}

//添加用户方法-打开一个Window，显示添加表单
function adduser(){
	<shiro:hasPermission name="user:add">
	createmodalwindow("添加用户",650,210,"${baseurl}/user/adduser.action");
	</shiro:hasPermission>
}

//执行删除，异步提交表单
function deleteuser(id){
	//权限校验
	<shiro:hasPermission name="user:delete">
	//赋值个form的input
	$("#sysuserdelid").val(id);
	_confirm("是否要删除？",function(){
		//确定后的回调函数，提交表单
		jquerySubByFId("sysuserdelForm",deleteuser_callback);
	});
	</shiro:hasPermission>
}

//执行删除后的回调函数
function deleteuser_callback(data) {
	message_alert(data,function(){
		//删除成功后刷新用户列表
		sysuserquery();
	});
}

//执行修改
function edituser(id){
	<shiro:hasPermission name="user:edit">
	createmodalwindow("修改用户",650,210,"${baseurl}/user/edituser.action?sysUserCustom.id="+id);
	</shiro:hasPermission>
}


</script>
</head>
<body>
<!-- 先写布局 -->
<form id="sysuserqueryForm" name="sysuserqueryForm" method="post" action="">
			<TABLE class="table_search">
				<TBODY>
					<TR>
						<TD class="left">用户账号：${sysUserCustom.usercode }</td>
						<td><INPUT type="text" name="sysUserQueryCustom.usercode" /></TD>
						<TD class="left">用户名称：</TD>
						<td><INPUT type="text" name="sysUserQueryCustom.username" /></TD>

						<TD class="left">用户类型：</TD>
						<td>
							<select name="sysUserQueryCustom.sysDictInfoByGroupid.id">
								<option value="">请选择</option>
								<c:forEach items="${userGroupList}" var="dictinfo">
								  <option value="${dictinfo.id}">${dictinfo.info}</option>
								</c:forEach>
								
							</select>
						</TD>
						<td >
						<a id="btn" href="#" onclick="sysuserquery()"
							class="easyui-linkbutton" iconCls='icon-search'>查询</a>
						<input type="submit" style="display:none" onclick="return sysuserquery()"/>
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
					<table id="sysuserlist"></table>
				</TD>
			</TR>
		</TBODY>
</TABLE>
<!-- 此form用于删除用户 -->
<form id="sysuserdelForm" action="${baseurl }/user/deleteuser_submit.action" method="post">
	<!-- form中包括要删除用户的id -->
	<input type="hidden"  id="sysuserdelid" name="sysUserCustom.id"/>
</form>
</body>

</html>