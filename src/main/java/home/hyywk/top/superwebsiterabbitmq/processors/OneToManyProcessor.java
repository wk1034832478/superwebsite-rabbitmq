package home.hyywk.top.superwebsiterabbitmq.processors;

import home.hyywk.top.superwebsiterabbitmq.entity.Message;
import home.hyywk.top.superwebsiterabbitmq.entity.MessageType;
import home.hyywk.top.superwebsiterabbitmq.entity.SystemMessages;
import home.hyywk.top.superwebsiterabbitmq.entity.UserGroup;
import home.hyywk.top.superwebsiterabbitmq.services.UserGroupService;
import home.hyywk.top.superwebsiterabbitmq.util.MyApplicationContextAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * 一对多分发消息，常用场景为群聊
 */
public class OneToManyProcessor extends AbstractProcessor {

    private Logger logger = LoggerFactory.getLogger( OneToManyProcessor.class );

    private final int CODE = MessageType.OneToMany;

    @Override
    public void process(Message message, Session session, HttpSession httpSession, ConcurrentMap<String, Session> allSessions) {
        UserGroupService userGroupService = MyApplicationContextAware.getApplicationContext().getBean( UserGroupService.class );
        Iterator<UserGroup> groups = userGroupService.getGroups().iterator();
        List<String> sessionids = null;
        while ( groups.hasNext() ) {
            UserGroup userGroup = groups.next();
            this.logger.info( "正在和" + userGroup.getId() + ", 进行比较，获取的群id为" + message.getGroupId() );
            if ( userGroup.getId() == message.getGroupId() )  {
                sessionids = userGroup.getUserSessionIds();
            }
        }

        if ( sessionids == null || sessionids.size() == 0) {
            try {
                this.logger.info( "当前群聊没有任何人！ ， {}", sessionids );
                sendMessage( session, SystemMessages.UNKNOWN_GROUP );
                return;
            } catch (Exception e) {
                this.logger.error(e.getMessage());
            }
        }

        for ( int i = 0; i <  sessionids.size(); i++ ) {
            Session sessionOfGroup = allSessions.get( sessionids.get(i) );
            try {
                sendMessage( sessionOfGroup, message );
            } catch (Exception e) {
                this.logger.error( e.getMessage() );
            }
        }
    }

    @Override
    public int getCode() {
        return CODE;
    }
}
