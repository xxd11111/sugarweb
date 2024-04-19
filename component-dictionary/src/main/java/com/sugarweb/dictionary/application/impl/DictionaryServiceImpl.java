package com.sugarweb.dictionary.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.dictionary.application.DictionaryService;
import com.sugarweb.dictionary.application.dto.DictionaryDto;
import com.sugarweb.dictionary.application.dto.DictionaryQueryDto;
import com.sugarweb.dictionary.domain.Dictionary;
import com.sugarweb.dictionary.domain.DictionaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 默认实现字典服务
 *
 * @author xxd
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {

    private final DictionaryRepository dictionaryRepository;

    @Override
    public void save(DictionaryDto dictionaryDto) {
        Dictionary dictionary = new Dictionary();
        dictionary.setId(dictionaryDto.getId());
        dictionary.setDictCode(dictionaryDto.getDictCode());
        dictionary.setDictName(dictionaryDto.getDictName());
        dictionary.setDictGroup(dictionaryDto.getDictGroup());
        dictionaryRepository.save(dictionary);
    }

    @Override
    public void save(Collection<DictionaryDto> dictionaryDtos) {
        for (DictionaryDto dictionaryDto : dictionaryDtos) {
            save(dictionaryDto);
        }
    }

    private DictionaryDto toDictDTO(Dictionary dict) {
        DictionaryDto dictionaryDto = new DictionaryDto();
        dictionaryDto.setId(dict.getId());
        dictionaryDto.setDictCode(dict.getDictCode());
        dictionaryDto.setDictName(dict.getDictName());
        dictionaryDto.setDictGroup(dict.getDictGroup());
        return dictionaryDto;
    }

    @Override
    public void removeById(String id) {
        dictionaryRepository.deleteById(id);
    }

    @Override
    public void removeByIds(Set<String> ids) {
        dictionaryRepository.deleteBatchIds(ids);
    }

    @Override
    public void removeByCode(String group, String code) {
        LambdaQueryWrapper<Dictionary> lambdaQueryWrapper = new LambdaQueryWrapper<Dictionary>()
                .eq(Dictionary::getDictGroup, group)
                .eq(Dictionary::getDictCode, code);
        dictionaryRepository.delete(lambdaQueryWrapper);
    }

    @Override
    public void removeByGroup(String group) {
        LambdaQueryWrapper<Dictionary> lambdaQueryWrapper = new LambdaQueryWrapper<Dictionary>()
                .eq(Dictionary::getDictGroup, group);
        dictionaryRepository.delete(lambdaQueryWrapper);
    }

    @Override
    public Optional<DictionaryDto> findById(String id) {
        return Optional.ofNullable(dictionaryRepository.selectById(id)).map(this::toDictDTO);
    }

    @Override
    public List<DictionaryDto> findByIds(Set<String> ids) {
        List<Dictionary> allById = dictionaryRepository.selectBatchIds(ids);
        return allById.stream().map(this::toDictDTO).toList();
    }

    @Override
    public List<DictionaryDto> findByGroup(String group) {
        LambdaQueryWrapper<Dictionary> lambdaQueryWrapper = new LambdaQueryWrapper<Dictionary>()
                .eq(Dictionary::getDictGroup, group);
        return dictionaryRepository.selectList(lambdaQueryWrapper).stream().map(this::toDictDTO).toList();
    }

    @Override
    public Optional<DictionaryDto> findByCode(String group, String code) {
        LambdaQueryWrapper<Dictionary> lambdaQueryWrapper = new LambdaQueryWrapper<Dictionary>()
                .eq(Dictionary::getDictGroup, group)
                .eq(Dictionary::getDictCode, code);
        return Optional.ofNullable(dictionaryRepository.selectOne(lambdaQueryWrapper)).map(this::toDictDTO);
    }

    @Override
    public IPage<DictionaryDto> findPage(PageQuery pageDto, DictionaryQueryDto queryDto) {
        LambdaQueryWrapper<Dictionary> lambdaQueryWrapper = new LambdaQueryWrapper<Dictionary>()
                .eq(Dictionary::getDictName, queryDto.getGroupName())
                .eq(Dictionary::getDictGroup, queryDto.getDictGroup());
        return dictionaryRepository.selectPage(new Page<>(pageDto.getPageNumber(), pageDto.getPageSize()), lambdaQueryWrapper)
                .convert(this::toDictDTO);
    }

    @Override
    public List<DictionaryDto> findAll() {
        return dictionaryRepository.selectList().stream().map(this::toDictDTO).toList();
    }

    @Override
    public boolean existByCode(String group, String code) {
        LambdaQueryWrapper<Dictionary> lambdaQueryWrapper = new LambdaQueryWrapper<Dictionary>()
                .eq(Dictionary::getDictGroup, group)
                .eq(Dictionary::getDictCode, code);
        return dictionaryRepository.exists(lambdaQueryWrapper);
    }

    @Override
    public boolean existByGroup(String group) {
        LambdaQueryWrapper<Dictionary> lambdaQueryWrapper = new LambdaQueryWrapper<Dictionary>()
                .eq(Dictionary::getDictGroup, group);
        return dictionaryRepository.exists(lambdaQueryWrapper);
    }

}
