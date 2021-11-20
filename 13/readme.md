# kafka-in-microservice-resources


```sh
C:\Users\pc>kafka-topics --bootstrap-server localhost:9092 --create --partitions 1 --replication-factor 1 --topic t.commodity.order
WARNING: Due to limitations in metric names, topics with a period ('.') or underscore ('_') could collide. To avoid issues it is best to use either, but not both.
Created topic t.commodity.order.


C:\Users\pc>kafka-topics --bootstrap-server localhost:9092 --create --partitions 1 --replication-factor 1 --topic t.commodity.promotion
WARNING: Due to limitations in metric names, topics with a period ('.') or underscore ('_') could collide. To avoid issues it is best to use either, but not both.
Created topic t.commodity.promotion.


C:\Users\pc>kafka-console-consumer --bootstrap-server localhost:9092 --topic t.commodity.order --from-beginning
{"creditCardNumber":"9963840851042649","itemName":"Plastic Table","orderDateTime":"2021-11-20 23:55:54","orderLocation":"Jersey","orderNumber":"LTDZO7IK","price":548,"quantity":895}
```

![image](https://user-images.githubusercontent.com/54174687/142736549-3731f2a1-bebb-43a1-b0bf-7e1e34e20257.png)

# H2 Console

![image](https://user-images.githubusercontent.com/54174687/142737212-f38784a2-951a-4995-88cd-ac07d559b684.png)

![image](https://user-images.githubusercontent.com/54174687/142737220-677c5aaa-d022-4af3-9eea-1449fdc2765a.png)

![image](https://user-images.githubusercontent.com/54174687/142737225-1414d025-a65d-401d-a2c6-824cb23bf93e.png)

---------------------------

# Promotion

```
C:\Users\pc>kafka-topics --bootstrap-server localhost:9092 --create --partitions 1 --replication-factor 1 --topic t.commodity.promotion
WARNING: Due to limitations in metric names, topics with a period ('.') or underscore ('_') could collide. To avoid issues it is best to use either, but not both.
Created topic t.commodity.promotion.

C:\Users\pc>kafka-console-consumer --bootstrap-server localhost:9092 --topic t.commodity.promotion --from-beginning
{"promotionCode":"Keyboard181"}
```

![image](https://user-images.githubusercontent.com/54174687/142737671-4daa02da-b89b-4d51-9318-7acfcef1252c.png)

Make all 4 apps up !

# order-kafka

```
[Kafka Order] [2m23:55:55.161[0;39m [32m INFO[0;39m [2m---[0;39m [36morg.apache.kafka.clients.Metadata       [0;39m [2m:[0;39m [Producer clientId=producer-1] Cluster ID: gqueOts7SumhadKhcsKmPg
[Kafka Order] [2m23:55:55.308[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.kafka.broker.producer.OrderProducer [0;39m [2m:[0;39m Just a dummy messsage for order LTDZO7IK, item Plastic Table published successfuly
[Kafka Order] [2m23:55:55.345[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.kafka.broker.producer.OrderProducer [0;39m [2m:[0;39m Order LTDZO7IK, item Plastic Table published successfuly
[Kafka Order] [2m00:13:05.413[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.k.broker.producer.PromotionProducer [0;39m [2m:[0;39m Send result success for message PromotionMessage [promotionCode=Keyboard181]
[Kafka Order] [2m00:18:15.960[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.k.broker.producer.PromotionProducer [0;39m [2m:[0;39m Send result success for message PromotionMessage [promotionCode=methodologies665]
[Kafka Order] [2m00:23:44.582[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.k.b.consumer.OrderReplyListener     [0;39m [2m:[0;39m Reply message received : OrderReplyMessage [replyMessage=Order 0BTHCS9I item Cotton Gloves processed.]
[Kafka Order] [2m00:23:44.583[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.k.b.consumer.OrderReplyListener     [0;39m [2m:[0;39m Reply message received : OrderReplyMessage [replyMessage=Order ENRU38ZF item Rubber Pizza processed.]
[Kafka Order] [2m00:23:44.583[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.k.b.consumer.OrderReplyListener     [0;39m [2m:[0;39m Reply message received : OrderReplyMessage [replyMessage=Order LTDZO7IK item Plastic Table processed.]
```

# kafka-storage

```
[Kafka Storage] [2m00:17:54.018[0;39m [32m INFO[0;39m [2m---[0;39m [36mo.s.k.l.KafkaMessageListenerContainer   [0;39m [2m:[0;39m kafka-storage-cg: partitions assigned: [t.commodity.promotion-0]
[Kafka Storage] [2m00:17:54.120[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.k.broker.consumer.PromotionListener [0;39m [2m:[0;39m Processing promotion : PromotionMessage [promotionCode=Keyboard181]
[Kafka Storage] [2m00:18:15.963[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.k.broker.consumer.PromotionListener [0;39m [2m:[0;39m Processing promotion : PromotionMessage [promotionCode=methodologies665]
[Kafka Storage] [2m00:18:18.913[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.k.broker.consumer.PromotionListener [0;39m [2m:[0;39m Processing discount : DiscountMessage [discountCode=orchid10, discountPercentage=5]
```

# kafka-patterns

```
[Kafka Pattern] [2m00:23:35.324[0;39m [32m INFO[0;39m [2m---[0;39m [36mo.s.k.l.KafkaMessageListenerContainer   [0;39m [2m:[0;39m kafka-pattern-cg: partitions assigned: [t.commodity.order-0, t.commodity.order-1]
[Kafka Pattern] [2m00:23:35.472[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.kafka.broker.consumer.OrderListener [0;39m [2m:[0;39m Processing order 0BTHCS9I, item Cotton Gloves, credit card number 4962436968178102. Total amount for this item is 7938
[Kafka Pattern] [2m00:23:35.473[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.kafka.broker.consumer.OrderListener [0;39m [2m:[0;39m Processing order ENRU38ZF, item Rubber Pizza, credit card number 1254172993537495. Total amount for this item is 122475
[Kafka Pattern] [2m00:23:35.473[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.kafka.broker.consumer.OrderListener [0;39m [2m:[0;39m Processing order LTDZO7IK, item Plastic Table, credit card number 9963840851042649. Total amount for this item is 490460
```

# Kafka-rewards

```
[Kafka Reward] [2m00:23:44.232[0;39m [32m INFO[0;39m [2m---[0;39m [36morg.apache.kafka.clients.Metadata       [0;39m [2m:[0;39m [Producer clientId=producer-1] Cluster ID: gqueOts7SumhadKhcsKmPg
[Kafka Reward] [2m00:23:44.260[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.k.broker.consumer.OrderListenerTwo  [0;39m [2m:[0;39m Processing order ENRU38ZF, item Rubber Pizza, credit card number 1254172993537495
[Kafka Reward] [2m00:23:44.261[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.k.broker.consumer.OrderListenerTwo  [0;39m [2m:[0;39m Headers are :
[Kafka Reward] [2m00:23:44.261[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.k.broker.consumer.OrderListenerTwo  [0;39m [2m:[0;39m   key : surpriseBonus, value : 15
[Kafka Reward] [2m00:23:44.262[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.k.broker.consumer.OrderListenerTwo  [0;39m [2m:[0;39m Surprise bonus is 18371.25
[Kafka Reward] [2m00:23:44.263[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.k.broker.consumer.OrderListenerTwo  [0;39m [2m:[0;39m Processing order LTDZO7IK, item Plastic Table, credit card number 9963840851042649
[Kafka Reward] [2m00:23:44.263[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.k.broker.consumer.OrderListenerTwo  [0;39m [2m:[0;39m Headers are :
[Kafka Reward] [2m00:23:44.263[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.k.broker.consumer.OrderListenerTwo  [0;39m [2m:[0;39m   key : surpriseBonus, value : 15
[Kafka Reward] [2m00:23:44.264[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.k.broker.consumer.OrderListenerTwo  [0;39m [2m:[0;39m Surprise bonus is 73569.0
```
