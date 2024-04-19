package com.sugarweb.param.application;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.param.domain.Param;
import com.sugarweb.param.domain.ParamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;

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
        Param param = Optional.ofNullable(paramRepository.selectById(paramDto.getId()))
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
    public void removeByCode(String code) {
        Optional<Param> one = Optional.ofNullable(paramRepository.selectOne(new LambdaQueryWrapper<Param>().eq(Param::getCode, code)));
        one.ifPresent(param -> paramRepository.deleteById(param.getId()));
    }

    @Override
    public void removeById(String id) {
        paramRepository.deleteById(id);
    }

    @Override
    public void removeByIds(Set<String> ids) {
        paramRepository.deleteBatchIds(ids);
    }

    @Override
    public List<ParamDto> findAll() {
        return paramRepository.selectList().stream().map(this::paramDto).toList();
    }

    private ParamDto paramDto(Param param) {
        ParamDto paramDto = new ParamDto();
        BeanUtils.copyProperties(param, paramDto);
        return paramDto;
    }

    @Override
    public Optional<ParamDto> findByCode(String code) {
        return Optional.ofNullable(paramRepository.selectOne(new LambdaQueryWrapper<Param>().eq(Param::getCode, code)))
                .map(ParamConvert::getParamDTO);
    }

    @Override
    public Optional<ParamDto> findById(String id) {
        return Optional.ofNullable(paramRepository.selectById(id))
                .map(ParamConvert::getParamDTO);
    }

    @Override
    public IPage<ParamDto> findPage(PageQuery pageQuery, ParamQueryDto cmd) {
        return paramRepository.selectPage(new Page<>(pageQuery.getPageNumber(), pageQuery.getPageSize()), new LambdaQueryWrapper<Param>())
                .convert(ParamConvert::getParamDTO);
    }

}
