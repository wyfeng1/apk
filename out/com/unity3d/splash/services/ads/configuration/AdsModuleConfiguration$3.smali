.class Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration$3;
.super Ljava/lang/Object;

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration;->initErrorState(Lcom/unity3d/splash/services/core/configuration/Configuration;Ljava/lang/String;Ljava/lang/String;)Z
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration;

.field final synthetic val$message:Ljava/lang/String;


# direct methods
.method constructor <init>(Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration;Ljava/lang/String;)V
    .registers 3

    iput-object p1, p0, Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration$3;->this$0:Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration;

    iput-object p2, p0, Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration$3;->val$message:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .registers 5

    invoke-static {}, Lcom/unity3d/splash/services/ads/properties/AdsProperties;->getListeners()Ljava/util/Set;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_8
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_1c

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/unity3d/splash/IUnityAdsListener;

    sget-object v2, Lcom/unity3d/splash/UnityAds$UnityAdsError;->INITIALIZE_FAILED:Lcom/unity3d/splash/UnityAds$UnityAdsError;

    iget-object v3, p0, Lcom/unity3d/splash/services/ads/configuration/AdsModuleConfiguration$3;->val$message:Ljava/lang/String;

    invoke-interface {v1, v2, v3}, Lcom/unity3d/splash/IUnityAdsListener;->onUnityAdsError(Lcom/unity3d/splash/UnityAds$UnityAdsError;Ljava/lang/String;)V

    goto :goto_8

    :cond_1c
    return-void
.end method
