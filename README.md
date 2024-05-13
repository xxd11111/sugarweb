# sugarweb

## 介绍

>
sugarweb采用组件化构架设计，整合了web应用所需通用组件。

## 技术选型

- 基础环境：jdk21, maven3.8.8, redis6.x, mysql8.x
- 基础依赖框架：springboot3.x, spring-security, spring-data-jpa, redisson, guava, lombok
- 组件技术选型： quartz, aws-s3, springdoc-openapi, spring-boot-starter-mail

## 项目结构

| 模组                   | 描述           | 模块依赖关系                        |
|----------------------|--------------|-------------------------------|
| base-framework       | 基础框架整合       | 无                             |
| component-dictionary | 数据字典组件       | base-framework                |
| component-email      | 邮件组件         | base-framework, component-oss |
| component-openapi    | 接口文档         | base-framework                |
| component-oss        | 文件存储         | base-framework                |
| component-param      | 系统参数组件       | base-framework                |
| component-scheduler  | 定时任务组件       | base-framework                |
| component-server     | 服务组件         | base-framework                |
| service-uims         | 用户管理实现(使用案例) | 全部                            |

## sugarweb做了什么

- base-framework：这里整合了主要常用核心框架并进行了通用功能封装，涉及异常处理，持久化框架整合，缓存框架整合，授权认证整合。
- component-dictionary：字典组件，提供了字典（group,code,name）的增删改查功能，自动注册功能。
- component-email：邮件组件，提供了邮件发送的基础功能。
- component-openapi：接口文档组件，提供对外接口信息。
- component-oss：文件存储组件，提供文件存储功能，以及文件与业务绑定功能。
- component-param：参数组件，提供了参数（key,value）的增删改查功能，自动注册功能。
- component-scheduler：定时任务组件，提供了定时任务注册，启停，执行功能，自动注册功能，动态参数配置。
- component-server：服务组件，提供了服务端访问请求日志功能，异常日志功能。
- service-uims：用户管理服务，提供用户管理，角色管理，菜单管理，登录登出接口。

## sugarweb使用说明

这是一套使用整合方案，并不建议使用maven整合，而是直接下载源码或者拉取分支根据自身业务情况进行部分调整后使用。

## 安装教程

1. 直接下载或使用git拉取代码。
2. 检测本地环境，建议jdk21, maven3.8.8, redis6.x, mysql8.x。
3. 修改service-uims模块下的application-dev.yml，配置数据库，redis，minio信息。
4. 项目中使用了querydsl代码生成技术，需要maven执行compile后才能启动。
5. 启动服务后，使用接口信息url配合接口调用工具进行测试，http://localhost:8889/uims/v3/api-docs

## 交流与讨论

如果遇到什么问题，或者有更好的建议，欢迎进群讨论。

sugar交流qq群：827838560。

