<template>
  <n-config-provider :theme="theme">
    <n-layout class="layout-wrapper">
      <!-- 头部栏 -->
      <n-layout-header class="layout-header">
        <HeaderBar/>
      </n-layout-header>
      
      <n-layout class="layout-main" has-sider>
        <!-- 左侧菜单栏 -->
        <n-layout-sider 
          class="layout-sider"
          :native-scrollbar="false"
          bordered
        >
          <MenuSide />
        </n-layout-sider>
        
        <n-layout class="layout-content-wrapper">
          <!-- Tab页管理 -->
          <n-layout-header class="layout-tabs">
            <TabManager />
          </n-layout-header>
          
          <!-- 内容区域 -->
          <n-layout-content class="layout-content">
            <RouterView />
          </n-layout-content>
        </n-layout>
      </n-layout>
    </n-layout>
  </n-config-provider>
</template>

<script lang="ts">
import type { GlobalTheme } from 'naive-ui'
import { darkTheme } from 'naive-ui'
import { defineComponent, ref } from 'vue'
import HeaderBar from '@/components/HeaderBar.vue'
import MenuSide from '@/components/MenuSide.vue'
import TabManager from '@/components/TabManager.vue'

export default defineComponent({
  components: { 
    HeaderBar,
    MenuSide,
    TabManager
  },
  setup() {
    return {
      darkTheme,
      theme: ref<GlobalTheme | null>(null)
    }
  }
})
</script>

<style scoped>
.layout-wrapper {
  height: 100vh;
}

.layout-header {
  height: 64px;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  z-index: 100;
}

.layout-main {
  flex: 1;
}

.layout-sider {
  width: 240px;
  height: calc(100vh - 64px);
  box-shadow: 2px 0 8px 0 rgba(29, 35, 41, 0.05);
}

.layout-content-wrapper {
  display: flex;
  flex-direction: column;
  flex: 1;
}

.layout-tabs {
  height: 40px;
  border-bottom: 1px solid #e8eaec;
  background: #f5f7fa;
}

.layout-content {
  flex: 1;
  padding: 16px;
  overflow: auto;
}
</style>