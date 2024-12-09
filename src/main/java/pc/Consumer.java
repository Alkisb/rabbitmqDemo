package pc;

import utils.RabbitmqUtil;
import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    @Test
    public void consumer() throws IOException, TimeoutException, InterruptedException {
        //创建连接工程
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        connectionFactory.setHost("192.168.141.130");
//        connectionFactory.setPort(5672);
//        connectionFactory.setVirtualHost("/ems");
//        connectionFactory.setUsername("root");
//        connectionFactory.setPassword("123456");
//        Connection connection = connectionFactory.newConnection();
        Connection connection = RabbitmqUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("hello",true,false,true,null);
        channel.basicConsume("hello", true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
            }
        });
        Thread.sleep(100);
//        connection.close();
//        channel.close();
        RabbitmqUtil.closeConnectionAndChanel(channel,connection);
    }
}
