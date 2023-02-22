package bitter.jnibridge;

import java.io.PrintStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JNIBridge
{
  static native void delete(long paramLong);

  static void disableInterfaceProxy(Object paramObject)
  {
    if (paramObject != null)
      ((a)Proxy.getInvocationHandler(paramObject)).a();
  }

  static native Object invoke(long paramLong, Class paramClass, Method paramMethod, Object[] paramArrayOfObject);

  static Object newInterfaceProxy(long paramLong, Class[] paramArrayOfClass)
  {
    return Proxy.newProxyInstance(JNIBridge.class.getClassLoader(), paramArrayOfClass, new a(paramLong));
  }

  private static final class a
    implements InvocationHandler
  {
    private Object a = new Object[0];
    private long b;
    private Constructor c;

    public a(long paramLong)
    {
      this.b = paramLong;
      try
      {
        Constructor localConstructor = MethodHandles.Lookup.class.getDeclaredConstructor(new Class[] { Class.class, Integer.TYPE });
        this.c = localConstructor;
        localConstructor.setAccessible(true);
        return;
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        this.c = null;
        return;
      }
      catch (NoClassDefFoundError localNoClassDefFoundError)
      {
        this.c = null;
      }
    }

    private Object a(Object paramObject, Method paramMethod, Object[] paramArrayOfObject)
    {
      Object[] arrayOfObject = paramArrayOfObject;
      if (paramArrayOfObject == null)
        arrayOfObject = new Object[0];
      paramArrayOfObject = paramMethod.getDeclaringClass();
      return ((MethodHandles.Lookup)this.c.newInstance(new Object[] { paramArrayOfObject, Integer.valueOf(2) })).in(paramArrayOfObject).unreflectSpecial(paramMethod, paramArrayOfObject).bindTo(paramObject).invokeWithArguments(arrayOfObject);
    }

    public final void a()
    {
      synchronized (this.a)
      {
        this.b = 0L;
        return;
      }
    }

    public final void finalize()
    {
      synchronized (this.a)
      {
        if (this.b == 0L)
          return;
        JNIBridge.delete(this.b);
        return;
      }
    }

    public final Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject)
    {
      synchronized (this.a)
      {
        if (this.b == 0L)
          return null;
        try
        {
          Object localObject2 = JNIBridge.invoke(this.b, paramMethod.getDeclaringClass(), paramMethod, paramArrayOfObject);
          return localObject2;
        }
        catch (NoSuchMethodError localNoSuchMethodError)
        {
          if (this.c != null)
          {
            if ((paramMethod.getModifiers() & 0x400) == 0)
            {
              paramObject = a(paramObject, paramMethod, paramArrayOfObject);
              return paramObject;
            }
            throw localNoSuchMethodError;
          }
          System.err.println("JNIBridge error: Java interface default methods are only supported since Android Oreo");
          throw localNoSuchMethodError;
        }
      }
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     bitter.jnibridge.JNIBridge
 * JD-Core Version:    0.6.0
 */