<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Live Chat</title>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>
</head>
<body>
    <h2>Chat Room</h2>

    <input type="text" id="message" placeholder="Type your message." />
    <button onclick="sendMessage()">Send</button>

    <div id="chat-box"></div>

    <script>
        let stompClient = null;
        const senderId = 1; // Replace with logged-in user id
        const conversationId = 123; // Replace with real conversation id

        function connect() {
            const socket = new SockJS('/ws');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function () {
                stompClient.subscribe(`/topic/chatroom/${conversationId}`, function (message) {
                    const msg = JSON.parse(message.body);
                    document.getElementById('chat-box').innerHTML += `<p><b>${msg.senderName}:</b> ${msg.content}</p>`;
                });
            });
        }

        function sendMessage() {
            const content = document.getElementById("message").value;
            stompClient.send("/app/send-message", {}, JSON.stringify({
                conversationId: conversationId,
                content: content
            }));
            document.getElementById("message").value = "";
        }

        connect();
    </script>
</body>
</html>
