package home.hyywk.top.superwebsiterabbitmq.controllers;

import home.hyywk.top.superwebsiterabbitmq.msg.Result;
import home.hyywk.top.superwebsiterabbitmq.services.RabbitmqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("api")
public class RabbitmqMessageController {

    private Logger logger = LoggerFactory.getLogger( RabbitmqMessageController.class );

    @Autowired
    private RabbitmqService rabbitmqService;

    @PostMapping("msg")
    public Result<Object> sendMessage(HttpSession session, String msg) {
        try {
            this.rabbitmqService.sendMessage(session, msg);
        } catch (IOException e) {
            this.logger.error( e.getMessage() );
            return Result.get500("服务器传输错误！");
        } catch (Exception e2) {
            this.logger.error( e2.getMessage() );
            return Result.get500("服务器内部错误！");
        }
        return Result.get201("发送成功！");
    }

}
