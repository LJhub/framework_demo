package com.test.demo;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author MyPC
 */
@Component
public class QueueProducer {

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private ActiveMQQueue queueTextDestination;
    @Autowired
    private ActiveMQTopic topicTextDestination;

    public void sendMessageText(String text){
        jmsTemplate.send(topicTextDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(text);
            }
        });
    }
}
