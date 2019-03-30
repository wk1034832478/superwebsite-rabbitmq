package home.hyywk.top.superwebsiterabbitmq.msg;

/**
 * 返回客户端消息实体对象
 */
public class Result<T> {

    private int code;
    private String msg;
    private String succ;
    private T data;

    public Result(int code, String msg, String succ, T data) {
        this.code = code;
        this.msg = msg;
        this.succ = succ;
        this.data = data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSucc() {
        return succ;
    }

    public void setSucc(String succ) {
        this.succ = succ;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 静态创建方法
     * @param message 消息
     * @return
     */

    public static Result get200(String message)  {
        return new Result(ResultEnum.CODE_SUCCESS, message);
    }

    public static Result get201(String message)  {
        return new Result(ResultEnum.CODE_CREATE, message);
    }

    public static Result get204(String message)  {
        return new Result(ResultEnum.CODE_DELETE, message);
    }

    public static Result get400(String message)  {
        return new Result(ResultEnum.CODE_INVALID, message);
    }

    public static Result get500(String message)  {
        return new Result(ResultEnum.INTERNAL_SERVER_ERROR, message);
    }



}
