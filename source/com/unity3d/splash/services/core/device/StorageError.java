package com.unity3d.splash.services.core.device;

public enum StorageError
{
  static
  {
    COULDNT_GET_VALUE = new StorageError("COULDNT_GET_VALUE", 1);
    COULDNT_WRITE_STORAGE_TO_CACHE = new StorageError("COULDNT_WRITE_STORAGE_TO_CACHE", 2);
    COULDNT_CLEAR_STORAGE = new StorageError("COULDNT_CLEAR_STORAGE", 3);
    COULDNT_GET_STORAGE = new StorageError("COULDNT_GET_STORAGE", 4);
    StorageError localStorageError = new StorageError("COULDNT_DELETE_VALUE", 5);
    COULDNT_DELETE_VALUE = localStorageError;
    $VALUES = new StorageError[] { COULDNT_SET_VALUE, COULDNT_GET_VALUE, COULDNT_WRITE_STORAGE_TO_CACHE, COULDNT_CLEAR_STORAGE, COULDNT_GET_STORAGE, localStorageError };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.device.StorageError
 * JD-Core Version:    0.6.0
 */