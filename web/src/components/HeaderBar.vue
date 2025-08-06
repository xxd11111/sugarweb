<script setup lang="ts">
import { NIcon, NDropdown } from 'naive-ui'
import { SettingsOutline, PersonCircleOutline } from '@vicons/ionicons5'
import { useRouter } from 'vue-router'

// 获取路由实例
const router = useRouter()

// 模拟用户数据
const username = '管理员'
const userAvatar = ''

// 设置菜单选项
const settingOptions = [
  {
    label: '个人中心',
    key: 'profile'
  },
  {
    label: '系统设置',
    key: 'settings'
  },
  {
    type: 'divider',
    key: 'd1'
  },
  {
    label: '退出登录',
    key: 'logout'
  }
]

// 处理设置图标点击
const handleSettings = () => {
  console.log('打开系统设置')
}

// 处理用户菜单选择
const handleUserCommand = (key: string) => {
  switch (key) {
    case 'profile':
      console.log('跳转到个人中心')
      break
    case 'settings':
      console.log('打开系统设置')
      break
    case 'logout':
      // 退出登录逻辑
      logout()
      break
  }
}

// 退出登录函数
const logout = () => {
  // 清除用户认证信息
  clearAuthData()
  
  // 跳转到登录页
  router.push('/login')
}

// 清除用户认证数据
const clearAuthData = () => {
  localStorage.removeItem('token')
  sessionStorage.removeItem('token')
  // 这里可以添加更多需要清除的用户数据
}
</script>

<template>
  <div class="header-container">
    <!-- 左侧头部栏 -->
    <div class="header-left">
      <!-- 系统logo -->
      <div class="logo-container">
        <img 
          v-if="false" 
          src="" 
          alt="系统logo" 
          class="system-logo"
        />
        <div v-else class="logo-placeholder">LOGO</div>
      </div>
      <!-- 系统名称 -->
      <div class="system-name">
        SugarWeb管理系统
      </div>
    </div>
    
    <!-- 右侧头部栏 -->
    <div class="header-right">
      <!-- 系统设置 -->
      <div class="settings-icon" @click="handleSettings">
        <n-icon size="24">
          <SettingsOutline />
        </n-icon>
      </div>
      
      <!-- 用户头像和信息 -->
      <div class="user-info">
        <n-dropdown 
          :options="settingOptions" 
          @select="handleUserCommand"
          trigger="hover"
          placement="bottom-end"
        >
          <div class="user-avatar-container">
            <n-icon size="32" class="user-avatar">
              <PersonCircleOutline />
            </n-icon>
            <span class="username">{{ username }}</span>
          </div>
        </n-dropdown>
      </div>
    </div>
  </div>
</template>

<style scoped>
.header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  padding: 0 24px;
}

.header-left {
  display: flex;
  align-items: center;
  flex-shrink: 0;
}

.logo-container {
  margin-right: 16px;
}

.system-logo {
  height: 32px;
  width: 32px;
}

.logo-placeholder {
  height: 32px;
  width: 32px;
  background: #409eff;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  border-radius: 4px;
}

.system-name {
  font-size: 20px;
  font-weight: bold;
  color: #303133;
}

.header-right {
  display: flex;
  align-items: center;
}

.settings-icon {
  margin-right: 24px;
  cursor: pointer;
  color: #606266;
  transition: color 0.3s;
}

.settings-icon:hover {
  color: #409eff;
}

.user-info {
  cursor: pointer;
}

.user-avatar-container {
  display: flex;
  align-items: center;
}

.user-avatar {
  margin-right: 8px;
  color: #606266;
}

.username {
  font-size: 14px;
  color: #606266;
}
</style>