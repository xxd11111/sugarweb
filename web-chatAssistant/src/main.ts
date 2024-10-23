import {createApp} from 'vue'
import {createPinia} from 'pinia'
// import App from './Demo.vue'
import App from './App.vue'
import router from './router'

// Vuetify
import "vuetify/styles";
import {createVuetify} from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
// Vuetify icons font
import {aliases, mdi} from "vuetify/iconsets/mdi";
import "@mdi/font/css/materialdesignicons.css";
import myIndex from "@/router";

const vuetify = createVuetify({
  components,
  directives,
  theme: {
    defaultTheme: 'light',
  },
  icons: {
    defaultSet: "mdi",
    aliases,
    sets: {
      mdi,
    },
  },
});

createApp(App).use(createPinia()).use(myIndex).use(vuetify).mount('#app')
