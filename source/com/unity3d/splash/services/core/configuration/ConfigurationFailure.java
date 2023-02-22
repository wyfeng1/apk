package com.unity3d.splash.services.core.configuration;

public enum ConfigurationFailure
{
  static
  {
    ConfigurationFailure localConfigurationFailure = new ConfigurationFailure("INVALID_DATA", 1);
    INVALID_DATA = localConfigurationFailure;
    $VALUES = new ConfigurationFailure[] { NETWORK_FAILURE, localConfigurationFailure };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.configuration.ConfigurationFailure
 * JD-Core Version:    0.6.0
 */