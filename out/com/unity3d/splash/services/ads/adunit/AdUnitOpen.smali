.class public Lcom/unity3d/splash/services/ads/adunit/AdUnitOpen;
.super Ljava/lang/Object;


# static fields
.field private static _waitShowStatus:Landroid/os/ConditionVariable;


# direct methods
.method public constructor <init>()V
    .registers 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static declared-synchronized open(Ljava/lang/String;Lorg/json/JSONObject;)Z
    .registers 10

    const-class v0, Lcom/unity3d/splash/services/ads/adunit/AdUnitOpen;

    monitor-enter v0

    :try_start_3
    const-string v1, "showCallback"

    const/4 v2, 0x1

    new-array v3, v2, [Ljava/lang/Class;

    const-class v4, Lcom/unity3d/splash/services/core/webview/bridge/CallbackStatus;

    const/4 v5, 0x0

    aput-object v4, v3, v5

    invoke-virtual {v0, v1, v3}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v1

    new-instance v3, Landroid/os/ConditionVariable;

    invoke-direct {v3}, Landroid/os/ConditionVariable;-><init>()V

    sput-object v3, Lcom/unity3d/splash/services/ads/adunit/AdUnitOpen;->_waitShowStatus:Landroid/os/ConditionVariable;

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v3

    const-string v4, "webview"

    const-string v6, "show"

    const/4 v7, 0x2

    new-array v7, v7, [Ljava/lang/Object;

    aput-object p0, v7, v5

    aput-object p1, v7, v2

    invoke-virtual {v3, v4, v6, v1, v7}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->invokeMethod(Ljava/lang/String;Ljava/lang/String;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Z

    sget-object p0, Lcom/unity3d/splash/services/ads/adunit/AdUnitOpen;->_waitShowStatus:Landroid/os/ConditionVariable;

    invoke-static {}, Lcom/unity3d/splash/services/ads/properties/AdsProperties;->getShowTimeout()I

    move-result p1

    int-to-long v1, p1

    invoke-virtual {p0, v1, v2}, Landroid/os/ConditionVariable;->block(J)Z

    move-result p0

    const/4 p1, 0x0

    sput-object p1, Lcom/unity3d/splash/services/ads/adunit/AdUnitOpen;->_waitShowStatus:Landroid/os/ConditionVariable;
    :try_end_38
    .catchall {:try_start_3 .. :try_end_38} :catchall_3a

    monitor-exit v0

    return p0

    :catchall_3a
    move-exception p0

    monitor-exit v0

    throw p0
.end method

.method public static showCallback(Lcom/unity3d/splash/services/core/webview/bridge/CallbackStatus;)V
    .registers 2

    sget-object v0, Lcom/unity3d/splash/services/ads/adunit/AdUnitOpen;->_waitShowStatus:Landroid/os/ConditionVariable;

    if-eqz v0, :cond_11

    sget-object v0, Lcom/unity3d/splash/services/core/webview/bridge/CallbackStatus;->OK:Lcom/unity3d/splash/services/core/webview/bridge/CallbackStatus;

    invoke-virtual {p0, v0}, Lcom/unity3d/splash/services/core/webview/bridge/CallbackStatus;->equals(Ljava/lang/Object;)Z

    move-result p0

    if-eqz p0, :cond_11

    sget-object p0, Lcom/unity3d/splash/services/ads/adunit/AdUnitOpen;->_waitShowStatus:Landroid/os/ConditionVariable;

    invoke-virtual {p0}, Landroid/os/ConditionVariable;->open()V

    :cond_11
    return-void
.end method
