package com.unity3d.player;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;

final class ReflectionHelper
{
  protected static boolean LOG = false;
  protected static final boolean LOGV = false;
  private static a[] a = new a[4096];
  private static long b;

  private static float a(Class paramClass1, Class paramClass2)
  {
    if (paramClass1.equals(paramClass2))
      return 1.0F;
    if ((!paramClass1.isPrimitive()) && (!paramClass2.isPrimitive()));
    try
    {
      Class localClass = paramClass1.asSubclass(paramClass2);
      if (localClass != null)
        return 0.5F;
    }
    catch (ClassCastException localClassCastException)
    {
      try
      {
        while (true)
        {
          paramClass1 = paramClass2.asSubclass(paramClass1);
          if (paramClass1 != null)
            return 0.1F;
          label50: return 0.0F;
          localClassCastException = localClassCastException;
        }
      }
      catch (ClassCastException paramClass1)
      {
        break label50;
      }
    }
  }

  private static float a(Class paramClass, Class[] paramArrayOfClass1, Class[] paramArrayOfClass2)
  {
    if (paramArrayOfClass2.length == 0)
      return 0.1F;
    int i = 0;
    int j;
    if (paramArrayOfClass1 == null)
      j = 0;
    else
      j = paramArrayOfClass1.length;
    if (j + 1 != paramArrayOfClass2.length)
      return 0.0F;
    float f = 1.0F;
    if (paramArrayOfClass1 != null)
    {
      int k = paramArrayOfClass1.length;
      j = 0;
      f = 1.0F;
      while (i < k)
      {
        f *= a(paramArrayOfClass1[i], paramArrayOfClass2[j]);
        i++;
        j++;
      }
    }
    return f * a(paramClass, paramArrayOfClass2[(paramArrayOfClass2.length - 1)]);
  }

  private static Class a(String paramString, int[] paramArrayOfInt)
  {
    char c;
    while (true)
      if (paramArrayOfInt[0] < paramString.length())
      {
        int i = paramArrayOfInt[0];
        paramArrayOfInt[0] = (i + 1);
        c = paramString.charAt(i);
        if ((c == '(') || (c == ')'))
          continue;
        if (c != 'L')
          break;
        i = paramString.indexOf(';', paramArrayOfInt[0]);
        if (i != -1)
        {
          paramString = paramString.substring(paramArrayOfInt[0], i);
          paramArrayOfInt[0] = (i + 1);
          paramString = paramString.replace('/', '.');
        }
      }
    try
    {
      paramString = Class.forName(paramString);
      return paramString;
      if (c == 'Z')
        return Boolean.TYPE;
      if (c == 'I')
        return Integer.TYPE;
      if (c == 'F')
        return Float.TYPE;
      if (c == 'V')
        return Void.TYPE;
      if (c == 'B')
        return Byte.TYPE;
      if (c == 'C')
        return Character.TYPE;
      if (c == 'S')
        return Short.TYPE;
      if (c == 'J')
        return Long.TYPE;
      if (c == 'D')
        return Double.TYPE;
      if (c == '[')
        return Array.newInstance(a(paramString, paramArrayOfInt), 0).getClass();
      paramString = new StringBuilder("! parseType; ");
      paramString.append(c);
      paramString.append(" is not known!");
      f.Log(5, paramString.toString());
      label230: return null;
    }
    catch (java.lang.ClassNotFoundException paramString)
    {
      break label230;
    }
  }

  private static void a(a parama, Member paramMember)
  {
    monitorenter;
    try
    {
      parama.a = paramMember;
      a[(parama.hashCode() & a.length - 1)] = parama;
      monitorexit;
      return;
    }
    finally
    {
      parama = finally;
      monitorexit;
    }
    throw parama;
  }

  private static boolean a(a parama)
  {
    monitorenter;
    try
    {
      a locala = a[(parama.hashCode() & a.length - 1)];
      boolean bool = parama.equals(locala);
      if (!bool)
        return false;
      parama.a = locala.a;
      return true;
    }
    finally
    {
      monitorexit;
    }
    throw parama;
  }

