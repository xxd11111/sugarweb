<template>
  <div class="layout-wrapper">
    <n-layout>
      <!-- 顶部系统信息 -->
      <n-layout-header bordered>
        <HeaderBar @toggle-collapse="collapsed = !collapsed" @logout="handleLogout" />
      </n-layout-header>

      <!-- 下面分为左边菜单和右边页面 -->
      <n-layout has-sider style="flex-direction: row;">
        <n-layout-sider
          bordered
          collapse-mode="width"
          :collapsed-width="64"
          :width="240"
          :collapsed="collapsed"
          show-trigger
          @collapse="collapsed = true"
          @expand="collapsed = false"
        >
          <MenuSide />
        </n-layout-sider>

        <n-layout>
          <n-layout-header bordered>
            <TabManager />
          </n-layout-header>

          <n-layout-content class="layout-content">
            <RouterView />
          </n-layout-content>
        </n-layout>
      </n-layout>
    </n-layout>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useMessage } from 'naive-ui'
import { NLayout, NLayoutSider, NLayoutHeader, NLayoutContent } from 'naive-ui'
import HeaderBar from './HeaderBar.vue'
import MenuSide from './MenuSide.vue'
import TabManager from './TabManager.vue'
import { logout } from '@/services/auth'

const collapsed = ref(false)
const router = useRouter()
const message = useMessage()

const handleLogout = async () => {
  try {
    // 调用登出API
    await logout()
    // 跳转到登录页
    message.success('登出成功')
    router.push('/login')
  } catch (error) {
    console.error('登出失败:', error)
    message.error('登出失败')
  }
}
</script>

<style scoped>
.layout-wrapper {
  height: 100vh;
}

.layout-content {
  padding: 16px;
  overflow: auto;
}
</style>