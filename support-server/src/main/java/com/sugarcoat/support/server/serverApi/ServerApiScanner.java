package com.sugarcoat.support.server.serverApi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 接口扫描者
 *
 * @author xxd
 * @date 2022-11-17
 */
@Slf4j
public class ServerApiScanner {

	private RequestMappingHandlerMapping mappingHandlerMapping;

	private Set<ServerApi> serverApis;

	public Set<ServerApi> scanApi() {
		Set<ServerApi> urlList = new HashSet<>();
		Map<RequestMappingInfo, HandlerMethod> map = mappingHandlerMapping.getHandlerMethods();
		Set<RequestMappingInfo> requestMappingInfos = map.keySet();
		for (RequestMappingInfo info : requestMappingInfos) {
			ServerApi serverApi = new ServerApi();
			PatternsRequestCondition patternsRequestCondition = info.getPatternsCondition();
			Set<String> patterns = patternsRequestCondition.getPatterns();
			log.info("url:{}", patterns);
			for (String url : patterns) {
				serverApi.setUrl(url);
			}
			RequestMethodsRequestCondition methodsRequestCondition = info.getMethodsCondition();
			Set<RequestMethod> methods = methodsRequestCondition.getMethods();
			log.info("method:{}", methods);
			for (RequestMethod method : methods) {
				String type = method.name();
				serverApi.setMethodType(type);
			}
			serverApi.setRemark(info.toString());
			urlList.add(serverApi);
		}
		log.info(String.valueOf(urlList));
		return urlList;
	}

	public Set<ServerApi> getApi() {
		return serverApis;
	}

}
