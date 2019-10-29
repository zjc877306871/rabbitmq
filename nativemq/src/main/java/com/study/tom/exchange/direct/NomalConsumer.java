package com.study.tom.exchange.direct;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NomalConsumer {

    public static final String EXCHAGE_NAME = "tom_alipay";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("106.14.139.151");
        connectionFactory.setUsername("tom");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("enjoyedu");

        //获取链接
        Connection connection = connectionFactory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
        //声明队列
        String queueName = "cat-tom";
        channel.queueDeclare(queueName,true,true,true,null);
        //将队列和交换器进行绑定
        String routKey = "cat";
        channel.queueBind(queueName,EXCHAGE_NAME,routKey);
        System.out.println("等待消息！！！");
        //创建消费者
        final Consumer consumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                String routKey = envelope.getRoutingKey();
                System.out.println("接收到的msg : " + msg + " routKey : " + routKey );
            }
        };

        
        //消费者确认消费队列的消息
        channel.basicConsume(queueName,true,consumer);


    }

}
