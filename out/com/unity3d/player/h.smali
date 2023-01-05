.class final Lcom/unity3d/player/h;
.super Ljava/lang/Object;

# interfaces
.implements Landroid/app/Application$ActivityLifecycleCallbacks;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/unity3d/player/h$a;
    }
.end annotation


# instance fields
.field a:Ljava/lang/ref/WeakReference;

.field b:Landroid/app/Activity;

.field c:Lcom/unity3d/player/h$a;


# direct methods
.method constructor <init>(Landroid/content/Context;)V
    .registers 4

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Ljava/lang/ref/WeakReference;

    const/4 v1, 0x0

    invoke-direct {v0, v1}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object v0, p0, Lcom/unity3d/player/h;->a:Ljava/lang/ref/WeakReference;

    iput-object v1, p0, Lcom/unity3d/player/h;->c:Lcom/unity3d/player/h$a;

    instance-of v0, p1, Landroid/app/Activity;

    if-eqz v0, :cond_1c

    check-cast p1, Landroid/app/Activity;

    iput-object p1, p0, Lcom/unity3d/player/h;->b:Landroid/app/Activity;

    invoke-virtual {p1}, Landroid/app/Activity;->getApplication()Landroid/app/Application;

    move-result-object p1

    invoke-virtual {p1, p0}, Landroid/app/Application;->registerActivityLifecycleCallbacks(Landroid/app/Application$ActivityLifecycleCallbacks;)V

    :cond_1c
    return-void
.end method


# virtual methods
.method public final a()V
    .registers 2

    iget-object v0, p0, Lcom/unity3d/player/h;->b:Landroid/app/Activity;

    if-eqz v0, :cond_b

    invoke-virtual {v0}, Landroid/app/Activity;->getApplication()Landroid/app/Application;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/app/Application;->unregisterActivityLifecycleCallbacks(Landroid/app/Application$ActivityLifecycleCallbacks;)V

    :cond_b
    return-void
.end method

.method public final a(Landroid/view/SurfaceView;)V
    .registers 4

    sget-boolean v0, Lcom/unity3d/player/PlatformSupport;->NOUGAT_SUPPORT:Z

    if-eqz v0, :cond_14

    iget-object v0, p0, Lcom/unity3d/player/h;->c:Lcom/unity3d/player/h$a;

    if-nez v0, :cond_14

    new-instance v0, Lcom/unity3d/player/h$a;

    iget-object v1, p0, Lcom/unity3d/player/h;->b:Landroid/app/Activity;

    invoke-direct {v0, p0, v1}, Lcom/unity3d/player/h$a;-><init>(Lcom/unity3d/player/h;Landroid/content/Context;)V

    iput-object v0, p0, Lcom/unity3d/player/h;->c:Lcom/unity3d/player/h$a;

    invoke-virtual {v0, p1}, Lcom/unity3d/player/h$a;->a(Landroid/view/SurfaceView;)V

    :cond_14
    return-void
.end method

.method public final a(Landroid/view/ViewGroup;)V
    .registers 3

    iget-object v0, p0, Lcom/unity3d/player/h;->c:Lcom/unity3d/player/h$a;

    if-eqz v0, :cond_14

    invoke-virtual {v0}, Lcom/unity3d/player/h$a;->getParent()Landroid/view/ViewParent;

    move-result-object v0

    if-nez v0, :cond_14

    iget-object v0, p0, Lcom/unity3d/player/h;->c:Lcom/unity3d/player/h$a;

    invoke-virtual {p1, v0}, Landroid/view/ViewGroup;->addView(Landroid/view/View;)V

    iget-object v0, p0, Lcom/unity3d/player/h;->c:Lcom/unity3d/player/h$a;

    invoke-virtual {p1, v0}, Landroid/view/ViewGroup;->bringChildToFront(Landroid/view/View;)V

    :cond_14
    return-void
.end method

.method public final b(Landroid/view/ViewGroup;)V
    .registers 3

    iget-object v0, p0, Lcom/unity3d/player/h;->c:Lcom/unity3d/player/h$a;

    if-eqz v0, :cond_f

    invoke-virtual {v0}, Lcom/unity3d/player/h$a;->getParent()Landroid/view/ViewParent;

    move-result-object v0

    if-eqz v0, :cond_f

    iget-object v0, p0, Lcom/unity3d/player/h;->c:Lcom/unity3d/player/h$a;

    invoke-virtual {p1, v0}, Landroid/view/ViewGroup;->removeView(Landroid/view/View;)V

    :cond_f
    return-void
.end method

.method public final onActivityCreated(Landroid/app/Activity;Landroid/os/Bundle;)V
    .registers 3

    return-void
.end method

.method public final onActivityDestroyed(Landroid/app/Activity;)V
    .registers 2

    return-void
.end method

.method public final onActivityPaused(Landroid/app/Activity;)V
    .registers 2

    return-void
.end method

.method public final onActivityResumed(Landroid/app/Activity;)V
    .registers 3

    new-instance v0, Ljava/lang/ref/WeakReference;

    invoke-direct {v0, p1}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object v0, p0, Lcom/unity3d/player/h;->a:Ljava/lang/ref/WeakReference;

    return-void
.end method

.method public final onActivitySaveInstanceState(Landroid/app/Activity;Landroid/os/Bundle;)V
    .registers 3

    return-void
.end method

.method public final onActivityStarted(Landroid/app/Activity;)V
    .registers 2

    return-void
.end method

.method public final onActivityStopped(Landroid/app/Activity;)V
    .registers 2

    return-void
.end method
