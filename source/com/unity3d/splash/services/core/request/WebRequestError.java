package com.unity3d.splash.services.core.request;

public enum WebRequestError
{
  static
  {
    WebRequestError localWebRequestError = new WebRequestError("MAPPING_HEADERS_FAILED", 0);
    MAPPING_HEADERS_FAILED = localWebRequestError;
    $VALUES = new WebRequestError[] { localWebRequestError };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.request.WebRequestError
 * JD-Core Version:    0.6.0
 */