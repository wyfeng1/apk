.class public final enum Lcom/unity3d/splash/services/ads/video/VideoPlayerError;
.super Ljava/lang/Enum;


# static fields
.field private static final synthetic $VALUES:[Lcom/unity3d/splash/services/ads/video/VideoPlayerError;

.field public static final enum API_LEVEL_ERROR:Lcom/unity3d/splash/services/ads/video/VideoPlayerError;

.field public static final enum PREPARE:Lcom/unity3d/splash/services/ads/video/VideoPlayerError;

.field public static final enum VIDEOVIEW_NULL:Lcom/unity3d/splash/services/ads/video/VideoPlayerError;


# direct methods
.method static constructor <clinit>()V
    .registers 6

    new-instance v0, Lcom/unity3d/splash/services/ads/video/VideoPlayerError;

    const-string v1, "VIDEOVIEW_NULL"

    const/4 v2, 0x0

    invoke-direct {v0, v1, v2}, Lcom/unity3d/splash/services/ads/video/VideoPlayerError;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/unity3d/splash/services/ads/video/VideoPlayerError;->VIDEOVIEW_NULL:Lcom/unity3d/splash/services/ads/video/VideoPlayerError;

    new-instance v0, Lcom/unity3d/splash/services/ads/video/VideoPlayerError;

    const-string v1, "PREPARE"

    const/4 v3, 0x1

    invoke-direct {v0, v1, v3}, Lcom/unity3d/splash/services/ads/video/VideoPlayerError;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/unity3d/splash/services/ads/video/VideoPlayerError;->PREPARE:Lcom/unity3d/splash/services/ads/video/VideoPlayerError;

    new-instance v0, Lcom/unity3d/splash/services/ads/video/VideoPlayerError;

    const-string v1, "API_LEVEL_ERROR"

    const/4 v4, 0x2

    invoke-direct {v0, v1, v4}, Lcom/unity3d/splash/services/ads/video/VideoPlayerError;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/unity3d/splash/services/ads/video/VideoPlayerError;->API_LEVEL_ERROR:Lcom/unity3d/splash/services/ads/video/VideoPlayerError;

    const/4 v1, 0x3

    new-array v1, v1, [Lcom/unity3d/splash/services/ads/video/VideoPlayerError;

    sget-object v5, Lcom/unity3d/splash/services/ads/video/VideoPlayerError;->VIDEOVIEW_NULL:Lcom/unity3d/splash/services/ads/video/VideoPlayerError;

    aput-object v5, v1, v2

    sget-object v2, Lcom/unity3d/splash/services/ads/video/VideoPlayerError;->PREPARE:Lcom/unity3d/splash/services/ads/video/VideoPlayerError;

    aput-object v2, v1, v3

    aput-object v0, v1, v4

    sput-object v1, Lcom/unity3d/splash/services/ads/video/VideoPlayerError;->$VALUES:[Lcom/unity3d/splash/services/ads/video/VideoPlayerError;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .registers 3

    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/unity3d/splash/services/ads/video/VideoPlayerError;
    .registers 2

    const-class v0, Lcom/unity3d/splash/services/ads/video/VideoPlayerError;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object p0

    check-cast p0, Lcom/unity3d/splash/services/ads/video/VideoPlayerError;

    return-object p0
.end method

.method public static values()[Lcom/unity3d/splash/services/ads/video/VideoPlayerError;
    .registers 1

    sget-object v0, Lcom/unity3d/splash/services/ads/video/VideoPlayerError;->$VALUES:[Lcom/unity3d/splash/services/ads/video/VideoPlayerError;

    invoke-virtual {v0}, [Lcom/unity3d/splash/services/ads/video/VideoPlayerError;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/unity3d/splash/services/ads/video/VideoPlayerError;

    return-object v0
.end method
