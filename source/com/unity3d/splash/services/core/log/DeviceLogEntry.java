package com.unity3d.splash.services.core.log;

class DeviceLogEntry
{
  private DeviceLogLevel _logLevel = null;
  private String _originalMessage = null;
  private StackTraceElement _stackTraceElement = null;

  public DeviceLogEntry(DeviceLogLevel paramDeviceLogLevel, String paramString, StackTraceElement paramStackTraceElement)
  {
    this._logLevel = paramDeviceLogLevel;
    this._originalMessage = paramString;
    this._stackTraceElement = paramStackTraceElement;
  }

  public DeviceLogLevel getLogLevel()
  {
    return this._logLevel;
  }

  public String getParsedMessage()
  {
    Object localObject1 = this._originalMessage;
    Object localObject2 = this._stackTraceElement;
    String str1;
    String str2;
    int i;
    if (localObject2 != null)
    {
      str1 = ((StackTraceElement)localObject2).getClassName();
      str2 = this._stackTraceElement.getMethodName();
      i = this._stackTraceElement.getLineNumber();
    }
    else
    {
      str1 = "UnknownClass";
      str2 = "unknownMethod";
      i = -1;
    }
    localObject2 = localObject1;
    if (localObject1 != null)
    {
      localObject2 = localObject1;
      if (!((String)localObject1).isEmpty())
      {
        localObject2 = new StringBuilder(" :: ");
        ((StringBuilder)localObject2).append((String)localObject1);
        localObject2 = ((StringBuilder)localObject2).toString();
      }
    }
    localObject1 = localObject2;
    if (localObject2 == null)
      localObject1 = "";
    localObject2 = new StringBuilder(" (line:");
    ((StringBuilder)localObject2).append(i);
    ((StringBuilder)localObject2).append(")");
    localObject2 = ((StringBuilder)localObject2).toString();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(str1);
    localStringBuilder.append(".");
    localStringBuilder.append(str2);
    localStringBuilder.append("()");
    localStringBuilder.append((String)localObject2);
    localStringBuilder.append((String)localObject1);
    return (String)(String)localStringBuilder.toString();
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.log.DeviceLogEntry
 * JD-Core Version:    0.6.0
 */