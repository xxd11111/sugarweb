import axios, { type AxiosInstance, type AxiosRequestConfig, type AxiosResponse, AxiosError, type InternalAxiosRequestConfig } from 'axios'

// 创建axios实例
const request: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:3000/api', // API基础URL
  timeout: 10000, // 请求超时时间
  headers: {
    'Content-Type': 'application/json'
  }
})

// 请求拦截器
request.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // 在发送请求之前做些什么
    const token = localStorage.getItem('token')
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error: AxiosError) => {
    // 对请求错误做些什么
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  (response: AxiosResponse) => {
    // 对响应数据做点什么
    return response.data
  },
  (error: AxiosError) => {
    // 对响应错误做点什么
    if (error.response) {
      // 服务器返回了错误状态码
      switch (error.response.status) {
        case 401:
          // 未授权，清除token并跳转到登录页
          localStorage.removeItem('token')
          localStorage.removeItem('isAuthenticated')
          // 使用 window.location 而不是 router 进行页面跳转
          window.location.href = '/login'
          break
        case 403:
          console.error('拒绝访问')
          break
        case 404:
          console.error('请求地址出错')
          break
        case 500:
          console.error('服务器内部错误')
          break
        default:
          console.error(`错误:${error.response.status}`)
      }
    } else if (error.request) {
      // 请求已发出但没有收到响应
      console.error('网络错误')
    } else {
      // 其他错误
      console.error('错误:', error.message)
    }
    return Promise.reject(error)
  }
)

export default request
