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
//保存采购单
function yycgdsave(){
	//ajaxForm提交
	jquerySubByFId('yycgdform',yycgdsave_callback,null,"json");
}
//回调
function yycgdsave_callback(result){
	_alert(result);
	//提交成功跳转到修改页面
	if(result.resultInfo.type=='1'){
		//alert("跳转到修改页面");
		window.location="${baseurl}/cgd/edit.action?yycgdCustom.id="+result.resultInfo.sysdata.yycgdid;
	}
}
//删除选项卡
function closeOption() {
	parent.closetab("创建采购单");
	//console.log(parent.document.URL);
}
</script>
<title>添加采购单</title>
</head>
<body>
<form id="yycgdform" action="${baseurl }/cgd/add_submit.action" method="post">
<!-- 添加采购单表格 -->
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>
	<TBODY>
		<TR>
				<TD background="" width="100%">
					<TABLE cellSpacing=0 cellPadding=0 width="100%">
						<TBODY>
							<TR>
								<TD>&nbsp;添加采购单</TD>
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
								<TD height=30 width="15%" align=right >采购单号：</TD>
								<TD class=category width="35%"> 
								
								</TD>
								<TD height=30 width="15%" align=right >采购单名称：</TD>
								<TD class=category width="35%">
								<div>
								<input type="text" id="yycgd_id" name="yycgdCustom.mc"  value="${yycgdCustom.mc}" readonly="readonly"  style="width:260px" />
								</div>
								<div id="yycgd_idTip"></div>
								</TD>
							</TR>
							<TR>
							   <TD height=30 width="15%" align=right >建单时间：</TD>
								<TD class=category width="35%">
									
								</TD>
								<TD height=30 width="15%" align=right >提交时间：</TD>
								<TD class=category width="35%">
								
								</TD>
								
							</TR>
							<TR>
								<TD height=30 width="15%" align=right>联系人：</TD>
								<TD class=category width="35%">
								<input type="text" name="yycgdCustom.lxr" id="yycgdCustom.lxr"  style="width:260px" />
								</TD>
								<TD height=30 width="15%" align=right >联系电话：</TD>
								<TD class=category width="35%">
								<input type="text" name="yycgdCustom.lxdh" id="yycgdCustom.lxdh" style="width:260px" />
								</TD>
							</TR>
							<TR>
								<TD height=30 width="15%" align=right>采购单状态：</TD>
								<TD class=category width="35%">
								
								</TD>
								<TD height=30 width="15%" align=right>备注：</TD>
								<TD colspan=3>
									<textarea rows="2" cols="30" name="yycgdCustom.bz"></textarea>
								</TD>
							</TR>
							
							<TR>
								<TD height=30 width="15%" align=right>审核时间：</TD>
								<TD class=category width="35%">
								
								</TD>
								<TD height=30 width="15%" align=right >审核意见：</TD>
								<TD class=category width="35%">
								
								</TD>
							</TR>
							<tr>
							  <td colspan=4 align=center class=category>
								<a id="submitbtn" href="#" onclick="yycgdsave()">提交</a>
								<a id="closebtn" href="#" onclick="closeOption()">关闭</a>
								<!-- <a id="closebtn" href="#" onclick="closeOption()">关闭</a> -->
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