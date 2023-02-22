package com.unity3d.splash.services.ads.adunit;

public class AdUnitMotionEvent
{
  private int _action;
  private int _deviceId;
  private long _eventTime;
  private boolean _isObscured;
  private float _pressure;
  private float _size;
  private int _source;
  private int _toolType;
  private float _x;
  private float _y;

  public AdUnitMotionEvent(int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, long paramLong, float paramFloat3, float paramFloat4)
  {
    this._action = paramInt1;
    this._isObscured = paramBoolean;
    this._toolType = paramInt2;
    this._source = paramInt3;
    this._deviceId = paramInt4;
    this._x = paramFloat1;
    this._y = paramFloat2;
    this._eventTime = paramLong;
    this._pressure = paramFloat3;
    this._size = paramFloat4;
  }

  public int getAction()
  {
    return this._action;
  }

  public int getDeviceId()
  {
    return this._deviceId;
  }

  public long getEventTime()
  {
    return this._eventTime;
  }

  public float getPressure()
  {
    return this._pressure;
  }

  public float getSize()
  {
    return this._size;
  }

  public int getSource()
  {
    return this._source;
  }

  public int getToolType()
  {
    return this._toolType;
  }

  public float getX()
  {
    return this._x;
  }

  public float getY()
  {
    return this._y;
  }

  public boolean isObscured()
  {
    return this._isObscured;
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.adunit.AdUnitMotionEvent
 * JD-Core Version:    0.6.0
 */