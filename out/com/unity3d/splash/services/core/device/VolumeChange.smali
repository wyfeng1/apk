.class public Lcom/unity3d/splash/services/core/device/VolumeChange;
.super Ljava/lang/Object;


# static fields
.field private static _contentObserver:Landroid/database/ContentObserver;

.field private static _listeners:Ljava/util/ArrayList;


# direct methods
.method public constructor <init>()V
    .registers 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$000()V
    .registers 0

    invoke-static {}, Lcom/unity3d/splash/services/core/device/VolumeChange;->triggerListeners()V

    return-void
.end method

.method public static clearAllListeners()V
    .registers 1

    sget-object v0, Lcom/unity3d/splash/services/core/device/VolumeChange;->_listeners:Ljava/util/ArrayList;

    if-eqz v0, :cond_7

    invoke-virtual {v0}, Ljava/util/ArrayList;->clear()V

    :cond_7
    invoke-static {}, Lcom/unity3d/splash/services/core/device/VolumeChange;->stopObservering()V

    const/4 v0, 0x0

    sput-object v0, Lcom/unity3d/splash/services/core/device/VolumeChange;->_listeners:Ljava/util/ArrayList;

    return-void
.end method

.method public static registerListener(Lcom/unity3d/splash/services/core/device/IVolumeChangeListener;)V
    .registers 2

    sget-object v0, Lcom/unity3d/splash/services/core/device/VolumeChange;->_listeners:Ljava/util/ArrayList;

    if-nez v0, :cond_b

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    sput-object v0, Lcom/unity3d/splash/services/core/device/VolumeChange;->_listeners:Ljava/util/ArrayList;

    :cond_b
    sget-object v0, Lcom/unity3d/splash/services/core/device/VolumeChange;->_listeners:Ljava/util/ArrayList;

    invoke-virtual {v0, p0}, Ljava/util/ArrayList;->contains(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_1b

    invoke-static {}, Lcom/unity3d/splash/services/core/device/VolumeChange;->startObserving()V

    sget-object v0, Lcom/unity3d/splash/services/core/device/VolumeChange;->_listeners:Ljava/util/ArrayList;

    invoke-virtual {v0, p0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    :cond_1b
    return-void
.end method

.method public static startObserving()V
    .registers 4

    sget-object v0, Lcom/unity3d/splash/services/core/device/VolumeChange;->_contentObserver:Landroid/database/ContentObserver;

    if-nez v0, :cond_28

    new-instance v0, Lcom/unity3d/splash/services/core/device/VolumeChange$1;

    new-instance v1, Landroid/os/Handler;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v2

    invoke-direct {v1, v2}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    invoke-direct {v0, v1}, Lcom/unity3d/splash/services/core/device/VolumeChange$1;-><init>(Landroid/os/Handler;)V

    sput-object v0, Lcom/unity3d/splash/services/core/device/VolumeChange;->_contentObserver:Landroid/database/ContentObserver;

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/ClientProperties;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    if-eqz v0, :cond_28

    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    if-eqz v0, :cond_28

    sget-object v1, Landroid/provider/Settings$System;->CONTENT_URI:Landroid/net/Uri;

    const/4 v2, 0x1

    sget-object v3, Lcom/unity3d/splash/services/core/device/VolumeChange;->_contentObserver:Landroid/database/ContentObserver;

    invoke-virtual {v0, v1, v2, v3}, Landroid/content/ContentResolver;->registerContentObserver(Landroid/net/Uri;ZLandroid/database/ContentObserver;)V

    :cond_28
    return-void
.end method

.method public static stopObservering()V
    .registers 2

    sget-object v0, Lcom/unity3d/splash/services/core/device/VolumeChange;->_contentObserver:Landroid/database/ContentObserver;

    if-eqz v0, :cond_18

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/ClientProperties;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    if-eqz v0, :cond_15

    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    if-eqz v0, :cond_15

    sget-object v1, Lcom/unity3d/splash/services/core/device/VolumeChange;->_contentObserver:Landroid/database/ContentObserver;

    invoke-virtual {v0, v1}, Landroid/content/ContentResolver;->unregisterContentObserver(Landroid/database/ContentObserver;)V

    :cond_15
    const/4 v0, 0x0

    sput-object v0, Lcom/unity3d/splash/services/core/device/VolumeChange;->_contentObserver:Landroid/database/ContentObserver;

    :cond_18
    return-void
.end method

.method private static triggerListeners()V
    .registers 3

    sget-object v0, Lcom/unity3d/splash/services/core/device/VolumeChange;->_listeners:Ljava/util/ArrayList;

    if-eqz v0, :cond_20

    invoke-virtual {v0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_8
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_20

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/unity3d/splash/services/core/device/IVolumeChangeListener;

    invoke-interface {v1}, Lcom/unity3d/splash/services/core/device/IVolumeChangeListener;->getStreamType()I

    move-result v2

    invoke-static {v2}, Lcom/unity3d/splash/services/core/device/Device;->getStreamVolume(I)I

    move-result v2

    invoke-interface {v1, v2}, Lcom/unity3d/splash/services/core/device/IVolumeChangeListener;->onVolumeChanged(I)V

    goto :goto_8

    :cond_20
    return-void
.end method

.method public static unregisterListener(Lcom/unity3d/splash/services/core/device/IVolumeChangeListener;)V
    .registers 2

    sget-object v0, Lcom/unity3d/splash/services/core/device/VolumeChange;->_listeners:Ljava/util/ArrayList;

    invoke-virtual {v0, p0}, Ljava/util/ArrayList;->contains(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_d

    sget-object v0, Lcom/unity3d/splash/services/core/device/VolumeChange;->_listeners:Ljava/util/ArrayList;

    invoke-virtual {v0, p0}, Ljava/util/ArrayList;->remove(Ljava/lang/Object;)Z

    :cond_d
    sget-object p0, Lcom/unity3d/splash/services/core/device/VolumeChange;->_listeners:Ljava/util/ArrayList;

    if-eqz p0, :cond_17

    invoke-virtual {p0}, Ljava/util/ArrayList;->size()I

    move-result p0

    if-nez p0, :cond_1a

    :cond_17
    invoke-static {}, Lcom/unity3d/splash/services/core/device/VolumeChange;->stopObservering()V

    :cond_1a
    return-void
.end method
