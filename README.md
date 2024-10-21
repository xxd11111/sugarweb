# sugarweb

## 介绍

>
sugarweb采用组件化构架设计，整合了web应用所需通用组件。

## 技术选型

- 基础环境：jdk21, maven3.8.8, redis6.x, mysql8.x, minio
- 基础依赖框架：springboot3.x, satoken, mybatis-plus, redisson, hutool, lombok
- 组件使用框架：aws-s3, springdoc-openapi, spring-boot-starter-mail, quartz

## 项目结构

| 模组                    | 描述     | 模块间依赖关系             |
|-----------------------|--------|---------------------|
| base-framework        | 基础框架整合 | 无                   |
| component-dictionary  | 字典组件   | base-framework      |
| component-email       | 邮件组件   | base-framework      |
| component-openapi     | 接口文档   | base-framework      |
| component-oss         | 文件存储   | base-framework      |
| component-param       | 系统参数组件 | base-framework      |
| component-server      | 服务组件   | base-framework      |
| component-task        | 定时任务组件 | base-framework      |
| service-uims          | 用户管理服务 | framework,component |
| service-chatAssistant | 数字人平台  | framework,component | 

## sugarweb做了什么

- base-framework：这里整合了主要常用核心框架并进行了通用功能封装，涉及异常处理，持久化框架整合，缓存框架整合，授权认证整合。
- component-dictionary：字典组件，提供了字典的增删改查功能，自动注册功能。
- component-email：邮件组件，提供了邮件发送的基础功能。
- component-openapi：接口文档组件，提供对外接口信息。
- component-oss：文件存储组件，提供文件存储功能，以及文件与业务绑定功能。
- component-param：参数组件，提供了系统参数的增删改查功能，自动注册功能。
- component-task：定时任务组件，提供了定时任务注册，启停，执行功能，自动注册功能，动态参数配置。
- component-server：服务组件，提供了服务端访问请求日志功能，异常日志功能。
- service-uims：用户管理服务，提供用户管理，角色管理，菜单管理，登录登出接口。

## sugarweb使用说明

这是一套通用整合方案，建议直接下载源码直接使用，根据自身情况进行调整。

## 安装教程

1. 直接下载或使用git拉取代码。
2. 检测本地环境，建议jdk21, maven3.8.8, redis6.x, mysql8.x。

### service-uims(案例项目)

1. 修改启动模块下的application-dev.yml，配置mysql（必填），redis（必填）信息。
2. 进入数据库执行sql脚本, scripts/ChatAssisitant-DDL.sql
3. 启动服务后，使用接口信息url配合接口调用工具进行测试，http://localhost:8080/v3/api-docs

### service-chatAssistant(数字人平台, 可独立使用)
1. 开发环境
```angular2html
jdk21, maven3.8.8, redis6.x, mysql8.x
ollama安装，关于ollama的使用请参考ollama官网，项目使用的模型为qwen2.5:7b，nomic-embed-text，读者可以根据自己的配置调整。
语音播报部分api使用的是VLC播放器，请自行安装。https://www.videolan.org/vlc/  https://get.videolan.org/vlc/3.0.21/win64/vlc-3.0.21-win64.exe
文本转语音api使用的是chattts项目，请自行安装。https://github.com/jianchang512/ChatTTS-ui/releases/tag/v1.0
```
2. 修改启动模块下的application-dev.yml，配置mysql（必填），redis（必填），milvus(可选)，ollama，tts，blbl配置。
```angular2html
目前只适配ollama，milvus向量库，如果需要其他模型及向量库，请修改ChatAssistantConfiguration类；
blbl的cookie，登录账号网页打开F12查看 
  chat-assistant:
    # 大模型配置
    llm-type: ollama
    ollama:
      base-url: 'http://localhost:11434'
      chat-model:
        model-name: 'qwen2.5:7b'
        temperature: 0.75
        timeout: 60000
      embedding-model:
        model-name: 'nomic-embed-text'
        timeout: 60000

    # 向量库配置，默认设置的是内存向量库。
    vector-store-type: in_memory
    # milvus向量库配置方式
    # vector-store-type: milvus
    # milvus:
    #   url: 'http://192.168.193.151:19530'
    #   username: 'root'
    #   password: 'milvus'
    #   database-name: 'default'
    #   collection-name: 'vector_store'
    #   dimension: 768
    #   consistency-level: strong
    #   metric-type: COSINE

    # blbl客户端配置
    blbl-client:
      # 进blbl浏览器上的cookie
      cookie:
      # 直播房间号
      room-id: 2470538
      # 自己的uid
      self-uid: 20047313
```
3. 进入数据库执行sql脚本, scripts/ChatAssisitant-DDL.sql

## 交流与讨论

如果遇到什么问题，或者有更好的建议，欢迎进群讨论。

sugar交流qq群：827838560。

