package com.llj.inject.gradle.plugin

import com.llj.inject.gradle.plugin.util.InjectMethodCell
import com.llj.inject.gradle.plugin.util.Log
import com.llj.inject.gradle.plugin.util.MethodLogVisitor
import com.llj.inject.gradle.plugin.util.ModifyClassUtil
import org.objectweb.asm.*

class InjectMethodFilterClassVisitor extends ClassVisitor {
    public boolean onlyVisit = false
    public HashSet<String> visitedFragMethods = new HashSet<>()
    private String superName//当前类的父类
    private String[] interfaces//当前类实现的接口
    private ClassVisitor classVisitor

    private HashSet<String> mSuperFragments

    public InjectMethodFilterClassVisitor(final ClassVisitor cv, HashSet<String> superFragments) {
        super(Opcodes.ASM5, cv)
        this.classVisitor = cv
        this.mSuperFragments = superFragments
    }

    @Override
    void visitEnd() {
        Log.logEach('* visitEnd *')

        if (mSuperFragments.contains(superName)) {
            MethodVisitor mv
            // 添加剩下的方法，确保super.onHiddenChanged(hidden)等先被调用
            Iterator<Map.Entry<String, InjectMethodCell>> iterator = InjectHookConfig.sFragmentMethods.entrySet().iterator()
            while (iterator.hasNext()) {
                Map.Entry<String, InjectMethodCell> entry = iterator.next()

                String key = entry.getKey()
                InjectMethodCell methodCell = entry.getValue()

                if (visitedFragMethods.contains(key))
                    continue

                mv = classVisitor.visitMethod(Opcodes.ACC_PUBLIC, methodCell.name, methodCell.desc, null, null)
                mv.visitCode()
                // call super
                ModifyClassUtil.visitMethodWithLoadedParams(mv, Opcodes.INVOKESPECIAL, superName, methodCell.name, methodCell.desc, methodCell.paramsStart, methodCell.paramsCount, methodCell.opcodes)
                // call injected method
                ModifyClassUtil.visitMethodWithLoadedParams(mv, Opcodes.INVOKESTATIC, InjectHookConfig.sAgentClassName, methodCell.agentName, methodCell.agentDesc, methodCell.paramsStart, methodCell.paramsCount, methodCell.opcodes)
                mv.visitInsn(Opcodes.RETURN)
                mv.visitMaxs(methodCell.paramsCount, methodCell.paramsCount)
                mv.visitEnd()
            }
        }
        super.visitEnd()
    }

    @Override
    void visitAttribute(Attribute attribute) {
        Log.logEach('* visitAttribute *', attribute, attribute.type, attribute.metaClass, attribute.metaPropertyValues, attribute.properties)
        super.visitAttribute(attribute)
    }

