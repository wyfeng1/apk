package com.unity3d.splash.services.ads.adunit;

public enum AdUnitError
{
  static
  {
    ACTIVITY_ID = new AdUnitError("ACTIVITY_ID", 1);
    GENERIC = new AdUnitError("GENERIC", 2);
    ORIENTATION = new AdUnitError("ORIENTATION", 3);
    SCREENVISIBILITY = new AdUnitError("SCREENVISIBILITY", 4);
    CORRUPTED_VIEWLIST = new AdUnitError("CORRUPTED_VIEWLIST", 5);
    CORRUPTED_KEYEVENTLIST = new AdUnitError("CORRUPTED_KEYEVENTLIST", 6);
    SYSTEM_UI_VISIBILITY = new AdUnitError("SYSTEM_UI_VISIBILITY", 7);
    UNKNOWN_VIEW = new AdUnitError("UNKNOWN_VIEW", 8);
    LAYOUT_NULL = new AdUnitError("LAYOUT_NULL", 9);
    MAX_MOTION_EVENT_COUNT_REACHED = new AdUnitError("MAX_MOTION_EVENT_COUNT_REACHED", 10);
    API_LEVEL_ERROR = new AdUnitError("API_LEVEL_ERROR", 11);
    NO_DISPLAY_CUTOUT_AVAILABLE = new AdUnitError("NO_DISPLAY_CUTOUT_AVAILABLE", 12);
    DISPLAY_CUTOUT_METHOD_NOT_AVAILABLE = new AdUnitError("DISPLAY_CUTOUT_METHOD_NOT_AVAILABLE", 13);
    DISPLAY_CUTOUT_JSON_ERROR = new AdUnitError("DISPLAY_CUTOUT_JSON_ERROR", 14);
    AdUnitError localAdUnitError = new AdUnitError("DISPLAY_CUTOUT_INVOKE_FAILED", 15);
    DISPLAY_CUTOUT_INVOKE_FAILED = localAdUnitError;
    $VALUES = new AdUnitError[] { ADUNIT_NULL, ACTIVITY_ID, GENERIC, ORIENTATION, SCREENVISIBILITY, CORRUPTED_VIEWLIST, CORRUPTED_KEYEVENTLIST, SYSTEM_UI_VISIBILITY, UNKNOWN_VIEW, LAYOUT_NULL, MAX_MOTION_EVENT_COUNT_REACHED, API_LEVEL_ERROR, NO_DISPLAY_CUTOUT_AVAILABLE, DISPLAY_CUTOUT_METHOD_NOT_AVAILABLE, DISPLAY_CUTOUT_JSON_ERROR, localAdUnitError };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.adunit.AdUnitError
 * JD-Core Version:    0.6.0
 */