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
        var myChart = echarts.init(document.getElementById('main'));

     	 //定义数据
		var rowKeys = ["销量"]//大概就是柱状图的描述(需要统计数据的哪个属性值)
		var dataSet = ["河北","北京"]//大概就是柱状图的数据(省份名称)
		var dataSumSet = [100]//大概就是对应柱状图数据的数量(就是每个省份的销量)
        
		$(function() {
			
			$.ajax({
				type:"post",
				url:"${baseurl}/analyze/test.action",
				dataType:"json",
				success:function(data){
					//把数据添加到数组
					$.each(data,function(i,n){
						for(var i = 0;i<data.length;i++){
							dataSet[i] = n.dqName;
							dataSum[i] = n.dqZje;
						}
					})
					
					
					
				}
			})
			
			
		})
        
// 指定图表的配置项和数据
	        var option = {
	            title: {
	                text: 'ECharts 入门示例'
	            },
	            tooltip: {},
	            legend: {
	                data:rowKeys
	            },
	            xAxis: {
	                data: dataSet
	            },
	            yAxis: {},
	            series: [{
	                name: '销量',
	                type: 'bar',
	                data: dataSumSet
	            }]
	        }; 
        myChart.setOption(option);
		
        // 使用刚指定的配置项和数据显示图表。
    </script>
</body>
</html>