package workQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitmqUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class provide {
    public static void main(String[] args) throws IOException {
        //获取连接对象
        Connection connection = RabbitmqUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("work",true,false,false,null);
        for (int i = 0;i < 20;i++){
            channel.basicPublish("","work",null,(i+"work msg").getBytes(StandardCharsets.UTF_8));
        }
        RabbitmqUtil.closeConnectionAndChanel(channel,connection);
    }
}
