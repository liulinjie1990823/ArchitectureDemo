package com.llj.lib.base.compiler.base;

import com.llj.lib.base.compiler.utils.Constants;
import com.llj.lib.base.compiler.utils.Logger;
import com.llj.lib.base.compiler.utils.TypeUtils;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-13
 */
public abstract class BaseProcessor<T extends BaseAnnotateClass> extends AbstractProcessor {

    protected Elements  mElementUtils;//元素相关的辅助类
    protected Types     mTypes;//用来处理TypeMirror的工具。
    protected TypeUtils mTypeUtils;

    protected Filer    mFiler;       //文件相关的辅助类
    protected Messager mMessager;    //日志相关的辅助类

    protected String prefix;
    protected Logger logger;//封装Messager，记录日志

    protected String mModuleName;

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementUtils = processingEnv.getElementUtils();
        mTypes = processingEnv.getTypeUtils();
        mTypeUtils = new TypeUtils(mTypes, mElementUtils);

        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();

        logger = new Logger(mMessager, prefix);

        // Attempt to get user configuration [moduleName]
        Map<String, String> options = processingEnv.getOptions();
        initModuleName(options);
    }

    private void initModuleName(Map<String, String> options) {
        if (options != null && !options.isEmpty()) {
            mModuleName = options.get(Constants.KEY_MODULE_NAME);
        }
        if (mModuleName != null && !mModuleName.isEmpty()) {
            mModuleName = mModuleName.replaceAll("[^0-9a-zA-Z_]+", "");

            logger.info("The user has configuration the module name, it was [" + mModuleName + "]");
        } else {
            logger.error(Constants.NO_MODULE_NAME_TIPS);
            throw new RuntimeException("ARouter::Compiler >>> No module name, for more information, look at gradle log.");
        }
    }

    protected Map<String, T> mAnnotatedClassMap = new HashMap<>();

    /**
     * 以类名为key，每个类名一个AnnotateClass，在多个类中有注释就会产生多个
     *
     * @param element 元素
     *
     * @return StableAnnotateClass
     */

//    protected JumpAnnotateClass getAnnotatedClass(Element element) {
//        TypeElement classElement;
//
//        if (element instanceof TypeElement) {
//            classElement = (TypeElement) element;
//        } else {
//            classElement = (TypeElement) element.getEnclosingElement();
//        }
//        //以类名的全限定名为key
//        String fullClassName = classElement.getQualifiedName().toString();
//
//        BaseAnnotateClass annotatedClass = mAnnotatedClassMap.get(fullClassName);
//        if (annotatedClass == null) {
//            mAnnotatedClassMap.put(fullClassName, (T) new JumpAnnotateClass(classElement, mElementUtils));
//        }
//        return (JumpAnnotateClass) annotatedClass;
//    }


}
