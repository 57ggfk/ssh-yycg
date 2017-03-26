<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<html>
  <head>
    <title>医药集中采购系统</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<LINK rel="stylesheet" type="text/css" href="${baseurl}/js/easyui/styles/default.css">
	<%@ include file="/common/common_css.jsp"%>
	<%@ include file="/common/common_js.jsp"%>
	<%-- <%@ include file="/common/weather.jsp"%> --%>

<script type="text/javascript" src="${baseurl}/js/first.js"></script>
<script type="text/javascript">
	$(function() {
		//为按钮添加图标
		$('#submitbtn').linkbutton({   
			iconCls: 'icon-ok'  
		});  
		$('#recivebtn').linkbutton({   
			iconCls: 'icon-reload'  
		});
		//设置窗体属性
		$('#win').window({
			width : 400,
			height : 300,
			title : '修改密码',
			modal : true,
			closed : true,
			collapsible:false,
			maximizable:false,
			minimizable:false,
			resizable:false
		});
		//校验两次输入的密码一不一致
		$.extend($.fn.validatebox.defaults.rules, {    
		    equals: {    
		        validator: function(value,param){    
		            return value == $(param[0]).val();    
		        },    
		        message: '两次输入的密码不一致'   
		    }    
		});  
	});
	//点击修改打开窗口
	function changePwd() {
		//显示修改密码div
		$("#win").css("display","block")
		$('#win').window('open');
		$('#win').window('center');  
	}
	//重置功能
	function clearForm(){
		$("input").val("");
	}
	//提交功能
	function changePwd_Submit(){
		var result = $("#changeSubmit").form('validate');
		if (result) {
			jquerySubByFId("changeSubmit",changeSubmit_callback );
		}else{
			alert_warn("请正确填写表单!");
		}
	}
	//提交后的回调
	function changeSubmit_callback(data){
		message_alert(data,changeSubmit_message_callback);
	}
	//点击确定后的回调
	var i= 3;
	function changeSubmit_message_callback(){
		$.messager.show({
			title:'提示信息',
			msg:"系统将在"+i+"秒后跳转到登录页面。",
			timeout:5000,
			showType:'fade',
			style:{
				top:document.body.scrollTop+document.documentElement.scrollTop
			}
		});
		if(i==0){
			//执行退出请求
			location.href="${baseurl}/auth/logout.action"; 
		}
		$("#time").html(i--);
		setTimeout("changeSubmit_message_callback()", 1000);
}
	function help(){
		
		opentabwindow("使用帮助","${baseurl}/FAQ.jsp")
		//选中该选项卡
		
	}
	
	$(function(){
		$("#tianqi").html("<iframe name='weather_inc' src='tianqi.html' width='300' height='15' frameborder='0' marginwidth='0' marginheight='0' scrolling='no'></iframe>")
	})
	
	/* <iframe name="weather_inc" src="http://i.tianqi.com/index.php?c=code&id=11" width="500" height="20" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe> */
</script>
<META name="GENERATOR" content="MSHTML 9.00.8112.16540">
</HEAD>

<BODY style="overflow-y: hidden;" class="easyui-layout" scroll="no">
	<!--background: url("images/layout-browser-hd-bg.gif") repeat-x center 50% rgb(127, 153, 190);color: rgb(255, 255, 255);  -->
	<DIV style='height: 30px;' border="true" split="false" region="north">
	  <div class="panel-header" style="line-height: 20px;border:0px; padding:0px;margin:0px;height:100%;width:100% overflow: hidden; font-family: Verdana, 微软雅黑, 黑体;">
		<div style="padding-top:5px">
	
		<SPAN style="padding-right: 45px; float: right;">
			欢迎当前用户：<span style="color:red;">${activeUser.username }</span>&nbsp;&nbsp;
			<A onclick="help()" href="javascript:void(0);">使用帮助</A>
			&nbsp;&nbsp;
			<A id="changPwd" title='修改密码' href="javascript:void(0)"
			  icon='icon-null' onclick="changePwd()">修改密码</A> &nbsp;&nbsp;
			&nbsp;&nbsp;
			
			<A id="loginOut" href=javascript:logout()>退出系统</A>

		</SPAN> <SPAN style="padding-left: 10px; font-size: 16px;"><IMG
			align="absmiddle" src="images/blocks.gif" width="20" height="20">
			医药集中采购系统</SPAN> <SPAN style="padding-left: 15px;" id="News">
			<!-- 主题切换 -->主题设置
			<select id="changeThemeIdselect" class="changeTheme"></select>
			</SPAN></div>

			<center style="margin-top:-23px;padding-left: 30%">
					<span id="tianqi">
						<!-- <iframe name="weather_inc" src="http://i.tianqi.com/index.php?c=code&id=11" width="500" height="20" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe> -->
					</span>
			</center>
			
		</div>
	</DIV>
