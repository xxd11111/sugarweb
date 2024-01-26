package com.sugarweb.param.application;

import com.querydsl.core.types.dsl.Expressions;
import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.param.domain.Param;
import com.sugarweb.param.domain.QParam;
import com.sugarweb.param.domain.ParamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.*;

/**
 * 参数服务实现类
 *
 * @author xxd
 * @version 1.0
 */
@RequiredArgsConstructor
public class ParamServiceImpl implements ParamService {

    private final ParamRepository paramRepository;

    @Override
    public void save(ParamDto paramDto) {
        Param param = paramRepository.findById(paramDto.getId())
                .orElseThrow(() -> new ValidateException("not find param"));
        //更新
        param.setName(paramDto.getName());
        param.setValue(paramDto.getValue());
        param.setComment(paramDto.getComment());
        paramRepository.save(param);
    }

    @Override
    public void saveAll(Collection<ParamDto> paramDtos) {
        for (ParamDto paramDto : paramDtos) {
            this.save(paramDto);
        }
    }

    @Override
    public void reset(Set<String> ids) {
        Iterable<Param> params = paramRepository.findAllById(ids);
        paramRepository.saveAll(params);
    }

    @Override
    public void removeByCode(String code) {
        Optional<Param> one = paramRepository.findOne(QParam.param.code.eq(code));
        one.ifPresent(param -> paramRepository.deleteById(param.getId()));
    }

    @Override
    public void removeById(String id) {
        paramRepository.deleteById(id);
    }

    @Override
    public void removeByIds(Set<String> ids) {
        paramRepository.deleteAllById(ids);
    }

    @Override
    public List<ParamDto> findAll() {
        return paramRepository.findAll().stream().map(this::paramDto).toList();
    }

    private ParamDto paramDto(Param param) {
        ParamDto paramDto = new ParamDto();
        BeanUtils.copyProperties(param, paramDto);
        return paramDto;
    }

    @Override
    public Optional<ParamDto> findByCode(String code) {
        return paramRepository
                .findOne(QParam.param.code.eq(code))
                .map(ParamConvert::getParamDTO);
    }

    @Override
    public Optional<ParamDto> findById(String id) {
        return paramRepository.findById(id)
                .map(ParamConvert::getParamDTO);
    }

    @Override
    public PageData<ParamDto> findPage(PageQuery pageDto, ParamQueryDto cmd) {
        PageRequest pageRequest = PageRequest.of(pageDto.getPageNumber(), pageDto.getPageSize());
        Page<ParamDto> page = paramRepository.findAll(Expressions.TRUE, pageRequest)
                .map(ParamConvert::getParamDTO);
        return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

}
