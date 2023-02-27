package org.jf.dexlib2.writer;

import java.util.Collection;
import org.jf.dexlib2.iface.Annotation;

public abstract interface AnnotationSetSection<AnnotationKey extends Annotation, AnnotationSetKey> extends NullableOffsetSection<AnnotationSetKey>
{
  public abstract Collection<? extends AnnotationKey> getAnnotations(AnnotationSetKey paramAnnotationSetKey);
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.AnnotationSetSection
 * JD-Core Version:    0.6.0
 */