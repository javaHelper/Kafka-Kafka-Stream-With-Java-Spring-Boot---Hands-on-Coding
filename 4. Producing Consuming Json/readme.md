# Producing Consuming and Customizing Json

# add topic
`kafka-topics.sh --bootstrap-server localhost:9092 --create --topic t_employee --partitions 1 --replication-factor 1`

`kafka-topics.bat --bootstrap-server localhost:9092 --create --topic t_employee --partitions 1 --replication-factor 1`

# Kafka console consumer
`kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic t_employee --offset earliest --partition 0`

`kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic t_employee --offset earliest --partition 0`

# Delete topic
`kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic t_employee`

`kafka-topics.bat --bootstrap-server localhost:9092 --delete --topic t_employee`

# Consume 
```>kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic t_employee --offset earliest --partition 0
{"name":"Employee 0","employee_id":"emp-0","birth_date":"2021-Nov-18"}
{"name":"Employee 1","employee_id":"emp-1","birth_date":"2021-Nov-18"}
{"name":"Employee 2","employee_id":"emp-2","birth_date":"2021-Nov-18"}
{"name":"Employee 3","employee_id":"emp-3","birth_date":"2021-Nov-18"}
{"name":"Employee 4","employee_id":"emp-4","birth_date":"2021-Nov-18"}
```

![image](https://user-images.githubusercontent.com/54174687/142470525-bd98e3bf-242c-4c50-92ac-2c38bd6cf1e8.png)
