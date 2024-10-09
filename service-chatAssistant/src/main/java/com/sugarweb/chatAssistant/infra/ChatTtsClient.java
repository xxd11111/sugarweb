package com.sugarweb.chatAssistant.infra;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.sugarweb.framework.exception.ServerException;
import com.sugarweb.framework.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * ChatTtsClient API调用代码
 *
 * @author xxd
 * @since 2024/9/30 22:01
 */


@Slf4j
public class ChatTtsClient {

    /**
     * # API调用代码
     * res = requests.post('http://127.0.0.1:9966/tts', data={
     * "text": "若不懂无需填写",
     * "prompt": "",
     * "voice": "3333",
     * "temperature": 0.3,
     * "top_p": 0.7,
     * "top_k": 20,
     * "skip_refine": 0,
     * "custom_voice": 0
     * })
     * print(res.json())
     * <p>
     * #ok
     * {code:0, msg:'ok', audio_files:[{filename: E:/python/chattts/static/wavs/20240601-22_12_12-c7456293f7b5e4dfd3ff83bbd884a23e.wav, url: http://127.0.0.1:9966/static/wavs/20240601-22_12_12-c7456293f7b5e4dfd3ff83bbd884a23e.wav}]}
     * <p>
     * #error
     * {code:1, msg:"error"}
     */

    public TtsResponse tts(TtsRequest ttsRequest) {
        Map<String, Object> formData = ttsRequest.toFormData();
        HttpResponse execute = HttpUtil.createRequest(Method.POST, "http://127.0.0.1:9966/tts")
                .form(formData)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .execute();
        if (!execute.isOk()) {
            log.error(StrUtil.format("tts请求失败，响应数据:{}", execute));
            throw new ServerException("网络异常");
        }
        return JsonUtil.toObject(execute.body(), TtsResponse.class);
    }

}
