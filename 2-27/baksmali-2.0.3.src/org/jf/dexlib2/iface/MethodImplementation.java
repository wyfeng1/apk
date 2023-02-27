package org.jf.dexlib2.iface;

import java.util.List;
import org.jf.dexlib2.iface.debug.DebugItem;
import org.jf.dexlib2.iface.instruction.Instruction;

public abstract interface MethodImplementation
{
  public abstract int getRegisterCount();

  public abstract Iterable<? extends Instruction> getInstructions();

  public abstract List<? extends TryBlock<? extends ExceptionHandler>> getTryBlocks();

  public abstract Iterable<? extends DebugItem> getDebugItems();
}

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.iface.MethodImplementation
 * JD-Core Version:    0.6.0
 */