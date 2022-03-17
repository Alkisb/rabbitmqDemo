import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class provied {
    //生产消息
    @Test
    public void testSendMsg() throws IOException, TimeoutException {
        //创建mq的连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置连接mq的主机
        connectionFactory.setHost("192.168.141.130");
        //设置端口
        connectionFactory.setPort(5672);
        //设置连接虚拟主机
        connectionFactory.setVirtualHost("/ems");
        //设置访问虚拟主机的用户名和密码
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("123456");

        //获取连接对象
        Connection connection = connectionFactory.newConnection();
        //获取连接中的通道
        Channel channel = connection.createChannel();
        /**
         *    声明通道绑定的消息队列(如果没有对应的消息队列，则会自己创建)
         *         argument1：队列名称
         *         argument2：这个消息队列是否持久化到disk
         *         argument3：exclusive当前链接是否是独占队列
         *         argument4：这个队列中的消息消费完成之后是否自动删除
         *         argument5：其他参数
         */
        channel.queueDeclare("hello",false,false,false,null);
        //这里如果交换机参数传入空串，会使用默认的交换机AMQP DEFAULT，默认交换机会和所有的队列建立隐含绑定关系，绑定的路由key就是队列名称
        channel.basicPublish("","hello",null,"msg".getBytes(StandardCharsets.UTF_8));
        channel.close();
        connection.close();
    }
}
