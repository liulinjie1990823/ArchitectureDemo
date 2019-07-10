package com.llj.lib.jump.compiler;

import com.example.lib.jump.annotation.Jump;
import com.example.lib.jump.annotation.JumpKey;
import com.example.lib.jump.annotation.callback.JumpCallback;
import com.google.auto.service.AutoService;
import com.llj.lib.base.compiler.base.BaseProcessor;
import com.llj.lib.base.compiler.enums.TypeKind;
import com.llj.lib.jump.compiler.utils.Consts;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2018/9/6
 */
@AutoService(Processor.class)
public class JumpProcessor extends BaseProcessor<JumpAnnotateClass> {

    static final ClassName APARSEUTILS = ClassName.get("com.llj.lib.utils", "AParseUtils");
    static final ClassName AROUTER     = ClassName.get("com.alibaba.android.arouter.launcher", "ARouter");
    static final ClassName POSTCARD    = ClassName.get("com.alibaba.android.arouter.facade", "Postcard");
    static final ClassName IROUTEGROUP = ClassName.get("com.llj.lib.jump.api.template", "IRouteGroup");

    static final String PACKAGE    = "com.llj.jump";
    static final String CLASS_NAME = "JumpHelp";

    private String loginOuterKey;//ciw配置的是否登录使用的key
    private String loginInnerKey = "needLogin";//内部判断是否登录的key,一般和loginOuterKey一样就好


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        setPrefix(Consts.PREFIX_OF_LOGGER);
        super.init(processingEnvironment);

        Map<String, String> options = processingEnv.getOptions();

