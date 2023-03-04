package com.xxd.sugarcoat.support.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xxd
 * @description 参数dto
 * @date 2022-11-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Param {
    private String key;
    private String value;
}