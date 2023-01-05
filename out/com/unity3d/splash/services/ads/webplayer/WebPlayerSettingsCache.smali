.class public Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;
.super Ljava/lang/Object;


# static fields
.field private static instance:Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;


# instance fields
.field private _webPlayerEventSettings:Ljava/util/HashMap;

.field private _webPlayerSettings:Ljava/util/HashMap;

.field private _webSettings:Ljava/util/HashMap;


# direct methods
.method public constructor <init>()V
    .registers 2

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    iput-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;->_webSettings:Ljava/util/HashMap;

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    iput-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;->_webPlayerSettings:Ljava/util/HashMap;

    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    iput-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;->_webPlayerEventSettings:Ljava/util/HashMap;

    return-void
.end method

.method public static getInstance()Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;
    .registers 1

    sget-object v0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;->instance:Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;

    if-nez v0, :cond_b

    new-instance v0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;

    invoke-direct {v0}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;-><init>()V

    sput-object v0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;->instance:Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;

    :cond_b
    sget-object v0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;->instance:Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;

    return-object v0
.end method


# virtual methods
.method public declared-synchronized addWebPlayerEventSettings(Ljava/lang/String;Lorg/json/JSONObject;)V
    .registers 4

    monitor-enter p0

    :try_start_1
    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;->_webPlayerEventSettings:Ljava/util/HashMap;

    invoke-virtual {v0, p1, p2}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_6
    .catchall {:try_start_1 .. :try_end_6} :catchall_8

    monitor-exit p0

    return-void

    :catchall_8
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public declared-synchronized addWebPlayerSettings(Ljava/lang/String;Lorg/json/JSONObject;)V
    .registers 4

    monitor-enter p0

    :try_start_1
    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;->_webPlayerSettings:Ljava/util/HashMap;

    invoke-virtual {v0, p1, p2}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_6
    .catchall {:try_start_1 .. :try_end_6} :catchall_8

    monitor-exit p0

    return-void

    :catchall_8
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public declared-synchronized addWebSettings(Ljava/lang/String;Lorg/json/JSONObject;)V
    .registers 4

    monitor-enter p0

    :try_start_1
    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;->_webSettings:Ljava/util/HashMap;

    invoke-virtual {v0, p1, p2}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_6
    .catchall {:try_start_1 .. :try_end_6} :catchall_8

    monitor-exit p0

    return-void

    :catchall_8
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public declared-synchronized getWebPlayerEventSettings(Ljava/lang/String;)Lorg/json/JSONObject;
    .registers 3

    monitor-enter p0

    :try_start_1
    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;->_webPlayerEventSettings:Ljava/util/HashMap;

    invoke-virtual {v0, p1}, Ljava/util/HashMap;->containsKey(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_13

    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;->_webPlayerEventSettings:Ljava/util/HashMap;

    invoke-virtual {v0, p1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Lorg/json/JSONObject;
    :try_end_11
    .catchall {:try_start_1 .. :try_end_11} :catchall_1a

    monitor-exit p0

    return-object p1

    :cond_13
    :try_start_13
    new-instance p1, Lorg/json/JSONObject;

    invoke-direct {p1}, Lorg/json/JSONObject;-><init>()V
    :try_end_18
    .catchall {:try_start_13 .. :try_end_18} :catchall_1a

    monitor-exit p0

    return-object p1

    :catchall_1a
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public declared-synchronized getWebPlayerSettings(Ljava/lang/String;)Lorg/json/JSONObject;
    .registers 3

    monitor-enter p0

    :try_start_1
    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;->_webPlayerSettings:Ljava/util/HashMap;

    invoke-virtual {v0, p1}, Ljava/util/HashMap;->containsKey(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_13

    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;->_webPlayerSettings:Ljava/util/HashMap;

    invoke-virtual {v0, p1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Lorg/json/JSONObject;
    :try_end_11
    .catchall {:try_start_1 .. :try_end_11} :catchall_1a

    monitor-exit p0

    return-object p1

    :cond_13
    :try_start_13
    new-instance p1, Lorg/json/JSONObject;

    invoke-direct {p1}, Lorg/json/JSONObject;-><init>()V
    :try_end_18
    .catchall {:try_start_13 .. :try_end_18} :catchall_1a

    monitor-exit p0

    return-object p1

    :catchall_1a
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public declared-synchronized getWebSettings(Ljava/lang/String;)Lorg/json/JSONObject;
    .registers 3

    monitor-enter p0

    :try_start_1
    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;->_webSettings:Ljava/util/HashMap;

    invoke-virtual {v0, p1}, Ljava/util/HashMap;->containsKey(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_13

    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;->_webSettings:Ljava/util/HashMap;

    invoke-virtual {v0, p1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Lorg/json/JSONObject;
    :try_end_11
    .catchall {:try_start_1 .. :try_end_11} :catchall_1a

    monitor-exit p0

    return-object p1

    :cond_13
    :try_start_13
    new-instance p1, Lorg/json/JSONObject;

    invoke-direct {p1}, Lorg/json/JSONObject;-><init>()V
    :try_end_18
    .catchall {:try_start_13 .. :try_end_18} :catchall_1a

    monitor-exit p0

    return-object p1

    :catchall_1a
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public declared-synchronized removeWebPlayerEventSettings(Ljava/lang/String;)V
    .registers 3

    monitor-enter p0

    :try_start_1
    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;->_webPlayerEventSettings:Ljava/util/HashMap;

    invoke-virtual {v0, p1}, Ljava/util/HashMap;->containsKey(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_e

    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;->_webPlayerEventSettings:Ljava/util/HashMap;

    invoke-virtual {v0, p1}, Ljava/util/HashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_e
    .catchall {:try_start_1 .. :try_end_e} :catchall_10

    :cond_e
    monitor-exit p0

    return-void

    :catchall_10
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public declared-synchronized removeWebPlayerSettings(Ljava/lang/String;)V
    .registers 3

    monitor-enter p0

    :try_start_1
    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;->_webPlayerSettings:Ljava/util/HashMap;

    invoke-virtual {v0, p1}, Ljava/util/HashMap;->containsKey(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_e

    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;->_webPlayerSettings:Ljava/util/HashMap;

    invoke-virtual {v0, p1}, Ljava/util/HashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_e
    .catchall {:try_start_1 .. :try_end_e} :catchall_10

    :cond_e
    monitor-exit p0

    return-void

    :catchall_10
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public declared-synchronized removeWebSettings(Ljava/lang/String;)V
    .registers 3

    monitor-enter p0

    :try_start_1
    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;->_webSettings:Ljava/util/HashMap;

    invoke-virtual {v0, p1}, Ljava/util/HashMap;->containsKey(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_e

    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerSettingsCache;->_webSettings:Ljava/util/HashMap;

    invoke-virtual {v0, p1}, Ljava/util/HashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_e
    .catchall {:try_start_1 .. :try_end_e} :catchall_10

    :cond_e
    monitor-exit p0

    return-void

    :catchall_10
    move-exception p1

    monitor-exit p0

    throw p1
.end method
