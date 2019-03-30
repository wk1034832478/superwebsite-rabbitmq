package home.hyywk.top.superwebsiterabbitmq.entity;

/**
 * 当前在线用户
 */
public class User {
    /**
     * 当前套接字会话id
     */
    private String sessionId;
    /**
     * 姓名（昵称）
     */
    private String name;

    public User(String sessionId, String name) {
        this.sessionId = sessionId;
        this.name = name;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
