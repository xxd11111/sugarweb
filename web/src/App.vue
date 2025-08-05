<template>
  <n-config-provider :theme="theme">
    <n-layout>
      <n-layout-header>
        <HeaderBar/>
      </n-layout-header>
      <n-layout-content content-style="padding: 24px;">
        <RouterView />
      </n-layout-content>
    </n-layout>
  </n-config-provider>
</template>


<script lang="ts">
import type { GlobalTheme } from 'naive-ui'
import { darkTheme } from 'naive-ui'
import { defineComponent, ref } from 'vue'
import HeaderBar from '@/components/HeaderBar.vue'

export default defineComponent({
  components: { HeaderBar: HeaderBar },
  setup() {
    return {
      darkTheme,
      theme: ref<GlobalTheme | null>(null)
    }
  }
})
</script>

<style scoped>

</style>