  private static Class[] a(String paramString)
  {
    int[] arrayOfInt = new int[1];
    int i = 0;
    arrayOfInt[0] = 0;
    Object localObject = new ArrayList();
    while (arrayOfInt[0] < paramString.length())
    {
      Class localClass = a(paramString, arrayOfInt);
      if (localClass == null)
        break;
      ((ArrayList)localObject).add(localClass);
    }
    paramString = new Class[((ArrayList)localObject).size()];
    localObject = ((ArrayList)localObject).iterator();
    while (((Iterator)localObject).hasNext())
    {
      paramString[i] = ((Class)((Iterator)localObject).next());
      i++;
    }
    return (Class)paramString;
  }

  protected static void endUnityLaunch()
  {
    b += 1L;
  }

  protected static Constructor getConstructorID(Class paramClass, String paramString)
  {
    a locala = new a(paramClass, "", paramString);
    Object localObject1;
    if (a(locala))
    {
      localObject1 = (Constructor)locala.a;
    }
    else
    {
      Class[] arrayOfClass = a(paramString);
      float f1 = 0.0F;
      Constructor[] arrayOfConstructor = paramClass.getConstructors();
      int i = arrayOfConstructor.length;
      int j = 0;
      localObject2 = null;
      while (true)
      {
        localObject1 = localObject2;
        if (j >= i)
          break;
        localObject1 = arrayOfConstructor[j];
        float f2 = a(Void.TYPE, ((Constructor)localObject1).getParameterTypes(), arrayOfClass);
        float f3 = f1;
        if (f2 > f1)
        {
          localObject2 = localObject1;
          localObject1 = localObject2;
          if (f2 == 1.0F)
            break;
          f3 = f2;
        }
        j++;
        f1 = f3;
      }
      a(locala, (Member)localObject1);
    }
    if (localObject1 != null)
      return localObject1;
    Object localObject2 = new StringBuilder("<init>");
    ((StringBuilder)localObject2).append(paramString);
    ((StringBuilder)localObject2).append(" in class ");
    ((StringBuilder)localObject2).append(paramClass.getName());
    throw new NoSuchMethodError(((StringBuilder)localObject2).toString());
  }

  protected static Field getFieldID(Class paramClass, String paramString1, String paramString2, boolean paramBoolean)
  {
    Class localClass = paramClass;
    a locala = new a(localClass, paramString1, paramString2);
    Object localObject;
    if (a(locala))
    {
      localObject = (Field)locala.a;
    }
    else
    {
      Class[] arrayOfClass = a(paramString2);
      float f1 = 0.0F;
      paramClass = null;
      while (true)
      {
        localObject = paramClass;
        if (localClass == null)
          break;
        Field[] arrayOfField = localClass.getDeclaredFields();
        int i = arrayOfField.length;
        int j = 0;
        float f2;
        for (localObject = paramClass; ; localObject = paramClass)
        {
          f2 = f1;
          paramClass = (Class)localObject;
          if (j >= i)
            break;
          Field localField = arrayOfField[j];
          f2 = f1;
          paramClass = (Class)localObject;
          if (paramBoolean == Modifier.isStatic(localField.getModifiers()))
          {
            f2 = f1;
            paramClass = (Class)localObject;
            if (localField.getName().compareTo(paramString1) == 0)
            {
              float f3 = a(localField.getType(), null, arrayOfClass);
              f2 = f1;
              paramClass = (Class)localObject;
              if (f3 > f1)
              {
                paramClass = localField;
                if (f3 != 1.0F)
                {
                  f2 = f3;
                }
                else
                {
                  f2 = f3;
                  break;
                }
              }
            }
          }
          j++;
          f1 = f2;
        }
        localObject = paramClass;
        if (f2 == 1.0F)
          break;
        localObject = paramClass;
        if (localClass.isPrimitive())
          break;
        localObject = paramClass;
        if (localClass.isInterface())
          break;
        localObject = paramClass;
        if (localClass.equals(Object.class))
          break;
        localObject = paramClass;
        if (localClass.equals(Void.TYPE))
          break;
        localClass = localClass.getSuperclass();
        f1 = f2;
      }
      a(locala, (Member)localObject);
    }
    if (localObject == null)
    {
      if (paramBoolean)
        paramClass = "static";
      else
        paramClass = "non-static";
      throw new NoSuchFieldError(String.format("no %s field with name='%s' signature='%s' in class L%s;", new Object[] { paramClass, paramString1, paramString2, localClass.getName() }));
    }
    return (Field)localObject;
  }

