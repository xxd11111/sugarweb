# sugarcoat

## 介绍

>sugarcoat是严格按照“规范”与“规范实现”这两个概念构建的框架。sugarcoat规范了web应用所需通用功能，并根据所制定规范提供一套可插拔的功能实现。使用者可根据自身需求对默认功能进行拓展和配置，也可以按照sugarcoat规范自定义实现相关功能。

## 框架选型:

- 基础环境：jdk21, maven3.6.3, redis6.x, mysql8.x
- 基础依赖框架：springboot3.x
- 默认实现层技术选型： spring-security, spring-data-jpa, quartz, redisson, lombok, mapstruct, hutool

## 项目结构

| 模组                 | 描述                        | sugarcoat依赖关系           | 开发进度 |
|--------------------|---------------------------|-------------------------|------|
| support-api        | 规范定义                      | 无                       | 进行中  |
| support-cache      | 缓存功能                      | support-api             | 进行中  |
| support-doc        | 接口文档，使用openapi 3.0规范      | support-api             | 进行中  |
| support-email      | 邮件实现，使用springboot-email实现 | support-api             | 进行中  |
| support-sms        | 短信功能，支持阿里云,腾讯云            | support-api             | 进行中  |
| support-oss        | 文件存储，使用通用AWS-S3实现         | support-api             | 进行中  |
| support-scheduler  | 定时任务实现，使用quartz           | support-api             | 未开始  |
| support-orm        | 持久化实现，使用spring-data-jpa实现 | support-api             | 进行中  |
| support-dict       | 数据字典通用功能                  | support-api,support-orm | 进行中  |
| support-parameter      | 系统参数通用功能                  | support-api,support-orm | 进行中  |
| support-server     | 服务端通用功能                   | support-api,support-orm | 进行中  |
| support-protection | 接口防护功能，限流，幂等              | support-api,support-orm | 进行中  |
| uims               | 用户管理实现(sugarcoat使用案例)     | sugarcoat               | 进行中  |

## sugarcoat规范

- common：通用对象规范，接口返回对象，分页参数对象，分页结果适配器，错误码，状态码，标识对象
- exception：异常规范，目前分为脏数据，框架异常，安全异常，服务器异常，检验异常，幂等异常，限流异常
- sms：短信规范，发送短信客户端，发送短信模板对象
- email：邮件规范，发送邮件客户端，发送邮件模板对象
- oss：文件存储服务规范，文件服务客户端，文件信息，文件组策略，文件附件管理，文件附件信息
- data：数据处理规范，敏感词过滤，枚举校验
- orm：持久化处理规范，数据权限，存储加密，多租户
- security：安全规范，用户信息管理
- protection：系统防护规范，幂等，限流，限流策略
- scheduler：定时任务规范，定时任务管理
- dict：数据字典规范，字典组对象，字典项对象，字典编码转化，字典管理，字典校验，内部字典导入
- parameter：参数规范，参数对象，参数管理
- server：服务端规范，接口标识，接口日志，接口对象，接口管理

sugarcoat规范详细说明文档地址：(准备中)

## sugarcoat使用说明

sugarcoat使用说明文档地址：(准备中)

## 安装教程

1. 下载或拉取代码
2. 检测本地环境，建议jdk21，mysql8.x，redis6.x
3. 打开application-dev.yml，配置数据库地址，配置redis地址
4. 项目中使用了querydsl，需要maven执行compile后才能启动
5. uims为sugarcoat使用案例，启动服务后，打开浏览器输入http://localhost:8889/sugarcoat/doc.html

## 交流与讨论

目前正在开发中，欢迎各位大佬加入我们。  

如果有更好的建议，或者遇到什么问题，欢迎各位开发者一起讨论。  

sugarcoat交流qq群：827838560  

