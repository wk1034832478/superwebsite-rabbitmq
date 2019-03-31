package home.hyywk.top.superwebsiterabbitmq.processors;

import home.hyywk.top.superwebsiterabbitmq.entity.*;
import home.hyywk.top.superwebsiterabbitmq.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class AbstractProcessor implements Processor {

    private Logger logger = LoggerFactory.getLogger( AbstractProcessor.class );
    /**
     * 保证子类可以获取到
     */
    protected ConcurrentMap<String, Session> currentSessions;

    public AbstractProcessor( boolean needInitialized ) {
        if ( needInitialized ) {
            this.currentSessions = new ConcurrentHashMap<>();
        }
    }

    public AbstractProcessor() { }

    @Override
    public int size() {
        return this.currentSessions.size();
    }

    @Override
    public void addSession(Session session, HttpSession httpSession) {
        User user =  (User) httpSession.getAttribute( "user" );
        if ( user == null ) { // 如果用户未登录，则在提醒登录后直接断掉套接字
            try {
                sendMessage( session, "请先登录！" );
                session.close();
            } catch (Exception e ) {
                this.logger.error( e.getMessage() );
            }
            return;
        }
        user.setSessionId( session.getId() ); // 用户设置session id
        httpSession.setAttribute( "session", session );
        this.currentSessions.put( session.getId(), session);
    }

    public Set<String> sessionArray() {
        return this.currentSessions.keySet();
    }

    public void deleteSession(String sessionId) {
        this.currentSessions.remove( sessionId );
    }

    /**
     * 使用某个特定的session来推送信息
     * @param session 当前会话
     * @param message 消息
     */
    public void sendMessage( Session session, Message message ) throws IOException {
        session.getBasicRemote().sendText( JsonUtil.fromObjectToString( message ) );
    }

    public void sendMessageByMessageText( Session session, String text ) throws IOException {
        session.getBasicRemote().sendText( text );
    }

    public void sendMessage( Session session, String msg ) throws IOException {
        Message message = new Message("", "", null, msg, MessageType.OneToOne, MessageCode.CODE_SUCCESS );
        session.getBasicRemote().sendText( JsonUtil.fromObjectToString( message) );
    }

    public boolean processorCheck( int processCode ){ return false; }

    @Override
    public String toString() {
        return "信息处理中心";
    }
}