<!-- background: rgb(210, 224, 242);  -->
	<DIV style="height: 30px;" split="false"
		region="south">
		<DIV class="footer panel-header" style="border:0px;padding:0px;margin:0px;height:100%;width:100%">
			系统版本号：&nbsp;<span id="version_number" style="color: red">1.13</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发布日期：&nbsp;<span id="version_date" style="color: red">2016-12-23</span>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;页面载入时间:&nbsp;&nbsp;&nbsp;<span id="ymzrsj" style="color: red">计算中....</span>
		</DIV>
		<script>
		function setYmzrsj(data){
			$("#ymzrsj").html(data)
		}
			//版本号
			/* $("#version_number").html("1.13")
			//发布日期
			$("#version_date").html("2016-12-23") */
			//加载天气
			
		</script>
	</DIV>

	<DIV style="width: 180px;" id="west" title="导航菜单" split="true"
		region="west" hide="true">

		<DIV id="nav" class="easyui-accordion" border="false" fit="true">
			<!--  导航内容 -->
		</DIV>
	</DIV>

	<DIV style="background: rgb(238, 238, 238); overflow-y: hidden;"
		id="mainPanle" region="center">
		<DIV id="tabs" class="easyui-tabs" border="false" fit="true"></DIV>
	</DIV>

<!--显示修改密码窗口-->
	<div id="win" style="display:none; background-image: url('/images/login/login_mainbg.png');">
		<div align="center" style="font-family:'楷体'; font-size:30px;padding:40px;color:windowtext;">密 码 修 改</div>
		<div>
			<form id="changeSubmit" action="${baseurl}/user/realChangePwd.action" class="easyui-form" method="post">
				<div style="margin-bottom: 10px" align='center'>
					<label style="font-size: 15;color:maroon;">旧&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;码 :</label>&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="password" class="easyui-validatebox" name="oldPassword"
						 style="width: 40%;height:25px " data-options="required:true">
				</div>
				<div style="margin-bottom: 10px" align='center'>
					<label style="font-size: 15;color:maroon;">新&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;码 :</label>
					&nbsp;&nbsp;&nbsp;&nbsp; 
					<input id='newPwd' type="password" class="easyui-validatebox" name="newPassword"
						style="width: 40%;height: 25px" data-options="required:true">
				</div>
				<div style="margin-bottom: 10px" align='center'>
					<label style="font-size: 15;color:maroon;">确&nbsp;认&nbsp;密&nbsp;码 :</label>&nbsp;&nbsp;&nbsp;&nbsp;
					<input  type="password" class="easyui-validatebox" name="relPassword"
						style="width: 40%;height: 25px" data-options="required:true" validType= "equals['#newPwd']">
				</div>
			</form>
			<div style="text-align: center; ">
				<a id='submitbtn' href="javascript:void(0)" class="easyui-linkbutton" onclick="changePwd_Submit()"
					style="width: 70px; height: 30px; font-size: 20">提&nbsp;&nbsp;&nbsp;交</a>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a id='recivebtn' href="javascript:void(0)" class="easyui-linkbutton" 
					style="width: 70px; height: 30px;" onclick="clearForm()">重&nbsp;&nbsp;&nbsp;置</a>
			</div>
		</div>
	</div>
<script type="text/javascript">
</script>
</BODY>
</HTML>

