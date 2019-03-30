package home.hyywk.top.superwebsiterabbitmq.websockets;
import home.hyywk.top.superwebsiterabbitmq.entity.Message;
import home.hyywk.top.superwebsiterabbitmq.entity.MessageCode;
import home.hyywk.top.superwebsiterabbitmq.entity.MessageType;
import home.hyywk.top.superwebsiterabbitmq.services.Processor;
import home.hyywk.top.superwebsiterabbitmq.util.JsonUtil;
import home.hyywk.top.superwebsiterabbitmq.util.MyApplicationContextAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpSession;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Component
@ServerEndpoint("/api/chatting")
public class WebSocketCentral {

    private Logger logger = LoggerFactory.getLogger( WebSocketCentral.class );

    private Processor processor;

    public WebSocketCentral() {
        this.logger.info( "websocket正在初始化" );
        processor = MyApplicationContextAware.getApplicationContext().getBean( Processor.class );
    }

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session) { // ,@PathParam("sid") String sid
        this.logger.info( "新的会话" );
        this.logger.info( processor.toString() );
        processor.addSession( session ); // 添加指map当中
    }

    @OnMessage
    public void onMessage( String message, Session session ) throws IOException, InterruptedException {
        this.logger.info( "收到消息： {}", message );
        this.processor.process(  JsonUtil.fromStringToObject( message, Message.class ) );
    }

    @OnClose
    public void onClose( Session session ) {
    }


}
