package com.llj.lib.mvp.compiler;

import com.llj.lib.base.compiler.base.BaseAnnotateClass;
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
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;



/**
 * ArchitectureDemo.
 * 自动生成网络请求的task,避免写重复的代码。
 * <pre>
 * {@code
 *
 * package com.llj.login.ui.presenter;
 *
 * import com.llj.adapter.refresh.IRefresh;
 * import com.llj.application.vo.UserInfoVo;
 * import com.llj.lib.net.RxApiManager;
 * import com.llj.lib.net.observer.BaseApiObserver;
 * import com.llj.lib.net.response.BaseResponse;
 * import com.llj.login.ui.repository.LoginRepository;
 * import com.llj.login.ui.view.ILoginView;
 * import io.reactivex.Single;
 * import io.reactivex.disposables.Disposable;
 * import java.lang.Override;
 * import java.lang.Throwable;
 * import java.util.HashMap;
 * import retrofit2.Response;
 *
 * public class LoginPresenter_Helper extends LoginPresenter {
 *   public LoginPresenter_Helper(LoginRepository repository) {
 *     super(repository);
 *   }
 *
 *   public void accountLogin(boolean showLoading, int requestId, ILoginView.PhoneLogin view) {
 *     if (view == null) {
 *       return;
 *     }
 *     int taskId = requestId;
 *     if (view.getLoadingDialog() != null) view.getLoadingDialog().setRequestId(taskId);
 *     HashMap<String, Object> param = view.getParams1(taskId);
 *     if (param == null)  {
 *       return;
 *     }
 *     Single<Response<BaseResponse<UserInfoVo>>> single = getRepository().accountLogin(param);
 *     if (showLoading) {
 *       single = single.doOnSubscribe(view).doFinally(view);
 *     }
 *     BaseApiObserver<UserInfoVo> baseApiObserver = new BaseApiObserver<UserInfoVo>(view) {
 *       @Override
 *       public void onSubscribe(Disposable d) {
 *         super.onSubscribe(d);
 *         view.addDisposable(getRequestId(), d);
 *       }
 *
 *       @Override
 *       public void onSuccess(BaseResponse<UserInfoVo> response) {
 *         super.onSuccess(response);
 *         view.onDataSuccess1(response.getData(), getRequestId());
 *       }
 *
 *       @Override
 *       public void onError(Throwable t) {
 *         super.onError(t);
 *         view.onDataError(1, t, getRequestId());
 *       }
 *     };
 *     RxApiManager.get().subscribeApi(single, view.bindRequestLifecycle(), baseApiObserver);
 *   }
 * }
 *
 * }
 * </pre>
 * author llj
 * date 2018/9/6
 */
public class MvpAnnotateClass extends BaseAnnotateClass {
    private static final String SUFFIX = "_Helper";

    static final ClassName HASHMAP         = ClassName.get("java.util", "HashMap");
    static final ClassName SINGLE          = ClassName.get("io.reactivex", "Single");
    static final ClassName RESPONSE        = ClassName.get("retrofit2", "Response");
    static final ClassName BASERESPONSE    = ClassName.get("com.llj.lib.net.response", "BaseResponse");
    static final ClassName BASEAPIOBSERVER = ClassName.get("com.llj.lib.net.observer", "BaseApiObserver");
    static final ClassName LOGINREPOSITORY = ClassName.get("com.llj.login.ui.repository", "LoginRepository");
    static final ClassName DISPOSABLE      = ClassName.get("io.reactivex.disposables", "Disposable");
    static final ClassName THROWABLE       = ClassName.get("java.lang", "Throwable");
    static final ClassName RXAPIMANAGER    = ClassName.get("com.llj.lib.net", "RxApiManager");


    private List<PresenterMethod> mPresenterMethods;//@Presenter注释的方法集合

    public MvpAnnotateClass(TypeElement classElement, Elements elementUtils) {
        super(classElement, elementUtils);
        mPresenterMethods = new ArrayList<>();
    }

    public void addIViewMethod(PresenterMethod method) {
        mPresenterMethods.add(method);
    }

