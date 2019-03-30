package home.hyywk.top.superwebsiterabbitmq.controllers;

import home.hyywk.top.superwebsiterabbitmq.msg.Result;
import home.hyywk.top.superwebsiterabbitmq.services.MessageService;
import home.hyywk.top.superwebsiterabbitmq.services.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class MessageController {


    private Processor processor;
    private MessageService messageService;

    private Logger logger = LoggerFactory.getLogger( MessageController.class);

    @PostMapping("broadcast")
    public Result<Object> broadcasetAllPeople(@RequestParam("msg") String msg ) {
        try {
            this.messageService.broadcast( processor, msg );
            return Result.get200( "ok" );
        } catch (Exception e) {
            this.logger.error( e.getMessage() );
            return Result.get500( "服务器内部错误" );
        }
    }

    @Autowired
    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }
}
