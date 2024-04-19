package com.sugarweb.oss.application;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sugarweb.oss.domain.*;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 文件业务绑定客户端
 *
 * @author xxd
 * @version 1.0
 */
@RequiredArgsConstructor
public class FileLinkServiceImpl implements FileLinkService {

    private final FileLinkInfoRepository fileLinkInfoRepository;

    @Override
    public void linkFiles(String bizId, String fileGroup, Collection<String> fileIds) {
        List<FileLinkInfo> sgcBizFiles = new ArrayList<>();
        for (String fileId : fileIds) {
            FileLinkInfo sgcBizFile = new FileLinkInfo();
            sgcBizFile.setFileId(fileId);
            sgcBizFile.setBizId(bizId);
            sgcBizFile.setFileGroup(fileGroup);
            sgcBizFiles.add(sgcBizFile);
        }
        fileLinkInfoRepository.saveAll(sgcBizFiles);
    }

    @Override
    public void linkFile(String bizId, String fileGroup, String fileId) {
        FileLinkInfo sgcBizFile = new FileLinkInfo();
        sgcBizFile.setFileId(fileId);
        sgcBizFile.setBizId(bizId);
        sgcBizFile.setFileGroup(fileGroup);
        fileLinkInfoRepository.save(sgcBizFile);
    }

    @Override
    public void breakFiles(String bizId, String fileGroup) {
        fileLinkInfoRepository.delete(new LambdaQueryWrapper<FileLinkInfo>()
                .eq(FileLinkInfo::getBizId, bizId)
                .eq(FileLinkInfo::getFileGroup, fileGroup)
        );
    }

    @Override
    public void breakFiles(String bizId) {
        fileLinkInfoRepository.delete(new LambdaQueryWrapper<FileLinkInfo>()
                .eq(FileLinkInfo::getBizId, bizId)
        );
    }

    @Override
    public void breakByFileId(String fileId) {
        fileLinkInfoRepository.delete(new LambdaQueryWrapper<FileLinkInfo>()
                .eq(FileLinkInfo::getFileId, fileId)
        );
    }

    @Override
    public void breakByFileIds(Collection<String> fileIds) {
        fileLinkInfoRepository.delete(new LambdaQueryWrapper<FileLinkInfo>()
                .in(FileLinkInfo::getFileId, fileIds)
        );
    }

    @Override
    public List<FileLinkInfo> findAll(String bizId, String fileGroup) {
        return fileLinkInfoRepository.selectList(new LambdaQueryWrapper<FileLinkInfo>()
                .eq(FileLinkInfo::getBizId, bizId)
                .eq(FileLinkInfo::getFileGroup, fileGroup)
        );
    }

    @Override
    public Optional<FileLinkInfo> findFirstOne(String bizId, String fileGroup) {
        List<FileLinkInfo> all = findAll(bizId, fileGroup);
        return all.stream().findFirst();
    }

}
