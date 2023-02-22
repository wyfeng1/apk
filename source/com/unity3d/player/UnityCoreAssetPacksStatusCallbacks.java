package com.unity3d.player;

class UnityCoreAssetPacksStatusCallbacks
  implements IAssetPackManagerDownloadStatusCallback, IAssetPackManagerStatusQueryCallback
{
  private final native void nativeStatusQueryResult(String paramString, int paramInt1, int paramInt2);

  public void onStatusResult(long paramLong, String[] paramArrayOfString, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    for (int i = 0; i < paramArrayOfInt1.length; i++)
      nativeStatusQueryResult(paramArrayOfString[i], paramArrayOfInt1[i], paramArrayOfInt2[i]);
  }

  public void onStatusUpdate(String paramString, int paramInt1, long paramLong1, long paramLong2, int paramInt2, int paramInt3)
  {
    nativeStatusQueryResult(paramString, paramInt1, paramInt3);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.UnityCoreAssetPacksStatusCallbacks
 * JD-Core Version:    0.6.0
 */