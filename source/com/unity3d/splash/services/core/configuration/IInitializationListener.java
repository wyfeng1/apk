package com.unity3d.splash.services.core.configuration;

public abstract interface IInitializationListener
{
  public abstract void onSdkInitializationFailed(String paramString, int paramInt);

  public abstract void onSdkInitialized();
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.configuration.IInitializationListener
 * JD-Core Version:    0.6.0
 */