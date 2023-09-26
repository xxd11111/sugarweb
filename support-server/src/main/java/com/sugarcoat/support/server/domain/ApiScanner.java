package com.sugarcoat.support.server.domain;

import lombok.extern.slf4j.Slf4j;
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
 * @since 2022-11-17
 */
@Slf4j
public class ApiScanner {

	private RequestMappingHandlerMapping mappingHandlerMapping;

	private Set<SgcApi> sgcApis;

	public Set<SgcApi> scan() {
		Set<SgcApi> urlList = new HashSet<>();
		Map<RequestMappingInfo, HandlerMethod> map = mappingHandlerMapping.getHandlerMethods();
		Set<RequestMappingInfo> requestMappingInfos = map.keySet();
		for (RequestMappingInfo info : requestMappingInfos) {
			SgcApi sgcApi = new SgcApi();
			PatternsRequestCondition patternsRequestCondition = info.getPatternsCondition();
			Set<String> patterns = patternsRequestCondition.getPatterns();
			log.info("url:{}", patterns);
			for (String url : patterns) {
				sgcApi.setUrl(url);
			}
			RequestMethodsRequestCondition methodsRequestCondition = info.getMethodsCondition();
			Set<RequestMethod> methods = methodsRequestCondition.getMethods();
			log.info("method:{}", methods);
			for (RequestMethod method : methods) {
				String type = method.name();
				sgcApi.setMethodType(type);
			}
			sgcApi.setRemark(info.toString());
			urlList.add(sgcApi);
		}
		log.info(String.valueOf(urlList));
		return urlList;
	}

	public Set<SgcApi> getApi() {
		return sgcApis;
	}

}
