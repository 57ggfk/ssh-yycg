<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>zTree test</title>
<link rel="stylesheet"
	href="${baseurl}/js/zTree_v3/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<%@ include file="/common/common_css.jsp"%>
<%@ include file="/common/common_js.jsp"%>

<script type="text/javascript"
	src="${baseurl}/js/zTree_v3/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="${baseurl}/js/zTree_v3/jquery.ztree.excheck-3.5.js"></script>

<script type="text/javascript">

</script>

</head>
<body>
<!-- 	<table id="tt" class="easyui-treegrid" style="width:600px;height:400px"   
        data-options="url:'get_data.php',idField:'id',treeField:'name'">   
    <thead>   
        <tr>   
            <th data-options="field:'name',width:180">Task Name</th>   
            <th data-options="field:'persons',width:60,align:'right'">Persons</th>   
            <th data-options="field:'begin',width:80">Begin Date</th>   
            <th data-options="field:'end',width:80">End Date</th>   
        </tr>   
    </thead>   
</table>  --> 



<table id="tt" style="width:600px;height:400px"></table>
	
	<script type="text/javascript">
	$(function() {
		
		$('#tt').treegrid({    
		    url:'get_data.php',    
		    idField:'id',    
		    treeField:'name',    
		    columns:[[    
		        {title:'Task Name',field:'name',width:180},    
		        {field:'persons',title:'Persons',width:60,align:'right'},    
		        {field:'begin',title:'Begin Date',width:80},    
		        {field:'end',title:'End Date',width:80}    
		    ]]    
		});  


	});
	
	</script>
</body>
</html>