  protected static String getFieldSignature(Field paramField)
  {
    Object localObject = paramField.getType();
    if (((Class)localObject).isPrimitive())
    {
      localObject = ((Class)localObject).getName();
      if ("boolean".equals(localObject))
        return "Z";
      if ("byte".equals(localObject))
        return "B";
      if ("char".equals(localObject))
        return "C";
      if ("double".equals(localObject))
        return "D";
      if ("float".equals(localObject))
        return "F";
      if ("int".equals(localObject))
        return "I";
      if ("long".equals(localObject))
        return "J";
      paramField = (Field)localObject;
      if ("short".equals(localObject))
        paramField = "S";
      return paramField;
    }
    if (((Class)localObject).isArray())
      return ((Class)localObject).getName().replace('.', '/');
    paramField = new StringBuilder("L");
    paramField.append(((Class)localObject).getName().replace('.', '/'));
    paramField.append(";");
    return (String)paramField.toString();
  }

  protected static Method getMethodID(Class paramClass, String paramString1, String paramString2, boolean paramBoolean)
  {
    a locala = new a(paramClass, paramString1, paramString2);
    Object localObject1;
    Object localObject2;
    if (a(locala))
    {
      localObject1 = (Method)locala.a;
    }
    else
    {
      Class[] arrayOfClass = a(paramString2);
      float f1 = 0.0F;
      localObject1 = null;
      localObject2 = paramClass;
      paramClass = (Class)localObject1;
      while (true)
      {
        localObject1 = paramClass;
        if (localObject2 == null)
          break;
        Method[] arrayOfMethod = ((Class)localObject2).getDeclaredMethods();
        int i = arrayOfMethod.length;
        int j = 0;
        float f2;
        for (localObject1 = paramClass; ; localObject1 = paramClass)
        {
          f2 = f1;
          paramClass = (Class)localObject1;
          if (j >= i)
            break;
          Method localMethod = arrayOfMethod[j];
          float f3 = f1;
          paramClass = (Class)localObject1;
          if (paramBoolean == Modifier.isStatic(localMethod.getModifiers()))
          {
            f3 = f1;
            paramClass = (Class)localObject1;
            if (localMethod.getName().compareTo(paramString1) == 0)
            {
              f2 = a(localMethod.getReturnType(), localMethod.getParameterTypes(), arrayOfClass);
              f3 = f1;
              paramClass = (Class)localObject1;
              if (f2 > f1)
              {
                paramClass = localMethod;
                if (f2 != 1.0F)
                  f3 = f2;
                else
                  break;
              }
            }
          }
          j++;
          f1 = f3;
        }
        localObject1 = paramClass;
        if (f2 == 1.0F)
          break;
        localObject1 = paramClass;
        if (((Class)localObject2).isPrimitive())
          break;
        localObject1 = paramClass;
        if (((Class)localObject2).isInterface())
          break;
        localObject1 = paramClass;
        if (localObject2.equals(Object.class))
          break;
        localObject1 = paramClass;
        if (localObject2.equals(Void.TYPE))
          break;
        localObject2 = ((Class)localObject2).getSuperclass();
        f1 = f2;
      }
      a(locala, (Member)localObject1);
      paramClass = (Class)localObject2;
    }
    if (localObject1 == null)
    {
      if (paramBoolean)
        localObject2 = "static";
      else
        localObject2 = "non-static";
      throw new NoSuchMethodError(String.format("no %s method with name='%s' signature='%s' in class L%s;", new Object[] { localObject2, paramString1, paramString2, paramClass.getName() }));
    }
    return (Method)(Method)localObject1;
  }

  private static native void nativeProxyFinalize(long paramLong);

