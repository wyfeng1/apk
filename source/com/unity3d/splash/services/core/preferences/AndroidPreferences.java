package com.unity3d.splash.services.core.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.properties.ClientProperties;

public class AndroidPreferences
{
  public static Boolean getBoolean(String paramString1, String paramString2)
  {
    paramString1 = ClientProperties.getApplicationContext().getSharedPreferences(paramString1, 0);
    if ((paramString1 != null) && (paramString1.contains(paramString2)))
      try
      {
        boolean bool = paramString1.getBoolean(paramString2, false);
        return Boolean.valueOf(bool);
      }
      catch (ClassCastException localClassCastException)
      {
        paramString1 = new StringBuilder("Unity Ads failed to cast ");
        paramString1.append(paramString2);
        paramString1.append(": ");
        paramString1.append(localClassCastException.getMessage());
        DeviceLog.error(paramString1.toString());
      }
    return null;
  }

  public static Float getFloat(String paramString1, String paramString2)
  {
    paramString1 = ClientProperties.getApplicationContext().getSharedPreferences(paramString1, 0);
    if ((paramString1 != null) && (paramString1.contains(paramString2)))
      try
      {
        float f = paramString1.getFloat(paramString2, (0.0F / 0.0F));
        return Float.valueOf(f);
      }
      catch (ClassCastException paramString1)
      {
        StringBuilder localStringBuilder = new StringBuilder("Unity Ads failed to cast ");
        localStringBuilder.append(paramString2);
        localStringBuilder.append(": ");
        localStringBuilder.append(paramString1.getMessage());
        DeviceLog.error(localStringBuilder.toString());
      }
    return null;
  }

  public static Integer getInteger(String paramString1, String paramString2)
  {
    paramString1 = ClientProperties.getApplicationContext().getSharedPreferences(paramString1, 0);
    if ((paramString1 != null) && (paramString1.contains(paramString2)))
      try
      {
        int i = paramString1.getInt(paramString2, -1);
        return Integer.valueOf(i);
      }
      catch (ClassCastException localClassCastException)
      {
        paramString1 = new StringBuilder("Unity Ads failed to cast ");
        paramString1.append(paramString2);
        paramString1.append(": ");
        paramString1.append(localClassCastException.getMessage());
        DeviceLog.error(paramString1.toString());
      }
    return null;
  }

  public static Long getLong(String paramString1, String paramString2)
  {
    paramString1 = ClientProperties.getApplicationContext().getSharedPreferences(paramString1, 0);
    if ((paramString1 != null) && (paramString1.contains(paramString2)))
      try
      {
        long l = paramString1.getLong(paramString2, -1L);
        return Long.valueOf(l);
      }
      catch (ClassCastException paramString1)
      {
        StringBuilder localStringBuilder = new StringBuilder("Unity Ads failed to cast ");
        localStringBuilder.append(paramString2);
        localStringBuilder.append(": ");
        localStringBuilder.append(paramString1.getMessage());
        DeviceLog.error(localStringBuilder.toString());
      }
    return null;
  }

  public static String getString(String paramString1, String paramString2)
  {
    paramString1 = ClientProperties.getApplicationContext().getSharedPreferences(paramString1, 0);
    if ((paramString1 != null) && (paramString1.contains(paramString2)))
      try
      {
        paramString1 = paramString1.getString(paramString2, "");
        return paramString1;
      }
      catch (ClassCastException paramString1)
      {
        StringBuilder localStringBuilder = new StringBuilder("Unity Ads failed to cast ");
        localStringBuilder.append(paramString2);
        localStringBuilder.append(": ");
        localStringBuilder.append(paramString1.getMessage());
        DeviceLog.error(localStringBuilder.toString());
      }
    return null;
  }

  public static boolean hasKey(String paramString1, String paramString2)
  {
    paramString1 = ClientProperties.getApplicationContext().getSharedPreferences(paramString1, 0);
    return (paramString1 != null) && (paramString1.contains(paramString2));
  }

  public static void removeKey(String paramString1, String paramString2)
  {
    paramString1 = ClientProperties.getApplicationContext().getSharedPreferences(paramString1, 0);
    if (paramString1 != null)
    {
      paramString1 = paramString1.edit();
      paramString1.remove(paramString2);
      paramString1.commit();
    }
  }

  public static void setBoolean(String paramString1, String paramString2, Boolean paramBoolean)
  {
    paramString1 = ClientProperties.getApplicationContext().getSharedPreferences(paramString1, 0);
    if (paramString1 != null)
    {
      paramString1 = paramString1.edit();
      paramString1.putBoolean(paramString2, paramBoolean.booleanValue());
      paramString1.commit();
    }
  }

  public static void setFloat(String paramString1, String paramString2, Double paramDouble)
  {
    paramString1 = ClientProperties.getApplicationContext().getSharedPreferences(paramString1, 0);
    if (paramString1 != null)
    {
      paramString1 = paramString1.edit();
      paramString1.putFloat(paramString2, paramDouble.floatValue());
      paramString1.commit();
    }
  }

  public static void setInteger(String paramString1, String paramString2, Integer paramInteger)
  {
    paramString1 = ClientProperties.getApplicationContext().getSharedPreferences(paramString1, 0);
    if (paramString1 != null)
    {
      paramString1 = paramString1.edit();
      paramString1.putInt(paramString2, paramInteger.intValue());
      paramString1.commit();
    }
  }

  public static void setLong(String paramString1, String paramString2, Long paramLong)
  {
    paramString1 = ClientProperties.getApplicationContext().getSharedPreferences(paramString1, 0);
    if (paramString1 != null)
    {
      paramString1 = paramString1.edit();
      paramString1.putLong(paramString2, paramLong.longValue());
      paramString1.commit();
    }
  }

  public static void setString(String paramString1, String paramString2, String paramString3)
  {
    paramString1 = ClientProperties.getApplicationContext().getSharedPreferences(paramString1, 0);
    if (paramString1 != null)
    {
      paramString1 = paramString1.edit();
      paramString1.putString(paramString2, paramString3);
      paramString1.commit();
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.preferences.AndroidPreferences
 * JD-Core Version:    0.6.0
 */