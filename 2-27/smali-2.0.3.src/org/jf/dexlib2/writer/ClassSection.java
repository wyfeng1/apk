package org.jf.dexlib2.writer;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import org.jf.dexlib2.builder.MutableMethodImplementation;
import org.jf.dexlib2.iface.ExceptionHandler;
import org.jf.dexlib2.iface.TryBlock;
import org.jf.dexlib2.iface.debug.DebugItem;
import org.jf.dexlib2.iface.instruction.Instruction;

public abstract interface ClassSection<StringKey extends CharSequence, TypeKey extends CharSequence, TypeListKey, ClassKey, FieldKey, MethodKey, AnnotationSetKey, EncodedValue> extends IndexSection<ClassKey>
{
  public abstract Collection<? extends ClassKey> getSortedClasses();

  public abstract Map.Entry<? extends ClassKey, Integer> getClassEntryByType(TypeKey paramTypeKey);

  public abstract TypeKey getType(ClassKey paramClassKey);

  public abstract int getAccessFlags(ClassKey paramClassKey);

  public abstract TypeKey getSuperclass(ClassKey paramClassKey);

  public abstract TypeListKey getSortedInterfaces(ClassKey paramClassKey);

  public abstract StringKey getSourceFile(ClassKey paramClassKey);

  public abstract Collection<? extends EncodedValue> getStaticInitializers(ClassKey paramClassKey);

  public abstract Collection<? extends FieldKey> getSortedStaticFields(ClassKey paramClassKey);

  public abstract Collection<? extends FieldKey> getSortedInstanceFields(ClassKey paramClassKey);

  public abstract Collection<? extends FieldKey> getSortedFields(ClassKey paramClassKey);

  public abstract Collection<? extends MethodKey> getSortedDirectMethods(ClassKey paramClassKey);

  public abstract Collection<? extends MethodKey> getSortedVirtualMethods(ClassKey paramClassKey);

  public abstract Collection<? extends MethodKey> getSortedMethods(ClassKey paramClassKey);

  public abstract int getFieldAccessFlags(FieldKey paramFieldKey);

  public abstract int getMethodAccessFlags(MethodKey paramMethodKey);

  public abstract AnnotationSetKey getClassAnnotations(ClassKey paramClassKey);

  public abstract AnnotationSetKey getFieldAnnotations(FieldKey paramFieldKey);

  public abstract AnnotationSetKey getMethodAnnotations(MethodKey paramMethodKey);

  public abstract List<? extends AnnotationSetKey> getParameterAnnotations(MethodKey paramMethodKey);

  public abstract Iterable<? extends DebugItem> getDebugItems(MethodKey paramMethodKey);

  public abstract Iterable<? extends StringKey> getParameterNames(MethodKey paramMethodKey);

  public abstract int getRegisterCount(MethodKey paramMethodKey);

  public abstract Iterable<? extends Instruction> getInstructions(MethodKey paramMethodKey);

  public abstract List<? extends TryBlock<? extends ExceptionHandler>> getTryBlocks(MethodKey paramMethodKey);

  public abstract TypeKey getExceptionType(ExceptionHandler paramExceptionHandler);

  public abstract MutableMethodImplementation makeMutableMethodImplementation(MethodKey paramMethodKey);

  public abstract void setEncodedArrayOffset(ClassKey paramClassKey, int paramInt);

  public abstract int getEncodedArrayOffset(ClassKey paramClassKey);

  public abstract void setAnnotationDirectoryOffset(ClassKey paramClassKey, int paramInt);

  public abstract int getAnnotationDirectoryOffset(ClassKey paramClassKey);

  public abstract void setAnnotationSetRefListOffset(MethodKey paramMethodKey, int paramInt);

  public abstract int getAnnotationSetRefListOffset(MethodKey paramMethodKey);

  public abstract void setCodeItemOffset(MethodKey paramMethodKey, int paramInt);

  public abstract int getCodeItemOffset(MethodKey paramMethodKey);

  public abstract void writeDebugItem(DebugWriter<StringKey, TypeKey> paramDebugWriter, DebugItem paramDebugItem)
    throws IOException;
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.ClassSection
 * JD-Core Version:    0.6.0
 */