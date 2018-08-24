package com.gmail.ivanjermakov1.telegramspeechrecognitionbot.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReflectionUtils {
	
	public static List<Method> getMethodsAnnotatedWith(Class clazz, Class<? extends Annotation> annotationClass) {
		return Arrays.stream(clazz.getDeclaredMethods())
				.filter(m -> m.isAnnotationPresent(annotationClass))
				.collect(Collectors.toList());
	}
	
	public static long methodsInStack(Class clazz, Thread thread) {
		return Arrays.stream(thread.getStackTrace())
				.filter(c -> c.getClassName().equals(clazz.getCanonicalName()))
				.count();
	}
	
	/**
	 * Returns all stackTraceElements of current class on current thread.
	 */
	public static List<StackTraceElement> getStackTraceElements(Class clazz, Thread thread) {
		return Arrays.stream(thread.getStackTrace())
				.filter(e -> e.getClassName().equals(clazz.getCanonicalName()))
				.collect(Collectors.toList());
	}
	
	public static Class getInvokerClass(String methodName) {
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		
		try {
			return Class.forName(trace[IntStream.range(0, trace.length - 1)
					.filter(i -> trace[i].getMethodName().equals(methodName))
					.findFirst()
					.orElseThrow(NoSuchElementException::new) + 1].getClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		throw new NullPointerException();
	}
	
}

