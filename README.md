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

1. 修改启动模块下的application-dev.yml，配置mysql（必填），redis（必填），milvus(可选)，ollama，tts，blbl配置。
2. 进入数据库执行sql脚本, scripts/ChatAssisitant-DDL.sql

## 交流与讨论

如果遇到什么问题，或者有更好的建议，欢迎进群讨论。

sugar交流qq群：827838560。

