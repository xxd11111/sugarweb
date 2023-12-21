package com.sugarcoat.uims.security.session;

import cn.hutool.core.util.StrUtil;
import com.sugarcoat.protocol.common.HttpCode;
import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.protocol.common.PageDto;
import com.sugarcoat.protocol.exception.SecurityException;
import com.sugarcoat.uims.security.SessionInfo;
import com.sugarcoat.uims.security.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 会话管理实现类
 *
 * @author xxd
 * @since 2023/7/2 20:51
 */
@Component
@RequiredArgsConstructor
public class SessionManagerImpl implements SessionManager {

    private final RedissonClient redissonClient;

    private static final String KEY_PREFIX = "session";

    @Override
    public void create(SessionInfo sessionInfo) {
        String key = sessionKey(sessionInfo.getUserId(), sessionInfo.getSessionId());
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(sessionInfo);
    }

    @Override
    public void update(SessionInfo sessionInfo) {
        String key = sessionKey(sessionInfo.getUserId(), sessionInfo.getSessionId());
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(sessionInfo);
    }

    @Override
    public void delete(String userId, String sessionId) {
        String key = userKeyPattern(sessionId);
        redissonClient.getBucket(key);
    }

    @Override
    public void deleteAll(String userId) {
        String key = userKeyPattern(userId);
        redissonClient.getKeys().deleteByPattern(key);
    }

    @Override
    public SessionInfo authenticate(TokenInfo tokenInfo) {
        String key = userKeyPattern(tokenInfo.getSessionId());
        RBucket<SessionInfo> bucket = redissonClient.getBucket(key);
        SessionInfo sessionInfo = bucket.get();
        if (sessionInfo == null) {
            throw new SecurityException("");
        }
        boolean flag = StrUtil.equals(tokenInfo.getIp(), sessionInfo.getIp())
                && StrUtil.equals(tokenInfo.getMac(), sessionInfo.getMac())
                && StrUtil.equals(tokenInfo.getUserId(), sessionInfo.getUserId());
        if (!flag) {
            throw new SecurityException(HttpCode.UNAUTHORIZED);
        }
        return sessionInfo;
    }

    @Override
    public PageData<SessionInfo> findAll(PageDto pageDto) {
        String allKeyPattern = allKeyPattern();
        RKeys keys = redissonClient.getKeys();
        Iterable<String> keysByPattern = keys.getKeysByPattern(allKeyPattern);
        List<String> keyResult = new ArrayList<>(pageDto.getPageSize());
        int i = 1;
        for (String key : keysByPattern) {
            if (pageDto.startIndex() >= i && pageDto.endIndex() <= i) {
                keyResult.add(key);
            } else if (pageDto.endIndex() > i) {
                break;
            }
            i++;
        }
        List<SessionInfo> records = new ArrayList<>();
        for (String key : keyResult) {
            RBucket<SessionInfo> bucket = redissonClient.getBucket(key);
            SessionInfo sessionInfo = bucket.get();
            records.add(sessionInfo);
        }
        PageData<SessionInfo> page = new PageData<>();
        page.setSize(pageDto.getPageSize());
        page.setPage(pageDto.getPageNumber());
        page.setContent(records);
        return page;
    }

    @Override
    public SessionInfo findOne(String userId, String sessionId) {
        String key = userKeyPattern(sessionId);
        RBucket<SessionInfo> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    @Override
    public List<SessionInfo> findAll(String userId) {
        String userKeyPattern = userKeyPattern(userId);
        RKeys keys = redissonClient.getKeys();
        Iterable<String> keysByPattern = keys.getKeysByPattern(userKeyPattern);
        List<SessionInfo> records = new ArrayList<>();
        for (String key : keysByPattern) {
            RBucket<SessionInfo> bucket = redissonClient.getBucket(key);
            SessionInfo sessionInfo = bucket.get();
            records.add(sessionInfo);
        }
        return records;
    }

    private String sessionKey(String userId, String sessionId) {
        return KEY_PREFIX + ":" + userId + ":" + sessionId;
    }

    private String userKeyPattern(String userId) {
        return KEY_PREFIX + ":" + userId + ":*";
    }

    private String allKeyPattern() {
        return KEY_PREFIX + ":*";
    }
}
