package com.unity3d.player;

import I;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.DisplayMetrics;
import android.view.View;

public final class l extends View
{
  final int a;
  final int b;
  Bitmap c;
  Bitmap d;

  public l(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.a = paramInt;
    paramInt = getResources().getIdentifier("unity_static_splash", "drawable", getContext().getPackageName());
    this.b = paramInt;
    if (paramInt != 0)
      forceLayout();
  }

  public final void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    Bitmap localBitmap = this.c;
    if (localBitmap != null)
    {
      localBitmap.recycle();
      this.c = null;
    }
    localBitmap = this.d;
    if (localBitmap != null)
    {
      localBitmap.recycle();
      this.d = null;
    }
  }

  public final void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.b == 0)
      return;
    Object localObject;
    if (this.c == null)
    {
      localObject = new BitmapFactory.Options();
      ((BitmapFactory.Options)localObject).inScaled = false;
      this.c = BitmapFactory.decodeResource(getResources(), this.b, (BitmapFactory.Options)localObject);
    }
    paramInt4 = this.c.getWidth();
    paramInt3 = this.c.getHeight();
    paramInt2 = getWidth();
    int i = getHeight();
    if ((paramInt2 != 0) && (i != 0))
    {
      float f1 = paramInt4 / paramInt3;
      float f2 = paramInt2;
      float f3 = i;
      if (f2 / f3 <= f1)
        paramInt1 = 1;
      else
        paramInt1 = 0;
      int j = 1.a[(this.a - 1)];
      if (j != 1)
      {
        if ((j != 2) && (j != 3))
        {
          paramInt1 = paramInt4;
          paramInt2 = paramInt3;
          break label244;
        }
        if (this.a == a.c)
          paramInt3 = 1;
        else
          paramInt3 = 0;
        if ((paramInt3 ^ paramInt1) != 0)
        {
          paramInt3 = (int)(f2 / f1);
          paramInt1 = paramInt2;
          paramInt2 = paramInt3;
          break label244;
        }
      }
      else
      {
        paramInt1 = paramInt4;
        if (paramInt2 < paramInt4)
        {
          paramInt3 = (int)(f2 / f1);
          paramInt1 = paramInt2;
        }
        paramInt2 = paramInt3;
        if (i >= paramInt3)
          break label244;
      }
      paramInt1 = (int)(f3 * f1);
      paramInt2 = i;
      label244: localObject = this.d;
      if (localObject != null)
      {
        if ((((Bitmap)localObject).getWidth() == paramInt1) && (this.d.getHeight() == paramInt2))
          return;
        localObject = this.d;
        if (localObject != this.c)
        {
          ((Bitmap)localObject).recycle();
          this.d = null;
        }
      }
      localObject = Bitmap.createScaledBitmap(this.c, paramInt1, paramInt2, true);
      this.d = ((Bitmap)localObject);
      ((Bitmap)localObject).setDensity(getResources().getDisplayMetrics().densityDpi);
      ColorDrawable localColorDrawable = new ColorDrawable(-16777216);
      localObject = new BitmapDrawable(getResources(), this.d);
      ((BitmapDrawable)localObject).setGravity(17);
      setBackground(new LayerDrawable(new Drawable[] { localColorDrawable, localObject }));
    }
  }

  static enum a
  {
    public static int[] a()
    {
      return (int[])d.clone();
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.l
 * JD-Core Version:    0.6.0
 */