<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { NForm, NFormItem, NInput, NButton, NCard, NCheckbox, NIcon, NSpace, NLayout, NLayoutContent } from 'naive-ui'
import { PersonCircleOutline, LockClosedOutline, LogoGithub, LogoTwitter } from '@vicons/ionicons5'

const router = useRouter()

const formValue = ref({
  username: '',
  password: '',
  rememberMe: false
})

const rules = {
  username: {
    required: true,
    message: '请输入用户名',
    trigger: 'blur'
  },
  password: {
    required: true,
    message: '请输入密码',
    trigger: 'blur'
  }
}

const handleLogin = (e: MouseEvent) => {
  e.preventDefault()
  // 这里应该调用实际的登录API
  console.log('登录信息:', formValue.value)
  // 登录成功后跳转到首页
  router.push('/')
}

const handleReset = () => {
  formValue.value.username = ''
  formValue.value.password = ''
  formValue.value.rememberMe = false
}
</script>

<template>
  <n-layout style="height: 100vh;">
    <n-layout-content style="display: flex; align-items: center; justify-content: center; background: linear-gradient(120deg, #f6f9fc 0%, #eef2f7 100%);">
      <div class="login-wrapper">
        <div class="login-left">
          <div class="welcome-content">
            <h1>欢迎使用 SugarWeb</h1>
            <p>现代化的组件化Web应用整合方案</p>
            <ul>
              <li>基于Vue 3和TypeScript构建</li>
              <li>集成多种常用业务组件</li>
              <li>模块化设计，易于扩展</li>
              <li>响应式设计，支持多端适配</li>
            </ul>
            <div class="social-links">
              <n-button circle>
                <template #icon>
                  <n-icon :component="LogoGithub" />
                </template>
              </n-button>
              <n-button circle>
                <template #icon>
                  <n-icon :component="LogoTwitter" />
                </template>
              </n-button>
            </div>
          </div>
        </div>
        <div class="login-right">
          <NCard :bordered="false" shadow="hover" style="width: 380px;">
            <div class="login-header">
              <h2>系统登录</h2>
              <p>请输入您的登录信息</p>
            </div>
            
            <NForm :model="formValue" :rules="rules" ref="formRef">
              <NFormItem path="username" label="用户名">
                <NInput 
                  v-model:value="formValue.username" 
                  placeholder="请输入用户名"
                  clearable
                  autofocus
                >
                  <template #prefix>
                    <NIcon :component="PersonCircleOutline" />
                  </template>
                </NInput>
              </NFormItem>
              
              <NFormItem path="password" label="密码">
                <NInput 
                  v-model:value="formValue.password" 
                  type="password" 
                  placeholder="请输入密码"
                  show-password-on="click"
                >
                  <template #prefix>
                    <NIcon :component="LockClosedOutline" />
                  </template>
                </NInput>
              </NFormItem>
              
              <NFormItem>
                <div class="login-options">
                  <NCheckbox v-model:checked="formValue.rememberMe">记住我</NCheckbox>
                  <a href="#" class="forgot-password">忘记密码？</a>
                </div>
              </NFormItem>
              
              <NSpace vertical :size="12">
                <NButton 
                  type="primary" 
                  size="large" 
                  block 
                  @click="handleLogin"
                >
                  登录
                </NButton>
                
                <NButton 
                  size="large" 
                  block
                  @click="handleReset"
                >
                  重置
                </NButton>
              </NSpace>
            </NForm>
            
            <div class="login-footer">
              <p>© 2025 SugarWeb - 组件化Web应用整合方案</p>
            </div>
          </NCard>
        </div>
      </div>
    </n-layout-content>
  </n-layout>
</template>

<style scoped>
.login-wrapper {
  display: flex;
  width: 900px;
  height: 550px;
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
}

.login-left {
  flex: 1;
  background: linear-gradient(135deg, #4098fc 0%, #52c41a 100%);
  color: white;
  padding: 40px;
  display: flex;
  align-items: center;
}

.welcome-content h1 {
  font-size: 32px;
  margin-bottom: 16px;
}

.welcome-content p {
  font-size: 18px;
  margin-bottom: 32px;
  opacity: 0.9;
}

.welcome-content ul {
  list-style: none;
  padding: 0;
  margin-bottom: 32px;
}

.welcome-content li {
  margin-bottom: 12px;
  padding-left: 24px;
  position: relative;
}

.welcome-content li::before {
  content: "✓";
  position: absolute;
  left: 0;
  top: 0;
}

.social-links {
  display: flex;
  gap: 16px;
}

.login-right {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header h2 {
  margin-bottom: 10px;
  color: #333;
}

.login-header p {
  color: #666;
  margin: 0;
}

.login-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.forgot-password {
  color: #4098fc;
  text-decoration: none;
  font-size: 14px;
}

.forgot-password:hover {
  text-decoration: underline;
}

.login-footer {
  text-align: center;
  margin-top: 30px;
  padding-top: 20px;
  border-top: 1px solid #eee;
}

.login-footer p {
  color: #999;
  font-size: 12px;
  margin: 0;
}

@media (max-width: 768px) {
  .login-wrapper {
    flex-direction: column;
    height: auto;
  }
  
  .login-left {
    padding: 20px;
  }
}
</style>