package home.hyywk.top.superwebsiterabbitmq.entity;

/**
 * 当前在线用户
 */
public class User {
    private int id;
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

    public User(int id, String sessionId, String name) {
        this.id = id;
        this.sessionId = sessionId;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
