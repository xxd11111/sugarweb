package com.sugarcoat.protocol.auto;

import cn.hutool.core.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Set;

/**
 * 注解扫描
 *
 * @author 许向东
 * @date 2023/11/22
 */
public abstract class AnnotationScanner<T> implements Scanner<T> {

    private final String packagePath;

    private final Class<? extends Annotation> scanAnnotation;

    public AnnotationScanner(String packagePath, Class<? extends Annotation> scanAnnotation) {
        this.packagePath = packagePath;
        this.scanAnnotation = scanAnnotation;
    }

    @Override
    public Collection<T> scan() {
        Set<Class<?>> scan = ClassUtil.scanPackageByAnnotation(packagePath, scanAnnotation);
        scan.forEach(this::resolver);
        return getResult();
    }

    public abstract void resolver(Class<?> clazz);

    public abstract Collection<T> getResult();

}
