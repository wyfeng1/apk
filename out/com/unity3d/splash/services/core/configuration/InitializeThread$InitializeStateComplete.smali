.class public Lcom/unity3d/splash/services/core/configuration/InitializeThread$InitializeStateComplete;
.super Lcom/unity3d/splash/services/core/configuration/InitializeThread$InitializeState;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/unity3d/splash/services/core/configuration/InitializeThread;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "InitializeStateComplete"
.end annotation


# instance fields
.field private _configuration:Lcom/unity3d/splash/services/core/configuration/Configuration;


# direct methods
.method public constructor <init>(Lcom/unity3d/splash/services/core/configuration/Configuration;)V
    .registers 3

    const/4 v0, 0x0

    invoke-direct {p0, v0}, Lcom/unity3d/splash/services/core/configuration/InitializeThread$InitializeState;-><init>(Lcom/unity3d/splash/services/core/configuration/InitializeThread$1;)V

    iput-object p1, p0, Lcom/unity3d/splash/services/core/configuration/InitializeThread$InitializeStateComplete;->_configuration:Lcom/unity3d/splash/services/core/configuration/Configuration;

    return-void
.end method


# virtual methods
.method public execute()Lcom/unity3d/splash/services/core/configuration/InitializeThread$InitializeState;
    .registers 6

    iget-object v0, p0, Lcom/unity3d/splash/services/core/configuration/InitializeThread$InitializeStateComplete;->_configuration:Lcom/unity3d/splash/services/core/configuration/Configuration;

    invoke-virtual {v0}, Lcom/unity3d/splash/services/core/configuration/Configuration;->getModuleConfigurationList()[Ljava/lang/String;

    move-result-object v0

    array-length v1, v0

    const/4 v2, 0x0

    :goto_8
    if-ge v2, v1, :cond_1c

    aget-object v3, v0, v2

    iget-object v4, p0, Lcom/unity3d/splash/services/core/configuration/InitializeThread$InitializeStateComplete;->_configuration:Lcom/unity3d/splash/services/core/configuration/Configuration;

    invoke-virtual {v4, v3}, Lcom/unity3d/splash/services/core/configuration/Configuration;->getModuleConfiguration(Ljava/lang/String;)Lcom/unity3d/splash/services/core/configuration/IModuleConfiguration;

    move-result-object v3

    if-eqz v3, :cond_19

    iget-object v4, p0, Lcom/unity3d/splash/services/core/configuration/InitializeThread$InitializeStateComplete;->_configuration:Lcom/unity3d/splash/services/core/configuration/Configuration;

    invoke-interface {v3, v4}, Lcom/unity3d/splash/services/core/configuration/IModuleConfiguration;->initCompleteState(Lcom/unity3d/splash/services/core/configuration/Configuration;)Z

    :cond_19
    add-int/lit8 v2, v2, 0x1

    goto :goto_8

    :cond_1c
    const/4 v0, 0x0

    return-object v0
.end method
