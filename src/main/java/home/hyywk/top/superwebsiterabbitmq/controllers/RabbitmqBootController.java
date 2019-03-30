package home.hyywk.top.superwebsiterabbitmq.controllers;

import home.hyywk.top.superwebsiterabbitmq.msg.Result;
import home.hyywk.top.superwebsiterabbitmq.services.RabbitmqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("api")
public class RabbitmqBootController {

    @Autowired
    private RabbitmqService rabbitmqService;

    /**
     * 开始会话
     * @return
     */
    @PostMapping("start")
    public Result<Object> start(HttpServletRequest request, HttpSession httpSession ) {
        this.rabbitmqService.openSession( httpSession );
        return Result.get201( "ok" );
    }

    @PostMapping("end")
    public Result<Object> end(HttpServletRequest request,  HttpSession httpSession) {
        try {
            this.rabbitmqService.endSession( httpSession );
        } catch (com.rabbitmq.client.AlreadyClosedException e ) {
            return Result.get400( "会话已经关闭" );
        } catch (Exception e) {
            return Result.get500( e.getMessage() );
        }
        return Result.get204( "ok" );
    }

}
