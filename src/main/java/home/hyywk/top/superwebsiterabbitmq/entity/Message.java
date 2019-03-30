package home.hyywk.top.superwebsiterabbitmq.entity;

/**
 * 通信实体对象
 */
public class Message {
    /**
     * 信息来源
     */
    private String from;

    /**
     * 信息目的地 tos
     */
    private String to;
    private String tos; // 是当发给多个人的时候使用的

    /**
     * 信息内容
     */
    private String msg;

    /**
     * 信息类型
     */
    private int type;

    /**
     * 发送相映码
     */
    private int code;

    public Message(String from, String to, String tos, String msg, int type, int code) {
        this.from = from;
        this.to = to;
        this.tos = tos;
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

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setTos(String tos) {
        this.tos = tos;
    }

    public String getTos() {
        return tos;
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
}
