package com.unity3d.player;

import android.app.Activity;
import android.content.Context;

class PlayAssetDeliveryUnityWrapper
{
  private static PlayAssetDeliveryUnityWrapper a;
  private d b;

  private PlayAssetDeliveryUnityWrapper(Context paramContext)
  {
    if (a == null)
      try
      {
        Class.forName("com.google.android.play.core.assetpacks.AssetPackManager");
        this.b = a(paramContext);
        return;
      }
      catch (java.lang.ClassNotFoundException paramContext)
      {
        this.b = null;
        return;
      }
    throw new RuntimeException("PlayAssetDeliveryUnityWrapper should be created only once. Use getInstance() instead.");
  }

  private static d a(Context paramContext)
  {
    return a.a(paramContext);
  }

  private void a()
  {
    if (!playCoreApiMissing())
      return;
    throw new RuntimeException("AssetPackManager API is not available! Make sure your gradle project includes \"com.google.android.play:core\" dependency.");
  }

  public static PlayAssetDeliveryUnityWrapper getInstance()
  {
    monitorenter;
    try
    {
      while (true)
      {
        PlayAssetDeliveryUnityWrapper localPlayAssetDeliveryUnityWrapper = a;
        if (localPlayAssetDeliveryUnityWrapper != null)
          break;
        try
        {
          PlayAssetDeliveryUnityWrapper.class.wait(3000L);
        }
        catch (InterruptedException localInterruptedException)
        {
          f.Log(6, localInterruptedException.getMessage());
        }
      }
      if (a != null)
      {
        localObject1 = a;
        return localObject1;
      }
      Object localObject1 = new java/lang/RuntimeException;
      ((RuntimeException)localObject1).<init>("PlayAssetDeliveryUnityWrapper is not yet initialised.");
      throw ((Throwable)localObject1);
    }
    finally
    {
      monitorexit;
    }
    throw localObject2;
  }

  public static PlayAssetDeliveryUnityWrapper init(Context paramContext)
  {
    monitorenter;
    try
    {
      if (a == null)
      {
        PlayAssetDeliveryUnityWrapper localPlayAssetDeliveryUnityWrapper = new com/unity3d/player/PlayAssetDeliveryUnityWrapper;
        localPlayAssetDeliveryUnityWrapper.<init>(paramContext);
        a = localPlayAssetDeliveryUnityWrapper;
        PlayAssetDeliveryUnityWrapper.class.notifyAll();
        paramContext = a;
        return paramContext;
      }
      paramContext = new java/lang/RuntimeException;
      paramContext.<init>("PlayAssetDeliveryUnityWrapper.init() should be called only once. Use getInstance() instead.");
      throw paramContext;
    }
    finally
    {
      monitorexit;
    }
    throw paramContext;
  }

  public void cancelAssetPackDownload(String paramString)
  {
    cancelAssetPackDownloads(new String[] { paramString });
  }

  public void cancelAssetPackDownloads(String[] paramArrayOfString)
  {
    a();
    this.b.a(paramArrayOfString);
  }

  public void downloadAssetPack(String paramString, IAssetPackManagerDownloadStatusCallback paramIAssetPackManagerDownloadStatusCallback)
  {
    downloadAssetPacks(new String[] { paramString }, paramIAssetPackManagerDownloadStatusCallback);
  }

  public void downloadAssetPacks(String[] paramArrayOfString, IAssetPackManagerDownloadStatusCallback paramIAssetPackManagerDownloadStatusCallback)
  {
    a();
    this.b.a(paramArrayOfString, paramIAssetPackManagerDownloadStatusCallback);
  }

  public String getAssetPackPath(String paramString)
  {
    a();
    return this.b.a(paramString);
  }

  public void getAssetPackState(String paramString, IAssetPackManagerStatusQueryCallback paramIAssetPackManagerStatusQueryCallback)
  {
    getAssetPackStates(new String[] { paramString }, paramIAssetPackManagerStatusQueryCallback);
  }

  public void getAssetPackStates(String[] paramArrayOfString, IAssetPackManagerStatusQueryCallback paramIAssetPackManagerStatusQueryCallback)
  {
    a();
    this.b.a(paramArrayOfString, paramIAssetPackManagerStatusQueryCallback);
  }

  public boolean playCoreApiMissing()
  {
    return this.b == null;
  }

  public Object registerDownloadStatusListener(IAssetPackManagerDownloadStatusCallback paramIAssetPackManagerDownloadStatusCallback)
  {
    a();
    return this.b.a(paramIAssetPackManagerDownloadStatusCallback);
  }

  public void removeAssetPack(String paramString)
  {
    a();
    this.b.b(paramString);
  }

  public void requestToUseMobileData(Activity paramActivity, IAssetPackManagerMobileDataConfirmationCallback paramIAssetPackManagerMobileDataConfirmationCallback)
  {
    a();
    this.b.a(paramActivity, paramIAssetPackManagerMobileDataConfirmationCallback);
  }

  public void unregisterDownloadStatusListener(Object paramObject)
  {
    a();
    this.b.a(paramObject);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.PlayAssetDeliveryUnityWrapper
 * JD-Core Version:    0.6.0
 */