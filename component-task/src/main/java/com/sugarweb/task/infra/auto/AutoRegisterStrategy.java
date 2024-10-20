package com.sugarweb.task.infra.auto;

public enum AutoRegisterStrategy {
    /**
     * 保存，不更新
     */
    SAVE,
    /**
     * 保存并更新
     */
    SAVE_AND_UPDATE,
    /**
     * 重置，删除所有任务，重新注册
     */
    RESET,
    /**
     * 禁用，不注册任何任务
     */
    DISABLED
}