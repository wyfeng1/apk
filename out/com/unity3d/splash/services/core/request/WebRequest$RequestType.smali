.class public final enum Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;
.super Ljava/lang/Enum;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/unity3d/splash/services/core/request/WebRequest;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "RequestType"
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

.field public static final enum GET:Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

.field public static final enum HEAD:Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

.field public static final enum POST:Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;


# direct methods
.method static constructor <clinit>()V
    .registers 6

    new-instance v0, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

    const-string v1, "POST"

    const/4 v2, 0x0

    invoke-direct {v0, v1, v2}, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;->POST:Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

    new-instance v0, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

    const-string v1, "GET"

    const/4 v3, 0x1

    invoke-direct {v0, v1, v3}, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;->GET:Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

    new-instance v0, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

    const-string v1, "HEAD"

    const/4 v4, 0x2

    invoke-direct {v0, v1, v4}, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;->HEAD:Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

    const/4 v1, 0x3

    new-array v1, v1, [Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

    sget-object v5, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;->POST:Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

    aput-object v5, v1, v2

    sget-object v2, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;->GET:Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

    aput-object v2, v1, v3

    aput-object v0, v1, v4

    sput-object v1, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;->$VALUES:[Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .registers 3

    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;
    .registers 2

    const-class v0, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object p0

    check-cast p0, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

    return-object p0
.end method

.method public static values()[Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;
    .registers 1

    sget-object v0, Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;->$VALUES:[Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

    invoke-virtual {v0}, [Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;

    return-object v0
.end method
