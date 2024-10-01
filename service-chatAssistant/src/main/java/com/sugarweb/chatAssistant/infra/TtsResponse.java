package com.sugarweb.chatAssistant.infra;

import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 *      * res = requests.post('http://127.0.0.1:9966/tts', data={
 *      *         "text": "若不懂无需填写",
 *      *         "prompt": "",
 *      *         "voice": "3333",
 *      *         "temperature": 0.3,
 *      *         "top_p": 0.7,
 *      *         "top_k": 20,
 *      *         "skip_refine": 0,
 *      *         "custom_voice": 0
 *      *         })
 *      *         print(res.json())
 *      *
 *      *         #ok
 *      *
 *      *         #error
 *      *         {code:1, msg:"error"}
 *
 * @author xxd
 * @since 2024/9/30 22:36
 */
@Data
public class TtsResponse {

    private Integer code;

    private String msg;

    private List<TtsAudioFile> audio_files;

    private boolean success(){
        return code == 0;
    }
}
