package com.study.tom.exchange.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DirectProducter {

    public static final String EXCHAGE_NAME = "tom_alipay";

    public static void main(String[] args) throws IOException, TimeoutException {

        //声明链接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("106.14.139.151");
        //获取链接
        Connection connection = connectionFactory.newConnection();
        //创建信道
        Channel channel = connection.createChannel();
        //在信道中声明交换器
        channel.exchangeDeclare(EXCHAGE_NAME, BuiltinExchangeType.DIRECT);
        //创建路由键和消息体
        String[] routKeys = {"cat", "dog", "rabbit"};
        for(int i=0; i<30; i++){
            String routKey = routKeys[i%3];
            String msg = "helllo rabbit " + i;
            //发布消息
            channel.basicPublish(EXCHAGE_NAME,routKey,null,msg.getBytes());
            System.out.println("send : " + routKey + " msg : " + msg);
        }

        //队列由消费者创建
        channel.close();;
        connection.close();


    }
}
