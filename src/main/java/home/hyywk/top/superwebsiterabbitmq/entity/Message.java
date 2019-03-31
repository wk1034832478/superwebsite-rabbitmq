package home.hyywk.top.superwebsiterabbitmq.entity;

/**
 * 通信实体对象
 */
public class Message {
    /**
     * 信息来源者的会话id
     */
    private String from;

    /**
     * 发送者id
     */
    private int fromId;

    /**
     * 发送者姓名
     */
    private String fromName;

    /**
     * 信息目的地 tos
     */
    private String to;
    /**
     * 信息内容
     */
    private String msg;

    /**
     * 信息类型
     */
    private int type;

    private int groupId;

    /**
     * 发送相映码
     */
    private int code;

    public Message(String msg, int type, int groupId) {
        this.msg = msg;
        this.type = type;
        this.groupId = groupId;
    }

    public Message(String from, String to, String msg, int type, int code) {
        this.from = from;
        this.to = to;
        this.msg = msg;
        this.type = type;
        this.code = code;
    }

    public Message(String from, String fromName, String to,  String msg, int type, int code) {
        this.from = from;
        this.fromName = fromName;
        this.to = to;
        this.msg = msg;
        this.type = type;
        this.code = code;
    }

    public Message(int fromId, String from, String fromName, String to, String msg, int type, int code) {
        this.fromId = fromId;
        this.from = from;
        this.fromName = fromName;
        this.to = to;
        this.msg = msg;
        this.type = type;
        this.code = code;
    }

    public Message(int groupId, int fromId, String from, String fromName, String to, String msg, int type, int code) {
        this.groupId = groupId;
        this.fromId = fromId;
        this.from = from;
        this.fromName = fromName;
        this.to = to;
        this.msg = msg;
        this.type = type;
        this.code = code;
    }

    public Message() {}

    public static Message getBroadcastMessage(String msg) {
        Message message = new Message();
        message.setMsg( msg );
        message.setType( MessageType.BROADCAST_ALL );
        message.setCode( MessageCode.CODE_SUCCESS );
        return message;
    }


    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromName() {
        return fromName;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "groupId:" + this.groupId +", msg:" + msg;
    }
}
