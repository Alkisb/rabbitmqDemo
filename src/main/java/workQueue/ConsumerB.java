package workQueue;

import com.rabbitmq.client.*;
import utils.RabbitmqUtil;

import java.io.IOException;

public class ConsumerB {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitmqUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("work",true,false,false,null);

        /**\
         *  默认autoAck是true  如果设置成true 代表消费者收到mq的消息就ack 不管回调方法有没有执行完成
         *  这样做照成的结果就是 队列里的消息会平均分给每个消费者
         *  但是这些消息只要mq一发送出去就能收到消费者的ack数据包 mq就会默认的认为消息已经被消费了, 如果这时候消费者宕机 就会造成消息丢失
         */
        //设置消费者一次只能消费一条消息
        channel.basicQos(1);
        channel.basicConsume("work",false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("consumer2->" +new String(body));
                //回调方法执行完成手动回调 argument1 : 手动确认消息标识 argument2 : false每次确认一个
                channel.basicAck(envelope.getDeliveryTag(),false);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
