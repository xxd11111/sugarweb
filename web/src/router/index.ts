import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue'),
      meta: {
        title: '登录'
      }
    },
    {
      path: '/home',
      name: 'home',
      component: () => import('../views/HomeView.vue'),
      meta: {
        title: '首页'
      }
    },
    {
      path: '/user',
      name: 'user',
      component: () => import('../views/UserView.vue'),
      meta: {
        title: '用户管理'
      }
    },
    {
      path: '/role',
      name: 'role',
      component: () => import('../views/RoleView.vue'),
      meta: {
        title: '角色管理'
      }
    }
  ]
})

export default router