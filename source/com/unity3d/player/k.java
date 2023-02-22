package com.unity3d.player;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.VideoView;
import java.net.URLDecoder;
import java.util.Timer;
import java.util.TimerTask;

public final class k extends RelativeLayout
{
  UnityPlayer a;
  j b;
  Context c;
  int d = 5;
  VideoView e;
  ImageView f;
  ImageView g;
  TextView h;
  Timer i;
  int j = 0;
  int k = 5;
  boolean l;
  TimerTask m = new TimerTask()
  {
    public final void run()
    {
      k.this.a.runOnUiThread(new Runnable()
      {
        public final void run()
        {
          if (!k.this.l)
            return;
          Object localObject = k.this;
          ((k)localObject).k -= 1;
          localObject = k.this.h;
          StringBuilder localStringBuilder = new StringBuilder("Skip ");
          localStringBuilder.append(k.this.k);
          ((TextView)localObject).setText(localStringBuilder.toString());
          if (k.this.k <= 0)
            k.b(k.this);
        }
      });
    }
  };

  public k(Context paramContext, UnityPlayer paramUnityPlayer, j paramj)
  {
    super(paramContext);
    this.c = paramContext;
    this.a = paramUnityPlayer;
    this.b = paramj;
    d();
    e();
    g();
  }

  private static String a(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      return null;
    if (paramString1.indexOf('?') == -1)
      return null;
    String[] arrayOfString = paramString1.split("\\?")[1].split("&");
    for (int n = 0; n < arrayOfString.length; n++)
    {
      paramString1 = arrayOfString[n].split("=");
      if (paramString2.equals(paramString1[0]))
        return paramString1[1];
    }
    return null;
  }

  private void b(String paramString)
  {
    if (paramString.startsWith("deeplinker"))
    {
      String str = f(a(paramString, "primaryUrl"));
      paramString = f(a(paramString, "fallbackUrl"));
      if ((d(str)) && (str != null) && (e(str)))
      {
        if (this.b.h() != null)
          new a().execute(this.b.h());
        c(str);
        return;
      }
      if ((paramString != null) && (e(paramString)))
      {
        if (this.b.i() != null)
          new a().execute(this.b.i());
        c(paramString);
      }
      return;
    }
    if (e(paramString))
      c(paramString);
  }

  private void c(String paramString)
  {
    if (paramString == null)
      return;
    Intent localIntent = new Intent();
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.setData(Uri.parse(paramString));
    this.c.startActivity(localIntent);
    if (this.b.e() != null)
      new a().execute(this.b.e());
  }

  private void d()
  {
    Object localObject1;
    Object localObject2;
    if ("VIDEO".equals(this.b.j()))
    {
      if ((this.b.k() != null) && (this.b.k() != ""))
      {
        if (this.e == null)
        {
          localObject1 = new RelativeLayout.LayoutParams(-1, -1);
          ((RelativeLayout.LayoutParams)localObject1).addRule(11);
          ((RelativeLayout.LayoutParams)localObject1).addRule(9);
          localObject2 = new MediaMetadataRetriever();
          ((MediaMetadataRetriever)localObject2).setDataSource(this.c, Uri.parse(this.b.k()));
          localObject2 = ((MediaMetadataRetriever)localObject2).getFrameAtTime(0L, 2);
          ImageView localImageView = new ImageView(this.c);
          localImageView.setLayoutParams((ViewGroup.LayoutParams)localObject1);
          localImageView.setImageBitmap((Bitmap)localObject2);
          localImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
          addView(localImageView);
          localObject2 = new VideoView(this.c);
          this.e = ((VideoView)localObject2);
          ((VideoView)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject1);
          this.e.setVideoPath(this.b.k());
          addView(this.e);
        }
      }
      else
        return;
    }
    else
    {
      localObject2 = this.b.b();
      if ((this.b.b() != null) && (this.b.b() != "") && (this.f == null))
      {
        localObject1 = localObject2;
        if (((String)localObject2).startsWith("file://"))
          localObject1 = ((String)localObject2).substring(7);
        localObject2 = BitmapFactory.decodeFile((String)localObject1);
        if (localObject2 == null)
          return;
        this.f = new ImageView(this.c);
        localObject1 = new RelativeLayout.LayoutParams(-1, -1);
        ((RelativeLayout.LayoutParams)localObject1).addRule(11);
        ((RelativeLayout.LayoutParams)localObject1).addRule(9);
        this.f.setLayoutParams((ViewGroup.LayoutParams)localObject1);
        this.f.setImageBitmap((Bitmap)localObject2);
        this.f.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addView(this.f);
      }
    }
  }

