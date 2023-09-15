package com.sugarcoat.support.oss.controller;

import com.sugarcoat.api.oss.FileInfo;
import com.sugarcoat.support.oss.application.FileQueryDto;
import com.sugarcoat.support.oss.application.FileService;
import com.sugarcoat.api.common.Result;
import com.sugarcoat.support.oss.domain.SgcBizFile;
import com.sugarcoat.support.oss.domain.SgcFileInfo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

/**
 * 文件控制器
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/5
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
	public Result<SgcFileInfo> upload(MultipartFile multipartFile, String fileGroup) {
		return Result.data(fileService.upload(fileGroup, multipartFile));
	}

	@PostMapping("batchRemove")
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
