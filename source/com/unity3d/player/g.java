package com.unity3d.player;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public final class g extends Fragment
{
  private final IPermissionRequestCallbacks a;
  private final Activity b;
  private final Looper c;

  public g()
  {
    this.a = null;
    this.b = null;
    this.c = null;
  }

  public g(Activity paramActivity, IPermissionRequestCallbacks paramIPermissionRequestCallbacks)
  {
    this.a = paramIPermissionRequestCallbacks;
    this.b = paramActivity;
    this.c = Looper.myLooper();
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestPermissions(getArguments().getStringArray("PermissionNames"), 96489);
  }

  public final void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfInt)
  {
    if (paramInt != 96489)
      return;
    if (paramArrayOfString.length == 0)
    {
      requestPermissions(getArguments().getStringArray("PermissionNames"), 96489);
      return;
    }
    for (paramInt = 0; (paramInt < paramArrayOfString.length) && (paramInt < paramArrayOfInt.length); paramInt++)
    {
      Object localObject = this.a;
      if ((localObject == null) || (this.b == null) || (this.c == null))
        continue;
      if ((localObject instanceof UnityPermissions.ModalWaitForPermissionResponse))
      {
        ((IPermissionRequestCallbacks)localObject).onPermissionGranted(paramArrayOfString[paramInt]);
      }
      else
      {
        if (paramArrayOfString[paramInt] == null)
          localObject = "<null>";
        else
          localObject = paramArrayOfString[paramInt];
        new Handler(this.c).post(new a(this.a, (String)localObject, paramArrayOfInt[paramInt], this.b.shouldShowRequestPermissionRationale((String)localObject)));
      }
    }
    paramArrayOfString = getActivity().getFragmentManager().beginTransaction();
    paramArrayOfString.remove(this);
    paramArrayOfString.commit();
  }

  final class a
    implements Runnable
  {
    private IPermissionRequestCallbacks b;
    private String c;
    private int d;
    private boolean e;

    a(IPermissionRequestCallbacks paramString, String paramInt, int paramBoolean, boolean arg5)
    {
      this.b = paramString;
      this.c = paramInt;
      this.d = paramBoolean;
      boolean bool;
      this.e = bool;
    }

    public final void run()
    {
      int i = this.d;
      if (i == -1)
      {
        if (this.e)
        {
          this.b.onPermissionDenied(this.c);
          return;
        }
        this.b.onPermissionDeniedAndDontAskAgain(this.c);
        return;
      }
      if (i == 0)
        this.b.onPermissionGranted(this.c);
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.g
 * JD-Core Version:    0.6.0
 */