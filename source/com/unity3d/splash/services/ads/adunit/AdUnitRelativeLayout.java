package com.unity3d.splash.services.ads.adunit;

import android.content.Context;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.Iterator;

public class AdUnitRelativeLayout extends RelativeLayout
{
  private int _maxEvents = 10000;
  private final ArrayList _motionEvents = new ArrayList();
  private boolean _shouldCapture = false;

  public AdUnitRelativeLayout(Context paramContext)
  {
    super(paramContext);
  }

  public void clearCapture()
  {
    synchronized (this._motionEvents)
    {
      this._motionEvents.clear();
      return;
    }
  }

  public void endCapture()
  {
    this._shouldCapture = false;
  }

  public int getCurrentEventCount()
  {
    synchronized (this._motionEvents)
    {
      int i = this._motionEvents.size();
      return i;
    }
  }

  public SparseIntArray getEventCount(ArrayList paramArrayList)
  {
    SparseIntArray localSparseIntArray = new SparseIntArray();
    synchronized (this._motionEvents)
    {
      Iterator localIterator1 = this._motionEvents.iterator();
      while (true)
      {
        if (!localIterator1.hasNext())
          break label111;
        AdUnitMotionEvent localAdUnitMotionEvent = (AdUnitMotionEvent)localIterator1.next();
        Iterator localIterator2 = paramArrayList.iterator();
        if (!localIterator2.hasNext())
          continue;
        Integer localInteger = (Integer)localIterator2.next();
        if (localAdUnitMotionEvent.getAction() != localInteger.intValue())
          break;
        localSparseIntArray.put(localInteger.intValue(), localSparseIntArray.get(localInteger.intValue(), 0) + 1);
      }
      label111: return localSparseIntArray;
    }
  }

  public SparseArray getEvents(SparseArray paramSparseArray)
  {
    SparseIntArray localSparseIntArray = new SparseIntArray();
    SparseArray localSparseArray1 = new SparseArray();
    synchronized (this._motionEvents)
    {
      Iterator localIterator = this._motionEvents.iterator();
      while (localIterator.hasNext())
      {
        AdUnitMotionEvent localAdUnitMotionEvent = (AdUnitMotionEvent)localIterator.next();
        ArrayList localArrayList2 = (ArrayList)paramSparseArray.get(localAdUnitMotionEvent.getAction());
        if (localArrayList2 == null)
          continue;
        int i = ((Integer)localArrayList2.get(0)).intValue();
        if (localSparseIntArray.get(localAdUnitMotionEvent.getAction(), 0) == i)
        {
          if (localSparseArray1.get(localAdUnitMotionEvent.getAction()) == null)
          {
            int j = localAdUnitMotionEvent.getAction();
            SparseArray localSparseArray2 = new android/util/SparseArray;
            localSparseArray2.<init>();
            localSparseArray1.put(j, localSparseArray2);
          }
          ((SparseArray)localSparseArray1.get(localAdUnitMotionEvent.getAction())).put(i, localAdUnitMotionEvent);
          localArrayList2.remove(0);
        }
        localSparseIntArray.put(localAdUnitMotionEvent.getAction(), localSparseIntArray.get(localAdUnitMotionEvent.getAction()) + 1);
      }
      return localSparseArray1;
    }
  }

  public int getMaxEventCount()
  {
    return this._maxEvents;
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    super.onInterceptTouchEvent(paramMotionEvent);
    if ((this._shouldCapture) && (this._motionEvents.size() < this._maxEvents))
    {
      boolean bool;
      if ((paramMotionEvent.getFlags() & 0x1) != 0)
        bool = true;
      else
        bool = false;
      synchronized (this._motionEvents)
      {
        ArrayList localArrayList2 = this._motionEvents;
        AdUnitMotionEvent localAdUnitMotionEvent = new com/unity3d/splash/services/ads/adunit/AdUnitMotionEvent;
        localAdUnitMotionEvent.<init>(paramMotionEvent.getActionMasked(), bool, paramMotionEvent.getToolType(0), paramMotionEvent.getSource(), paramMotionEvent.getDeviceId(), paramMotionEvent.getX(0), paramMotionEvent.getY(0), paramMotionEvent.getEventTime(), paramMotionEvent.getPressure(0), paramMotionEvent.getSize(0));
        localArrayList2.add(localAdUnitMotionEvent);
      }
    }
    return false;
  }

  public void startCapture(int paramInt)
  {
    this._maxEvents = paramInt;
    this._shouldCapture = true;
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.adunit.AdUnitRelativeLayout
 * JD-Core Version:    0.6.0
 */