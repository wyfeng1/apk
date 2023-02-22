package com.unity3d.player;

import I;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CameraCaptureSession.StateCallback;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraDevice.StateCallback;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureRequest.Builder;
import android.hardware.camera2.CaptureRequest.Key;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.Image.Plane;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Range;
import android.util.Size;
import android.util.SizeF;
import android.view.Surface;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public final class c
{
  private static CameraManager b;
  private static String[] c;
  private static Semaphore e = new Semaphore(1);
  private CameraCaptureSession.CaptureCallback A = new CameraCaptureSession.CaptureCallback()
  {
    public final void onCaptureCompleted(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, TotalCaptureResult paramTotalCaptureResult)
    {
      c.a(c.this, paramCaptureRequest.getTag());
    }

    public final void onCaptureFailed(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, CaptureFailure paramCaptureFailure)
    {
      paramCameraCaptureSession = new StringBuilder("Camera2: Capture session failed ");
      paramCameraCaptureSession.append(paramCaptureRequest.getTag());
      paramCameraCaptureSession.append(" reason ");
      paramCameraCaptureSession.append(paramCaptureFailure.getReason());
      f.Log(5, paramCameraCaptureSession.toString());
      c.a(c.this, paramCaptureRequest.getTag());
    }

    public final void onCaptureSequenceAborted(CameraCaptureSession paramCameraCaptureSession, int paramInt)
    {
    }

    public final void onCaptureSequenceCompleted(CameraCaptureSession paramCameraCaptureSession, int paramInt, long paramLong)
    {
    }
  };
  private final CameraDevice.StateCallback B = new CameraDevice.StateCallback()
  {
    public final void onClosed(CameraDevice paramCameraDevice)
    {
      c.f().release();
    }

    public final void onDisconnected(CameraDevice paramCameraDevice)
    {
      f.Log(5, "Camera2: CameraDevice disconnected.");
      c.b(c.this, paramCameraDevice);
      c.f().release();
    }

    public final void onError(CameraDevice paramCameraDevice, int paramInt)
    {
      StringBuilder localStringBuilder = new StringBuilder("Camera2: Error opeining CameraDevice ");
      localStringBuilder.append(paramInt);
      f.Log(6, localStringBuilder.toString());
      c.b(c.this, paramCameraDevice);
      c.f().release();
    }

    public final void onOpened(CameraDevice paramCameraDevice)
    {
      c.a(c.this, paramCameraDevice);
      c.f().release();
    }
  };
  private final ImageReader.OnImageAvailableListener C = new ImageReader.OnImageAvailableListener()
  {
    public final void onImageAvailable(ImageReader paramImageReader)
    {
      if (c.f().tryAcquire())
      {
        paramImageReader = paramImageReader.acquireNextImage();
        if (paramImageReader != null)
        {
          Image.Plane[] arrayOfPlane = paramImageReader.getPlanes();
          if ((paramImageReader.getFormat() == 35) && (arrayOfPlane != null) && (arrayOfPlane.length == 3))
            c.h(c.this).a(arrayOfPlane[0].getBuffer(), arrayOfPlane[1].getBuffer(), arrayOfPlane[2].getBuffer(), arrayOfPlane[0].getRowStride(), arrayOfPlane[1].getRowStride(), arrayOfPlane[1].getPixelStride());
          else
            f.Log(6, "Camera2: Wrong image format.");
          if (c.i(c.this) != null)
            c.i(c.this).close();
          c.a(c.this, paramImageReader);
        }
        c.f().release();
      }
    }
  };
  private final SurfaceTexture.OnFrameAvailableListener D = new SurfaceTexture.OnFrameAvailableListener()
  {
    public final void onFrameAvailable(SurfaceTexture paramSurfaceTexture)
    {
      c.h(c.this).a(paramSurfaceTexture);
    }
  };
  private e a = null;
  private CameraDevice d;
  private HandlerThread f;
  private Handler g;
  private Rect h;
  private Rect i;
  private int j;
  private int k;
  private float l = -1.0F;
  private float m = -1.0F;
  private int n;
  private int o;
  private boolean p = false;
  private Range q;
  private ImageReader r = null;
  private Image s;
  private CaptureRequest.Builder t;
  private CameraCaptureSession u = null;
  private Object v = new Object();
  private int w;
  private SurfaceTexture x;
  private Surface y = null;
  private int z = a.c;

  protected c(e parame)
  {
    this.a = parame;
    g();
  }

  public static int a(Context paramContext)
  {
    return c(paramContext).length;
  }

  public static int a(Context paramContext, int paramInt)
  {
    try
    {
      paramInt = ((Integer)b(paramContext).getCameraCharacteristics(c(paramContext)[paramInt]).get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
      return paramInt;
    }
    catch (CameraAccessException paramContext)
    {
      StringBuilder localStringBuilder = new StringBuilder("Camera2: CameraAccessException ");
      localStringBuilder.append(paramContext);
      f.Log(6, localStringBuilder.toString());
    }
    return 0;
  }

  private static int a(Range[] paramArrayOfRange, int paramInt)
  {
    int i1 = -1;
    double d1 = 1.7976931348623157E+308D;
    int i2 = 0;
    while (i2 < paramArrayOfRange.length)
    {
      int i3 = ((Integer)paramArrayOfRange[i2].getLower()).intValue();
      int i4 = ((Integer)paramArrayOfRange[i2].getUpper()).intValue();
      float f1 = paramInt;
      if ((f1 + 0.1F > i3) && (f1 - 0.1F < i4))
        return paramInt;
      double d2 = Math.min(Math.abs(paramInt - i3), Math.abs(paramInt - i4));
      double d3 = d1;
      if (d2 < d1)
      {
        i1 = i2;
        d3 = d2;
      }
      i2++;
      d1 = d3;
    }
    if (paramInt > ((Integer)paramArrayOfRange[i1].getUpper()).intValue());
    for (paramArrayOfRange = paramArrayOfRange[i1].getUpper(); ; paramArrayOfRange = paramArrayOfRange[i1].getLower())
      return ((Integer)paramArrayOfRange).intValue();
  }

  private static Rect a(Size[] paramArrayOfSize, double paramDouble1, double paramDouble2)
  {
    double d1 = 1.7976931348623157E+308D;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    while (i1 < paramArrayOfSize.length)
    {
      int i4 = paramArrayOfSize[i1].getWidth();
      int i5 = paramArrayOfSize[i1].getHeight();
      double d2 = Math.abs(Math.log(paramDouble1 / i4)) + Math.abs(Math.log(paramDouble2 / i5));
      double d3 = d1;
      if (d2 < d1)
      {
        i2 = i4;
        i3 = i5;
        d3 = d2;
      }
      i1++;
      d1 = d3;
    }
    return new Rect(0, 0, i2, i3);
  }

  private void a(CameraDevice paramCameraDevice)
  {
    synchronized (this.v)
    {
      this.u = null;
      paramCameraDevice.close();
      this.d = null;
      return;
    }
  }

  private void a(Object arg1)
  {
    if (??? == "Focus")
    {
      this.p = false;
      synchronized (this.v)
      {
        CameraCaptureSession localCameraCaptureSession = this.u;
        if (localCameraCaptureSession != null)
          try
          {
            this.t.set(CaptureRequest.CONTROL_AF_TRIGGER, Integer.valueOf(0));
            this.t.setTag("Regular");
            this.u.setRepeatingRequest(this.t.build(), this.A, this.g);
          }
          catch (CameraAccessException localCameraAccessException)
          {
            StringBuilder localStringBuilder = new java/lang/StringBuilder;
            localStringBuilder.<init>("Camera2: CameraAccessException ");
            localStringBuilder.append(localCameraAccessException);
            f.Log(6, localStringBuilder.toString());
          }
        return;
      }
    }
    if (??? == "Cancel focus")
      synchronized (this.v)
      {
        if (this.u != null)
          j();
        return;
      }
  }

  private static Size[] a(CameraCharacteristics paramCameraCharacteristics)
  {
    paramCameraCharacteristics = (StreamConfigurationMap)paramCameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
    if (paramCameraCharacteristics == null)
    {
      f.Log(6, "Camera2: configuration map is not available.");
      return null;
    }
    paramCameraCharacteristics = paramCameraCharacteristics.getOutputSizes(35);
    if ((paramCameraCharacteristics != null) && (paramCameraCharacteristics.length != 0))
      return paramCameraCharacteristics;
    return null;
  }

  private static CameraManager b(Context paramContext)
  {
    if (b == null)
      b = (CameraManager)paramContext.getSystemService("camera");
    return b;
  }

  private void b(CameraCharacteristics paramCameraCharacteristics)
  {
    int i1 = ((Integer)paramCameraCharacteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AF)).intValue();
    this.k = i1;
    if (i1 > 0)
    {
      paramCameraCharacteristics = (Rect)paramCameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
      this.i = paramCameraCharacteristics;
      float f1 = paramCameraCharacteristics.width() / this.i.height();
      float f2 = this.h.width() / this.h.height();
      if (f2 > f1)
      {
        this.n = 0;
        this.o = (int)((this.i.height() - this.i.width() / f2) / 2.0F);
      }
      else
      {
        this.o = 0;
        this.n = (int)((this.i.width() - this.i.height() * f2) / 2.0F);
      }
      this.j = (Math.min(this.i.width(), this.i.height()) / 20);
    }
  }

  public static boolean b(Context paramContext, int paramInt)
  {
    try
    {
      paramInt = ((Integer)b(paramContext).getCameraCharacteristics(c(paramContext)[paramInt]).get(CameraCharacteristics.LENS_FACING)).intValue();
      return paramInt == 0;
    }
    catch (CameraAccessException paramContext)
    {
      StringBuilder localStringBuilder = new StringBuilder("Camera2: CameraAccessException ");
      localStringBuilder.append(paramContext);
      f.Log(6, localStringBuilder.toString());
    }
    return false;
  }

  public static boolean c(Context paramContext, int paramInt)
  {
    try
    {
      paramInt = ((Integer)b(paramContext).getCameraCharacteristics(c(paramContext)[paramInt]).get(CameraCharacteristics.CONTROL_MAX_REGIONS_AF)).intValue();
      return paramInt > 0;
    }
    catch (CameraAccessException localCameraAccessException)
    {
      paramContext = new StringBuilder("Camera2: CameraAccessException ");
      paramContext.append(localCameraAccessException);
      f.Log(6, paramContext.toString());
    }
    return false;
  }

  private static String[] c(Context paramContext)
  {
    if (c == null)
      try
      {
        c = b(paramContext).getCameraIdList();
      }
      catch (CameraAccessException localCameraAccessException)
      {
        paramContext = new StringBuilder("Camera2: CameraAccessException ");
        paramContext.append(localCameraAccessException);
        f.Log(6, paramContext.toString());
        c = new String[0];
      }
    return c;
  }

  public static int d(Context paramContext, int paramInt)
  {
    try
    {
      localObject = b(paramContext).getCameraCharacteristics(c(paramContext)[paramInt]);
      paramContext = (float[])((CameraCharacteristics)localObject).get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
      localObject = (SizeF)((CameraCharacteristics)localObject).get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE);
      if (paramContext.length > 0)
      {
        float f1 = paramContext[0];
        float f2 = ((SizeF)localObject).getWidth();
        return (int)(f1 * 36.0F / f2);
      }
    }
    catch (CameraAccessException paramContext)
    {
      Object localObject = new StringBuilder("Camera2: CameraAccessException ");
      ((StringBuilder)localObject).append(paramContext);
      f.Log(6, ((StringBuilder)localObject).toString());
    }
    return 0;
  }

  public static int[] e(Context paramContext, int paramInt)
  {
    try
    {
      paramContext = b(paramContext).getCameraCharacteristics(c(paramContext)[paramInt]);
      paramContext = a(paramContext);
      if (paramContext != null)
      {
        localObject = new int[paramContext.length * 2];
        for (paramInt = 0; paramInt < paramContext.length; paramInt++)
        {
          int i1 = paramInt * 2;
          localObject[i1] = paramContext[paramInt].getWidth();
          localObject[(i1 + 1)] = paramContext[paramInt].getHeight();
        }
        return localObject;
      }
      return null;
    }
    catch (CameraAccessException paramContext)
    {
      Object localObject = new StringBuilder("Camera2: CameraAccessException ");
      ((StringBuilder)localObject).append(paramContext);
      f.Log(6, ((StringBuilder)localObject).toString());
    }
    return (I)null;
  }

  private void g()
  {
    HandlerThread localHandlerThread = new HandlerThread("CameraBackground");
    this.f = localHandlerThread;
    localHandlerThread.start();
    this.g = new Handler(this.f.getLooper());
  }

  private void h()
  {
    this.f.quit();
    try
    {
      this.f.join(4000L);
      this.f = null;
      this.g = null;
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      this.f.interrupt();
      StringBuilder localStringBuilder = new StringBuilder("Camera2: Interrupted while waiting for the background thread to finish ");
      localStringBuilder.append(localInterruptedException);
      f.Log(6, localStringBuilder.toString());
    }
  }

  private void i()
  {
    try
    {
      if (!e.tryAcquire(4L, TimeUnit.SECONDS))
      {
        f.Log(5, "Camera2: Timeout waiting to lock camera for closing.");
        return;
      }
      this.d.close();
      try
      {
        if (!e.tryAcquire(4L, TimeUnit.SECONDS))
          f.Log(5, "Camera2: Timeout waiting to close camera.");
      }
      catch (InterruptedException localInterruptedException1)
      {
        localStringBuilder = new StringBuilder("Camera2: Interrupted while waiting to close camera ");
        localStringBuilder.append(localInterruptedException1);
        f.Log(6, localStringBuilder.toString());
      }
      this.d = null;
      e.release();
      return;
    }
    catch (InterruptedException localInterruptedException2)
    {
      StringBuilder localStringBuilder = new StringBuilder("Camera2: Interrupted while trying to lock camera for closing ");
      localStringBuilder.append(localInterruptedException2);
      f.Log(6, localStringBuilder.toString());
    }
  }

  private void j()
  {
    try
    {
      if ((this.k != 0) && (this.l >= 0.0F) && (this.l <= 1.0F) && (this.m >= 0.0F) && (this.m <= 1.0F))
      {
        this.p = true;
        int i1 = (int)((this.i.width() - this.n * 2) * this.l + this.n);
        int i2 = (int)((this.i.height() - this.o * 2) * (1.0D - this.m) + this.o);
        i1 = Math.max(this.j + 1, Math.min(i1, this.i.width() - this.j - 1));
        i2 = Math.max(this.j + 1, Math.min(i2, this.i.height() - this.j - 1));
        localObject = this.t;
        CaptureRequest.Key localKey = CaptureRequest.CONTROL_AF_REGIONS;
        MeteringRectangle localMeteringRectangle = new android/hardware/camera2/params/MeteringRectangle;
        localMeteringRectangle.<init>(i1 - this.j, i2 - this.j, this.j * 2, this.j * 2, 999);
        ((CaptureRequest.Builder)localObject).set(localKey, new MeteringRectangle[] { localMeteringRectangle });
        this.t.set(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(1));
        this.t.set(CaptureRequest.CONTROL_AF_TRIGGER, Integer.valueOf(1));
        this.t.setTag("Focus");
        this.u.capture(this.t.build(), this.A, this.g);
      }
      else
      {
        this.t.set(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(4));
        this.t.setTag("Regular");
        if (this.u != null)
          this.u.setRepeatingRequest(this.t.build(), this.A, this.g);
      }
      return;
    }
    catch (CameraAccessException localCameraAccessException)
    {
      Object localObject = new StringBuilder("Camera2: CameraAccessException ");
      ((StringBuilder)localObject).append(localCameraAccessException);
      f.Log(6, ((StringBuilder)localObject).toString());
    }
  }

  private void k()
  {
    try
    {
      if (this.u != null)
      {
        this.u.stopRepeating();
        this.t.set(CaptureRequest.CONTROL_AF_TRIGGER, Integer.valueOf(2));
        this.t.set(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(0));
        this.t.setTag("Cancel focus");
        this.u.capture(this.t.build(), this.A, this.g);
      }
      return;
    }
    catch (CameraAccessException localCameraAccessException)
    {
      StringBuilder localStringBuilder = new StringBuilder("Camera2: CameraAccessException ");
      localStringBuilder.append(localCameraAccessException);
      f.Log(6, localStringBuilder.toString());
    }
  }

  public final Rect a()
  {
    return this.h;
  }

  public final boolean a(float paramFloat1, float paramFloat2)
  {
    if (this.k > 0)
    {
      if (!this.p)
      {
        this.l = paramFloat1;
        this.m = paramFloat2;
        synchronized (this.v)
        {
          if ((this.u != null) && (this.z != a.b))
            k();
          return true;
        }
      }
      f.Log(5, "Camera2: Setting manual focus point already started.");
    }
    return false;
  }

  public final boolean a(Context paramContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    try
    {
      Object localObject1 = b.getCameraCharacteristics(c(paramContext)[paramInt1]);
      if (((Integer)((CameraCharacteristics)localObject1).get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).intValue() == 2)
      {
        f.Log(5, "Camera2: only LEGACY hardware level is supported.");
        return false;
      }
      Object localObject2 = a((CameraCharacteristics)localObject1);
      if ((localObject2 != null) && (localObject2.length != 0))
      {
        this.h = a(localObject2, paramInt2, paramInt3);
        localObject2 = (Range[])((CameraCharacteristics)localObject1).get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
        if ((localObject2 != null) && (localObject2.length != 0))
        {
          paramInt2 = a(localObject2, paramInt4);
          this.q = new Range(Integer.valueOf(paramInt2), Integer.valueOf(paramInt2));
          try
          {
            if (!e.tryAcquire(4L, TimeUnit.SECONDS))
            {
              f.Log(5, "Camera2: Timeout waiting to lock camera for opening.");
              return false;
            }
            try
            {
              b.openCamera(c(paramContext)[paramInt1], this.B, this.g);
              try
              {
                if (!e.tryAcquire(4L, TimeUnit.SECONDS))
                {
                  f.Log(5, "Camera2: Timeout waiting to open camera.");
                  return false;
                }
                e.release();
              }
              catch (InterruptedException localInterruptedException)
              {
                paramContext = new StringBuilder("Camera2: Interrupted while waiting to open camera ");
                paramContext.append(localInterruptedException);
                f.Log(6, paramContext.toString());
              }
              this.w = paramInt5;
              b((CameraCharacteristics)localObject1);
              return this.d != null;
            }
            catch (CameraAccessException paramContext)
            {
              localObject1 = new StringBuilder("Camera2: CameraAccessException ");
              ((StringBuilder)localObject1).append(paramContext);
              f.Log(6, ((StringBuilder)localObject1).toString());
              e.release();
              return false;
            }
          }
          catch (InterruptedException paramContext)
          {
            localObject1 = new StringBuilder("Camera2: Interrupted while trying to lock camera for opening ");
            ((StringBuilder)localObject1).append(paramContext);
            f.Log(6, ((StringBuilder)localObject1).toString());
            return false;
          }
        }
        f.Log(6, "Camera2: target FPS ranges are not avialable.");
      }
      return false;
    }
    catch (CameraAccessException localCameraAccessException)
    {
      paramContext = new StringBuilder("Camera2: CameraAccessException ");
      paramContext.append(localCameraAccessException);
      f.Log(6, paramContext.toString());
    }
    return false;
  }

  public final void b()
  {
    if (this.d != null)
    {
      e();
      i();
      this.A = null;
      this.y = null;
      this.x = null;
      Object localObject = this.s;
      if (localObject != null)
      {
        ((Image)localObject).close();
        this.s = null;
      }
      localObject = this.r;
      if (localObject != null)
      {
        ((ImageReader)localObject).close();
        this.r = null;
      }
    }
    h();
  }

  public final void c()
  {
    Object localObject;
    if (this.r == null)
    {
      localObject = ImageReader.newInstance(this.h.width(), this.h.height(), 35, 2);
      this.r = ((ImageReader)localObject);
      ((ImageReader)localObject).setOnImageAvailableListener(this.C, this.g);
      this.s = null;
      if (this.w != 0)
      {
        localObject = new SurfaceTexture(this.w);
        this.x = ((SurfaceTexture)localObject);
        ((SurfaceTexture)localObject).setDefaultBufferSize(this.h.width(), this.h.height());
        this.x.setOnFrameAvailableListener(this.D, this.g);
        this.y = new Surface(this.x);
      }
    }
    try
    {
      if (this.u != null)
      {
        if (this.z == a.b)
          this.u.setRepeatingRequest(this.t.build(), this.A, this.g);
      }
      else
      {
        CameraDevice localCameraDevice = this.d;
        if (this.y != null)
          localObject = Arrays.asList(new Surface[] { this.y, this.r.getSurface() });
        else
          localObject = Arrays.asList(new Surface[] { this.r.getSurface() });
        2 local2 = new com/unity3d/player/c$2;
        local2.<init>(this);
        localCameraDevice.createCaptureSession((List)localObject, local2, this.g);
      }
      this.z = a.a;
      return;
    }
    catch (CameraAccessException localCameraAccessException)
    {
      localObject = new StringBuilder("Camera2: CameraAccessException ");
      ((StringBuilder)localObject).append(localCameraAccessException);
      f.Log(6, ((StringBuilder)localObject).toString());
    }
  }

  public final void d()
  {
    synchronized (this.v)
    {
      CameraCaptureSession localCameraCaptureSession = this.u;
      if (localCameraCaptureSession != null)
        try
        {
          this.u.stopRepeating();
          this.z = a.b;
        }
        catch (CameraAccessException localCameraAccessException)
        {
          StringBuilder localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>("Camera2: CameraAccessException ");
          localStringBuilder.append(localCameraAccessException);
          f.Log(6, localStringBuilder.toString());
        }
      return;
    }
  }

  public final void e()
  {
    synchronized (this.v)
    {
      CameraCaptureSession localCameraCaptureSession = this.u;
      if (localCameraCaptureSession != null)
      {
        try
        {
          this.u.abortCaptures();
        }
        catch (CameraAccessException localCameraAccessException)
        {
          StringBuilder localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>("Camera2: CameraAccessException ");
          localStringBuilder.append(localCameraAccessException);
          f.Log(6, localStringBuilder.toString());
        }
        this.u.close();
        this.u = null;
        this.z = a.c;
      }
      return;
    }
  }

  private static enum a
  {
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.c
 * JD-Core Version:    0.6.0
 */