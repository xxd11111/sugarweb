import type { App } from 'vue'
import type { Router } from 'vue-router'

// 错误类型枚举
export enum ErrorCode {
  NETWORK_ERROR = 'NETWORK_ERROR',           // 网络错误
  SERVER_ERROR = 'SERVER_ERROR',             // 服务器错误
  UNAUTHORIZED = 'UNAUTHORIZED',             // 未授权
  FORBIDDEN = 'FORBIDDEN',                   // 禁止访问
  NOT_FOUND = 'NOT_FOUND',                   // 资源未找到
  VALIDATION_ERROR = 'VALIDATION_ERROR',     // 验证错误
  TIMEOUT_ERROR = 'TIMEOUT_ERROR',           // 超时错误
  UNKNOWN_ERROR = 'UNKNOWN_ERROR'            // 未知错误
}

// 错误信息接口
export interface AppError {
  code: ErrorCode
  message: string
  detail?: any
  timestamp: number
}

// 错误处理选项
export interface ErrorHandlerOptions {
  router: Router
  enableConsoleLog?: boolean
}

// 错误处理器类
class ErrorHandler {
  private router: Router
  private enableConsoleLog: boolean

  constructor(options: ErrorHandlerOptions) {
    this.router = options.router
    this.enableConsoleLog = options.enableConsoleLog ?? true
  }

  // 处理错误
  handleError(error: AppError | Error | any) {
    // 记录错误日志
    if (this.enableConsoleLog) {
      console.error('应用错误:', error)
    }

    // 如果是自定义错误对象
    if (this.isAppError(error)) {
      this.handleAppError(error)
    }
    // 如果是 JavaScript 错误对象
    else if (error instanceof Error) {
      this.handleJsError(error)
    }
    // 其他类型错误
    else {
      this.handleUnknownError(error)
    }
  }

  // 判断是否为自定义错误对象
  private isAppError(error: any): error is AppError {
    return error && typeof error.code === 'string' && typeof error.message === 'string'
  }

  // 处理自定义错误
  private handleAppError(error: AppError) {
    switch (error.code) {
      case ErrorCode.NOT_FOUND:
        this.router.push('/404')
        break
      case ErrorCode.UNAUTHORIZED:
        // 跳转到登录页
        this.router.push('/login')
        break
      case ErrorCode.FORBIDDEN:
        this.router.push({
          path: '/error',
          query: {
            status: '403',
            title: '访问被拒绝',
            description: error.message || '您没有权限访问此页面'
          }
        })
        break
      case ErrorCode.SERVER_ERROR:
        // 改为使用通知而不是跳转到错误页面
        this.showNotification('error', '服务器错误', error.message || '服务器发生错误，请稍后重试')
        break
      default:
        this.router.push({
          path: '/error',
          query: {
            status: 'error',
            title: '系统异常',
            description: error.message || '系统发生未知错误'
          }
        })
    }
  }

  // 处理 JavaScript 错误
  private handleJsError(error: Error) {
    // 改为使用通知而不是跳转到错误页面
    this.showNotification('error', '系统异常', error.message || '系统发生未知错误')
  }

  // 处理未知错误
  private handleUnknownError(error: any) {
    // 改为使用通知而不是跳转到错误页面
    this.showNotification('error', '未知错误', '系统发生未知错误')
  }

  // 显示通知
  private showNotification(type: 'success' | 'error' | 'warning' | 'info', title: string, message: string) {
    // 获取全局应用实例
    const appInstance = (window as any).__VUE_APP__
    if (appInstance && appInstance.$message) {
      appInstance.$message[type](`${title}: ${message}`)
    } else {
      // 如果无法获取消息实例，则使用console作为后备方案
    }
  }

  // 创建自定义错误对象
  createError(code: ErrorCode, message: string, detail?: any): AppError {
    return {
      code,
      message,
      detail,
      timestamp: Date.now()
    }
  }
}

// 全局错误处理器实例
let errorHandler: ErrorHandler | null = null

// 插件安装函数
export default {
  install(app: App, options: ErrorHandlerOptions) {
    // 创建错误处理器实例
    errorHandler = new ErrorHandler(options)

    // 将错误处理器添加到全局属性
    app.config.globalProperties.$errorHandler = errorHandler

    // 提供错误处理器给依赖注入
    app.provide('errorHandler', errorHandler)

    // 全局未捕获的 Promise 错误处理
    window.addEventListener('unhandledrejection', (event) => {
      errorHandler?.handleError(event.reason)
      event.preventDefault()
    })

    // 全局未捕获的 JavaScript 错误处理
    window.addEventListener('error', (event) => {
      errorHandler?.handleError(event.error || event)
    })
  }
}

// 用于在组件外部获取错误处理器实例
export const getErrorHandler = () => errorHandler

// 导出工具函数
export const createAppError = (code: ErrorCode, message: string, detail?: any): AppError => {
  if (!errorHandler) {
    throw new Error('错误处理器未初始化')
  }
  return errorHandler.createError(code, message, detail)
}
