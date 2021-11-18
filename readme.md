# Kafka-Kafka-Stream-With-Java-Spring-Boot---Hands-on-Coding (Udemy)

# Steps to start the zookeeper and Kafka on windows

Download the latest version of Kakfa from `https://kafka.apache.org/downloads`

Go to `C:\kafka_2.13-3.0.0\config` and change the path in `server.properties` and `zookeeper.properties`.

Now, go to `C:\kafka_2.13-3.0.0` and type below commands to start zookeeper first then kafka

```
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

.\bin\windows\kafka-server-start.bat .\config\server.properties
```

# Kill the Process in windows

```
C:\WINDOWS\system32>netstat -ano | find "9092"
  TCP    0.0.0.0:9092           0.0.0.0:0              LISTENING       3196

C:\WINDOWS\system32>taskkill /F /PID 3196
SUCCESS: The process with PID 3196 has been terminated.

C:\WINDOWS\system32>
```
