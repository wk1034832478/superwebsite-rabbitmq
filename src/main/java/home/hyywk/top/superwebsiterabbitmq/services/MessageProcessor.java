package home.hyywk.top.superwebsiterabbitmq.services;

import home.hyywk.top.superwebsiterabbitmq.entity.Message;
import home.hyywk.top.superwebsiterabbitmq.entity.MessageCode;
import home.hyywk.top.superwebsiterabbitmq.entity.MessageType;
import home.hyywk.top.superwebsiterabbitmq.entity.User;
import home.hyywk.top.superwebsiterabbitmq.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 作为单例模式使用
 */
@Service
public class MessageProcessor implements Processor {

    private Logger logger = LoggerFactory.getLogger( MessageProcessor.class );

    private ConcurrentMap<String, Session> currentSessions;


    public MessageProcessor() {
        this.logger.info( "处理器正在初始化" );
        this.currentSessions = new ConcurrentHashMap<>();
    }

    @Override
    public void process(Message message) {
        switch ( message.getType() ) {
            case MessageType.OneToOne:
                fromOneToOne( message );
                break;
            case MessageType.BROADCAST_ALL:
                broadcast( message );
                break;
        }
    }

    @Override
    public int size() {
        return this.currentSessions.size();
    }

    @Override
    public void addSession(Session session) {
        this.currentSessions.put( session.getId(), session);
        try {
            sendMessage( session, "session-id=" + session.getId() );
        } catch (Exception e ) {
            this.logger.error( e.getMessage() );
        }
    }

    public void broadcast( Message message ) {
        String msg = JsonUtil.fromObjectToString( message );
        Iterator<Session> iterator = this.currentSessions.values().iterator();
        while ( iterator.hasNext() ) {
            Session session = iterator.next();
            try {
                this.sendMessageByMessageText( session, msg );
            } catch (IOException e) {
                this.logger.error( "发送错误，{}", e.getMessage() );
            }
        }
    }

    /**
     * 消息从一个人发送给另外一个人
     * @param message 消息对象
     */
    public void fromOneToOne(Message message) {
        String sessionId = message.getTo();
        Session session = this.currentSessions.get( sessionId );
        if ( !session.isOpen() ) { // 对方已经下线，则通知消息发出人并删除此连接
            this.currentSessions.remove( message.getTo() );
            Session session2 = this.currentSessions.get( message.getFrom() );
            try {
                this.sendMessage(session2, new Message(message.getFrom(), message.getTo(), null, message.getMsg(),
                        MessageType.OneToOne, MessageCode.CODE_NOT_FOUND));
            } catch (IOException e) { // 发消息的人通讯也出现问题，关闭该连接
                this.currentSessions.remove( message.getFrom() );
                this.logger.error( e.getMessage() );

                try {
                    session2.close();
                } catch (Exception e2) {
                    this.logger.error( e2.getMessage());
                }
            }
        }
        // 一切正常，将信息发送给接受人
        try {
            // 发送失败
            this.sendMessage( session, message );
        } catch (IOException e) {
            this.logger.error( e.getMessage() );
        } catch (Exception e2) {
            this.logger.error( e2.getMessage() );
        }
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
        session.getBasicRemote().sendText( JsonUtil.fromObjectToString( message) );
    }


    public void sendMessageByMessageText( Session session, String text ) throws IOException {
        session.getBasicRemote().sendText( text );
    }

    public void sendMessage( Session session, String msg ) throws IOException {
        Message message = new Message("", "", null, msg, MessageType.OneToOne, MessageCode.CODE_SUCCESS );
        session.getBasicRemote().sendText( JsonUtil.fromObjectToString( message) );
    }

    @Override
    public String toString() {
        return "信息处理中心";
    }
}
