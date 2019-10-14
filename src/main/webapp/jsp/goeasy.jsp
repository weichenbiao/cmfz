<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; utf-8" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <script src="../boot/js/jquery-3.3.1.min.js"></script>
    <script>
        $(function () {
            var goEasy = new GoEasy({
                appkey: "BC-d2595c81a10248f59ec9fbb788f8b780"
            });
            goEasy.subscribe({
                channel: "wcb",
                onMessage: function (message) {
                    $("#ss").append(message.content + "<br/>")

                }
            });
            $("#btn").click(function () {
                $.ajax({
                    type: "post",
                    datetype: "json",
                    data: {"inp": $("#in").val()},
                    url: "${pageContext.request.contextPath}/user/go",
                    success: function (data) {
                        $("#in").val("")
                    }
                })
            })

            $("#btn1").click(function () {
                $("#ss").text("")
            })
        })
    </script>

</head>
<body>

<div align="center">
    <h1>hello!!</h1>
    <br/>
    <input id="in" type="text" name="inp" style="width: 280px;height: 25px">
    <button id="btn">发送</button>
    <button id="btn1">清空</button>
    <br/>
    <hr>
    <h3 id="ss"></h3>

</div>


</body>
</html>