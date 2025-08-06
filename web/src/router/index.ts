import { createRouter, createWebHistory } from 'vue-router'
import AppLayout from '@/components/AppLayout.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue'),
      meta: {
        title: '登录',
        requiresAuth: false
      }
    },
    {
      path: '/',
      component: AppLayout,
      children: [
        {
          path: '',
          redirect: '/home'
        },
        {
          path: '/home',
          name: 'home',
          component: () => import('../views/HomeView.vue'),
          meta: {
            title: '首页',
            requiresAuth: true
          }
        },
        {
          path: '/user',
          name: 'user',
          component: () => import('../views/UserView.vue'),
          meta: {
            title: '用户管理',
            requiresAuth: true
          }
        },
        {
          path: '/role',
          name: 'role',
          component: () => import('../views/RoleView.vue'),
          meta: {
            title: '角色管理',
            requiresAuth: true
          }
        },
        {
          path: '/menu',
          name: 'menu',
          component: () => import('../views/MenuView.vue'),
          meta: {
            title: '菜单管理',
            requiresAuth: true
          }
        }
      ]
    },
    // 404页面处理 - 需要放在最后
    {
      path: '/404',
      name: 'not-found',
      component: () => import('../views/NotFoundView.vue'),
      meta: {
        title: '页面未找到',
        requiresAuth: false
      }
    },
    // 错误页面
    {
      path: '/error',
      name: 'error',
      component: () => import('../views/ErrorView.vue'),
      meta: {
        title: '系统错误',
        requiresAuth: false
      }
    },
    // 匹配所有未定义的路径，重定向到404页面
    {
      path: '/:pathMatch(.*)*',
      redirect: '/404'
    }
  ]
})

// 全局前置守卫 - 处理路由权限验证
router.beforeEach((to, from, next) => {
  // 设置页面标题
  if (to.meta.title) {
    document.title = to.meta.title as string
  } else {
    document.title = 'AI SugarWeb'
  }

  // 检查是否需要认证
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth)

  // 检查用户是否有有效的token
  const token = localStorage.getItem('token')
  const isAuthenticated = localStorage.getItem('isAuthenticated')

  if (requiresAuth && !token && !isAuthenticated) {
    // 需要认证但没有认证信息，跳转到登录页
    next('/login')
  } else if (!requiresAuth && token && isAuthenticated) {
    // 不需要认证但已有认证信息且访问的是登录页，跳转到首页
    if (to.path === '/login') {
      next('/home')
    } else {
      next()
    }
  } else {
    // 其他情况正常通行
    next()
  }
})

// 全局后置钩子 - 处理页面滚动等
router.afterEach((to, from) => {
  // 滚动到页面顶部
  window.scrollTo(0, 0)
})


export default router
