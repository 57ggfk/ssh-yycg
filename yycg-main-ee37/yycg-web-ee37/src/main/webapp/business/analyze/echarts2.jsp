<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<head>
<meta charset="utf-8">
<%@ include file="/common/common_css.jsp"%>
<%@ include file="/common/common_js.jsp"%>
<title>ECharts</title>
</head>

<body>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 600px;height:400px;"></div>
    <script type="text/javascript" src="echarts/dist/echarts.js"></script>
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
       // var myChart = echarts.init(document.getElementById('main'));

     	 //定义数据
		var rowKeys = ["销量"]//大概就是柱状图的描述(需要统计数据的哪个属性值)
		var dataSet = ["河北","北京"]//大概就是柱状图的数据(省份名称)
		var dataSumSet = [100]//大概就是对应柱状图数据的数量(就是每个省份的销量)
		//${baseurl}/analyze/test.action
        
		var myChart = echarts.init(document.getElementById('main'));
			// 显示标题，图例和空的坐标轴
			myChart.setOption({
			    title: {
			        text: '统计'
			    },
			    tooltip: {
			    	data:["销量"]
			    },
			    legend: {//
			        data:["标题"]
			    },
			    xAxis: {//数据集合
			        data: ["河北","北京"]
			    
			    },
			    yAxis: {
			    	name:["列名"]//列名
			    },
			    series: [{//需要统计的字段
			        name: '销量悬浮',
			        type: 'bar',
			        data: [15,20]//数据
			    }]
			});
			
			// 异步加载数据
			/* $.get('${baseurl}/analyze/test.action').done(function (data) {
				alert(data)
			    // 填入数据
			    myChart.setOption({
			        xAxis: {
			            data: data.dqName
			        },
			        series: [{
			            // 根据名字对应到相应的系列
			            name: '销量',
			            data: data.dqZje
			        }]
			    });
			}); */
    </script>
</body>
</html>