.class Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration$1;
.super Ljava/lang/Thread;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration;->initModuleState(Lcom/unity3d/splash/services/core/configuration/Configuration;)Z
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration;

.field final synthetic val$configHost:Ljava/lang/String;

.field final synthetic val$cv:Landroid/os/ConditionVariable;


# direct methods
.method constructor <init>(Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration;Ljava/lang/String;Landroid/os/ConditionVariable;)V
    .registers 4

    iput-object p1, p0, Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration$1;->this$0:Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration;

    iput-object p2, p0, Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration$1;->val$configHost:Ljava/lang/String;

    iput-object p3, p0, Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration$1;->val$cv:Landroid/os/ConditionVariable;

    invoke-direct {p0}, Ljava/lang/Thread;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .registers 4

    :try_start_0
    iget-object v0, p0, Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration$1;->this$0:Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration;

    iget-object v1, p0, Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration$1;->val$configHost:Ljava/lang/String;

    invoke-static {v1}, Ljava/net/InetAddress;->getByName(Ljava/lang/String;)Ljava/net/InetAddress;

    move-result-object v1

    # setter for: Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration;->_address:Ljava/net/InetAddress;
    invoke-static {v0, v1}, Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration;->access$002(Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration;Ljava/net/InetAddress;)Ljava/net/InetAddress;

    iget-object v0, p0, Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration$1;->val$cv:Landroid/os/ConditionVariable;

    invoke-virtual {v0}, Landroid/os/ConditionVariable;->open()V
    :try_end_10
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_10} :catch_11

    return-void

    :catch_11
    move-exception v0

    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "Couldn\'t get address. Host: "

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    iget-object v2, p0, Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration$1;->val$configHost:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1, v0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    iget-object v0, p0, Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration$1;->val$cv:Landroid/os/ConditionVariable;

    invoke-virtual {v0}, Landroid/os/ConditionVariable;->open()V

    return-void
.end method
