package com.sugarweb.chatAssistant.infra;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.sugarweb.framework.exception.ServerException;
import com.sugarweb.framework.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author xxd
 * @since 2024/9/30 22:01
 */


@Slf4j
public class ChatTtsClient {

    /**
     * # API调用代码
     *
     *
     *
     *
     *
     *

     *
     * import requests
     *
     *
     * res = requests.post('http://127.0.0.1:9966/tts', data={
     *         "text": "若不懂无需填写",
     *         "prompt": "",
     *         "voice": "3333",
     *         "temperature": 0.3,
     *         "top_p": 0.7,
     *         "top_k": 20,
     *         "skip_refine": 0,
     *         "custom_voice": 0
     *         })
     *         print(res.json())
     *
     *         #ok
     *         {code:0, msg:'ok', audio_files:[{filename: E:/python/chattts/static/wavs/20240601-22_12_12-c7456293f7b5e4dfd3ff83bbd884a23e.wav, url: http://127.0.0.1:9966/static/wavs/20240601-22_12_12-c7456293f7b5e4dfd3ff83bbd884a23e.wav}]}
     *
     *         #error
     *         {code:1, msg:"error"}
     */

    public TtsResponse tts(TtsRequest ttsRequest){
        Map<String, Object> formData = new HashMap<>();
        // * text: str| 必须， 要合成语音的文字
        formData.put("text", ttsRequest.getText());
        // * prompt: str| 可选，默认 空， 设定 笑声、停顿，例如 [oral_2][laugh_0][break_6]
        if (StrUtil.isEmpty(ttsRequest.getPrompt())){
            formData.put("prompt", "");
        }else {
            formData.put("prompt", ttsRequest.getPrompt());
        }
        // * voice: 可选，默认 2222, 决定音色的数字， 2222 | 7869 | 6653 | 4099 | 5099，可选其一，或者任意传入将随机使用音色
        if (StrUtil.isEmpty(ttsRequest.getVoice())){
            formData.put("voice", "2222");
        }else {
            formData.put("voice", ttsRequest.getVoice());
        }
        // * temperature: float| 可选， 默认 0.3
        if (null == ttsRequest.getTemperature()){
            formData.put("temperature", 0.3f);
        }else {
            formData.put("temperature", ttsRequest.getTemperature());
        }
        // * top_p: float| 可选， 默认 0.7
        if (null == ttsRequest.getTop_p()){
            formData.put("top_p", 0.7f);
        }else {
            formData.put("top_p", ttsRequest.getTop_p());
        }
        // * top_k: int| 可选， 默认 20
        if (null == ttsRequest.getTop_k()){
            formData.put("top_k", 20);
        }else {
            formData.put("top_k", ttsRequest.getTop_k());
        }
        // * skip_refine: int| 可选， 默认0， 1=跳过 refine text，0=不跳过
        if (null == ttsRequest.getSkip_refine()){
            formData.put("skip_refine", 0);
        }else {
            formData.put("skip_refine", ttsRequest.getSkip_refine());
        }
        // * custom_voice: int| 可选， 默认0，自定义获取音色值时的种子值，需要大于0的整数，如果设置了则以此为准，将忽略 voice
        if (null == ttsRequest.getCustom_voice()){
            formData.put("custom_voice", 0);
        }else {
            formData.put("custom_voice", ttsRequest.getCustom_voice());
        }

        HttpResponse execute = HttpUtil.createRequest(Method.POST, "http://127.0.0.1:9966/tts")
                .form(formData)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .execute();
        if (!execute.isOk()) {
            log.error(StrUtil.format("tts请求失败，响应数据:{}", execute));
            throw new ServerException("网络异常");
        }
        TtsResponse ttsResponse = JsonUtil.toObject(execute.body(), TtsResponse.class);

        return ttsResponse;
    }

    public static void main(String[] args) {
        ChatTtsClient chatTtsClient = new ChatTtsClient();
        long start = System.currentTimeMillis();
        TtsResponse ttsResponse = chatTtsClient.tts(TtsRequest.builder().text("想看看你在说什么").build());
        long end = System.currentTimeMillis();
        long start2 = System.currentTimeMillis();
        TtsResponse ttsResponse2 = chatTtsClient.tts(TtsRequest.builder().text("想看看你在说什么").build());
        long end2 = System.currentTimeMillis();
        System.out.println(end2-start2);
        System.out.println(ttsResponse2);
    }

}
