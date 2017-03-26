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
	function permSave(){
		
		jquerySubByFId("addpermform",permSave_collback);
	}
	function permSave_collback(data){
		message_alert(data,permSave_message_collback);
	}
	function permSave_message_collback(){

		parent.closemodalwindow();
		//parent.location.reload();
		
	}
	
</script>
<title>添加权限</title>
</head>
<body>
<!-- 添加权限 -->
<form id="addpermform" action="${baseurl }/perm/add_submit.action" method="post">
	<!-- 采购单id -->
	
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed align=center>
	<TBODY>
		<TR>
				<TD	 width="100%">
					<TABLE cellSpacing=0 cellPadding=0 width="100%">
						<TBODY>
							<TR>
								<TD>&nbsp;添加权限</TD>
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
								<TD height=30 width="15%" align=right >权限名称：</TD>
								<TD class=category width="35%"> 
								<input type="text" name="sysPermissionCustom.name"   style="width:260px" />
								</TD>
								<TD height=30 width="15%" align=right >权限URL：</TD>
								<TD class=category width="35%">
								<input type="text" name="sysPermissionCustom.url"   style="width:260px" />
								</TD>
							</TR>
							<TR>
							   <TD height=30 width="15%" align=right >PCODE：</TD>
								<TD class=category width="35%">
									<input type="text" name="sysPermissionCustom.pcode"   style="width:260px" />
								</TD>
								<TD height=30 width="15%" align=right >ID：</TD>
								<TD class=category width="35%">
									<input type="text" name="sysPermissionCustom.id"  readonly="readonly" value="${sysPermissionCustom.id}" />
								</TD>
							</TR>
							<tr>
							 <TD height=30 width="15%" align=right >parentId：</TD>
								<TD class=category width="35%">
									<input type="text" name="sysPermissionCustom.parentid"  readonly="readonly" value="${sysPermissionCustom.parentid}"/>
								</TD>
								<TD height=30 width="15%" align=right >PLevel：</TD>
								<TD class=category width="35%">
									<input type="text" name="sysPermissionCustom.plevel" readonly="readonly" value="${sysPermissionCustom.plevel}"  style="width:260px" />
								</TD>
							</tr>
							
							
							<tr>
							
							 <TD height=30 width="15%" align=right >ismenu：</TD>
								<TD class=category width="35%">
									<input type="text" name="sysPermissionCustom.ismenu" readonly="readonly"  value="${sysPermissionCustom.ismenu}"
								</TD>
								<TD height=30 width="15%" align=right >isused：</TD>
								<TD class=category width="35%">
									<input type="text" name="sysPermissionCustom.isused" readonly="readonly" value="${sysPermissionCustom.isused}"  style="width:260px" />
								</TD>
								</tr>
								<tr>
							  <td colspan=4 align=center class=category>
								<a  href="#" onclick="permSave()" class="easyui-linkbutton" iconCls='icon-save'>保存</a>
							    
							  </td>
							</tr>
						</TBODY>
					</TABLE>
				</TD>
		    </TR>
	</TBODY>
</TABLE>

</form>






</body>
</html>