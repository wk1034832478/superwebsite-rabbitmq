package home.hyywk.top.superwebsiterabbitmq.services;

import home.hyywk.top.superwebsiterabbitmq.entity.Message;

import javax.websocket.Session;
import java.util.Set;

/**
 * 信息处理器接口
 */
public interface Processor {
    /**
     * 处理信息
      * @param message 要被处理的信息
     */
    void process(Message message );

    /**
     * 返回当前一共有多少个在线用户
     * @return 当前用户数量
     */
    int size();

    /**
     * 添加新的会话
     * @param session
     */
    void addSession(Session session);


    void deleteSession( String sessionId );

    Set<String> sessionArray();

}
