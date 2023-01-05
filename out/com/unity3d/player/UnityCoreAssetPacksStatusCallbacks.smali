.class Lcom/unity3d/player/UnityCoreAssetPacksStatusCallbacks;
.super Ljava/lang/Object;

# interfaces
.implements Lcom/unity3d/player/IAssetPackManagerDownloadStatusCallback;
.implements Lcom/unity3d/player/IAssetPackManagerStatusQueryCallback;


# direct methods
.method public constructor <init>()V
    .registers 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method private final native nativeStatusQueryResult(Ljava/lang/String;II)V
.end method


# virtual methods
.method public onStatusResult(J[Ljava/lang/String;[I[I)V
    .registers 8

    const/4 p1, 0x0

    :goto_1
    array-length p2, p4

    if-ge p1, p2, :cond_10

    aget-object p2, p3, p1

    aget v0, p4, p1

    aget v1, p5, p1

    invoke-direct {p0, p2, v0, v1}, Lcom/unity3d/player/UnityCoreAssetPacksStatusCallbacks;->nativeStatusQueryResult(Ljava/lang/String;II)V

    add-int/lit8 p1, p1, 0x1

    goto :goto_1

    :cond_10
    return-void
.end method

.method public onStatusUpdate(Ljava/lang/String;IJJII)V
    .registers 9

    invoke-direct {p0, p1, p2, p8}, Lcom/unity3d/player/UnityCoreAssetPacksStatusCallbacks;->nativeStatusQueryResult(Ljava/lang/String;II)V

    return-void
.end method
