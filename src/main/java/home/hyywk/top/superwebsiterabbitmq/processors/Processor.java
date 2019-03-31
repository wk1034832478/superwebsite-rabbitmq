package home.hyywk.top.superwebsiterabbitmq.processors;

import home.hyywk.top.superwebsiterabbitmq.entity.Message;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * 信息处理器接口
 */
public interface Processor {
    /**
     * 处理信息
      * @param message 要被处理的信息
     */
    void process(Message message, Session session, HttpSession httpSession, ConcurrentMap<String, Session> allSessions );

    /**
     * 返回当前一共有多少个在线用户
     * @return 当前用户数量
     */
    int size();

    /**
     * 添加新的会话
     * @param session
     */
    void addSession(Session session, HttpSession httpSession);


    void deleteSession( String sessionId );

    Set<String> sessionArray();

    int getCode();

}
