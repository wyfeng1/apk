package org.jf.dexlib2.writer;

import java.util.Collection;

public abstract interface AnnotationSection<StringKey, TypeKey, AnnotationKey, AnnotationElement, EncodedValue> extends OffsetSection<AnnotationKey>
{
  public abstract int getVisibility(AnnotationKey paramAnnotationKey);

  public abstract TypeKey getType(AnnotationKey paramAnnotationKey);

  public abstract Collection<? extends AnnotationElement> getElements(AnnotationKey paramAnnotationKey);

  public abstract StringKey getElementName(AnnotationElement paramAnnotationElement);

  public abstract EncodedValue getElementValue(AnnotationElement paramAnnotationElement);
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.AnnotationSection
 * JD-Core Version:    0.6.0
 */