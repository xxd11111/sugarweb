package com.xxd.server.domain;

import com.xxd.server.ServletUtil;
import com.xxd.exception.FrameworkException;
import com.xxd.server.ApiInfo;
import com.xxd.server.ApiManager;
import com.xxd.server.auto.ApiRegister;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * SgcApiManager
 *
 * @author xxd
 * @version 1.0
 */
@RequiredArgsConstructor
@Slf4j
public class SgcApiManager implements ApiManager {

    private final SgcApiRepository sgcApiRepository;

    private final Map<String, SgcApi> apiInfos;

    public SgcApiManager(SgcApiRepository sgcApiRepository, ApiRegister apiRegister) {
        this.sgcApiRepository = sgcApiRepository;
        Map<String, SgcApi> scan = apiRegister.scan();
        apiInfos = Collections.unmodifiableMap(scan);
    }

    @Override
    public Optional<ApiInfo> findApiByUrl(String url) {
        for (SgcApi sgcApi : apiInfos.values()) {
            if (match(sgcApi.getUrl(), url)) {
                return Optional.of(sgcApi);
            }
        }
        return Optional.empty();
    }

    //todo copy mvc识别方法
    private static boolean match(String apiUrl, String requestUrl) {
        return false;
    }

    @Override
    public Optional<ApiInfo> findApiById(String id) {
        return Optional.of(apiInfos.get(id));
    }

    @Override
    public Optional<ApiInfo> findCurrentApi() {
        HttpServletRequest request = ServletUtil.getRequest();
        if (request == null) {
            return Optional.empty();
        }
        return findApiByUrl(request.getRequestURI());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ApiInfo apiInfo) {
        if (apiInfo instanceof SgcApi sgcApi) {
            SgcApi oldOne = null;
            try {
                sgcApiRepository.save(sgcApi);
                oldOne = apiInfos.get(sgcApi.getOperationId());
                apiInfos.put(sgcApi.getOperationId(), sgcApi);
            } catch (Throwable ex) {
                if (oldOne != null) {
                    apiInfos.put(sgcApi.getOperationId(), oldOne);
                } else {
                    apiInfos.remove(sgcApi.getOperationId());
                }
                log.error("ApiInfo保存失败，apiInfo：{}", apiInfo, ex);
                throw ex;
            }
        } else {
            throw new FrameworkException("ApiInfo类型异常，无法转换");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(String id) {
        SgcApi oldOne = null;
        try {
            sgcApiRepository.deleteById(id);
            oldOne = apiInfos.get(id);
            apiInfos.remove(id);
        } catch (Throwable ex) {
            if (oldOne != null) {
                apiInfos.put(id, oldOne);
            }
            log.error("ApiInfo删除失败，id：{}", id, ex);
            throw ex;
        }
    }

    @Override
    public Collection<ApiInfo> findAll() {
        return new ArrayList<>(apiInfos.values());
    }

}
