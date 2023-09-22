package com.sugarcoat.protocol.protection;

/**
 * TODO 限流策略接口
 *
 * @Author lmh
 * @Description
 * @CreateTime 2023-08-23 15:59
 */

public interface RateLimitStrategy {

    Boolean limit();

}
