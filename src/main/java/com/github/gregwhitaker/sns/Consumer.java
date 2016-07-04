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

    }
}
