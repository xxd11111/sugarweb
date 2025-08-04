<script setup lang="ts">
import { reactive, ref } from 'vue'
import { NButton, NCheckbox, NForm, NFormItem, NInput } from 'naive-ui'
import router from '@/router/index.js'

import { Icon } from '@vicons/utils'
import { Alarm } from '@vicons/ionicons5'

// 登录
let formRef = ref(null)
let form = reactive({
  username: 'admin',
  password: 'admin123',
  code: '',
  uuid: ''
})
let rules = {
  username: {
    required: true,
    trigger: ['input', 'blur'],
    message: '请输入用户名'
  },
  password: {
    required: true,
    trigger: ['input', 'blur'],
    message: '请输入密码'
  },
  code: {
    required: true,
    trigger: ['input', 'blur'],
    message: '请输入验证码'
  }
}
let rememberMe = ref(false)
let loginBtnState = ref(false)
let handleLogin = () => {
  loginBtnState.value = true
  formRef.value?.validate((errors) => {
    if (!errors) {
      if (rememberMe.value) {
        cookie.set('username', form.username, { expires: 30 })
        cookie.set('password', encrypt(form.password), { expires: 30 })
        cookie.set('rememberMe', rememberMe.value, { expires: 30 })
      } else {
        cookie.remove('username')
        cookie.remove('password')
        cookie.remove('rememberMe')
      }

      userStore
        .login(form)
        .then(() => {
          window.$msg.success('登录成功')
          router.push('/home')
        })
        .catch(() => {
          loginBtnState.value = false
          getCode()
        })
    } else {
      loginBtnState.value = false
    }
  })
}

// getCookie(); // 需要记住密码，自己取消注释
</script>

<template>
  <div class="login-bg c-center">
    <div class="login__box">
      <div class="login-logo__box">
        <div class="login__title login-logo">
          登 录
        </div>
      </div>
      <n-form
        ref="formRef"
        class="login-form__box"
        :model="form"
        :rules="rules"
        label-placement="left"
      >
        <n-form-item path="username">
          <n-input
            class="login-input"
            v-model:value="form.username"
            placeholder="请输入用户名/手机号"
          >
            <template #prefix>
              <Icon>
                <Alarm />
              </Icon>
            </template>
          </n-input>
        </n-form-item>
        <n-form-item path="password">
          <n-input
            class="login-input"
            v-model:value="form.password"
            placeholder="请输入密码"
            type="password"
            show-password-on="mousedown"
            @keyup.enter="handleLogin"
          >
            <template #prefix>
              <Icon>
                <Alarm />
              </Icon>
            </template>
          </n-input>
        </n-form-item>
        <n-form-item v-if="needCode" class="login-code" path="code">
          <n-input
            v-model:value="form.code"
            class="login-input login-input_code"
            placeholder="验证码"
            @keyup.enter="handleLogin"
          >
          </n-input>
        </n-form-item>
        <div class="login-checkbox_box">
          <n-checkbox
            class="login-checkbox"
            v-model:checked="rememberMe"
          ></n-checkbox>
          <span>记住密码</span>
        </div>
        <n-button
          class="login-btn_login"
          type="info"
          @click="handleLogin"
          :loading="loginBtnState"
          :disabled="loginBtnState"
        >登录
        </n-button
        >
      </n-form>
      <!-- <div class="login-btn_forget" @click="handleForget">忘记密码 ？</div> -->
    </div>
  </div>
</template>
