package com.sugarweb.chatAssistant.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.chatAssistant.domain.KbInfo;

/**
 * 知识库服务
 *
 * @author xxd
 * @version 1.0
 */
public class KbService {
    public IPage<KbDetailDto> page(KbPageQuery query) {
    }

    public KbDetailDto detail(String kbId) {
        KbInfo kbInfo = Db.getById(kbId, KbInfo.class);


    }

    public KbDetailDto save(KbSaveDto saveDto) {
    }

    public KbDetailDto update(KbUpdateDto updateDto) {

    }

    private KbDetailDto buildKbDetail(KbInfo kbInfo) {
        KbDetailDto kbDetailDto = new KbDetailDto();
        kbDetailDto.setKbId(kbInfo.getKbId());
    }



}
