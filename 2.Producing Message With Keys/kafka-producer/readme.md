# Producing Message with Keys!

# create topic
```sh
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic t_multi_partitions --partitions 3 --replication-factor 1

kafka-topics.bat --bootstrap-server localhost:9092 --create --topic t_multi_partitions --partitions 3 --replication-factor 1
```

# describe topic
```sh
kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic t_multi_partitions

kafka-topics.bat --bootstrap-server localhost:9092 --describe --topic t_multi_partitions
```

# Kafka console consumer (change the partition number as needed)
```sh
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic t_multi_partitions --offset earliest --partition 0

kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic t_multi_partitions --offset earliest --partition 0

kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic t_multi_partitions --offset earliest --partition 1

kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic t_multi_partitions --offset earliest --partition 2
```

![image](https://user-images.githubusercontent.com/54174687/142462235-52bdafa7-8939-46a2-a21d-ebebdb29a516.png)
