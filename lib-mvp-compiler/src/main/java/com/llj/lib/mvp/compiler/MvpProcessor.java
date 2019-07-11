package com.llj.lib.mvp.compiler;

import com.google.auto.service.AutoService;
import com.llj.lib.base.compiler.base.BaseProcessor;
import com.llj.lib.mvp.annotation.Presenter;
import com.llj.lib.mvp.compiler.utils.Consts;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * ArchitectureDemo.
 * describe:generate Mvp Code
 *
 * @author llj
 * @date 2018/9/6
 */
@AutoService(Processor.class)
public class MvpProcessor extends BaseProcessor<MvpAnnotateClass> {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        setPrefix(Consts.PREFIX_OF_LOGGER);
        super.init(processingEnvironment);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        mAnnotatedClassMap.clear();

        //处理注解
        try {
            processPresenterMethod(roundEnv);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return true; // stop process
        }
        //生成代码
        for (MvpAnnotateClass annotatedClass : mAnnotatedClassMap.values()) {
            try {
                annotatedClass.generateCode().writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                return true;
            }
        }
        return true;
    }

    private void processPresenterMethod(RoundEnvironment roundEnv) throws IllegalArgumentException {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Presenter.class);
        for (Element element : elements) {
            MvpAnnotateClass annotatedClass = getAnnotatedClass(element);
            annotatedClass.addIViewMethod(new PresenterMethod(element));
        }
    }

    protected MvpAnnotateClass getAnnotatedClass(Element element) {
        TypeElement classElement;

        if (element instanceof TypeElement) {
            classElement = (TypeElement) element;
        } else {
            classElement = (TypeElement) element.getEnclosingElement();
        }
        //以类名的全限定名为key
        String fullClassName = classElement.getQualifiedName().toString();

        MvpAnnotateClass annotatedClass = mAnnotatedClassMap.get(fullClassName);
        if (annotatedClass == null) {
            annotatedClass = new MvpAnnotateClass(classElement, mElementUtils);
            mAnnotatedClassMap.put(fullClassName, annotatedClass);
        }
        return annotatedClass;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(Presenter.class.getCanonicalName());
        return types;
    }


}
