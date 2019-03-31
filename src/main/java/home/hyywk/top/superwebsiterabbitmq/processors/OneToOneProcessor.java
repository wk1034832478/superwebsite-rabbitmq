package home.hyywk.top.superwebsiterabbitmq.processors;

import home.hyywk.top.superwebsiterabbitmq.entity.Message;
import home.hyywk.top.superwebsiterabbitmq.entity.MessageCode;
import home.hyywk.top.superwebsiterabbitmq.entity.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.ConcurrentMap;

/**
 * 一对一进行发送信息
 */
public class OneToOneProcessor extends AbstractProcessor {

    private Logger logger = LoggerFactory.getLogger( OneToOneProcessor.class );

    private final int CODE = MessageType.OneToOne;

    public OneToOneProcessor() { }

    @Override
    public void process(Message message, Session session, HttpSession httpSession, ConcurrentMap<String, Session> allSessions ) {
        String sessionId = message.getTo();
        Session target = allSessions.get( sessionId );
        if ( !target.isOpen() ) { // 对方已经下线，则通知消息发出人并删除此连接
            allSessions.remove( message.getTo() );
            Session session2 = allSessions.get( message.getFrom() );
            try {
                this.sendMessage(session2, new Message(message.getFrom(), message.getTo(), null, message.getMsg(),
                        MessageType.OneToOne, MessageCode.CODE_NOT_FOUND));
            } catch (IOException e) { // 发消息的人通讯也出现问题，关闭该连接
                allSessions.remove( message.getFrom() );
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
            this.sendMessage( target, message );
        } catch (IOException e) {
            this.logger.error( e.getMessage() );
        } catch (Exception e2) {
            this.logger.error( e2.getMessage() );
        }
    }

    @Override
    public int getCode() {
        return CODE;
    }

    //    @Override
//    public boolean processorCheck(int processCode) {
//        if ( CODE == processCode ) {
//            return true;
//        }
//        return false;
//    }

    //    public void broadcast( Message message ) {
//        String msg = JsonUtil.fromObjectToString( message );
//        Iterator<Session> iterator = this.currentSessions.values().iterator();
//        while ( iterator.hasNext() ) {
//            Session session = iterator.next();
//            try {
//                this.sendMessageByMessageText( session, msg );
//            } catch (IOException e) {
//                this.logger.error( "发送错误，{}", e.getMessage() );
//            }
//        }
//    }



}
