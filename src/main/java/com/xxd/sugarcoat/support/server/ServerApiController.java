package com.xxd.sugarcoat.support.server;

import com.xxd.sugarcoat.support.server.api.ServerApiDTO;
import com.xxd.sugarcoat.support.server.api.ServerApiQueryVO;
import com.xxd.sugarcoat.support.server.api.ServerApiService;
import com.xxd.sugarcoat.support.servelt.protocol.PageData;
import com.xxd.sugarcoat.support.servelt.protocol.PageParam;
import com.xxd.sugarcoat.support.servelt.protocol.Result;
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
