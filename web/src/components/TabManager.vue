<script setup lang="ts">
import { ref, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { NTab, NTabs, NIcon } from 'naive-ui'
import { Close } from '@vicons/ionicons5'

// 定义标签页类型
interface Tab {
  name: string
  path: string
  title: string
}

// 获取路由实例
const router = useRouter()
const route = useRoute()

// 标签页数据
const tabs = ref<Tab[]>([
  {
    name: 'home',
    path: '/home',
    title: '首页'
  }
])

// 当前激活的标签页
const activeTab = ref('/home')

// 监听路由变化，同步标签页
watch(
  () => route.path,
  (newPath) => {
    activeTab.value = newPath
    
    // 如果标签页不存在，则添加
    const existingTab = tabs.value.find(tab => tab.path === newPath)
    if (!existingTab && newPath !== '/') {
      tabs.value.push({
        name: route.name as string || newPath,
        path: newPath,
        title: (route.meta?.title as string) || '未命名页面'
      })
    }
  },
  { immediate: true }
)

// 切换标签页
const handleTabChange = (path: string) => {
  activeTab.value = path
  router.push(path)
}

// 关闭标签页
const handleCloseTab = (path: string, e: Event) => {
  e.stopPropagation()
  
  // 阻止关闭最后一个标签页
  if (tabs.value.length <= 1) {
    return
  }
  
  const index = tabs.value.findIndex(tab => tab.path === path)
  if (index !== -1) {
    tabs.value.splice(index, 1)
    
    // 如果关闭的是当前激活的标签页，跳转到第一个标签页
    if (activeTab.value === path) {
      const firstTab = tabs.value[0]
      if (firstTab) {
        activeTab.value = firstTab.path
        router.push(firstTab.path)
      }
    }
  }
}

// 关闭其他标签页
const handleCloseOtherTabs = () => {
  tabs.value = tabs.value.filter(tab => tab.path === activeTab.value || tab.path === '/home')
}
</script>

<template>
  <div class="tab-manager">
    <n-tabs 
      type="card" 
      :value="activeTab"
      @update:value="handleTabChange"
      class="tabs-container"
    >
      <n-tab 
        v-for="tab in tabs" 
        :key="tab.path"
        :name="tab.path"
        :closable="tab.path !== '/home'"
        @close="handleCloseTab(tab.path, $event)"
      >
        {{ tab.title }}
      </n-tab>
    </n-tabs>
    
    <div class="tab-actions">
      <n-icon @click="handleCloseOtherTabs" class="close-icon">
        <Close />
      </n-icon>
    </div>
  </div>
</template>

<style scoped>
.tab-manager {
  display: flex;
  align-items: center;
  height: 100%;
  padding-right: 16px;
}

.tabs-container {
  flex: 1;
}

.tab-actions {
  margin-left: 8px;
}

.close-icon {
  cursor: pointer;
  padding: 4px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.close-icon:hover {
  background-color: #f5f5f5;
}
</style>