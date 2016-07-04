/*
 * Copyright 2016 Greg Whitaker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.gregwhitaker.sns;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

import java.util.List;

/**
 * Consumes messages from the example SQS queue.  The messages are published to the queue automatically by SNS.
 */
public class Consumer implements Runnable {
    private final String name;
    private final String queueArn;
    private final AmazonSQSClient sqsClient;

    public Consumer(String name, String queueArn) {
        this.name = name;
        this.queueArn = queueArn;
        this.sqsClient = new AmazonSQSClient(new DefaultAWSCredentialsProviderChain());
    }

    @Override
    public void run() {
        String queueName = queueArn.substring(queueArn.lastIndexOf("/"));
        String queueUrl = sqsClient.getQueueUrl(queueName).getQueueUrl();

        while (true) {
            ReceiveMessageResult result = sqsClient.receiveMessage(queueUrl);

            List<Message> messages = result.getMessages();

            if (messages != null && !messages.isEmpty()) {
                for (Message message : messages) {
                    System.out.println(name + ": " + message.getBody());

                    // Acknowledge the message so that it will not be redelivered
                    sqsClient.deleteMessage(queueUrl, message.getReceiptHandle());
                }
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
