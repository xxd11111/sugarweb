package com.sugarweb.oss.controller;

import com.sugarweb.framework.exception.ServerException;
import com.sugarweb.oss.application.FileService;
import com.sugarweb.framework.common.Result;
import com.sugarweb.oss.domain.FileInfo;
import com.sugarweb.oss.utils.WebDownloadUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
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
	public void download(HttpServletResponse response, String fileId) {
		Optional<FileInfo> optionalFileInfo = fileService.findOne(fileId);
		if (optionalFileInfo.isPresent()) {
			try(InputStream content = fileService.findContent(fileId)) {
				FileInfo fileInfo = optionalFileInfo.get();
				WebDownloadUtil.download(response, content, fileInfo.getFilename(), fileInfo.getContentType());
			} catch (IOException e) {
                throw new ServerException(e);
            }
        }
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
	public Result<Void> remove(Set<String> fileIds) {
		fileService.remove(fileIds);
		return Result.ok();
	}

	@PostMapping("remove")
	public Result<Void> remove(String fileId) {
		fileService.remove(fileId);
		return Result.ok();
	}

}
