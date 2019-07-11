package com.llj.lib.mvp.compiler;

import com.google.auto.service.AutoService;
import com.llj.lib.base.compiler.base.BaseProcessor;
import com.llj.lib.mvp.annotation.IView;
import com.llj.lib.mvp.annotation.ShowLoading;
import com.llj.lib.mvp.annotation.ShowRefreshing;
import com.llj.lib.mvp.compiler.utils.Consts;
import com.squareup.javapoet.ClassName;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2018/9/6
 */
@SupportedAnnotationTypes({})
@AutoService(Processor.class)
public class MvpProcessor extends BaseProcessor<MvpAnnotateClass> {

    static final ClassName APARSEUTILS = ClassName.get("com.llj.lib.utils", "AParseUtils");


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
            processIViewMethod(roundEnv);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return true; // stop process
        }
        //生成代码
//        for (MvpAnnotateClass annotatedClass : mAnnotatedClassMap.values()) {
//            try {
//                annotatedClass.generateCode().writeTo(mFiler);
//            } catch (IOException e) {
//                e.printStackTrace();
//                logger.error(e.getMessage());
//                return true;
//            }
//        }
        return true;
    }

    private void processIViewMethod(RoundEnvironment roundEnv) throws IllegalArgumentException {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(IView.class);
        for (Element element : elements) {
            MvpAnnotateClass annotatedClass = getAnnotatedClass(element);
            annotatedClass.addIViewMethod(new IViewMethod(element));
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
            annotatedClass = new MvpAnnotateClass(mElementUtils);
            mAnnotatedClassMap.put(fullClassName, annotatedClass);
        }
        return annotatedClass;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(IView.class.getCanonicalName());
        types.add(ShowLoading.class.getCanonicalName());
        types.add(ShowRefreshing.class.getCanonicalName());
        return types;
    }


}
