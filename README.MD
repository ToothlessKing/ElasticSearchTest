#### elasticSearch 以及 elasticSearch-head 安装、启动
1. 官网下载，解压文件到指定目录，如：/opt
2. 启动：
```
##到安装目录下
cd elasticsearch-<version>

bin/elasticsearch
## 后台启动
bin/elasticsearch -d 
```
3、下载elasticsearch-head，可在github上搜索
4、elasticsearch设置跨域
```
vim config/elasticsearch.yml
# elasticsearch中启用CORS
http.cors.enabled: true
# 允许访问的IP地址段，* 为所有IP都可以访问
http.cors.allow-origin: "*"
```
4、下载并启动head
```
git clone git://github.com/mobz/elasticsearch-head.git
cd elasticsearch-head
npm install
npm run start
open http://localhost:9100/
```

#### 使用 TransportClient 操作 ElasticSearch Demo
* 使用spring-boot简单搭建环境
* 所有配置在application.properties中
* 注：9300是tcp通讯端口，集群间和TCPClient都走的它，9200是http协议的RESTful接口