  private boolean d(String paramString)
  {
    if (paramString == null)
      return false;
    Intent localIntent = new Intent();
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.setData(Uri.parse(paramString));
    return this.c.getPackageManager().resolveActivity(localIntent, 0) != null;
  }

  private void e()
  {
    if (this.a.getShowSplashSlogan().booleanValue())
    {
      int n = getResources().getIdentifier("unity_splash_slogan", "drawable", this.c.getPackageName());
      Bitmap localBitmap = BitmapFactory.decodeResource(getResources(), n, new BitmapFactory.Options());
      this.g = new ImageView(this.c);
      RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-1, this.a.getShowSplashSloganHeight());
      localLayoutParams.addRule(11);
      localLayoutParams.addRule(9);
      localLayoutParams.addRule(12);
      this.g.setLayoutParams(localLayoutParams);
      this.g.setImageBitmap(localBitmap);
      this.g.setScaleType(ImageView.ScaleType.CENTER);
      this.g.setBackgroundColor(-1);
      addView(this.g);
    }
  }

  private static boolean e(String paramString)
  {
    if (paramString == null)
      return false;
    return paramString.startsWith("http");
  }

  private static String f(String paramString)
  {
    if (paramString == null)
      return null;
    try
    {
      paramString = Uri.encode(URLDecoder.decode(paramString, "UTF-8"), "@#&=*+-_.,:!?()/~'%");
      return paramString;
    }
    catch (java.io.UnsupportedEncodingException paramString)
    {
    }
    return null;
  }

  private void f()
  {
    this.h = new TextView(this.c);
    Object localObject = new StringBuilder("Skip ");
    ((StringBuilder)localObject).append(this.d);
    localObject = ((StringBuilder)localObject).toString();
    if (this.e != null)
      localObject = "Skip";
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams.addRule(11);
    localLayoutParams.addRule(10);
    localLayoutParams.rightMargin = 48;
    localLayoutParams.topMargin = 72;
    this.h.setLayoutParams(localLayoutParams);
    this.h.setText((CharSequence)localObject);
    this.h.setTextSize(15.0F);
    this.h.setTextColor(-1);
    localObject = new GradientDrawable();
    ((GradientDrawable)localObject).setColor(-7829368);
    ((GradientDrawable)localObject).setCornerRadius(12.0F);
    ((GradientDrawable)localObject).setStroke(3, -1);
    this.h.setBackground((Drawable)localObject);
    this.h.setPadding(20, 5, 20, 5);
    this.h.setAlpha(0.8F);
    this.h.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramView)
      {
        k.a(k.this);
      }
    });
    addView(this.h);
  }

  private void g()
  {
    TextView localTextView = new TextView(this.c);
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams.addRule(9);
    localLayoutParams.addRule(10);
    localLayoutParams.leftMargin = 32;
    localLayoutParams.topMargin = 64;
    localTextView.setLayoutParams(localLayoutParams);
    localTextView.setText("ADS");
    localTextView.setTextSize(8.0F);
    localTextView.setTextColor(-7829368);
    localTextView.setAlpha(0.8F);
    localTextView.setPadding(10, 5, 10, 5);
    addView(localTextView);
  }

  private void h()
  {
    Object localObject = this.e;
    if (localObject != null)
      ((VideoView)localObject).stopPlayback();
    localObject = this.i;
    if (localObject != null)
      ((Timer)localObject).cancel();
    this.a.NotifySplashAdsFinished();
  }

  private void i()
  {
    if (this.b.l() != null)
      new a().execute(this.b.l());
    h();
  }

  public final void a()
  {
    if (this.b.f() > 0)
    {
      int n = this.b.f();
      this.d = n;
      this.k = n;
    }
    f();
    Object localObject = this.e;
    if (localObject != null)
    {
      ((VideoView)localObject).setOnCompletionListener(new MediaPlayer.OnCompletionListener()
      {
        public final void onCompletion(MediaPlayer paramMediaPlayer)
        {
          k.b(k.this);
        }
      });
      this.e.start();
    }
    else
    {
      localObject = new Timer();
      this.i = ((Timer)localObject);
      this.l = true;
      ((Timer)localObject).schedule(this.m, 1000L, 1000L);
    }
    if (this.b.d() != null)
      new a().execute(this.b.d());
    setOnTouchListener(new View.OnTouchListener()
    {
      public final boolean onTouch(View paramView, MotionEvent paramMotionEvent)
      {
        if (paramMotionEvent.getAction() == 0)
        {
          paramView = k.this;
          k.a(paramView, paramView.b.c());
        }
        return true;
      }
    });
  }

  public final void b()
  {
    VideoView localVideoView = this.e;
    if (localVideoView != null)
    {
      localVideoView.pause();
      this.j = this.e.getCurrentPosition();
    }
    if (this.i != null)
      this.l = false;
  }

  public final void c()
  {
    VideoView localVideoView = this.e;
    if (localVideoView != null)
    {
      localVideoView.start();
      this.e.seekTo(this.j);
    }
    if (this.i != null)
      this.l = true;
  }

  final class a extends AsyncTask
  {
    private String[] b;

    a()
    {
    }

    // ERROR //
    private static void a(String paramString)
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore_1
      //   2: aconst_null
      //   3: astore_2
      //   4: new 23	java/net/URL
      //   7: astore_3
      //   8: aload_3
      //   9: ldc 25
      //   11: invokespecial 27	java/net/URL:<init>	(Ljava/lang/String;)V
      //   14: aload_3
      //   15: invokevirtual 31	java/net/URL:openConnection	()Ljava/net/URLConnection;
      //   18: checkcast 33	java/net/HttpURLConnection
      //   21: astore_3
      //   22: aload_3
      //   23: sipush 30000
      //   26: invokevirtual 37	java/net/HttpURLConnection:setConnectTimeout	(I)V
      //   29: aload_3
      //   30: ldc 39
      //   32: invokevirtual 42	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
      //   35: aload_3
      //   36: ldc 44
      //   38: ldc 46
      //   40: invokevirtual 50	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
      //   43: aload_3
      //   44: iconst_1
      //   45: invokevirtual 54	java/net/HttpURLConnection:setDoOutput	(Z)V
      //   48: aload_3
      //   49: invokevirtual 58	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
      //   52: astore_2
      //   53: new 60	org/json/JSONObject
      //   56: astore_1
      //   57: aload_1
      //   58: invokespecial 61	org/json/JSONObject:<init>	()V
      //   61: aload_1
      //   62: ldc 63
      //   64: ldc 65
      //   66: invokevirtual 69	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      //   69: pop
      //   70: aload_1
      //   71: ldc 71
      //   73: aload_0
      //   74: invokevirtual 69	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      //   77: pop
      //   78: aload_1
      //   79: ldc 73
      //   81: ldc 75
      //   83: invokevirtual 69	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      //   86: pop
      //   87: aload_1
      //   88: invokevirtual 79	org/json/JSONObject:toString	()Ljava/lang/String;
      //   91: invokevirtual 85	java/lang/String:getBytes	()[B
      //   94: astore_0
      //   95: aload_2
      //   96: aload_0
      //   97: iconst_0
      //   98: aload_0
      //   99: arraylength
      //   100: invokevirtual 91	java/io/OutputStream:write	([BII)V
      //   103: aload_2
      //   104: invokevirtual 94	java/io/OutputStream:flush	()V
      //   107: aload_2
      //   108: invokevirtual 97	java/io/OutputStream:close	()V
      //   111: aload_3
      //   112: invokevirtual 101	java/net/HttpURLConnection:getResponseCode	()I
      //   115: pop
      //   116: aload_3
      //   117: ifnull +42 -> 159
      //   120: aload_3
      //   121: invokevirtual 104	java/net/HttpURLConnection:disconnect	()V
      //   124: return
      //   125: astore_0
      //   126: goto +12 -> 138
      //   129: astore_0
      //   130: aload_3
      //   131: astore_0
      //   132: goto +19 -> 151
      //   135: astore_0
      //   136: aload_2
      //   137: astore_3
      //   138: aload_3
      //   139: ifnull +7 -> 146
      //   142: aload_3
      //   143: invokevirtual 104	java/net/HttpURLConnection:disconnect	()V
      //   146: aload_0
      //   147: athrow
      //   148: astore_0
      //   149: aload_1
      //   150: astore_0
      //   151: aload_0
      //   152: ifnull +7 -> 159
      //   155: aload_0
      //   156: invokevirtual 104	java/net/HttpURLConnection:disconnect	()V
      //   159: return
      //
      // Exception table:
      //   from	to	target	type
      //   22	116	125	finally
      //   22	116	129	java/lang/Exception
      //   4	22	135	finally
      //   4	22	148	java/lang/Exception
    }

    // ERROR //
    protected final java.lang.Void doInBackground(String[] paramArrayOfString)
    {
      // Byte code:
      //   0: aload_0
      //   1: aload_1
      //   2: putfield 112	com/unity3d/player/k$a:b	[Ljava/lang/String;
      //   5: aconst_null
      //   6: astore_2
      //   7: aload_1
      //   8: ifnonnull +5 -> 13
      //   11: aconst_null
      //   12: areturn
      //   13: iconst_0
      //   14: istore_3
      //   15: aload_0
      //   16: getfield 112	com/unity3d/player/k$a:b	[Ljava/lang/String;
      //   19: astore_1
      //   20: iload_3
      //   21: aload_1
      //   22: arraylength
      //   23: if_icmpge +391 -> 414
      //   26: aload_1
      //   27: iload_3
      //   28: aaload
      //   29: astore 4
      //   31: aload 4
      //   33: ifnull +375 -> 408
      //   36: new 23	java/net/URL
      //   39: astore_1
      //   40: aload_1
      //   41: aload 4
      //   43: invokestatic 115	com/unity3d/player/k:a	(Ljava/lang/String;)Ljava/lang/String;
      //   46: invokespecial 27	java/net/URL:<init>	(Ljava/lang/String;)V
      //   49: aload_1
      //   50: invokevirtual 31	java/net/URL:openConnection	()Ljava/net/URLConnection;
      //   53: checkcast 33	java/net/HttpURLConnection
      //   56: astore_1
      //   57: aload_1
      //   58: astore 5
      //   60: aload_1
      //   61: sipush 30000
      //   64: invokevirtual 37	java/net/HttpURLConnection:setConnectTimeout	(I)V
      //   67: aload_1
      //   68: astore 5
      //   70: aload_1
      //   71: ldc 117
      //   73: invokevirtual 42	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
      //   76: aload_1
      //   77: astore 5
      //   79: aload_1
      //   80: invokevirtual 101	java/net/HttpURLConnection:getResponseCode	()I
      //   83: istore 6
      //   85: iload 6
      //   87: sipush 400
      //   90: if_icmplt +208 -> 298
      //   93: aload_1
      //   94: astore 5
      //   96: aload_1
      //   97: invokevirtual 121	java/net/HttpURLConnection:getErrorStream	()Ljava/io/InputStream;
      //   100: astore 7
      //   102: aload_1
      //   103: astore 5
      //   105: new 123	java/io/BufferedReader
      //   108: astore 8
      //   110: aload_1
      //   111: astore 5
      //   113: new 125	java/io/InputStreamReader
      //   116: astore 9
      //   118: aload_1
      //   119: astore 5
      //   121: aload 9
      //   123: aload 7
      //   125: invokespecial 128	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
      //   128: aload_1
      //   129: astore 5
      //   131: aload 8
      //   133: aload 9
      //   135: invokespecial 131	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
      //   138: ldc 133
      //   140: astore 7
      //   142: aload_1
      //   143: astore 5
      //   145: aload 8
      //   147: invokevirtual 136	java/io/BufferedReader:readLine	()Ljava/lang/String;
      //   150: astore 10
      //   152: aload 10
      //   154: ifnull +54 -> 208
      //   157: aload_1
      //   158: astore 5
      //   160: new 138	java/lang/StringBuilder
      //   163: astore 9
      //   165: aload_1
      //   166: astore 5
      //   168: aload 9
      //   170: invokespecial 139	java/lang/StringBuilder:<init>	()V
      //   173: aload_1
      //   174: astore 5
      //   176: aload 9
      //   178: aload 7
      //   180: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   183: pop
      //   184: aload_1
      //   185: astore 5
      //   187: aload 9
      //   189: aload 10
      //   191: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   194: pop
      //   195: aload_1
      //   196: astore 5
      //   198: aload 9
      //   200: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   203: astore 7
      //   205: goto -63 -> 142
      //   208: aload_1
      //   209: astore 5
      //   211: new 138	java/lang/StringBuilder
      //   214: astore 9
      //   216: aload_1
      //   217: astore 5
      //   219: aload 9
      //   221: invokespecial 139	java/lang/StringBuilder:<init>	()V
      //   224: aload_1
      //   225: astore 5
      //   227: aload 9
      //   229: aload 4
      //   231: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   234: pop
      //   235: aload_1
      //   236: astore 5
      //   238: aload 9
      //   240: ldc 146
      //   242: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   245: pop
      //   246: aload_1
      //   247: astore 5
      //   249: aload 9
      //   251: iload 6
      //   253: invokevirtual 149	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   256: pop
      //   257: aload_1
      //   258: astore 5
      //   260: aload 9
      //   262: ldc 146
      //   264: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   267: pop
      //   268: aload_1
      //   269: astore 5
      //   271: aload 9
      //   273: aload 7
      //   275: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   278: pop
      //   279: aload_1
      //   280: astore 5
      //   282: aload 9
      //   284: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   287: invokestatic 151	com/unity3d/player/k$a:a	(Ljava/lang/String;)V
      //   290: aload_1
      //   291: astore 5
      //   293: aload 8
      //   295: invokevirtual 152	java/io/BufferedReader:close	()V
      //   298: aload_1
      //   299: ifnull +109 -> 408
      //   302: aload_1
      //   303: invokevirtual 104	java/net/HttpURLConnection:disconnect	()V
      //   306: goto +102 -> 408
      //   309: astore 7
      //   311: goto +14 -> 325
      //   314: astore_1
      //   315: aload_2
      //   316: astore 5
      //   318: goto +78 -> 396
      //   321: astore 7
      //   323: aconst_null
      //   324: astore_1
      //   325: aload_1
      //   326: astore 5
      //   328: new 138	java/lang/StringBuilder
      //   331: astore 8
      //   333: aload_1
      //   334: astore 5
      //   336: aload 8
      //   338: invokespecial 139	java/lang/StringBuilder:<init>	()V
      //   341: aload_1
      //   342: astore 5
      //   344: aload 8
      //   346: aload 4
      //   348: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   351: pop
      //   352: aload_1
      //   353: astore 5
      //   355: aload 8
      //   357: ldc 146
      //   359: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   362: pop
      //   363: aload_1
      //   364: astore 5
      //   366: aload 8
      //   368: aload 7
      //   370: invokevirtual 153	java/lang/Exception:toString	()Ljava/lang/String;
      //   373: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   376: pop
      //   377: aload_1
      //   378: astore 5
      //   380: aload 8
      //   382: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   385: invokestatic 151	com/unity3d/player/k$a:a	(Ljava/lang/String;)V
      //   388: aload_1
      //   389: ifnull +19 -> 408
      //   392: goto -90 -> 302
      //   395: astore_1
      //   396: aload 5
      //   398: ifnull +8 -> 406
      //   401: aload 5
      //   403: invokevirtual 104	java/net/HttpURLConnection:disconnect	()V
      //   406: aload_1
      //   407: athrow
      //   408: iinc 3 1
      //   411: goto -396 -> 15
      //   414: aconst_null
      //   415: areturn
      //
      // Exception table:
      //   from	to	target	type
      //   60	67	309	java/lang/Exception
      //   70	76	309	java/lang/Exception
      //   79	85	309	java/lang/Exception
      //   96	102	309	java/lang/Exception
      //   105	110	309	java/lang/Exception
      //   113	118	309	java/lang/Exception
      //   121	128	309	java/lang/Exception
      //   131	138	309	java/lang/Exception
      //   145	152	309	java/lang/Exception
      //   160	165	309	java/lang/Exception
      //   168	173	309	java/lang/Exception
      //   176	184	309	java/lang/Exception
      //   187	195	309	java/lang/Exception
      //   198	205	309	java/lang/Exception
      //   211	216	309	java/lang/Exception
      //   219	224	309	java/lang/Exception
      //   227	235	309	java/lang/Exception
      //   238	246	309	java/lang/Exception
      //   249	257	309	java/lang/Exception
      //   260	268	309	java/lang/Exception
      //   271	279	309	java/lang/Exception
      //   282	290	309	java/lang/Exception
      //   293	298	309	java/lang/Exception
      //   36	57	314	finally
      //   36	57	321	java/lang/Exception
      //   60	67	395	finally
      //   70	76	395	finally
      //   79	85	395	finally
      //   96	102	395	finally
      //   105	110	395	finally
      //   113	118	395	finally
      //   121	128	395	finally
      //   131	138	395	finally
      //   145	152	395	finally
      //   160	165	395	finally
      //   168	173	395	finally
      //   176	184	395	finally
      //   187	195	395	finally
      //   198	205	395	finally
      //   211	216	395	finally
      //   219	224	395	finally
      //   227	235	395	finally
      //   238	246	395	finally
      //   249	257	395	finally
      //   260	268	395	finally
      //   271	279	395	finally
      //   282	290	395	finally
      //   293	298	395	finally
      //   328	333	395	finally
      //   336	341	395	finally
      //   344	352	395	finally
      //   355	363	395	finally
      //   366	377	395	finally
      //   380	388	395	finally
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.k
 * JD-Core Version:    0.6.0
 */