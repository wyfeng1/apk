.class final Lcom/unity3d/player/UnityPlayer$28;
.super Ljava/lang/Object;

# interfaces
.implements Landroid/view/SurfaceHolder$Callback;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/unity3d/player/UnityPlayer;->CreateGlView()Landroid/view/SurfaceView;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:Lcom/unity3d/player/UnityPlayer;


# direct methods
.method constructor <init>(Lcom/unity3d/player/UnityPlayer;)V
    .registers 2

    iput-object p1, p0, Lcom/unity3d/player/UnityPlayer$28;->a:Lcom/unity3d/player/UnityPlayer;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public final surfaceChanged(Landroid/view/SurfaceHolder;III)V
    .registers 5

    iget-object p2, p0, Lcom/unity3d/player/UnityPlayer$28;->a:Lcom/unity3d/player/UnityPlayer;

    invoke-interface {p1}, Landroid/view/SurfaceHolder;->getSurface()Landroid/view/Surface;

    move-result-object p1

    const/4 p3, 0x0

    # invokes: Lcom/unity3d/player/UnityPlayer;->updateGLDisplay(ILandroid/view/Surface;)V
    invoke-static {p2, p3, p1}, Lcom/unity3d/player/UnityPlayer;->access$1500(Lcom/unity3d/player/UnityPlayer;ILandroid/view/Surface;)V

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$28;->a:Lcom/unity3d/player/UnityPlayer;

    # invokes: Lcom/unity3d/player/UnityPlayer;->sendSurfaceChangedEvent()V
    invoke-static {p1}, Lcom/unity3d/player/UnityPlayer;->access$1700(Lcom/unity3d/player/UnityPlayer;)V

    return-void
.end method

.method public final surfaceCreated(Landroid/view/SurfaceHolder;)V
    .registers 4

    iget-object v0, p0, Lcom/unity3d/player/UnityPlayer$28;->a:Lcom/unity3d/player/UnityPlayer;

    invoke-interface {p1}, Landroid/view/SurfaceHolder;->getSurface()Landroid/view/Surface;

    move-result-object p1

    const/4 v1, 0x0

    # invokes: Lcom/unity3d/player/UnityPlayer;->updateGLDisplay(ILandroid/view/Surface;)V
    invoke-static {v0, v1, p1}, Lcom/unity3d/player/UnityPlayer;->access$1500(Lcom/unity3d/player/UnityPlayer;ILandroid/view/Surface;)V

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$28;->a:Lcom/unity3d/player/UnityPlayer;

    # getter for: Lcom/unity3d/player/UnityPlayer;->m_PersistentUnitySurface:Lcom/unity3d/player/h;
    invoke-static {p1}, Lcom/unity3d/player/UnityPlayer;->access$1600(Lcom/unity3d/player/UnityPlayer;)Lcom/unity3d/player/h;

    move-result-object p1

    if-eqz p1, :cond_1d

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$28;->a:Lcom/unity3d/player/UnityPlayer;

    # getter for: Lcom/unity3d/player/UnityPlayer;->m_PersistentUnitySurface:Lcom/unity3d/player/h;
    invoke-static {p1}, Lcom/unity3d/player/UnityPlayer;->access$1600(Lcom/unity3d/player/UnityPlayer;)Lcom/unity3d/player/h;

    move-result-object p1

    iget-object v0, p0, Lcom/unity3d/player/UnityPlayer$28;->a:Lcom/unity3d/player/UnityPlayer;

    invoke-virtual {p1, v0}, Lcom/unity3d/player/h;->a(Landroid/view/ViewGroup;)V

    :cond_1d
    return-void
.end method

.method public final surfaceDestroyed(Landroid/view/SurfaceHolder;)V
    .registers 4

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$28;->a:Lcom/unity3d/player/UnityPlayer;

    # getter for: Lcom/unity3d/player/UnityPlayer;->m_PersistentUnitySurface:Lcom/unity3d/player/h;
    invoke-static {p1}, Lcom/unity3d/player/UnityPlayer;->access$1600(Lcom/unity3d/player/UnityPlayer;)Lcom/unity3d/player/h;

    move-result-object p1

    if-eqz p1, :cond_17

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$28;->a:Lcom/unity3d/player/UnityPlayer;

    # getter for: Lcom/unity3d/player/UnityPlayer;->m_PersistentUnitySurface:Lcom/unity3d/player/h;
    invoke-static {p1}, Lcom/unity3d/player/UnityPlayer;->access$1600(Lcom/unity3d/player/UnityPlayer;)Lcom/unity3d/player/h;

    move-result-object p1

    iget-object v0, p0, Lcom/unity3d/player/UnityPlayer$28;->a:Lcom/unity3d/player/UnityPlayer;

    # getter for: Lcom/unity3d/player/UnityPlayer;->mGlView:Landroid/view/SurfaceView;
    invoke-static {v0}, Lcom/unity3d/player/UnityPlayer;->access$1800(Lcom/unity3d/player/UnityPlayer;)Landroid/view/SurfaceView;

    move-result-object v0

    invoke-virtual {p1, v0}, Lcom/unity3d/player/h;->a(Landroid/view/SurfaceView;)V

    :cond_17
    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$28;->a:Lcom/unity3d/player/UnityPlayer;

    const/4 v0, 0x0

    const/4 v1, 0x0

    # invokes: Lcom/unity3d/player/UnityPlayer;->updateGLDisplay(ILandroid/view/Surface;)V
    invoke-static {p1, v0, v1}, Lcom/unity3d/player/UnityPlayer;->access$1500(Lcom/unity3d/player/UnityPlayer;ILandroid/view/Surface;)V

    return-void
.end method
