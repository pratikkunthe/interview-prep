# AWS and Cloud Services

## AWS Services

### 1. AWS - General questions

**Answer:**

**AWS (Amazon Web Services)** = Cloud computing platform

**Think of it like:** Renting computer resources instead of buying them

**Common AWS Services:**

**1. EC2 (Elastic Compute Cloud)**
- Virtual servers in cloud
- Like renting a computer
- Can scale up/down

**2. S3 (Simple Storage Service)**
- Object storage
- Store files, images, backups
- Highly scalable

**3. RDS (Relational Database Service)**
- Managed databases
- MySQL, PostgreSQL, etc.
- Automatic backups

**4. Lambda**
- Serverless functions
- Run code without servers
- Pay per execution

**5. SQS (Simple Queue Service)**
- Message queuing
- Decouple services
- Reliable messaging

**6. SNS (Simple Notification Service)**
- Notifications
- Pub/sub messaging
- Alerts

**7. CloudWatch**
- Monitoring and logging
- Track metrics
- Set alarms

**8. API Gateway**
- API management
- Create REST APIs
- Handle requests

**9. DynamoDB**
- NoSQL database
- Fast, scalable
- Serverless

**10. VPC (Virtual Private Cloud)**
- Isolated network
- Secure environment
- Control access

**Use Cases:**

**Web Application:**
```
EC2 (servers) → RDS (database) → S3 (storage) → CloudWatch (monitoring)
```

**Serverless Application:**
```
API Gateway → Lambda → DynamoDB → SNS (notifications)
```

**Key Benefits:**
- ✅ Pay as you go
- ✅ Scalable
- ✅ Reliable
- ✅ Global infrastructure

**Simple rule:** AWS = Cloud services. Use what you need, pay for what you use!

---

### 2. What is Cloudwatch

**Answer:**

**CloudWatch** = AWS monitoring and logging service

**Think of it like:** Dashboard that shows what's happening in your AWS resources

**What it does:**

**1. Metrics**
- Collect data from AWS services
- CPU usage, memory, requests, etc.
- View in graphs

**2. Logs**
- Collect application logs
- Centralized logging
- Search and filter

**3. Alarms**
- Set thresholds
- Get notified when exceeded
- Trigger actions

**4. Dashboards**
- Visualize metrics
- Custom dashboards
- Real-time monitoring

**Example:**

```java
// Send custom metrics from Java application
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.StandardUnit;

public class CloudWatchExample {
    
    private AmazonCloudWatch cloudWatch;
    
    public CloudWatchExample() {
        cloudWatch = AmazonCloudWatchClientBuilder.defaultClient();
    }
    
    public void sendCustomMetric(String metricName, double value) {
        MetricDatum datum = new MetricDatum()
            .withMetricName(metricName)
            .withValue(value)
            .withUnit(StandardUnit.Count);
        
        PutMetricDataRequest request = new PutMetricDataRequest()
            .withNamespace("MyApplication")
            .withMetricData(datum);
        
        cloudWatch.putMetricData(request);
    }
    
    // Usage
    public void processOrder(Order order) {
        // Process order
        process(order);
        
        // Send metric
        sendCustomMetric("OrdersProcessed", 1.0);
    }
}
```

**Common Metrics:**

- **EC2:** CPU utilization, network in/out, disk read/write
- **RDS:** Database connections, CPU, storage
- **Lambda:** Invocations, errors, duration
- **S3:** Requests, storage size

**Alarms:**

```java
// Set alarm when CPU > 80%
// Action: Send email notification
// Action: Auto-scale EC2 instances
```

**Logs:**

```java
// Application logs go to CloudWatch Logs
logger.info("Order processed: {}", orderId);
// View in CloudWatch Logs console
```

**Benefits:**
- ✅ Centralized monitoring
- ✅ Real-time alerts
- ✅ Historical data
- ✅ Integration with other AWS services

**Simple rule:** CloudWatch = Monitor everything. Track metrics, collect logs, set alarms!

---

### 3. Features of Lambda and drawbacks of Lambda

**Answer:**

**AWS Lambda** = Serverless compute service (run code without managing servers)

**Think of it like:** Hiring temporary workers - they work when needed, you only pay for work done

**Features:**

**1. Serverless**
- No server management
- AWS handles everything
- Just write code

**2. Auto-scaling**
- Scales automatically
- Handles any number of requests
- No capacity planning

**3. Pay per Use**
- Pay only for execution time
- No charges when idle
- Cost-effective

**4. Event-Driven**
- Triggers on events
- S3 upload, API call, schedule, etc.

**5. Multiple Languages**
- Java, Python, Node.js, etc.
- Choose your language

**6. Integration**
- Works with many AWS services
- Easy to connect

**Example:**

```java
// Lambda function (Java)
public class OrderProcessor implements RequestHandler<OrderEvent, String> {
    
    @Override
    public String handleRequest(OrderEvent event, Context context) {
        // Process order
        Order order = event.getOrder();
        processOrder(order);
        
        // Send notification
        sendNotification(order);
        
        return "Order processed: " + order.getId();
    }
    
    private void processOrder(Order order) {
        // Business logic
    }
    
    private void sendNotification(Order order) {
        // Send SNS notification
    }
}
```

**Use Cases:**

**1. API Backend**
```
API Gateway → Lambda → DynamoDB
```

**2. File Processing**
```
S3 Upload → Lambda → Process File → Save to S3
```

**3. Scheduled Tasks**
```
CloudWatch Events → Lambda → Run Task
```

**4. Event Processing**
```
SQS Message → Lambda → Process Message
```

**Drawbacks:**

**1. Cold Start**
- First request slower (initialization)
- Can be 1-5 seconds
- Not good for real-time

**2. Execution Time Limit**
- Maximum 15 minutes
- Not for long-running tasks
- Need to break into smaller tasks

**3. Memory Limits**
- Maximum 10GB memory
- May not be enough for heavy processing

**4. Stateless**
- No persistent storage
- Can't maintain state between invocations
- Use external storage (DynamoDB, S3)

**5. Debugging**
- Harder to debug
- Limited local testing
- Need to deploy to test

**6. Vendor Lock-in**
- Tied to AWS
- Hard to migrate
- AWS-specific code

**7. Cost for High Volume**
- Can be expensive at scale
- Pay per invocation
- May be cheaper with EC2 for constant load

**Comparison:**

| Feature | Lambda | EC2 |
|---------|--------|-----|
| Server Management | ❌ No | ✅ Yes |
| Scaling | ✅ Automatic | ⚠️ Manual |
| Cost Model | Pay per use | Pay for running |
| Cold Start | ❌ Yes | ✅ No |
| Execution Time | 15 min max | Unlimited |
| Best For | Event-driven, sporadic | Constant load |

**When to use Lambda:**

**Good for:**
- ✅ Event-driven tasks
- ✅ Sporadic workloads
- ✅ API backends
- ✅ File processing
- ✅ Scheduled tasks

**Not good for:**
- ❌ Long-running tasks
- ❌ Real-time applications (cold start)
- ❌ Applications needing persistent connections
- ❌ Heavy processing requiring lots of memory

**Simple rule:** Lambda = Great for event-driven, short tasks. Not good for long-running or real-time needs!
