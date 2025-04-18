package com.sugarweb.digitalHuman.generate;

import lombok.Data;

import java.util.List;

/**
 * RustApiGenerator
 *
 * @author xxd
 * @since 2025/4/15 22:03
 */
public class RustApiGenerator {

    String structTemplate = """
            #[derive(Default, serde::Deserialize, serde::Serialize)]
            pub struct {structName} {
            {fieldListTemplate}
            }
            """;

    String fieldListTemplate = """
                {comment}
                pub {fieldName}: option<{fieldType}>,
            """;

    String urlApiTemplate = """
            pub async fn {funName}(request: {requestStruct}) -> anyhow::Result<{responseStruct}> {
                // 构造完整的 URL
                let param = serde_urlencoded::to_string(&request).context("urlencoded序列化失败")?;
                let url = format!("{}{}?{}", URL_PREFIX, KB_LIST_ENDPOINT, param);
            
                // 发起 HTTP 请求
                let client = reqwest::Client::new();
                let response = client
                    .get(url)
                    .send()
                    .await
                    .context("Failed to send request")?;
            
                // 检查是否200响应
                let status = response.status();
                // 处理响应体
                let body = response
                    .text()
                    .await
                    .context("Failed to read response body")?;
            
                if !status.is_success() {
                    return Err(anyhow::anyhow!("HTTP status: {}, body: {}", status, body));
                }
            
                // 解析JSON
                let r: {responseStruct} = serde_json::from_str(&body)
                    .context("Failed to deserialize JSON")?;
                Ok(r)
            }
            """;

    @Data
    public class RustStruct {
        String structName;
        List<RustField> fieldList;
    }

    @Data
    public class RustField {
        String fieldName;
        String fieldType;
        String comment;
    }

    @Data
    public class RustApi{
        String funName;
        String requestStruct;
        String responseStruct;

    }

}
