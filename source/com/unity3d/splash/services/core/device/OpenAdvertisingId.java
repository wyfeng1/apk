package com.unity3d.splash.services.core.device;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.unity3d.splash.services.core.log.DeviceLog;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class OpenAdvertisingId
{
  private static final String HW_DEVICE_NAME = "HUAWEI";
  private static final String HW_OPEN_ADVERTISING_ID_SERVICE_NAME = "com.uodis.opendevice.aidl.OpenDeviceIdentifierService";
  private static OpenAdvertisingId instance;
  private boolean limitedOpenAdTracking = false;
  private String openAdvertisingIdentifier = null;

  // ERROR //
  private void fetchOAId(Context paramContext)
  {
    // Byte code:
    //   0: new 17	com/unity3d/splash/services/core/device/OpenAdvertisingId$HWAdvertisingServiceConnection
    //   3: dup
    //   4: aload_0
    //   5: aconst_null
    //   6: invokespecial 47	com/unity3d/splash/services/core/device/OpenAdvertisingId$HWAdvertisingServiceConnection:<init>	(Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;Lcom/unity3d/splash/services/core/device/OpenAdvertisingId$1;)V
    //   9: astore_2
    //   10: new 49	android/content/Intent
    //   13: dup
    //   14: ldc 51
    //   16: invokespecial 54	android/content/Intent:<init>	(Ljava/lang/String;)V
    //   19: astore_3
    //   20: aload_3
    //   21: ldc 56
    //   23: invokevirtual 60	android/content/Intent:setPackage	(Ljava/lang/String;)Landroid/content/Intent;
    //   26: pop
    //   27: aload_1
    //   28: aload_3
    //   29: aload_2
    //   30: iconst_1
    //   31: invokevirtual 66	android/content/Context:bindService	(Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z
    //   34: istore 4
    //   36: goto +13 -> 49
    //   39: astore_3
    //   40: ldc 68
    //   42: aload_3
    //   43: invokestatic 74	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   46: iconst_0
    //   47: istore 4
    //   49: iload 4
    //   51: ifeq +69 -> 120
    //   54: aload_2
    //   55: invokevirtual 78	com/unity3d/splash/services/core/device/OpenAdvertisingId$HWAdvertisingServiceConnection:getBinder	()Landroid/os/IBinder;
    //   58: invokestatic 82	com/unity3d/splash/services/core/device/OpenAdvertisingId$HWAdvertisingInfo$HWAdvertisingInfoBinder:create	(Landroid/os/IBinder;)Lcom/unity3d/splash/services/core/device/OpenAdvertisingId$HWAdvertisingInfo;
    //   61: astore_3
    //   62: aload_0
    //   63: aload_3
    //   64: invokeinterface 86 1 0
    //   69: putfield 38	com/unity3d/splash/services/core/device/OpenAdvertisingId:openAdvertisingIdentifier	Ljava/lang/String;
    //   72: aload_0
    //   73: aload_3
    //   74: iconst_1
    //   75: invokeinterface 90 2 0
    //   80: putfield 40	com/unity3d/splash/services/core/device/OpenAdvertisingId:limitedOpenAdTracking	Z
    //   83: goto +37 -> 120
    //   86: astore_3
    //   87: goto +21 -> 108
    //   90: astore_3
    //   91: ldc 92
    //   93: aload_3
    //   94: invokestatic 74	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   97: iload 4
    //   99: ifeq +31 -> 130
    //   102: aload_1
    //   103: aload_2
    //   104: invokevirtual 96	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
    //   107: return
    //   108: iload 4
    //   110: ifeq +8 -> 118
    //   113: aload_1
    //   114: aload_2
    //   115: invokevirtual 96	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
    //   118: aload_3
    //   119: athrow
    //   120: iload 4
    //   122: ifeq +8 -> 130
    //   125: aload_1
    //   126: aload_2
    //   127: invokevirtual 96	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
    //   130: return
    //
    // Exception table:
    //   from	to	target	type
    //   27	36	39	java/lang/Exception
    //   54	83	86	finally
    //   91	97	86	finally
    //   54	83	90	java/lang/Exception
  }

  private static OpenAdvertisingId getInstance()
  {
    if (instance == null)
      instance = new OpenAdvertisingId();
    return instance;
  }

  public static boolean getLimitedOpenAdTracking()
  {
    return getInstance().limitedOpenAdTracking;
  }

  public static String getOpenAdvertisingTrackingId()
  {
    return getInstance().openAdvertisingIdentifier;
  }

  public static void init(Context paramContext)
  {
    if (Build.MANUFACTURER.toUpperCase().equals("HUAWEI"))
      getInstance().fetchOAId(paramContext);
  }

  private static abstract interface HWAdvertisingInfo extends IInterface
  {
    public abstract boolean getEnabled(boolean paramBoolean);

    public abstract String getId();

    public static abstract class HWAdvertisingInfoBinder extends Binder
      implements OpenAdvertisingId.HWAdvertisingInfo
    {
      public static OpenAdvertisingId.HWAdvertisingInfo create(IBinder paramIBinder)
      {
        if (paramIBinder == null)
          return null;
        IInterface localIInterface = paramIBinder.queryLocalInterface("com.uodis.opendevice.aidl.OpenDeviceIdentifierService");
        if ((localIInterface != null) && ((localIInterface instanceof OpenAdvertisingId.HWAdvertisingInfo)))
          return (OpenAdvertisingId.HWAdvertisingInfo)localIInterface;
        return new HWAdvertisingInfoImplementation(paramIBinder);
      }

      public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      {
        if (paramInt1 != 1)
        {
          if (paramInt1 != 2)
            return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
          paramParcel1.enforceInterface("com.uodis.opendevice.aidl.OpenDeviceIdentifierService");
          boolean bool;
          if (paramParcel1.readInt() != 0)
            bool = true;
          else
            bool = false;
          paramInt1 = getEnabled(bool);
          paramParcel2.writeNoException();
          paramParcel2.writeInt(paramInt1);
          return true;
        }
        paramParcel1.enforceInterface("com.uodis.opendevice.aidl.OpenDeviceIdentifierService");
        paramParcel1 = getId();
        paramParcel2.writeNoException();
        paramParcel2.writeString(paramParcel1);
        return true;
      }

      private static class HWAdvertisingInfoImplementation
        implements OpenAdvertisingId.HWAdvertisingInfo
      {
        private final IBinder _binder;

        HWAdvertisingInfoImplementation(IBinder paramIBinder)
        {
          this._binder = paramIBinder;
        }

        public IBinder asBinder()
        {
          return this._binder;
        }

        public boolean getEnabled(boolean paramBoolean)
        {
          Parcel localParcel1 = Parcel.obtain();
          Parcel localParcel2 = Parcel.obtain();
          try
          {
            localParcel1.writeInterfaceToken("com.uodis.opendevice.aidl.OpenDeviceIdentifierService");
            boolean bool = true;
            if (paramBoolean)
              i = 1;
            else
              i = 0;
            localParcel1.writeInt(i);
            this._binder.transact(2, localParcel1, localParcel2, 0);
            localParcel2.readException();
            int i = localParcel2.readInt();
            if (i != 0)
              paramBoolean = bool;
            else
              paramBoolean = false;
            return paramBoolean;
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          throw localObject;
        }

        public String getId()
        {
          Parcel localParcel1 = Parcel.obtain();
          Parcel localParcel2 = Parcel.obtain();
          try
          {
            localParcel1.writeInterfaceToken("com.uodis.opendevice.aidl.OpenDeviceIdentifierService");
            this._binder.transact(1, localParcel1, localParcel2, 0);
            localParcel2.readException();
            String str = localParcel2.readString();
            return str;
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          throw localObject;
        }
      }
    }
  }

  private class HWAdvertisingServiceConnection
    implements ServiceConnection
  {
    private final BlockingQueue _binderQueue = new LinkedBlockingQueue();
    boolean _consumed = false;

    private HWAdvertisingServiceConnection()
    {
    }

    public IBinder getBinder()
    {
      if (!this._consumed)
      {
        this._consumed = true;
        return (IBinder)this._binderQueue.take();
      }
      throw new IllegalStateException();
    }

    public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      try
      {
        this._binderQueue.put(paramIBinder);
        return;
      }
      catch (java.lang.InterruptedException paramComponentName)
      {
        DeviceLog.debug("Couldn't put service to binder que");
      }
    }

    public void onServiceDisconnected(ComponentName paramComponentName)
    {
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.device.OpenAdvertisingId
 * JD-Core Version:    0.6.0
 */