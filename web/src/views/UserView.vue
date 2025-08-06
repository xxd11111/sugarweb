<script setup lang="ts">
import { ref, onMounted, h } from 'vue'
import {
  NButton,
  NDataTable,
  NCard,
  NForm,
  NFormItem,
  NInput,
  NSelect,
  NModal,
  NSpace,
  NPopconfirm,
  NTag,
  useMessage
} from 'naive-ui'
import type { DataTableColumns } from 'naive-ui'
import {
  getUserList,
  createUser,
  updateUser,
  deleteUser,
  type User as ServiceUser,
  type UserForm as ServiceUserForm,
  type UserQuery
} from '@/services/user'

// 消息提示
const message = useMessage()

// 用户数据接口
interface User {
  id: string
  username: string
  email: string
  mobilePhone: string
  nickName: string
  enable: string
}

// 用户表单接口
interface UserForm {
  id?: string
  username: string
  email: string
  mobilePhone: string
  nickName: string
  password?: string
  enable: string
}

// 用户查询参数
const queryParams = ref<UserQuery>({
  username: '',
  email: '',
  mobilePhone: '',
  nickName: '',
  enable: ''
})

// 表格数据和加载状态
const tableData = ref<User[]>([])
const loading = ref(false)
const pagination = ref({
  page: 1,
  pageSize: 10,
  itemCount: 0
})

// 表单相关
const showModal = ref(false)
const formType = ref<'create' | 'edit'>('create')
const formModel = ref<UserForm>({
  username: '',
  email: '',
  mobilePhone: '',
  nickName: '',
  enable: '1'
})

// 表单规则
const formRules = {
  username: {
    required: true,
    message: '请输入用户名',
    trigger: 'blur'
  },
  email: {
    required: true,
    message: '请输入邮箱',
    trigger: 'blur'
  }
}

// 表格列定义
const columns: DataTableColumns<User> = [
  {
    title: '用户名',
    key: 'username'
  },
  {
    title: '邮箱',
    key: 'email'
  },
  {
    title: '手机号',
    key: 'mobilePhone'
  },
  {
    title: '昵称',
    key: 'nickName'
  },
  {
    title: '状态',
    key: 'enable',
    render(row) {
      return row.enable === '1' ?
        h(NTag, { type: 'success' }, { default: () => '启用' }) :
        h(NTag, { type: 'error' }, { default: () => '禁用' })
    }
  },
  {
    title: '操作',
    key: 'actions',
    render(row) {
      return h(NSpace, {}, {
        default: () => [
          h(NButton, {
            size: 'small',
            onClick: () => handleEdit(row)
          }, { default: () => '编辑' }),
          h(NPopconfirm, {
            onPositiveClick: () => handleDelete(row.id)
          }, {
            trigger: () => h(NButton, { size: 'small', type: 'error' }, { default: () => '删除' }),
            default: () => '确认删除该用户吗？'
          })
        ]
      })
    }
  }
]

// 获取用户列表
const fetchUsers = async () => {
  loading.value = true
  try {
    const response = await getUserList({
      ...queryParams.value,
      current: pagination.value.page,
      size: pagination.value.pageSize
    })

    tableData.value = response.data.records
    pagination.value.itemCount = response.data.total
  } catch (error) {
    console.error('获取用户列表失败:', error)
    message.error('获取用户列表失败')
  } finally {
    loading.value = false
  }
}

// 处理分页变化
const handlePageChange = (page: number) => {
  pagination.value.page = page
  fetchUsers()
}

// 处理页面大小变化
const handlePageSizeChange = (pageSize: number) => {
  pagination.value.pageSize = pageSize
  pagination.value.page = 1
  fetchUsers()
}

// 处理查询
const handleSearch = () => {
  pagination.value.page = 1
  fetchUsers()
}

// 重置查询
const handleReset = () => {
  queryParams.value = {
    username: '',
    email: '',
    mobilePhone: '',
    nickName: '',
    enable: ''
  }
  pagination.value.page = 1
  fetchUsers()
}

// 处理新增
const handleCreate = () => {
  formType.value = 'create'
  formModel.value = {
    username: '',
    email: '',
    mobilePhone: '',
    nickName: '',
    enable: '1'
  }
  showModal.value = true
}

// 处理编辑
const handleEdit = (row: User) => {
  formType.value = 'edit'
  formModel.value = {
    id: row.id,
    username: row.username,
    email: row.email,
    mobilePhone: row.mobilePhone,
    nickName: row.nickName,
    enable: row.enable
  }
  showModal.value = true
}

