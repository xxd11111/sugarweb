package com.sugarweb.oss.application;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.exception.FrameworkException;
import com.sugarweb.framework.exception.ServerException;
import com.sugarweb.framework.orm.PageHelper;
import com.sugarweb.oss.application.dto.FileQuery;
import com.sugarweb.oss.po.FileInfo;
import com.sugarweb.oss.po.FileLink;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

/**
 * 文件存储服务
 *
 * @author xxd
 * @version 1.0
 */
public class FileService {

    private final String bucketName;

    private final AmazonS3 client;

    public FileService(String bucketName, AmazonS3 client) {
        this.bucketName = bucketName;
        this.client = client;
    }

    /**
     * 文件上传
     */
    @Transactional(rollbackFor = Exception.class)
    public FileDto upload(String groupCode, InputStream inputStream, String contentType, String filename) {
        String key = generateFileKey(groupCode, filename);
        long size;
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, metadata);
            putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
            PutObjectResult putObjectResult = client.putObject(putObjectRequest);
            size = putObjectResult.getMetadata().getContentLength();
        } catch (Exception e) {
            throw new ServerException("上传文件失败，请检查配置信息:[" + e.getMessage() + "]");
        }

        FileInfo fileInfo = new FileInfo();
        fileInfo.setGroupCode(groupCode);
        fileInfo.setFilename(filename);
        fileInfo.setFileKey(key);
        fileInfo.setFileSize(size);
        fileInfo.setFileType(getFileType(filename));
        Db.save(fileInfo);

        return FileConvert.toDto(fileInfo);
    }

    private String generateFileKey(String groupCode, String filename) {
        return "/" + groupCode + "/" + UUID.randomUUID() + "/" + filename;
    }

    /**
     * 根据文件名获取文件类型
     *
     * @param filename 文件名称
     * @return 文件类型
     */
    private String getFileType(String filename) {
        //只判断文件名，不识别文件magic值
        if (filename == null) {
            return null;
        }
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex == -1) {
            return null;
        }
        return filename.substring(dotIndex, filename.length() - 1);
    }

    public FileInfo getFileInfo(String fileId) {
        return Db.getById(fileId, FileInfo.class);
    }

    public void createBucket() {
        try {
            if (client.doesBucketExistV2(bucketName)) {
                return;
            }
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
            createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
            client.createBucket(createBucketRequest);
        } catch (Exception e) {
            throw new FrameworkException("创建Bucket失败, message:{}", e.getMessage());
        }
    }

    public InputStream getContentByKey(String fileKey) {
        S3Object object = client.getObject(bucketName, fileKey);
        return object.getObjectContent();
    }

    @Transactional(rollbackFor = Exception.class)
    public void remove(String fileId) {
        FileInfo fileInfo = Db.getById(fileId, FileInfo.class);
        if (fileInfo == null) {
            return;
        }
        Db.removeById(fileId, FileInfo.class);
        Db.remove(new LambdaQueryWrapper<FileLink>().eq(FileLink::getFileId, fileId));
        try {
            client.deleteObject(bucketName, fileInfo.getFileKey());
        } catch (Exception e) {
            throw new ServerException("删除文件失败，请检查配置信息:[" + e.getMessage() + "]");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchRemove(Set<String> fileIds) {
        for (String fileId : fileIds) {
            remove(fileId);
        }
    }

    public IPage<FileDto> page(PageQuery pageQuery, FileQuery query) {
        return Db.page(PageHelper.getPage(pageQuery), new LambdaQueryWrapper<FileInfo>()
                .like(StrUtil.isNotEmpty(query.getFilename()), FileInfo::getFilename, query.getFilename())
                .in(CollUtil.isNotEmpty(query.getFileGroups()), FileInfo::getGroupCode, query.getFileGroups())
        ).convert(FileConvert::toDto);
    }
}
