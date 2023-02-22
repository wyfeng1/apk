package com.unity3d.splash.services.core.device;

public enum DeviceError
{
  static
  {
    COULDNT_GET_STORAGE_LOCATION = new DeviceError("COULDNT_GET_STORAGE_LOCATION", 4);
    COULDNT_GET_GL_VERSION = new DeviceError("COULDNT_GET_GL_VERSION", 5);
    JSON_ERROR = new DeviceError("JSON_ERROR", 6);
    COULDNT_GET_DIGEST = new DeviceError("COULDNT_GET_DIGEST", 7);
    COULDNT_GET_FINGERPRINT = new DeviceError("COULDNT_GET_FINGERPRINT", 8);
    COULDNT_GET_ADB_STATUS = new DeviceError("COULDNT_GET_ADB_STATUS", 9);
    DeviceError localDeviceError = new DeviceError("API_LEVEL_ERROR", 10);
    API_LEVEL_ERROR = localDeviceError;
    $VALUES = new DeviceError[] { APPLICATION_CONTEXT_NULL, APPLICATION_INFO_NOT_AVAILABLE, AUDIOMANAGER_NULL, INVALID_STORAGETYPE, COULDNT_GET_STORAGE_LOCATION, COULDNT_GET_GL_VERSION, JSON_ERROR, COULDNT_GET_DIGEST, COULDNT_GET_FINGERPRINT, COULDNT_GET_ADB_STATUS, localDeviceError };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.device.DeviceError
 * JD-Core Version:    0.6.0
 */