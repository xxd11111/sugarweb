<script setup lang="ts">
import { useRouter } from 'vue-router'
import { NButton, NResult } from 'naive-ui'

const router = useRouter()

const handleBack = () => {
  router.back()
}

const handleHome = () => {
  router.push('/')
}
</script>

<template>
  <div class="not-found-page">
    <n-result
      status="404"
      title="404 页面未找到"
      description="抱歉，您访问的页面不存在"
    >
      <template #footer>
        <n-button @click="handleBack" class="action-button">
          返回上一页
        </n-button>
        <n-button type="primary" @click="handleHome" class="action-button">
          返回首页
        </n-button>
      </template>
    </n-result>
  </div>
</template>

<style scoped>
.not-found-page {
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