package com.sugarweb.dictionary.domain;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 字典信息
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class Dictionary {
    @Size(max = 32)
    private String id;

    @Size(max = 32)
    private String dictGroup;

    @Size(max = 32)
    private String dictCode;

    @Size(max = 32)
    private String dictName;

    @Size(max = 1)
    private String dictType;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
