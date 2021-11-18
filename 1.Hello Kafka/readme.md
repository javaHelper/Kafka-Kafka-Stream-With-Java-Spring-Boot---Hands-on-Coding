# Hello World

```
C:\Users\pc>kafka-topics.bat --bootstrap-server localhost:9092 --create --topic t_hello --partitions 1 --replication-factor 1
WARNING: Due to limitations in metric names, topics with a period ('.') or underscore ('_') could collide. To avoid issues it is best to use either, but not both.
Created topic t_hello.

C:\Users\pc>kafka-topics.bat --bootstrap-server localhost:9092 --list
t_hello

C:\Users\pc>kafka-topics.bat --bootstrap-server localhost:9092 --describe --topic t_hello
Topic: t_hello  PartitionCount: 1       ReplicationFactor: 1    Configs: segment.bytes=1073741824
        Topic: t_hello  Partition: 0    Leader: 0       Replicas: 0     Isr: 0

C:\Users\pc>kafka-console-consumer.bat --bootstrap-server localhost:9092  --topic t_hello --from-beginning
Hello Timotius 0.8577710395619949
Hello Timotius 0.8751078039943258
```

# Some commands

NOTE : 
- See lecture with title "Executing Kafka Commands" if you need guidance to execute commands
- If you use windows, change the .sh extension to .bat

# For Windows
```
kafka-topics.bat --bootstrap-server localhost:9092 --create --topic t_hello --partitions 1 --replication-factor 1

kafka-topics.bat --bootstrap-server localhost:9092 --list

kafka-topics.bat --bootstrap-server localhost:9092 --describe --topic t_hello

kafka-console-consumer.bat --bootstrap-server localhost:9092  --topic t_hello --from-beginning

Token - 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
```
----------------------------
# create topic t_hello
`kafka-topics.sh --bootstrap-server localhost:9092 --create --topic t_hello --partitions 1 --replication-factor 1`

# list topic
`kafka-topics.sh --bootstrap-server localhost:9092 --list`

# describe topic
`kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic t_hello`

# create topic t_test
`kafka-topics.sh --bootstrap-server localhost:9092 --create --topic t_test --partitions 1 --replication-factor 1`

# delete topic t_test
`kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic t_test`
