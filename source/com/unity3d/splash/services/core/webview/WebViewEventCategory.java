package com.unity3d.splash.services.core.webview;

public enum WebViewEventCategory
{
  static
  {
    REQUEST = new WebViewEventCategory("REQUEST", 3);
    RESOLVE = new WebViewEventCategory("RESOLVE", 4);
    CACHE = new WebViewEventCategory("CACHE", 5);
    CONNECTIVITY = new WebViewEventCategory("CONNECTIVITY", 6);
    STORAGE = new WebViewEventCategory("STORAGE", 7);
    BROADCAST = new WebViewEventCategory("BROADCAST", 8);
    LIFECYCLE = new WebViewEventCategory("LIFECYCLE", 9);
    DEVICEINFO = new WebViewEventCategory("DEVICEINFO", 10);
    WEBPLAYER = new WebViewEventCategory("WEBPLAYER", 11);
    PURCHASING = new WebViewEventCategory("PURCHASING", 12);
    ANALYTICS = new WebViewEventCategory("ANALYTICS", 13);
    AR = new WebViewEventCategory("AR", 14);
    PERMISSIONS = new WebViewEventCategory("PERMISSIONS", 15);
    STORE = new WebViewEventCategory("STORE", 16);
    WebViewEventCategory localWebViewEventCategory = new WebViewEventCategory("LOAD_API", 17);
    LOAD_API = localWebViewEventCategory;
    $VALUES = new WebViewEventCategory[] { ADUNIT, BANNER, VIDEOPLAYER, REQUEST, RESOLVE, CACHE, CONNECTIVITY, STORAGE, BROADCAST, LIFECYCLE, DEVICEINFO, WEBPLAYER, PURCHASING, ANALYTICS, AR, PERMISSIONS, STORE, localWebViewEventCategory };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.webview.WebViewEventCategory
 * JD-Core Version:    0.6.0
 */