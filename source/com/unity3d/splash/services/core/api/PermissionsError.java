package com.unity3d.splash.services.core.api;

 enum PermissionsError
{
  static
  {
    ERROR_CHECKING_PERMISSION = new PermissionsError("ERROR_CHECKING_PERMISSION", 2);
    ERROR_REQUESTING_PERMISSIONS = new PermissionsError("ERROR_REQUESTING_PERMISSIONS", 3);
    PermissionsError localPermissionsError = new PermissionsError("PERMISSION_NOT_GRANTED", 4);
    PERMISSION_NOT_GRANTED = localPermissionsError;
    $VALUES = new PermissionsError[] { COULDNT_GET_PERMISSIONS, NO_REQUESTED_PERMISSIONS, ERROR_CHECKING_PERMISSION, ERROR_REQUESTING_PERMISSIONS, localPermissionsError };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.api.PermissionsError
 * JD-Core Version:    0.6.0
 */