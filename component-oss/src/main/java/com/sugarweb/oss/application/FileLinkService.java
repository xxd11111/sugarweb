package com.sugarweb.oss.application;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.oss.domain.po.FileInfo;
import com.sugarweb.oss.domain.po.FileLink;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 文件关联服务
 *
 * @author xxd
 * @version 1.0
 */
public class FileLinkService {

    public void replaceLinkFiles(String bizId, Set<String> fileIds) {
        List<FileInfo> fileInfos = Db.listByIds(fileIds, FileInfo.class);
        List<FileLink> fileLinks = fileInfos.stream()
                .map(a -> {
                    FileLink fileLink = new FileLink();
                    fileLink.setBizId(bizId);
                    fileLink.setFileId(a.getFileId());
                    fileLink.setGroupCode(a.getGroupCode());
                    return fileLink;
                })
                .toList();
        Db.remove(new LambdaQueryWrapper<FileLink>().eq(FileLink::getBizId, bizId));
        Db.saveBatch(fileLinks);
    }

    public void replaceLinkFiles(String bizId, String groupCode, Set<String> fileIds) {
        List<FileInfo> fileInfos = Db.listByIds(fileIds, FileInfo.class);
        List<FileLink> fileLinks = fileInfos.stream()
                .filter(a -> StrUtil.equals(a.getGroupCode(), groupCode))
                .map(a -> {
                    FileLink fileLink = new FileLink();
                    fileLink.setBizId(bizId);
                    fileLink.setFileId(a.getFileId());
                    fileLink.setGroupCode(a.getGroupCode());
                    return fileLink;
                })
                .toList();
        Db.remove(new LambdaQueryWrapper<FileLink>()
                .eq(FileLink::getGroupCode, groupCode)
                .eq(FileLink::getBizId, bizId));
        Db.saveBatch(fileLinks);
    }

    public void removeAllLinkFiles(String bizId) {
        Db.remove(new LambdaQueryWrapper<FileLink>().eq(FileLink::getBizId, bizId));
    }

    public void removeGroupLinkFiles(String bizId, String groupCode) {
        Db.remove(new LambdaQueryWrapper<FileLink>()
                .eq(FileLink::getGroupCode, groupCode)
                .eq(FileLink::getBizId, bizId));
    }

    public List<FileDto> getAllLinkFiles(String bizId) {
        List<FileLink> list = Db.list(new LambdaQueryWrapper<FileLink>()
                .eq(FileLink::getBizId, bizId));
        List<String> fileIds = list.stream().map(FileLink::getFileId).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(fileIds)) {
            return Db.listByIds(fileIds, FileInfo.class).stream().map(FileConvert::toDto).toList();
        } else {
            return new ArrayList<>();
        }
    }

    public List<FileDto> getGroupLinkFiles(String bizId, String groupCode) {
        List<FileLink> list = Db.list(new LambdaQueryWrapper<FileLink>()
                .eq(FileLink::getGroupCode, groupCode)
                .eq(FileLink::getBizId, bizId));
        List<String> fileIds = list.stream().map(FileLink::getFileId).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(fileIds)) {
            return Db.listByIds(fileIds, FileInfo.class).stream().map(FileConvert::toDto).toList();
        } else {
            return new ArrayList<>();
        }
    }

}
