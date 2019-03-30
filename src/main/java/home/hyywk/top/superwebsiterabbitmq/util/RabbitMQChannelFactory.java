package home.hyywk.top.superwebsiterabbitmq.util;

import com.rabbitmq.client.Channel;

public interface RabbitMQChannelFactory {

    /**
     * 获取一个打开的channel
     * @return
     */
    Channel getChannel();
}
