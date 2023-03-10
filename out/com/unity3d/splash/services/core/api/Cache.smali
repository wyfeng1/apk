.class public Lcom/unity3d/splash/services/core/api/Cache;
.super Ljava/lang/Object;


# direct methods
.method public constructor <init>()V
    .registers 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static deleteFile(Ljava/lang/String;Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 3
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    new-instance v0, Ljava/io/File;

    invoke-static {p0}, Lcom/unity3d/splash/services/core/api/Cache;->fileIdToFilename(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    invoke-direct {v0, p0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/io/File;->delete()Z

    move-result p0

    const/4 v0, 0x0

    if-eqz p0, :cond_16

    new-array p0, v0, [Ljava/lang/Object;

    invoke-virtual {p1, p0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void

    :cond_16
    sget-object p0, Lcom/unity3d/splash/services/core/cache/CacheError;->FILE_IO_ERROR:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v0, v0, [Ljava/lang/Object;

    invoke-virtual {p1, p0, v0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void
.end method

.method public static download(Ljava/lang/String;Ljava/lang/String;Lorg/json/JSONArray;Ljava/lang/Boolean;Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 7
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    invoke-static {}, Lcom/unity3d/splash/services/core/cache/CacheThread;->isActive()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_f

    sget-object p0, Lcom/unity3d/splash/services/core/cache/CacheError;->FILE_ALREADY_CACHING:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array p1, v1, [Ljava/lang/Object;

    invoke-virtual {p4, p0, p1}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void

    :cond_f
    invoke-static {}, Lcom/unity3d/splash/services/core/device/Device;->isActiveNetworkConnected()Z

    move-result v0

    if-nez v0, :cond_1d

    sget-object p0, Lcom/unity3d/splash/services/core/cache/CacheError;->NO_INTERNET:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array p1, v1, [Ljava/lang/Object;

    invoke-virtual {p4, p0, p1}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void

    :cond_1d
    :try_start_1d
    invoke-static {p2}, Lcom/unity3d/splash/services/core/api/Request;->getHeadersMap(Lorg/json/JSONArray;)Ljava/util/HashMap;

    move-result-object p2
    :try_end_21
    .catch Ljava/lang/Exception; {:try_start_1d .. :try_end_21} :catch_32

    invoke-static {p1}, Lcom/unity3d/splash/services/core/api/Cache;->fileIdToFilename(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p3}, Ljava/lang/Boolean;->booleanValue()Z

    move-result p3

    invoke-static {p0, p1, p2, p3}, Lcom/unity3d/splash/services/core/cache/CacheThread;->download(Ljava/lang/String;Ljava/lang/String;Ljava/util/HashMap;Z)V

    new-array p0, v1, [Ljava/lang/Object;

    invoke-virtual {p4, p0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void

    :catch_32
    move-exception p2

    const-string p3, "Error mapping headers for the request"

    invoke-static {p3, p2}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    sget-object p2, Lcom/unity3d/splash/services/core/request/WebRequestError;->MAPPING_HEADERS_FAILED:Lcom/unity3d/splash/services/core/request/WebRequestError;

    const/4 p3, 0x2

    new-array p3, p3, [Ljava/lang/Object;

    aput-object p0, p3, v1

    const/4 p0, 0x1

    aput-object p1, p3, p0

    invoke-virtual {p4, p2, p3}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void
.end method

.method private static fileIdToFilename(Ljava/lang/String;)Ljava/lang/String;
    .registers 3

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->getCacheDirectory()Ljava/io/File;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v1, "/"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->getCacheFilePrefix()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public static getCacheDirectoryExists(Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 4
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->getCacheDirectory()Ljava/io/File;

    move-result-object v0

    const/4 v1, 0x0

    if-nez v0, :cond_f

    sget-object v0, Lcom/unity3d/splash/services/core/cache/CacheError;->CACHE_DIRECTORY_NULL:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v1, v1, [Ljava/lang/Object;

    invoke-virtual {p0, v0, v1}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void

    :cond_f
    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/Object;

    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v0

    invoke-static {v0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v0

    aput-object v0, v2, v1

    invoke-virtual {p0, v2}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void
.end method

.method public static getCacheDirectoryType(Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 4
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->getCacheDirectoryObject()Lcom/unity3d/splash/services/core/cache/CacheDirectory;

    move-result-object v0

    const/4 v1, 0x0

    if-eqz v0, :cond_43

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/ClientProperties;->getApplicationContext()Landroid/content/Context;

    move-result-object v2

    invoke-virtual {v0, v2}, Lcom/unity3d/splash/services/core/cache/CacheDirectory;->getCacheDirectory(Landroid/content/Context;)Ljava/io/File;

    move-result-object v2

    if-nez v2, :cond_12

    goto :goto_43

    :cond_12
    invoke-static {}, Lcom/unity3d/splash/services/core/properties/ClientProperties;->getApplicationContext()Landroid/content/Context;

    move-result-object v2

    invoke-virtual {v0, v2}, Lcom/unity3d/splash/services/core/cache/CacheDirectory;->getCacheDirectory(Landroid/content/Context;)Ljava/io/File;

    move-result-object v2

    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v2

    if-nez v2, :cond_28

    sget-object v0, Lcom/unity3d/splash/services/core/cache/CacheError;->CACHE_DIRECTORY_DOESNT_EXIST:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v1, v1, [Ljava/lang/Object;

    invoke-virtual {p0, v0, v1}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void

    :cond_28
    invoke-virtual {v0}, Lcom/unity3d/splash/services/core/cache/CacheDirectory;->getType()Lcom/unity3d/splash/services/core/cache/CacheDirectoryType;

    move-result-object v0

    if-nez v0, :cond_36

    sget-object v0, Lcom/unity3d/splash/services/core/cache/CacheError;->CACHE_DIRECTORY_TYPE_NULL:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v1, v1, [Ljava/lang/Object;

    invoke-virtual {p0, v0, v1}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void

    :cond_36
    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/Object;

    invoke-virtual {v0}, Lcom/unity3d/splash/services/core/cache/CacheDirectoryType;->name()Ljava/lang/String;

    move-result-object v0

    aput-object v0, v2, v1

    invoke-virtual {p0, v2}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void

    :cond_43
    :goto_43
    sget-object v0, Lcom/unity3d/splash/services/core/cache/CacheError;->CACHE_DIRECTORY_NULL:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v1, v1, [Ljava/lang/Object;

    invoke-virtual {p0, v0, v1}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void
.end method

.method public static getFileContent(Ljava/lang/String;Ljava/lang/String;Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 11
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    invoke-static {p0}, Lcom/unity3d/splash/services/core/api/Cache;->fileIdToFilename(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    new-instance v1, Ljava/io/File;

    invoke-direct {v1, v0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1}, Ljava/io/File;->exists()Z

    move-result v2

    const/4 v3, 0x1

    const/4 v4, 0x0

    const/4 v5, 0x2

    if-nez v2, :cond_1e

    sget-object p1, Lcom/unity3d/splash/services/core/cache/CacheError;->FILE_NOT_FOUND:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v1, v5, [Ljava/lang/Object;

    aput-object p0, v1, v4

    aput-object v0, v1, v3

    invoke-virtual {p2, p1, v1}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void

    :cond_1e
    const/4 v2, 0x3

    :try_start_1f
    invoke-static {v1}, Lcom/unity3d/splash/services/core/misc/Utilities;->readFileBytes(Ljava/io/File;)[B

    move-result-object v1
    :try_end_23
    .catch Ljava/io/IOException; {:try_start_1f .. :try_end_23} :catch_6e

    if-nez p1, :cond_33

    sget-object v1, Lcom/unity3d/splash/services/core/cache/CacheError;->UNSUPPORTED_ENCODING:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v2, v2, [Ljava/lang/Object;

    aput-object p0, v2, v4

    aput-object v0, v2, v3

    aput-object p1, v2, v5

    invoke-virtual {p2, v1, v2}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void

    :cond_33
    const-string v6, "UTF-8"

    invoke-virtual {p1, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    if-eqz v7, :cond_4c

    invoke-static {v1}, Ljava/nio/ByteBuffer;->wrap([B)Ljava/nio/ByteBuffer;

    move-result-object p0

    invoke-static {v6}, Ljava/nio/charset/Charset;->forName(Ljava/lang/String;)Ljava/nio/charset/Charset;

    move-result-object p1

    invoke-virtual {p1, p0}, Ljava/nio/charset/Charset;->decode(Ljava/nio/ByteBuffer;)Ljava/nio/CharBuffer;

    move-result-object p0

    invoke-virtual {p0}, Ljava/nio/CharBuffer;->toString()Ljava/lang/String;

    move-result-object p0

    goto :goto_58

    :cond_4c
    const-string v6, "Base64"

    invoke-virtual {p1, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v6

    if-eqz v6, :cond_60

    invoke-static {v1, v5}, Landroid/util/Base64;->encodeToString([BI)Ljava/lang/String;

    move-result-object p0

    :goto_58
    new-array p1, v3, [Ljava/lang/Object;

    aput-object p0, p1, v4

    invoke-virtual {p2, p1}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void

    :cond_60
    sget-object v1, Lcom/unity3d/splash/services/core/cache/CacheError;->UNSUPPORTED_ENCODING:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v2, v2, [Ljava/lang/Object;

    aput-object p0, v2, v4

    aput-object v0, v2, v3

    aput-object p1, v2, v5

    invoke-virtual {p2, v1, v2}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void

    :catch_6e
    move-exception p1

    sget-object v1, Lcom/unity3d/splash/services/core/cache/CacheError;->FILE_IO_ERROR:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v2, v2, [Ljava/lang/Object;

    aput-object p0, v2, v4

    aput-object v0, v2, v3

    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p1}, Ljava/io/IOException;->getMessage()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v0, ", "

    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/Class;->getName()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    aput-object p0, v2, v5

    invoke-virtual {p2, v1, v2}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void
.end method

.method public static getFileInfo(Ljava/lang/String;Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 4
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    const/4 v0, 0x0

    :try_start_1
    invoke-static {p0}, Lcom/unity3d/splash/services/core/api/Cache;->getFileJson(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object p0

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/Object;

    aput-object p0, v1, v0

    invoke-virtual {p1, v1}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V
    :try_end_d
    .catch Lorg/json/JSONException; {:try_start_1 .. :try_end_d} :catch_e

    return-void

    :catch_e
    move-exception p0

    const-string v1, "Error creating JSON"

    invoke-static {v1, p0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    sget-object p0, Lcom/unity3d/splash/services/core/cache/CacheError;->JSON_ERROR:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v0, v0, [Ljava/lang/Object;

    invoke-virtual {p1, p0, v0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void
.end method

.method private static getFileJson(Ljava/lang/String;)Lorg/json/JSONObject;
    .registers 5

    new-instance v0, Lorg/json/JSONObject;

    invoke-direct {v0}, Lorg/json/JSONObject;-><init>()V

    const-string v1, "id"

    invoke-virtual {v0, v1, p0}, Lorg/json/JSONObject;->put(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;

    new-instance v1, Ljava/io/File;

    invoke-static {p0}, Lcom/unity3d/splash/services/core/api/Cache;->fileIdToFilename(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    invoke-direct {v1, p0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1}, Ljava/io/File;->exists()Z

    move-result p0

    const-string v2, "found"

    if-eqz p0, :cond_32

    const/4 p0, 0x1

    invoke-virtual {v0, v2, p0}, Lorg/json/JSONObject;->put(Ljava/lang/String;Z)Lorg/json/JSONObject;

    invoke-virtual {v1}, Ljava/io/File;->length()J

    move-result-wide v2

    const-string p0, "size"

    invoke-virtual {v0, p0, v2, v3}, Lorg/json/JSONObject;->put(Ljava/lang/String;J)Lorg/json/JSONObject;

    invoke-virtual {v1}, Ljava/io/File;->lastModified()J

    move-result-wide v1

    const-string p0, "mtime"

    invoke-virtual {v0, p0, v1, v2}, Lorg/json/JSONObject;->put(Ljava/lang/String;J)Lorg/json/JSONObject;

    goto :goto_36

    :cond_32
    const/4 p0, 0x0

    invoke-virtual {v0, v2, p0}, Lorg/json/JSONObject;->put(Ljava/lang/String;Z)Lorg/json/JSONObject;

    :goto_36
    return-object v0
.end method

.method public static getFilePath(Ljava/lang/String;Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 4
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    new-instance v0, Ljava/io/File;

    invoke-static {p0}, Lcom/unity3d/splash/services/core/api/Cache;->fileIdToFilename(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_1d

    const/4 v0, 0x1

    new-array v0, v0, [Ljava/lang/Object;

    invoke-static {p0}, Lcom/unity3d/splash/services/core/api/Cache;->fileIdToFilename(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    aput-object p0, v0, v1

    invoke-virtual {p1, v0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void

    :cond_1d
    sget-object p0, Lcom/unity3d/splash/services/core/cache/CacheError;->FILE_NOT_FOUND:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v0, v1, [Ljava/lang/Object;

    invoke-virtual {p1, p0, v0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void
.end method

.method public static getFiles(Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 12
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->getCacheDirectory()Ljava/io/File;

    move-result-object v0

    if-nez v0, :cond_7

    return-void

    :cond_7
    const-string v1, "Unity Ads cache: checking app directory for Unity Ads cached files"

    invoke-static {v1}, Lcom/unity3d/splash/services/core/log/DeviceLog;->debug(Ljava/lang/String;)V

    new-instance v1, Lcom/unity3d/splash/services/core/api/Cache$1;

    invoke-direct {v1}, Lcom/unity3d/splash/services/core/api/Cache$1;-><init>()V

    invoke-virtual {v0, v1}, Ljava/io/File;->listFiles(Ljava/io/FilenameFilter;)[Ljava/io/File;

    move-result-object v0

    const/4 v1, 0x1

    const/4 v2, 0x0

    if-eqz v0, :cond_1c

    array-length v3, v0

    if-nez v3, :cond_28

    :cond_1c
    new-array v3, v1, [Ljava/lang/Object;

    new-instance v4, Lorg/json/JSONArray;

    invoke-direct {v4}, Lorg/json/JSONArray;-><init>()V

    aput-object v4, v3, v2

    invoke-virtual {p0, v3}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    :cond_28
    :try_start_28
    new-instance v3, Lorg/json/JSONArray;

    invoke-direct {v3}, Lorg/json/JSONArray;-><init>()V

    array-length v4, v0

    const/4 v5, 0x0

    :goto_2f
    if-ge v5, v4, :cond_6f

    aget-object v6, v0, v5

    invoke-virtual {v6}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v7

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->getCacheFilePrefix()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/String;->length()I

    move-result v8

    invoke-virtual {v7, v8}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v7

    new-instance v8, Ljava/lang/StringBuilder;

    const-string v9, "Unity Ads cache: found "

    invoke-direct {v8, v9}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v8, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v9, ", "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/io/File;->length()J

    move-result-wide v9

    invoke-virtual {v8, v9, v10}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    const-string v6, " bytes"

    invoke-virtual {v8, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v6}, Lcom/unity3d/splash/services/core/log/DeviceLog;->debug(Ljava/lang/String;)V

    invoke-static {v7}, Lcom/unity3d/splash/services/core/api/Cache;->getFileJson(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object v6

    invoke-virtual {v3, v6}, Lorg/json/JSONArray;->put(Ljava/lang/Object;)Lorg/json/JSONArray;

    add-int/lit8 v5, v5, 0x1

    goto :goto_2f

    :cond_6f
    new-array v0, v1, [Ljava/lang/Object;

    aput-object v3, v0, v2

    invoke-virtual {p0, v0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V
    :try_end_76
    .catch Lorg/json/JSONException; {:try_start_28 .. :try_end_76} :catch_77

    return-void

    :catch_77
    move-exception v0

    const-string v1, "Error creating JSON"

    invoke-static {v1, v0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    sget-object v0, Lcom/unity3d/splash/services/core/cache/CacheError;->JSON_ERROR:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v1, v2, [Ljava/lang/Object;

    invoke-virtual {p0, v0, v1}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void
.end method

.method public static getFreeSpace(Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 4
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    const/4 v0, 0x1

    new-array v0, v0, [Ljava/lang/Object;

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->getCacheDirectory()Ljava/io/File;

    move-result-object v1

    invoke-static {v1}, Lcom/unity3d/splash/services/core/device/Device;->getFreeSpace(Ljava/io/File;)J

    move-result-wide v1

    invoke-static {v1, v2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v1

    const/4 v2, 0x0

    aput-object v1, v0, v2

    invoke-virtual {p0, v0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void
.end method

.method public static getHash(Ljava/lang/String;Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 4
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    const/4 v0, 0x1

    new-array v0, v0, [Ljava/lang/Object;

    invoke-static {p0}, Lcom/unity3d/splash/services/core/misc/Utilities;->Sha256(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    const/4 v1, 0x0

    aput-object p0, v0, v1

    invoke-virtual {p1, v0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void
.end method

.method private static getMetaData(Ljava/lang/String;Lorg/json/JSONArray;)Landroid/util/SparseArray;
    .registers 6

    new-instance v0, Ljava/io/File;

    invoke-direct {v0, p0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    new-instance p0, Landroid/util/SparseArray;

    invoke-direct {p0}, Landroid/util/SparseArray;-><init>()V

    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v1

    if-eqz v1, :cond_34

    new-instance v1, Landroid/media/MediaMetadataRetriever;

    invoke-direct {v1}, Landroid/media/MediaMetadataRetriever;-><init>()V

    invoke-virtual {v0}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v1, v0}, Landroid/media/MediaMetadataRetriever;->setDataSource(Ljava/lang/String;)V

    const/4 v0, 0x0

    :goto_1d
    invoke-virtual {p1}, Lorg/json/JSONArray;->length()I

    move-result v2

    if-ge v0, v2, :cond_33

    invoke-virtual {p1, v0}, Lorg/json/JSONArray;->getInt(I)I

    move-result v2

    invoke-virtual {v1, v2}, Landroid/media/MediaMetadataRetriever;->extractMetadata(I)Ljava/lang/String;

    move-result-object v3

    if-eqz v3, :cond_30

    invoke-virtual {p0, v2, v3}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    :cond_30
    add-int/lit8 v0, v0, 0x1

    goto :goto_1d

    :cond_33
    return-object p0

    :cond_34
    new-instance p0, Ljava/io/IOException;

    new-instance p1, Ljava/lang/StringBuilder;

    const-string v1, "File: "

    invoke-direct {p1, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v0, " doesn\'t exist"

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw p0
.end method

.method public static getMetaData(Ljava/lang/String;Lorg/json/JSONArray;Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 8
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    invoke-static {p0}, Lcom/unity3d/splash/services/core/api/Cache;->fileIdToFilename(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    const/4 v0, 0x0

    const/4 v1, 0x1

    :try_start_6
    invoke-static {p0, p1}, Lcom/unity3d/splash/services/core/api/Cache;->getMetaData(Ljava/lang/String;Lorg/json/JSONArray;)Landroid/util/SparseArray;

    move-result-object p0
    :try_end_a
    .catch Lorg/json/JSONException; {:try_start_6 .. :try_end_a} :catch_55
    .catch Ljava/lang/RuntimeException; {:try_start_6 .. :try_end_a} :catch_46
    .catch Ljava/io/IOException; {:try_start_6 .. :try_end_a} :catch_37

    new-instance p1, Lorg/json/JSONArray;

    invoke-direct {p1}, Lorg/json/JSONArray;-><init>()V

    const/4 v2, 0x0

    :goto_10
    invoke-virtual {p0}, Landroid/util/SparseArray;->size()I

    move-result v3

    if-ge v2, v3, :cond_2f

    new-instance v3, Lorg/json/JSONArray;

    invoke-direct {v3}, Lorg/json/JSONArray;-><init>()V

    invoke-virtual {p0, v2}, Landroid/util/SparseArray;->keyAt(I)I

    move-result v4

    invoke-virtual {v3, v4}, Lorg/json/JSONArray;->put(I)Lorg/json/JSONArray;

    invoke-virtual {p0, v2}, Landroid/util/SparseArray;->valueAt(I)Ljava/lang/Object;

    move-result-object v4

    invoke-virtual {v3, v4}, Lorg/json/JSONArray;->put(Ljava/lang/Object;)Lorg/json/JSONArray;

    invoke-virtual {p1, v3}, Lorg/json/JSONArray;->put(Ljava/lang/Object;)Lorg/json/JSONArray;

    add-int/lit8 v2, v2, 0x1

    goto :goto_10

    :cond_2f
    new-array p0, v1, [Ljava/lang/Object;

    aput-object p1, p0, v0

    invoke-virtual {p2, p0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void

    :catch_37
    move-exception p0

    sget-object p1, Lcom/unity3d/splash/services/core/cache/CacheError;->FILE_IO_ERROR:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v1, v1, [Ljava/lang/Object;

    invoke-virtual {p0}, Ljava/io/IOException;->getMessage()Ljava/lang/String;

    move-result-object p0

    aput-object p0, v1, v0

    invoke-virtual {p2, p1, v1}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void

    :catch_46
    move-exception p0

    sget-object p1, Lcom/unity3d/splash/services/core/cache/CacheError;->INVALID_ARGUMENT:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v1, v1, [Ljava/lang/Object;

    invoke-virtual {p0}, Ljava/lang/RuntimeException;->getMessage()Ljava/lang/String;

    move-result-object p0

    aput-object p0, v1, v0

    invoke-virtual {p2, p1, v1}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void

    :catch_55
    move-exception p0

    sget-object p1, Lcom/unity3d/splash/services/core/cache/CacheError;->JSON_ERROR:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v1, v1, [Ljava/lang/Object;

    invoke-virtual {p0}, Lorg/json/JSONException;->getMessage()Ljava/lang/String;

    move-result-object p0

    aput-object p0, v1, v0

    invoke-virtual {p2, p1, v1}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void
.end method

.method public static getProgressInterval(Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 4
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    const/4 v0, 0x1

    new-array v0, v0, [Ljava/lang/Object;

    invoke-static {}, Lcom/unity3d/splash/services/core/cache/CacheThread;->getProgressInterval()I

    move-result v1

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const/4 v2, 0x0

    aput-object v1, v0, v2

    invoke-virtual {p0, v0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void
.end method

.method public static getTimeouts(Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 4
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    const/4 v0, 0x2

    new-array v0, v0, [Ljava/lang/Object;

    invoke-static {}, Lcom/unity3d/splash/services/core/cache/CacheThread;->getConnectTimeout()I

    move-result v1

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const/4 v2, 0x0

    aput-object v1, v0, v2

    invoke-static {}, Lcom/unity3d/splash/services/core/cache/CacheThread;->getReadTimeout()I

    move-result v1

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const/4 v2, 0x1

    aput-object v1, v0, v2

    invoke-virtual {p0, v0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void
.end method

.method public static getTotalSpace(Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 4
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    const/4 v0, 0x1

    new-array v0, v0, [Ljava/lang/Object;

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->getCacheDirectory()Ljava/io/File;

    move-result-object v1

    invoke-static {v1}, Lcom/unity3d/splash/services/core/device/Device;->getTotalSpace(Ljava/io/File;)J

    move-result-wide v1

    invoke-static {v1, v2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v1

    const/4 v2, 0x0

    aput-object v1, v0, v2

    invoke-virtual {p0, v0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void
.end method

.method public static isCaching(Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 4
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    const/4 v0, 0x1

    new-array v0, v0, [Ljava/lang/Object;

    invoke-static {}, Lcom/unity3d/splash/services/core/cache/CacheThread;->isActive()Z

    move-result v1

    invoke-static {v1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    const/4 v2, 0x0

    aput-object v1, v0, v2

    invoke-virtual {p0, v0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void
.end method

.method public static recreateCacheDirectory(Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 3
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->getCacheDirectory()Ljava/io/File;

    move-result-object v0

    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_13

    sget-object v0, Lcom/unity3d/splash/services/core/cache/CacheError;->CACHE_DIRECTORY_EXISTS:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v1, v1, [Ljava/lang/Object;

    invoke-virtual {p0, v0, v1}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void

    :cond_13
    const/4 v0, 0x0

    invoke-static {v0}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->setCacheDirectory(Lcom/unity3d/splash/services/core/cache/CacheDirectory;)V

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->getCacheDirectory()Ljava/io/File;

    move-result-object v0

    if-nez v0, :cond_25

    sget-object v0, Lcom/unity3d/splash/services/core/cache/CacheError;->CACHE_DIRECTORY_NULL:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v1, v1, [Ljava/lang/Object;

    invoke-virtual {p0, v0, v1}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void

    :cond_25
    new-array v0, v1, [Ljava/lang/Object;

    invoke-virtual {p0, v0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void
.end method

.method public static setFileContent(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 13
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    const-string v0, "UTF-8"

    const-string v1, "Error closing FileOutputStream"

    invoke-static {p0}, Lcom/unity3d/splash/services/core/api/Cache;->fileIdToFilename(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    const/4 v3, 0x3

    const/4 v4, 0x2

    const/4 v5, 0x1

    const/4 v6, 0x0

    :try_start_c
    invoke-virtual {p2, v0}, Ljava/lang/String;->getBytes(Ljava/lang/String;)[B

    move-result-object v7
    :try_end_10
    .catch Ljava/io/UnsupportedEncodingException; {:try_start_c .. :try_end_10} :catch_95

    if-eqz p1, :cond_39

    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v8

    if-lez v8, :cond_39

    const-string v8, "Base64"

    invoke-virtual {p1, v8}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    if-eqz v8, :cond_25

    invoke-static {p2, v4}, Landroid/util/Base64;->decode(Ljava/lang/String;I)[B

    move-result-object v7

    goto :goto_39

    :cond_25
    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p2

    if-nez p2, :cond_39

    sget-object p2, Lcom/unity3d/splash/services/core/cache/CacheError;->UNSUPPORTED_ENCODING:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v0, v3, [Ljava/lang/Object;

    aput-object p0, v0, v6

    aput-object v2, v0, v5

    aput-object p1, v0, v4

    invoke-virtual {p3, p2, v0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void

    :cond_39
    :goto_39
    const/4 p2, 0x0

    :try_start_3a
    new-instance v0, Ljava/io/FileOutputStream;

    invoke-direct {v0, v2}, Ljava/io/FileOutputStream;-><init>(Ljava/lang/String;)V
    :try_end_3f
    .catch Ljava/io/FileNotFoundException; {:try_start_3a .. :try_end_3f} :catch_6a
    .catch Ljava/io/IOException; {:try_start_3a .. :try_end_3f} :catch_57
    .catchall {:try_start_3a .. :try_end_3f} :catchall_55

    :try_start_3f
    invoke-virtual {v0, v7}, Ljava/io/FileOutputStream;->write([B)V

    invoke-virtual {v0}, Ljava/io/FileOutputStream;->flush()V
    :try_end_45
    .catch Ljava/io/FileNotFoundException; {:try_start_3f .. :try_end_45} :catch_53
    .catch Ljava/io/IOException; {:try_start_3f .. :try_end_45} :catch_51
    .catchall {:try_start_3f .. :try_end_45} :catchall_4e

    :try_start_45
    invoke-virtual {v0}, Ljava/io/FileOutputStream;->close()V
    :try_end_48
    .catch Ljava/lang/Exception; {:try_start_45 .. :try_end_48} :catch_49

    goto :goto_82

    :catch_49
    move-exception p0

    invoke-static {v1, p0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    goto :goto_82

    :catchall_4e
    move-exception p0

    move-object p2, v0

    goto :goto_8a

    :catch_51
    move-object p2, v0

    goto :goto_57

    :catch_53
    move-object p2, v0

    goto :goto_6a

    :catchall_55
    move-exception p0

    goto :goto_8a

    :catch_57
    :goto_57
    :try_start_57
    sget-object v0, Lcom/unity3d/splash/services/core/cache/CacheError;->FILE_IO_ERROR:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v3, v3, [Ljava/lang/Object;

    aput-object p0, v3, v6

    aput-object v2, v3, v5

    aput-object p1, v3, v4

    invoke-virtual {p3, v0, v3}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V
    :try_end_64
    .catchall {:try_start_57 .. :try_end_64} :catchall_55

    if-eqz p2, :cond_81

    :try_start_66
    invoke-virtual {p2}, Ljava/io/FileOutputStream;->close()V
    :try_end_69
    .catch Ljava/lang/Exception; {:try_start_66 .. :try_end_69} :catch_7d

    goto :goto_81

    :catch_6a
    :goto_6a
    :try_start_6a
    sget-object v0, Lcom/unity3d/splash/services/core/cache/CacheError;->FILE_NOT_FOUND:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v3, v3, [Ljava/lang/Object;

    aput-object p0, v3, v6

    aput-object v2, v3, v5

    aput-object p1, v3, v4

    invoke-virtual {p3, v0, v3}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V
    :try_end_77
    .catchall {:try_start_6a .. :try_end_77} :catchall_55

    if-eqz p2, :cond_81

    :try_start_79
    invoke-virtual {p2}, Ljava/io/FileOutputStream;->close()V
    :try_end_7c
    .catch Ljava/lang/Exception; {:try_start_79 .. :try_end_7c} :catch_7d

    goto :goto_81

    :catch_7d
    move-exception p0

    invoke-static {v1, p0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    :cond_81
    :goto_81
    const/4 v5, 0x0

    :goto_82
    if-eqz v5, :cond_89

    new-array p0, v6, [Ljava/lang/Object;

    invoke-virtual {p3, p0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    :cond_89
    return-void

    :goto_8a
    if-eqz p2, :cond_94

    :try_start_8c
    invoke-virtual {p2}, Ljava/io/FileOutputStream;->close()V
    :try_end_8f
    .catch Ljava/lang/Exception; {:try_start_8c .. :try_end_8f} :catch_90

    goto :goto_94

    :catch_90
    move-exception p1

    invoke-static {v1, p1}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    :cond_94
    :goto_94
    throw p0

    :catch_95
    sget-object p2, Lcom/unity3d/splash/services/core/cache/CacheError;->UNSUPPORTED_ENCODING:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v0, v3, [Ljava/lang/Object;

    aput-object p0, v0, v6

    aput-object v2, v0, v5

    aput-object p1, v0, v4

    invoke-virtual {p3, p2, v0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void
.end method

.method public static setProgressInterval(Ljava/lang/Integer;Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 2
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result p0

    invoke-static {p0}, Lcom/unity3d/splash/services/core/cache/CacheThread;->setProgressInterval(I)V

    const/4 p0, 0x0

    new-array p0, p0, [Ljava/lang/Object;

    invoke-virtual {p1, p0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void
.end method

.method public static setTimeouts(Ljava/lang/Integer;Ljava/lang/Integer;Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 3
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result p0

    invoke-static {p0}, Lcom/unity3d/splash/services/core/cache/CacheThread;->setConnectTimeout(I)V

    invoke-virtual {p1}, Ljava/lang/Integer;->intValue()I

    move-result p0

    invoke-static {p0}, Lcom/unity3d/splash/services/core/cache/CacheThread;->setReadTimeout(I)V

    const/4 p0, 0x0

    new-array p0, p0, [Ljava/lang/Object;

    invoke-virtual {p2, p0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void
.end method

.method public static stop(Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 3
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    invoke-static {}, Lcom/unity3d/splash/services/core/cache/CacheThread;->isActive()Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_f

    sget-object v0, Lcom/unity3d/splash/services/core/cache/CacheError;->NOT_CACHING:Lcom/unity3d/splash/services/core/cache/CacheError;

    new-array v1, v1, [Ljava/lang/Object;

    invoke-virtual {p0, v0, v1}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void

    :cond_f
    invoke-static {}, Lcom/unity3d/splash/services/core/cache/CacheThread;->cancel()V

    new-array v0, v1, [Ljava/lang/Object;

    invoke-virtual {p0, v0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void
.end method
