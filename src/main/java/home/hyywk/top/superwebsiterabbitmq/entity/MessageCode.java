package home.hyywk.top.superwebsiterabbitmq.entity;

public interface MessageCode {
    /**
     *服务器成功发送用户的消息
     */
    int CODE_SUCCESS = 200;


    /**
     * 表示一个请求已经进入后台排队（异步任务）
     */
    int CODE_ACCEPTED =202;


    /**
     * 用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的
     */
    int CODE_INVALID = 400;


    /**
     * 用户还未提供个人信息认证
     */
    int CODE_FORBIDDEN = 403;

    /**
     * 对方已经不在线
     */
    int CODE_NOT_FOUND = 404;

    /**
     * 格式错误
     */
    int CODE_FORMAT_ERROR = 406;



    /**
     * 服务器发生错误，用户将无法判断发出的请求是否成功
     */
    int INTERNAL_SERVER_ERROR = 500;
}
