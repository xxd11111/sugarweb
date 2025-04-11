package com.sugarweb.digitalHuman.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.service.dto.ScriptDetailDto;
import com.sugarweb.digitalHuman.service.dto.ScriptPageQuery;
import com.sugarweb.digitalHuman.service.dto.ScriptSaveDto;
import com.sugarweb.digitalHuman.service.dto.ScriptUpdateDto;
import com.sugarweb.framework.orm.PageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ScriptService
 *
 * @author xxd
 * @version 1.0
 */
@Service
public class ScriptService {

    public IPage<ScriptDetailDto> page(ScriptPageQuery query) {
        return Db.page(PageHelper.getPage(query), new LambdaQueryWrapper<Script>()
                .like(StrUtil.isNotEmpty(query.getScriptName()), Script::getScriptName, query.getScriptName())
                .orderByDesc(Script::getCreateTime)
        ).convert(this::buildScriptDetailDto);
    }

    public ScriptDetailDto detail(String scriptId) {
        Script script = Db.getById(scriptId, Script.class);
        return buildScriptDetailDto(script);
    }

    public ScriptDetailDto save(ScriptSaveDto saveDto) {
        Script script = new Script();
        script.setScriptName(saveDto.getScriptName());
        script.setDescription(saveDto.getDescription());
        Db.save(script);
        return buildScriptDetailDto(script);
    }

    public ScriptDetailDto update(ScriptUpdateDto updateDto) {
        Script script = Db.getById(updateDto.getScriptId(), Script.class);
        if (script != null) {
            script.setScriptName(updateDto.getScriptName());
            script.setDescription(updateDto.getDescription());
            Db.updateById(script);
        }
        return buildScriptDetailDto(script);
    }

    private ScriptDetailDto buildScriptDetailDto(Script script) {
        if (script == null) {
            return null;
        }
        ScriptDetailDto scriptDetailDto = new ScriptDetailDto();
        scriptDetailDto.setScriptId(script.getScriptId());
        scriptDetailDto.setScriptName(script.getScriptName());
        scriptDetailDto.setDescription(script.getDescription());
        return scriptDetailDto;
    }

    public void remove(String scriptId) {
        Db.removeById(scriptId, Script.class);
    }

    public Script getById(String scriptId) {
        Script script = Db.getById(scriptId, Script.class);
        List<ScriptNode> scriptNodeList = Db.lambdaQuery(ScriptNode.class)
                .eq(ScriptNode::getScriptId, scriptId)
                .list();
        script.setScriptNodeList(scriptNodeList);
        return script;
    }
}