    @Override
    public JavaFile generateCode() {
        System.out.println("----- start ---- generate" + getSimpleClassName() + SUFFIX + "----------");

        List<MethodSpec> methodSpecList = new ArrayList<>();


        MethodSpec.Builder initMethod = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(LOGINREPOSITORY, "repository")
                .addStatement("super(repository)");

        methodSpecList.add(initMethod.build());

        for (int i = 0; i < mPresenterMethods.size(); i++) {
            PresenterMethod presenterMethod = mPresenterMethods.get(i);
            if (presenterMethod == null) {
                continue;
            }

            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(presenterMethod.getMethodName().toString())
                    .addModifiers(Modifier.PUBLIC);
            List<? extends VariableElement> parameters = presenterMethod.getParameters();

            List<String> paramNames = new ArrayList<>();
            if (parameters != null) {
                for (int j = 0; j < parameters.size(); j++) {
                    VariableElement variableElement = parameters.get(j);
                    methodBuilder.addParameter(TypeName.get(variableElement.asType()), variableElement.getSimpleName().toString());
                    paramNames.add(variableElement.getSimpleName().toString());
                }
            }
            if (presenterMethod.isShowLoading()) {
                methodBuilder.beginControlFlow("if (view == null)").addStatement("return").endControlFlow();
                if (paramNames.contains(presenterMethod.getRequestIdKey())) {
                    methodBuilder.addStatement("int taskId = requestId");
                } else {
                    methodBuilder.addStatement("int taskId = view.hashCode()");
                }
                methodBuilder.addStatement("if (view.getLoadingDialog() != null) view.getLoadingDialog().setRequestId(taskId)");

                methodBuilder.addStatement("$T<String, Object> param = view.getParams1(taskId)", HASHMAP);
                methodBuilder.beginControlFlow("if (param == null) ");
                methodBuilder.addStatement("return");
                methodBuilder.endControlFlow();

                String method = (presenterMethod.getMethod() != null && presenterMethod.getMethod().length() != 0) ? presenterMethod.getMethod() : presenterMethod.getMethodName().toString();
                methodBuilder.addStatement("$T<$T<$T<$T>>> single = getRepository()." + method + "(param)", SINGLE, RESPONSE, BASERESPONSE, TypeName.get(presenterMethod.getObject()));

                methodBuilder.beginControlFlow("if (showLoading)");
                methodBuilder.addStatement("single = single.doOnSubscribe(view).doFinally(view)");//
                methodBuilder.endControlFlow();//
            } else {
                methodBuilder.beginControlFlow("if (iRefresh == null || view == null)").addStatement("return").endControlFlow();
                if (paramNames.contains(presenterMethod.getRequestIdKey())) {
                    methodBuilder.addStatement("int taskId = requestId");
                } else {
                    methodBuilder.addStatement("int taskId = view.hashCode()");
                }
                methodBuilder.addStatement("if (view.getLoadingDialog() != null) view.getLoadingDialog().setRequestId(taskId)");

                methodBuilder.addStatement("$T<String, Object> param = view.getParams1(taskId)", HASHMAP);
                methodBuilder.beginControlFlow("if (param == null) ");
                methodBuilder.addStatement("return");
                methodBuilder.endControlFlow();

                methodBuilder.beginControlFlow("if (refresh)");
                methodBuilder.addStatement("iRefresh.resetPageNum()");
                methodBuilder.endControlFlow();

                methodBuilder.addStatement("param.put(\"pageNum\", iRefresh.getCurrentPageNum())");
                methodBuilder.addStatement("param.put(\"pageSize\", iRefresh.getPageSize())");


                String method = (presenterMethod.getMethod() != null && presenterMethod.getMethod().length() != 0) ? presenterMethod.getMethod() : presenterMethod.getMethodName().toString();
                methodBuilder.addStatement("$T<$T<$T<$T>>> single = getRepository()." + method + "(param)", SINGLE, RESPONSE, BASERESPONSE, TypeName.get(presenterMethod.getObject()));

            }

            MethodSpec onSubscribeMethod = MethodSpec.methodBuilder("onSubscribe")
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(DISPOSABLE, "d")
                    .addStatement("super.onSubscribe(d)")
                    .addStatement("view.addDisposable(getRequestId(), d)")
                    .build();

            MethodSpec onSuccessMethod = MethodSpec.methodBuilder("onSuccess")
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ParameterizedTypeName.get(BASERESPONSE, TypeName.get(presenterMethod.getObject())), "response")
                    .addStatement("super.onSuccess(response)")
                    .addStatement("view.onDataSuccess1(response.getData(), getRequestId())")
                    .build();

            MethodSpec onErrorMethod = MethodSpec.methodBuilder("onError")
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(THROWABLE, "t")
                    .addStatement("super.onError(t)")
                    .addStatement("view.onDataError($L, t, getRequestId())", presenterMethod.getExceptionId())
                    .build();

            TypeSpec callBack = TypeSpec.anonymousClassBuilder("view")
                    .superclass(ParameterizedTypeName.get(BASEAPIOBSERVER, TypeName.get(presenterMethod.getObject())))
                    .addMethod(onSubscribeMethod)
                    .addMethod(onSuccessMethod)
                    .addMethod(onErrorMethod)
                    .build();

            methodBuilder.addStatement("$T<UserInfoVo> baseApiObserver = $L", BASEAPIOBSERVER, callBack);
            methodBuilder.addStatement("$T.get().subscribeApi(single, view.bindRequestLifecycle(), baseApiObserver)", RXAPIMANAGER);

            methodSpecList.add(methodBuilder.build());
        }

        // generate whole class
        TypeSpec finderClass = TypeSpec.classBuilder(getSimpleClassName() + SUFFIX)
                .addModifiers(Modifier.PUBLIC)
                .superclass(TypeName.get(mClassElement.asType()))
                .addMethods(methodSpecList)
                .build();
        JavaFile javaFile = JavaFile.builder(getPackageName(), finderClass).build();

        System.out.println(javaFile.toString());
        System.out.println("----- end ---- generate" + getSimpleClassName() + SUFFIX + "----------");
        return javaFile;
    }
}
