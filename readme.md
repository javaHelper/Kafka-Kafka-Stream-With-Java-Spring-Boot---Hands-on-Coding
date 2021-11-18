# Kafka-Kafka-Stream-With-Java-Spring-Boot---Hands-on-Coding (Udemy)

# Steps to start the zookeeper and Kafka on windows

Download the latest version of Kakfa from `https://kafka.apache.org/downloads`

Go to `C:\kafka_2.13-3.0.0\config` and change the path in `server.properties` and `zookeeper.properties`.

Now, go to `C:\kafka_2.13-3.0.0` and type below commands to start zookeeper first then kafka

```
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

.\bin\windows\kafka-server-start.bat .\config\server.properties
```

