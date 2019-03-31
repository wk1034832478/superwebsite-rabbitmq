package home.hyywk.top.superwebsiterabbitmq.services;

import home.hyywk.top.superwebsiterabbitmq.entity.User;
import home.hyywk.top.superwebsiterabbitmq.entity.UserGroup;
import home.hyywk.top.superwebsiterabbitmq.exceptions.UserException;
import home.hyywk.top.superwebsiterabbitmq.exceptions.UserGroupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class UserGroupService {

    private Logger logger = LoggerFactory.getLogger( UserGroupService.class );

    /**
     * 这是群组索引，当添加的时候，需要将该索引进行同步
     */
    private Integer index = 1001;

    /**
     * 保存当期的群组, 键是群的id
     */
    private ConcurrentMap<Integer, UserGroup> concurrentUserGroup;
    public UserGroupService() {
        this.concurrentUserGroup = new ConcurrentHashMap<>();
    }

    public List<String> addGroupUser( Integer id, String[] sessionIds ) throws Exception {
        this.logger.info( "群聊id：{}", id);
        this.logger.info( "成员会话ids：{}", sessionIds);
        UserGroup userGroup = this.concurrentUserGroup.get( id );
        if ( userGroup == null )  {
            throw new Exception("群聊不存在！");
        }
        List<String> si2 = userGroup.getUserSessionIds();
        /**
         * 加上锁，防止同时加入重复的成员
         */
        synchronized ( si2 ) {
            for ( int i = 0; i < sessionIds.length; i++ ) {
                if ( si2.size() != 0) {
                    for ( int j = 0; j < si2.size(); j++ ) {
                        if ( sessionIds[i].equals( si2.get( j ) ) ) { // 如果相同，说明群聊已经加入过该成员
                            break;
                        }
                        if ( j == si2.size() - 1 ) {
                            si2.add( sessionIds[i] );
                        }
                    }
                } else {
                    si2.add( sessionIds[i] );
                }
            }
        }
        return si2;
    }

    /**
     * 添加新的群聊
     * @param userGroup
     * @param httpSession
     * @return 群聊id
     * @throws UserGroupException
     * @throws Exception
     */
    public int add(UserGroup userGroup, HttpSession httpSession) throws UserGroupException, UserException, UserGroupException {
        User user = (User) httpSession.getAttribute( "user" );
        this.logger.info( "新添群组：{}" , userGroup.getGroupName()  );
        if ( user == null ) {
            throw new UserException("用户还未登录");
        }

        Iterator<UserGroup> iterator = this.concurrentUserGroup.values().iterator();
        while ( iterator.hasNext() ) {
            String name = iterator.next().getGroupName();
            if ( name.equals( userGroup.getGroupName() )) {
                throw new UserGroupException( "\"" + name + "\"" +", 已经有了相同名字的群聊，亲请换个名字吧！");
            }
        }

        synchronized ( this.index ) {
            userGroup.setId( this.index++ );
            userGroup.setCreaterId( user.getId());
        }

        this.concurrentUserGroup.put( userGroup.getId(), userGroup );
        return userGroup.getId();
    }

    /**
     * 返回群组集合, 所有的群组
     * @return 群组集合
     */
    public Collection<UserGroup> getGroups() {
        return this.concurrentUserGroup.values();
    }
}
