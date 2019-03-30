package home.hyywk.top.superwebsiterabbitmq.config;

import com.rabbitmq.client.ConnectionFactory;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.scope.ScopedObject;
import org.springframework.aop.scope.ScopedProxyFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class SpringInitialierConfig {

    private Logger logger = LoggerFactory.getLogger( SpringInitialierConfig.class );

    @Value("${superwebsite.rabbitmq.address}")
    private String rabbitAddrsss;
    @Value("${superwebsite.rabbitmq.port}")
    private String rabbitPort;
    @Value("${superwebsite.rabbitmq.username}")
    private String rabbitUsername;
    @Value("${superwebsite.rabbitmq.password}")
    private String rabbitPassword;

    @Value("${superwebsite.zookeeper.servers}")
    private String zkServers;
    @Value("${superwebsite.zookeeper.root}")
    private String root;

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1允许任何域名使用
        corsConfiguration.addAllowedHeader("*"); // 2允许任何头
        corsConfiguration.addAllowedMethod("*"); // 3允许任何方法（post、get等）
        return corsConfiguration;
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }

    /**
     * 初始化连接工厂对象，还没有对其连接进行缓存,此对象应当为单丽模式
     * @return
     */
    @Bean
    @Scope("singleton")
    public ConnectionFactory rabbitConnection() {
        this.logger.info( "rabbitmq默认端口为：" + this.rabbitPort );
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( this.rabbitAddrsss);
        factory.setPort( Integer.valueOf( this.rabbitPort ) );
        factory.setUsername( this.rabbitUsername );
        factory.setPassword( this.rabbitPassword );
        return factory;
    }

    @Scope("singleton")
    @Bean
    public ZkClient zkClient() {
        ZkClient zkClient = new ZkClient(this.zkServers, 3000 );
        if ( !zkClient.exists(this.root ) ) {
            this.logger.info("正在创建超人网站通讯固定根节点");
            zkClient.createPersistent(this.root, "超人网站通讯固定根节点");
        }
        return zkClient;
    }

}
