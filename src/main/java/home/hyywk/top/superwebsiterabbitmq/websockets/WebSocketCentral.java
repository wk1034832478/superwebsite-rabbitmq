package home.hyywk.top.superwebsiterabbitmq.websockets;
import home.hyywk.top.superwebsiterabbitmq.config.HttpSessionConfigurator;
import home.hyywk.top.superwebsiterabbitmq.entity.Message;
import home.hyywk.top.superwebsiterabbitmq.entity.SystemMessages;
import home.hyywk.top.superwebsiterabbitmq.processors.Processor;
import home.hyywk.top.superwebsiterabbitmq.util.JsonUtil;
import home.hyywk.top.superwebsiterabbitmq.util.MyApplicationContextAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint( value = "/api/chatting", configurator = HttpSessionConfigurator.class)
public class WebSocketCentral {

    private Logger logger = LoggerFactory.getLogger( WebSocketCentral.class );

    private Processor processor;

    private HttpSession httpSession;

    public WebSocketCentral() {
        this.logger.info( "websocket正在初始化" );
        processor = MyApplicationContextAware.getApplicationContext().getBean( Processor.class );
    }

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) { // ,@PathParam("sid") String sid
        this.logger.info( "新的会话" );
        this.httpSession = (HttpSession)config.getUserProperties().get( HttpSession.class.getName() );
        processor.addSession( session, this.httpSession ); // 添加指map当中
    }

    @OnMessage
    public void onMessage( String message, Session session) {
        this.logger.info( "收到消息： {}", message );
        Message message1 = null;
        try {
            message1 = JsonUtil.fromStringToObject(message, Message.class);
        } catch (Exception e) {
            try {
                session.getBasicRemote().sendText( JsonUtil.fromObjectToString(SystemMessages.getMessage(SystemMessages.ERROR_TEXT_FORMAT)) );
            } catch (Exception e2) {
                this.logger.error( "用户消息无法达到！" );
                this.logger.error( e2.getMessage() );
            }
            this.logger.error( "消息格式错误！" );
            this.logger.error( e.getMessage() );
            return;
        }
        this.processor.process( message1, session, this.httpSession, null );
    }

    @OnClose
    public void onClose( Session session ) {
        this.logger.info( session.getId() + "会话关闭");
    }

    @Autowired
    public void setProcessor(Processor processor) {
        this.processor = processor;
    }
}
