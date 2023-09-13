package com.kingofboss.backend.consumer;

import com.kingofboss.backend.consumer.utils.JwtAuthentication;
import com.kingofboss.backend.mapper.UserMapper;
import com.kingofboss.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {

    // 设置一个后端中所有线程可见的全局变量
    private static ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>();
    private User user;
    private Session session = null;

    // websocker 不是标准的 springboot组件，需要先定义一个static变量注入
    // 不是单例， 因为每建立一个链接都会建立一个实例
    private static UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        WebSocketServer.userMapper = userMapper;
    }


    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        // 建立连接
        this.session = session;
        System.out.println("connect!");
        Integer userId = JwtAuthentication.getUserId(token);
        this.user = userMapper.selectById(userId);

        if(this.user != null) {
            users.put(userId, this);
        } else {
            this.session.close();  // 此处需要抛异常
        }

        System.out.println(user);
    }

    @OnClose
    public void onClose() {
        // 关闭链接
        System.out.println("disconnect!");
        if (this.user != null) {
            users.remove(this.user.getId());
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // 从Client接收消息
        System.out.println("receive message!");

    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    //  从后端向当前链接发送信息，session 不能为 final
    public void sendMessage(String message) {
        synchronized (this.session) {
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
