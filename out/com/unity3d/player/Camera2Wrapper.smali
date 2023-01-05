.class public Lcom/unity3d/player/Camera2Wrapper;
.super Ljava/lang/Object;

# interfaces
.implements Lcom/unity3d/player/e;


# instance fields
.field private a:Landroid/content/Context;

.field private b:Lcom/unity3d/player/c;

.field private final c:I


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .registers 3

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/unity3d/player/Camera2Wrapper;->b:Lcom/unity3d/player/c;

    const/16 v0, 0x64

    iput v0, p0, Lcom/unity3d/player/Camera2Wrapper;->c:I

    iput-object p1, p0, Lcom/unity3d/player/Camera2Wrapper;->a:Landroid/content/Context;

    invoke-direct {p0}, Lcom/unity3d/player/Camera2Wrapper;->initCamera2Jni()V

    return-void
.end method

.method private static a(F)I
    .registers 2

    const/high16 v0, 0x44fa0000

    mul-float p0, p0, v0

    const/high16 v0, -0x3b860000

    add-float/2addr p0, v0

    const/high16 v0, -0x3b9f0000

    invoke-static {p0, v0}, Ljava/lang/Math;->max(FF)F

    move-result p0

    const/high16 v0, 0x44610000

    invoke-static {p0, v0}, Ljava/lang/Math;->min(FF)F

    move-result p0

    float-to-int p0, p0

    return p0
.end method

.method private final native deinitCamera2Jni()V
.end method

.method private final native initCamera2Jni()V
.end method

.method private final native nativeFrameReady(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;III)V
.end method

.method private final native nativeSurfaceTextureReady(Ljava/lang/Object;)V
.end method


# virtual methods
.method public final a()V
    .registers 1

    invoke-direct {p0}, Lcom/unity3d/player/Camera2Wrapper;->deinitCamera2Jni()V

    invoke-virtual {p0}, Lcom/unity3d/player/Camera2Wrapper;->closeCamera2()V

    return-void
.end method

.method public final a(Ljava/lang/Object;)V
    .registers 2

    invoke-direct {p0, p1}, Lcom/unity3d/player/Camera2Wrapper;->nativeSurfaceTextureReady(Ljava/lang/Object;)V

    return-void
.end method

.method public final a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;III)V
    .registers 7

    invoke-direct/range {p0 .. p6}, Lcom/unity3d/player/Camera2Wrapper;->nativeFrameReady(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;III)V

    return-void
.end method

.method protected closeCamera2()V
    .registers 2

    iget-object v0, p0, Lcom/unity3d/player/Camera2Wrapper;->b:Lcom/unity3d/player/c;

    if-eqz v0, :cond_7

    invoke-virtual {v0}, Lcom/unity3d/player/c;->b()V

    :cond_7
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/unity3d/player/Camera2Wrapper;->b:Lcom/unity3d/player/c;

    return-void
.end method

.method protected getCamera2Count()I
    .registers 2

    iget-object v0, p0, Lcom/unity3d/player/Camera2Wrapper;->a:Landroid/content/Context;

    invoke-static {v0}, Lcom/unity3d/player/c;->a(Landroid/content/Context;)I

    move-result v0

    return v0
.end method

.method protected getCamera2FocalLengthEquivalent(I)I
    .registers 3

    iget-object v0, p0, Lcom/unity3d/player/Camera2Wrapper;->a:Landroid/content/Context;

    invoke-static {v0, p1}, Lcom/unity3d/player/c;->d(Landroid/content/Context;I)I

    move-result p1

    return p1
.end method

