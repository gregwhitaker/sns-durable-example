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

/**
 * Runs the Durable SNS example.
 */
public class ExampleRunner {

    /**
     * Main entry-point for this example.  This starts the producer and subscriber applications.
     *
     * @param args command line arguments
     * @throws Exception
     */
    public static void main(String... args) throws Exception {
        Producer.main();
        Subscriber.main();

        Thread.currentThread().join();
    }
}
