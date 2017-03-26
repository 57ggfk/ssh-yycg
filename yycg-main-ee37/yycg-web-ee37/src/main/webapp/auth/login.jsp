<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<html>
  <head>
    <title>系统登陆</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/common/common_css.jsp"%>
<%@ include file="/common/common_js.jsp"%>
<LINK rel="stylesheet" type="text/css" href="${baseurl}/styles/login.css">

<STYLE type="text/css">
.btnalink {
	cursor: hand;
	display: block;
	width: 80px;
	height: 29px;
	float: left;
	margin: 12px 28px 12px auto;
	line-height: 30px;
	background: url('${baseurl}/images/login/btnbg.jpg') no-repeat;
	font-size: 14px;
	color: #fff;
	font-weight: bold;
	text-decoration: none;
}
</STYLE>

<script type="text/javascript">

/* 方法占位不实现功能 */

 function setYmzrsj(data){}
$(function(){
	if (top!=self) {
		top.location = self.location;
	}
});

	function checkinput() {
		
		return true;
	}

	function loginsubmit() {
		if(checkinput()){
			jquerySubByFId('loginform', login_commit_callback);
		} 
		//document.loginform.submit();

	}
	function login_commit_callback(data) {
		//_alert(data);
		$.messager.alert("登录提示",data.resultInfo.message);
		var type = data.resultInfo.type;
		if (TYPE_RESULT_SUCCESS == type) {
			setTimeout("tofirst()", 1000); 
		} else {
			randomcode_refresh();
		}

	}
	function randomcode_refresh() {
		$("#randomcode_img").attr('src',
				'${baseurl}/auth/validatecode.jsp?time' + new Date().getTime());
	}
	function tofirst(){
// 		if (top!=self) {
// 			top.location = self.location;
// 		} else {
// 			top.location= "${baseurl}/";
// 		}
		//alert("baseurl:${baseurl}");没有项目名为空串
		//alert("baseUrl:"+baseUrl); baseUrl未定义
// 		if (top==self) {
// 			location.href = "${baseurl}/";
// 		} else {
// 			top.location.href = "${baseurl}/";
// 		}
		top.location.href = "${baseurl}/";
	}
</SCRIPT>
</HEAD>
<BODY style="background: #f6fdff url(${baseurl}/images/login/bg1.jpg) repeat-x;">
	<FORM id="loginform" name="loginform" action="${baseurl}/auth/login.action"
		method="post">
		<DIV class="logincon">

			<DIV class="title">
				<IMG alt="" src="${baseurl}/images/login/logo.png">
			</DIV>

			<DIV class="cen_con">
				<IMG alt="" src="${baseurl}/images/login/bg2.png">
			</DIV>

			<DIV class="tab_con">
				<input type="password" style="display:none;" />
				<TABLE class="tab" border="0" cellSpacing="6" cellPadding="8">
					<TBODY>
						<TR>
							<TD>用户名：</TD>
							<TD colSpan="2"><input type="text" id="usercode"
								name="usercode" style="WIDTH: 130px" /></TD>
						</TR>
						<TR>
							<TD>密 码：</TD>
							<TD><input type="password" id="password" name="pwd"  style="WIDTH: 130px" />
							</TD>
						</TR>
						<TR>
							<TD>验证码：</TD>
							<TD><input id="randomcode" name="validateCode" size="8" /> <img
								id="randomcode_img" src="${baseurl}/auth/validatecode.jsp" alt=""
								width="53" height="20" align='absMiddle' /> <a
								href=javascript:randomcode_refresh()>刷新</a></TD>
						</TR>

						<TR>
							<TD colSpan="2" align="center"><input type="button"
								class="btnalink" onclick="loginsubmit()" value="登&nbsp;&nbsp;录" />
								<input type="submit" style="display:none" onclick="loginsubmit();return false" />
								<input type="reset" class="btnalink" value="重&nbsp;&nbsp;置" /></TD>
						</TR>
					</TBODY>
				</TABLE>

			</DIV>
		</DIV>
	</FORM>
</BODY>
</HTML>
