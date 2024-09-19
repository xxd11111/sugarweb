package com.sugarweb.param.application;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.exception.ServiceException;
import com.sugarweb.framework.orm.PageHelper;
import com.sugarweb.param.domain.Param;

import java.util.List;
import java.util.Set;

/**
 * 参数缓存
 *
 * @author xxd
 * @version 1.0
 */
public class ParamService {

    public void save(ParamDto paramDto) {
        if (Db.getOne(new LambdaQueryWrapper<Param>().eq(Param::getParamCode, paramDto.getParamCode())) != null) {
            throw new ServiceException("参数编码已存在");
        }
        Param param = new Param();
        param.setParamCode(paramDto.getParamCode());
        param.setParamName(paramDto.getParamName());
        param.setParamValue(paramDto.getParamValue());
        param.setParamComment(paramDto.getParamComment());
        Db.save(param);
    }

    public void update(ParamDto paramDto) {
        Param param = Db.getById(paramDto.getParamId(), Param.class);
        if (param == null) {
            throw new ServiceException("参数不存在");
        }
        param.setParamCode(paramDto.getParamCode());
        param.setParamName(paramDto.getParamName());
        param.setParamValue(paramDto.getParamValue());
        param.setParamComment(paramDto.getParamComment());
        Db.save(param);
    }

    public void updateValue(String code, String value) {
        Param param = Db.getOne(new LambdaQueryWrapper<Param>().eq(Param::getParamCode, code));
        if (param == null) {
            throw new ServiceException("参数不存在");
        }
        param.setParamValue(value);
        Db.updateById(param);
    }

    public void removeByCode(String code) {
        Db.remove(new LambdaQueryWrapper<Param>().eq(Param::getParamCode, code));
    }

    public void removeById(String id) {
        Db.removeById(id, Param.class);
    }

    public void removeByIds(Set<String> ids) {
        Db.removeByIds(ids, Param.class);
    }

    public List<ParamDto> findAll() {
        return Db.list(Param.class).stream().map(ParamConvert::toParamDto).toList();
    }

    public ParamDto getByCode(String code) {
        Param param = Db.getOne(new LambdaQueryWrapper<Param>().eq(Param::getParamCode, code));
        if (param == null) {
            return null;
        }
        return ParamConvert.toParamDto(param);
    }

    public String getValueByCode(String code) {
        Param param = Db.getOne(new LambdaQueryWrapper<Param>()
                .select(Param::getParamValue)
                .eq(Param::getParamCode, code));
        if (param == null) {
            return null;
        } else {
            return param.getParamValue();
        }
    }

    public ParamDto getById(String id) {
        Param param = Db.getById(id, Param.class);
        if (param == null) {
            return null;
        }
        return ParamConvert.toParamDto(param);
    }


    public IPage<ParamDto> page(PageQuery pageQuery, ParamQuery query) {
        return Db.page(PageHelper.getPage(pageQuery), new LambdaQueryWrapper<Param>()
                .like(query.getParamCode() != null, Param::getParamCode, query.getParamCode())
                .like(query.getParamName() != null, Param::getParamName, query.getParamName())
        ).convert(ParamConvert::toParamDto);
    }

}
