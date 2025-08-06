import request from '@/utils/request'

// 用户相关API服务
export interface User {
  id: number
  username: string
  email: string
  roles: string[]
  createdAt: string
  updatedAt: string
}

// 用户查询参数
export interface UserQuery {
  username?: string
  email?: string
  mobilePhone?: string
  nickName?: string
  enable?: string
  current?: number
  size?: number
}

// 分页响应数据
export interface PageResponse<T> {
  records: T[]
  total: number
  current: number
  size: number
}

// 获取用户列表（分页）
export const getUserList = (params: UserQuery): Promise<{ data: PageResponse<User> }> => {
  // 模拟API调用
  return new Promise((resolve) => {
    setTimeout(() => {
      // 模拟分页数据
      const records: User[] = [
        {
          id: 1,
          username: params.username || 'admin',
          email: params.email || 'admin@example.com',
          roles: ['admin'],
          createdAt: '2023-01-01',
          updatedAt: '2023-01-01'
        },
        {
          id: 2,
          username: params.username || 'user1',
          email: params.email || 'user1@example.com',
          roles: ['user'],
          createdAt: '2023-01-02',
          updatedAt: '2023-01-02'
        }
      ]

      resolve({
        data: {
          records,
          total: 2,
          current: params.current || 1,
          size: params.size || 10
        }
      })
    }, 500)
  })

  // 真实的API调用应该是这样的:
  // return request.get<{ data: PageResponse<User> }>('/users', { params })
}

// 获取用户列表
export const getUsers = (): Promise<User[]> => {
  // 模拟API调用
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve([
        {
          id: 1,
          username: 'admin',
          email: 'admin@example.com',
          roles: ['admin'],
          createdAt: '2023-01-01',
          updatedAt: '2023-01-01'
        },
        {
          id: 2,
          username: 'user1',
          email: 'user1@example.com',
          roles: ['user'],
          createdAt: '2023-01-02',
          updatedAt: '2023-01-02'
        }
      ])
    }, 1000)
  })

  // 真实的API调用应该是这样的:
  // return request.get<User[]>('/users')
}

// 获取单个用户
export const getUserById = (id: number): Promise<User> => {
  // 模拟API调用
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve({
        id,
        username: 'user' + id,
        email: 'user' + id + '@example.com',
        roles: ['user'],
        createdAt: '2023-01-01',
        updatedAt: '2023-01-01'
      })
    }, 500)
  })

  // 真实的API调用应该是这样的:
  // return request.get<User>(`/users/${id}`)
}

// 创建用户
export const createUser = (user: any): Promise<User> => {
  // 模拟API调用
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve({
        id: Math.floor(Math.random() * 1000),
        username: user.username || '',
        email: user.email || '',
        roles: user.roles || ['user'],
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString()
      })
    }, 1000)
  })

  // 真实的API调用应该是这样的:
  // return request.post<User>('/users', user)
}

// 更新用户
export const updateUser = (id: number | string, user: any): Promise<User> => {
  // 模拟API调用
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve({
        id: Number(id),
        username: user.username || '',
        email: user.email || '',
        roles: user.roles || ['user'],
        createdAt: '2023-01-01',
        updatedAt: new Date().toISOString()
      })
    }, 1000)
  })

  // 真实的API调用应该是这样的:
  // return request.put<User>(`/users/${id}`, user)
}

// 删除用户
export const deleteUser = (id: number | string): Promise<void> => {
  // 模拟API调用
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve()
    }, 500)
  })

  // 真实的API调用应该是这样的:
  // return request.delete(`/users/${id}`)
}
