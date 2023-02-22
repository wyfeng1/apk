package com.unity3d.splash.services.core.cache;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import com.unity3d.splash.services.core.api.Request;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.request.IWebRequestProgressListener;
import com.unity3d.splash.services.core.request.WebRequest;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class CacheThreadHandler extends Handler
{
  private boolean _active = false;
  private boolean _canceled = false;
  private WebRequest _currentRequest = null;

  // ERROR //
  private void downloadFile(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 19	com/unity3d/splash/services/core/cache/CacheThreadHandler:_canceled	Z
    //   4: ifne +1142 -> 1146
    //   7: aload_1
    //   8: ifnull +1138 -> 1146
    //   11: aload_2
    //   12: ifnonnull +6 -> 18
    //   15: goto +1131 -> 1146
    //   18: new 38	java/io/File
    //   21: dup
    //   22: aload_2
    //   23: invokespecial 41	java/io/File:<init>	(Ljava/lang/String;)V
    //   26: astore 8
    //   28: new 43	java/lang/StringBuilder
    //   31: astore 9
    //   33: iload 7
    //   35: ifeq +57 -> 92
    //   38: aload 9
    //   40: ldc 45
    //   42: invokespecial 46	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   45: aload 9
    //   47: aload_1
    //   48: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   51: pop
    //   52: aload 9
    //   54: ldc 52
    //   56: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   59: pop
    //   60: aload 9
    //   62: aload_2
    //   63: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: pop
    //   67: aload 9
    //   69: ldc 54
    //   71: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   74: pop
    //   75: aload 9
    //   77: aload 8
    //   79: invokevirtual 58	java/io/File:length	()J
    //   82: invokevirtual 61	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   85: pop
    //   86: ldc 63
    //   88: astore_2
    //   89: goto +25 -> 114
    //   92: aload 9
    //   94: ldc 65
    //   96: invokespecial 46	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   99: aload 9
    //   101: aload_1
    //   102: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   105: pop
    //   106: aload 9
    //   108: ldc 52
    //   110: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   113: pop
    //   114: aload 9
    //   116: aload_2
    //   117: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   120: pop
    //   121: aload 9
    //   123: invokevirtual 69	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   126: invokestatic 74	com/unity3d/splash/services/core/log/DeviceLog:debug	(Ljava/lang/String;)V
    //   129: invokestatic 80	com/unity3d/splash/services/core/device/Device:isActiveNetworkConnected	()Z
    //   132: ifne +36 -> 168
    //   135: ldc 82
    //   137: invokestatic 74	com/unity3d/splash/services/core/log/DeviceLog:debug	(Ljava/lang/String;)V
    //   140: invokestatic 88	com/unity3d/splash/services/core/webview/WebViewApp:getCurrentApp	()Lcom/unity3d/splash/services/core/webview/WebViewApp;
    //   143: getstatic 94	com/unity3d/splash/services/core/webview/WebViewEventCategory:CACHE	Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;
    //   146: getstatic 100	com/unity3d/splash/services/core/cache/CacheEvent:DOWNLOAD_ERROR	Lcom/unity3d/splash/services/core/cache/CacheEvent;
    //   149: iconst_2
    //   150: anewarray 102	java/lang/Object
    //   153: dup
    //   154: iconst_0
    //   155: getstatic 108	com/unity3d/splash/services/core/cache/CacheError:NO_INTERNET	Lcom/unity3d/splash/services/core/cache/CacheError;
    //   158: aastore
    //   159: dup
    //   160: iconst_1
    //   161: aload_1
    //   162: aastore
    //   163: invokevirtual 112	com/unity3d/splash/services/core/webview/WebViewApp:sendEvent	(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z
    //   166: pop
    //   167: return
    //   168: aload_0
    //   169: iconst_1
    //   170: putfield 21	com/unity3d/splash/services/core/cache/CacheThreadHandler:_active	Z
    //   173: invokestatic 117	android/os/SystemClock:elapsedRealtime	()J
    //   176: lstore 10
    //   178: aconst_null
    //   179: astore 12
    //   181: aconst_null
    //   182: astore 13
    //   184: aconst_null
    //   185: astore 14
    //   187: aconst_null
    //   188: astore 15
    //   190: aconst_null
    //   191: astore 16
    //   193: aconst_null
    //   194: astore 9
    //   196: new 119	java/io/FileOutputStream
    //   199: astore_2
    //   200: aload_2
    //   201: aload 8
    //   203: iload 7
    //   205: invokespecial 122	java/io/FileOutputStream:<init>	(Ljava/io/File;Z)V
    //   208: aload_0
    //   209: aload_1
    //   210: iload_3
    //   211: iload 4
    //   213: aload 6
    //   215: invokespecial 126	com/unity3d/splash/services/core/cache/CacheThreadHandler:getWebRequest	(Ljava/lang/String;IILjava/util/HashMap;)Lcom/unity3d/splash/services/core/request/WebRequest;
    //   218: astore 9
    //   220: aload_0
    //   221: aload 9
    //   223: putfield 17	com/unity3d/splash/services/core/cache/CacheThreadHandler:_currentRequest	Lcom/unity3d/splash/services/core/request/WebRequest;
    //   226: new 6	com/unity3d/splash/services/core/cache/CacheThreadHandler$1
    //   229: astore 6
    //   231: aload 6
    //   233: aload_0
    //   234: aload 8
    //   236: iload 5
    //   238: invokespecial 129	com/unity3d/splash/services/core/cache/CacheThreadHandler$1:<init>	(Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;Ljava/io/File;I)V
    //   241: aload 9
    //   243: aload 6
    //   245: invokevirtual 135	com/unity3d/splash/services/core/request/WebRequest:setProgressListener	(Lcom/unity3d/splash/services/core/request/IWebRequestProgressListener;)V
    //   248: aload_0
    //   249: getfield 17	com/unity3d/splash/services/core/cache/CacheThreadHandler:_currentRequest	Lcom/unity3d/splash/services/core/request/WebRequest;
    //   252: aload_2
    //   253: invokevirtual 139	com/unity3d/splash/services/core/request/WebRequest:makeStreamRequest	(Ljava/io/OutputStream;)J
    //   256: lstore 17
    //   258: aload_0
    //   259: iconst_0
    //   260: putfield 21	com/unity3d/splash/services/core/cache/CacheThreadHandler:_active	Z
    //   263: aload_0
    //   264: getfield 17	com/unity3d/splash/services/core/cache/CacheThreadHandler:_currentRequest	Lcom/unity3d/splash/services/core/request/WebRequest;
    //   267: invokevirtual 142	com/unity3d/splash/services/core/request/WebRequest:getContentLength	()J
    //   270: lstore 19
    //   272: aload_0
    //   273: getfield 17	com/unity3d/splash/services/core/cache/CacheThreadHandler:_currentRequest	Lcom/unity3d/splash/services/core/request/WebRequest;
    //   276: invokevirtual 145	com/unity3d/splash/services/core/request/WebRequest:isCanceled	()Z
    //   279: istore 7
    //   281: aload_0
    //   282: getfield 17	com/unity3d/splash/services/core/cache/CacheThreadHandler:_currentRequest	Lcom/unity3d/splash/services/core/request/WebRequest;
    //   285: invokevirtual 149	com/unity3d/splash/services/core/request/WebRequest:getResponseCode	()I
    //   288: istore_3
    //   289: aload_0
    //   290: getfield 17	com/unity3d/splash/services/core/cache/CacheThreadHandler:_currentRequest	Lcom/unity3d/splash/services/core/request/WebRequest;
    //   293: invokevirtual 153	com/unity3d/splash/services/core/request/WebRequest:getResponseHeaders	()Ljava/util/Map;
    //   296: astore 6
    //   298: aload_0
    //   299: lload 10
    //   301: aload_1
    //   302: aload 8
    //   304: lload 17
    //   306: lload 19
    //   308: iload 7
    //   310: iload_3
    //   311: aload 6
    //   313: invokespecial 157	com/unity3d/splash/services/core/cache/CacheThreadHandler:postProcessDownload	(JLjava/lang/String;Ljava/io/File;JJZILjava/util/Map;)V
    //   316: aload_0
    //   317: aconst_null
    //   318: putfield 17	com/unity3d/splash/services/core/cache/CacheThreadHandler:_currentRequest	Lcom/unity3d/splash/services/core/request/WebRequest;
    //   321: aload_2
    //   322: invokevirtual 160	java/io/FileOutputStream:close	()V
    //   325: return
    //   326: astore_2
    //   327: ldc 162
    //   329: aload_2
    //   330: invokestatic 166	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   333: invokestatic 88	com/unity3d/splash/services/core/webview/WebViewApp:getCurrentApp	()Lcom/unity3d/splash/services/core/webview/WebViewApp;
    //   336: getstatic 94	com/unity3d/splash/services/core/webview/WebViewEventCategory:CACHE	Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;
    //   339: getstatic 100	com/unity3d/splash/services/core/cache/CacheEvent:DOWNLOAD_ERROR	Lcom/unity3d/splash/services/core/cache/CacheEvent;
    //   342: iconst_3
    //   343: anewarray 102	java/lang/Object
    //   346: dup
    //   347: iconst_0
    //   348: getstatic 169	com/unity3d/splash/services/core/cache/CacheError:FILE_IO_ERROR	Lcom/unity3d/splash/services/core/cache/CacheError;
    //   351: aastore
    //   352: dup
    //   353: iconst_1
    //   354: aload_1
    //   355: aastore
    //   356: dup
    //   357: iconst_2
    //   358: aload_2
    //   359: invokevirtual 172	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   362: aastore
    //   363: invokevirtual 112	com/unity3d/splash/services/core/webview/WebViewApp:sendEvent	(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z
    //   366: pop
    //   367: return
    //   368: astore 6
    //   370: goto +30 -> 400
    //   373: astore 6
    //   375: goto +30 -> 405
    //   378: astore 6
    //   380: goto +37 -> 417
    //   383: astore 6
    //   385: goto +44 -> 429
    //   388: astore 6
    //   390: goto +51 -> 441
    //   393: astore 6
    //   395: goto +58 -> 453
    //   398: astore 6
    //   400: goto +686 -> 1086
    //   403: astore 6
    //   405: aload 6
    //   407: astore 9
    //   409: aload_2
    //   410: astore 6
    //   412: goto +65 -> 477
    //   415: astore 6
    //   417: aload 6
    //   419: astore 9
    //   421: aload_2
    //   422: astore 6
    //   424: goto +175 -> 599
    //   427: astore 6
    //   429: aload 6
    //   431: astore 9
    //   433: aload_2
    //   434: astore 6
    //   436: goto +285 -> 721
    //   439: astore 6
    //   441: aload 6
    //   443: astore 9
    //   445: aload_2
    //   446: astore 6
    //   448: goto +395 -> 843
    //   451: astore 6
    //   453: aload 6
    //   455: astore 9
    //   457: aload_2
    //   458: astore 6
    //   460: goto +505 -> 965
    //   463: astore 6
    //   465: aload 9
    //   467: astore_2
    //   468: goto +618 -> 1086
    //   471: astore 9
    //   473: aload 12
    //   475: astore 6
    //   477: aload 6
    //   479: astore_2
    //   480: ldc 174
    //   482: aload 9
    //   484: invokestatic 166	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   487: aload 6
    //   489: astore_2
    //   490: aload_0
    //   491: iconst_0
    //   492: putfield 21	com/unity3d/splash/services/core/cache/CacheThreadHandler:_active	Z
    //   495: aload 6
    //   497: astore_2
    //   498: invokestatic 88	com/unity3d/splash/services/core/webview/WebViewApp:getCurrentApp	()Lcom/unity3d/splash/services/core/webview/WebViewApp;
    //   501: getstatic 94	com/unity3d/splash/services/core/webview/WebViewEventCategory:CACHE	Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;
    //   504: getstatic 100	com/unity3d/splash/services/core/cache/CacheEvent:DOWNLOAD_ERROR	Lcom/unity3d/splash/services/core/cache/CacheEvent;
    //   507: iconst_3
    //   508: anewarray 102	java/lang/Object
    //   511: dup
    //   512: iconst_0
    //   513: getstatic 177	com/unity3d/splash/services/core/cache/CacheError:NETWORK_ERROR	Lcom/unity3d/splash/services/core/cache/CacheError;
    //   516: aastore
    //   517: dup
    //   518: iconst_1
    //   519: aload_1
    //   520: aastore
    //   521: dup
    //   522: iconst_2
    //   523: aload 9
    //   525: invokevirtual 178	com/unity3d/splash/services/core/request/NetworkIOException:getMessage	()Ljava/lang/String;
    //   528: aastore
    //   529: invokevirtual 112	com/unity3d/splash/services/core/webview/WebViewApp:sendEvent	(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z
    //   532: pop
    //   533: aload_0
    //   534: aconst_null
    //   535: putfield 17	com/unity3d/splash/services/core/cache/CacheThreadHandler:_currentRequest	Lcom/unity3d/splash/services/core/request/WebRequest;
    //   538: aload 6
    //   540: ifnull +52 -> 592
    //   543: aload 6
    //   545: invokevirtual 160	java/io/FileOutputStream:close	()V
    //   548: goto +44 -> 592
    //   551: astore_2
    //   552: ldc 162
    //   554: aload_2
    //   555: invokestatic 166	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   558: invokestatic 88	com/unity3d/splash/services/core/webview/WebViewApp:getCurrentApp	()Lcom/unity3d/splash/services/core/webview/WebViewApp;
    //   561: getstatic 94	com/unity3d/splash/services/core/webview/WebViewEventCategory:CACHE	Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;
    //   564: getstatic 100	com/unity3d/splash/services/core/cache/CacheEvent:DOWNLOAD_ERROR	Lcom/unity3d/splash/services/core/cache/CacheEvent;
    //   567: iconst_3
    //   568: anewarray 102	java/lang/Object
    //   571: dup
    //   572: iconst_0
    //   573: getstatic 169	com/unity3d/splash/services/core/cache/CacheError:FILE_IO_ERROR	Lcom/unity3d/splash/services/core/cache/CacheError;
    //   576: aastore
    //   577: dup
    //   578: iconst_1
    //   579: aload_1
    //   580: aastore
    //   581: dup
    //   582: iconst_2
    //   583: aload_2
    //   584: invokevirtual 172	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   587: aastore
    //   588: invokevirtual 112	com/unity3d/splash/services/core/webview/WebViewApp:sendEvent	(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z
    //   591: pop
    //   592: return
    //   593: astore 9
    //   595: aload 13
    //   597: astore 6
    //   599: aload 6
    //   601: astore_2
    //   602: ldc 180
    //   604: aload 9
    //   606: invokestatic 166	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   609: aload 6
    //   611: astore_2
    //   612: aload_0
    //   613: iconst_0
    //   614: putfield 21	com/unity3d/splash/services/core/cache/CacheThreadHandler:_active	Z
    //   617: aload 6
    //   619: astore_2
    //   620: invokestatic 88	com/unity3d/splash/services/core/webview/WebViewApp:getCurrentApp	()Lcom/unity3d/splash/services/core/webview/WebViewApp;
    //   623: getstatic 94	com/unity3d/splash/services/core/webview/WebViewEventCategory:CACHE	Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;
    //   626: getstatic 100	com/unity3d/splash/services/core/cache/CacheEvent:DOWNLOAD_ERROR	Lcom/unity3d/splash/services/core/cache/CacheEvent;
    //   629: iconst_3
    //   630: anewarray 102	java/lang/Object
    //   633: dup
    //   634: iconst_0
    //   635: getstatic 183	com/unity3d/splash/services/core/cache/CacheError:ILLEGAL_STATE	Lcom/unity3d/splash/services/core/cache/CacheError;
    //   638: aastore
    //   639: dup
    //   640: iconst_1
    //   641: aload_1
    //   642: aastore
    //   643: dup
    //   644: iconst_2
    //   645: aload 9
    //   647: invokevirtual 184	java/lang/IllegalStateException:getMessage	()Ljava/lang/String;
    //   650: aastore
    //   651: invokevirtual 112	com/unity3d/splash/services/core/webview/WebViewApp:sendEvent	(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z
    //   654: pop
    //   655: aload_0
    //   656: aconst_null
    //   657: putfield 17	com/unity3d/splash/services/core/cache/CacheThreadHandler:_currentRequest	Lcom/unity3d/splash/services/core/request/WebRequest;
    //   660: aload 6
    //   662: ifnull +52 -> 714
    //   665: aload 6
    //   667: invokevirtual 160	java/io/FileOutputStream:close	()V
    //   670: goto +44 -> 714
    //   673: astore_2
    //   674: ldc 162
    //   676: aload_2
    //   677: invokestatic 166	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   680: invokestatic 88	com/unity3d/splash/services/core/webview/WebViewApp:getCurrentApp	()Lcom/unity3d/splash/services/core/webview/WebViewApp;
    //   683: getstatic 94	com/unity3d/splash/services/core/webview/WebViewEventCategory:CACHE	Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;
    //   686: getstatic 100	com/unity3d/splash/services/core/cache/CacheEvent:DOWNLOAD_ERROR	Lcom/unity3d/splash/services/core/cache/CacheEvent;
    //   689: iconst_3
    //   690: anewarray 102	java/lang/Object
    //   693: dup
    //   694: iconst_0
    //   695: getstatic 169	com/unity3d/splash/services/core/cache/CacheError:FILE_IO_ERROR	Lcom/unity3d/splash/services/core/cache/CacheError;
    //   698: aastore
    //   699: dup
    //   700: iconst_1
    //   701: aload_1
    //   702: aastore
    //   703: dup
    //   704: iconst_2
    //   705: aload_2
    //   706: invokevirtual 172	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   709: aastore
    //   710: invokevirtual 112	com/unity3d/splash/services/core/webview/WebViewApp:sendEvent	(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z
    //   713: pop
    //   714: return
    //   715: astore 9
    //   717: aload 14
    //   719: astore 6
    //   721: aload 6
    //   723: astore_2
    //   724: ldc 186
    //   726: aload 9
    //   728: invokestatic 166	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   731: aload 6
    //   733: astore_2
    //   734: aload_0
    //   735: iconst_0
    //   736: putfield 21	com/unity3d/splash/services/core/cache/CacheThreadHandler:_active	Z
    //   739: aload 6
    //   741: astore_2
    //   742: invokestatic 88	com/unity3d/splash/services/core/webview/WebViewApp:getCurrentApp	()Lcom/unity3d/splash/services/core/webview/WebViewApp;
    //   745: getstatic 94	com/unity3d/splash/services/core/webview/WebViewEventCategory:CACHE	Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;
    //   748: getstatic 100	com/unity3d/splash/services/core/cache/CacheEvent:DOWNLOAD_ERROR	Lcom/unity3d/splash/services/core/cache/CacheEvent;
    //   751: iconst_3
    //   752: anewarray 102	java/lang/Object
    //   755: dup
    //   756: iconst_0
    //   757: getstatic 169	com/unity3d/splash/services/core/cache/CacheError:FILE_IO_ERROR	Lcom/unity3d/splash/services/core/cache/CacheError;
    //   760: aastore
    //   761: dup
    //   762: iconst_1
    //   763: aload_1
    //   764: aastore
    //   765: dup
    //   766: iconst_2
    //   767: aload 9
    //   769: invokevirtual 187	java/io/IOException:getMessage	()Ljava/lang/String;
    //   772: aastore
    //   773: invokevirtual 112	com/unity3d/splash/services/core/webview/WebViewApp:sendEvent	(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z
    //   776: pop
    //   777: aload_0
    //   778: aconst_null
    //   779: putfield 17	com/unity3d/splash/services/core/cache/CacheThreadHandler:_currentRequest	Lcom/unity3d/splash/services/core/request/WebRequest;
    //   782: aload 6
    //   784: ifnull +52 -> 836
    //   787: aload 6
    //   789: invokevirtual 160	java/io/FileOutputStream:close	()V
    //   792: goto +44 -> 836
    //   795: astore_2
    //   796: ldc 162
    //   798: aload_2
    //   799: invokestatic 166	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   802: invokestatic 88	com/unity3d/splash/services/core/webview/WebViewApp:getCurrentApp	()Lcom/unity3d/splash/services/core/webview/WebViewApp;
    //   805: getstatic 94	com/unity3d/splash/services/core/webview/WebViewEventCategory:CACHE	Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;
    //   808: getstatic 100	com/unity3d/splash/services/core/cache/CacheEvent:DOWNLOAD_ERROR	Lcom/unity3d/splash/services/core/cache/CacheEvent;
    //   811: iconst_3
    //   812: anewarray 102	java/lang/Object
    //   815: dup
    //   816: iconst_0
    //   817: getstatic 169	com/unity3d/splash/services/core/cache/CacheError:FILE_IO_ERROR	Lcom/unity3d/splash/services/core/cache/CacheError;
    //   820: aastore
    //   821: dup
    //   822: iconst_1
    //   823: aload_1
    //   824: aastore
    //   825: dup
    //   826: iconst_2
    //   827: aload_2
    //   828: invokevirtual 172	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   831: aastore
    //   832: invokevirtual 112	com/unity3d/splash/services/core/webview/WebViewApp:sendEvent	(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z
    //   835: pop
    //   836: return
    //   837: astore 9
    //   839: aload 15
    //   841: astore 6
    //   843: aload 6
    //   845: astore_2
    //   846: ldc 189
    //   848: aload 9
    //   850: invokestatic 166	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   853: aload 6
    //   855: astore_2
    //   856: aload_0
    //   857: iconst_0
    //   858: putfield 21	com/unity3d/splash/services/core/cache/CacheThreadHandler:_active	Z
    //   861: aload 6
    //   863: astore_2
    //   864: invokestatic 88	com/unity3d/splash/services/core/webview/WebViewApp:getCurrentApp	()Lcom/unity3d/splash/services/core/webview/WebViewApp;
    //   867: getstatic 94	com/unity3d/splash/services/core/webview/WebViewEventCategory:CACHE	Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;
    //   870: getstatic 100	com/unity3d/splash/services/core/cache/CacheEvent:DOWNLOAD_ERROR	Lcom/unity3d/splash/services/core/cache/CacheEvent;
    //   873: iconst_3
    //   874: anewarray 102	java/lang/Object
    //   877: dup
    //   878: iconst_0
    //   879: getstatic 192	com/unity3d/splash/services/core/cache/CacheError:MALFORMED_URL	Lcom/unity3d/splash/services/core/cache/CacheError;
    //   882: aastore
    //   883: dup
    //   884: iconst_1
    //   885: aload_1
    //   886: aastore
    //   887: dup
    //   888: iconst_2
    //   889: aload 9
    //   891: invokevirtual 193	java/net/MalformedURLException:getMessage	()Ljava/lang/String;
    //   894: aastore
    //   895: invokevirtual 112	com/unity3d/splash/services/core/webview/WebViewApp:sendEvent	(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z
    //   898: pop
    //   899: aload_0
    //   900: aconst_null
    //   901: putfield 17	com/unity3d/splash/services/core/cache/CacheThreadHandler:_currentRequest	Lcom/unity3d/splash/services/core/request/WebRequest;
    //   904: aload 6
    //   906: ifnull +52 -> 958
    //   909: aload 6
    //   911: invokevirtual 160	java/io/FileOutputStream:close	()V
    //   914: goto +44 -> 958
    //   917: astore_2
    //   918: ldc 162
    //   920: aload_2
    //   921: invokestatic 166	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   924: invokestatic 88	com/unity3d/splash/services/core/webview/WebViewApp:getCurrentApp	()Lcom/unity3d/splash/services/core/webview/WebViewApp;
    //   927: getstatic 94	com/unity3d/splash/services/core/webview/WebViewEventCategory:CACHE	Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;
    //   930: getstatic 100	com/unity3d/splash/services/core/cache/CacheEvent:DOWNLOAD_ERROR	Lcom/unity3d/splash/services/core/cache/CacheEvent;
    //   933: iconst_3
    //   934: anewarray 102	java/lang/Object
    //   937: dup
    //   938: iconst_0
    //   939: getstatic 169	com/unity3d/splash/services/core/cache/CacheError:FILE_IO_ERROR	Lcom/unity3d/splash/services/core/cache/CacheError;
    //   942: aastore
    //   943: dup
    //   944: iconst_1
    //   945: aload_1
    //   946: aastore
    //   947: dup
    //   948: iconst_2
    //   949: aload_2
    //   950: invokevirtual 172	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   953: aastore
    //   954: invokevirtual 112	com/unity3d/splash/services/core/webview/WebViewApp:sendEvent	(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z
    //   957: pop
    //   958: return
    //   959: astore 9
    //   961: aload 16
    //   963: astore 6
    //   965: aload 6
    //   967: astore_2
    //   968: ldc 195
    //   970: aload 9
    //   972: invokestatic 166	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   975: aload 6
    //   977: astore_2
    //   978: aload_0
    //   979: iconst_0
    //   980: putfield 21	com/unity3d/splash/services/core/cache/CacheThreadHandler:_active	Z
    //   983: aload 6
    //   985: astore_2
    //   986: invokestatic 88	com/unity3d/splash/services/core/webview/WebViewApp:getCurrentApp	()Lcom/unity3d/splash/services/core/webview/WebViewApp;
    //   989: getstatic 94	com/unity3d/splash/services/core/webview/WebViewEventCategory:CACHE	Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;
    //   992: getstatic 100	com/unity3d/splash/services/core/cache/CacheEvent:DOWNLOAD_ERROR	Lcom/unity3d/splash/services/core/cache/CacheEvent;
    //   995: iconst_3
    //   996: anewarray 102	java/lang/Object
    //   999: dup
    //   1000: iconst_0
    //   1001: getstatic 169	com/unity3d/splash/services/core/cache/CacheError:FILE_IO_ERROR	Lcom/unity3d/splash/services/core/cache/CacheError;
    //   1004: aastore
    //   1005: dup
    //   1006: iconst_1
    //   1007: aload_1
    //   1008: aastore
    //   1009: dup
    //   1010: iconst_2
    //   1011: aload 9
    //   1013: invokevirtual 196	java/io/FileNotFoundException:getMessage	()Ljava/lang/String;
    //   1016: aastore
    //   1017: invokevirtual 112	com/unity3d/splash/services/core/webview/WebViewApp:sendEvent	(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z
    //   1020: pop
    //   1021: aload_0
    //   1022: aconst_null
    //   1023: putfield 17	com/unity3d/splash/services/core/cache/CacheThreadHandler:_currentRequest	Lcom/unity3d/splash/services/core/request/WebRequest;
    //   1026: aload 6
    //   1028: ifnull +52 -> 1080
    //   1031: aload 6
    //   1033: invokevirtual 160	java/io/FileOutputStream:close	()V
    //   1036: goto +44 -> 1080
    //   1039: astore_2
    //   1040: ldc 162
    //   1042: aload_2
    //   1043: invokestatic 166	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   1046: invokestatic 88	com/unity3d/splash/services/core/webview/WebViewApp:getCurrentApp	()Lcom/unity3d/splash/services/core/webview/WebViewApp;
    //   1049: getstatic 94	com/unity3d/splash/services/core/webview/WebViewEventCategory:CACHE	Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;
    //   1052: getstatic 100	com/unity3d/splash/services/core/cache/CacheEvent:DOWNLOAD_ERROR	Lcom/unity3d/splash/services/core/cache/CacheEvent;
    //   1055: iconst_3
    //   1056: anewarray 102	java/lang/Object
    //   1059: dup
    //   1060: iconst_0
    //   1061: getstatic 169	com/unity3d/splash/services/core/cache/CacheError:FILE_IO_ERROR	Lcom/unity3d/splash/services/core/cache/CacheError;
    //   1064: aastore
    //   1065: dup
    //   1066: iconst_1
    //   1067: aload_1
    //   1068: aastore
    //   1069: dup
    //   1070: iconst_2
    //   1071: aload_2
    //   1072: invokevirtual 172	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   1075: aastore
    //   1076: invokevirtual 112	com/unity3d/splash/services/core/webview/WebViewApp:sendEvent	(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z
    //   1079: pop
    //   1080: return
    //   1081: astore 6
    //   1083: goto -615 -> 468
    //   1086: aload_0
    //   1087: aconst_null
    //   1088: putfield 17	com/unity3d/splash/services/core/cache/CacheThreadHandler:_currentRequest	Lcom/unity3d/splash/services/core/request/WebRequest;
    //   1091: aload_2
    //   1092: ifnull +51 -> 1143
    //   1095: aload_2
    //   1096: invokevirtual 160	java/io/FileOutputStream:close	()V
    //   1099: goto +44 -> 1143
    //   1102: astore_2
    //   1103: ldc 162
    //   1105: aload_2
    //   1106: invokestatic 166	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   1109: invokestatic 88	com/unity3d/splash/services/core/webview/WebViewApp:getCurrentApp	()Lcom/unity3d/splash/services/core/webview/WebViewApp;
    //   1112: getstatic 94	com/unity3d/splash/services/core/webview/WebViewEventCategory:CACHE	Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;
    //   1115: getstatic 100	com/unity3d/splash/services/core/cache/CacheEvent:DOWNLOAD_ERROR	Lcom/unity3d/splash/services/core/cache/CacheEvent;
    //   1118: iconst_3
    //   1119: anewarray 102	java/lang/Object
    //   1122: dup
    //   1123: iconst_0
    //   1124: getstatic 169	com/unity3d/splash/services/core/cache/CacheError:FILE_IO_ERROR	Lcom/unity3d/splash/services/core/cache/CacheError;
    //   1127: aastore
    //   1128: dup
    //   1129: iconst_1
    //   1130: aload_1
    //   1131: aastore
    //   1132: dup
    //   1133: iconst_2
    //   1134: aload_2
    //   1135: invokevirtual 172	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   1138: aastore
    //   1139: invokevirtual 112	com/unity3d/splash/services/core/webview/WebViewApp:sendEvent	(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z
    //   1142: pop
    //   1143: aload 6
    //   1145: athrow
    //   1146: return
    //
    // Exception table:
    //   from	to	target	type
    //   321	325	326	java/lang/Exception
    //   298	316	368	finally
    //   298	316	373	com/unity3d/splash/services/core/request/NetworkIOException
    //   298	316	378	java/lang/IllegalStateException
    //   298	316	383	java/io/IOException
    //   298	316	388	java/net/MalformedURLException
    //   298	316	393	java/io/FileNotFoundException
    //   208	298	398	finally
    //   208	298	403	com/unity3d/splash/services/core/request/NetworkIOException
    //   208	298	415	java/lang/IllegalStateException
    //   208	298	427	java/io/IOException
    //   208	298	439	java/net/MalformedURLException
    //   208	298	451	java/io/FileNotFoundException
    //   196	208	463	finally
    //   196	208	471	com/unity3d/splash/services/core/request/NetworkIOException
    //   543	548	551	java/lang/Exception
    //   196	208	593	java/lang/IllegalStateException
    //   665	670	673	java/lang/Exception
    //   196	208	715	java/io/IOException
    //   787	792	795	java/lang/Exception
    //   196	208	837	java/net/MalformedURLException
    //   909	914	917	java/lang/Exception
    //   196	208	959	java/io/FileNotFoundException
    //   1031	1036	1039	java/lang/Exception
    //   480	487	1081	finally
    //   490	495	1081	finally
    //   498	533	1081	finally
    //   602	609	1081	finally
    //   612	617	1081	finally
    //   620	655	1081	finally
    //   724	731	1081	finally
    //   734	739	1081	finally
    //   742	777	1081	finally
    //   846	853	1081	finally
    //   856	861	1081	finally
    //   864	899	1081	finally
    //   968	975	1081	finally
    //   978	983	1081	finally
    //   986	1021	1081	finally
    //   1095	1099	1102	java/lang/Exception
  }

  private WebRequest getWebRequest(String paramString, int paramInt1, int paramInt2, HashMap paramHashMap)
  {
    HashMap localHashMap = new HashMap();
    if (paramHashMap != null)
      localHashMap.putAll(paramHashMap);
    return new WebRequest(paramString, "GET", localHashMap, paramInt1, paramInt2);
  }

  private void postProcessDownload(long paramLong1, String paramString, File paramFile, long paramLong2, long paramLong3, boolean paramBoolean, int paramInt, Map paramMap)
  {
    paramLong1 = SystemClock.elapsedRealtime() - paramLong1;
    if (!paramFile.setReadable(true, false))
      DeviceLog.debug("Unity Ads cache: could not set file readable!");
    if (!paramBoolean)
    {
      StringBuilder localStringBuilder = new StringBuilder("Unity Ads cache: File ");
      localStringBuilder.append(paramFile.getName());
      localStringBuilder.append(" of ");
      localStringBuilder.append(paramLong2);
      localStringBuilder.append(" bytes downloaded in ");
      localStringBuilder.append(paramLong1);
      localStringBuilder.append("ms");
      DeviceLog.debug(localStringBuilder.toString());
      WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.CACHE, CacheEvent.DOWNLOAD_END, new Object[] { paramString, Long.valueOf(paramLong2), Long.valueOf(paramLong3), Long.valueOf(paramLong1), Integer.valueOf(paramInt), Request.getResponseHeadersMap(paramMap) });
      return;
    }
    paramFile = new StringBuilder("Unity Ads cache: downloading of ");
    paramFile.append(paramString);
    paramFile.append(" stopped");
    DeviceLog.debug(paramFile.toString());
    WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.CACHE, CacheEvent.DOWNLOAD_STOPPED, new Object[] { paramString, Long.valueOf(paramLong2), Long.valueOf(paramLong3), Long.valueOf(paramLong1), Integer.valueOf(paramInt), Request.getResponseHeadersMap(paramMap) });
  }

  public void handleMessage(Message paramMessage)
  {
    Object localObject = paramMessage.getData();
    String str1 = ((Bundle)localObject).getString("source");
    ((Bundle)localObject).remove("source");
    String str2 = ((Bundle)localObject).getString("target");
    ((Bundle)localObject).remove("target");
    int i = ((Bundle)localObject).getInt("connectTimeout");
    ((Bundle)localObject).remove("connectTimeout");
    int j = ((Bundle)localObject).getInt("readTimeout");
    ((Bundle)localObject).remove("readTimeout");
    int k = ((Bundle)localObject).getInt("progressInterval");
    ((Bundle)localObject).remove("progressInterval");
    boolean bool = ((Bundle)localObject).getBoolean("append", false);
    ((Bundle)localObject).remove("append");
    HashMap localHashMap;
    if (((Bundle)localObject).size() > 0)
    {
      DeviceLog.debug("There are headers left in data, reading them");
      localHashMap = new HashMap();
      Iterator localIterator = ((Bundle)localObject).keySet().iterator();
      while (localIterator.hasNext())
      {
        String str3 = (String)localIterator.next();
        localHashMap.put(str3, Arrays.asList(((Bundle)localObject).getStringArray(str3)));
      }
    }
    else
    {
      localHashMap = null;
    }
    localObject = new File(str2);
    if (((bool) && (!((File)localObject).exists())) || ((!bool) && (((File)localObject).exists())))
    {
      this._active = false;
      WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.CACHE, CacheEvent.DOWNLOAD_ERROR, new Object[] { CacheError.FILE_STATE_WRONG, str1, str2, Boolean.valueOf(bool), Boolean.valueOf(((File)localObject).exists()) });
      return;
    }
    if (paramMessage.what == 1)
      downloadFile(str1, str2, i, j, k, localHashMap, bool);
  }

  public boolean isActive()
  {
    return this._active;
  }

  public void setCancelStatus(boolean paramBoolean)
  {
    this._canceled = paramBoolean;
    if (paramBoolean)
    {
      WebRequest localWebRequest = this._currentRequest;
      if (localWebRequest != null)
      {
        this._active = false;
        localWebRequest.cancel();
      }
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.cache.CacheThreadHandler
 * JD-Core Version:    0.6.0
 */