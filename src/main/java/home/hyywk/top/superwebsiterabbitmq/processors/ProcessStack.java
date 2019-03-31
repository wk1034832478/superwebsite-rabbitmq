package home.hyywk.top.superwebsiterabbitmq.processors;

import home.hyywk.top.superwebsiterabbitmq.entity.Message;
import home.hyywk.top.superwebsiterabbitmq.entity.SystemMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class ProcessStack extends AbstractProcessor{

    private Logger logger = LoggerFactory.getLogger( ProcessStack.class );

    private List<Processor> processors;

    public ProcessStack(boolean isInitialized) {
        super( true );
        this.processors = new ArrayList<>();
    }

    /**
     * 根据匹配处理
     * @param message 要被处理的信息
     * @param session
     * @param httpSession
     * @param allSessions
     */
    @Override
    public void process(Message message, Session session, HttpSession httpSession, ConcurrentMap<String, Session> allSessions) {
        for ( int i = 0; i < this.processors.size(); i++ ) {
            if ( this.processors.get(i).getCode() == message.getType() ) {
                this.processors.get(i).process(message, session,httpSession,this.currentSessions );
                return;
            }
        }
        try {
            sendMessage( session, SystemMessages.getMessage( SystemMessages.UNKNOWN_TYPE_MESSAGE ));
        } catch (Exception e) {
            this.logger.error( e.getMessage() );
        }
    }

    public void addProcessor( Processor processor ) {
        this.processors.add( processor );
    }

    @Override
    public int getCode() { return 0; }
}
