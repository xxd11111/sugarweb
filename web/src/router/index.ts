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
        }
      ]
    }
  ]
})

export default router