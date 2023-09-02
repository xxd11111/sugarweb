package com.sugarcoat.api.oss;

import lombok.Data;

/**
 * 文件业务信息 todo 待思考业务绑定存储及上传限制
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/7
 */
@Data
public class FileBusinessInfo {

	private String associationId;

	private String fileGroup;

	private String fileId;

}
