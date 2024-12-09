package utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class RabbitmqUtil {
    private static ConnectionFactory connectionFactory;
    static {
          connectionFactory = new ConnectionFactory();
    }
    public  static Connection getConnection(){
        try {
            connectionFactory.setUsername("root");
            connectionFactory.setPassword("123456");
            connectionFactory.setVirtualHost("/ems");
            connectionFactory.setPort(5672);
            connectionFactory.setHost("192.168.141.130");
            Connection connection = connectionFactory.newConnection();
            return connection;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static void closeConnectionAndChanel(Channel channel,Connection con){
        try {
            channel.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
