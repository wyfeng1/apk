package com.unity3d.splash.services.ads.video;

public enum VideoPlayerEvent
{
  static
  {
    INFO = new VideoPlayerEvent("INFO", 2);
    COMPLETED = new VideoPlayerEvent("COMPLETED", 3);
    PREPARED = new VideoPlayerEvent("PREPARED", 4);
    PREPARE_ERROR = new VideoPlayerEvent("PREPARE_ERROR", 5);
    PREPARE_TIMEOUT = new VideoPlayerEvent("PREPARE_TIMEOUT", 6);
    PLAY = new VideoPlayerEvent("PLAY", 7);
    PAUSE_ERROR = new VideoPlayerEvent("PAUSE_ERROR", 8);
    PAUSE = new VideoPlayerEvent("PAUSE", 9);
    SEEKTO_ERROR = new VideoPlayerEvent("SEEKTO_ERROR", 10);
    SEEKTO = new VideoPlayerEvent("SEEKTO", 11);
    STOP = new VideoPlayerEvent("STOP", 12);
    VideoPlayerEvent localVideoPlayerEvent = new VideoPlayerEvent("ILLEGAL_STATE", 13);
    ILLEGAL_STATE = localVideoPlayerEvent;
    $VALUES = new VideoPlayerEvent[] { GENERIC_ERROR, PROGRESS, INFO, COMPLETED, PREPARED, PREPARE_ERROR, PREPARE_TIMEOUT, PLAY, PAUSE_ERROR, PAUSE, SEEKTO_ERROR, SEEKTO, STOP, localVideoPlayerEvent };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.video.VideoPlayerEvent
 * JD-Core Version:    0.6.0
 */