package com.unity3d.player;

final class o
{
  private static boolean a = false;
  private boolean b = false;
  private boolean c = false;
  private boolean d = true;
  private boolean e = false;

  static void a()
  {
    a = true;
  }

  static void b()
  {
    a = false;
  }

  static boolean c()
  {
    return a;
  }

  final void a(boolean paramBoolean)
  {
    this.b = paramBoolean;
  }

  final void b(boolean paramBoolean)
  {
    this.d = paramBoolean;
  }

  final void c(boolean paramBoolean)
  {
    this.e = paramBoolean;
  }

  final void d(boolean paramBoolean)
  {
    this.c = paramBoolean;
  }

  final boolean d()
  {
    return this.d;
  }

  final boolean e()
  {
    return this.e;
  }

  final boolean e(boolean paramBoolean)
  {
    return (a) && ((paramBoolean) || (this.b)) && (!this.d) && (!this.c);
  }

  final boolean f()
  {
    return this.c;
  }

  public final String toString()
  {
    return super.toString();
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.o
 * JD-Core Version:    0.6.0
 */