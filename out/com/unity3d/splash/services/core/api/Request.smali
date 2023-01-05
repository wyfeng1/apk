.class public Lcom/unity3d/splash/services/core/api/Request;
.super Ljava/lang/Object;


# direct methods
.method public constructor <init>()V
    .registers 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static get(Ljava/lang/String;Ljava/lang/String;Lorg/json/JSONArray;Ljava/lang/Integer;Ljava/lang/Integer;Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 15
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    if-eqz p2, :cond_9

    invoke-virtual {p2}, Lorg/json/JSONArray;->length()I

    move-result v0

    if-nez v0, :cond_9

    const/4 p2, 0x0

    :cond_9
    const/4 v0, 0x0

    const/4 v1, 0x1

    :try_start_b
    invoke-static {p2}, Lcom/unity3d/splash/services/core/api/Request;->getHeadersMap(Lorg/json/JSONArray;)Ljava/util/HashMap;

    move-result-object v4
    :try_end_f
    .catch Ljava/lang/Exception; {:try_start_b .. :try_end_f} :catch_25

    sget-object v3, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;->GET:Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

    const/4 v5, 0x0

    new-instance v8, Lcom/unity3d/splash/services/core/api/Request$1;

    invoke-direct {v8, p0}, Lcom/unity3d/splash/services/core/api/Request$1;-><init>(Ljava/lang/String;)V

    move-object v2, p1

    move-object v6, p3

    move-object v7, p4

    invoke-static/range {v2 .. v8}, Lcom/unity3d/splash/services/core/request/WebRequestThread;->request(Ljava/lang/String;Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;Ljava/util/Map;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Lcom/unity3d/splash/services/core/request/IWebRequestListener;)V

    new-array p1, v1, [Ljava/lang/Object;

    aput-object p0, p1, v0

    invoke-virtual {p5, p1}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void

    :catch_25
    move-exception p1

    const-string p2, "Error mapping headers for the request"

    invoke-static {p2, p1}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    sget-object p1, Lcom/unity3d/splash/services/core/request/WebRequestError;->MAPPING_HEADERS_FAILED:Lcom/unity3d/splash/services/core/request/WebRequestError;

    new-array p2, v1, [Ljava/lang/Object;

    aput-object p0, p2, v0

    invoke-virtual {p5, p1, p2}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void
.end method

.method public static getHeadersMap(Lorg/json/JSONArray;)Ljava/util/HashMap;
    .registers 7

    if-eqz p0, :cond_38

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    const/4 v1, 0x0

    const/4 v2, 0x0

    :goto_9
    invoke-virtual {p0}, Lorg/json/JSONArray;->length()I

    move-result v3

    if-ge v2, v3, :cond_39

    invoke-virtual {p0, v2}, Lorg/json/JSONArray;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lorg/json/JSONArray;

    invoke-virtual {v3, v1}, Lorg/json/JSONArray;->getString(I)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v0, v4}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/util/List;

    if-nez v4, :cond_26

    new-instance v4, Ljava/util/ArrayList;

    invoke-direct {v4}, Ljava/util/ArrayList;-><init>()V

    :cond_26
    const/4 v5, 0x1

    invoke-virtual {v3, v5}, Lorg/json/JSONArray;->getString(I)Ljava/lang/String;

    move-result-object v5

    invoke-interface {v4, v5}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    invoke-virtual {v3, v1}, Lorg/json/JSONArray;->getString(I)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v3, v4}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    add-int/lit8 v2, v2, 0x1

    goto :goto_9

    :cond_38
    const/4 v0, 0x0

    :cond_39
    return-object v0
.end method

.method public static getResponseHeadersMap(Ljava/util/Map;)Lorg/json/JSONArray;
    .registers 7

    new-instance v0, Lorg/json/JSONArray;

    invoke-direct {v0}, Lorg/json/JSONArray;-><init>()V

    if-eqz p0, :cond_49

    invoke-interface {p0}, Ljava/util/Map;->size()I

    move-result v1

    if-lez v1, :cond_49

    invoke-interface {p0}, Ljava/util/Map;->keySet()Ljava/util/Set;

    move-result-object v1

    invoke-interface {v1}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :goto_15
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_49

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    const/4 v3, 0x0

    invoke-interface {p0, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/util/List;

    invoke-interface {v4}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v4

    :goto_2c
    invoke-interface {v4}, Ljava/util/Iterator;->hasNext()Z

    move-result v5

    if-eqz v5, :cond_45

    invoke-interface {v4}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/String;

    new-instance v5, Lorg/json/JSONArray;

    invoke-direct {v5}, Lorg/json/JSONArray;-><init>()V

    invoke-virtual {v5, v2}, Lorg/json/JSONArray;->put(Ljava/lang/Object;)Lorg/json/JSONArray;

    invoke-virtual {v5, v3}, Lorg/json/JSONArray;->put(Ljava/lang/Object;)Lorg/json/JSONArray;

    move-object v3, v5

    goto :goto_2c

    :cond_45
    invoke-virtual {v0, v3}, Lorg/json/JSONArray;->put(Ljava/lang/Object;)Lorg/json/JSONArray;

    goto :goto_15

    :cond_49
    return-object v0
.end method

.method public static head(Ljava/lang/String;Ljava/lang/String;Lorg/json/JSONArray;Ljava/lang/Integer;Ljava/lang/Integer;Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 14
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    if-eqz p2, :cond_9

    invoke-virtual {p2}, Lorg/json/JSONArray;->length()I

    move-result v0

    if-nez v0, :cond_9

    const/4 p2, 0x0

    :cond_9
    const/4 v0, 0x0

    const/4 v1, 0x1

    :try_start_b
    invoke-static {p2}, Lcom/unity3d/splash/services/core/api/Request;->getHeadersMap(Lorg/json/JSONArray;)Ljava/util/HashMap;

    move-result-object v4
    :try_end_f
    .catch Ljava/lang/Exception; {:try_start_b .. :try_end_f} :catch_24

    sget-object v3, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;->HEAD:Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

    new-instance v7, Lcom/unity3d/splash/services/core/api/Request$3;

    invoke-direct {v7, p0}, Lcom/unity3d/splash/services/core/api/Request$3;-><init>(Ljava/lang/String;)V

    move-object v2, p1

    move-object v5, p3

    move-object v6, p4

    invoke-static/range {v2 .. v7}, Lcom/unity3d/splash/services/core/request/WebRequestThread;->request(Ljava/lang/String;Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;Ljava/util/Map;Ljava/lang/Integer;Ljava/lang/Integer;Lcom/unity3d/splash/services/core/request/IWebRequestListener;)V

    new-array p1, v1, [Ljava/lang/Object;

    aput-object p0, p1, v0

    invoke-virtual {p5, p1}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void

    :catch_24
    move-exception p1

    const-string p2, "Error mapping headers for the request"

    invoke-static {p2, p1}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    sget-object p1, Lcom/unity3d/splash/services/core/request/WebRequestError;->MAPPING_HEADERS_FAILED:Lcom/unity3d/splash/services/core/request/WebRequestError;

    new-array p2, v1, [Ljava/lang/Object;

    aput-object p0, p2, v0

    invoke-virtual {p5, p1, p2}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void
.end method

.method public static post(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lorg/json/JSONArray;Ljava/lang/Integer;Ljava/lang/Integer;Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 16
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    const/4 v0, 0x0

    if-eqz p2, :cond_b

    invoke-virtual {p2}, Ljava/lang/String;->length()I

    move-result v1

    if-nez v1, :cond_b

    move-object v5, v0

    goto :goto_c

    :cond_b
    move-object v5, p2

    :goto_c
    if-eqz p3, :cond_15

    invoke-virtual {p3}, Lorg/json/JSONArray;->length()I

    move-result p2

    if-nez p2, :cond_15

    move-object p3, v0

    :cond_15
    const/4 p2, 0x0

    const/4 v0, 0x1

    :try_start_17
    invoke-static {p3}, Lcom/unity3d/splash/services/core/api/Request;->getHeadersMap(Lorg/json/JSONArray;)Ljava/util/HashMap;

    move-result-object v4
    :try_end_1b
    .catch Ljava/lang/Exception; {:try_start_17 .. :try_end_1b} :catch_30

    sget-object v3, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;->POST:Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

    new-instance v8, Lcom/unity3d/splash/services/core/api/Request$2;

    invoke-direct {v8, p0}, Lcom/unity3d/splash/services/core/api/Request$2;-><init>(Ljava/lang/String;)V

    move-object v2, p1

    move-object v6, p4

    move-object v7, p5

    invoke-static/range {v2 .. v8}, Lcom/unity3d/splash/services/core/request/WebRequestThread;->request(Ljava/lang/String;Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;Ljava/util/Map;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Lcom/unity3d/splash/services/core/request/IWebRequestListener;)V

    new-array p1, v0, [Ljava/lang/Object;

    aput-object p0, p1, p2

    invoke-virtual {p6, p1}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void

    :catch_30
    move-exception p1

    const-string p3, "Error mapping headers for the request"

    invoke-static {p3, p1}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    sget-object p1, Lcom/unity3d/splash/services/core/request/WebRequestError;->MAPPING_HEADERS_FAILED:Lcom/unity3d/splash/services/core/request/WebRequestError;

    new-array p3, v0, [Ljava/lang/Object;

    aput-object p0, p3, p2

    invoke-virtual {p6, p1, p3}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->error(Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void
.end method

.method public static setConcurrentRequestCount(Ljava/lang/Integer;Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 2
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result p0

    invoke-static {p0}, Lcom/unity3d/splash/services/core/request/WebRequestThread;->setConcurrentRequestCount(I)V

    const/4 p0, 0x0

    new-array p0, p0, [Ljava/lang/Object;

    invoke-virtual {p1, p0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void
.end method

.method public static setKeepAliveTime(Ljava/lang/Integer;Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 4
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    invoke-virtual {p0}, Ljava/lang/Integer;->longValue()J

    move-result-wide v0

    invoke-static {v0, v1}, Lcom/unity3d/splash/services/core/request/WebRequestThread;->setKeepAliveTime(J)V

    const/4 p0, 0x0

    new-array p0, p0, [Ljava/lang/Object;

    invoke-virtual {p1, p0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void
.end method

.method public static setMaximumPoolSize(Ljava/lang/Integer;Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;)V
    .registers 2
    .annotation runtime Lcom/unity3d/splash/services/core/webview/bridge/WebViewExposed;
    .end annotation

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result p0

    invoke-static {p0}, Lcom/unity3d/splash/services/core/request/WebRequestThread;->setMaximumPoolSize(I)V

    const/4 p0, 0x0

    new-array p0, p0, [Ljava/lang/Object;

    invoke-virtual {p1, p0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke([Ljava/lang/Object;)V

    return-void
.end method
