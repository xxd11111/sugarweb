package com.sugarcoat.support.server.domain.later;

import lombok.Data;

/**
 * 内存信息
 *
 * @author 许向东
 * @date 2023/9/25
 */
@Data
public class MemoryInfo {

    private Long heapUsed;

    private Long heapMax;

    private Long nonHeapUsed;

    private Long nonHeapMax;

}
