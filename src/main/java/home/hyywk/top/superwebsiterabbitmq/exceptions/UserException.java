package home.hyywk.top.superwebsiterabbitmq.exceptions;

/**
 * 当发出用户异常情况下进行该操作
 */
public class UserException extends Exception {

    public UserException(String msg) {
        super( msg );
    }

    public UserException() {
        super( );
    }
}
