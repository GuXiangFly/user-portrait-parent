## 启动kafka的方式  

#### 1  修改config/server.properties
```properties
# 修改 log dirs 放到指定的目录
# A comma seperated list of directories under which to store log files
log.dirs=/opt/module/kafka/logs/kafka-logs


# 修改zookeeper的链接地址
# root directory for all kafka znodes.
zookeeper.connect=hadoop101:2181
```

#### 2 启动kafka
```bash
bin/kafka-server-start.sh config/server.properties
```

#### 3 启用 topic
```bash
bin/kafka-topics.sh --create --topic attentionProductLog --zookeeper 127.0.0.1:2181 --replication-factor 1 --partitions 1
bin/kafka-topics.sh --create --topic buyCartProductLog --zookeeper 127.0.0.1:2181 --replication-factor 1 --partitions 1
bin/kafka-topics.sh --create --topic collectProductLog --zookeeper 127.0.0.1:2181 --replication-factor 1 --partitions 1
bin/kafka-topics.sh --create --topic scanProductLog --zookeeper 127.0.0.1:2181 --replication-factor 1 --partitions 1
```