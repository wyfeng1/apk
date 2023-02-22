package com.unity3d.splash.services.core.lifecycle;

public enum LifecycleError
{
  static
  {
    LifecycleError localLifecycleError = new LifecycleError("JSON_ERROR", 2);
    JSON_ERROR = localLifecycleError;
    $VALUES = new LifecycleError[] { APPLICATION_NULL, LISTENER_NOT_NULL, localLifecycleError };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.lifecycle.LifecycleError
 * JD-Core Version:    0.6.0
 */