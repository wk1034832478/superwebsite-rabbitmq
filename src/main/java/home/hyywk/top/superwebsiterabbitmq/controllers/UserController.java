package home.hyywk.top.superwebsiterabbitmq.controllers;

import home.hyywk.top.superwebsiterabbitmq.msg.Result;
import home.hyywk.top.superwebsiterabbitmq.services.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    private Processor processor;

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

}
