package com.llj.lib.base.compiler.utils;

import com.llj.lib.base.compiler.enums.TypeKind;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;


/**
 * Utils for type exchange
 *
 * @author zhilong <a href="mailto:zhilong.lzl@alibaba-inc.com">Contact me.</a>
 * @version 1.0
 * @since 2017/2/21 下午1:06
 */
public class TypeUtils {

    private Types      types;
    private TypeMirror parcelableType;
    private TypeMirror serializableType;

    public TypeUtils(Types types, Elements elements) {
        this.types = types;

        parcelableType = elements.getTypeElement(Constants.PARCELABLE).asType();
        serializableType = elements.getTypeElement(Constants.SERIALIZABLE).asType();
    }

    /**
     * Diagnostics out the true java type
     *
     * @param element Raw type
     *
     * @return Type class of java
     */
    public int typeExchange(Element element) {
        TypeMirror typeMirror = element.asType();

        // Primitive
        if (typeMirror.getKind().isPrimitive()) {
            return element.asType().getKind().ordinal();
        }

        //Box Kind and other
        switch (typeMirror.toString()) {
            case Constants.BYTE:
                return TypeKind.BYTE.ordinal();
            case Constants.SHORT:
                return TypeKind.SHORT.ordinal();
            case Constants.INTEGER:
                return TypeKind.INT.ordinal();
            case Constants.LONG:
                return TypeKind.LONG.ordinal();
            case Constants.FLOAT:
                return TypeKind.FLOAT.ordinal();
            case Constants.DOUBLE:
                return TypeKind.DOUBLE.ordinal();
            case Constants.BOOLEAN:
                return TypeKind.BOOLEAN.ordinal();
            case Constants.CHAR:
                return TypeKind.CHAR.ordinal();
            case Constants.STRING:
                return TypeKind.STRING.ordinal();
            default:
                // Other side, maybe the PARCELABLE or SERIALIZABLE or OBJECT.
                if (types.isSubtype(typeMirror, parcelableType)) {
                    // PARCELABLE
                    return TypeKind.PARCELABLE.ordinal();
                } else if (types.isSubtype(typeMirror, serializableType)) {
                    // SERIALIZABLE
                    return TypeKind.SERIALIZABLE.ordinal();
                } else {
                    return TypeKind.OBJECT.ordinal();
                }
        }
    }
}