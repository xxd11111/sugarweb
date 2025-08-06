import request from '@/utils/request'

// 登录接口
export interface LoginRequest {
  username: string
  password: string
  rememberMe?: boolean
}

export interface LoginResponse {
  token: string
  user: {
    id: number
    username: string
    email: string
    roles: string[]
  }
}

// 登录
export const login = (data: LoginRequest): Promise<LoginResponse> => {
  // 这里应该是一个真实的登录API调用
  // 暂时返回模拟数据
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve({
        token: 'fake-token-' + Date.now(),
        user: {
          id: 1,
          username: data.username,
          email: data.username + '@example.com',
          roles: ['admin']
        }
      })
    }, 1000)
  })

  // 真实的API调用应该是这样的:
  // return request.post<LoginResponse>('/auth/login', data)
}

// 获取用户信息
export const getUserInfo = (): Promise<any> => {
  // 模拟获取用户信息
  return new Promise((resolve) => {
    setTimeout(() => {
      const token = localStorage.getItem('token')
      if (token) {
        resolve({
          id: 1,
          username: 'admin',
          email: 'admin@example.com',
          roles: ['admin']
        })
      } else {
        resolve(null)
      }
    }, 500)
  })

  // 真实的API调用应该是这样的:
  // return request.get('/user/info')
}

// 登出
export const logout = (): Promise<void> => {
  // 清除本地存储的认证信息
  localStorage.removeItem('token')
  localStorage.removeItem('isAuthenticated')

  // 真实的API调用应该是这样的:
  // return request.post('/auth/logout')
  return Promise.resolve()
}
