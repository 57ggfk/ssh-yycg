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
		jquerySubByFId("editpermform",permSave_collback);
	}
	function permSave_collback(data){
		message_alert(data,permSave_message_collback);
		
	
	}
	function permSave_message_collback(){
		parent.closemodalwindow();
	}
</script>
<title>编辑权限</title>
</head>
<body>
<!-- 修改权限 -->
<form id="editpermform" action="${baseurl }/perm/edit_submit.action" method="post">
	<!-- 采购单id -->
	
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed align=center>
	<TBODY>
		<TR>
		<input  type="hidden" name="sysPermissionCustom.isused" value="${sysPermissionCustom.isused}"  />
		<input  type="hidden" name="sysPermissionCustom.parentid"   value="${sysPermissionCustom.parentid}"/>
		<input 	type="hidden" name="sysPermissionCustom.plevel" value="${sysPermissionCustom.plevel}" />
		<input  type="hidden" name="sysPermissionCustom.ismenu"   value="${sysPermissionCustom.ismenu}"/>
		<input 	type="hidden" name="sysPermissionCustom.id"   value="${sysPermissionCustom.id}" />
				<TD	 width="100%">
					<TABLE cellSpacing=0 cellPadding=0 width="100%">
						<TBODY>
							<TR>
								<TD>&nbsp;编辑权限</TD>
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
								<input type="text" name="sysPermissionCustom.name" value="${sysPermissionCustom.name}"  style="width:260px" />
								</TD>
								<TD height=30 width="15%" align=right >权限URL：</TD>
								<TD class=category width="35%">
								<input type="text" name="sysPermissionCustom.url"  value="${sysPermissionCustom.url}" style="width:260px" />
								</TD>
							</TR>
							<TR>
							   <TD height=30 width="15%" align=right >PCODE：</TD>
								<TD class=category width="35%">
									<input type="text" name="sysPermissionCustom.pcode"  value="${sysPermissionCustom.pcode}" style="width:260px" />
								</TD>
								
									
								
							</TR>	
							<tr>
							  <td colspan=4 align=center class=category>
								<a  href="#" onclick="permSave()" class="easyui-linkbutton" iconCls='icon-update'>更新</a>
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