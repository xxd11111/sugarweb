package com.sugarweb.chatAssistant.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * ai特征
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class AiFeature {
    @TableId
    private String userId;

    private String userName;

    //发言次数
    private Integer speakCount;

    //发言频率
    private Integer speakFrequency;

    //用户性格
    private String userCharacter;

    //友善性
    private Integer friendliness;

    //用户喜好
    private String chatPreference;

    //用户印象描述
    private String userDescription;

}
