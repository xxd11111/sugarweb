import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import { setupNaive } from '@/plugins/NavieUI.ts'

const app = createApp(App)

app.use(createPinia())
app.use(router)

setupNaive(app)

app.mount('#app')