    @Override
    AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        Log.logEach('* visitAnnotation *', desc, visible)
        return super.visitAnnotation(desc, visible)
    }

    @Override
    void visitInnerClass(String name, String outerName, String innerName, int access) {
        Log.logEach('* visitInnerClass *', name, outerName, innerName, Log.accCode2String(access))
        super.visitInnerClass(name, outerName, innerName, access)
    }

    @Override
    void visitOuterClass(String owner, String name, String desc) {
        Log.logEach('* visitOuterClass *', owner, name, desc)
        super.visitOuterClass(owner, name, desc)
    }

    @Override
    void visitSource(String source, String debug) {
        Log.logEach('* visitSource *', source, debug)
        super.visitSource(source, debug)
    }

    @Override
    FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        Log.logEach('* visitField *', Log.accCode2String(access), name, desc, signature, value)
        return super.visitField(access, name, desc, signature, value)
    }

    /**
     * 该方法是当扫描类时第一个拜访的方法，主要用于类声明使用
     * @param version 表示类版本：51，表示 “.class” 文件的版本是 JDK 1.7
     * @param access 类的修饰符：修饰符在 ASM 中是以 “ACC_” 开头的常量进行定义。
     *                          可以作用到类级别上的修饰符有：ACC_PUBLIC（public）、ACC_PRIVATE（private）、ACC_PROTECTED（protected）、
     *                          ACC_FINAL（final）、ACC_SUPER（extends）、ACC_INTERFACE（接口）、ACC_ABSTRACT（抽象类）、
     *                          ACC_ANNOTATION（注解类型）、ACC_ENUM（枚举类型）、ACC_DEPRECATED（标记了@Deprecated注解的类）、ACC_SYNTHETIC
     * @param name 类的名称：通常我们的类完整类名使用 “org.test.mypackage.MyClass” 来表示，但是到了字节码中会以路径形式表示它们 “org/test/mypackage/MyClass” 。
     *                      值得注意的是虽然是路径表示法但是不需要写明类的 “.class” 扩展名。
     * @param signature 表示泛型信息，如果类并未定义任何泛型该参数为空
     * @param superName 表示所继承的父类：由于 Java 的类是单根结构，即所有类都继承自 java.lang.Object。 因此可以简单的理解为任何类都会具有一个父类。
     *                  虽然在编写 Java 程序时我们没有去写 extends 关键字去明确继承的父类，但是 JDK在编译时 总会为我们加上 “ extends Object”。
     * @param interfaces 表示类实现的接口，在 Java 中类是可以实现多个不同的接口因此此处是一个数组。
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        Log.logEach('* visit *', Log.accCode2String(access), name, signature, superName, interfaces)
        this.superName = superName
        this.interfaces = interfaces
        super.visit(version, access, name, signature, superName, interfaces)
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor myMv = null
        if (!onlyVisit) {
            Log.logEach("* visitMethod *", Log.accCode2String(access), name, desc, signature, exceptions)
        }

        boolean isHasTracked = false

        String nameDesc = name + desc//通过name + desc获取对应的InjectMethodCell

        //注入sInterfaceMethods配置的代码
        if (interfaces != null && interfaces.length > 0) {
            InjectMethodCell methodCell = InjectHookConfig.sInterfaceMethods.get(nameDesc)
            //判断该类是否实现了该方法的接口
            if (methodCell != null && interfaces.contains(methodCell.parent)) {
                if (onlyVisit) {
                    //仅仅是访问，只会打log
                    myMv = new MethodLogVisitor(cv.visitMethod(access, name, desc, signature, exceptions))
                } else {
                    //打log,也会修改方法
                    try {
                        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions)
                        myMv = new MethodLogVisitor(methodVisitor) {

                            @Override
                            void visitInsn(int opcode) {
                                // 确保super.onHiddenChanged(hidden)等先被调用
                                if (opcode == Opcodes.RETURN) { //在返回之前安插代码
                                    ModifyClassUtil.visitMethodWithLoadedParams(methodVisitor, Opcodes.INVOKESTATIC, InjectHookConfig.sAgentClassName, methodCell)
                                }
                                isHasTracked = true
                                super.visitInsn(opcode)
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace()
                        myMv = null
                    }
                }
            }
        }

        if (!isHasTracked) {
            InjectMethodCell methodCell = InjectHookConfig.sSuperNameMethods.get(nameDesc)
            //判断该类是否实现了该方法的接口
            if (methodCell != null && superName.equals(methodCell.parent)) {
                if (onlyVisit) {
                    //仅仅是访问，只会打log
                    myMv = new MethodLogVisitor(cv.visitMethod(access, name, desc, signature, exceptions))
                } else {
                    //打log,也会修改方法
                    try {
                        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions)
                        myMv = new MethodLogVisitor(methodVisitor) {

                            @Override
                            void visitInsn(int opcode) {
                                // 确保super.onHiddenChanged(hidden)等先被调用
                                if (opcode == Opcodes.RETURN) { //在返回之前安插代码
                                    ModifyClassUtil.visitMethodWithLoadedParams(methodVisitor, Opcodes.INVOKESTATIC, InjectHookConfig.sAgentClassName, methodCell)
                                }
                                isHasTracked = true
                                super.visitInsn(opcode)
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace()
                        myMv = null
                    }
                }
            }

        }
        //注入sFragmentMethods配置的生命周期回调
        if ((!isHasTracked) && mSuperFragments.contains(superName)) {
            InjectMethodCell methodCell = InjectHookConfig.sFragmentMethods.get(name + desc)
            if (methodCell != null) {
                // 记录该方法已存在
                visitedFragMethods.add(name + desc)
                if (onlyVisit) {
                    MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions)
                    myMv = new MethodLogVisitor(methodVisitor)
                } else {
                    try {
                        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions)
                        myMv = new MethodLogVisitor(methodVisitor) {

                            @Override
                            void visitInsn(int opcode) {
                                // 确保super.onHiddenChanged(hidden)等先被调用
                                if (opcode == Opcodes.RETURN) { //在返回之前安插代码
                                    ModifyClassUtil.visitMethodWithLoadedParams(methodVisitor, Opcodes.INVOKESTATIC, InjectHookConfig.sAgentClassName, methodCell)
                                }
                                super.visitInsn(opcode)
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace()
                        myMv = null
                    }
                }
            }
        }
        if (myMv != null) {
            if (onlyVisit) {
                Log.logEach("* revisitMethod *", Log.accCode2String(access), name, desc, signature)
            }
            return myMv
        } else {
            return cv.visitMethod(access, name, desc, signature, exceptions)
        }
    }
}