.method protected getCamera2Resolutions(I)[I
    .registers 3

    iget-object v0, p0, Lcom/unity3d/player/Camera2Wrapper;->a:Landroid/content/Context;

    invoke-static {v0, p1}, Lcom/unity3d/player/c;->e(Landroid/content/Context;I)[I

    move-result-object p1

    return-object p1
.end method

.method protected getCamera2SensorOrientation(I)I
    .registers 3

    iget-object v0, p0, Lcom/unity3d/player/Camera2Wrapper;->a:Landroid/content/Context;

    invoke-static {v0, p1}, Lcom/unity3d/player/c;->a(Landroid/content/Context;I)I

    move-result p1

    return p1
.end method

.method protected getCameraFocusArea(FF)Ljava/lang/Object;
    .registers 6

    invoke-static {p1}, Lcom/unity3d/player/Camera2Wrapper;->a(F)I

    move-result p1

    const/high16 v0, 0x3f800000

    sub-float/2addr v0, p2

    invoke-static {v0}, Lcom/unity3d/player/Camera2Wrapper;->a(F)I

    move-result p2

    new-instance v0, Landroid/graphics/Rect;

    add-int/lit8 v1, p1, -0x64

    add-int/lit8 v2, p2, -0x64

    add-int/lit8 p1, p1, 0x64

    add-int/lit8 p2, p2, 0x64

    invoke-direct {v0, v1, v2, p1, p2}, Landroid/graphics/Rect;-><init>(IIII)V

    new-instance p1, Landroid/hardware/Camera$Area;

    const/16 p2, 0x3e8

    invoke-direct {p1, v0, p2}, Landroid/hardware/Camera$Area;-><init>(Landroid/graphics/Rect;I)V

    return-object p1
.end method

.method protected getFrameSizeCamera2()Landroid/graphics/Rect;
    .registers 2

    iget-object v0, p0, Lcom/unity3d/player/Camera2Wrapper;->b:Lcom/unity3d/player/c;

    if-eqz v0, :cond_9

    invoke-virtual {v0}, Lcom/unity3d/player/c;->a()Landroid/graphics/Rect;

    move-result-object v0

    return-object v0

    :cond_9
    new-instance v0, Landroid/graphics/Rect;

    invoke-direct {v0}, Landroid/graphics/Rect;-><init>()V

    return-object v0
.end method

.method protected initializeCamera2(IIIII)Z
    .registers 14

    iget-object v0, p0, Lcom/unity3d/player/Camera2Wrapper;->b:Lcom/unity3d/player/c;

    if-nez v0, :cond_1b

    sget-object v0, Lcom/unity3d/player/UnityPlayer;->currentActivity:Landroid/app/Activity;

    if-eqz v0, :cond_1b

    new-instance v1, Lcom/unity3d/player/c;

    invoke-direct {v1, p0}, Lcom/unity3d/player/c;-><init>(Lcom/unity3d/player/e;)V

    iput-object v1, p0, Lcom/unity3d/player/Camera2Wrapper;->b:Lcom/unity3d/player/c;

    iget-object v2, p0, Lcom/unity3d/player/Camera2Wrapper;->a:Landroid/content/Context;

    move v3, p1

    move v4, p2

    move v5, p3

    move v6, p4

    move v7, p5

    invoke-virtual/range {v1 .. v7}, Lcom/unity3d/player/c;->a(Landroid/content/Context;IIIII)Z

    move-result p1

    return p1

    :cond_1b
    const/4 p1, 0x0

    return p1
.end method

.method protected isCamera2AutoFocusPointSupported(I)Z
    .registers 3

    iget-object v0, p0, Lcom/unity3d/player/Camera2Wrapper;->a:Landroid/content/Context;

    invoke-static {v0, p1}, Lcom/unity3d/player/c;->c(Landroid/content/Context;I)Z

    move-result p1

    return p1
.end method

.method protected isCamera2FrontFacing(I)Z
    .registers 3

    iget-object v0, p0, Lcom/unity3d/player/Camera2Wrapper;->a:Landroid/content/Context;

    invoke-static {v0, p1}, Lcom/unity3d/player/c;->b(Landroid/content/Context;I)Z

    move-result p1

    return p1
.end method

.method protected pauseCamera2()V
    .registers 2

    iget-object v0, p0, Lcom/unity3d/player/Camera2Wrapper;->b:Lcom/unity3d/player/c;

    if-eqz v0, :cond_7

    invoke-virtual {v0}, Lcom/unity3d/player/c;->d()V

    :cond_7
    return-void
.end method

.method protected setAutoFocusPoint(FF)Z
    .registers 4

    iget-object v0, p0, Lcom/unity3d/player/Camera2Wrapper;->b:Lcom/unity3d/player/c;

    if-eqz v0, :cond_9

    invoke-virtual {v0, p1, p2}, Lcom/unity3d/player/c;->a(FF)Z

    move-result p1

    return p1

    :cond_9
    const/4 p1, 0x0

    return p1
.end method

.method protected startCamera2()V
    .registers 2

    iget-object v0, p0, Lcom/unity3d/player/Camera2Wrapper;->b:Lcom/unity3d/player/c;

    if-eqz v0, :cond_7

    invoke-virtual {v0}, Lcom/unity3d/player/c;->c()V

    :cond_7
    return-void
.end method

.method protected stopCamera2()V
    .registers 2

    iget-object v0, p0, Lcom/unity3d/player/Camera2Wrapper;->b:Lcom/unity3d/player/c;

    if-eqz v0, :cond_7

    invoke-virtual {v0}, Lcom/unity3d/player/c;->e()V

    :cond_7
    return-void
.end method
