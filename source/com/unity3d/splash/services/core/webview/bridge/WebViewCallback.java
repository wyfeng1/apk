package com.unity3d.splash.services.core.webview.bridge;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.unity3d.splash.services.core.log.DeviceLog;
import java.util.ArrayList;
import java.util.Arrays;

public class WebViewCallback
  implements Parcelable
{
  public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
  {
    public final WebViewCallback createFromParcel(Parcel paramParcel)
    {
      return new WebViewCallback(paramParcel);
    }

    public final WebViewCallback[] newArray(int paramInt)
    {
      return new WebViewCallback[paramInt];
    }
  };
  private String _callbackId;
  private int _invocationId;
  private boolean _invoked;

  public WebViewCallback(Parcel paramParcel)
  {
    this._callbackId = paramParcel.readString();
    boolean bool;
    if (paramParcel.readByte() != 0)
      bool = true;
    else
      bool = false;
    this._invoked = bool;
    this._invocationId = paramParcel.readInt();
  }

  public WebViewCallback(String paramString, int paramInt)
  {
    this._callbackId = paramString;
    this._invocationId = paramInt;
  }

  private void invoke(CallbackStatus paramCallbackStatus, Enum paramEnum, Object[] paramArrayOfObject)
  {
    if (!this._invoked)
    {
      Object localObject = this._callbackId;
      if ((localObject != null) && (((String)localObject).length() != 0))
      {
        this._invoked = true;
        localObject = new ArrayList();
        ((ArrayList)localObject).addAll(Arrays.asList(paramArrayOfObject));
        ((ArrayList)localObject).add(0, this._callbackId);
        paramArrayOfObject = Invocation.getInvocationById(this._invocationId);
        if (paramArrayOfObject == null)
        {
          paramCallbackStatus = new StringBuilder("Couldn't get batch with id: ");
          paramCallbackStatus.append(getInvocationId());
          DeviceLog.error(paramCallbackStatus.toString());
          return;
        }
        paramArrayOfObject.setInvocationResponse(paramCallbackStatus, paramEnum, ((ArrayList)localObject).toArray());
      }
    }
  }

  public int describeContents()
  {
    return 45678;
  }

  public void error(Enum paramEnum, Object[] paramArrayOfObject)
  {
    invoke(CallbackStatus.ERROR, paramEnum, paramArrayOfObject);
  }

  public String getCallbackId()
  {
    return this._callbackId;
  }

  public int getInvocationId()
  {
    return this._invocationId;
  }

  public void invoke(Object[] paramArrayOfObject)
  {
    invoke(CallbackStatus.OK, null, paramArrayOfObject);
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this._callbackId);
    paramParcel.writeByte((byte)this._invoked);
    paramParcel.writeInt(this._invocationId);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.webview.bridge.WebViewCallback
 * JD-Core Version:    0.6.0
 */