package com.llj.inject.gradle.plugin.util

import com.llj.inject.gradle.plugin.InjectMethodFilterClassVisitor
import org.objectweb.asm.*

/**
 * Created by bryansharp(bsp0911932@163.com) on 2016/5/10.
 * Modified by nailperry on 2017/3/2.
 *
 */
class ModifyClassUtil {

    static byte[] modifyClasses(String className, byte[] srcByteCode, HashSet<String> superFragments) {
        byte[] classBytesCode = null
        try {
            Log.info(" ")
            Log.info(" ")
            Log.info("====start modifying ${className}====")
            classBytesCode = modifyClass(srcByteCode, superFragments)
            Log.info("====revisit modified ${className}====")
            onlyVisitClassMethod(classBytesCode, superFragments)
            Log.info("====finish modifying ${className}====")
            return classBytesCode
        } catch (Exception e) {
            e.printStackTrace()
        }
        if (classBytesCode == null) {
            classBytesCode = srcByteCode
        }
        return classBytesCode
    }


    private static byte[] modifyClass(byte[] srcClass, HashSet<String> superFragments) throws IOException {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS)
        ClassVisitor classVisitor = new InjectMethodFilterClassVisitor(classWriter, superFragments)
        ClassReader classReader = new ClassReader(srcClass)
        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG)
        return classWriter.toByteArray()
    }

    private static void onlyVisitClassMethod(byte[] srcClass, HashSet<String> superFragments) throws IOException {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS)
        InjectMethodFilterClassVisitor classVisitor = new InjectMethodFilterClassVisitor(classWriter, superFragments)
        classVisitor.onlyVisit = true
        ClassReader classReader = new ClassReader(srcClass)
        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG)
    }


    /**
     *
     * @param opcode
     *            the opcode of the type instruction to be visited. This opcode
     *            is either INVOKEVIRTUAL, INVOKESPECIAL, INVOKESTATIC or
     *            INVOKEINTERFACE.
     * @param owner
     *            the internal name of the method's owner class (see
     * {@link org.objectweb.asm.Type#getInternalName() getInternalName}).
     * @param name
     *            the method's name.
     * @param desc
     *            the method's descriptor (see {@link org.objectweb.asm.Type Type}).
     * @param start 方法参数起始索引（ 0：this，1+：普通参数 ）
     *
     * @param count 方法参数个数
     *
     * @param paramOpcodes 参数类型对应的ASM指令
     *
     */
    public static void visitMethodWithLoadedParams(MethodVisitor methodVisitor, int opcode, String owner, String methodName, String methodDesc, int start, int count, List<Integer> paramOpcodes) {
        for (int i = start; i < start + count; i++) {
            methodVisitor.visitVarInsn(paramOpcodes[i - start], i)
        }
        methodVisitor.visitMethodInsn(opcode, owner, methodName, methodDesc, false)
    }


    public static void visitMethodWithLoadedParams(MethodVisitor methodVisitor, int opcode, String owner, InjectMethodCell methodCell) {
        int start = methodCell.paramsStart
        int count = methodCell.paramsCount
        List<Integer> paramOpcodes = methodCell.opcodes
        String agentName = methodCell.agentName
        String agentDesc = methodCell.agentDesc

        for (int i = start; i < start + count; i++) {
            methodVisitor.visitVarInsn(paramOpcodes[i - start], i)
        }
        methodVisitor.visitMethodInsn(opcode, owner, agentName, agentDesc, false)
    }


    public static boolean isSynthetic(int access) {
        return (access & Opcodes.ACC_SYNTHETIC) != 0
    }

    public static boolean isPrivate(int access) {
        return (access & Opcodes.ACC_PRIVATE) != 0
    }

    public static boolean isPublic(int access) {
        return (access & Opcodes.ACC_PUBLIC) != 0
    }

    public static boolean isStatic(int access) {
        return (access & Opcodes.ACC_STATIC) != 0
    }


}