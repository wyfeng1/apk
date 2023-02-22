package com.unity3d.splash.services.core.misc;

import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import com.unity3d.splash.services.core.log.DeviceLog;
import java.lang.reflect.Method;

public class ViewUtilities
{
  public static void removeViewFromParent(View paramView)
  {
    if ((paramView != null) && (paramView.getParent() != null))
      try
      {
        ((ViewGroup)paramView.getParent()).removeView(paramView);
        return;
      }
      catch (java.lang.Exception paramView)
      {
        DeviceLog.exception("Error while removing view from it's parent", paramView);
      }
  }

  public static void setBackground(View paramView, Drawable paramDrawable)
  {
    String str;
    if (Build.VERSION.SDK_INT < 16)
      str = "setBackgroundDrawable";
    else
      str = "setBackground";
    try
    {
      View.class.getMethod(str, new Class[] { Drawable.class }).invoke(paramView, new Object[] { paramDrawable });
      return;
    }
    catch (java.lang.Exception paramDrawable)
    {
      paramView = new StringBuilder("Couldn't run");
      paramView.append(str);
      DeviceLog.exception(paramView.toString(), paramDrawable);
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.misc.ViewUtilities
 * JD-Core Version:    0.6.0
 */