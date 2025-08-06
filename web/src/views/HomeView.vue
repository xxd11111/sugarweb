<script setup lang="ts">
import { ref } from 'vue'
import { NButton, NCard, NSpace } from 'naive-ui'
import { ErrorCode, createAppError } from '@/plugins/errorHandler.ts'

// 模拟不同类型的错误
const triggerNotFoundError = () => {
  const error = createAppError(
    ErrorCode.NOT_FOUND,
    '请求的资源不存在',
    { resourceId: '12345' }
  )
  
  // 使用全局错误处理器处理错误
  const errorHandler = (window as any).__VUE_APP__.$errorHandler
  if (errorHandler) {
    errorHandler.handleError(error)
  }
}

const triggerServerError = () => {
  const error = createAppError(
    ErrorCode.SERVER_ERROR,
    '服务器内部错误，请稍后重试',
    { statusCode: 500 }
  )
  
  const errorHandler = (window as any).__VUE_APP__.$errorHandler
  if (errorHandler) {
    errorHandler.handleError(error)
  }
}

const triggerNetworkError = () => {
  const error = createAppError(
    ErrorCode.NETWORK_ERROR,
    '网络连接异常，请检查网络设置',
    { url: '/api/data' }
  )
  
  const errorHandler = (window as any).__VUE_APP__.$errorHandler
  if (errorHandler) {
    errorHandler.handleError(error)
  }
}

const triggerJsError = () => {
  try {
    // 故意触发一个 JavaScript 错误
    throw new Error('这是一个模拟的 JavaScript 错误')
  } catch (error) {
    const errorHandler = (window as any).__VUE_APP__.$errorHandler
    if (errorHandler) {
      errorHandler.handleError(error)
    }
  }
}

const triggerUnknownError = () => {
  const errorHandler = (window as any).__VUE_APP__.$errorHandler
  if (errorHandler) {
    errorHandler.handleError({ custom: '这是一个未知类型的错误' })
  }
}
</script>

<template>
  <div class="home-view">
    <n-card title="错误处理演示">
      <p>点击以下按钮来测试不同类型的错误处理：</p>
      
      <n-space vertical>
        <n-button @click="triggerNotFoundError" type="warning">
          触发 404 错误 (资源未找到)
        </n-button>
        
        <n-button @click="triggerServerError" type="error">
          触发 500 错误 (服务器错误)
        </n-button>
        
        <n-button @click="triggerNetworkError" type="error">
          触发网络错误
        </n-button>
        
        <n-button @click="triggerJsError" type="error">
          触发 JavaScript 错误
        </n-button>
        
        <n-button @click="triggerUnknownError" type="error">
          触发未知类型错误
        </n-button>
      </n-space>
    </n-card>
  </div>
</template>

<style scoped>
.home-view {
  padding: 20px;
}

.n-card {
  max-width: 600px;
  margin: 0 auto;
}
</style>