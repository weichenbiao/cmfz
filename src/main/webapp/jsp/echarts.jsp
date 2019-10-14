<%@page isELIgnored="false" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="app" value="${pageContext.request.contextPath}"/>
<%--折线图--%>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="../boot/js/echarts.min.js"></script>
    <script src="../boot/js/china.js"></script>
</head>
<body>
<div id="main" style="width: 1200px;height:600px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '近一周注册量'
        },
        tooltip: {},
        legend: {
            data: ['注册量']
        },
        xAxis: {
            data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子", "sad"]
        },
        yAxis: {},
        series: [{
            name: '注册量',
            type: 'line',
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);


    $.ajax({
        url: "${pageContext.request.contextPath}/user/queryDate",
        datatype: "json",
        type: "POST",
        success: function (da) {
            myChart.setOption({
                xAxis: {data: da.bb},
                series: [{data: da.aa}]
            });
        }
    })


</script>

</body>
</html>