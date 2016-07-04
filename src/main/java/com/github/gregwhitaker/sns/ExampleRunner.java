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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Runs the Durable SNS subscription example.
 */
public class ExampleRunner {

    /**
     * Main entry-point for this example.  This starts the producer and consumer applications.
     *
     * @param args command line arguments
     * @throws Exception
     */
    public static void main(String... args) throws Exception {
        if (System.getProperty("topicArn") == null) {
            throw new RuntimeException("The arn of the example topic is required to run this demo!");
        }

        if (System.getProperty("queueArn") == null) {
            throw new RuntimeException("The arn of the example queue is required to run this demo!");
        }

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(new Producer("Producer1", System.getProperty("topicArn")));
        executor.execute(new Consumer("Consumer1", System.getProperty("queueArn")));

        Thread.currentThread().join();
    }
}
