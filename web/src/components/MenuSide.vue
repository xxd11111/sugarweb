<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { NMenu } from 'naive-ui'
import type { MenuOption } from 'naive-ui'
import menuConfig from '@/config/menuConfig.json'

const router = useRouter()

// 处理菜单选项，添加路由处理逻辑
const processMenuOptions = (menuItems: any[]): MenuOption[] => {
  return menuItems.map(item => {
    const processedItem: MenuOption = {
      label: item.label,
      key: item.key,
      icon: () => null
    }

    // 如果有子菜单，递归处理
    if (item.children && item.children.length > 0) {
      processedItem.children = processMenuOptions(item.children)
    }

    return processedItem
  })
}

// 处理菜单数据
const menuOptions = processMenuOptions(menuConfig)

// 处理菜单选择
const handleMenuSelect = (key: string, item: MenuOption) => {
  // 查找选中项的路由
  const findRoute = (menuItems: any[]): string | null => {
    for (const menuItem of menuItems) {
      if (menuItem.key === key) {
        return menuItem.route || null
      }
      
      if (menuItem.children) {
        const route = findRoute(menuItem.children)
        if (route) {
          return route
        }
      }
    }
    
    return null
  }

  // 获取路由路径
  const routePath = findRoute(menuConfig)
  
  // 只有叶子节点才跳转路由
  if (routePath) {
    router.push(routePath)
  }
}
</script>

<template>
  <div class="menu-side">
    <n-menu
      :options="menuOptions"
      @update:value="handleMenuSelect"
      class="menu-container"
    />
  </div>
</template>

<style scoped>
.menu-side {
  height: 100%;
}

.menu-container {
  height: 100%;
}
</style>