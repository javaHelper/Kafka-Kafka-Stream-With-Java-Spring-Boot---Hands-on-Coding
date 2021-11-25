# Kafka In Microservices

- Start the `order-kafka`

![image](https://user-images.githubusercontent.com/54174687/143459193-b838cd54-bca0-4fb1-a7df-c71d7efdb629.png)

STS Console logs

```
[Kafka Order] [2m19:58:19.839[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.kafka.broker.producer.OrderProducer [0;39m [2m:[0;39m Just a dummy messsage for order JFM1MWJM, item Cotton Table published successfuly
[Kafka Order] [2m19:58:19.905[0;39m [32m INFO[0;39m [2m---[0;39m [36mc.c.kafka.broker.producer.OrderProducer [0;39m [2m:[0;39m Order JFM1MWJM, item Cotton Table published successfuly
```

Kafka Console 

```
E:\kafka_2.13-2.7.0>kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic t.commodity.order
{"creditCardNumber":"5789557688969592","itemName":"Cotton Table","orderDateTime":"2021-11-25 19:58:19","orderLocation":"Belgium","orderNumber":"JFM1MWJM","price":286,"quantity":527}
```

`Jdbc UrL = jdbc:h2:mem:kafkaorderdb`

![image](https://user-images.githubusercontent.com/54174687/143459923-72735758-3ebb-4889-b73f-a48a3d681e6d.png)


![image](https://user-images.githubusercontent.com/54174687/143460019-56671a1a-f01c-44c8-a246-49892dcf760b.png)

![image](https://user-images.githubusercontent.com/54174687/143460094-4d783d91-c7b1-4915-9dc4-594d76024ef6.png)

- Start kafka-patterns

![image](https://user-images.githubusercontent.com/54174687/143461735-d203dbac-ceab-403c-9470-6dc6906f48e2.png)


