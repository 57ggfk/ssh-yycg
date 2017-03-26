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
$(function (){
	//***********按钮**************
	$('#submitbtn').linkbutton({   
		iconCls: 'icon-ok'  
	});  
	$('#closebtn').linkbutton({   
		iconCls: 'icon-cancel'  
	});
});

//采购单审核提交
	function checksubmit() {
		_confirm('您确定要提交审核吗?', function() {
			// $("#yycgdcheckForm").attr("action","${baseurl }/cgd/check_submit.action");
			jquerySubByFId("yycgdcheckForm", yycgdcheck_callback);
		});
	}
	/**
	 * 提交成功后回调
	 */
	function yycgdcheck_callback(data) {
		message_alert(data,function(){
		parent.closemodalwindow();
		parent.yycgdquery();
		});
	}
</script>
</HEAD>
<BODY>

<form id="yycgdcheckForm" name="yycgdcheckForm" action="${baseurl}/cgd/check_submit.action" method="post">
<input type="hidden" name="yycgdCustom.id" value="${yycgdCustom.id }"/>
<!-- 任务id -->
<%-- <input type="hidden" name="taskId" value="${taskId}"/> --%>
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed align=center>
		<TBODY>
			<TR>
				<TD width="100%">
					<TABLE cellSpacing=0 cellPadding=0 width="100%">
						<TBODY>
							<TR>
								<TD>&nbsp;药品采购单审核</TD>
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
							    
								<TD height=30 width="15%" align=right>采购单号：</TD>
								<TD class=category width="35%">
								${yycgdCustom.id }
								
								</TD>
								<TD height=30 width="15%" align=right >采购单名称：</TD>
								<TD class=category width="35%">
								${yycgdCustom.mc }
								</TD>
							</TR>
							<TR>
							   <TD height=30 width="15%" align=right >建单时间：</TD>
								<TD class=category width="35%">
									<fmt:formatDate value="${yycgdCustom.cjsj}" pattern="yyyy-MM-dd"/>
								</TD>
								<TD height=30 width="15%" align=right >提交时间：</TD>
								<TD class=category width="35%">
								<fmt:formatDate value="${yycgdCustom.tjsj}" pattern="yyyy-MM-dd"/>
								</TD>
								
							</TR>
							<TR>
								<TD height=30 width="15%" align=right>联系人：</TD>
								<TD class=category width="35%">
								${yycgdCustom.lxr}
								</TD>
								<TD height=30 width="15%" align=right >联系电话：</TD>
								<TD class=category width="35%">
								${yycgdCustom.lxdh}
								</TD>
							</TR>
							<TR>
								<TD height=30 width="15%" align=right>采购单状态：</TD>
								<TD class=category width="35%">
								${yycgdCustom.sysDictInfoByzt.info}
								</TD>
								<TD height=30 width="15%" align=right>备注：</TD>
								<TD class=category width="35%">
									${yycgdCustom.bz}
								</TD>
							</TR>
							
							<TR>
								<TD height=30 width="15%" align=right>审核结果：</TD>
								<TD class=category width="35%" >
								     <input type="radio" name="yycgdCustom.sysDictInfoByzt.id" value="01003"/>审核通过
								     <input type="radio" name="yycgdCustom.sysDictInfoByzt.id" value="01004"/>审核不通过
								</TD>
								<TD height=30 width="15%" align=right >审核意见：</TD>
								<TD class=category width="35%">
								   <textarea rows="2" cols="30" name="yycgdCustom.shyj"></textarea>
								</TD>
							</TR>
							
						
							
							<tr>
							  <td colspan=4 align=center class=category>
								<a  href="javascript:void(0)" onclick="checksubmit()" class="easyui-linkbutton" iconCls='icon-save'>提交</a>
								<a id="closebtn" href="javascript:void(0)" onclick="parent.closemodalwindow()">关闭</a>
							  </td>
							</tr>
						</TBODY>
					</TABLE>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</form>

</BODY>
</HTML>

