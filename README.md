## 简介

清洗推送到kafka的csv和json数据,新数据以avro格式推送到kafka相关主题．
整个过程用kafka streams实现.

### 特性

- csv, json数据清洗．
- kafka streams处理数据．

## 使用说明

启动应用程序,向主题"testdata"写入测试数据
```
kafka-console-producer --broker-list 192.168.18.8:9092 --topic testdata
```
从主题"datalogs"查看avro格式数据
```
kafka-avro-console-consumer --property schema.registry.url=http://192.168.18.8:8081 --bootstrap-server 192.168.18.8:9092 --topic datalogs
```

### 编译打包

```
git clone https://github.com/hnlaomie/log_stream_v2.git
cd log_stream_v2
gradle shadowJar
```
