package com.unity3d.splash.services.core.webview.bridge;

public enum WebViewBridgeError
{
  static
  {
    CLASS_NOT_EXPOSED = new WebViewBridgeError("CLASS_NOT_EXPOSED", 1);
    GETALLOWEDMETHODS_NOT_FOUND = new WebViewBridgeError("GETALLOWEDMETHODS_NOT_FOUND", 2);
    GETALLOWEDMETHODS_INVOCATION_FAILED = new WebViewBridgeError("GETALLOWEDMETHODS_INVOCATION_FAILED", 3);
    METHOD_NOT_FOUND = new WebViewBridgeError("METHOD_NOT_FOUND", 4);
    METHOD_UNALLOWED = new WebViewBridgeError("METHOD_UNALLOWED", 5);
    DATA_JSON_PARSE_FAILED = new WebViewBridgeError("DATA_JSON_PARSE_FAILED", 6);
    DATA_GET_PARAMETER_VALUE_FAILED = new WebViewBridgeError("DATA_GET_PARAMETER_VALUE_FAILED", 7);
    DATA_PARAMETER_NULL = new WebViewBridgeError("DATA_PARAMETER_NULL", 8);
    WebViewBridgeError localWebViewBridgeError = new WebViewBridgeError("INVOCATION_FAILED", 9);
    INVOCATION_FAILED = localWebViewBridgeError;
    $VALUES = new WebViewBridgeError[] { CLASS_NOT_FOUND, CLASS_NOT_EXPOSED, GETALLOWEDMETHODS_NOT_FOUND, GETALLOWEDMETHODS_INVOCATION_FAILED, METHOD_NOT_FOUND, METHOD_UNALLOWED, DATA_JSON_PARSE_FAILED, DATA_GET_PARAMETER_VALUE_FAILED, DATA_PARAMETER_NULL, localWebViewBridgeError };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.webview.bridge.WebViewBridgeError
 * JD-Core Version:    0.6.0
 */