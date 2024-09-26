package com.sugarweb.task.infra.auto;

public enum AutoRegisterStrategy {
    /**
     * 保存，不更新
     */
    save,
    /**
     * 保存并更新
     */
    saveAndUpdate,
    /**
     * 重置，删除所有任务，重新注册
     */
    reset,
    /**
     * 禁用，不注册任何任务
     */
    disabled
}