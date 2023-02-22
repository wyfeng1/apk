package com.unity3d.splash.services.core.sensorinfo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.properties.ClientProperties;
import org.json.JSONException;
import org.json.JSONObject;

public class SensorInfoListener
  implements SensorEventListener
{
  private static SensorInfoListener _accelerometerListener;
  private static Sensor _accelerometerSensor;
  private static SensorEvent _latestAccelerometerEvent;

  public static JSONObject getAccelerometerData()
  {
    JSONObject localJSONObject;
    if (_latestAccelerometerEvent != null)
    {
      localJSONObject = new JSONObject();
      try
      {
        localJSONObject.put("x", _latestAccelerometerEvent.values[0]);
        localJSONObject.put("y", _latestAccelerometerEvent.values[1]);
        localJSONObject.put("z", _latestAccelerometerEvent.values[2]);
      }
      catch (JSONException localJSONException)
      {
        DeviceLog.exception("JSON error while constructing accelerometer data", localJSONException);
      }
    }
    else
    {
      localJSONObject = null;
    }
    return localJSONObject;
  }

  public static boolean isAccelerometerListenerActive()
  {
    return _accelerometerListener != null;
  }

  public static boolean startAccelerometerListener(int paramInt)
  {
    if (_accelerometerListener == null)
      _accelerometerListener = new SensorInfoListener();
    SensorManager localSensorManager = (SensorManager)ClientProperties.getApplicationContext().getSystemService("sensor");
    Sensor localSensor = localSensorManager.getDefaultSensor(1);
    _accelerometerSensor = localSensor;
    return localSensorManager.registerListener(_accelerometerListener, localSensor, paramInt);
  }

  public static void stopAccelerometerListener()
  {
    if (_accelerometerListener != null)
    {
      ((SensorManager)ClientProperties.getApplicationContext().getSystemService("sensor")).unregisterListener(_accelerometerListener);
      _accelerometerListener = null;
    }
  }

  public void onAccuracyChanged(Sensor paramSensor, int paramInt)
  {
  }

  public void onSensorChanged(SensorEvent paramSensorEvent)
  {
    if (paramSensorEvent.sensor.getType() == 1)
      _latestAccelerometerEvent = paramSensorEvent;
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.sensorinfo.SensorInfoListener
 * JD-Core Version:    0.6.0
 */