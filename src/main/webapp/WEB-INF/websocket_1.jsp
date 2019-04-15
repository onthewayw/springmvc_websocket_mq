<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"></meta>
    <title>websocket+redis集群</title>
</head>
<input type="hidden" id="port" value='${port }'>
<input type="hidden" id="topic" value='${topic }'>
<input type="hidden" id="username" value='${username }'>
<body>
${topic} 频道 聊天中。。。<br/>
<input id="input_id" type="text" /><button onclick="sendMessage()">发送</button>    <button onclick="closeWebsocket()">关闭</button>
<div id="message_id"></div>
</body>
<script>
    var topic = document.getElementById("topic").value;
    var username = document.getElementById("username").value;
    var websocket = new WebSocket('ws://127.0.0.1:8081/im_webSocket/'+topic+'/'+username);
    console.log(websocket);
    websocket.onopen = function(event){
        setMessage("打开连接");
    }

    websocket.onclose = function(event){
        setMessage("关闭连接");
    }

    websocket.onmessage = function(event){
        setMessage(event.data);
    }

    websocket.onerror = function(event){
        setMessage("连接异常");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function(){
        closeWebsocket();
    }

    //关闭websocket
    function closeWebsocket(){
        //3代表已经关闭
        if(3!=websocket.readyState){
            websocket.close();
        }else{
            alert("websocket之前已经关闭");
        }
    }

    //将消息显示在网页上
    function setMessage(message){
        document.getElementById('message_id').innerHTML += message + '<br/>';
    }

    //发送消息
    function sendMessage(){
        //1代表正在连接
        if(1==websocket.readyState){
            var message = document.getElementById('input_id').value;
            //setMessage(message);
            websocket.send(message);
        }else{
            alert("websocket未连接");
        }
        document.getElementById('input_id').value="";
        document.getElementById('input_id').focus();
    }
</script>
</html>