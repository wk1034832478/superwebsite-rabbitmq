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

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data =data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, T t) {
        this.code = code;
        this.data = t;
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

    public static Result get200(String message, Object data)  {
        return new Result<>(ResultEnum.CODE_SUCCESS, message, data );
    }

    public static Result get201(String message)  {
        return new Result(ResultEnum.CODE_CREATE, message);
    }

    public static <T> Result get201(T t)  {
        return new Result(ResultEnum.CODE_CREATE, t);
    }

    public static Result get204(String message)  {
        return new Result(ResultEnum.CODE_DELETE, message);
    }

    public static Result get400(String message)  {
        return new Result(ResultEnum.CODE_INVALID, message);
    }

    public static Result get403(String message)  {
        return new Result(ResultEnum.CODE_FORBIDDEN, message);
    }

    public static Result get404(String message)  {
        return new Result(ResultEnum.CODE_NOT_FOUND, message);
    }

    public static Result get500(String message)  {
        return new Result(ResultEnum.INTERNAL_SERVER_ERROR, message);
    }



}
