package com.sugarcoat.support.orm;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源配置
 *
 * @author xxd
 * @since 2023/11/3 23:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "sugarcoat.datasource")
public class DynamicDataSourceProperties extends SgcDataSourceProperties{

    private Map<String, SgcDataSourceProperties> dynamic = new HashMap<>();

}
