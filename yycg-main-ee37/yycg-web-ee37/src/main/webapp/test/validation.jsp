<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>jQuery EasyUI</title>
<LINK rel="stylesheet" type="text/css" href="${baseurl}/js/easyui/styles/default.css">
<%@ include file="/common/common_css.jsp"%>
<%@ include file="/common/common_js.jsp"%>
	<style>
		input[type='text'],input[type='password'] {
			width:60%;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function () {
			$("#formId").validate({
				rules:{
					loginname:{
						required:true,
						minlength:2,
						maxlength:5
					},
					loginpwd:{
						required:true,
						number:true
					},
					reloginpwd:{
						equalTo:"[name='loginpwd']"
					},
					email:"email",
					username:{
						required:true,
						rangelength:[2,5]
					},
					gender:{
						required:true
					},
					birthday:"dateISO"
					/*,
					verifyCode:{
						remote:"t.html"
					},*/
				},
				messages:{
					gender:{
						required:"性别必须勾选"
					}
				}
			});
		});
	</script>
</head>
<body>
	<form id="formId" action="../index.html" >
		<table  width="600" height="350px">
			<tr>
				<td colspan="3">
					<font color="#3164af">会员注册</font> USER REGISTER
				</td>
			</tr>
			<tr>
				<td align="right">用户名</td>
				<td colspan="2"><input id="loginnameId" type="text" name="loginname" size="60"/> </td>
			</tr>
			<tr>
				<td align="right">密码</td>
				<td colspan="2"><input id="loginpwdId" type="password" name="loginpwd" size="60" /> </td>
			</tr>
			<tr>
				<td align="right">确认密码</td>
				<td colspan="2"><input id="reloginpwdId" type="password" name="reloginpwd" size="60" /> </td>
			</tr>
			<tr>
				<td align="right">Email</td>
				<td colspan="2"><input id="emailId" type="text" name="email" size="60" /> </td>
			</tr>
			<tr>
				<td align="right">姓名</td>
				<td colspan="2"><input type="text" name="username" size="60" /> </td>
			</tr>
			<tr>
				<td align="right">性别</td>
				<td colspan="2">
					<input type="radio" name="gender" value="男" />男
					<input type="radio" name="gender" value="女" />女
					<!--在指定位置显示错误信息
						* class 必须是error
						* for 必须设置错误对象
						
					-->
					<label for="gender" class="error"></label>
				</td>
			</tr>
			<tr>
				<td align="right">出生日期</td>
				<td colspan="2"><input type="text" name="birthday" size="60" /> </td>
			</tr>
			<tr>
				<td width="80" align="right">验证码</td>
				<td width="100"><input type="text" name="verifyCode" /> </td>
				<td><img src="${baseurl}/images/code.jpg" /> </td>
			</tr>
			<tr>
				<td></td>
				<td colspan="2">
					<input type="submit" value="注册" /> 
				</td>
			</tr>
		</table>
	</form>
	
</body>
</html>