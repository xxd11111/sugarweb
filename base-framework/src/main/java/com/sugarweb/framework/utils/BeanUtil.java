package com.sugarweb.framework.utils;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

public class BeanUtil implements BeanFactoryPostProcessor, ApplicationContextAware {

	private static ConfigurableListableBeanFactory beanFactory;

	private static ApplicationContext applicationContext;

	public BeanUtil() {
	}

	
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		BeanUtil.beanFactory = beanFactory;
	}

	
	public void setApplicationContext(ApplicationContext applicationContext) {
		BeanUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType){
		return applicationContext.getBeansWithAnnotation(annotationType);
	}

	public static ListableBeanFactory getBeanFactory() {
		return (ListableBeanFactory) (null == beanFactory ? applicationContext : beanFactory);
	}

	public static <T> T getBean(Class<T> clazz) {
		return getBeanFactory().getBean(clazz);
	}

	public static Object getBean(String beanName) {
		return getBeanFactory().getBean(beanName);
	}

	public static <T> T getBean(String name, Class<T> clazz) {
		return getBeanFactory().getBean(name, clazz);
	}

	public static <T> Map<String, T> getBeansOfType(Class<T> type) {
		return getBeanFactory().getBeansOfType(type);
	}

	public static String[] getBeanNamesForType(Class<?> type) {
		return getBeanFactory().getBeanNamesForType(type);
	}

	public static String getProperty(String key) {
		return null == applicationContext ? null : applicationContext.getEnvironment().getProperty(key);
	}

	public static String getApplicationName() {
		return getProperty("spring.application.name");
	}

	public static String[] getActiveProfiles() {
		return null == applicationContext ? null : applicationContext.getEnvironment().getActiveProfiles();
	}

	public static void publishEvent(ApplicationEvent event) {
		if (null != applicationContext) {
			applicationContext.publishEvent(event);
		}

	}

	public static void publishEvent(Object event) {
		if (null != applicationContext) {
			applicationContext.publishEvent(event);
		}

	}

}