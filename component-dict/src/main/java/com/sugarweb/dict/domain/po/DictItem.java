package com.sugarweb.dict.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 字典信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class DictItem {
    @TableId
    @Size(max = 32)
    private String dictItemId;

    @Size(max = 32)
    private String dictGroupId;

    @Size(max = 32)
    private String dictItemCode;

    @Size(max = 32)
    private String dictItemName;

    @Size(max = 1)
    private String dictItemStatus;

}
