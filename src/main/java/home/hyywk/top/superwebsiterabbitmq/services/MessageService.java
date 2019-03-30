package home.hyywk.top.superwebsiterabbitmq.services;

import home.hyywk.top.superwebsiterabbitmq.entity.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageService {


    public void broadcast(Processor processor, String msg) {
        Message message = Message.getBroadcastMessage( msg );
        processor.process( message );
    }

}
