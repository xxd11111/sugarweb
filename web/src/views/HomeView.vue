<script setup lang="ts">
import { 
  NCard, 
  NSpace, 
  NGrid, 
  NGridItem,
  NStatistic,
  NIcon,
  NLayout,
  NLayoutContent
} from 'naive-ui'
import { PersonOutline, DocumentTextOutline, BarChartOutline, SettingsOutline } from '@vicons/ionicons5'

const statistics = [
  { label: '用户总数', value: '1,234', icon: PersonOutline },
  { label: '文档数量', value: '567', icon: DocumentTextOutline },
  { label: '系统状态', value: '正常', icon: BarChartOutline },
  { label: '配置项', value: '89', icon: SettingsOutline }
]

const quickActions = [
  { name: '用户管理', icon: PersonOutline },
  { name: '角色权限', icon: DocumentTextOutline },
  { name: '系统监控', icon: BarChartOutline },
  { name: '配置管理', icon: SettingsOutline }
]
</script>

<template>
  <n-layout-content>
    <!-- 统计数据 -->
    <n-card title="系统概览" style="margin-bottom: 24px;">
      <n-grid cols="1 s:2 m:4" responsive="screen" :x-gap="12" :y-gap="12">
        <n-grid-item v-for="stat in statistics" :key="stat.label">
          <n-statistic :label="stat.label" :value="stat.value">
            <template #prefix>
              <n-icon :component="stat.icon" />
            </template>
          </n-statistic>
        </n-grid-item>
      </n-grid>
    </n-card>

    <n-grid cols="1 600:2" :x-gap="24" :y-gap="24" responsive="screen">
      <!-- 快捷操作 -->
      <n-grid-item>
        <n-card title="快捷操作">
          <n-grid cols="2" :x-gap="12" :y-gap="12">
            <n-grid-item v-for="action in quickActions" :key="action.name">
              <n-card hoverable style="cursor: pointer; text-align: center;">
                <n-icon size="24" :component="action.icon" />
                <div style="margin-top: 8px;">{{ action.name }}</div>
              </n-card>
            </n-grid-item>
          </n-grid>
        </n-card>
      </n-grid-item>

      <!-- 系统信息 -->
      <n-grid-item>
        <n-card title="系统信息">
          <n-space vertical>
            <div>SugarWeb管理系统</div>
            <div>版本: v1.0.0</div>
            <div>最后更新: 2025-08-05</div>
            <div>运行环境: Vue 3 + Naive UI</div>
            <div>状态: 正常运行</div>
          </n-space>
        </n-card>
      </n-grid-item>
    </n-grid>
  </n-layout-content>
</template>

<style scoped>
p {
  line-height: 1.6;
}
</style>