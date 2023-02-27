package org.jf.dexlib2.iface.instruction;

public abstract interface FiveRegisterInstruction extends VariableRegisterInstruction
{
  public abstract int getRegisterC();

  public abstract int getRegisterD();

  public abstract int getRegisterE();

  public abstract int getRegisterF();

  public abstract int getRegisterG();
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.iface.instruction.FiveRegisterInstruction
 * JD-Core Version:    0.6.0
 */