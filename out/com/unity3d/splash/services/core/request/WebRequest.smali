.class public Lcom/unity3d/splash/services/core/request/WebRequest;
.super Ljava/lang/Object;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;
    }
.end annotation


# instance fields
.field private _body:Ljava/lang/String;

.field private _canceled:Z

.field private _connectTimeout:I

.field private _contentLength:J

.field private _headers:Ljava/util/Map;

.field private _progressListener:Lcom/unity3d/splash/services/core/request/IWebRequestProgressListener;

.field private _readTimeout:I

.field private _requestType:Ljava/lang/String;

.field private _responseCode:I

.field private _responseHeaders:Ljava/util/Map;

.field private _url:Ljava/net/URL;


# direct methods
.method public constructor <init>(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;)V
    .registers 10

    const/16 v4, 0x7530

    const/16 v5, 0x7530

    move-object v0, p0

    move-object v1, p1

    move-object v2, p2

    move-object v3, p3

    invoke-direct/range {v0 .. v5}, Lcom/unity3d/splash/services/core/request/WebRequest;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;II)V

    return-void
.end method

.method public constructor <init>(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;II)V
    .registers 8

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    sget-object v0, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;->GET:Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

    invoke-virtual {v0}, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;->name()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_requestType:Ljava/lang/String;

    const/4 v0, -0x1

    iput v0, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_responseCode:I

    const-wide/16 v0, -0x1

    iput-wide v0, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_contentLength:J

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_canceled:Z

    new-instance v0, Ljava/net/URL;

    invoke-direct {v0, p1}, Ljava/net/URL;-><init>(Ljava/lang/String;)V

    iput-object v0, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_url:Ljava/net/URL;

    iput-object p2, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_requestType:Ljava/lang/String;

    iput-object p3, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_headers:Ljava/util/Map;

    iput p4, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_connectTimeout:I

    iput p5, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_readTimeout:I

    return-void
.end method

