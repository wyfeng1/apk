package com.unity3d.splash.services.ads.adunit;

import android.os.Bundle;
import android.view.View;

public abstract interface IAdUnitViewHandler
{
  public abstract boolean create(AdUnitActivity paramAdUnitActivity);

  public abstract boolean destroy();

  public abstract View getView();

  public abstract void onCreate(AdUnitActivity paramAdUnitActivity, Bundle paramBundle);

  public abstract void onDestroy(AdUnitActivity paramAdUnitActivity);

  public abstract void onPause(AdUnitActivity paramAdUnitActivity);

  public abstract void onResume(AdUnitActivity paramAdUnitActivity);

  public abstract void onStart(AdUnitActivity paramAdUnitActivity);

  public abstract void onStop(AdUnitActivity paramAdUnitActivity);
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.adunit.IAdUnitViewHandler
 * JD-Core Version:    0.6.0
 */