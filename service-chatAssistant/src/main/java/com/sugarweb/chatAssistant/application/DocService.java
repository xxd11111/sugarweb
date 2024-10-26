package com.sugarweb.chatAssistant.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.chatAssistant.controller.DocDetailDto;
import com.sugarweb.chatAssistant.controller.DocPageQuery;
import com.sugarweb.chatAssistant.controller.DocSaveDto;
import com.sugarweb.chatAssistant.controller.DocUpdateDto;
import com.sugarweb.chatAssistant.domain.DocInfo;

/**
 * DocService
 *
 * @author xxd
 * @version 1.0
 */
public class DocService {
    public IPage<DocDetailDto> page(DocPageQuery query) {
        return null;
    }

    public DocDetailDto detail(String docId) {
        return null;
    }

    public DocDetailDto save(DocSaveDto saveDto) {
        return null;

    }

    public DocDetailDto update(DocUpdateDto updateDto) {
        return null;

    }

    private DocDetailDto convert(DocInfo docInfo) {
        return null;

    }
}
