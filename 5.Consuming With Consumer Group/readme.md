# Producing and Consuming With Consumer Groups

# add topic
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic t_commodity --partitions 1 --replication-factor 1

kafka-topics.bat --bootstrap-server localhost:9092 --create --topic t_commodity --partitions 1 --replication-factor 1

# Kafka console consumer
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic t_commodity --offset earliest --partition 0

kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic t_commodity --offset earliest --partition 0

# describe consumer group (change the group name as required)
```kafka-consumer-groups.bat --bootstrap-server localhost:9092 --group cg-dashboard --describe

Consumer group 'cg-dashboard' has no active members.

GROUP           TOPIC           PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID     HOST            CLIENT-ID
cg-dashboard    t_commodity     0          56              56              0               -               -               -
```


# reset offset of consumer group
# topic format is topic:partition, e.g. : t_some_topic:0,2,4
# topic format without partition (e.g. : t_my_topic) will reset all partitions
```kafka-consumer-groups.bat --bootstrap-server localhost:9092 --group cg-dashboard --execute --reset-offsets --to-offset 10 --topic t_commodity:0

GROUP                          TOPIC                          PARTITION  NEW-OFFSET
cg-dashboard                   t_commodity                    0          10
```

```
kafka-consumer-groups.bat --bootstrap-server localhost:9092 --group cg-dashboard --describe

Consumer group 'cg-dashboard' has no active members.

GROUP           TOPIC           PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID     HOST            CLIENT-ID
cg-dashboard    t_commodity     0          10              56              46              -               -               -
```

Note - Even if we put a lag in consume, `Thread.sleep(ThreadLocalRandom.current().nextLong(500, 1000));` still both the consumer do not block each other and works fine

![image](https://user-images.githubusercontent.com/54174687/142476808-5b030626-0da3-43a5-9842-bbee95dcee4d.png)
