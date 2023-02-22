package com.unity3d.splash.services.ads.video;

public enum VideoPlayerError
{
  static
  {
    PREPARE = new VideoPlayerError("PREPARE", 1);
    VideoPlayerError localVideoPlayerError = new VideoPlayerError("API_LEVEL_ERROR", 2);
    API_LEVEL_ERROR = localVideoPlayerError;
    $VALUES = new VideoPlayerError[] { VIDEOVIEW_NULL, PREPARE, localVideoPlayerError };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.video.VideoPlayerError
 * JD-Core Version:    0.6.0
 */