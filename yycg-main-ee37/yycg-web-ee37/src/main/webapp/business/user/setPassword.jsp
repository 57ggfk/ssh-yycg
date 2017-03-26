<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<html>
  <head>
    <title>修改密码</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/common/common_css.jsp"%>
<%@ include file="/common/common_js.jsp"%>

	<script type="text/javascript">
	
	</script>
 </HEAD>
<BODY>
<form id="sysuseraddform" name="sysusereditform" action="${baseurl}/user/adduser_submit.action" method="post" >
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>
		<TBODY>
			<TR>
				<TD width="100%">
					<TABLE cellSpacing=0 cellPadding=0 width="100%">
						<TBODY>
							<TR>
								<TD>&nbsp;系统用户信息</TD>
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
								<TD height=30 width="15%" align=right >用户账号：</TD>
								<TD class=category width="35%">
								<input type="text" id="sysuser_password" name="sysUserCustom.pwd" autocomplete="off" />
								<div id="sysuser_usernameTip"></div>
								</TD>
							</TR>

							<TR>
								<TD height=30 width="15%" align=right >用户密码：</TD>
								<TD class=category width="35%">
								<div>
									<input type="password" id="sysuser_password" name="sysUserCustom.pwd" autocomplete="off" />
									<!-- 加一个隐藏密码框，防止密码 自动填充 -->
									<input type="password" style="display:none"/>
								</div>
								</TD>

							</TR>
							
							
							<tr>
							  <td colspan=4 align=center class=category>
								<a id="submitbtn" href="#" onclick="sysusersave()">提交</a>
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

<%-- 通过dictcode查询单位名称 
<form id="findDwForm" action="/user/findDwByDictcode.action" method="post" style="display: none">
	<input type="text" id="findDwDictcode" name="" value=""/>
</form>
--%>
</BODY>
</HTML>

