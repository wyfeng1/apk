package com.unity3d.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;

public class HFPStatus
{
  private Context a;
  private BroadcastReceiver b = null;
  private Intent c = null;
  private boolean d = false;
  private AudioManager e = null;
  private boolean f = false;
  private int g = a.a;

  public HFPStatus(Context paramContext)
  {
    this.a = paramContext;
    this.e = ((AudioManager)paramContext.getSystemService("audio"));
    initHFPStatusJni();
  }

  private void b()
  {
    BroadcastReceiver localBroadcastReceiver = this.b;
    if (localBroadcastReceiver != null)
    {
      this.a.unregisterReceiver(localBroadcastReceiver);
      this.b = null;
      this.c = null;
    }
    this.g = a.a;
  }

  private void c()
  {
    if (this.f)
    {
      this.f = false;
      this.e.stopBluetoothSco();
    }
  }

  private final native void deinitHFPStatusJni();

  private final native void initHFPStatusJni();

  public final void a()
  {
    clearHFPStat();
    deinitHFPStatusJni();
  }

  protected void clearHFPStat()
  {
    b();
    c();
  }

  protected boolean getHFPStat()
  {
    return this.g == a.b;
  }

  protected void requestHFPStat()
  {
    clearHFPStat();
    1 local1 = new BroadcastReceiver()
    {
      public void onReceive(Context paramContext, Intent paramIntent)
      {
        if (paramIntent.getIntExtra("android.media.extra.SCO_AUDIO_STATE", -1) == 1)
        {
          HFPStatus.a(HFPStatus.this, HFPStatus.a.b);
          HFPStatus.a(HFPStatus.this);
          if (HFPStatus.b(HFPStatus.this))
            HFPStatus.c(HFPStatus.this).setMode(3);
        }
      }
    };
    this.b = local1;
    this.c = this.a.registerReceiver(local1, new IntentFilter("android.media.ACTION_SCO_AUDIO_STATE_UPDATED"));
    try
    {
      this.f = true;
      this.e.startBluetoothSco();
      return;
    }
    catch (NullPointerException localNullPointerException)
    {
      f.Log(5, "startBluetoothSco() failed. no bluetooth device connected.");
    }
  }

  protected void setHFPRecordingStat(boolean paramBoolean)
  {
    this.d = paramBoolean;
    if (!paramBoolean)
      this.e.setMode(0);
  }

  static enum a
  {
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.HFPStatus
 * JD-Core Version:    0.6.0
 */