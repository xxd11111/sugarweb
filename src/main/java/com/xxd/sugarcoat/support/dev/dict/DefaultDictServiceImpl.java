package com.xxd.sugarcoat.support.dev.dict;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.xxd.sugarcoat.support.dev.dict.api.*;
import com.xxd.sugarcoat.support.dev.dict.model.*;
import com.xxd.sugarcoat.support.dev.exception.ValidateException;
import com.xxd.sugarcoat.support.prod.common.PageParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.ListJoin;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author xxd
 * @description TODO
 * @date 2023/4/22 10:07
 */
@Service
@RequiredArgsConstructor
public class DefaultDictServiceImpl implements DictService {
    private final DictRepository dictRepository;
    private final DictItemRepository dictItemRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void save(DictDTO dictDTO) {
        DictGroup dictGroup = DictFactory.create(dictDTO);
        dictRepository.save(dictGroup);
    }

    @Override
    public void save(DictItemDTO dictItemDTO) {
        DictItem dictItem = new DictItem();
        DictGroup dictGroup = dictRepository.findById(dictItemDTO.getGroupId()).orElseThrow(() -> new ValidateException("dict not find"));
        dictItem.setId(dictItem.getId());
        dictItem.setCode(dictItem.getCode());
        dictItem.setName(dictItem.getName());
        dictItem.setDictGroup(dictGroup);
        dictItemRepository.save(dictItem);
    }

    @Override
    public void removeDict(Set<String> groupIds) {
        dictRepository.deleteAllById(groupIds);
    }

    @Override
    public void removeDictItem(Set<String> dictItemIds) {
        dictItemRepository.deleteAllById(dictItemIds);
    }

    @Override
    public DictItemDTO findByItemId(String dictItemId) {
        DictItem dictItem = dictItemRepository.findById(dictItemId)
                .orElseThrow(() -> new ValidateException("dictItem not find"));
        DictItemDTO dictItemDTO = new DictItemDTO();
        dictItemDTO.setId(dictItem.getId());
        dictItemDTO.setCode(dictItem.getCode());
        dictItemDTO.setName(dictItem.getName());
        return dictItemDTO;
    }

    @Override
    public DictDTO findByGroupId(String groupId) {
        DictGroup dictGroup = dictRepository.findById(groupId)
                .orElseThrow(() -> new ValidateException("dict not find"));
        List<DictItem> dictItemList = dictGroup.getDictItems();
        List<DictItemDTO> dictItemDTOList = new ArrayList<>();
        for (DictItem dictItem : dictItemList) {
            DictItemDTO dictItemDTO = new DictItemDTO();
            dictItemDTO.setId(dictItem.getId());
            dictItemDTO.setCode(dictItem.getCode());
            dictItemDTO.setName(dictItem.getName());
            dictItemDTOList.add(dictItemDTO);
        }
        DictDTO dictDTO = new DictDTO();
        dictDTO.setId(dictGroup.getId());
        dictDTO.setGroupCode(dictGroup.getGroupCode());
        dictDTO.setGroupName(dictGroup.getGroupName());
        dictDTO.setDictItemDTOList(dictItemDTOList);
        return dictDTO;
    }

    @Override
    public DictDTO findByGroupCode(String groupCode) {
        DictGroup dictGroup = dictRepository.findById(groupCode)
                .orElseThrow(() -> new ValidateException("dict not find"));
        List<DictItem> dictItemList = dictGroup.getDictItems();
        List<DictItemDTO> dictItemDTOList = new ArrayList<>();
        for (DictItem dictItem : dictItemList) {
            DictItemDTO dictItemDTO = new DictItemDTO();
            dictItemDTO.setId(dictItem.getId());
            dictItemDTO.setCode(dictItem.getCode());
            dictItemDTO.setName(dictItem.getName());
            dictItemDTOList.add(dictItemDTO);
        }
        DictDTO dictDTO = new DictDTO();
        dictDTO.setId(dictGroup.getId());
        dictDTO.setGroupCode(dictGroup.getGroupCode());
        dictDTO.setGroupName(dictGroup.getGroupName());
        dictDTO.setDictItemDTOList(dictItemDTOList);
        return dictDTO;
    }


    @Override
    public Object pageDict(PageParam pageParam, DictQueryVO queryVO) {
        QDictGroup dictGroup = QDictGroup.dictGroup;
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.query("sql", new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                return null;
            }
        });
        PageRequest pageRequest = PageRequest
                .of(pageParam.getPageNum(), pageParam.getPageSize())
                .withSort(Sort.Direction.DESC, dictGroup.createDate.getMetadata().getName());
        dictRepository.findAll(
                , pageRequest);
        return null;
    }

    public void test() {
        PageRequest pageRequest = PageRequest.of(1, 20);
        QDictGroup dictGroup = QDictGroup.dictGroup;
        QDictItem dictItem = QDictItem.dictItem;
        List<DictGroup> dictGroupList = jpaQueryFactory.selectFrom(dictGroup)
                .where(dictGroup.groupCode.eq("queryVO.getGroupCode()")
                        .or(dictGroup.groupName.like("queryVO.getGroupName())"))
                )
                .leftJoin(dictItem).on(dictItem.dictGroupId.eq(dictGroup.id))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();
    }

}
