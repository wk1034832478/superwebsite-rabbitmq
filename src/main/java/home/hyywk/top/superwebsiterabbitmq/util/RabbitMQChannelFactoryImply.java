package home.hyywk.top.superwebsiterabbitmq.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Stack;

/**
 * 默认使用单例
 */
@Component
public class RabbitMQChannelFactoryImply implements RabbitMQChannelFactory {

    private Logger logger = LoggerFactory.getLogger( RabbitMQChannelFactoryImply.class );

    @Autowired
    private ConnectionFactory connectionFactory;

    @Value("${superwebsite.rabbitmq.max.connection.size}")
    private int maxConnectionSize;

    /**
     * 缓存连接池
     */
    private Stack<Connection> connections = new Stack<>();

    @Override
    public Channel getChannel() {
        try {
            Connection connection = this.getConnection();
            Channel channel = connection.createChannel();
            this.cacheConnection( connection );
            return channel;
        } catch (IOException e) {

        } catch (Exception e2) {

        }
        return null;
    }

    /**
     * 将连接对象进行缓存
     * @param connection 连接对象
     */
    public void cacheConnection( Connection connection ) {
        if ( this.connections.size() >= this.maxConnectionSize ) {
            try {
                connection.close();
            } catch (Exception e) {
                this.logger.error( "连接对象关闭异常" );
                this.logger.error( e.getMessage() );
            }
        }
        this.connections.push( connection );
    }

    /**
     * 从缓存数组获取当中连接对象
     * @return
     * @throws IOException
     * @throws Exception
     */
    public Connection getConnection() throws IOException, Exception  {
        synchronized ( this.connections ) { // 锁定连接数组
            if ( this.connections.size() < this.maxConnectionSize ) { // 没有连接，则创建一个
                return this.connectionFactory.newConnection();
            }
            Connection connection = this.connections.pop();
            if ( !connection.isOpen() ) { // 未保持连接状态，则
                return this.connectionFactory.newConnection();
            }
            return connection;
        }
    }
}
