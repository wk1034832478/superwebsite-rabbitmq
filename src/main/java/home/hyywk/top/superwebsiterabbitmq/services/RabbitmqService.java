package home.hyywk.top.superwebsiterabbitmq.services;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import home.hyywk.top.superwebsiterabbitmq.util.RabbitMQChannelFactory;
import home.hyywk.top.superwebsiterabbitmq.util.RabbitmqUtil;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Service
public class RabbitmqService {

    private Logger logger = LoggerFactory.getLogger( RabbitmqService.class );
    @Autowired
    private RabbitMQChannelFactory rabbitMQChannelFactory;
    @Autowired
    private ZkClient zkClient;
    @Value("${superwebsite.zookeeper.root}")
    private String root;

    /**
     * 开启新会话
     * @param session
     */
    public void openSession( HttpSession session ) {
        try {
            this.logger.info( "开始新的会话" );
            Channel channel = this.rabbitMQChannelFactory.getChannel();

            String sendName = this.getSendQueueName( session );
            String receiveName = this.getReceiveQueueName( session );

            channel.queueDeclare( sendName, false, false, false, null);
            channel.queueDeclare( receiveName, false, false, false, null);

            // 根据sessionId创建一个queue
            session.setAttribute( RabbitmqUtil.RABBIT_SESSION_KEY, channel );
            // 向zookeeper中写入当前会话节点
            zkClient.createPersistent(this.getSessionNode( session ) );
            // 创建队列信号节点，客户人员看到这两个节点的时候，说明需要客户人员进行接入
            zkClient.createEphemeral( this.getSessionSendNode( session ), "客户端发送队列名");
            zkClient.createEphemeral( this.getSessionReceiveNode( session ), "客户端接收队列名");

        } catch (Exception e) {
            e.printStackTrace();
            this.logger.error("发送未知错误");
            this.logger.error( e.getMessage() );
        }
    }


    public void endSession( HttpSession session ) throws com.rabbitmq.client.AlreadyClosedException,Exception {
        try {
            this.logger.info( "结束会话" );
            Channel channel = ( Channel ) session.getAttribute( RabbitmqUtil.RABBIT_SESSION_KEY );

            if ( channel == null ) {
                throw new Exception( "会话已经关闭" );
            }

            String sendName = this.getSendQueueName( session );
            String receiveName = this.getReceiveQueueName( session );

            // 根据sessionId删除queue
            channel.queueDelete( getSendQueueName( session ) );
            channel.queueDelete( getReceiveQueueName( session ) );

            channel.close();

            this.zkClient.deleteRecursive( this.getSessionNode( session ) ); // 进行递归删除

        } catch (com.rabbitmq.client.AlreadyClosedException e1) {
            throw  e1;
        }
    }

    /**
     * 发送消息
     * @param session 当前会话
     * @param msg 要发送的消息
     * @throws IOException io异常
     */
    public void sendMessage ( HttpSession session, String msg ) throws Exception {
        Channel channel = ( Channel )  session.getAttribute( RabbitmqUtil.RABBIT_SESSION_KEY );
        channel.basicPublish( "", this.getSendQueueName(session), null , msg.getBytes("utf-8"));
    }

    /**
     * 通过websocket来通知客户
     * @param session
     * @param msg
     * @return
     * @throws Exception
     */
    public void receiveMessage(  HttpSession session, String msg  ) throws Exception  {
        Channel channel = ( Channel )  session.getAttribute( RabbitmqUtil.RABBIT_SESSION_KEY );
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume( this.getReceiveQueueName(session), true, deliverCallback, consumerTag -> { });
    }

    public String getSessionNode( HttpSession session  ) {
        return this.root + "/" + session.getId();
    }

    public String getSessionSendNode( HttpSession session  ) {
        return this.root + "/" + session.getId() + "/" + this.getSendQueueName( session );
    }

    public String getSessionReceiveNode( HttpSession session  ) {
        return this.root + "/" + session.getId() + "/" + this.getReceiveQueueName( session );
    }

    /**
     * 获取接受队列名
     * @param session 当前会话
     * @return
     */
    public String getReceiveQueueName(HttpSession session) {
        return session.getId() + "-receive";
    }
    /**
     * 获取发送队列名
     * @param session 当前会话
     * @return
     */
    public String getSendQueueName(HttpSession session) {
        return session.getId() + "-send";
    }
}