  private static native Object nativeProxyInvoke(long paramLong, String paramString, Object[] paramArrayOfObject);

  private static native void nativeProxyLogJNIInvokeException(long paramLong);

  protected static Object newProxyInstance(long paramLong, Class paramClass)
  {
    return newProxyInstance(paramLong, new Class[] { paramClass });
  }

  protected static Object newProxyInstance(long paramLong, Class[] paramArrayOfClass)
  {
    return Proxy.newProxyInstance(ReflectionHelper.class.getClassLoader(), paramArrayOfClass, new b(paramLong, paramArrayOfClass)
    {
      private long c = ReflectionHelper.a();
      private long d;
      private boolean e;

      private Object a(Object paramObject, Method paramMethod, Object[] paramArrayOfObject)
      {
        Object[] arrayOfObject = paramArrayOfObject;
        if (paramArrayOfObject == null);
        try
        {
          arrayOfObject = new Object[0];
          Class localClass = paramMethod.getDeclaringClass();
          paramArrayOfObject = MethodHandles.Lookup.class.getDeclaredConstructor(new Class[] { Class.class, Integer.TYPE });
          paramArrayOfObject.setAccessible(true);
          paramObject = ((MethodHandles.Lookup)paramArrayOfObject.newInstance(new Object[] { localClass, Integer.valueOf(2) })).in(localClass).unreflectSpecial(paramMethod, localClass).bindTo(paramObject).invokeWithArguments(arrayOfObject);
          return paramObject;
        }
        catch (java.lang.NoClassDefFoundError paramObject)
        {
          f.Log(6, String.format("Java interface default methods are only supported since Android Oreo", new Object[0]));
          ReflectionHelper.a(this.d);
        }
        return null;
      }

      public final void a(long paramLong, boolean paramBoolean)
      {
        this.d = paramLong;
        this.e = paramBoolean;
      }

      protected final void finalize()
      {
        try
        {
          if (this.c == ReflectionHelper.a())
            ReflectionHelper.b(this.a);
          return;
        }
        finally
        {
          super.finalize();
        }
        throw localObject;
      }

      public final Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject)
      {
        if (this.c != ReflectionHelper.a())
        {
          f.Log(6, "Scripting proxy object was destroyed, because Unity player was unloaded.");
          return null;
        }
        this.d = 0L;
        this.e = false;
        Object localObject = ReflectionHelper.a(this.a, paramMethod.getName(), paramArrayOfObject);
        long l;
        if (this.e)
        {
          if ((paramMethod.getModifiers() & 0x400) == 0)
            return a(paramObject, paramMethod, paramArrayOfObject);
          l = this.d;
        }
        else
        {
          l = this.d;
          if (l == 0L)
            break label97;
        }
        ReflectionHelper.a(l);
        label97: return localObject;
      }
    });
  }

  protected static void setNativeExceptionOnProxy(Object paramObject, long paramLong, boolean paramBoolean)
  {
    ((b)Proxy.getInvocationHandler(paramObject)).a(paramLong, paramBoolean);
  }

  private static final class a
  {
    public volatile Member a;
    private final Class b;
    private final String c;
    private final String d;
    private final int e;

    a(Class paramClass, String paramString1, String paramString2)
    {
      this.b = paramClass;
      this.c = paramString1;
      this.d = paramString2;
      this.e = (((paramClass.hashCode() + 527) * 31 + this.c.hashCode()) * 31 + this.d.hashCode());
    }

    public final boolean equals(Object paramObject)
    {
      if (paramObject == this)
        return true;
      if ((paramObject instanceof a))
      {
        paramObject = (a)paramObject;
        if ((this.e == paramObject.e) && (this.d.equals(paramObject.d)) && (this.c.equals(paramObject.c)) && (this.b.equals(paramObject.b)))
          return true;
      }
      return false;
    }

    public final int hashCode()
    {
      return this.e;
    }
  }

  protected static abstract interface b extends InvocationHandler
  {
    public abstract void a(long paramLong, boolean paramBoolean);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.ReflectionHelper
 * JD-Core Version:    0.6.0
 */