# Kafka Configurations with rebalancing 

```
E:\>kafka-topics.bat --bootstrap-server localhost:9092 --create --topic t_rebalance_new --partitions 1 --replication-factor 1
WARNING: Due to limitations in metric names, topics with a period ('.') or underscore ('_') could collide. To avoid issues it is best to use either, but not both.
Created topic t_rebalance_new.

E:\>kafka-topics.bat --bootstrap-server localhost:9092 --alter --topic t_rebalance_new --partitions 2

E:\>kafka-topics.bat --bootstrap-server localhost:9092 --alter --topic t_rebalance_new --partitions 2
Error while executing topic command : Topic already has 2 partitions.
[2021-11-19 00:26:53,096] ERROR org.apache.kafka.common.errors.InvalidPartitionsException: Topic already has 2 partitions.
 (kafka.admin.TopicCommand$)

E:\>kafka-topics.bat --bootstrap-server localhost:9092 --alter --topic t_rebalance_new --partitions 3

```
