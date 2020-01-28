package com.febryo.aws.sandbox.controller;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/sqs")
public class SqsTriggerController {

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.end-point.uri}")
    private String sqsEndPoint;

    @PostMapping("/sendMessageQueue")
    public String write(@RequestBody String notificationData){
        try {
          /*  log.info("===============================================");
            log.info("Getting Started with Amazon SQS Standard Queues");
            log.info("===============================================\n");
            log.info("Sending a message to MyQueue.\n");*/

            Map<String, Object> headers = new HashMap<>();
            // just for fifo
            headers.put("message-group-id", "fifoSQS");
            //queueMessagingTemplate.send(sqsEndPoint,  MessageBuilder.withPayload(notificationData + "-" + System.currentTimeMillis()).build());
            //for fifo
            queueMessagingTemplate.convertAndSend(sqsEndPoint,  MessageBuilder.withPayload(notificationData + "-" + System.currentTimeMillis()).build(), headers);
//            log.info("Message Sent.\n");
        }catch (final AmazonServiceException ase) {
            log.error("Caught an AmazonServiceException, which means " +
                    "your request made it to Amazon SQS, but was " +
                    "rejected with an error response for some reason.");
            log.error("Error Message:    " + ase.getMessage());
            log.error("HTTP Status Code: " + ase.getStatusCode());
            log.error("AWS Error Code:   " + ase.getErrorCode());
            log.error("Error Type:       " + ase.getErrorType());
            log.error("Request ID:       " + ase.getRequestId());
        } catch (final AmazonClientException ace) {
            log.error("Caught an AmazonClientException, which means " +
                    "the client encountered a serious internal problem while " +
                    "trying to communicate with Amazon SQS, such as not " +
                    "being able to access the network.");
            log.error("Error Message: " + ace.getMessage());
        }
        return "ok";
    }

}
