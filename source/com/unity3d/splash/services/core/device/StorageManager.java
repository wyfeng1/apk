package com.unity3d.splash.services.core.device;

import android.content.Context;
import com.unity3d.splash.services.core.properties.SdkProperties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StorageManager
{
  protected static final Map _storageFileMap = new HashMap();
  protected static final List _storages = new ArrayList();

  public static void addStorageLocation(StorageType paramStorageType, String paramString)
  {
    monitorenter;
    try
    {
      if (!_storageFileMap.containsKey(paramStorageType))
        _storageFileMap.put(paramStorageType, paramString);
      monitorexit;
      return;
    }
    finally
    {
      paramStorageType = finally;
      monitorexit;
    }
    throw paramStorageType;
  }

  public static Storage getStorage(StorageType paramStorageType)
  {
    Object localObject = _storages;
    if (localObject != null)
    {
      Iterator localIterator = ((List)localObject).iterator();
      while (localIterator.hasNext())
      {
        localObject = (Storage)localIterator.next();
        if ((localObject != null) && (((Storage)localObject).getType().equals(paramStorageType)))
          return localObject;
      }
    }
    return (Storage)null;
  }

  public static boolean hasStorage(StorageType paramStorageType)
  {
    Object localObject = _storages;
    if (localObject != null)
    {
      Iterator localIterator = ((List)localObject).iterator();
      while (localIterator.hasNext())
      {
        localObject = (Storage)localIterator.next();
        if ((localObject != null) && (((Storage)localObject).getType().equals(paramStorageType)))
          return true;
      }
    }
    return false;
  }

  public static boolean init(Context paramContext)
  {
    if (paramContext == null)
      return false;
    paramContext = paramContext.getFilesDir();
    if (paramContext == null)
      return false;
    Object localObject1 = StorageType.PUBLIC;
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append(paramContext);
    ((StringBuilder)localObject2).append("/");
    ((StringBuilder)localObject2).append(SdkProperties.getLocalStorageFilePrefix());
    ((StringBuilder)localObject2).append("public-data.json");
    addStorageLocation((StorageType)localObject1, ((StringBuilder)localObject2).toString());
    if (!setupStorage(StorageType.PUBLIC))
      return false;
    localObject2 = StorageType.PRIVATE;
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(paramContext);
    ((StringBuilder)localObject1).append("/");
    ((StringBuilder)localObject1).append(SdkProperties.getLocalStorageFilePrefix());
    ((StringBuilder)localObject1).append("private-data.json");
    addStorageLocation((StorageType)localObject2, ((StringBuilder)localObject1).toString());
    return setupStorage(StorageType.PRIVATE);
  }

  public static void initStorage(StorageType paramStorageType)
  {
    if (hasStorage(paramStorageType))
    {
      paramStorageType = getStorage(paramStorageType);
      if (paramStorageType != null)
        paramStorageType.initStorage();
      return;
    }
    if (_storageFileMap.containsKey(paramStorageType))
    {
      paramStorageType = new Storage((String)_storageFileMap.get(paramStorageType), paramStorageType);
      paramStorageType.initStorage();
      _storages.add(paramStorageType);
    }
  }

  public static void removeStorage(StorageType paramStorageType)
  {
    monitorenter;
    try
    {
      if (getStorage(paramStorageType) != null)
        _storages.remove(getStorage(paramStorageType));
      if (_storageFileMap != null)
        _storageFileMap.remove(paramStorageType);
      return;
    }
    finally
    {
      monitorexit;
    }
    throw paramStorageType;
  }

  private static boolean setupStorage(StorageType paramStorageType)
  {
    if (!hasStorage(paramStorageType))
    {
      initStorage(paramStorageType);
      paramStorageType = getStorage(paramStorageType);
      if ((paramStorageType != null) && (!paramStorageType.storageFileExists()))
        paramStorageType.writeStorage();
      if (paramStorageType == null)
        return false;
    }
    return true;
  }

  public static enum StorageType
  {
    static
    {
      StorageType localStorageType = new StorageType("PUBLIC", 1);
      PUBLIC = localStorageType;
      $VALUES = new StorageType[] { PRIVATE, localStorageType };
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.device.StorageManager
 * JD-Core Version:    0.6.0
 */