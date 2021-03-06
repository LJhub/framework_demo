package com.test.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext-jms-producer.xml")
public class QueueProducerTest {

    @Autowired
    private QueueProducer queueProducer;

    @Test
    public void sendMessageText() {
        queueProducer.sendMessageText("发送spring-jms 点对点消息");
    }
}