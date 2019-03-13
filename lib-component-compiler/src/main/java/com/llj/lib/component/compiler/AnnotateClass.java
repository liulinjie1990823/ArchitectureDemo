package com.llj.lib.component.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/6
 */
public class AnnotateClass {
    private static final String FINDER_SUFFIX = "_Finder";

    private static final ClassName ANDROID_VIEW              = ClassName.get("android.view", "View");
    private static final ClassName ANDROID_ON_CLICK_LISTENER = ClassName.get("android.view", "View", "OnClickListener");
    private static final ClassName FINDER                    = ClassName.get("com.llj.lib.component.api.finder", "Finder");
    private static final ClassName PROVIDER                  = ClassName.get("com.llj.lib.component.api.provider", "Provider");
    private static final ClassName UTILS                     = ClassName.get("com.llj.lib.component.api.finder", "Utils");

    public List<IntentField>   mIntentFields;
    public List<BindViewField> mBindViewFields;
    public List<OnClickMethod> mMethods;


    public TypeElement mClassElement;
    public Elements    mElementUtils;

    public AnnotateClass(TypeElement classElement, Elements elementUtils) {
        this.mClassElement = classElement;

        this.mIntentFields = new ArrayList<>();
        this.mBindViewFields = new ArrayList<>();
        this.mMethods = new ArrayList<>();
        this.mElementUtils = elementUtils;
    }

    public String getFullClassName() {
        return mClassElement.getQualifiedName().toString();
    }

    public void addField(BindViewField field) {
        mBindViewFields.add(field);
    }

    public void addMethod(OnClickMethod method) {
        mMethods.add(method);
    }

    public void addIntent(IntentField intent) {
        mIntentFields.add(intent);
    }

    public JavaFile generateFinder() {
        System.out.println("----- start ---- generateFinder----------");
        // method inject(final T activity, Object source, Provider provider)
        MethodSpec.Builder injectMethodBuilder = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(mClassElement.asType()), "activity", Modifier.FINAL)
                .addParameter(ANDROID_VIEW, "source")
                ;

        //赋值mIntentFields
        for (IntentField field : mIntentFields) {
            injectMethodBuilder.addStatement("activity.$N = activity.getIntent().getStringExtra($S)", field.getFieldName(), field.getKey());
        }

        //赋值mBindViewFields
        for (BindViewField field : mBindViewFields) {
            // find views
//            injectMethodBuilder.addStatement("activity.$N = ($T)(provider.findView(source, $L))", field.getFieldName(), ClassName.get(field.getFieldType()), field.getResId());
            injectMethodBuilder.addStatement("activity.$N = $T.findRequiredViewAsType(source, $L, $S, $T.class)", field.getFieldName(), UTILS, field.getResId(),"activity",field.getRawType());
//            injectMethodBuilder.addStatement("activity.$N = $T.findViewById(source, $L)", field.getFieldName(), UTILS, field.getResId());
        }

        if (mMethods.size() > 0) {
            injectMethodBuilder.addStatement("$T listener", ANDROID_ON_CLICK_LISTENER);
        }

        //设置listener
        for (OnClickMethod method : mMethods) {
            TypeSpec listener = TypeSpec.anonymousClassBuilder("")
                    .addSuperinterface(ANDROID_ON_CLICK_LISTENER)
                    .addMethod(MethodSpec.methodBuilder("onClick")
                            .addModifiers(Modifier.PUBLIC)
                            .addAnnotation(Override.class)
                            .returns(TypeName.VOID)
                            .addParameter(ANDROID_VIEW, "view")
                            .addStatement("activity.$N()", method.getMethodName())
                            .build())
                    .build();
            injectMethodBuilder.addStatement("listener = $L ", listener);
            for (int id : method.ids) {
                // set listeners
                injectMethodBuilder.addStatement("$T.findViewById(source, $L).setOnClickListener(listener)", UTILS, id);
            }
        }

        // generate whole class
        TypeSpec finderClass = TypeSpec.classBuilder(mClassElement.getSimpleName() + FINDER_SUFFIX)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(FINDER, TypeName.get(mClassElement.asType())))
                .addMethod(injectMethodBuilder.build())
                .build();

        String packageName = mElementUtils.getPackageOf(mClassElement).getQualifiedName().toString();

        JavaFile javaFile = JavaFile.builder(packageName, finderClass).build();
        System.out.println("~~@#￥%……&*（ ------ === " + javaFile.toString());
        return javaFile;
    }
}
