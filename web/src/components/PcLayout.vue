<template>
  <n-layout style="height: 100vh;">
    <!-- 顶部导航栏 -->
    <n-layout-header bordered style="height: 64px; padding: 0 24px;" class="header">
      <div class="header-content">
        <div class="logo-section">
          <img src="@/assets/logo.svg" alt="Logo" class="logo" />
          <h2>SugarWeb管理系统</h2>
        </div>
        <div class="user-actions">
          <n-space>
            <n-button strong secondary>
              <template #icon>
                <n-icon><PersonCircleOutline /></n-icon>
              </template>
              用户中心
            </n-button>
            <n-button strong secondary @click="handleLogout">
              <template #icon>
                <n-icon><LogOutOutline /></n-icon>
              </template>
              退出登录
            </n-button>
          </n-space>
        </div>
      </div>
    </n-layout-header>

    <n-layout position="absolute" style="top: 64px;" has-sider>
      <!-- 侧边栏 -->
      <n-layout-sider
        bordered
        show-trigger
        collapse-mode="width"
        :collapsed-width="64"
        :width="240"
        :native-scrollbar="false"
        class="sidebar"
      >
        <div class="sidebar-content">
          <n-menu
            :collapsed-width="64"
            :collapsed-icon-size="22"
            :options="menuOptions"
            :indent="24"
          />
        </div>
      </n-layout-sider>

      <!-- 主内容区域 -->
      <n-layout>
        <n-layout-header bordered style="height: 48px; display: flex; align-items: center; padding: 0 24px;">
          <n-breadcrumb>
            <n-breadcrumb-item>首页</n-breadcrumb-item>
            <n-breadcrumb-item>仪表盘</n-breadcrumb-item>
          </n-breadcrumb>
        </n-layout-header>
        <n-layout-content
          content-style="padding: 24px;"
          :native-scrollbar="false"
          style="height: calc(100vh - 112px);"
        >
          <RouterView />
        </n-layout-content>
        <n-layout-footer bordered position="absolute" style="height: 40px;">
          <div class="footer-content">
            <p>© 2025 SugarWeb - 组件化Web应用整合方案</p>
          </div>
        </n-layout-footer>
      </n-layout>
    </n-layout>
  </n-layout>
</template>

<script lang="ts" setup>
import { h } from 'vue'
import { RouterLink, useRouter } from 'vue-router'
import type { MenuOption } from 'naive-ui'
import { NIcon } from 'naive-ui'
import {
  HomeOutline,
  InformationCircleOutline,
  LogInOutline,
  PersonCircleOutline,
  LogOutOutline,
  GridOutline,
  SettingsOutline,
  DocumentTextOutline,
  PeopleOutline
} from '@vicons/ionicons5'

const router = useRouter()

function renderIcon(icon: any) {
  return () => h(NIcon, null, { default: () => h(icon) })
}

const menuOptions: MenuOption[] = [
  {
    label: () =>
      h(
        RouterLink,
        {
          to: {
            name: 'home',
            params: {}
          }
        },
        { default: () => '首页' }
      ),
    key: 'home',
    icon: renderIcon(HomeOutline)
  },
  {
    label: '系统管理',
    key: 'system',
    icon: renderIcon(SettingsOutline),
    children: [
      {
        label: () =>
          h(
            RouterLink,
            {
              to: '/users'
            },
            { default: () => '用户管理' }
          ),
        key: 'users',
        icon: renderIcon(PeopleOutline)
      },
      {
        label: () =>
          h(
            RouterLink,
            {
              to: '/roles'
            },
            { default: () => '角色管理' }
          ),
        key: 'roles'
      }
    ]
  },
  {
    label: () =>
      h(
        RouterLink,
        {
          to: {
            name: 'about',
            params: {}
          }
        },
        { default: () => '关于' }
      ),
    key: 'about',
    icon: renderIcon(InformationCircleOutline)
  }
]

const handleLogout = () => {
  // 执行退出登录逻辑
  router.push('/login')
}
</script>

<style scoped>
.header {
  background-color: #ffffff;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
}

.logo-section {
  display: flex;
  align-items: center;
  gap: 16px;
}

.logo {
  height: 36px;
  width: 36px;
}

.sidebar-content {
  padding: 12px 0;
}

.footer-content {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.n-layout-footer {
  background-color: #f8f9fa;
}
</style>
