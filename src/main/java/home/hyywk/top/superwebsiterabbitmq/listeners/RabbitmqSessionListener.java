package home.hyywk.top.superwebsiterabbitmq.listeners;


import home.hyywk.top.superwebsiterabbitmq.services.RabbitmqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


// @WebListener
public class RabbitmqSessionListener implements HttpSessionListener {

    @Autowired
    private RabbitmqService rabbitmqService;
    private Logger logger = LoggerFactory.getLogger( RabbitmqSessionListener.class );

//    @Override
//    public void sessionCreated(HttpSessionEvent se) {
//        try {
//            this.logger.info( "开始新的会话" );
//            Channel channel = this.rabbitMQChannelFactory.getChannel();
//            Channel channel2 = this.rabbitMQChannelFactory.getChannel();
//
//            String sendName = this.getSendQueueName( se.getSession());
//            String receiveName = this.getReceiveQueueName( se.getSession());
//
//            channel.queueDeclare( sendName, false, false, false, null);
//            channel2.queueDeclare( receiveName, false, false, false, null);
//
//            // 根据sessionId创建一个queue
//            se.getSession().setAttribute( RabbitmqUtil.RABBIT_SESSION_SEND_KEY, channel );
//            se.getSession().setAttribute( RabbitmqUtil.RABBIT_SESSION_RECEIVE_KEY, channel );
//
//            // 向zookeeper中写入当前会话节点
//            zkClient.createEphemeral(root + "/" + se.getSession().getId() );
//            // 创建队列信号节点，客户人员看到这两个节点的时候，说明需要客户人员进行接入
//            zkClient.createEphemeral( root + "/" + se.getSession().getId() + "/" + sendName);
//            zkClient.createEphemeral( root + "/" + se.getSession().getId() + "/" + receiveName);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            this.logger.error("发送未知错误");
//            this.logger.error( e.getMessage() );
//        }
//    }
//

    /**
     * 如果客户端没有主动关闭，则当会话结束的时候关闭消息队列
     * @param se
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se)  {
        try {
            this.rabbitmqService.endSession( se.getSession() );
        } catch (Exception e) {
            this.logger.error( "会话自动关闭失败" );
            this.logger.error( e.getMessage() );
        }
    }


}
