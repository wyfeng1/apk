package com.unity3d.splash.services.ads.adunit;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import com.unity3d.splash.services.core.misc.ViewUtilities;

public class AdUnitTransparentActivity extends AdUnitActivity
{
  protected void createLayout()
  {
    super.createLayout();
    ViewUtilities.setBackground(this._layout, new ColorDrawable(0));
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    ViewUtilities.setBackground(this._layout, new ColorDrawable(0));
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.adunit.AdUnitTransparentActivity
 * JD-Core Version:    0.6.0
 */