.class public Lcom/unity3d/splash/services/core/request/WebRequestRunnable;
.super Ljava/lang/Object;

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final _body:Ljava/lang/String;

.field private _canceled:Z

.field private final _connectTimeout:I

.field private _currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

.field private final _headers:Ljava/util/Map;

.field private final _listener:Lcom/unity3d/splash/services/core/request/IWebRequestListener;

.field private final _readTimeout:I

.field private final _type:Ljava/lang/String;

.field private final _url:Ljava/lang/String;


# direct methods
.method public constructor <init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/util/Map;Lcom/unity3d/splash/services/core/request/IWebRequestListener;)V
    .registers 9

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_canceled:Z

    iput-object p1, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_url:Ljava/lang/String;

    iput-object p2, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_type:Ljava/lang/String;

    iput-object p3, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_body:Ljava/lang/String;

    iput p4, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_connectTimeout:I

    iput p5, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_readTimeout:I

    iput-object p6, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_headers:Ljava/util/Map;

    iput-object p7, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_listener:Lcom/unity3d/splash/services/core/request/IWebRequestListener;

    return-void
.end method

.method private getResponseHeaders(Landroid/os/Bundle;)Ljava/util/Map;
    .registers 7

    invoke-virtual {p1}, Landroid/os/Bundle;->size()I

    move-result v0

    if-lez v0, :cond_32

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    invoke-virtual {p1}, Landroid/os/Bundle;->keySet()Ljava/util/Set;

    move-result-object v1

    invoke-interface {v1}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :cond_13
    :goto_13
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_33

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    invoke-virtual {p1, v2}, Landroid/os/Bundle;->getStringArray(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v3

    if-eqz v3, :cond_13

    new-instance v4, Ljava/util/ArrayList;

    invoke-static {v3}, Ljava/util/Arrays;->asList([Ljava/lang/Object;)Ljava/util/List;

    move-result-object v3

    invoke-direct {v4, v3}, Ljava/util/ArrayList;-><init>(Ljava/util/Collection;)V

    invoke-interface {v0, v2, v4}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_13

    :cond_32
    const/4 v0, 0x0

    :cond_33
    return-object v0
.end method

.method private makeRequest(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;II)V
    .registers 14

    iget-boolean v0, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_canceled:Z

    if-eqz v0, :cond_5

    return-void

    :cond_5
    new-instance v0, Lcom/unity3d/splash/services/core/request/WebRequest;

    move-object v1, v0

    move-object v2, p1

    move-object v3, p2

    move-object v4, p3

    move v5, p5

    move v6, p6

    invoke-direct/range {v1 .. v6}, Lcom/unity3d/splash/services/core/request/WebRequest;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;II)V

    iput-object v0, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    if-eqz p4, :cond_17

    invoke-virtual {v0, p4}, Lcom/unity3d/splash/services/core/request/WebRequest;->setBody(Ljava/lang/String;)V

    :cond_17
    :try_start_17
    iget-object p1, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    invoke-virtual {p1}, Lcom/unity3d/splash/services/core/request/WebRequest;->makeRequest()Ljava/lang/String;

    move-result-object p1
    :try_end_1d
    .catch Ljava/io/IOException; {:try_start_17 .. :try_end_1d} :catch_ac
    .catch Lcom/unity3d/splash/services/core/request/NetworkIOException; {:try_start_17 .. :try_end_1d} :catch_aa
    .catch Ljava/lang/IllegalStateException; {:try_start_17 .. :try_end_1d} :catch_a8
    .catch Ljava/lang/IllegalArgumentException; {:try_start_17 .. :try_end_1d} :catch_a6

    iget-object p2, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    invoke-virtual {p2}, Lcom/unity3d/splash/services/core/request/WebRequest;->isCanceled()Z

    move-result p2

    if-eqz p2, :cond_26

    return-void

    :cond_26
    new-instance p2, Landroid/os/Bundle;

    invoke-direct {p2}, Landroid/os/Bundle;-><init>()V

    iget-object p3, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    invoke-virtual {p3}, Lcom/unity3d/splash/services/core/request/WebRequest;->getResponseHeaders()Ljava/util/Map;

    move-result-object p3

    invoke-interface {p3}, Ljava/util/Map;->keySet()Ljava/util/Set;

    move-result-object p3

    invoke-interface {p3}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object p3

    :cond_39
    :goto_39
    invoke-interface {p3}, Ljava/util/Iterator;->hasNext()Z

    move-result p4

    if-eqz p4, :cond_8f

    invoke-interface {p3}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object p4

    check-cast p4, Ljava/lang/String;

    if-eqz p4, :cond_39

    const-string p5, "null"

    invoke-virtual {p4, p5}, Ljava/lang/String;->contentEquals(Ljava/lang/CharSequence;)Z

    move-result p5

    if-nez p5, :cond_39

    iget-object p5, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    invoke-virtual {p5}, Lcom/unity3d/splash/services/core/request/WebRequest;->getResponseHeaders()Ljava/util/Map;

    move-result-object p5

    invoke-interface {p5, p4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p5

    check-cast p5, Ljava/util/List;

    invoke-interface {p5}, Ljava/util/List;->size()I

    move-result p5

    new-array p5, p5, [Ljava/lang/String;

    const/4 p6, 0x0

    :goto_62
    iget-object v0, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    invoke-virtual {v0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getResponseHeaders()Ljava/util/Map;

    move-result-object v0

    invoke-interface {v0, p4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    if-ge p6, v0, :cond_8b

    iget-object v0, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    invoke-virtual {v0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getResponseHeaders()Ljava/util/Map;

    move-result-object v0

    invoke-interface {v0, p4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/List;

    invoke-interface {v0, p6}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    aput-object v0, p5, p6

    add-int/lit8 p6, p6, 0x1

    goto :goto_62

    :cond_8b
    invoke-virtual {p2, p4, p5}, Landroid/os/Bundle;->putStringArray(Ljava/lang/String;[Ljava/lang/String;)V

    goto :goto_39

    :cond_8f
    iget-object p3, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    invoke-virtual {p3}, Lcom/unity3d/splash/services/core/request/WebRequest;->isCanceled()Z

    move-result p3

    if-eqz p3, :cond_98

    return-void

    :cond_98
    iget-object p3, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    invoke-virtual {p3}, Lcom/unity3d/splash/services/core/request/WebRequest;->getResponseCode()I

    move-result p3

    invoke-direct {p0, p2}, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->getResponseHeaders(Landroid/os/Bundle;)Ljava/util/Map;

    move-result-object p2

    invoke-direct {p0, p1, p3, p2}, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->onSucceed(Ljava/lang/String;ILjava/util/Map;)V

    return-void

    :catch_a6
    move-exception p1

    goto :goto_ad

    :catch_a8
    move-exception p1

    goto :goto_ad

    :catch_aa
    move-exception p1

    goto :goto_ad

    :catch_ac
    move-exception p1

    :goto_ad
    const-string p2, "Error completing request"

    invoke-static {p2, p1}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object p3

    invoke-virtual {p3}, Ljava/lang/Class;->getName()Ljava/lang/String;

    move-result-object p3

    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string p3, ": "

    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->onFailed(Ljava/lang/String;)V

    return-void
.end method

.method private onFailed(Ljava/lang/String;)V
    .registers 4

    iget-object v0, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_listener:Lcom/unity3d/splash/services/core/request/IWebRequestListener;

    iget-object v1, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_url:Ljava/lang/String;

    invoke-interface {v0, v1, p1}, Lcom/unity3d/splash/services/core/request/IWebRequestListener;->onFailed(Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method private onSucceed(Ljava/lang/String;ILjava/util/Map;)V
    .registers 6

    iget-object v0, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_listener:Lcom/unity3d/splash/services/core/request/IWebRequestListener;

    iget-object v1, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_url:Ljava/lang/String;

    invoke-interface {v0, v1, p1, p2, p3}, Lcom/unity3d/splash/services/core/request/IWebRequestListener;->onComplete(Ljava/lang/String;Ljava/lang/String;ILjava/util/Map;)V

    return-void
.end method


# virtual methods
.method public run()V
    .registers 9

    new-instance v0, Ljava/lang/StringBuilder;

    const-string v1, "Handling request message: "

    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    iget-object v1, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_url:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, " type="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_type:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->debug(Ljava/lang/String;)V

    :try_start_1d
    iget-object v2, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_url:Ljava/lang/String;

    iget-object v3, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_type:Ljava/lang/String;

    iget-object v4, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_headers:Ljava/util/Map;

    iget-object v5, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_body:Ljava/lang/String;

    iget v6, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_connectTimeout:I

    iget v7, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_readTimeout:I

    move-object v1, p0

    invoke-direct/range {v1 .. v7}, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->makeRequest(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;II)V
    :try_end_2d
    .catch Ljava/net/MalformedURLException; {:try_start_1d .. :try_end_2d} :catch_2e

    return-void

    :catch_2e
    move-exception v0

    const-string v1, "Malformed URL"

    invoke-static {v1, v0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    invoke-direct {p0, v1}, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->onFailed(Ljava/lang/String;)V

    return-void
.end method

.method public setCancelStatus(Z)V
    .registers 2

    iput-boolean p1, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_canceled:Z

    if-eqz p1, :cond_b

    iget-object p1, p0, Lcom/unity3d/splash/services/core/request/WebRequestRunnable;->_currentRequest:Lcom/unity3d/splash/services/core/request/WebRequest;

    if-eqz p1, :cond_b

    invoke-virtual {p1}, Lcom/unity3d/splash/services/core/request/WebRequest;->cancel()V

    :cond_b
    return-void
.end method
