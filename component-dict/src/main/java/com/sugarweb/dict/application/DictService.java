package com.sugarweb.dict.application;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.dict.application.dto.DictGroupDto;
import com.sugarweb.dict.application.dto.DictItemDto;
import com.sugarweb.dict.application.dto.DictQuery;
import com.sugarweb.dict.domain.DictGroup;
import com.sugarweb.dict.domain.DictItem;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.exception.ServerException;
import com.sugarweb.framework.orm.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典服务
 *
 * @author xxd
 * @version 1.0
 */
@Service
public class DictService {

    public void saveGroup(DictGroupDto groupDto) {
        DictGroup dictGroup = new DictGroup();
        dictGroup.setGroupId(groupDto.getGroupId());
        dictGroup.setGroupCode(groupDto.getGroupCode());
        dictGroup.setGroupName(groupDto.getGroupName());
        dictGroup.setGroupStatus(groupDto.getGroupStatus());
        Db.save(dictGroup);
    }

    public void updateGroup(DictGroupDto groupDto) {
        DictGroup dictGroup = Db.getById(groupDto.getGroupId(), DictGroup.class);
        if (dictGroup == null) {
            throw new ServerException("字典不存在");
        }
        dictGroup.setGroupCode(groupDto.getGroupCode());
    }

    public void saveItem(DictItemDto dictItemDto) {
        DictItem dictItem = new DictItem();
        dictItem.setDictItemId(dictItemDto.getItemId());
        dictItem.setDictItemCode(dictItemDto.getItemCode());
        dictItem.setDictItemName(dictItemDto.getItemName());
        dictItem.setDictGroupId(dictItemDto.getGroupId());
        dictItem.setDictItemStatus(dictItemDto.getItemStatus());
        Db.save(dictItem);
    }

    public void updateItem(DictItemDto dictItemDto) {
        DictItem dictItem = Db.getById(dictItemDto.getItemId(), DictItem.class);
        if (dictItem == null) {
            throw new ServerException("字典不存在");
        }
        dictItem.setDictItemCode(dictItemDto.getItemCode());
        dictItem.setDictItemName(dictItemDto.getItemName());
        dictItem.setDictItemStatus(dictItemDto.getItemStatus());
        Db.save(dictItem);
    }

    private DictItemDto toDictItemDto(DictItem dictItem) {
        DictItemDto dictItemDto = new DictItemDto();
        dictItemDto.setItemId(dictItem.getDictItemId());
        dictItemDto.setGroupId(dictItem.getDictGroupId());
        dictItemDto.setItemName(dictItem.getDictItemName());
        dictItemDto.setItemCode(dictItem.getDictItemCode());
        return dictItemDto;
    }

    public void removeItemById(String itemId) {
        Db.removeById(itemId, DictItem.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeGroupById(String groupId) {
        Db.removeById(groupId, DictGroup.class);
        Db.remove(new LambdaQueryWrapper<DictItem>().eq(DictItem::getDictGroupId, groupId));
    }


    public DictItemDto getItemById(String itemId) {
        DictItem dictItem = Db.getById(itemId, DictItem.class);
        return toDictItemDto(dictItem);
    }

    public List<DictItemDto> getItemsByGroupId(String groupId) {
        return Db.list(new LambdaQueryWrapper<DictItem>()
                        .eq(DictItem::getDictGroupId, groupId))
                .stream()
                .map(this::toDictItemDto)
                .toList();
    }

    public IPage<DictGroupDto> groupPage(PageQuery pageDto, DictQuery queryDto) {
        return Db.page(PageHelper.getPage(pageDto), new LambdaQueryWrapper<DictGroup>()
                .eq(DictGroup::getGroupName, queryDto.getGroupName())
                .eq(DictGroup::getGroupCode, queryDto.getGroupCode())
        ).convert(this::toDictGroupDto);
    }

    private DictGroupDto toDictGroupDto(DictGroup dictGroup) {
        DictGroupDto dictGroupDto = new DictGroupDto();
        dictGroupDto.setGroupId(dictGroup.getGroupId());
        dictGroupDto.setGroupName(dictGroup.getGroupName());
        dictGroupDto.setGroupCode(dictGroup.getGroupCode());
        dictGroupDto.setGroupStatus(dictGroup.getGroupStatus());
        return dictGroupDto;
    }


    public List<DictItemDto> findAll() {
        return Db.list(DictItem.class).stream().map(this::toDictItemDto).collect(Collectors.toList());
    }

    public boolean existsItemByCode(String groupCode, String itemCode, String excludeItemId) {
        DictGroup dictGroup = Db.getOne(new LambdaQueryWrapper<DictGroup>()
                .eq(DictGroup::getGroupCode, groupCode)
        );
        if (dictGroup == null) {
            return false;
        }
        LambdaQueryWrapper<DictItem> lambdaQueryWrapper = new LambdaQueryWrapper<DictItem>()
                .ne(StrUtil.isNotEmpty(excludeItemId), DictItem::getDictItemId, excludeItemId)
                .eq(DictItem::getDictItemCode, itemCode)
                .eq(DictItem::getDictGroupId, dictGroup.getGroupId());
        return Db.count(lambdaQueryWrapper) > 0;
    }

    public boolean existsGroupByCode(String groupCode, String excludeGroupId) {
        LambdaQueryWrapper<DictGroup> lambdaQueryWrapper = new LambdaQueryWrapper<DictGroup>()
                .ne(StrUtil.isNotEmpty(excludeGroupId), DictGroup::getGroupId, excludeGroupId)
                .eq(DictGroup::getGroupCode, groupCode);
        return Db.count(lambdaQueryWrapper) > 0;
    }

    public Map<String, String> getDictItemMapByGroupCode(String groupCode) {
        return Db.list(new LambdaQueryWrapper<DictItem>()
                        .eq(DictItem::getDictGroupId, groupCode))
                .stream()
                .collect(Collectors.toMap(DictItem::getDictItemCode, DictItem::getDictItemName));
    }

}
