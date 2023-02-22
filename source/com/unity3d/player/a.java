package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.play.core.assetpacks.AssetPackLocation;
import com.google.android.play.core.assetpacks.AssetPackManager;
import com.google.android.play.core.assetpacks.AssetPackManagerFactory;
import com.google.android.play.core.assetpacks.AssetPackState;
import com.google.android.play.core.assetpacks.AssetPackStateUpdateListener;
import com.google.android.play.core.assetpacks.AssetPackStates;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.RuntimeExecutionException;
import com.google.android.play.core.tasks.Task;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

final class a
  implements d
{
  private static a a;
  private AssetPackManager b;
  private HashSet c;
  private Object d;

  private a(Context paramContext)
  {
    if (a == null)
    {
      this.b = AssetPackManagerFactory.getInstance(paramContext);
      this.c = new HashSet();
      return;
    }
    throw new RuntimeException("AssetPackManagerWrapper should be created only once. Use getInstance() instead.");
  }

  public static d a(Context paramContext)
  {
    if (a == null)
      a = new a(paramContext);
    return a;
  }

  private void a(String paramString, IAssetPackManagerDownloadStatusCallback paramIAssetPackManagerDownloadStatusCallback, Looper paramLooper)
  {
    synchronized (a)
    {
      if (this.d == null)
      {
        b localb = new com/unity3d/player/a$b;
        localb.<init>(this, paramIAssetPackManagerDownloadStatusCallback, paramLooper);
        this.b.registerListener(localb);
        this.d = localb;
      }
      else
      {
        ((b)this.d).a(paramIAssetPackManagerDownloadStatusCallback);
      }
      this.c.add(paramString);
      this.b.fetch(Collections.singletonList(paramString));
      return;
    }
  }

  public final Object a(IAssetPackManagerDownloadStatusCallback paramIAssetPackManagerDownloadStatusCallback)
  {
    paramIAssetPackManagerDownloadStatusCallback = new b(paramIAssetPackManagerDownloadStatusCallback);
    this.b.registerListener(paramIAssetPackManagerDownloadStatusCallback);
    return paramIAssetPackManagerDownloadStatusCallback;
  }

  public final String a(String paramString)
  {
    paramString = this.b.getPackLocation(paramString);
    if (paramString == null)
      return "";
    return paramString.assetsPath();
  }

  public final void a(Activity paramActivity, IAssetPackManagerMobileDataConfirmationCallback paramIAssetPackManagerMobileDataConfirmationCallback)
  {
    this.b.showCellularDataConfirmation(paramActivity).addOnSuccessListener(new c(paramIAssetPackManagerMobileDataConfirmationCallback));
  }

  public final void a(Object paramObject)
  {
    if (!(paramObject instanceof b))
      return;
    this.b.unregisterListener((b)paramObject);
  }

  public final void a(String[] paramArrayOfString)
  {
    this.b.cancel(Arrays.asList(paramArrayOfString));
  }

  public final void a(String[] paramArrayOfString, IAssetPackManagerDownloadStatusCallback paramIAssetPackManagerDownloadStatusCallback)
  {
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++)
    {
      String str = paramArrayOfString[j];
      this.b.getPackStates(Collections.singletonList(str)).addOnCompleteListener(new d(paramIAssetPackManagerDownloadStatusCallback, str));
    }
  }

  public final void a(String[] paramArrayOfString, IAssetPackManagerStatusQueryCallback paramIAssetPackManagerStatusQueryCallback)
  {
    this.b.getPackStates(Arrays.asList(paramArrayOfString)).addOnCompleteListener(new e(paramIAssetPackManagerStatusQueryCallback, paramArrayOfString));
  }

  public final void b(String paramString)
  {
    this.b.removePack(paramString);
  }

  private static final class a
    implements Runnable
  {
    private Set a;
    private String b;
    private int c;
    private long d;
    private long e;
    private int f;
    private int g;

    a(Set paramSet, String paramString, int paramInt1, long paramLong1, long paramLong2, int paramInt2, int paramInt3)
    {
      this.a = paramSet;
      this.b = paramString;
      this.c = paramInt1;
      this.d = paramLong1;
      this.e = paramLong2;
      this.f = paramInt2;
      this.g = paramInt3;
    }

    public final void run()
    {
      Iterator localIterator = this.a.iterator();
      while (localIterator.hasNext())
        ((IAssetPackManagerDownloadStatusCallback)localIterator.next()).onStatusUpdate(this.b, this.c, this.d, this.e, this.f, this.g);
    }
  }

  private final class b
    implements AssetPackStateUpdateListener
  {
    private HashSet b;
    private Looper c;

    public b(IAssetPackManagerDownloadStatusCallback arg2)
    {
      this(localIAssetPackManagerDownloadStatusCallback, Looper.myLooper());
    }

    public b(IAssetPackManagerDownloadStatusCallback paramLooper, Looper arg3)
    {
      this$1 = new HashSet();
      this.b = a.this;
      a.this.add(paramLooper);
      Object localObject;
      this.c = localObject;
    }

    private static Set a(HashSet paramHashSet)
    {
      return (Set)paramHashSet.clone();
    }

    private void a(AssetPackState paramAssetPackState)
    {
      monitorenter;
      try
      {
        if ((paramAssetPackState.status() == 4) || (paramAssetPackState.status() == 5) || (paramAssetPackState.status() == 0));
        synchronized (a.a())
        {
          a.a(a.this).remove(paramAssetPackState.name());
          if (a.a(a.this).isEmpty())
          {
            a.this.a(a.b(a.this));
            a.c(a.this);
          }
          int i = this.b.size();
          if (i == 0)
          {
            monitorexit;
            return;
          }
          ??? = new android/os/Handler;
          ((Handler)???).<init>(this.c);
          a.a locala = new com/unity3d/player/a$a;
          locala.<init>(a(this.b), paramAssetPackState.name(), paramAssetPackState.status(), paramAssetPackState.totalBytesToDownload(), paramAssetPackState.bytesDownloaded(), paramAssetPackState.transferProgressPercentage(), paramAssetPackState.errorCode());
          ((Handler)???).post(locala);
          monitorexit;
          return;
        }
      }
      finally
      {
        monitorexit;
      }
    }

    public final void a(IAssetPackManagerDownloadStatusCallback paramIAssetPackManagerDownloadStatusCallback)
    {
      monitorenter;
      try
      {
        this.b.add(paramIAssetPackManagerDownloadStatusCallback);
        monitorexit;
        return;
      }
      finally
      {
        paramIAssetPackManagerDownloadStatusCallback = finally;
        monitorexit;
      }
      throw paramIAssetPackManagerDownloadStatusCallback;
    }
  }

  private static final class c
    implements OnSuccessListener
  {
    private IAssetPackManagerMobileDataConfirmationCallback a;
    private Looper b;

    public c(IAssetPackManagerMobileDataConfirmationCallback paramIAssetPackManagerMobileDataConfirmationCallback)
    {
      this.a = paramIAssetPackManagerMobileDataConfirmationCallback;
      this.b = Looper.myLooper();
    }

    private void a(Integer paramInteger)
    {
      if (this.a != null)
      {
        Handler localHandler = new Handler(this.b);
        IAssetPackManagerMobileDataConfirmationCallback localIAssetPackManagerMobileDataConfirmationCallback = this.a;
        boolean bool;
        if (paramInteger.intValue() == -1)
          bool = true;
        else
          bool = false;
        localHandler.post(new a(localIAssetPackManagerMobileDataConfirmationCallback, bool));
      }
    }

    private static final class a
      implements Runnable
    {
      private IAssetPackManagerMobileDataConfirmationCallback a;
      private boolean b;

      a(IAssetPackManagerMobileDataConfirmationCallback paramIAssetPackManagerMobileDataConfirmationCallback, boolean paramBoolean)
      {
        this.a = paramIAssetPackManagerMobileDataConfirmationCallback;
        this.b = paramBoolean;
      }

      public final void run()
      {
        this.a.onMobileDataConfirmationResult(this.b);
      }
    }
  }

  private static final class d
    implements OnCompleteListener
  {
    private IAssetPackManagerDownloadStatusCallback a;
    private Looper b;
    private String c;

    public d(IAssetPackManagerDownloadStatusCallback paramIAssetPackManagerDownloadStatusCallback, String paramString)
    {
      this.a = paramIAssetPackManagerDownloadStatusCallback;
      this.b = Looper.myLooper();
      this.c = paramString;
    }

    private void a(String paramString, int paramInt1, int paramInt2, long paramLong)
    {
      long l;
      if (paramInt1 == 4)
        l = paramLong;
      else
        l = 0L;
      new Handler(this.b).post(new a.a(Collections.singleton(this.a), paramString, paramInt1, paramLong, l, 0, paramInt2));
    }

    public final void onComplete(Task paramTask)
    {
      try
      {
        paramTask = (AssetPackStates)paramTask.getResult();
        Object localObject = paramTask.packStates();
        if (((Map)localObject).size() == 0)
          return;
        localObject = ((Map)localObject).values().iterator();
        while (((Iterator)localObject).hasNext())
        {
          AssetPackState localAssetPackState = (AssetPackState)((Iterator)localObject).next();
          if ((localAssetPackState.errorCode() == 0) && (localAssetPackState.status() != 4) && (localAssetPackState.status() != 5) && (localAssetPackState.status() != 0))
          {
            a.a(a.a(), localAssetPackState.name(), this.a, this.b);
            continue;
          }
          a(localAssetPackState.name(), localAssetPackState.status(), localAssetPackState.errorCode(), paramTask.totalBytes());
        }
        return;
      }
      catch (RuntimeExecutionException paramTask)
      {
        a(this.c, 0, paramTask.getErrorCode(), 0L);
      }
    }
  }

  private static final class e
    implements OnCompleteListener
  {
    private IAssetPackManagerStatusQueryCallback a;
    private Looper b;
    private String[] c;

    public e(IAssetPackManagerStatusQueryCallback paramIAssetPackManagerStatusQueryCallback, String[] paramArrayOfString)
    {
      this.a = paramIAssetPackManagerStatusQueryCallback;
      this.b = Looper.myLooper();
      this.c = paramArrayOfString;
    }

    public final void onComplete(Task paramTask)
    {
      if (this.a == null)
        return;
      int i = 0;
      try
      {
        localObject1 = (AssetPackStates)paramTask.getResult();
        Object localObject2 = ((AssetPackStates)localObject1).packStates();
        j = ((Map)localObject2).size();
        paramTask = new String[j];
        localObject3 = new int[j];
        arrayOfInt = new int[j];
        localObject2 = ((Map)localObject2).values().iterator();
        while (((Iterator)localObject2).hasNext())
        {
          AssetPackState localAssetPackState = (AssetPackState)((Iterator)localObject2).next();
          paramTask[i] = localAssetPackState.name();
          localObject3[i] = localAssetPackState.status();
          arrayOfInt[i] = localAssetPackState.errorCode();
          i++;
        }
        new Handler(this.b).post(new a(this.a, ((AssetPackStates)localObject1).totalBytes(), paramTask, localObject3, arrayOfInt));
        return;
      }
      catch (RuntimeExecutionException paramTask)
      {
        Object localObject1;
        int j;
        Object localObject3 = paramTask.getMessage();
        for (arrayOfInt : this.c)
        {
          if (!((String)localObject3).contains(arrayOfInt))
            continue;
          localObject1 = new Handler(this.b);
          localObject3 = this.a;
          i = paramTask.getErrorCode();
          ((Handler)localObject1).post(new a((IAssetPackManagerStatusQueryCallback)localObject3, 0L, new String[] { arrayOfInt }, new int[] { 0 }, new int[] { i }));
          return;
        }
        localObject3 = this.c;
        int[] arrayOfInt = new int[localObject3.length];
        localObject3 = new int[localObject3.length];
        for (i = 0; i < this.c.length; i++)
        {
          arrayOfInt[i] = 0;
          localObject3[i] = paramTask.getErrorCode();
        }
        new Handler(this.b).post(new a(this.a, 0L, this.c, arrayOfInt, localObject3));
      }
    }

    private static final class a
      implements Runnable
    {
      private IAssetPackManagerStatusQueryCallback a;
      private long b;
      private String[] c;
      private int[] d;
      private int[] e;

      a(IAssetPackManagerStatusQueryCallback paramIAssetPackManagerStatusQueryCallback, long paramLong, String[] paramArrayOfString, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
      {
        this.a = paramIAssetPackManagerStatusQueryCallback;
        this.b = paramLong;
        this.c = paramArrayOfString;
        this.d = paramArrayOfInt1;
        this.e = paramArrayOfInt2;
      }

      public final void run()
      {
        this.a.onStatusResult(this.b, this.c, this.d, this.e);
      }
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.a
 * JD-Core Version:    0.6.0
 */