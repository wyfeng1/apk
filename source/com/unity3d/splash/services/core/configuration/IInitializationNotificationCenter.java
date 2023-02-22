package com.unity3d.splash.services.core.configuration;

public abstract interface IInitializationNotificationCenter
{
  public abstract void addListener(IInitializationListener paramIInitializationListener);

  public abstract void removeListener(IInitializationListener paramIInitializationListener);

  public abstract void triggerOnSdkInitializationFailed(String paramString, int paramInt);

  public abstract void triggerOnSdkInitialized();
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.configuration.IInitializationNotificationCenter
 * JD-Core Version:    0.6.0
 */