.class public Lcom/unity3d/splash/services/UnityServices;
.super Ljava/lang/Object;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/unity3d/splash/services/UnityServices$UnityServicesError;
    }
.end annotation


# static fields
.field private static _configurationInitialized:Z = false


# direct methods
.method static constructor <clinit>()V
    .registers 0

    return-void
.end method

.method public constructor <init>()V
    .registers 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static getDebugMode()Z
    .registers 1

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->getDebugMode()Z

    move-result v0

    return v0
.end method

.method public static getVersion()Ljava/lang/String;
    .registers 1

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->getVersionName()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static initialize(Landroid/app/Activity;Ljava/lang/String;Lcom/unity3d/splash/services/IUnityServicesListener;ZZ)V
    .registers 9

    invoke-static {}, Lcom/unity3d/splash/services/core/log/DeviceLog;->entered()V

    sget-boolean v0, Lcom/unity3d/splash/services/UnityServices;->_configurationInitialized:Z

    if-eqz v0, :cond_1d

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/ClientProperties;->getGameId()Ljava/lang/String;

    move-result-object p0

    if-eqz p0, :cond_1c

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/ClientProperties;->getGameId()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {p0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    if-nez p0, :cond_1c

    const-string p0, "You are trying to re-initialize with a different gameId"

    invoke-static {p0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->warning(Ljava/lang/String;)V

    :cond_1c
    return-void

    :cond_1d
    const/4 v0, 0x1

    sput-boolean v0, Lcom/unity3d/splash/services/UnityServices;->_configurationInitialized:Z

    invoke-static {}, Lcom/unity3d/splash/services/UnityServices;->isSupported()Z

    move-result v0

    if-nez v0, :cond_2c

    const-string p0, "Error while initializing Unity Services: device is not supported"

    invoke-static {p0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->error(Ljava/lang/String;)V

    return-void

    :cond_2c
    new-instance v0, Ljava/lang/StringBuilder;

    const-string v1, "Application start initializing at "

    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    new-instance v1, Ljava/util/Date;

    invoke-direct {v1}, Ljava/util/Date;-><init>()V

    invoke-virtual {v1}, Ljava/util/Date;->getTime()J

    move-result-wide v1

    invoke-virtual {v0, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->info(Ljava/lang/String;)V

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    invoke-static {v0, v1}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->setInitializationTime(J)V

    if-eqz p1, :cond_f9

    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v0

    if-nez v0, :cond_57

    goto/16 :goto_f9

    :cond_57
    if-nez p0, :cond_68

    const-string p0, "Error while initializing Unity Services: null activity, halting Unity Ads init"

    invoke-static {p0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->error(Ljava/lang/String;)V

    if-eqz p2, :cond_67

    sget-object p0, Lcom/unity3d/splash/services/UnityServices$UnityServicesError;->INVALID_ARGUMENT:Lcom/unity3d/splash/services/UnityServices$UnityServicesError;

    const-string p1, "Null activity"

    invoke-interface {p2, p0, p1}, Lcom/unity3d/splash/services/IUnityServicesListener;->onUnityServicesError(Lcom/unity3d/splash/services/UnityServices$UnityServicesError;Ljava/lang/String;)V

    :cond_67
    return-void

    :cond_68
    const-string v0, ") with game id "

    const-string v1, " ("

    const-string v2, "Initializing Unity Services "

    new-instance v3, Ljava/lang/StringBuilder;

    if-eqz p3, :cond_8f

    invoke-direct {v3, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->getVersionName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->getVersionCode()I

    move-result v1

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v0, " in test mode"

    goto :goto_ab

    :cond_8f
    invoke-direct {v3, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->getVersionName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->getVersionCode()I

    move-result v1

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v0, " in production mode"

    :goto_ab
    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->info(Ljava/lang/String;)V

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->getDebugMode()Z

    move-result v0

    invoke-static {v0}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->setDebugMode(Z)V

    invoke-static {p2}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->setListener(Lcom/unity3d/splash/services/IUnityServicesListener;)V

    invoke-static {p1}, Lcom/unity3d/splash/services/core/properties/ClientProperties;->setGameId(Ljava/lang/String;)V

    invoke-virtual {p0}, Landroid/app/Activity;->getApplicationContext()Landroid/content/Context;

    move-result-object p1

    invoke-static {p1}, Lcom/unity3d/splash/services/core/properties/ClientProperties;->setApplicationContext(Landroid/content/Context;)V

    invoke-virtual {p0}, Landroid/app/Activity;->getApplication()Landroid/app/Application;

    move-result-object p0

    invoke-static {p0}, Lcom/unity3d/splash/services/core/properties/ClientProperties;->setApplication(Landroid/app/Application;)V

    invoke-static {p4}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->setPerPlacementLoadEnabled(Z)V

    invoke-static {p3}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->setTestMode(Z)V

    invoke-static {}, Lcom/unity3d/splash/services/core/configuration/EnvironmentCheck;->isEnvironmentOk()Z

    move-result p0

    if-eqz p0, :cond_ea

    const-string p0, "Unity Services environment check OK"

    invoke-static {p0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->info(Ljava/lang/String;)V

    new-instance p0, Lcom/unity3d/splash/services/core/configuration/Configuration;

    invoke-direct {p0}, Lcom/unity3d/splash/services/core/configuration/Configuration;-><init>()V

    invoke-static {p0}, Lcom/unity3d/splash/services/core/configuration/InitializeThread;->initialize(Lcom/unity3d/splash/services/core/configuration/Configuration;)V

    return-void

    :cond_ea
    const-string p0, "Error during Unity Services environment check, halting Unity Services init"

    invoke-static {p0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->error(Ljava/lang/String;)V

    if-eqz p2, :cond_f8

    sget-object p0, Lcom/unity3d/splash/services/UnityServices$UnityServicesError;->INIT_SANITY_CHECK_FAIL:Lcom/unity3d/splash/services/UnityServices$UnityServicesError;

    const-string p1, "Unity Services init environment check failed"

    invoke-interface {p2, p0, p1}, Lcom/unity3d/splash/services/IUnityServicesListener;->onUnityServicesError(Lcom/unity3d/splash/services/UnityServices$UnityServicesError;Ljava/lang/String;)V

    :cond_f8
    return-void

    :cond_f9
    :goto_f9
    const-string p0, "Error while initializing Unity Services: empty game ID, halting Unity Ads init"

    invoke-static {p0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->error(Ljava/lang/String;)V

    if-eqz p2, :cond_107

    sget-object p0, Lcom/unity3d/splash/services/UnityServices$UnityServicesError;->INVALID_ARGUMENT:Lcom/unity3d/splash/services/UnityServices$UnityServicesError;

    const-string p1, "Empty game ID"

    invoke-interface {p2, p0, p1}, Lcom/unity3d/splash/services/IUnityServicesListener;->onUnityServicesError(Lcom/unity3d/splash/services/UnityServices$UnityServicesError;Ljava/lang/String;)V

    :cond_107
    return-void
.end method

.method public static isInitialized()Z
    .registers 1

    invoke-static {}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->isInitialized()Z

    move-result v0

    return v0
.end method

.method public static isSupported()Z
    .registers 2

    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x10

    if-lt v0, v1, :cond_8

    const/4 v0, 0x1

    return v0

    :cond_8
    const/4 v0, 0x0

    return v0
.end method

.method public static setDebugMode(Z)V
    .registers 1

    invoke-static {p0}, Lcom/unity3d/splash/services/core/properties/SdkProperties;->setDebugMode(Z)V

    return-void
.end method
