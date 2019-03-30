package home.hyywk.top.superwebsiterabbitmq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

@Component
@WebListener
public class RequestListener implements ServletRequestListener {

    private Logger logger = LoggerFactory.getLogger( RequestListener.class );

    public void requestInitialized(ServletRequestEvent sre)  {
        //将所有request请求都携带上httpSession, 注意，如果请求当中没有httpsession对象，则会自动创建一个！
        ((HttpServletRequest) sre.getServletRequest()).getSession();
        this.logger.info( "http-session可能重新创建中" );
    }

    public RequestListener() {
    }

    public void requestDestroyed(ServletRequestEvent arg0)  {
    }
}