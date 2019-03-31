package home.hyywk.top.superwebsiterabbitmq.services;

import home.hyywk.top.superwebsiterabbitmq.entity.User;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

@Service
public class UserService {

    private Integer id = 0;

    public void setName(HttpSession httpSession, String name) {
        User user = (User) httpSession.getAttribute( "user");
        if ( user == null ) {
            synchronized ( this.id ) {
                user = new User( id,null, name );
                httpSession.setAttribute( "user",user);
            }
        } else {
            user.setName( name);
        }
    }

    public String getSessionId( HttpSession httpSession) throws Exception {
        Session session = (Session) httpSession.getAttribute("session");
        if ( session != null ) {
           return session.getId();
        }
        throw new Exception("还没有建立连接！");
    }

}
