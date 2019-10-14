<%@page contentType="text/html; utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    var goEasy = new GoEasy({
        appkey: "BC-d2595c81a10248f59ec9fbb788f8b780"
    });

    $(function () {
        var pubMsg = "";

        $("#message-send").click(function () {
            var msg = $("#message-input").val();
            pubMsg = msg;
            goEasy.publish({
                channel: "lts",
                message: msg,
                onSuccess: function () {
                    var msgDiv = $(
                        "<div style='float: right;background: #b2dba1;'>" +
                        msg +
                        "</div><br/><br/>"
                    );
                    $("#message-show").append(msgDiv);
                }

            });
            $("#message-input").val("");
        });

        goEasy.subscribe({
            channel: "lts",
            onMessage: function (message) {
                var subMsg = message.content;
                if (pubMsg != subMsg) {
                    var msgDiv = $(
                        "<div style='float: left;background: gray'>" +
                        subMsg +
                        "</div><br/><br/>"
                    );
                    $("#message-show").append(msgDiv);
                } else {
                    pubMsg = ""
                }


            }
        });

        $("#message-del").click(function () {
            $("#message-show").html("")
        })
    });
</script>

<div style="width: 800px; height: 600px;border: solid red 1px ">
    <div id="message-show" style=" overflow-y:auto; width: 800px; height: 400px;border: solid blue 1px "></div>

    <div style="width: 800px; height: 200px;border: solid green 1px ">
        <textarea id="message-input" style="width: 798px;height: 150px"></textarea>

        <button id="message-send" style="float: right">发送</button>&nbsp;<button id="message-del">清空</button>

    </div>
</div>