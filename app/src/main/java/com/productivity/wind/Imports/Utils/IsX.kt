package com.productivity.wind.Imports.Utils

import com.productivity.wind.*




fun isString(clazz: Class<*>) =
    clazz == String::class.java

fun isInteger(clazz: Class<*>) =
    clazz == Integer::class.java

fun isBoolean(clazz: Class<*>) =
    clazz == Boolean::class.java

fun isLong(clazz: Class<*>) =
    clazz == Long::class.java

fun isDouble(clazz: Class<*>) =
    clazz == Double::class.java

fun isFloat(clazz: Class<*>) =
    clazz == Float::class.java

fun isShort(clazz: Class<*>) =
    clazz == Short::class.java

fun isByte(clazz: Class<*>) =
    clazz == Byte::class.java

fun isCharacter(clazz: Class<*>) =
    clazz == Character::class.java

fun isVoid(clazz: Class<*>) =
    clazz == Void::TYPE || clazz == Void::class.java

fun isNumber(clazz: Class<*>) =
    Number::class.java.isAssignableFrom(clazz)

fun isPrimitive(clazz: Class<*>) =
    clazz.isPrimitive

fun isArray(clazz: Class<*>) =
    clazz.isArray

fun isEnum(clazz: Class<*>) =
    clazz.isEnum

fun isInterface(clazz: Class<*>) =
    clazz.isInterface

fun isAnnotation(clazz: Class<*>) =
    clazz.isAnnotation

fun isSynthetic(clazz: Class<*>) =
    clazz.isSynthetic

fun isAnonymous(clazz: Class<*>) =
    clazz.isAnonymousClass

fun isLocal(clazz: Class<*>) =
    clazz.isLocalClass

fun isMember(clazz: Class<*>) =
    clazz.isMemberClass

fun isNested(clazz: Class<*>) =
    clazz.isMemberClass || clazz.isLocalClass || clazz.isAnonymousClass

fun isAbstract(clazz: Class<*>) =
    java.lang.reflect.Modifier.isAbstract(clazz.modifiers)

fun isFinal(clazz: Class<*>) =
    java.lang.reflect.Modifier.isFinal(clazz.modifiers)

fun isPublic(clazz: Class<*>) =
    java.lang.reflect.Modifier.isPublic(clazz.modifiers)

fun isPrivate(clazz: Class<*>) =
    java.lang.reflect.Modifier.isPrivate(clazz.modifiers)

fun isProtected(clazz: Class<*>) =
    java.lang.reflect.Modifier.isProtected(clazz.modifiers)

fun isStatic(clazz: Class<*>) =
    java.lang.reflect.Modifier.isStatic(clazz.modifiers)

fun isStrict(clazz: Class<*>) =
    java.lang.reflect.Modifier.isStrict(clazz.modifiers)

fun isSynchronized(clazz: Class<*>) =
    java.lang.reflect.Modifier.isSynchronized(clazz.modifiers)

fun isNative(clazz: Class<*>) =
    java.lang.reflect.Modifier.isNative(clazz.modifiers)

fun isTransient(clazz: Class<*>) =
    java.lang.reflect.Modifier.isTransient(clazz.modifiers)

fun isVolatile(clazz: Class<*>) =
    java.lang.reflect.Modifier.isVolatile(clazz.modifiers)

fun isCollection(clazz: Class<*>) =
    Collection::class.java.isAssignableFrom(clazz)

fun isList(clazz: Class<*>) =
    List::class.java.isAssignableFrom(clazz)

fun isMutableList(clazz: Class<*>) =
    MutableList::class.java.isAssignableFrom(clazz)

fun isSet(clazz: Class<*>) =
    Set::class.java.isAssignableFrom(clazz)

fun isMutableSet(clazz: Class<*>) =
    MutableSet::class.java.isAssignableFrom(clazz)

fun isMap(clazz: Class<*>) =
    Map::class.java.isAssignableFrom(clazz)

fun isMutableMap(clazz: Class<*>) =
    MutableMap::class.java.isAssignableFrom(clazz)

fun isIterable(clazz: Class<*>) =
    Iterable::class.java.isAssignableFrom(clazz)

fun isIterator(clazz: Class<*>) =
    Iterator::class.java.isAssignableFrom(clazz)

fun isSequence(clazz: Class<*>) =
    Sequence::class.java.isAssignableFrom(clazz)

fun isComparable(clazz: Class<*>) =
    Comparable::class.java.isAssignableFrom(clazz)

fun isCloneable(clazz: Class<*>) =
    Cloneable::class.java.isAssignableFrom(clazz)

fun isSerializable(clazz: Class<*>) =
    java.io.Serializable::class.java.isAssignableFrom(clazz)

fun isThrowable(clazz: Class<*>) =
    Throwable::class.java.isAssignableFrom(clazz)

fun isException(clazz: Class<*>) =
    Exception::class.java.isAssignableFrom(clazz)

fun isRuntimeException(clazz: Class<*>) =
    RuntimeException::class.java.isAssignableFrom(clazz)

fun isError(clazz: Class<*>) =
    Error::class.java.isAssignableFrom(clazz)

fun isThread(clazz: Class<*>) =
    Thread::class.java.isAssignableFrom(clazz)

fun isObject(clazz: Class<*>) =
    clazz == Object::class.java

fun isComplexClass(clazz: Class<*>) =
    clazz.name.startsWith(pkgMyApp)

fun isJavaClass(clazz: Class<*>) =
    clazz.name.startsWith("java.")

fun isKotlinClass(clazz: Class<*>) =
    clazz.name.startsWith("kotlin.")

fun isAndroidClass(clazz: Class<*>) =
    clazz.name.startsWith("android.")

fun isMyAppClass(clazz: Class<*>) =
    clazz.name.startsWith(pkgMyApp)

fun isInnerClass(clazz: Class<*>) =
    clazz.enclosingClass != null

fun hasSuperclass(clazz: Class<*>) =
    clazz.superclass != null

fun hasInterfaces(clazz: Class<*>) =
    clazz.interfaces.isNotEmpty()

fun hasAnnotations(clazz: Class<*>) =
    clazz.annotations.isNotEmpty()

fun hasMethods(clazz: Class<*>) =
    clazz.declaredMethods.isNotEmpty()

fun hasFields(clazz: Class<*>) =
    clazz.declaredFields.isNotEmpty()

fun hasConstructors(clazz: Class<*>) =
    clazz.declaredConstructors.isNotEmpty()
