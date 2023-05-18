package com.sugarcoat.support.server;

import com.sugarcoat.protocol.PageData;
import com.sugarcoat.protocol.PageParam;
import com.sugarcoat.protocol.Result;
import com.sugarcoat.support.server.api.ServerApiQueryVO;
import com.sugarcoat.support.server.api.ServerApiService;
import com.sugarcoat.support.server.api.ServerApiDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * serverApi接口
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/8
 */
@RestController
@RequestMapping("serverApi")
@RequiredArgsConstructor
public class ServerApiController {
    private final ServerApiService serverApiService;

    @PostMapping("save")
    public Result<?> save(ServerApiDTO serverApiDTO) {
        serverApiService.save(serverApiDTO);
        return Result.ok();
    }

    @DeleteMapping("remove/{id}")
    public Result<?> remove(@PathVariable String id) {
        serverApiService.remove(id);
        return Result.ok();
    }

    @GetMapping("findOne/{id}")
    public Result<ServerApiDTO> findOne(@PathVariable String id) {
        return Result.data(serverApiService.findOne(id));
    }

    @GetMapping("findPage")
    public Result<PageData<ServerApiDTO>> findPage(PageParam pageParam, ServerApiQueryVO queryVO) {
        return Result.data(serverApiService.findPage(pageParam, queryVO));
    }
}
