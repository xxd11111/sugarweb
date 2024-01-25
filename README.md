# sugarweb

## 介绍

>
sugarweb是按照“框架”与“组件”这两个概念构建的一套整合方案。sugarweb整合了web应用所需通用框架，并根据业务共性提供一套可插拔的组件实现。

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

- framework：通用对象规范，接口返回对象，分页参数对象，分页结果适配器，错误码，状态码，标识对象
- exception：异常规范，目前分为脏数据，框架异常，安全异常，服务器异常，检验异常，幂等异常，限流异常
- sms：短信规范，发送短信客户端，发送短信模板对象
- email：邮件规范，发送邮件客户端，发送邮件模板对象
- oss：文件存储服务规范，文件服务客户端，文件信息，文件组策略，文件附件管理，文件附件信息
- data：数据处理规范，敏感词过滤，枚举校验
- orm：持久化处理规范，数据权限，存储加密，多租户
- security：安全规范，用户信息管理
- scheduler：定时任务规范，定时任务管理
- dict：数据字典规范，字典组对象，字典项对象，字典编码转化，字典管理，字典校验，内部字典导入
- parameter：参数规范，参数对象，参数管理
- server：服务端规范，接口标识，接口日志，接口对象，接口管理

sugarcoat规范详细说明文档地址：(准备中)

## sugarcoat使用说明

sugarcoat使用说明文档地址：(准备中)

## 安装教程

1. 下载或拉取代码
2. 检测本地环境，建议jdk21, maven3.8.8, redis6.x, mysql8.x
3. 打开application-dev.yml，配置数据库，redis，minio
4. 项目中使用了querydsl代码生成技术，需要maven执行compile后才能启动
5. uims为sugarweb使用案例，启动服务后，打开浏览器输入http://localhost:8889/uims/doc.html

## 交流与讨论

如果你热爱技术，欢迎加入我们。

如果遇到什么问题，或者有更好的建议，欢迎进群讨论。

sugarweb交流qq群：827838560。

