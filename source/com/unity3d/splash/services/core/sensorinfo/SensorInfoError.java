package com.unity3d.splash.services.core.sensorinfo;

public enum SensorInfoError
{
  static
  {
    SensorInfoError localSensorInfoError = new SensorInfoError("ACCELEROMETER_DATA_NOT_AVAILABLE", 0);
    ACCELEROMETER_DATA_NOT_AVAILABLE = localSensorInfoError;
    $VALUES = new SensorInfoError[] { localSensorInfoError };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.sensorinfo.SensorInfoError
 * JD-Core Version:    0.6.0
 */