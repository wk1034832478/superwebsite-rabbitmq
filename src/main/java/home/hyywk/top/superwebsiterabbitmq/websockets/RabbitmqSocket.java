package home.hyywk.top.superwebsiterabbitmq.websockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Scanner;

@Component
@ServerEndpoint("/test")
public class RabbitmqSocket {

    private Logger logger = LoggerFactory.getLogger( RabbitmqSocket.class );

    public RabbitmqSocket() {
        this.logger.info( "websocket正在初始化" );
    }

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("sid") String sid) {
        try {
            session.getBasicRemote().sendText("连接成功! 欢迎来到websocket聊天室，这里有rabbitmq的集成。");
        } catch (IOException e) {
            this.logger.error("websocket IO异常");
        }
    }

    @OnMessage
    public void onMessage( String message, Session session ) throws IOException, InterruptedException {
        while ( true ) {
            Scanner scanner = new Scanner(System.in);
            this.logger.info( "收到新的信息" );
            session.getBasicRemote().sendText( "hello" );
            session.getBasicRemote().sendText( scanner.next() );
        }
    }
}
