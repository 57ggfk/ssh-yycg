<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>布局</title>
<%@include file="/common/common_css.jsp" %>
<%@include file="/common/common_js.jsp" %>
</head>
<body>  
<div id="p" class="easyui-panel"
        style="width:100%;height:0;padding:0px;"   
        data-options="border:false">   
   <header>面板标题</header>
  
    <p>panel content.</p>  
    <footer>面板页脚</footer> 
</div>  
<div id="p" class="easyui-panel" title="My Panel"     
        style="width:100%;height:0;padding:0px;"   
        data-options="border:false;">   
   <header>面板标题</header>
  
    <p>panel content.</p>  
    <footer>面板页脚</footer> 
</div>
</body>
</html>