<script setup lang="ts">
import { useRouter, useRoute } from 'vue-router'
import { NButton, NResult } from 'naive-ui'
import { ref, onMounted } from 'vue'

const router = useRouter()
const route = useRoute()

// 错误信息
const errorInfo = ref({
  status: 'error',
  title: '系统异常',
  description: '抱歉，系统发生了未知错误'
})

onMounted(() => {
  // 从路由参数中获取错误信息
  if (route.query.status) {
    errorInfo.value.status = route.query.status as string
  }
  
  if (route.query.title) {
    errorInfo.value.title = route.query.title as string
  }
  
  if (route.query.description) {
    errorInfo.value.description = route.query.description as string
  }
})

const handleBack = () => {
  router.back()
}

const handleHome = () => {
  router.push('/')
}

const handleReload = () => {
  window.location.reload()
}
</script>

<template>
  <div class="error-page">
    <n-result
      :status="errorInfo.status"
      :title="errorInfo.title"
      :description="errorInfo.description"
    >
      <template #footer>
        <n-button @click="handleBack" class="action-button">
          返回上一页
        </n-button>
        <n-button @click="handleHome" class="action-button">
          返回首页
        </n-button>
        <n-button type="primary" @click="handleReload" class="action-button">
          刷新页面
        </n-button>
      </template>
    </n-result>
  </div>
</template>

<style scoped>
.error-page {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe3 100%);
  padding: 20px;
}

.action-button {
  margin: 0 10px;
}
</style>