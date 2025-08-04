package com.sugarweb.oss.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.oss.service.dto.FileConvert;
import com.sugarweb.oss.service.dto.FileDetailDto;
import com.sugarweb.oss.entity.FileInfo;
import com.sugarweb.oss.entity.FileLink;

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

    public List<FileDetailDto> getAllLinkFiles(String bizId) {
        List<FileLink> list = Db.list(new LambdaQueryWrapper<FileLink>()
                .eq(FileLink::getBizId, bizId));
        List<String> fileIds = list.stream().map(FileLink::getFileId).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(fileIds)) {
            return Db.listByIds(fileIds, FileInfo.class).stream().map(FileConvert::toDto).toList();
        } else {
            return new ArrayList<>();
        }
    }

    public List<FileDetailDto> getGroupLinkFiles(String bizId, String groupCode) {
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

    public List<FileLink> listFileLinkByGroup(String bizId, String groupCode) {
        List<FileLink> fileLink = Db.list(new LambdaQueryWrapper<FileLink>()
                .eq(FileLink::getGroupCode, groupCode)
                .eq(FileLink::getBizId, bizId));
        List<String> fileIds = fileLink.stream().map(FileLink::getFileId).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(fileIds)) {
            List<FileInfo> fileInfos = Db.listByIds(fileIds, FileInfo.class);
            for (FileLink link : fileLink) {
                link.setFileInfo(fileInfos.stream().filter(a -> StrUtil.equals(a.getFileId(), link.getFileId())).findFirst().orElse(null));
            }
            return fileLink;
        } else {
            return new ArrayList<>();
        }
    }

    public FileLink getFileLinkByGroup(String bizId, String groupCode) {
        List<FileLink> fileLinkList = Db.list(new LambdaQueryWrapper<FileLink>()
                .eq(FileLink::getGroupCode, groupCode)
                .eq(FileLink::getBizId, bizId));
        int size = CollUtil.size(fileLinkList);
        if (size > 1) {
            throw new IllegalArgumentException("bizId:" + bizId + " groupCode:" + groupCode + " 存在多个文件, 请联系系统管理员");
        }
        if (size == 0) {
            return null;
        }
        FileLink fileLink = fileLinkList.getFirst();
        FileInfo fileInfo = Db.getById(fileLink.getFileId(), FileInfo.class);
        fileLink.setFileInfo(fileInfo);
        return fileLink;
    }

    public FileInfo getFileInfoByGroup(String bizId, String groupCode) {
        List<FileLink> fileLinkList = Db.list(new LambdaQueryWrapper<FileLink>()
                .eq(FileLink::getGroupCode, groupCode)
                .eq(FileLink::getBizId, bizId));
        int size = CollUtil.size(fileLinkList);
        if (size > 1) {
            throw new IllegalArgumentException("bizId:" + bizId + " groupCode:" + groupCode + " 存在多个文件, 请联系系统管理员");
        }
        if (size == 0) {
            return null;
        }
        FileLink fileLink = fileLinkList.getFirst();
        return Db.getById(fileLink.getFileId(), FileInfo.class);
    }

}
