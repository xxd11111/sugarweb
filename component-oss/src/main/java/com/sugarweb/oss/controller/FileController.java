package com.sugarweb.oss.controller;

import com.sugarweb.oss.application.FileQueryDto;
import com.sugarweb.oss.application.FileService;
import com.sugarweb.framework.common.Result;
import com.sugarweb.oss.domain.FileInfo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

/**
 * 文件控制器
 *
 * @author xxd
 * @version 1.0
 */
@RestController
@RequestMapping("file")
@RequiredArgsConstructor
public class FileController {

	private final FileService fileService;

	@GetMapping("download")
	public void download(HttpServletResponse response, FileQueryDto fileQueryDto) {
		fileService.download(response, fileQueryDto.getFileGroup(), fileQueryDto.getFileId());
	}

	@PostMapping("upload")
	public Result<FileInfo> upload(MultipartFile multipartFile, String fileGroup) {
        try {
            return Result.data(fileService.upload(fileGroup, multipartFile.getInputStream(), multipartFile.getContentType(), multipartFile.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	@PostMapping("batchRemove")
	@CreatedDate
	public Result<Void> remove(String fileGroup, Set<String> fileIds) {
		fileService.remove(fileGroup, fileIds);
		return Result.ok();
	}

	@PostMapping("remove")
	public Result<Void> remove(String fileGroup, String fileId) {
		fileService.remove(fileGroup, fileId);
		return Result.ok();
	}

}
