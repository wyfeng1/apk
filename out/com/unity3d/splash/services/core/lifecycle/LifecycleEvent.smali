.class public final enum Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;
.super Ljava/lang/Enum;


# static fields
.field private static final synthetic $VALUES:[Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

.field public static final enum CREATED:Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

.field public static final enum DESTROYED:Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

.field public static final enum PAUSED:Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

.field public static final enum RESUMED:Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

.field public static final enum SAVE_INSTANCE_STATE:Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

.field public static final enum STARTED:Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

.field public static final enum STOPPED:Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;


# direct methods
.method static constructor <clinit>()V
    .registers 10

    new-instance v0, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    const-string v1, "CREATED"

    const/4 v2, 0x0

    invoke-direct {v0, v1, v2}, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;->CREATED:Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    new-instance v0, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    const-string v1, "STARTED"

    const/4 v3, 0x1

    invoke-direct {v0, v1, v3}, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;->STARTED:Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    new-instance v0, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    const-string v1, "RESUMED"

    const/4 v4, 0x2

    invoke-direct {v0, v1, v4}, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;->RESUMED:Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    new-instance v0, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    const-string v1, "PAUSED"

    const/4 v5, 0x3

    invoke-direct {v0, v1, v5}, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;->PAUSED:Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    new-instance v0, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    const-string v1, "STOPPED"

    const/4 v6, 0x4

    invoke-direct {v0, v1, v6}, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;->STOPPED:Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    new-instance v0, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    const-string v1, "SAVE_INSTANCE_STATE"

    const/4 v7, 0x5

    invoke-direct {v0, v1, v7}, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;->SAVE_INSTANCE_STATE:Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    new-instance v0, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    const-string v1, "DESTROYED"

    const/4 v8, 0x6

    invoke-direct {v0, v1, v8}, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;->DESTROYED:Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    const/4 v1, 0x7

    new-array v1, v1, [Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    sget-object v9, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;->CREATED:Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    aput-object v9, v1, v2

    sget-object v2, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;->STARTED:Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    aput-object v2, v1, v3

    sget-object v2, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;->RESUMED:Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    aput-object v2, v1, v4

    sget-object v2, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;->PAUSED:Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    aput-object v2, v1, v5

    sget-object v2, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;->STOPPED:Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    aput-object v2, v1, v6

    sget-object v2, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;->SAVE_INSTANCE_STATE:Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    aput-object v2, v1, v7

    aput-object v0, v1, v8

    sput-object v1, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;->$VALUES:[Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .registers 3

    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;
    .registers 2

    const-class v0, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object p0

    check-cast p0, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    return-object p0
.end method

.method public static values()[Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;
    .registers 1

    sget-object v0, Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;->$VALUES:[Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    invoke-virtual {v0}, [Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/unity3d/splash/services/core/lifecycle/LifecycleEvent;

    return-object v0
.end method
