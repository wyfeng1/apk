.class public Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;
.super Ljava/lang/Object;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/unity3d/splash/services/core/device/OpenAdvertisingId$HWAdvertisingServiceConnection;,
        Lcom/unity3d/splash/services/core/device/OpenAdvertisingId$HWAdvertisingInfo;
    }
.end annotation


# static fields
.field private static final HW_DEVICE_NAME:Ljava/lang/String; = "HUAWEI"

.field private static final HW_OPEN_ADVERTISING_ID_SERVICE_NAME:Ljava/lang/String; = "com.uodis.opendevice.aidl.OpenDeviceIdentifierService"

.field private static instance:Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;


# instance fields
.field private limitedOpenAdTracking:Z

.field private openAdvertisingIdentifier:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .registers 0

    return-void
.end method

.method public constructor <init>()V
    .registers 2

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;->openAdvertisingIdentifier:Ljava/lang/String;

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;->limitedOpenAdTracking:Z

    return-void
.end method

.method private fetchOAId(Landroid/content/Context;)V
    .registers 7

    new-instance v0, Lcom/unity3d/splash/services/core/device/OpenAdvertisingId$HWAdvertisingServiceConnection;

    const/4 v1, 0x0

    invoke-direct {v0, p0, v1}, Lcom/unity3d/splash/services/core/device/OpenAdvertisingId$HWAdvertisingServiceConnection;-><init>(Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;Lcom/unity3d/splash/services/core/device/OpenAdvertisingId$1;)V

    new-instance v1, Landroid/content/Intent;

    const-string v2, "com.uodis.opendevice.OPENIDS_SERVICE"

    invoke-direct {v1, v2}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    const-string v2, "com.huawei.hwid"

    invoke-virtual {v1, v2}, Landroid/content/Intent;->setPackage(Ljava/lang/String;)Landroid/content/Intent;

    const/4 v2, 0x1

    :try_start_13
    invoke-virtual {p1, v1, v0, v2}, Landroid/content/Context;->bindService(Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z

    move-result v1
    :try_end_17
    .catch Ljava/lang/Exception; {:try_start_13 .. :try_end_17} :catch_18

    goto :goto_1f

    :catch_18
    move-exception v1

    const-string v3, "Couldn\'t bind to identifier service intent"

    invoke-static {v3, v1}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    const/4 v1, 0x0

    :goto_1f
    if-eqz v1, :cond_4a

    :try_start_21
    invoke-virtual {v0}, Lcom/unity3d/splash/services/core/device/OpenAdvertisingId$HWAdvertisingServiceConnection;->getBinder()Landroid/os/IBinder;

    move-result-object v3

    invoke-static {v3}, Lcom/unity3d/splash/services/core/device/OpenAdvertisingId$HWAdvertisingInfo$HWAdvertisingInfoBinder;->create(Landroid/os/IBinder;)Lcom/unity3d/splash/services/core/device/OpenAdvertisingId$HWAdvertisingInfo;

    move-result-object v3

    invoke-interface {v3}, Lcom/unity3d/splash/services/core/device/OpenAdvertisingId$HWAdvertisingInfo;->getId()Ljava/lang/String;

    move-result-object v4

    iput-object v4, p0, Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;->openAdvertisingIdentifier:Ljava/lang/String;

    invoke-interface {v3, v2}, Lcom/unity3d/splash/services/core/device/OpenAdvertisingId$HWAdvertisingInfo;->getEnabled(Z)Z

    move-result v2

    iput-boolean v2, p0, Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;->limitedOpenAdTracking:Z
    :try_end_35
    .catch Ljava/lang/Exception; {:try_start_21 .. :try_end_35} :catch_38
    .catchall {:try_start_21 .. :try_end_35} :catchall_36

    goto :goto_4a

    :catchall_36
    move-exception v2

    goto :goto_44

    :catch_38
    move-exception v2

    :try_start_39
    const-string v3, "Couldn\'t get openAdvertising info"

    invoke-static {v3, v2}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V
    :try_end_3e
    .catchall {:try_start_39 .. :try_end_3e} :catchall_36

    if-eqz v1, :cond_4f

    invoke-virtual {p1, v0}, Landroid/content/Context;->unbindService(Landroid/content/ServiceConnection;)V

    return-void

    :goto_44
    if-eqz v1, :cond_49

    invoke-virtual {p1, v0}, Landroid/content/Context;->unbindService(Landroid/content/ServiceConnection;)V

    :cond_49
    throw v2

    :cond_4a
    :goto_4a
    if-eqz v1, :cond_4f

    invoke-virtual {p1, v0}, Landroid/content/Context;->unbindService(Landroid/content/ServiceConnection;)V

    :cond_4f
    return-void
.end method

.method private static getInstance()Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;
    .registers 1

    sget-object v0, Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;->instance:Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;

    if-nez v0, :cond_b

    new-instance v0, Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;

    invoke-direct {v0}, Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;-><init>()V

    sput-object v0, Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;->instance:Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;

    :cond_b
    sget-object v0, Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;->instance:Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;

    return-object v0
.end method

.method public static getLimitedOpenAdTracking()Z
    .registers 1

    invoke-static {}, Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;->getInstance()Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;

    move-result-object v0

    iget-boolean v0, v0, Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;->limitedOpenAdTracking:Z

    return v0
.end method

.method public static getOpenAdvertisingTrackingId()Ljava/lang/String;
    .registers 1

    invoke-static {}, Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;->getInstance()Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;

    move-result-object v0

    iget-object v0, v0, Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;->openAdvertisingIdentifier:Ljava/lang/String;

    return-object v0
.end method

.method public static init(Landroid/content/Context;)V
    .registers 3

    sget-object v0, Landroid/os/Build;->MANUFACTURER:Ljava/lang/String;

    invoke-virtual {v0}, Ljava/lang/String;->toUpperCase()Ljava/lang/String;

    move-result-object v0

    const-string v1, "HUAWEI"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_15

    invoke-static {}, Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;->getInstance()Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;

    move-result-object v0

    invoke-direct {v0, p0}, Lcom/unity3d/splash/services/core/device/OpenAdvertisingId;->fetchOAId(Landroid/content/Context;)V

    :cond_15
    return-void
.end method
