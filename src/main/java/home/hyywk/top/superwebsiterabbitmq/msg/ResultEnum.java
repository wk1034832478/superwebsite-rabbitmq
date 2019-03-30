package home.hyywk.top.superwebsiterabbitmq.msg;

/**
 * 相应体代码
 */
public interface ResultEnum {
    /**
     *服务器成功返回用户请求的数据
     */
     int CODE_SUCCESS = 200;

    /**
     * 用户新建或修改数据成功
     */
    int CODE_CREATE = 201;

    /**
     * 表示一个请求已经进入后台排队（异步任务）
     */
    int CODE_ACCEPTED =202;

    /**
     * 用户删除数据成功
     */
    int CODE_DELETE = 204;

    /**
     * 用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的
     */
    int CODE_INVALID = 400;

    /**
     * 表示用户没有权限
     */
    int CODE_UNAUTHORIZED = 401;

    /**
     * 用户授权禁止
     */
    int CODE_FORBIDDEN = 403;

    /**
     * 找不到资源
     */
    int CODE_NOT_FOUND = 404;

    /**
     * 格式错误
     */
    int CODE_FORMAT_ERROR = 406;

    /**
     * 用户请求的资源被永久删除
     */
    int CODE_GONE = 410;

    /**
     * 当创建一个对象时发生错误
     */
    int CODE_UNPROCESABLE = 422;

    /**
     * 服务器发生错误，用户将无法判断发出的请求是否成功
     */
    int INTERNAL_SERVER_ERROR = 500;
}
