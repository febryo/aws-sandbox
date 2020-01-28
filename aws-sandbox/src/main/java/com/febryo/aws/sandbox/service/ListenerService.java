package com.febryo.aws.sandbox.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ListenerService {

    @Value("${cloud.aws.end-point.uri}")
    private String sqsEndPoint;

    @SqsListener(value = "sqs-sandbox.fifo")
    public void getMessage(String message) {
        log.info("Message from SQS Queue - "+ System.currentTimeMillis()+" -- "+message);
        try{
            Thread.sleep(5000);
        }catch (Exception e){

        }

    }

}
