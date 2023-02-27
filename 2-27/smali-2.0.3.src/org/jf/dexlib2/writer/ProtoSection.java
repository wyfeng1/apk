package org.jf.dexlib2.writer;

public abstract interface ProtoSection<StringKey, TypeKey, ProtoKey, TypeListKey> extends IndexSection<ProtoKey>
{
  public abstract StringKey getShorty(ProtoKey paramProtoKey);

  public abstract TypeKey getReturnType(ProtoKey paramProtoKey);

  public abstract TypeListKey getParameters(ProtoKey paramProtoKey);
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.ProtoSection
 * JD-Core Version:    0.6.0
 */