## 简介

清洗推送到kafka的csv和json数据,新数据以avro格式推送到kafka相关主题．
整个过程用kafka streams实现.

### 特性

- csv, json数据清洗．
- kafka streams处理数据．

## 使用说明

### 编译打包

```
git clone https://github.com/hnlaomie/log_stream_v2.git
cd log_stream_v2
gradle shadowJar
```
