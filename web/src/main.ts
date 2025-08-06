import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import { setupNaive } from '@/plugins/navie.ts'
import errorHandler from '@/plugins/errorHandler.ts'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(errorHandler, { router })

setupNaive(app)

// 将应用实例挂载到全局，方便在错误处理中访问
const appInstance = app.mount('#app')
;(window as any).__VUE_APP__ = appInstance

export { appInstance }
