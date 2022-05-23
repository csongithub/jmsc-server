/**
 * 
 */
package com.jmsc.app.config.jmsc;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Chandan
 *
 */
public class ServiceLocator {
	
	private ConfigurableApplicationContext ctx = null;
	
	private final static ServiceLocator INSTANCE = new ServiceLocator();
	
	private ServiceLocator() {}
	
	public static ServiceLocator getInstance() {
		return INSTANCE;
	}
	
	public synchronized void initialize(ConfigurableApplicationContext ctx) {
		if(INSTANCE.ctx == null) {
			INSTANCE.ctx = ctx;
		}
	}
	
	public static <T> T getBean(Class<T> beanType){
		return ServiceLocator.getInstance().getApplicationContext().getBean(beanType);
	}
	
	
	public static <T> T getBean(Class<T> beanType, String qualifier) {
		return BeanFactoryAnnotationUtils.qualifiedBeanOfType(ServiceLocator.getInstance().getApplicationContext(), beanType, qualifier);
	}
	
	public static Object getBean(String beanName) {
		return ServiceLocator.getInstance().getApplicationContext().getBean(beanName);
	}
	
	public ApplicationContext getApplicationContext() {
		return INSTANCE.ctx;
	}
	
	public static <T> T getService(Class<T> clazz){
		return INSTANCE.ctx.getBean(clazz);
	}
	
	public static <T> List<T> getServices(Class<T> clazz) {
		Map<String, T> servicesMap = INSTANCE.ctx.getBeansOfType(clazz);
		List<T> services = new ArrayList<>();
		services.addAll(servicesMap.values());
		return services;
	}
}
