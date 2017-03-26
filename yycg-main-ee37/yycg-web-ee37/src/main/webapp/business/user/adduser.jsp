<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<html>
  <head>
    <title>添加用户</title>
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
		
		//绑定事件，选择用户类别，级联查询所属单位
		$("#sysuser_groupid").change(function(){
			//获取dictcode
			//this代表当前select元素
			//this.options代表select的字元素option数组,this.selectedIndex代表被选择的索引
			$dwmc = $("#dwmcListId");
			var dictcode = $(this.options[this.selectedIndex]).data("dictcode");
			//alert(dictcode); //可以正常弹出
			//判断value为""或者dictcode为"0"就不级联查询
			if (this.value=="" || dictcode== "0"){
				//$("#dwmcListId").hide; //调用方法一定要带()
				
				//隐藏所属单位列表
				//$("#dwmcListId").css('display','none');
				$("#dwmcListId").hide("slow");
				//清空子元素
				$("#dwmcListId").empty();
				$("#dwmcListId").attr("name","");
			} else { //否则就级联查询
				//先设置所属单位的name值
				if (dictcode=="1" || dictcode=="2"){
					$dwmc.attr("name","sysUserCustom.dwWsy.id");
				} else if (dictcode=="3") {
					$dwmc.attr("name","sysUserCustom.dwWss.id");
				} else if (dictcode=="4") {
					$dwmc.attr("name","sysUserCustom.dwGys.id");
				}
				//开始查询
				var url = "${baseurl}/user/findDwByDictcode.action";
				var params = {"sysUserQueryCustom.sysDictInfoByGroupid.dictcode":dictcode};
				$.post(url,params,function(data){
					$dwmc = $("#dwmcListId");
					
					//重置所属单位列表id为dwmcListId
					//$("#dwmcListId").empty();
					$dwmc.html("<option value=''>--请选择--</option>");
					
					//添加数据到所属单位列表
					$.each(data,function(){
						if (this) { //this代表data的每个元素，如果存在...
							$("<option></option>").val(this.id).text(this.mc).appendTo($dwmc);
						}
					});
					
					//显示所属单位列表
					//$dwmc.css('display','block');
					$dwmc.show('slow');
					
				},"json");
				
			}
		});
		
		
		
	}); //$(function(){}) 的结束
		
	//执行用户保存
	function sysusersave() {
		jquerySubByFId("sysuseraddform",sysusersave_callback);
	}
	
	//保存用户后的回调
	function sysusersave_callback(data){
		message_alert(data,function(){
			//返回成功信息的回调
			//从父层关闭本窗口
			parent.closemodalwindow();
			//刷新父iframe的datagrid
			parent.sysuserquery();
		});
	}
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
								<div>
								<input type="text" id="sysuser_usercode" name="sysUserCustom.usercode" />
								</div>
								<!-- 失败信息提示区域 sysuser_usercodeTip，命名规则：input的id + Tip-->
								<div id="sysuser_usercodeTip"></div>
								</TD>
								<TD height=30 width="15%" align=right >用户名称：</TD>
								<TD class=category width="35%">
								<div>
								<input type="text" id="sysuser_username" name="sysUserCustom.username"  />
								</div>
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
								<div id="sysuser_passwordTip"></div>
								</TD>
								<TD height=30 width="15%" align=right >用户类型：</TD>
								<TD class=category width="35%">
								<div>
								<select name="sysUserCustom.sysDictInfoByGroupid.id" id="sysuser_groupid">
									<option value="">请选择</option>
									<c:forEach items="${userGroupList}" var="dictinfo">
									   <option value="${dictinfo.id}" 
									   		<c:if test="${sysUserCustom.sysDictInfoByGroupid.id== dictinfo.id}">selected</c:if>
									   		data-dictcode="${dictinfo.dictcode}" >${dictinfo.info}
									   </option>
									</c:forEach>
									
								</select>
								</div>
								<div id="sysuser_groupidTip"></div>
								</TD>

							</TR>
							<TR>
							    <TD height=30 width="15%" align=right >用户单位名称：</TD>
								<TD class=category width="35%">
								<select id="dwmcListId" name="" style="display:none">
								</select>
								</TD>
								<TD height=30 width="15%" align=right>用户状态：</TD>
								<TD class=category width="35%">
								<c:forEach items="${userStateList}" var="dictinfo" varStatus="status">
<%-- 								<c:if test="${sysUserCustom.sysDictInfoByUserstate.id== dictinfo.id}">checked</c:if> --%>
								   <input type="radio" name="sysUserCustom.sysDictInfoByUserstate.id" value="${dictinfo.id}" <c:if test="${status.count==1}">checked</c:if>/>${dictinfo.info}
								</c:forEach>								
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

