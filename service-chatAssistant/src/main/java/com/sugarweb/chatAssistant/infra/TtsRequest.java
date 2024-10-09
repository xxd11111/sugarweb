package com.sugarweb.chatAssistant.infra;

import cn.hutool.core.util.StrUtil;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * text: str| 必须， 要合成语音的文字
 *
 * voice: 可选，默认 2222, 决定音色的数字， 2222 | 7869 | 6653 | 4099 | 5099，可选其一，或者任意传入将随机使用音色
 *
 * prompt: str| 可选，默认 空， 设定 笑声、停顿，例如 [oral_2][laugh_0][break_6]
 *
 * temperature: float| 可选， 默认 0.3
 *
 * top_p: float| 可选， 默认 0.7
 *
 * top_k: int| 可选， 默认 20
 *
 * skip_refine: int| 可选， 默认0， 1=跳过 refine text，0=不跳过
 *
 * custom_voice: int| 可选， 默认0，自定义获取音色值时的种子值，需要大于0的整数，如果设置了则以此为准，将忽略 voice
 *
 * @author xxd
 * @since 2024/9/30 22:36
 */
@Data
@Builder
public class TtsRequest {

    private String text;
    private String prompt;
    private String voice;
    private Float temperature;
    private Float top_p;
    private Float top_k;
    private Integer skip_refine;
    private Integer custom_voice;
    
    public Map<String, Object> toFormData() {
        Map<String, Object> formData = new HashMap<>();
        // * text: str| 必须， 要合成语音的文字
        formData.put("text", this.getText());
        // * prompt: str| 可选，默认 空， 设定 笑声、停顿，例如 [oral_2][laugh_0][break_6]
        if (StrUtil.isEmpty(this.getPrompt())){
            formData.put("prompt", "");
        }else {
            formData.put("prompt", this.getPrompt());
        }
        // * voice: 可选，默认 2222, 决定音色的数字， 2222 | 7869 | 6653 | 4099 | 5099，可选其一，或者任意传入将随机使用音色
        if (StrUtil.isEmpty(this.getVoice())){
            formData.put("voice", "2222");
        }else {
            formData.put("voice", this.getVoice());
        }
        // * temperature: float| 可选， 默认 0.3
        if (null == this.getTemperature()){
            formData.put("temperature", 0.3f);
        }else {
            formData.put("temperature", this.getTemperature());
        }
        // * top_p: float| 可选， 默认 0.7
        if (null == this.getTop_p()){
            formData.put("top_p", 0.7f);
        }else {
            formData.put("top_p", this.getTop_p());
        }
        // * top_k: int| 可选， 默认 20
        if (null == this.getTop_k()){
            formData.put("top_k", 20);
        }else {
            formData.put("top_k", this.getTop_k());
        }
        // * skip_refine: int| 可选， 默认0， 1=跳过 refine text，0=不跳过
        if (null == this.getSkip_refine()){
            formData.put("skip_refine", 0);
        }else {
            formData.put("skip_refine", this.getSkip_refine());
        }
        // * custom_voice: int| 可选， 默认0，自定义获取音色值时的种子值，需要大于0的整数，如果设置了则以此为准，将忽略 voice
        if (null == this.getCustom_voice()){
            formData.put("custom_voice", 0);
        }else {
            formData.put("custom_voice", this.getCustom_voice());
        }
        return formData;
    }

}
