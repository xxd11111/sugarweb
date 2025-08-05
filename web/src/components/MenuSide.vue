<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { NMenu } from 'naive-ui'
import type { MenuOption } from 'naive-ui'

const router = useRouter()

// 菜单选项
const menuOptions: MenuOption[] = [
  {
    label: '首页',
    key: '/home',
    icon: () => null
  },
  {
    label: '系统管理',
    key: 'system',
    icon: () => null,
    children: [
      {
        label: '用户管理',
        key: '/user'
      },
      {
        label: '角色管理',
        key: '/role'
      },
      {
        label: '菜单管理',
        key: '/menu'
      }
    ]
  },
  {
    label: '业务管理',
    key: 'business',
    icon: () => null,
    children: [
      {
        label: '订单管理',
        key: '/order'
      },
      {
        label: '产品管理',
        key: '/product'
      }
    ]
  }
]

// 处理菜单选择
const handleMenuSelect = (key: string, item: MenuOption) => {
  // 只有叶子节点才跳转路由
  if (key.startsWith('/')) {
    router.push(key)
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