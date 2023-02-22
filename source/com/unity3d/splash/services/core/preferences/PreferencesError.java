package com.unity3d.splash.services.core.preferences;

public enum PreferencesError
{
  static
  {
    PreferencesError localPreferencesError = new PreferencesError("COULDNT_GET_VALUE", 0);
    COULDNT_GET_VALUE = localPreferencesError;
    $VALUES = new PreferencesError[] { localPreferencesError };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.preferences.PreferencesError
 * JD-Core Version:    0.6.0
 */