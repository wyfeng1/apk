package com.unity3d.splash.services.core.request;

public enum ResolveHostError
{
  static
  {
    UNEXPECTED_EXCEPTION = new ResolveHostError("UNEXPECTED_EXCEPTION", 2);
    ResolveHostError localResolveHostError = new ResolveHostError("TIMEOUT", 3);
    TIMEOUT = localResolveHostError;
    $VALUES = new ResolveHostError[] { INVALID_HOST, UNKNOWN_HOST, UNEXPECTED_EXCEPTION, localResolveHostError };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.request.ResolveHostError
 * JD-Core Version:    0.6.0
 */