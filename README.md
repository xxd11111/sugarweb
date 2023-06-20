# sugarcoat

## 介绍

>sugarcoat提供一组通用web应用api以及默认实现，方便快捷的构建web应用，真正做到开箱即用，直接编写业务代码，免去重复造轮子的问题

## 框架选型:

springboot+springSecurity+jpa+quartz+redisson

## 项目结构

| 模组        | 描述         | 依赖组件             |
|-----------|------------|------------------|
| api       | 功能api规范    | 无                |
| cache     | 缓存实现       | redisson         |
| doc       | 接口文档实现     | openapi,knife4j  |
| email     | 邮件实现       | springboot-email |
| sms       | 短信实现       | aliyun,tencent   |
| oss       | 文件存储实现     | aws-s3           |
| scheduler | 定时任务实现     | quartz           |
| orm       | 持久化实现      | jpa              |
| dict      | 字典实现       | orm              |
| param     | 系统参数实现     | orm              |
| server    | 服务端实现      | orm              |
| uims      | 用户界面实现(案例) | 以上全部             |

## 功能清单

服务端：接口返回对象，通用分页对象，错误码，状态码，通用异常类，接口日志，错误日志，应用接口文档，接口信息  
定时任务:quartz  
安全防护：敏感数据处理，幂等，限流，防重提交  
第三方接入：邮件功能，短信功能  
文件存储：亚马逊s3规范  
持久化：jpa，数据权限，数据存储加密，多租户  
数据字典：字典功能实现，字典校验  
系统参数:功能实现  
缓存:redisson  

#### 依赖关系图

一张图片

## 安装教程

1. xxxx
2. xxxx
3. xxxx

## 使用说明

1. xxxx
2. xxxx
3. xxxx

## 参与贡献

## 交流与讨论
qq群：

