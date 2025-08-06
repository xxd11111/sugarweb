<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { NButton, NCard, NCheckbox, NForm, NFormItem, NIcon, NInput, NSpace } from 'naive-ui'
import { LockClosedOutline, LogoGithub, LogoTwitter, PersonCircleOutline } from '@vicons/ionicons5'

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
  router.push('/home')
}

const handleReset = () => {
  formValue.value.username = ''
  formValue.value.password = ''
  formValue.value.rememberMe = false
}
</script>

<template>
  <div class="login-page">
    <div class="login-container">
      <div class="login-wrapper">
        <div class="login-left">
          <div class="welcome-content">
            <h1>欢迎使用 AI SugarWeb</h1>
            <p>基于人工智能的现代化Web应用平台</p>
            <ul>
              <li>基于Vue 3和AI技术构建</li>
              <li>集成智能业务组件与机器学习能力</li>
              <li>模块化设计，易于扩展AI功能</li>
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
          <NCard :bordered="false" style="width: 100%; max-width: 380px;">
            <div class="login-header">
              <h2>AI 系统登录</h2>
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
                  class="login-btn"
                >
                  登录
                </NButton>
                
                <NButton 
                  size="large" 
                  block
                  @click="handleReset"
                  class="reset-btn"
                >
                  重置
                </NButton>
              </NSpace>
            </NForm>
            
            <div class="login-footer">
              <p>© 2025 AI SugarWeb - 智能化Web应用平台</p>
            </div>
          </NCard>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #e0eafc 0%, #cfdef3 100%);
  padding: 20px;
  box-sizing: border-box;
  overflow: hidden;
}

.login-container {
  width: 100%;
  max-width: 1200px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-wrapper {
  display: flex;
  width: 100%;
  max-width: 900px;
  height: 550px;
  background: white;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  box-sizing: border-box;
}

.login-left {
  flex: 1;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 40px;
  display: flex;
  align-items: center;
  box-sizing: border-box;
}

.welcome-content h1 {
  font-size: 32px;
  margin-bottom: 16px;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
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
  color: #fff;
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
  box-sizing: border-box;
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
  color: #667eea;
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

/* 登录按钮样式 */
.login-btn {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  transition: all 0.3s ease;
}

.login-btn:hover {
  background: linear-gradient(135deg, #5a6fd8 0%, #6a4190 100%);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
}

/* 重置按钮样式 */
.reset-btn {
  border: 1px solid #667eea;
  color: #667eea;
  background: transparent;
  transition: all 0.3s ease;
}

.reset-btn:hover {
  background: rgba(102, 126, 234, 0.1);
  border-color: #5a6fd8;
  color: #5a6fd8;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.2);
}

@media (max-width: 768px) {
  .login-page {
    padding: 10px;
    overflow: auto;
  }
  
  .login-wrapper {
    flex-direction: column;
    height: auto;
    max-height: 90vh;
  }
  
  .login-left {
    padding: 20px;
  }
  
  .login-right {
    padding: 20px;
  }
  
  .welcome-content h1 {
    font-size: 24px;
  }
  
  .welcome-content p {
    font-size: 16px;
  }
}
</style>
