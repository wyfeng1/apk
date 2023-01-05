.class Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;
.super Landroid/os/Handler;


# instance fields
.field private _active:Z

.field private _canceled:Z

.field private _currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;


# direct methods
.method constructor <init>()V
    .registers 2

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_canceled:Z

    iput-boolean v0, p0, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_active:Z

    return-void
.end method

.method private downloadFile(Ljava/lang/String;Ljava/lang/String;IIILjava/util/HashMap;Z)V
    .registers 31

    move-object/from16 v13, p0

    move-object/from16 v14, p1

    move-object/from16 v0, p2

    move/from16 v1, p7

    const-string v15, "Error closing stream"

    iget-boolean v2, v13, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_canceled:Z

    if-nez v2, :cond_326

    if-eqz v14, :cond_326

    if-nez v0, :cond_14

    goto/16 :goto_326

    :cond_14
    new-instance v5, Ljava/io/File;

    invoke-direct {v5, v0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    const-string v2, " to "

    new-instance v3, Ljava/lang/StringBuilder;

    if-eqz v1, :cond_3c

    const-string v4, "Unity Ads cache: resuming download "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v0, " at "

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/io/File;->length()J

    move-result-wide v6

    invoke-virtual {v3, v6, v7}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    const-string v0, " bytes"

    goto :goto_47

    :cond_3c
    const-string v4, "Unity Ads cache: start downloading "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    :goto_47
    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->debug(Ljava/lang/String;)V

    invoke-static {}, Lcom/unity3d/splash/services/core/device/Device;->isActiveNetworkConnected()Z

    move-result v0

    const/4 v12, 0x2

    const/4 v11, 0x1

    const/4 v10, 0x0

    if-nez v0, :cond_73

    const-string v0, "Unity Ads cache: download cancelled, no internet connection available"

    invoke-static {v0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->debug(Ljava/lang/String;)V

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v0

    sget-object v1, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->CACHE:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v2, Lcom/unity3d/splash/services/core/cache/CacheEvent;->DOWNLOAD_ERROR:Lcom/unity3d/splash/services/core/cache/CacheEvent;

    new-array v3, v12, [Ljava/lang/Object;

    sget-object v4, Lcom/unity3d/splash/services/core/cache/CacheError;->NO_INTERNET:Lcom/unity3d/splash/services/core/cache/CacheError;

    aput-object v4, v3, v10

    aput-object v14, v3, v11

    invoke-virtual {v0, v1, v2, v3}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    return-void

    :cond_73
    iput-boolean v11, v13, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_active:Z

    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v2

    const/4 v8, 0x0

    const/4 v9, 0x3

    :try_start_7b
    new-instance v6, Ljava/io/FileOutputStream;

    invoke-direct {v6, v5, v1}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;Z)V
    :try_end_80
    .catch Ljava/io/FileNotFoundException; {:try_start_7b .. :try_end_80} :catch_2ad
    .catch Ljava/net/MalformedURLException; {:try_start_7b .. :try_end_80} :catch_25e
    .catch Ljava/io/IOException; {:try_start_7b .. :try_end_80} :catch_20f
    .catch Ljava/lang/IllegalStateException; {:try_start_7b .. :try_end_80} :catch_1c0
    .catch Lcom/unity3d/splash/services/core/request/NetworkIOException; {:try_start_7b .. :try_end_80} :catch_171
    .catchall {:try_start_7b .. :try_end_80} :catchall_166

    move/from16 v0, p3

    move/from16 v1, p4

    move-object/from16 v4, p6

    :try_start_86
    invoke-direct {v13, v14, v0, v1, v4}, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->getWebRequest(Ljava/lang/String;IILjava/util/HashMap;)Lcom/unity3d/splash/services/core/request/WebRequest;

    move-result-object v0

    iput-object v0, v13, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    new-instance v1, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler$1;

    move/from16 v4, p5

    invoke-direct {v1, v13, v5, v4}, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler$1;-><init>(Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;Ljava/io/File;I)V

    invoke-virtual {v0, v1}, Lcom/unity3d/splash/services/core/request/WebRequest;->setProgressListener(Lcom/unity3d/splash/services/core/request/IWebRequestProgressListener;)V

    iget-object v0, v13, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    invoke-virtual {v0, v6}, Lcom/unity3d/splash/services/core/request/WebRequest;->makeStreamRequest(Ljava/io/OutputStream;)J

    move-result-wide v16

    iput-boolean v10, v13, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_active:Z

    iget-object v0, v13, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    invoke-virtual {v0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getContentLength()J

    move-result-wide v18

    iget-object v0, v13, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    invoke-virtual {v0}, Lcom/unity3d/splash/services/core/request/WebRequest;->isCanceled()Z

    move-result v0

    iget-object v1, v13, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    invoke-virtual {v1}, Lcom/unity3d/splash/services/core/request/WebRequest;->getResponseCode()I

    move-result v20

    iget-object v1, v13, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    invoke-virtual {v1}, Lcom/unity3d/splash/services/core/request/WebRequest;->getResponseHeaders()Ljava/util/Map;

    move-result-object v21
    :try_end_b6
    .catch Ljava/io/FileNotFoundException; {:try_start_86 .. :try_end_b6} :catch_158
    .catch Ljava/net/MalformedURLException; {:try_start_86 .. :try_end_b6} :catch_14a
    .catch Ljava/io/IOException; {:try_start_86 .. :try_end_b6} :catch_13c
    .catch Ljava/lang/IllegalStateException; {:try_start_86 .. :try_end_b6} :catch_12e
    .catch Lcom/unity3d/splash/services/core/request/NetworkIOException; {:try_start_86 .. :try_end_b6} :catch_121
    .catchall {:try_start_86 .. :try_end_b6} :catchall_112

    move-object/from16 v1, p0

    move-object/from16 v4, p1

    move-object/from16 v22, v6

    move-wide/from16 v6, v16

    move-object v14, v8

    move-wide/from16 v8, v18

    move v10, v0

    const/16 v16, 0x1

    move/from16 v11, v20

    const/16 v17, 0x2

    move-object/from16 v12, v21

    :try_start_ca
    invoke-direct/range {v1 .. v12}, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->postProcessDownload(JLjava/lang/String;Ljava/io/File;JJZILjava/util/Map;)V
    :try_end_cd
    .catch Ljava/io/FileNotFoundException; {:try_start_ca .. :try_end_cd} :catch_10d
    .catch Ljava/net/MalformedURLException; {:try_start_ca .. :try_end_cd} :catch_108
    .catch Ljava/io/IOException; {:try_start_ca .. :try_end_cd} :catch_103
    .catch Ljava/lang/IllegalStateException; {:try_start_ca .. :try_end_cd} :catch_fe
    .catch Lcom/unity3d/splash/services/core/request/NetworkIOException; {:try_start_ca .. :try_end_cd} :catch_f9
    .catchall {:try_start_ca .. :try_end_cd} :catchall_f4

    iput-object v14, v13, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    :try_start_cf
    invoke-virtual/range {v22 .. v22}, Ljava/io/FileOutputStream;->close()V
    :try_end_d2
    .catch Ljava/lang/Exception; {:try_start_cf .. :try_end_d2} :catch_d3

    return-void

    :catch_d3
    move-exception v0

    move-object v1, v0

    invoke-static {v15, v1}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v0

    sget-object v2, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->CACHE:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v3, Lcom/unity3d/splash/services/core/cache/CacheEvent;->DOWNLOAD_ERROR:Lcom/unity3d/splash/services/core/cache/CacheEvent;

    const/4 v4, 0x3

    new-array v4, v4, [Ljava/lang/Object;

    sget-object v5, Lcom/unity3d/splash/services/core/cache/CacheError;->FILE_IO_ERROR:Lcom/unity3d/splash/services/core/cache/CacheError;

    const/4 v6, 0x0

    aput-object v5, v4, v6

    aput-object p1, v4, v16

    invoke-virtual {v1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v1

    aput-object v1, v4, v17

    invoke-virtual {v0, v2, v3, v4}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    return-void

    :catchall_f4
    move-exception v0

    move-object v1, v14

    const/4 v4, 0x3

    const/4 v6, 0x0

    goto :goto_11c

    :catch_f9
    move-exception v0

    move-object v1, v14

    const/4 v4, 0x3

    const/4 v6, 0x0

    goto :goto_12b

    :catch_fe
    move-exception v0

    move-object v1, v14

    const/4 v4, 0x3

    const/4 v6, 0x0

    goto :goto_138

    :catch_103
    move-exception v0

    move-object v1, v14

    const/4 v4, 0x3

    const/4 v6, 0x0

    goto :goto_146

    :catch_108
    move-exception v0

    move-object v1, v14

    const/4 v4, 0x3

    const/4 v6, 0x0

    goto :goto_154

    :catch_10d
    move-exception v0

    move-object v1, v14

    const/4 v4, 0x3

    const/4 v6, 0x0

    goto :goto_162

    :catchall_112
    move-exception v0

    move-object/from16 v22, v6

    move-object v1, v8

    const/4 v4, 0x3

    const/4 v6, 0x0

    const/16 v16, 0x1

    const/16 v17, 0x2

    :goto_11c
    move-object v2, v0

    move-object/from16 v8, v22

    goto/16 :goto_2ff

    :catch_121
    move-exception v0

    move-object/from16 v22, v6

    move-object v1, v8

    const/4 v4, 0x3

    const/4 v6, 0x0

    const/16 v16, 0x1

    const/16 v17, 0x2

    :goto_12b
    move-object/from16 v8, v22

    goto :goto_179

    :catch_12e
    move-exception v0

    move-object/from16 v22, v6

    move-object v1, v8

    const/4 v4, 0x3

    const/4 v6, 0x0

    const/16 v16, 0x1

    const/16 v17, 0x2

    :goto_138
    move-object/from16 v8, v22

    goto/16 :goto_1c8

    :catch_13c
    move-exception v0

    move-object/from16 v22, v6

    move-object v1, v8

    const/4 v4, 0x3

    const/4 v6, 0x0

    const/16 v16, 0x1

    const/16 v17, 0x2

    :goto_146
    move-object/from16 v8, v22

    goto/16 :goto_217

    :catch_14a
    move-exception v0

    move-object/from16 v22, v6

    move-object v1, v8

    const/4 v4, 0x3

    const/4 v6, 0x0

    const/16 v16, 0x1

    const/16 v17, 0x2

    :goto_154
    move-object/from16 v8, v22

    goto/16 :goto_266

    :catch_158
    move-exception v0

    move-object/from16 v22, v6

    move-object v1, v8

    const/4 v4, 0x3

    const/4 v6, 0x0

    const/16 v16, 0x1

    const/16 v17, 0x2

    :goto_162
    move-object/from16 v8, v22

    goto/16 :goto_2b5

    :catchall_166
    move-exception v0

    move-object v1, v8

    const/4 v4, 0x3

    const/4 v6, 0x0

    const/16 v16, 0x1

    const/16 v17, 0x2

    :goto_16e
    move-object v2, v0

    goto/16 :goto_2ff

    :catch_171
    move-exception v0

    move-object v1, v8

    const/4 v4, 0x3

    const/4 v6, 0x0

    const/16 v16, 0x1

    const/16 v17, 0x2

    :goto_179
    :try_start_179
    const-string v2, "Network error"

    invoke-static {v2, v0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    iput-boolean v6, v13, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_active:Z

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v2

    sget-object v3, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->CACHE:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v5, Lcom/unity3d/splash/services/core/cache/CacheEvent;->DOWNLOAD_ERROR:Lcom/unity3d/splash/services/core/cache/CacheEvent;

    new-array v7, v4, [Ljava/lang/Object;

    sget-object v9, Lcom/unity3d/splash/services/core/cache/CacheError;->NETWORK_ERROR:Lcom/unity3d/splash/services/core/cache/CacheError;

    aput-object v9, v7, v6

    aput-object p1, v7, v16

    invoke-virtual {v0}, Lcom/unity3d/splash/services/core/request/NetworkIOException;->getMessage()Ljava/lang/String;

    move-result-object v0

    aput-object v0, v7, v17

    invoke-virtual {v2, v3, v5, v7}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z
    :try_end_199
    .catchall {:try_start_179 .. :try_end_199} :catchall_2fc

    iput-object v1, v13, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    if-eqz v8, :cond_1bf

    :try_start_19d
    invoke-virtual {v8}, Ljava/io/FileOutputStream;->close()V
    :try_end_1a0
    .catch Ljava/lang/Exception; {:try_start_19d .. :try_end_1a0} :catch_1a1

    goto :goto_1bf

    :catch_1a1
    move-exception v0

    move-object v1, v0

    invoke-static {v15, v1}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v0

    sget-object v2, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->CACHE:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v3, Lcom/unity3d/splash/services/core/cache/CacheEvent;->DOWNLOAD_ERROR:Lcom/unity3d/splash/services/core/cache/CacheEvent;

    new-array v4, v4, [Ljava/lang/Object;

    sget-object v5, Lcom/unity3d/splash/services/core/cache/CacheError;->FILE_IO_ERROR:Lcom/unity3d/splash/services/core/cache/CacheError;

    aput-object v5, v4, v6

    aput-object p1, v4, v16

    invoke-virtual {v1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v1

    aput-object v1, v4, v17

    invoke-virtual {v0, v2, v3, v4}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_1bf
    :goto_1bf
    return-void

    :catch_1c0
    move-exception v0

    move-object v1, v8

    const/4 v4, 0x3

    const/4 v6, 0x0

    const/16 v16, 0x1

    const/16 v17, 0x2

    :goto_1c8
    :try_start_1c8
    const-string v2, "Illegal state"

    invoke-static {v2, v0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    iput-boolean v6, v13, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_active:Z

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v2

    sget-object v3, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->CACHE:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v5, Lcom/unity3d/splash/services/core/cache/CacheEvent;->DOWNLOAD_ERROR:Lcom/unity3d/splash/services/core/cache/CacheEvent;

    new-array v7, v4, [Ljava/lang/Object;

    sget-object v9, Lcom/unity3d/splash/services/core/cache/CacheError;->ILLEGAL_STATE:Lcom/unity3d/splash/services/core/cache/CacheError;

    aput-object v9, v7, v6

    aput-object p1, v7, v16

    invoke-virtual {v0}, Ljava/lang/IllegalStateException;->getMessage()Ljava/lang/String;

    move-result-object v0

    aput-object v0, v7, v17

    invoke-virtual {v2, v3, v5, v7}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z
    :try_end_1e8
    .catchall {:try_start_1c8 .. :try_end_1e8} :catchall_2fc

    iput-object v1, v13, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    if-eqz v8, :cond_20e

    :try_start_1ec
    invoke-virtual {v8}, Ljava/io/FileOutputStream;->close()V
    :try_end_1ef
    .catch Ljava/lang/Exception; {:try_start_1ec .. :try_end_1ef} :catch_1f0

    goto :goto_20e

    :catch_1f0
    move-exception v0

    move-object v1, v0

    invoke-static {v15, v1}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v0

    sget-object v2, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->CACHE:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v3, Lcom/unity3d/splash/services/core/cache/CacheEvent;->DOWNLOAD_ERROR:Lcom/unity3d/splash/services/core/cache/CacheEvent;

    new-array v4, v4, [Ljava/lang/Object;

    sget-object v5, Lcom/unity3d/splash/services/core/cache/CacheError;->FILE_IO_ERROR:Lcom/unity3d/splash/services/core/cache/CacheError;

    aput-object v5, v4, v6

    aput-object p1, v4, v16

    invoke-virtual {v1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v1

    aput-object v1, v4, v17

    invoke-virtual {v0, v2, v3, v4}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_20e
    :goto_20e
    return-void

    :catch_20f
    move-exception v0

    move-object v1, v8

    const/4 v4, 0x3

    const/4 v6, 0x0

    const/16 v16, 0x1

    const/16 v17, 0x2

    :goto_217
    :try_start_217
    const-string v2, "Couldn\'t request stream"

    invoke-static {v2, v0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    iput-boolean v6, v13, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_active:Z

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v2

    sget-object v3, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->CACHE:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v5, Lcom/unity3d/splash/services/core/cache/CacheEvent;->DOWNLOAD_ERROR:Lcom/unity3d/splash/services/core/cache/CacheEvent;

    new-array v7, v4, [Ljava/lang/Object;

    sget-object v9, Lcom/unity3d/splash/services/core/cache/CacheError;->FILE_IO_ERROR:Lcom/unity3d/splash/services/core/cache/CacheError;

    aput-object v9, v7, v6

    aput-object p1, v7, v16

    invoke-virtual {v0}, Ljava/io/IOException;->getMessage()Ljava/lang/String;

    move-result-object v0

    aput-object v0, v7, v17

    invoke-virtual {v2, v3, v5, v7}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z
    :try_end_237
    .catchall {:try_start_217 .. :try_end_237} :catchall_2fc

    iput-object v1, v13, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    if-eqz v8, :cond_25d

    :try_start_23b
    invoke-virtual {v8}, Ljava/io/FileOutputStream;->close()V
    :try_end_23e
    .catch Ljava/lang/Exception; {:try_start_23b .. :try_end_23e} :catch_23f

    goto :goto_25d

    :catch_23f
    move-exception v0

    move-object v1, v0

    invoke-static {v15, v1}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v0

    sget-object v2, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->CACHE:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v3, Lcom/unity3d/splash/services/core/cache/CacheEvent;->DOWNLOAD_ERROR:Lcom/unity3d/splash/services/core/cache/CacheEvent;

    new-array v4, v4, [Ljava/lang/Object;

    sget-object v5, Lcom/unity3d/splash/services/core/cache/CacheError;->FILE_IO_ERROR:Lcom/unity3d/splash/services/core/cache/CacheError;

    aput-object v5, v4, v6

    aput-object p1, v4, v16

    invoke-virtual {v1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v1

    aput-object v1, v4, v17

    invoke-virtual {v0, v2, v3, v4}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_25d
    :goto_25d
    return-void

    :catch_25e
    move-exception v0

    move-object v1, v8

    const/4 v4, 0x3

    const/4 v6, 0x0

    const/16 v16, 0x1

    const/16 v17, 0x2

    :goto_266
    :try_start_266
    const-string v2, "Malformed URL"

    invoke-static {v2, v0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    iput-boolean v6, v13, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_active:Z

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v2

    sget-object v3, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->CACHE:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v5, Lcom/unity3d/splash/services/core/cache/CacheEvent;->DOWNLOAD_ERROR:Lcom/unity3d/splash/services/core/cache/CacheEvent;

    new-array v7, v4, [Ljava/lang/Object;

    sget-object v9, Lcom/unity3d/splash/services/core/cache/CacheError;->MALFORMED_URL:Lcom/unity3d/splash/services/core/cache/CacheError;

    aput-object v9, v7, v6

    aput-object p1, v7, v16

    invoke-virtual {v0}, Ljava/net/MalformedURLException;->getMessage()Ljava/lang/String;

    move-result-object v0

    aput-object v0, v7, v17

    invoke-virtual {v2, v3, v5, v7}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z
    :try_end_286
    .catchall {:try_start_266 .. :try_end_286} :catchall_2fc

    iput-object v1, v13, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    if-eqz v8, :cond_2ac

    :try_start_28a
    invoke-virtual {v8}, Ljava/io/FileOutputStream;->close()V
    :try_end_28d
    .catch Ljava/lang/Exception; {:try_start_28a .. :try_end_28d} :catch_28e

    goto :goto_2ac

    :catch_28e
    move-exception v0

    move-object v1, v0

    invoke-static {v15, v1}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v0

    sget-object v2, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->CACHE:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v3, Lcom/unity3d/splash/services/core/cache/CacheEvent;->DOWNLOAD_ERROR:Lcom/unity3d/splash/services/core/cache/CacheEvent;

    new-array v4, v4, [Ljava/lang/Object;

    sget-object v5, Lcom/unity3d/splash/services/core/cache/CacheError;->FILE_IO_ERROR:Lcom/unity3d/splash/services/core/cache/CacheError;

    aput-object v5, v4, v6

    aput-object p1, v4, v16

    invoke-virtual {v1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v1

    aput-object v1, v4, v17

    invoke-virtual {v0, v2, v3, v4}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_2ac
    :goto_2ac
    return-void

    :catch_2ad
    move-exception v0

    move-object v1, v8

    const/4 v4, 0x3

    const/4 v6, 0x0

    const/16 v16, 0x1

    const/16 v17, 0x2

    :goto_2b5
    :try_start_2b5
    const-string v2, "Couldn\'t create target file"

    invoke-static {v2, v0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    iput-boolean v6, v13, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_active:Z

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v2

    sget-object v3, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->CACHE:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v5, Lcom/unity3d/splash/services/core/cache/CacheEvent;->DOWNLOAD_ERROR:Lcom/unity3d/splash/services/core/cache/CacheEvent;

    new-array v7, v4, [Ljava/lang/Object;

    sget-object v9, Lcom/unity3d/splash/services/core/cache/CacheError;->FILE_IO_ERROR:Lcom/unity3d/splash/services/core/cache/CacheError;

    aput-object v9, v7, v6

    aput-object p1, v7, v16

    invoke-virtual {v0}, Ljava/io/FileNotFoundException;->getMessage()Ljava/lang/String;

    move-result-object v0

    aput-object v0, v7, v17

    invoke-virtual {v2, v3, v5, v7}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z
    :try_end_2d5
    .catchall {:try_start_2b5 .. :try_end_2d5} :catchall_2fc

    iput-object v1, v13, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    if-eqz v8, :cond_2fb

    :try_start_2d9
    invoke-virtual {v8}, Ljava/io/FileOutputStream;->close()V
    :try_end_2dc
    .catch Ljava/lang/Exception; {:try_start_2d9 .. :try_end_2dc} :catch_2dd

    goto :goto_2fb

    :catch_2dd
    move-exception v0

    move-object v1, v0

    invoke-static {v15, v1}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v0

    sget-object v2, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->CACHE:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v3, Lcom/unity3d/splash/services/core/cache/CacheEvent;->DOWNLOAD_ERROR:Lcom/unity3d/splash/services/core/cache/CacheEvent;

    new-array v4, v4, [Ljava/lang/Object;

    sget-object v5, Lcom/unity3d/splash/services/core/cache/CacheError;->FILE_IO_ERROR:Lcom/unity3d/splash/services/core/cache/CacheError;

    aput-object v5, v4, v6

    aput-object p1, v4, v16

    invoke-virtual {v1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v1

    aput-object v1, v4, v17

    invoke-virtual {v0, v2, v3, v4}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_2fb
    :goto_2fb
    return-void

    :catchall_2fc
    move-exception v0

    goto/16 :goto_16e

    :goto_2ff
    iput-object v1, v13, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    if-eqz v8, :cond_325

    :try_start_303
    invoke-virtual {v8}, Ljava/io/FileOutputStream;->close()V
    :try_end_306
    .catch Ljava/lang/Exception; {:try_start_303 .. :try_end_306} :catch_307

    goto :goto_325

    :catch_307
    move-exception v0

    move-object v1, v0

    invoke-static {v15, v1}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v0

    sget-object v3, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->CACHE:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v5, Lcom/unity3d/splash/services/core/cache/CacheEvent;->DOWNLOAD_ERROR:Lcom/unity3d/splash/services/core/cache/CacheEvent;

    new-array v4, v4, [Ljava/lang/Object;

    sget-object v7, Lcom/unity3d/splash/services/core/cache/CacheError;->FILE_IO_ERROR:Lcom/unity3d/splash/services/core/cache/CacheError;

    aput-object v7, v4, v6

    aput-object p1, v4, v16

    invoke-virtual {v1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v1

    aput-object v1, v4, v17

    invoke-virtual {v0, v3, v5, v4}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_325
    :goto_325
    throw v2

    :cond_326
    :goto_326
    return-void
.end method

.method private getWebRequest(Ljava/lang/String;IILjava/util/HashMap;)Lcom/unity3d/splash/services/core/request/WebRequest;
    .registers 11

    new-instance v3, Ljava/util/HashMap;

    invoke-direct {v3}, Ljava/util/HashMap;-><init>()V

    if-eqz p4, :cond_a

    invoke-virtual {v3, p4}, Ljava/util/HashMap;->putAll(Ljava/util/Map;)V

    :cond_a
    new-instance p4, Lcom/unity3d/splash/services/core/request/WebRequest;

    const-string v2, "GET"

    move-object v0, p4

    move-object v1, p1

    move v4, p2

    move v5, p3

    invoke-direct/range {v0 .. v5}, Lcom/unity3d/splash/services/core/request/WebRequest;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;II)V

    return-object p4
.end method

.method private postProcessDownload(JLjava/lang/String;Ljava/io/File;JJZILjava/util/Map;)V
    .registers 27

    move-object/from16 v0, p3

    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v1

    sub-long v1, v1, p1

    const/4 v3, 0x1

    const/4 v4, 0x0

    move-object/from16 v5, p4

    invoke-virtual {v5, v3, v4}, Ljava/io/File;->setReadable(ZZ)Z

    move-result v6

    if-nez v6, :cond_17

    const-string v6, "Unity Ads cache: could not set file readable!"

    invoke-static {v6}, Lcom/unity3d/splash/services/core/log/DeviceLog;->debug(Ljava/lang/String;)V

    :cond_17
    const/4 v6, 0x5

    const/4 v7, 0x4

    const/4 v8, 0x3

    const/4 v9, 0x2

    const/4 v10, 0x6

    if-nez p9, :cond_78

    new-instance v11, Ljava/lang/StringBuilder;

    const-string v12, "Unity Ads cache: File "

    invoke-direct {v11, v12}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual/range {p4 .. p4}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v11, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v5, " of "

    invoke-virtual {v11, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-wide/from16 v12, p5

    invoke-virtual {v11, v12, v13}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    const-string v5, " bytes downloaded in "

    invoke-virtual {v11, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v11, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    const-string v5, "ms"

    invoke-virtual {v11, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v5}, Lcom/unity3d/splash/services/core/log/DeviceLog;->debug(Ljava/lang/String;)V

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v5

    sget-object v11, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->CACHE:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v14, Lcom/unity3d/splash/services/core/cache/CacheEvent;->DOWNLOAD_END:Lcom/unity3d/splash/services/core/cache/CacheEvent;

    new-array v10, v10, [Ljava/lang/Object;

    aput-object v0, v10, v4

    invoke-static/range {p5 .. p6}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v0

    aput-object v0, v10, v3

    invoke-static/range {p7 .. p8}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v0

    aput-object v0, v10, v9

    invoke-static {v1, v2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v0

    aput-object v0, v10, v8

    invoke-static/range {p10 .. p10}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    aput-object v0, v10, v7

    invoke-static/range {p11 .. p11}, Lcom/unity3d/splash/services/core/api/Request;->getResponseHeadersMap(Ljava/util/Map;)Lorg/json/JSONArray;

    move-result-object v0

    aput-object v0, v10, v6

    invoke-virtual {v5, v11, v14, v10}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    return-void

    :cond_78
    move-wide/from16 v12, p5

    new-instance v5, Ljava/lang/StringBuilder;

    const-string v11, "Unity Ads cache: downloading of "

    invoke-direct {v5, v11}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v11, " stopped"

    invoke-virtual {v5, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v5}, Lcom/unity3d/splash/services/core/log/DeviceLog;->debug(Ljava/lang/String;)V

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v5

    sget-object v11, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->CACHE:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v14, Lcom/unity3d/splash/services/core/cache/CacheEvent;->DOWNLOAD_STOPPED:Lcom/unity3d/splash/services/core/cache/CacheEvent;

    new-array v10, v10, [Ljava/lang/Object;

    aput-object v0, v10, v4

    invoke-static/range {p5 .. p6}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v0

    aput-object v0, v10, v3

    invoke-static/range {p7 .. p8}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v0

    aput-object v0, v10, v9

    invoke-static {v1, v2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v0

    aput-object v0, v10, v8

    invoke-static/range {p10 .. p10}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    aput-object v0, v10, v7

    invoke-static/range {p11 .. p11}, Lcom/unity3d/splash/services/core/api/Request;->getResponseHeadersMap(Ljava/util/Map;)Lorg/json/JSONArray;

    move-result-object v0

    aput-object v0, v10, v6

    invoke-virtual {v5, v11, v14, v10}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .registers 14

    invoke-virtual {p1}, Landroid/os/Message;->getData()Landroid/os/Bundle;

    move-result-object v0

    const-string v1, "source"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->remove(Ljava/lang/String;)V

    const-string v1, "target"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->remove(Ljava/lang/String;)V

    const-string v1, "connectTimeout"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getInt(Ljava/lang/String;)I

    move-result v5

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->remove(Ljava/lang/String;)V

    const-string v1, "readTimeout"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getInt(Ljava/lang/String;)I

    move-result v6

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->remove(Ljava/lang/String;)V

    const-string v1, "progressInterval"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getInt(Ljava/lang/String;)I

    move-result v7

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->remove(Ljava/lang/String;)V

    const-string v1, "append"

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Landroid/os/Bundle;->getBoolean(Ljava/lang/String;Z)Z

    move-result v9

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->remove(Ljava/lang/String;)V

    invoke-virtual {v0}, Landroid/os/Bundle;->size()I

    move-result v1

    if-lez v1, :cond_6d

    const-string v1, "There are headers left in data, reading them"

    invoke-static {v1}, Lcom/unity3d/splash/services/core/log/DeviceLog;->debug(Ljava/lang/String;)V

    new-instance v1, Ljava/util/HashMap;

    invoke-direct {v1}, Ljava/util/HashMap;-><init>()V

    invoke-virtual {v0}, Landroid/os/Bundle;->keySet()Ljava/util/Set;

    move-result-object v8

    invoke-interface {v8}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v8

    :goto_53
    invoke-interface {v8}, Ljava/util/Iterator;->hasNext()Z

    move-result v10

    if-eqz v10, :cond_6b

    invoke-interface {v8}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v10

    check-cast v10, Ljava/lang/String;

    invoke-virtual {v0, v10}, Landroid/os/Bundle;->getStringArray(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v11

    invoke-static {v11}, Ljava/util/Arrays;->asList([Ljava/lang/Object;)Ljava/util/List;

    move-result-object v11

    invoke-virtual {v1, v10, v11}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_53

    :cond_6b
    move-object v8, v1

    goto :goto_6f

    :cond_6d
    const/4 v0, 0x0

    move-object v8, v0

    :goto_6f
    new-instance v0, Ljava/io/File;

    invoke-direct {v0, v4}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    const/4 v1, 0x1

    if-eqz v9, :cond_7d

    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v10

    if-eqz v10, :cond_85

    :cond_7d
    if-nez v9, :cond_b1

    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v10

    if-eqz v10, :cond_b1

    :cond_85
    iput-boolean v2, p0, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_active:Z

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object p1

    sget-object v5, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->CACHE:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v6, Lcom/unity3d/splash/services/core/cache/CacheEvent;->DOWNLOAD_ERROR:Lcom/unity3d/splash/services/core/cache/CacheEvent;

    const/4 v7, 0x5

    new-array v7, v7, [Ljava/lang/Object;

    sget-object v8, Lcom/unity3d/splash/services/core/cache/CacheError;->FILE_STATE_WRONG:Lcom/unity3d/splash/services/core/cache/CacheError;

    aput-object v8, v7, v2

    aput-object v3, v7, v1

    const/4 v1, 0x2

    aput-object v4, v7, v1

    const/4 v1, 0x3

    invoke-static {v9}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v2

    aput-object v2, v7, v1

    const/4 v1, 0x4

    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v0

    invoke-static {v0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v0

    aput-object v0, v7, v1

    invoke-virtual {p1, v5, v6, v7}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    return-void

    :cond_b1
    iget p1, p1, Landroid/os/Message;->what:I

    if-eq p1, v1, :cond_b6

    goto :goto_ba

    :cond_b6
    move-object v2, p0

    invoke-direct/range {v2 .. v9}, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->downloadFile(Ljava/lang/String;Ljava/lang/String;IIILjava/util/HashMap;Z)V

    :goto_ba
    return-void
.end method

.method public isActive()Z
    .registers 2

    iget-boolean v0, p0, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_active:Z

    return v0
.end method

.method public setCancelStatus(Z)V
    .registers 3

    iput-boolean p1, p0, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_canceled:Z

    if-eqz p1, :cond_e

    iget-object p1, p0, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    if-eqz p1, :cond_e

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/unity3d/splash/services/core/cache/CacheThreadHandler;->_active:Z

    invoke-virtual {p1}, Lcom/unity3d/splash/services/core/request/WebRequest;->cancel()V

    :cond_e
    return-void
.end method
