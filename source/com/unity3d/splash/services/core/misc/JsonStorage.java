package com.unity3d.splash.services.core.misc;

import android.text.TextUtils;
import com.unity3d.splash.services.core.log.DeviceLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public class JsonStorage
{
  private JSONObject _data;

  private void createObjectTree(String paramString)
  {
    monitorenter;
    try
    {
      String[] arrayOfString = paramString.split("\\.");
      Object localObject = this._data;
      int i = paramString.length();
      if (i == 0)
        return;
      i = 0;
      paramString = (String)localObject;
      while (i < arrayOfString.length)
      {
        boolean bool = paramString.has(arrayOfString[i]);
        String str;
        if (!bool)
        {
          str = arrayOfString[i];
          localObject = paramString;
          try
          {
            JSONObject localJSONObject = new org/json/JSONObject;
            localObject = paramString;
            localJSONObject.<init>();
            localObject = paramString;
            paramString = paramString.put(str, localJSONObject);
            localObject = paramString;
            paramString = paramString.getJSONObject(arrayOfString[i]);
          }
          catch (Exception localException1)
          {
            str = "Couldn't create new JSONObject";
            paramString = (String)localObject;
          }
        }
        else
        {
          while (true)
          {
            DeviceLog.exception(str, localException1);
            break;
            try
            {
              localObject = paramString.getJSONObject(arrayOfString[i]);
              paramString = (String)localObject;
            }
            catch (Exception localException2)
            {
              str = "Couldn't get existing JSONObject";
            }
          }
        }
        i++;
      }
      return;
    }
    finally
    {
      monitorexit;
    }
    throw paramString;
  }

  private Object findObject(String paramString)
  {
    monitorenter;
    try
    {
      String[] arrayOfString = paramString.split("\\.");
      JSONObject localJSONObject = this._data;
      int i = paramString.length();
      if (i == 0)
        return localJSONObject;
      i = 0;
      paramString = localJSONObject;
      while (true)
      {
        if (i >= arrayOfString.length)
          break label107;
        boolean bool = paramString.has(arrayOfString[i]);
        if (!bool)
          break;
        try
        {
          paramString = paramString.getJSONObject(arrayOfString[i]);
          i++;
        }
        catch (Exception localException)
        {
          paramString = new java/lang/StringBuilder;
          paramString.<init>("Couldn't read JSONObject: ");
          paramString.append(arrayOfString[i]);
          DeviceLog.exception(paramString.toString(), localException);
          monitorexit;
          return null;
        }
      }
      return null;
      label107: return paramString;
    }
    finally
    {
      monitorexit;
    }
    throw paramString;
  }

  private String getParentObjectTreeFor(String paramString)
  {
    monitorenter;
    try
    {
      String[] arrayOfString = paramString.split("\\.");
      paramString = new java/util/ArrayList;
      paramString.<init>(Arrays.asList(arrayOfString));
      paramString.remove(paramString.size() - 1);
      paramString = TextUtils.join(".", paramString.toArray());
      monitorexit;
      return paramString;
    }
    finally
    {
      paramString = finally;
      monitorexit;
    }
    throw paramString;
  }

  public void clearData()
  {
    monitorenter;
    try
    {
      this._data = null;
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public boolean delete(String paramString)
  {
    monitorenter;
    try
    {
      if (this._data == null)
      {
        DeviceLog.error("Data is NULL, readStorage probably not called");
        return false;
      }
      String[] arrayOfString = paramString.split("\\.");
      if ((findObject(getParentObjectTreeFor(paramString)) instanceof JSONObject))
      {
        paramString = (JSONObject)findObject(getParentObjectTreeFor(paramString));
        if (paramString != null)
        {
          paramString = paramString.remove(arrayOfString[(arrayOfString.length - 1)]);
          if (paramString != null)
            return true;
        }
      }
      return false;
    }
    finally
    {
      monitorexit;
    }
    throw paramString;
  }

  public Object get(String paramString)
  {
    monitorenter;
    try
    {
      Object localObject1 = this._data;
      Object localObject2 = null;
      if (localObject1 == null)
      {
        DeviceLog.error("Data is NULL, readStorage probably not called");
        return null;
      }
      localObject1 = paramString.split("\\.");
      if ((findObject(getParentObjectTreeFor(paramString)) instanceof JSONObject))
      {
        JSONObject localJSONObject = (JSONObject)findObject(getParentObjectTreeFor(paramString));
        if (localJSONObject != null)
        {
          paramString = localObject2;
          try
          {
            if (localJSONObject.has(localObject1[(localObject1.length - 1)]))
              paramString = localJSONObject.get(localObject1[(localObject1.length - 1)]);
          }
          catch (Exception paramString)
          {
            DeviceLog.exception("Error getting data", paramString);
            paramString = localObject2;
          }
          return paramString;
        }
      }
      return null;
    }
    finally
    {
      monitorexit;
    }
    throw paramString;
  }

  public JSONObject getData()
  {
    monitorenter;
    try
    {
      JSONObject localJSONObject = this._data;
      monitorexit;
      return localJSONObject;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public List getKeys(String paramString, boolean paramBoolean)
  {
    monitorenter;
    try
    {
      if ((get(paramString) instanceof JSONObject))
      {
        Object localObject = (JSONObject)get(paramString);
        ArrayList localArrayList = new java/util/ArrayList;
        localArrayList.<init>();
        if (localObject != null)
        {
          Iterator localIterator1 = ((JSONObject)localObject).keys();
          while (localIterator1.hasNext())
          {
            String str1 = (String)localIterator1.next();
            if (paramBoolean)
            {
              localObject = new java/lang/StringBuilder;
              ((StringBuilder)localObject).<init>();
              ((StringBuilder)localObject).append(paramString);
              ((StringBuilder)localObject).append(".");
              ((StringBuilder)localObject).append(str1);
              localObject = getKeys(((StringBuilder)localObject).toString(), paramBoolean);
            }
            else
            {
              localObject = null;
            }
            localArrayList.add(str1);
            if (localObject == null)
              continue;
            Iterator localIterator2 = ((List)localObject).iterator();
            while (localIterator2.hasNext())
            {
              String str2 = (String)localIterator2.next();
              localObject = new java/lang/StringBuilder;
              ((StringBuilder)localObject).<init>();
              ((StringBuilder)localObject).append(str1);
              ((StringBuilder)localObject).append(".");
              ((StringBuilder)localObject).append(str2);
              localArrayList.add(((StringBuilder)localObject).toString());
            }
          }
        }
        return localArrayList;
      }
      return null;
    }
    finally
    {
      monitorexit;
    }
    throw paramString;
  }

  public boolean hasData()
  {
    monitorenter;
    try
    {
      if (this._data != null)
      {
        int i = this._data.length();
        if (i > 0)
        {
          j = 1;
          return j;
        }
      }
      int j = 0;
    }
    finally
    {
      monitorexit;
    }
  }

  public boolean initData()
  {
    monitorenter;
    try
    {
      if (this._data == null)
      {
        JSONObject localJSONObject = new org/json/JSONObject;
        localJSONObject.<init>();
        this._data = localJSONObject;
        i = 1;
        return i;
      }
      int i = 0;
    }
    finally
    {
      monitorexit;
    }
  }

  public boolean set(String paramString, Object paramObject)
  {
    monitorenter;
    try
    {
      if ((this._data != null) && (paramString != null) && (paramString.length() != 0) && (paramObject != null))
      {
        createObjectTree(getParentObjectTreeFor(paramString));
        if ((findObject(getParentObjectTreeFor(paramString)) instanceof JSONObject))
        {
          localObject = (JSONObject)findObject(getParentObjectTreeFor(paramString));
          paramString = paramString.split("\\.");
          if (localObject != null)
            try
            {
              ((JSONObject)localObject).put(paramString[(paramString.length - 1)], paramObject);
            }
            catch (org.json.JSONException paramString)
            {
              DeviceLog.exception("Couldn't set value", paramString);
              monitorexit;
              return false;
            }
          return true;
        }
        DeviceLog.debug("Cannot set subvalue to an object that is not JSONObject");
        return false;
      }
      Object localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>("Storage not properly initialized or incorrect parameters:");
      ((StringBuilder)localObject).append(this._data);
      ((StringBuilder)localObject).append(", ");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append(", ");
      ((StringBuilder)localObject).append(paramObject);
      DeviceLog.error(((StringBuilder)localObject).toString());
      return false;
    }
    finally
    {
      monitorexit;
    }
    throw paramString;
  }

  public void setData(JSONObject paramJSONObject)
  {
    monitorenter;
    try
    {
      this._data = paramJSONObject;
      monitorexit;
      return;
    }
    finally
    {
      paramJSONObject = finally;
      monitorexit;
    }
    throw paramJSONObject;
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.misc.JsonStorage
 * JD-Core Version:    0.6.0
 */