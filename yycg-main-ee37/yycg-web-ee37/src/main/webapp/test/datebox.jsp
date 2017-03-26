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
	<script type="text/javascript">
	</script>
</head>
<body>
	<table>
		<tr>
			<td>Start Date:</td>
			<td>
				<input class="easyui-datebox" data-options="sharedCalendar:'#cc'">
			</td>
			<td>End Date:</td>
			<td>
				<input class="easyui-datebox" data-options="sharedCalendar:'#cc'">
			</td>
		</tr>
	</table>
	<div id="cc" class="easyui-calendar"></div>
	
</body>
</html>