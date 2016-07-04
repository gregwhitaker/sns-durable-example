sns-durable-example
===

[![Build Status](https://travis-ci.org/gregwhitaker/sns-durable-example.svg?branch=master)](https://travis-ci.org/gregwhitaker/sns-durable-example)

This example shows you how to durably subscribe to messages from an AWS [Simple Notification Service](https://aws.amazon.com/sns/) topic.

The example creates an SNS topic that is consumed and stored by an SQS queue.  This allows the subscriber to read the events from SNS
off of the SQS queue where they are durably stored.  The subscriber need not worry about message loss in the case of a disconnect 
because the SQS queue is storing the notifications.

##Running the Example
The example can be run using the following gradle command:

```
$ ./gradlew run -DtopicArn={sns topic arn} -DqueueArn={sqs queue arn}
```

##License
Copyright 2016 Greg Whitaker

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
