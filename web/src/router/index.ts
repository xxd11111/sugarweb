import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '@/views/LoginView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: () => import('../App.vue'),
    },
    {
      path: '/about',
      component: AboutView,
    },
    {
      path: '/login',
      component: LoginView,
    },
  ],
})

export default router