        if (options != null && !options.isEmpty()) {
            this.loginOuterKey = options.get("loginOuterKey");

            String temp = options.get("loginInnerKey");
            if (temp != null && !temp.isEmpty()) {
                this.loginInnerKey = options.get("loginInnerKey");
            }
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        mAnnotatedClassMap.clear();

        //处理注解
        try {
            processJumpClass(roundEnv);
            processInnerKey(roundEnv);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return true; // stop process
        }
        //生成代码
        if (!mAnnotatedClassMap.isEmpty()) {
            try {
                generateCode().writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private void processInnerKey(RoundEnvironment roundEnv) throws IllegalArgumentException {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(JumpKey.class);
        for (Element element : elements) {
            JumpAnnotateClass annotatedClass = getAnnotatedClass(element);
            annotatedClass.addField(new InnerKeyField(element));
        }
    }

    private void processJumpClass(RoundEnvironment roundEnv) {
        //拿到被InnerJump注解的元素集合，可能是类，方法，变量
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Jump.class);
        for (Element element : elements) {
            JumpAnnotateClass annotatedClass = getAnnotatedClass(element);
            annotatedClass.setJumpClass(new JumpClass(element));
        }
    }


    protected JumpAnnotateClass getAnnotatedClass(Element element) {
        TypeElement classElement;

        if (element instanceof TypeElement) {
            classElement = (TypeElement) element;
        } else {
            classElement = (TypeElement) element.getEnclosingElement();
        }
        //以类名的全限定名为key
        String fullClassName = classElement.getQualifiedName().toString();

        JumpAnnotateClass annotatedClass = mAnnotatedClassMap.get(fullClassName);
        if (annotatedClass == null) {
            annotatedClass = new JumpAnnotateClass(mElementUtils);
            mAnnotatedClassMap.put(fullClassName, annotatedClass);
        }
        return annotatedClass;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(Jump.class.getCanonicalName());
        types.add(JumpKey.class.getCanonicalName());
        return types;
    }


    public JavaFile generateCode() {
        System.out.println();
        System.out.println();
        System.out.println("----- start ---- generateInnerJump----------");

        //Map<String, JumpCallback>
        ParameterizedTypeName paramType = ParameterizedTypeName.get(Map.class, String.class, JumpCallback.class);

        //private static Map<String, JumpCallback> sMap = new HashMap<>();
        FieldSpec.Builder mapFieldBuilder = FieldSpec.builder(paramType, "map")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .initializer("new $T<>()", HashMap.class);

        MethodSpec.Builder loadIntoMethodOfGroupBuilder = MethodSpec.methodBuilder("loadInto")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addParameter(ParameterSpec.builder(paramType, "map").build());


        CodeBlock.Builder builder = CodeBlock.builder();
        for (JumpAnnotateClass annotatedClass : mAnnotatedClassMap.values()) {

            JumpClass jumpClass = annotatedClass.getJumpClass();
            List<InnerKeyField> innerKeyFields = annotatedClass.getInnerKeyFields();

            // @Override
            //public void process(String paramOriginStr, Map<String, String> map) {}
            MethodSpec.Builder method = MethodSpec.methodBuilder("process")
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.VOID)
                    .addParameter(String.class, "paramOriginStr")
                    .addParameter(ParameterizedTypeName.get(Map.class, String.class, String.class), "map");

            for (InnerKeyField innerKeyField : innerKeyFields) {
                if (innerKeyField.isRequired()) {
                    method.beginControlFlow("if(map == null || map.get($S) == null)", innerKeyField.getCiw());//if(map != null) {
                    method.addStatement("return");//
                    method.endControlFlow();//
                }
            }
            method.addStatement("$T postcard = $T.getInstance().build($S)"
                    , POSTCARD
                    , AROUTER
                    , jumpClass.getRoute());//Postcard postcard = ARouter.getInstance().build("/app/AptActivity2");

            for (InnerKeyField innerKeyField : innerKeyFields) {
                // postcard.withBoolean("BOOLEAN",Boolean.parseBoolean(map.get("boolean1")));
                addStatement(method, "if(map != null) postcard.", innerKeyField, mTypeUtils.typeExchange(innerKeyField.getElement()));
            }

            //获取配置的登录参数
            if (loginOuterKey != null && !loginOuterKey.isEmpty()) {
                method.addStatement("postcard.withInt($S, $T.parseInt(map.get($S)))", loginInnerKey, APARSEUTILS, loginOuterKey);
            }

            //页面强制需要登录
            if (annotatedClass.getJumpClass().needLogin()) {
                //withInt(JHRoute.AROUTER_NEED_LOGIN, 1)
                method.addStatement("postcard.withInt($S, 1)", loginInnerKey);
            }

            method.addStatement("postcard.navigation()");//postcard.navigation();

            //内部类JumpCallback
            TypeSpec.Builder callBack = TypeSpec.anonymousClassBuilder("")
                    .superclass(JumpCallback.class)
                    .addMethod(method.build());

            //map.put("ciw://AptActivity2",new JumpCallback() {}
            //builder.addStatement("sMap.put($S,$L)", jumpClass.getCiw(), callBack.build());
            //map.put("ciw://EventActivity",new JumpCallback() {}
            loadIntoMethodOfGroupBuilder.addStatement("map.put($S,$L)", jumpClass.getCiw(), callBack.build());
        }

        // generate whole class
        TypeSpec finderClass = TypeSpec.classBuilder(CLASS_NAME + "_" + mModuleName)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(IROUTEGROUP)
//                .addField(mapFieldBuilder.build())
//                .addStaticBlock(builder.build())
                .addMethod(loadIntoMethodOfGroupBuilder.build())
                .build();

        //Which package is class generated in
        JavaFile javaFile = JavaFile.builder(PACKAGE, finderClass).build();

        System.out.println(javaFile.toString());
        System.out.println("----- end ---- generateInnerJump----------");
        System.out.println();
        System.out.println();
        return javaFile;
    }

    private void addStatement(MethodSpec.Builder method, String statement, InnerKeyField innerKeyField, int type) {
        switch (TypeKind.values()[type]) {
            case BOOLEAN:
                //method.addStatement(statement + "withBoolean($S," + "$T.parseBoolean(" + innerKeyField.getCiw() + "))", innerKeyField.getRoute(), Boolean.class);
                method.addStatement(statement + "withBoolean($S," + "$T.parseBoolean(map.get($S)" + "))", innerKeyField.getRoute(), Boolean.class, innerKeyField.getCiw());
                break;
            case BYTE:
                break;
            case SHORT:
                //method.addStatement(statement + "withShort($S," + innerKeyField.getCiw() + ")", innerKeyField.getRoute());
                method.addStatement(statement + "withShort($S," + "$T.parseShort(map.get($S)" + "))", innerKeyField.getRoute(), APARSEUTILS, innerKeyField.getCiw());
                break;
            case INT:
                //method.addStatement(statement + "withInt($S," + innerKeyField.getCiw() + ")", innerKeyField.getRoute());
                method.addStatement(statement + "withInt($S," + "$T.parseInt(map.get($S)" + "))", innerKeyField.getRoute(), APARSEUTILS, innerKeyField.getCiw());
                break;
            case LONG:
                //method.addStatement(statement + "withLong($S," + innerKeyField.getCiw() + ")", innerKeyField.getRoute());
                method.addStatement(statement + "withLong($S," + "$T.parseLong(map.get($S)" + "))", innerKeyField.getRoute(), APARSEUTILS, innerKeyField.getCiw());
                break;
            case CHAR:
                break;
            case FLOAT:
                //method.addStatement(statement + "withFloat($S," + innerKeyField.getCiw() + ")", innerKeyField.getRoute());
                method.addStatement(statement + "withFloat($S," + "$T.parseFloat(map.get($S)" + "))", innerKeyField.getRoute(), APARSEUTILS, innerKeyField.getCiw());
                break;
            case DOUBLE:
                //method.addStatement(statement + "withDouble($S," + innerKeyField.getCiw() + ")", innerKeyField.getRoute());
                method.addStatement(statement + "withDouble($S," + "$T.parseDouble(map.get($S)" + "))", innerKeyField.getRoute(), APARSEUTILS, innerKeyField.getCiw());
                break;
            case STRING:
                method.addStatement(statement + "withString($S,map.get($S))", innerKeyField.getRoute(), innerKeyField.getCiw());
                break;
            case SERIALIZABLE:
                break;
            case PARCELABLE:
                break;
            case OBJECT:
                break;
        }

    }

}
