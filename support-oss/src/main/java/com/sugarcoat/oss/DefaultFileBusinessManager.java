package com.sugarcoat.oss;

import com.sugarcoat.oss.api.FileBusinessManager;
import com.sugarcoat.oss.api.FileGroup;
import com.sugarcoat.oss.api.FileInfo;

import java.util.List;

/**
 * 文件业务绑定客户端
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/31
 */
public class DefaultFileBusinessManager implements FileBusinessManager {
    @Override
    public void associateFile(String associationId, FileGroup fileGroup, List<String> fileIds) {

    }

    @Override
    public void separateFile(String associationId, FileGroup fileGroup) {

    }

    @Override
    public void separateAllFile(String associationId) {

    }

    @Override
    public List<FileInfo> listAssociationFile(String associationId, FileGroup fileGroup) {
        return null;
    }

    @Override
    public List<FileInfo> listAllAssociationFile(String associationId) {
        return null;
    }
}
