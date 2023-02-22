package com.unity3d.player;

import android.graphics.BitmapFactory;
import org.json.JSONArray;
import org.json.JSONObject;

public final class j
{
  private String a;
  private String b;
  private String[] c;
  private String[] d;
  private int e;
  private long f;
  private String[] g;
  private String[] h;
  private String i;
  private String j;
  private String[] k;

  public j(JSONObject paramJSONObject)
  {
    this.a = paramJSONObject.optString("imageUrl");
    this.b = paramJSONObject.optString("clickUrl", "");
    this.e = paramJSONObject.optInt("duration", 5);
    this.f = paramJSONObject.optLong("expiration", 0L);
    this.c = a(paramJSONObject.optJSONArray("impression"));
    this.d = a(paramJSONObject.optJSONArray("clickImpression"));
    this.g = a(paramJSONObject.optJSONArray("primaryClickImpression"));
    this.h = a(paramJSONObject.optJSONArray("fallbackClickImpression"));
    this.i = paramJSONObject.optString("mediaType");
    this.j = paramJSONObject.optString("videoUrl");
    this.k = a(paramJSONObject.optJSONArray("completeClickImpression"));
  }

  private static String[] a(JSONArray paramJSONArray)
  {
    if (paramJSONArray == null)
      return null;
    int m = paramJSONArray.length();
    String[] arrayOfString = new String[m];
    for (int n = 0; n < m; n++)
      arrayOfString[n] = paramJSONArray.optString(n);
    return arrayOfString;
  }

  public final boolean a()
  {
    if ("VIDEO".equals(j()))
    {
      if ((k() == null) || (k() == ""))
        return false;
    }
    else
    {
      String str1 = b();
      if ((str1 == null) || (str1 == ""))
        break label77;
      String str2 = str1;
      if (str1.startsWith("file://"))
        str2 = str1.substring(7);
      if (BitmapFactory.decodeFile(str2) == null)
        return false;
    }
    return true;
    label77: return false;
  }

  public final String b()
  {
    return this.a;
  }

  public final String c()
  {
    return this.b;
  }

  public final String[] d()
  {
    return this.c;
  }

  public final String[] e()
  {
    return this.d;
  }

  public final int f()
  {
    return this.e;
  }

  public final long g()
  {
    return this.f;
  }

  public final String[] h()
  {
    return this.g;
  }

  public final String[] i()
  {
    return this.h;
  }

  public final String j()
  {
    return this.i;
  }

  public final String k()
  {
    return this.j;
  }

  public final String[] l()
  {
    return this.k;
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.j
 * JD-Core Version:    0.6.0
 */