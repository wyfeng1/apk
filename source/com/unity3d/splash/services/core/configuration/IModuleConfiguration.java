package com.unity3d.splash.services.core.configuration;

public abstract interface IModuleConfiguration
{
  public abstract Class[] getWebAppApiClassList();

  public abstract boolean initCompleteState(Configuration paramConfiguration);

  public abstract boolean initErrorState(Configuration paramConfiguration, String paramString1, String paramString2);

  public abstract boolean initModuleState(Configuration paramConfiguration);

  public abstract boolean resetState(Configuration paramConfiguration);
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.configuration.IModuleConfiguration
 * JD-Core Version:    0.6.0
 */