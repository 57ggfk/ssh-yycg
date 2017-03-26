<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>欢迎访问市级医药采集系统</title>
	<link rel="stylesheet" type="text/css" href="css/normalize.css" />
	<link rel="stylesheet" type="text/css" href="css/htmleaf-demo.css">
	<style type="text/css">
		* {
			margin: 0;
			padding: 0;
		}
		body{
			background-image:url('images/snow.jpg');
			background-size:cover;
			background-repeat: no-repeat;
			background-color:#222;
			position: relative;
			width:100%;
			height:100%;
			overflow-x: hidden;
		}
	</style>
</head>
<body>
	<div class="htmleaf-container">
		<header class="htmleaf-header">
			<h1>欢迎访问市级医药采集系统</h1>
			<div class="htmleaf-links">
				<a class="htmleaf-icon icon-htmleaf-home-outline" href="#" title="首页"><span>首页</span></a>
				<a class="htmleaf-icon icon-htmleaf-arrow-forward-outline" href="#" title="返回"><span> 返回下载页</span></a>
			</div>
		</header>
	</div>
	
	<script src="js/ThreeCanvas.js"></script>
	<script>window.jQuery || document.write('<script src="js/jquery-1.11.0.min.js"><\/script>')</script>
	<script src="js/Snow.js"></script>
	<script src="js/snowFall.js"></script>
	<script type="text/javascript">
		$.snowFall({
			//创建粒子数量，密度
			particleNo: 600,
			//粒子下拉速度
			particleSpeed:30,
			//粒子在垂直（Y轴）方向运动范围
			particleY_Range:1300,
			//粒子在垂直（X轴）方向运动范围
			particleX_Range:1000,
			//是否绑定鼠标事件
		    bindMouse: true,
		    //相机离Z轴原点距离
		    zIndex:300,
		  //摄像机视野角度
		    angle:60,
		    wind_weight:20
		});
		
		 document.documentElement.style.overflow="hidden";
	     document.documentElement.style.paddingRight="17px";
	     document.documentElement.style.width="100%";
		
	</script>
</body>
</html>