// 处理删除
const handleDelete = async (id: string) => {
  try {
    await deleteUser(Number(id))
    message.success('删除成功')
    fetchUsers()
  } catch (error) {
    console.error('删除用户失败:', error)
    message.error('删除用户失败')
  }
}

// 表单提交
const handleSubmit = async () => {
  try {
    if (formType.value === 'create') {
      // 检查密码是否填写
      if (!formModel.value.password) {
        message.warning('请输入密码')
        return
      }

      await createUser({
        username: formModel.value.username,
        email: formModel.value.email,
        roles: []
      } as Omit<ServiceUser, 'id' | 'createdAt' | 'updatedAt'>)
      message.success('新增用户成功')
    } else {
      if (formModel.value.id) {
        await updateUser(Number(formModel.value.id), {
          username: formModel.value.username,
          email: formModel.value.email
        } as Partial<ServiceUser>)
        message.success('更新用户成功')
      }
    }

    showModal.value = false
    fetchUsers()
  } catch (error) {
    console.error('保存用户失败:', error)
    message.error('保存用户失败')
  }
}

// 挂载时获取数据
onMounted(() => {
  fetchUsers()
})
</script>

<template>
  <div class="user-view">
    <n-card title="用户管理">
      <!-- 查询表单 -->
      <n-form inline :model="queryParams" label-placement="left">
        <n-form-item label="用户名">
          <n-input v-model:value="queryParams.username" placeholder="请输入用户名" />
        </n-form-item>
        <n-form-item label="邮箱">
          <n-input v-model:value="queryParams.email" placeholder="请输入邮箱" />
        </n-form-item>
        <n-form-item label="手机号">
          <n-input v-model:value="queryParams.mobilePhone" placeholder="请输入手机号" />
        </n-form-item>
        <n-form-item label="状态">
          <n-select
            v-model:value="queryParams.enable"
            :options="[
              { label: '全部', value: '' },
              { label: '启用', value: '1' },
              { label: '禁用', value: '0' }
            ]"
            style="width: 100px"
          />
        </n-form-item>
        <n-form-item>
          <n-button type="primary" @click="handleSearch">查询</n-button>
          <n-button style="margin-left: 10px" @click="handleReset">重置</n-button>
        </n-form-item>
      </n-form>

      <!-- 操作按钮 -->
      <div style="margin-bottom: 16px">
        <n-button type="primary" @click="handleCreate">新增用户</n-button>
      </div>

      <!-- 数据表格 -->
      <n-data-table
        :columns="columns"
        :data="tableData"
        :loading="loading"
        :pagination="pagination"
        remote
        @update:page="handlePageChange"
        @update:page-size="handlePageSizeChange"
      />
    </n-card>

    <!-- 表单弹窗 -->
    <n-modal v-model:show="showModal" preset="card" style="width: 500px" title="用户信息">
      <n-form :model="formModel" :rules="formRules" ref="formRef">
        <n-form-item label="用户名" path="username">
          <n-input v-model:value="formModel.username" placeholder="请输入用户名" />
        </n-form-item>
        <n-form-item label="邮箱" path="email">
          <n-input v-model:value="formModel.email" placeholder="请输入邮箱" />
        </n-form-item>
        <n-form-item label="手机号" path="mobilePhone">
          <n-input v-model:value="formModel.mobilePhone" placeholder="请输入手机号" />
        </n-form-item>
        <n-form-item label="昵称" path="nickName">
          <n-input v-model:value="formModel.nickName" placeholder="请输入昵称" />
        </n-form-item>
        <n-form-item v-if="formType === 'create'" label="密码" path="password" required>
          <n-input v-model:value="formModel.password" type="password" placeholder="请输入密码" />
        </n-form-item>
        <n-form-item label="状态" path="enable">
          <n-select
            v-model:value="formModel.enable"
            :options="[
              { label: '启用', value: '1' },
              { label: '禁用', value: '0' }
            ]"
          />
        </n-form-item>
      </n-form>
      <template #footer>
        <n-space justify="end">
          <n-button @click="showModal = false">取消</n-button>
          <n-button type="primary" @click="handleSubmit">确定</n-button>
        </n-space>
      </template>
    </n-modal>
  </div>
</template>

<style scoped>
.user-view {
  padding: 20px;
}
</style>
