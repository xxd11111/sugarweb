package com.sugarcoat.oss;

import java.util.List;

/**
 * 文件业务绑定服务（对内）
 *
 * @author xxd
 * @date 2023/6/2
 */
public interface FileBusinessClient {

    void associateFile(String associationId, FileGroup fileGroup, List<String> fileIds);

    void separateFile(String associationId, FileGroup fileGroup);

    void separateAllFile(String associationId);

    List<FileInfo> listAssociationFile(String associationId, FileGroup fileGroup);

    List<FileInfo> listAllAssociationFile(String associationId);

}
