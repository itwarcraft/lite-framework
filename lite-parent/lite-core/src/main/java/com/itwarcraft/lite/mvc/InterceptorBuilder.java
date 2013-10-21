package com.itwarcraft.lite.mvc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.itwarcraft.lite.annotation.Interceptors;

/**
 * 拦截器构建类：查找注解构建拦截器Map
 */
public class InterceptorBuilder {
	private static Logger logging = Logger.getLogger(InterceptorBuilder.class);

	private static final Interceptor[] Null = new Interceptor[0];
	private Map<Class<Interceptor>, Interceptor> interceptorMap = new HashMap<Class<Interceptor>, Interceptor>();

	@SuppressWarnings("unchecked")
	/**
	 * 将系统最基础的拦截器数组转换为HashMap<Interceptor>
	 */
	public void addBaseInterceptorsToInterceptorMap(Interceptor[] baseInterceptors) {
		for (Interceptor interceptor : baseInterceptors) {
			interceptorMap.put((Class<Interceptor>) interceptor.getClass(), interceptor);
		}
	}

	/**
	 * 构建Action的拦截器
	 */
	public Interceptor[] buildActionInterceptors(Interceptor[] baseInterceptors, Class<?> controllerClass, Interceptor[] controllerInterceptors, Method method, Interceptor[] methodInterceptors) {

		/*
		ClearLayer controllerClearType = getClearTypeOnTheController(controllerClass);
		if (controllerClearType != null) {
			baseInterceptors = Null;
		}
		ClearLayer methodClearType = getClearTypeOnTheMethod(method);
		if (methodClearType != null) {
			// 该处的设计方式很好,至少去除Controller上面的拦截器
			controllerInterceptors = Null;
			if (methodClearType == ClearLayer.All) {
				baseInterceptors = Null;
			}
		}*/
		int size = (baseInterceptors == null ? 0 : baseInterceptors.length) + controllerInterceptors.length + methodInterceptors.length;
		if (size == 0) {
			return Null;
		} else {
			Interceptor[] allInterceptorsOnTheAction = new Interceptor[size];
			int index = 0;
			if (baseInterceptors != null) {
				for (int i = 0; i < baseInterceptors.length; i++) {
					allInterceptorsOnTheAction[index++] = baseInterceptors[i];
				}
			}

			for (int i = 0; i < controllerInterceptors.length; i++) {
				allInterceptorsOnTheAction[index++] = controllerInterceptors[i];
			}
			for (int i = 0; i < methodInterceptors.length; i++) {
				allInterceptorsOnTheAction[index++] = methodInterceptors[i];
			}
			return allInterceptorsOnTheAction;
		}

	}

	/**
	 * 构建控制器上面的拦截器
	 */
	public Interceptor[] buildControllerInterceptors(Class<?> controllerClass) {
		Interceptors intercepters = controllerClass.getAnnotation(Interceptors.class);
		return intercepters != null ? createInterceptors(intercepters) : Null;
	}

	/**
	 * 构建方法上面的拦截器
	 */
	public Interceptor[] buildMethodInterceptors(Method method) {
		Interceptors intercepters = method.getAnnotation(Interceptors.class);
		return intercepters != null ? createInterceptors(intercepters) : Null;
	}

	/**
	 * 创建拦截器:每个拦截器只实例化一次
	 */
	// 该类实现的模式是数据库存储模式,即有一个返回值和一个或者多个引用传值:interceptorMap是引用传值,interceptors是返回值
	private Interceptor[] createInterceptors(Interceptors before) {
		Interceptor[] interceptors = null;
		@SuppressWarnings("unchecked")
		// :Before(values={xx.class,yy.class})
		Class<Interceptor>[] interceptorClasses = (Class<Interceptor>[]) before.value();
		if (interceptorClasses != null && interceptorClasses.length > 0) {
			interceptors = new Interceptor[interceptorClasses.length];
			int length = interceptors.length;
			for (int i = 0; i < length; i++) {
				interceptors[i] = interceptorMap.get(interceptorClasses[i]);
				if (interceptors[i] != null) {
					// 只实例化一次
					continue;
				}
				try {
					interceptors[i] = interceptorClasses[i].newInstance();
					interceptorMap.put(interceptorClasses[i], interceptors[i]);
				} catch (Exception e) {
					logging.error(e);
					throw new RuntimeException(e);
				}
			}
		}
		return interceptors;
	}

	/**
	 * 得到标记在控制器上的ClearInterceptor的值
	 
	private ClearLayer getClearTypeOnTheController(Class<?> controllerClass) {

		ClearInterceptor clearInterceptor = controllerClass.getAnnotation(ClearInterceptor.class);
		return clearInterceptor != null ? clearInterceptor.value() : null;
	}
*/
	/**
	 * 得到标记在方法上的ClearInterceptor的值
	 
	private ClearLayer getClearTypeOnTheMethod(Method method) {
		ClearInterceptor clearInterceptor = method.getAnnotation(ClearInterceptor.class);
		return clearInterceptor != null ? clearInterceptor.value() : null;
	}*/

}