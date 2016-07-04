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
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import rx.Observable;
import rx.RxReactiveStreams;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Sends messages to the example sns topic at a set interval which are then forwarded by AWS to the example SQS queue
 * for durable storage.
 */
public class Producer implements Runnable {
    private final String name;
    private final String topicArn;
    private final AmazonSNSClient snsClient;

    public Producer(String name, String topicArn) {
        this.name = name;
        this.topicArn = topicArn;
        this.snsClient = new AmazonSNSClient(new DefaultAWSCredentialsProviderChain());
        this.snsClient.setRegion(Region.getRegion(Regions.US_WEST_2));
    }

    @Override
    public void run() {
        final CountDownLatch latch = new CountDownLatch(Integer.MAX_VALUE);

        RxReactiveStreams.toPublisher(Observable.interval(1_000, TimeUnit.MILLISECONDS)
                                        .onBackpressureDrop()
                                        .map(i -> "This is message " + i))
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Integer.MAX_VALUE);
                    }

                    @Override
                    public void onNext(String message) {
                        System.out.println(name + ": " + message);
                        snsClient.publish(topicArn, message, "Durable SNS Topic Example");
                        latch.countDown();
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        latch.countDown();
                    }

                    @Override
                    public void onComplete() {
                        latch.countDown();
                    }
                });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
