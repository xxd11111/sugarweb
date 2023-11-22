package com.sugarcoat.protocol.auto;

import cn.hutool.core.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Set;

/**
 * TODO
 *
 * @author 许向东
 * @date 2023/11/22
 */
public abstract class AnnotationScanner implements Scanner {

    @Override
    public Collection<Object> scan() {
        String aPackage = getPackage();
        Class<? extends Annotation> scanAnnotation = getScanAnnotation();
        Set<Class<?>> scan = ClassUtil.scanPackageByAnnotation(aPackage, scanAnnotation);
        scan.forEach(this::resolver);
        return getResult();
    }

    public abstract void resolver(Class<?> clazz);

    public abstract String getPackage();

    public abstract Class<? extends Annotation> getScanAnnotation();

    public abstract Collection<Object> getResult();

}
