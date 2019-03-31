package home.hyywk.top.superwebsiterabbitmq.controllers;

import home.hyywk.top.superwebsiterabbitmq.entity.UserGroup;
import home.hyywk.top.superwebsiterabbitmq.exceptions.UserGroupException;
import home.hyywk.top.superwebsiterabbitmq.msg.Result;
import home.hyywk.top.superwebsiterabbitmq.processors.Processor;
import home.hyywk.top.superwebsiterabbitmq.services.UserGroupService;
import home.hyywk.top.superwebsiterabbitmq.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api")
public class UserController {

    private Logger logger = LoggerFactory.getLogger( UserController.class );
    private Processor processor;
    private UserService userService;
    private UserGroupService userGroupService;
    private HttpSession httpSession;
    /**
     * 获取当前会话ids
     * @return 返回当前所有的会话id
     */
    @GetMapping("session/list")
    public Result<Set> getCurrentSessionIds() {
        Set<String> set = processor.sessionArray();
        Result<Set> r = Result.get200( "ok",set );
        return r;
    }

    /**
     * 设置当前用户的名称
     * @param name 当前用户的名称
     */
    @GetMapping("username")
    public Result setUsername(@RequestParam(value = "name", required = true) String name, HttpSession httpSession ) {
        userService.setName( httpSession, name );
        return Result.get201( "ok" );
    }

    @GetMapping("usergroup")
    public Result<Object> createGroup( UserGroup userGroup , HttpSession httpSession ) {
        int id = 0;
        try {
            id = this.userGroupService.add( userGroup, httpSession );
        } catch (UserGroupException uge) {
            return Result.get400( uge.getMessage() );
        }catch (Exception e) {
            this.logger.error( "群聊创建错误：" + e.getMessage() );
            return Result.get403( e.getMessage() + " 群聊名称重复！ ");
        }
        return Result.get201( id );
    }

    @GetMapping("usergroup/newuser")
    public Result addGroupUsers( Integer groupId, String[] sessionIds ) {
        List<String> users;
        try {
            users = this.userGroupService.addGroupUser( groupId, sessionIds );
            return Result.get201(  users );
        } catch (Exception e) {
            this.logger.error( e.getMessage() );
            return Result.get500( "服务器发送错误！" );
        }
    }

    @GetMapping("usergroup/list")
    public Result<Collection> getAllGroup(  ) {
        Collection<UserGroup> userGroups;
        try {
            userGroups = this.userGroupService.getGroups();
            if ( userGroups.size() == 0 ) {
                return Result.get404("暂时还没有创建群聊咯！" );
            }
        } catch (Exception e) {
            this.logger.error( e.getMessage() );
            return Result.get403( e.getMessage() );
        }
        return Result.get200("ok", userGroups );
    }

    @GetMapping("sessionid")
    public Result<String> getessionid()  {
        String msg = null;
        try {
            msg = this.userService.getSessionId(this.httpSession );
            return Result.get200( msg );
        } catch (Exception e) {
            this.logger.error( e.getMessage() );
            return Result.get403( e.getMessage() );
        }
    }

    @Autowired
    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @Autowired
    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserGroupService(UserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }
}
