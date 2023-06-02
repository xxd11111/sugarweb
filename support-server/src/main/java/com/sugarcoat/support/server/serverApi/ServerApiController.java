package com.sugarcoat.support.server.serverApi;

import com.sugarcoat.protocol.PageData;
import com.sugarcoat.protocol.PageParameter;
import com.sugarcoat.protocol.Result;
import com.sugarcoat.support.server.serverApi.ServerApiQueryVO;
import com.sugarcoat.support.server.serverApi.ServerApiService;
import com.sugarcoat.support.server.serverApi.ServerApiDTO;
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

    @GetMapping("findOne/{id}")
    public Result<ServerApiDTO> findOne(@PathVariable String id) {
        return Result.data(serverApiService.findOne(id));
    }

    @GetMapping("findPage")
    public Result<PageData<ServerApiDTO>> findPage(PageParameter pageParameter, ServerApiQueryVO queryVO) {
        return Result.data(serverApiService.findPage(pageParameter, queryVO));
    }
}
