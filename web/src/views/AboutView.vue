<template>
  <div class="about">
    <n-card title="关于SugarWeb">
      <n-space vertical>
        <p>SugarWeb是一个组件化架构设计的Web应用通用整合方案，旨在提供Web应用所需的通用组件和基础功能。</p>
        
        <n-card title="技术架构">
          <ul>
            <li>模块化架构：各组件独立开发、部署，通过依赖管理集成</li>
            <li>自动配置模式：通过AutoConfiguration类实现组件自动注册</li>
            <li>配置驱动模式：使用Properties类管理组件配置</li>
            <li>依赖注入模式：基于Spring Boot的IoC容器管理组件依赖</li>
          </ul>
        </n-card>
        
        <n-card title="技术选型">
          <n-grid cols="2" :x-gap="12" :y-gap="12">
            <n-grid-item>
              <p><strong>前端:</strong> Vue 3 + TypeScript + Vite</p>
              <p><strong>后端:</strong> Spring Boot 3.2.5</p>
              <p><strong>数据库:</strong> MySQL 8.x</p>
              <p><strong>缓存:</strong> Redis 6.x</p>
            </n-grid-item>
            <n-grid-item>
              <p><strong>对象存储:</strong> MinIO、AWS S3</p>
              <p><strong>日志:</strong> Logback</p>
              <p><strong>文档:</strong> SpringDoc OpenAPI 2.2.0</p>
              <p><strong>任务调度:</strong> Quartz</p>
            </n-grid-item>
          </n-grid>
        </n-card>
      </n-space>
    </n-card>
  </div>
</template>

<script setup lang="ts">
import { NCard, NSpace, NGrid, NGridItem } from 'naive-ui'
</script>

<style scoped>
.about {
  padding: 20px;
}

ul {
  padding-left: 20px;
}

li {
  margin-bottom: 8px;
  line-height: 1.5;
}
</style>