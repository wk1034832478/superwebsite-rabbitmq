package home.hyywk.top.superwebsiterabbitmq.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统默认信息
 */
@Scope("singleton")
@Component
public class SystemMessages {

    private static Map<String, Message> sysMsg;

    public static final String UNKNOWN_TYPE_MESSAGE = "UNKNOW_TYPE_MESSAGE";
    public static final String UNLOGIN = "UNLOGIN";
    public static final String UNKNOWN_GROUP = "UNKNOWN_GROUP";
    public static final String ERROR_TEXT_FORMAT = "ERROR_TEXT_FORMAT";

    static {
        sysMsg = new HashMap<>();
        sysMsg.put( UNKNOWN_TYPE_MESSAGE, new Message( "system", null, null, "未知的消息类型", MessageType.OneToOne, MessageCode.CODE_INVALID ));
        sysMsg.put( UNLOGIN, new Message( "system", null, null, "还未登录", MessageType.OneToOne, MessageCode.CODE_FORBIDDEN ));
        sysMsg.put( UNKNOWN_GROUP, new Message( "system", null, null, "群聊不存在", MessageType.OneToMany, MessageCode.CODE_FORBIDDEN ));
        sysMsg.put( ERROR_TEXT_FORMAT, new Message( "system", null, null, "格式错误！", MessageType.OneToOne, MessageCode.CODE_INVALID ));
    }

    public static Message getMessage( String key ) {
        return sysMsg.get( key );
    }

}