.method private getHttpUrlConnectionWithHeaders()Ljava/net/HttpURLConnection;
    .registers 8

    invoke-virtual {p0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getUrl()Ljava/net/URL;

    move-result-object v0

    invoke-virtual {v0}, Ljava/net/URL;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "https://"

    invoke-virtual {v0, v1}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_34

    :try_start_10
    invoke-virtual {p0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getUrl()Ljava/net/URL;

    move-result-object v0

    invoke-virtual {v0}, Ljava/net/URL;->openConnection()Ljava/net/URLConnection;

    move-result-object v0

    check-cast v0, Ljavax/net/ssl/HttpsURLConnection;
    :try_end_1a
    .catch Ljava/io/IOException; {:try_start_10 .. :try_end_1a} :catch_1b

    goto :goto_4e

    :catch_1b
    move-exception v0

    new-instance v1, Lcom/unity3d/splash/services/core/request/NetworkIOException;

    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "Open HTTPS connection: "

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/io/IOException;->getMessage()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v0}, Lcom/unity3d/splash/services/core/request/NetworkIOException;-><init>(Ljava/lang/String;)V

    throw v1

    :cond_34
    invoke-virtual {p0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getUrl()Ljava/net/URL;

    move-result-object v0

    invoke-virtual {v0}, Ljava/net/URL;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "http://"

    invoke-virtual {v0, v1}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_105

    :try_start_44
    invoke-virtual {p0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getUrl()Ljava/net/URL;

    move-result-object v0

    invoke-virtual {v0}, Ljava/net/URL;->openConnection()Ljava/net/URLConnection;

    move-result-object v0

    check-cast v0, Ljava/net/HttpURLConnection;
    :try_end_4e
    .catch Ljava/io/IOException; {:try_start_44 .. :try_end_4e} :catch_ec

    :goto_4e
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Ljava/net/HttpURLConnection;->setInstanceFollowRedirects(Z)V

    invoke-virtual {p0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getConnectTimeout()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/net/HttpURLConnection;->setConnectTimeout(I)V

    invoke-virtual {p0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getReadTimeout()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/net/HttpURLConnection;->setReadTimeout(I)V

    :try_start_60
    invoke-virtual {p0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getRequestType()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/net/HttpURLConnection;->setRequestMethod(Ljava/lang/String;)V
    :try_end_67
    .catch Ljava/net/ProtocolException; {:try_start_60 .. :try_end_67} :catch_c7

    invoke-virtual {p0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getHeaders()Ljava/util/Map;

    move-result-object v1

    if-eqz v1, :cond_c6

    invoke-virtual {p0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getHeaders()Ljava/util/Map;

    move-result-object v1

    invoke-interface {v1}, Ljava/util/Map;->size()I

    move-result v1

    if-lez v1, :cond_c6

    invoke-virtual {p0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getHeaders()Ljava/util/Map;

    move-result-object v1

    invoke-interface {v1}, Ljava/util/Map;->keySet()Ljava/util/Set;

    move-result-object v1

    invoke-interface {v1}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :cond_83
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_c6

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    invoke-virtual {p0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getHeaders()Ljava/util/Map;

    move-result-object v3

    invoke-interface {v3, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/util/List;

    invoke-interface {v3}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v3

    :goto_9d
    invoke-interface {v3}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-eqz v4, :cond_83

    invoke-interface {v3}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/lang/String;

    new-instance v5, Ljava/lang/StringBuilder;

    const-string v6, "Setting header: "

    invoke-direct {v5, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v5, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v6, "="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v5}, Lcom/unity3d/splash/services/core/log/DeviceLog;->debug(Ljava/lang/String;)V

    invoke-virtual {v0, v2, v4}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_9d

    :cond_c6
    return-object v0

    :catch_c7
    move-exception v0

    new-instance v1, Lcom/unity3d/splash/services/core/request/NetworkIOException;

    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "Set Request Method: "

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {p0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getRequestType()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, ", "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/net/ProtocolException;->getMessage()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v0}, Lcom/unity3d/splash/services/core/request/NetworkIOException;-><init>(Ljava/lang/String;)V

    throw v1

    :catch_ec
    move-exception v0

    new-instance v1, Lcom/unity3d/splash/services/core/request/NetworkIOException;

    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "Open HTTP connection: "

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/io/IOException;->getMessage()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v0}, Lcom/unity3d/splash/services/core/request/NetworkIOException;-><init>(Ljava/lang/String;)V

    throw v1

    :cond_105
    new-instance v0, Ljava/lang/IllegalArgumentException;

    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "Invalid url-protocol in url: "

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {p0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getUrl()Ljava/net/URL;

    move-result-object v2

    invoke-virtual {v2}, Ljava/net/URL;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v0
.end method


# virtual methods
.method public cancel()V
    .registers 2

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_canceled:Z

    return-void
.end method

.method public getBody()Ljava/lang/String;
    .registers 2

    iget-object v0, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_body:Ljava/lang/String;

    return-object v0
.end method

.method public getConnectTimeout()I
    .registers 2

    iget v0, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_connectTimeout:I

    return v0
.end method

.method public getContentLength()J
    .registers 3

    iget-wide v0, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_contentLength:J

    return-wide v0
.end method

.method public getHeaders()Ljava/util/Map;
    .registers 2

    iget-object v0, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_headers:Ljava/util/Map;

    return-object v0
.end method

.method public getQuery()Ljava/lang/String;
    .registers 2

    iget-object v0, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_url:Ljava/net/URL;

    if-eqz v0, :cond_9

    invoke-virtual {v0}, Ljava/net/URL;->getQuery()Ljava/lang/String;

    move-result-object v0

    return-object v0

    :cond_9
    const/4 v0, 0x0

    return-object v0
.end method

.method public getReadTimeout()I
    .registers 2

    iget v0, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_readTimeout:I

    return v0
.end method

.method public getRequestType()Ljava/lang/String;
    .registers 2

    iget-object v0, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_requestType:Ljava/lang/String;

    return-object v0
.end method

.method public getResponseCode()I
    .registers 2

    iget v0, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_responseCode:I

    return v0
.end method

.method public getResponseHeaders()Ljava/util/Map;
    .registers 2

    iget-object v0, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_responseHeaders:Ljava/util/Map;

    return-object v0
.end method

.method public getUrl()Ljava/net/URL;
    .registers 2

    iget-object v0, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_url:Ljava/net/URL;

    return-object v0
.end method

.method public isCanceled()Z
    .registers 2

    iget-boolean v0, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_canceled:Z

    return v0
.end method

.method public makeRequest()Ljava/lang/String;
    .registers 3

    new-instance v0, Ljava/io/ByteArrayOutputStream;

    invoke-direct {v0}, Ljava/io/ByteArrayOutputStream;-><init>()V

    invoke-virtual {p0, v0}, Lcom/unity3d/splash/services/core/request/WebRequest;->makeStreamRequest(Ljava/io/OutputStream;)J

    const-string v1, "UTF-8"

    invoke-virtual {v0, v1}, Ljava/io/ByteArrayOutputStream;->toString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public makeStreamRequest(Ljava/io/OutputStream;)J
    .registers 19

    move-object/from16 v1, p0

    const-string v2, "Error closing writer"

    invoke-direct/range {p0 .. p0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getHttpUrlConnectionWithHeaders()Ljava/net/HttpURLConnection;

    move-result-object v3

    const/4 v0, 0x1

    invoke-virtual {v3, v0}, Ljava/net/HttpURLConnection;->setDoInput(Z)V

    invoke-virtual/range {p0 .. p0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getRequestType()Ljava/lang/String;

    move-result-object v4

    sget-object v5, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;->POST:Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

    invoke-virtual {v5}, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;->name()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_83

    invoke-virtual {v3, v0}, Ljava/net/HttpURLConnection;->setDoOutput(Z)V

    const/4 v4, 0x0

    :try_start_20
    new-instance v5, Ljava/io/PrintWriter;

    new-instance v6, Ljava/io/OutputStreamWriter;

    invoke-virtual {v3}, Ljava/net/HttpURLConnection;->getOutputStream()Ljava/io/OutputStream;

    move-result-object v7

    const-string v8, "UTF-8"

    invoke-direct {v6, v7, v8}, Ljava/io/OutputStreamWriter;-><init>(Ljava/io/OutputStream;Ljava/lang/String;)V

    invoke-direct {v5, v6, v0}, Ljava/io/PrintWriter;-><init>(Ljava/io/Writer;Z)V
    :try_end_30
    .catch Ljava/io/IOException; {:try_start_20 .. :try_end_30} :catch_58
    .catchall {:try_start_20 .. :try_end_30} :catchall_56

    :try_start_30
    invoke-virtual/range {p0 .. p0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getBody()Ljava/lang/String;

    move-result-object v0

    if-nez v0, :cond_3e

    invoke-virtual/range {p0 .. p0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getQuery()Ljava/lang/String;

    move-result-object v0

    :goto_3a
    invoke-virtual {v5, v0}, Ljava/io/PrintWriter;->print(Ljava/lang/String;)V

    goto :goto_43

    :cond_3e
    invoke-virtual/range {p0 .. p0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getBody()Ljava/lang/String;

    move-result-object v0

    goto :goto_3a

    :goto_43
    invoke-virtual {v5}, Ljava/io/PrintWriter;->flush()V
    :try_end_46
    .catch Ljava/io/IOException; {:try_start_30 .. :try_end_46} :catch_53
    .catchall {:try_start_30 .. :try_end_46} :catchall_50

    :try_start_46
    invoke-virtual {v5}, Ljava/io/PrintWriter;->close()V
    :try_end_49
    .catch Ljava/lang/Exception; {:try_start_46 .. :try_end_49} :catch_4a

    goto :goto_83

    :catch_4a
    move-exception v0

    move-object v3, v0

    invoke-static {v2, v3}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    throw v3

    :catchall_50
    move-exception v0

    move-object v4, v5

    goto :goto_76

    :catch_53
    move-exception v0

    move-object v4, v5

    goto :goto_59

    :catchall_56
    move-exception v0

    goto :goto_76

    :catch_58
    move-exception v0

    :goto_59
    :try_start_59
    const-string v3, "Error while writing POST params"

    invoke-static {v3, v0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    new-instance v3, Lcom/unity3d/splash/services/core/request/NetworkIOException;

    new-instance v5, Ljava/lang/StringBuilder;

    const-string v6, "Error writing POST params: "

    invoke-direct {v5, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/io/IOException;->getMessage()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v3, v0}, Lcom/unity3d/splash/services/core/request/NetworkIOException;-><init>(Ljava/lang/String;)V

    throw v3
    :try_end_76
    .catchall {:try_start_59 .. :try_end_76} :catchall_56

    :goto_76
    if-eqz v4, :cond_82

    :try_start_78
    invoke-virtual {v4}, Ljava/io/PrintWriter;->close()V
    :try_end_7b
    .catch Ljava/lang/Exception; {:try_start_78 .. :try_end_7b} :catch_7c

    goto :goto_82

    :catch_7c
    move-exception v0

    move-object v3, v0

    invoke-static {v2, v3}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    throw v3

    :cond_82
    :goto_82
    throw v0

    :cond_83
    :goto_83
    :try_start_83
    invoke-virtual {v3}, Ljava/net/HttpURLConnection;->getResponseCode()I

    move-result v0

    iput v0, v1, Lcom/unity3d/splash/services/core/request/WebRequest;->_responseCode:I
    :try_end_89
    .catch Ljava/io/IOException; {:try_start_83 .. :try_end_89} :catch_132
    .catch Ljava/lang/RuntimeException; {:try_start_83 .. :try_end_89} :catch_130

    invoke-virtual {v3}, Ljava/net/HttpURLConnection;->getContentLength()I

    move-result v0

    int-to-long v4, v0

    iput-wide v4, v1, Lcom/unity3d/splash/services/core/request/WebRequest;->_contentLength:J

    invoke-virtual {v3}, Ljava/net/HttpURLConnection;->getHeaderFields()Ljava/util/Map;

    move-result-object v0

    if-eqz v0, :cond_9c

    invoke-virtual {v3}, Ljava/net/HttpURLConnection;->getHeaderFields()Ljava/util/Map;

    move-result-object v0

    iput-object v0, v1, Lcom/unity3d/splash/services/core/request/WebRequest;->_responseHeaders:Ljava/util/Map;

    :cond_9c
    :try_start_9c
    invoke-virtual {v3}, Ljava/net/HttpURLConnection;->getInputStream()Ljava/io/InputStream;

    move-result-object v0
    :try_end_a0
    .catch Ljava/io/IOException; {:try_start_9c .. :try_end_a0} :catch_a1

    goto :goto_a9

    :catch_a1
    move-exception v0

    move-object v2, v0

    invoke-virtual {v3}, Ljava/net/HttpURLConnection;->getErrorStream()Ljava/io/InputStream;

    move-result-object v0

    if-eqz v0, :cond_118

    :goto_a9
    iget-object v4, v1, Lcom/unity3d/splash/services/core/request/WebRequest;->_progressListener:Lcom/unity3d/splash/services/core/request/IWebRequestProgressListener;

    if-eqz v4, :cond_be

    invoke-virtual/range {p0 .. p0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getUrl()Ljava/net/URL;

    move-result-object v2

    invoke-virtual {v2}, Ljava/net/URL;->toString()Ljava/lang/String;

    move-result-object v5

    iget-wide v6, v1, Lcom/unity3d/splash/services/core/request/WebRequest;->_contentLength:J

    iget v8, v1, Lcom/unity3d/splash/services/core/request/WebRequest;->_responseCode:I

    iget-object v9, v1, Lcom/unity3d/splash/services/core/request/WebRequest;->_responseHeaders:Ljava/util/Map;

    invoke-interface/range {v4 .. v9}, Lcom/unity3d/splash/services/core/request/IWebRequestProgressListener;->onRequestStart(Ljava/lang/String;JILjava/util/Map;)V

    :cond_be
    new-instance v2, Ljava/io/BufferedInputStream;

    invoke-direct {v2, v0}, Ljava/io/BufferedInputStream;-><init>(Ljava/io/InputStream;)V

    const-wide/16 v4, 0x0

    const/16 v0, 0x1000

    new-array v0, v0, [B

    const/4 v6, 0x0

    const/4 v7, 0x0

    :cond_cb
    :goto_cb
    invoke-virtual/range {p0 .. p0}, Lcom/unity3d/splash/services/core/request/WebRequest;->isCanceled()Z

    move-result v8

    if-nez v8, :cond_10f

    const/4 v8, -0x1

    if-eq v7, v8, :cond_10f

    :try_start_d4
    invoke-virtual {v2, v0}, Ljava/io/BufferedInputStream;->read([B)I

    move-result v7
    :try_end_d8
    .catch Ljava/io/IOException; {:try_start_d4 .. :try_end_d8} :catch_f5

    move-object/from16 v8, p1

    if-lez v7, :cond_cb

    invoke-virtual {v8, v0, v6, v7}, Ljava/io/OutputStream;->write([BII)V

    int-to-long v9, v7

    add-long/2addr v4, v9

    iget-object v11, v1, Lcom/unity3d/splash/services/core/request/WebRequest;->_progressListener:Lcom/unity3d/splash/services/core/request/IWebRequestProgressListener;

    if-eqz v11, :cond_cb

    invoke-virtual/range {p0 .. p0}, Lcom/unity3d/splash/services/core/request/WebRequest;->getUrl()Ljava/net/URL;

    move-result-object v9

    invoke-virtual {v9}, Ljava/net/URL;->toString()Ljava/lang/String;

    move-result-object v12

    iget-wide v9, v1, Lcom/unity3d/splash/services/core/request/WebRequest;->_contentLength:J

    move-wide v13, v4

    move-wide v15, v9

    invoke-interface/range {v11 .. v16}, Lcom/unity3d/splash/services/core/request/IWebRequestProgressListener;->onRequestProgress(Ljava/lang/String;JJ)V

    goto :goto_cb

    :catch_f5
    move-exception v0

    move-object v2, v0

    new-instance v0, Lcom/unity3d/splash/services/core/request/NetworkIOException;

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Network exception: "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2}, Ljava/io/IOException;->getMessage()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-direct {v0, v2}, Lcom/unity3d/splash/services/core/request/NetworkIOException;-><init>(Ljava/lang/String;)V

    throw v0

    :cond_10f
    move-object/from16 v8, p1

    invoke-virtual {v3}, Ljava/net/HttpURLConnection;->disconnect()V

    invoke-virtual/range {p1 .. p1}, Ljava/io/OutputStream;->flush()V

    return-wide v4

    :cond_118
    new-instance v0, Lcom/unity3d/splash/services/core/request/NetworkIOException;

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Can\'t open error stream: "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2}, Ljava/io/IOException;->getMessage()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-direct {v0, v2}, Lcom/unity3d/splash/services/core/request/NetworkIOException;-><init>(Ljava/lang/String;)V

    throw v0

    :catch_130
    move-exception v0

    goto :goto_133

    :catch_132
    move-exception v0

    :goto_133
    new-instance v2, Lcom/unity3d/splash/services/core/request/NetworkIOException;

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Response code: "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v0}, Lcom/unity3d/splash/services/core/request/NetworkIOException;-><init>(Ljava/lang/String;)V

    throw v2
.end method

.method public setBody(Ljava/lang/String;)V
    .registers 2

    iput-object p1, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_body:Ljava/lang/String;

    return-void
.end method

.method public setConnectTimeout(I)V
    .registers 2

    iput p1, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_connectTimeout:I

    return-void
.end method

.method public setProgressListener(Lcom/unity3d/splash/services/core/request/IWebRequestProgressListener;)V
    .registers 2

    iput-object p1, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_progressListener:Lcom/unity3d/splash/services/core/request/IWebRequestProgressListener;

    return-void
.end method

.method public setReadTimeout(I)V
    .registers 2

    iput p1, p0, Lcom/unity3d/splash/services/core/request/WebRequest;->_readTimeout:I

    return-void
.end method
