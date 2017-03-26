<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<html>
  <head>
    <title>修改用户</title>
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
			
			var dictcode = $(this.options[this.selectedIndex]).data("dictcode");
			//alert(dictcode); //可以正常弹出
			if (!dictcode) {
				dictcode="";
			}
			showDwmc(dictcode);
		});
		
		var dictcode = "${sysUserCustom.sysDictInfoByGroupid.dictcode}";
		showDwmc(dictcode);
	});
	var dwid;
	//根据dictcode查询所属单位
	function showDwmc(dictcode) {
		//如果没有传dictcode就设置为""
		if (!dictcode) {
			dictcode="";
		}
		
		//判断value为""或者dictcode为"0"就不级联查询
		//隐藏所属单位列表
		//$("#dwmcListId").css('display','none');
		$dwmc = $("#dwmcListId");
		$("#dwmcListId").hide();
		if (dictcode=="" || dictcode== "0"){ //如果是管理员，或者未选择
			//$("#dwmcListId").hide; //调用方法一定要带()
			//清空子元素
			$("#dwmcListId").empty();
			$("#dwmcListId").removeAttr("name"); //移除name属性
			$("#loading").hide(); //loading图片也不显示
		} else { //否则就级联查询
			//先设置所属单位的name值
			if (dictcode=="1" || dictcode=="2"){
				$dwmc.attr("name","sysUserCustom.dwWsy.id");
				dwid = "${sysUserCustom.dwWsy.id}";
			} else if (dictcode=="3") {
				$dwmc.attr("name","sysUserCustom.dwWss.id");
				dwid = "${sysUserCustom.dwWss.id}";
			} else if (dictcode=="4") {
				$dwmc.attr("name","sysUserCustom.dwGys.id");
				dwid = "${sysUserCustom.dwGys.id}";
			}
		
			//显示loading图片
			$("#loading").show();
			//开始查询
			var url = "${baseurl}/user/findDwByDictcode.action";
			var params = {"sysUserQueryCustom.sysDictInfoByGroupid.dictcode":dictcode};
			$.post(url,params,function(data){
				$dwmc = $("#dwmcListId");
				//隐藏loading图片
				$("#loading").hide();
				
				//重置所属单位列表id为dwmcListId
				//$("#dwmcListId").empty();
				$dwmc.html("<option value=''>--请选择--</option>");
				
				//添加数据到所属单位列表
				$.each(data,function(){
					if (this) { //this代表data的每个元素，如果存在...
						$("<option></option>").val(this.id).text(this.mc).appendTo($dwmc);
					}
				});
				$dwmc.children("[value='"+dwid+"']").prop("selected",true); //加上单引号，防止dwid为""
				//显示所属单位列表
				//$dwmc.css('display','block');
				
				$dwmc.show('slow');
				
			},"json");
			
		}
	}
	
	function sysusersave(){
		//if($.formValidator.pageIsValid()){
			//使用封装js执行ajaxform提交。
			//参数：1、form的id，form的action配置url
			//2：回调方法
			//4：json表示 预期服务端响应的结果类型
			//5：async: false ,false表示同步提交，true表示异步提交，默认是true
			
			jquerySubByFId('sysusereditform',sysusersave_callback,null,"json");
			//当jquerySubByFId下边的代码需要在jquerySubByFId方法执行后来执行，采用同步
			//当jquerySubByFId下边的代码不依赖jquerySubByFId方法执行结果，可以采用异步，如果采用异步，单独启动一个线程执行ajax请求
			//alert(result);
		//}
		
	}
	//data是服务端响应的结果（json）,是一个固定的参数
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
<form id="sysusereditform" name="sysusereditform" action="${baseurl}/user/edituser_submit.action" method="post" >
<input type="hidden" name="sysUserCustom.id" value="${sysUserCustom.id}"/>
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
								<input type="text" id="sysuser_usercode" name="sysUserCustom.usercode" value="${sysUserCustom.usercode}"   />
								</div>
								<div id="sysuser_usercodeTip"></div>
								</TD>
								<TD height=30 width="15%" align=right >用户名称：</TD>
								<TD class=category width="35%">
								<div>
								<input type="text" id="sysuser_username" name="sysUserCustom.username" value="${sysUserCustom.username}"   />
								</div>
								<div id="sysuser_usernameTip"></div>
								</TD>
							</TR>
							
							
							<TR>
								<TD height=30 width="15%" align=right >用户密码：</TD>
								<TD class=category width="35%">
								<div>
									<input type="password" id="sysuser_password" name="sysUserCustom.pwd" value=""/>
								</div>
								<div id="sysuser_passwordTip"></div>
								</TD>
								<TD height=30 width="15%" align=right >用户类型：</TD>
								<TD class=category width="35%">
								<div>
								<select name="sysUserCustom.sysDictInfoByGroupid.id" id="sysuser_groupid">
									<option value="">请选择</option>
									<c:forEach items="${userGroupList}" var="dictinfo">
									   <option value="${dictinfo.id}" <c:if test="${sysUserCustom.sysDictInfoByGroupid.id== dictinfo.id}">selected</c:if> 
									   		data-dictcode="${dictinfo.dictcode}" >${dictinfo.info}</option>
									</c:forEach>
								</select>
								</div>
								<div id="sysuser_groupidTip"></div>
								</TD>
								
							</TR>
							<TR>
							    <TD height=30 width="15%" align=right >用户单位名称：</TD>
								<TD class=category width="35%">
									<img id="loading" alt="loading" src="${baseurl}/js/easyui/themes/default/images/loading.gif" style="display:none"/>
									<select id="dwmcListId" name="" style="display:none">
								</TD>
								<TD height=30 width="15%" align=right>用户状态：</TD>
								<TD class=category width="35%">
									<c:forEach items="${userStateList}" var="dictinfo">
									   <input type="radio" name="sysUserCustom.sysDictInfoByUserstate.id" value="${dictinfo.id}" <c:if test="${sysUserCustom.sysDictInfoByUserstate.id== dictinfo.id}">checked</c:if>/>${dictinfo.info}
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

</BODY>
</HTML>

