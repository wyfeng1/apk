.class final Lcom/unity3d/player/UnityPlayer$g$1;
.super Ljava/lang/Object;

# interfaces
.implements Landroid/os/Handler$Callback;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/unity3d/player/UnityPlayer$g;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:Lcom/unity3d/player/UnityPlayer$g;


# direct methods
.method constructor <init>(Lcom/unity3d/player/UnityPlayer$g;)V
    .registers 2

    iput-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method private a()V
    .registers 3

    iget-object v0, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget v0, v0, Lcom/unity3d/player/UnityPlayer$g;->d:I

    sget v1, Lcom/unity3d/player/UnityPlayer$b;->c:I

    if-ne v0, v1, :cond_1c

    iget-object v0, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget-boolean v0, v0, Lcom/unity3d/player/UnityPlayer$g;->c:Z

    if-eqz v0, :cond_1c

    iget-object v0, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget-object v0, v0, Lcom/unity3d/player/UnityPlayer$g;->k:Lcom/unity3d/player/UnityPlayer;

    const/4 v1, 0x1

    # invokes: Lcom/unity3d/player/UnityPlayer;->nativeFocusChanged(Z)V
    invoke-static {v0, v1}, Lcom/unity3d/player/UnityPlayer;->access$000(Lcom/unity3d/player/UnityPlayer;Z)V

    iget-object v0, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    sget v1, Lcom/unity3d/player/UnityPlayer$b;->a:I

    iput v1, v0, Lcom/unity3d/player/UnityPlayer$g;->d:I

    :cond_1c
    return-void
.end method


# virtual methods
.method public final handleMessage(Landroid/os/Message;)Z
    .registers 6

    iget v0, p1, Landroid/os/Message;->what:I

    const/16 v1, 0x8dd

    const/4 v2, 0x0

    if-eq v0, v1, :cond_8

    return v2

    :cond_8
    iget-object p1, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast p1, Lcom/unity3d/player/UnityPlayer$f;

    sget-object v0, Lcom/unity3d/player/UnityPlayer$f;->h:Lcom/unity3d/player/UnityPlayer$f;

    const/4 v3, 0x1

    if-ne p1, v0, :cond_a5

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget v0, p1, Lcom/unity3d/player/UnityPlayer$g;->e:I

    sub-int/2addr v0, v3

    iput v0, p1, Lcom/unity3d/player/UnityPlayer$g;->e:I

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget-object p1, p1, Lcom/unity3d/player/UnityPlayer$g;->k:Lcom/unity3d/player/UnityPlayer;

    invoke-virtual {p1}, Lcom/unity3d/player/UnityPlayer;->executeGLThreadJobs()V

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget-boolean p1, p1, Lcom/unity3d/player/UnityPlayer$g;->b:Z

    if-nez p1, :cond_26

    return v3

    :cond_26
    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget-boolean p1, p1, Lcom/unity3d/player/UnityPlayer$g;->c:Z

    if-nez p1, :cond_2d

    return v3

    :cond_2d
    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget p1, p1, Lcom/unity3d/player/UnityPlayer$g;->i:I

    if-ltz p1, :cond_51

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget p1, p1, Lcom/unity3d/player/UnityPlayer$g;->i:I

    if-nez p1, :cond_4a

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget-object p1, p1, Lcom/unity3d/player/UnityPlayer$g;->k:Lcom/unity3d/player/UnityPlayer;

    # invokes: Lcom/unity3d/player/UnityPlayer;->getSplashEnabled()Z
    invoke-static {p1}, Lcom/unity3d/player/UnityPlayer;->access$100(Lcom/unity3d/player/UnityPlayer;)Z

    move-result p1

    if-eqz p1, :cond_4a

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget-object p1, p1, Lcom/unity3d/player/UnityPlayer$g;->k:Lcom/unity3d/player/UnityPlayer;

    # invokes: Lcom/unity3d/player/UnityPlayer;->DisableStaticSplashScreen()V
    invoke-static {p1}, Lcom/unity3d/player/UnityPlayer;->access$200(Lcom/unity3d/player/UnityPlayer;)V

    :cond_4a
    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget v0, p1, Lcom/unity3d/player/UnityPlayer$g;->i:I

    sub-int/2addr v0, v3

    iput v0, p1, Lcom/unity3d/player/UnityPlayer$g;->i:I

    :cond_51
    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget p1, p1, Lcom/unity3d/player/UnityPlayer$g;->i:I

    if-nez p1, :cond_68

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget-object p1, p1, Lcom/unity3d/player/UnityPlayer$g;->k:Lcom/unity3d/player/UnityPlayer;

    # getter for: Lcom/unity3d/player/UnityPlayer;->shouldShowLaunchScreenAds:Z
    invoke-static {p1}, Lcom/unity3d/player/UnityPlayer;->access$300(Lcom/unity3d/player/UnityPlayer;)Z

    move-result p1

    if-eqz p1, :cond_68

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget-object p1, p1, Lcom/unity3d/player/UnityPlayer$g;->k:Lcom/unity3d/player/UnityPlayer;

    # invokes: Lcom/unity3d/player/UnityPlayer;->ShowSplashAdsScreen()V
    invoke-static {p1}, Lcom/unity3d/player/UnityPlayer;->access$400(Lcom/unity3d/player/UnityPlayer;)V

    :cond_68
    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget-boolean p1, p1, Lcom/unity3d/player/UnityPlayer$g;->h:Z

    if-eqz p1, :cond_88

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget p1, p1, Lcom/unity3d/player/UnityPlayer$g;->j:I

    if-ltz p1, :cond_88

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget p1, p1, Lcom/unity3d/player/UnityPlayer$g;->j:I

    if-nez p1, :cond_81

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget-object p1, p1, Lcom/unity3d/player/UnityPlayer$g;->k:Lcom/unity3d/player/UnityPlayer;

    # invokes: Lcom/unity3d/player/UnityPlayer;->DisableSplashAdsScreen()V
    invoke-static {p1}, Lcom/unity3d/player/UnityPlayer;->access$500(Lcom/unity3d/player/UnityPlayer;)V

    :cond_81
    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget v0, p1, Lcom/unity3d/player/UnityPlayer$g;->j:I

    sub-int/2addr v0, v3

    iput v0, p1, Lcom/unity3d/player/UnityPlayer$g;->j:I

    :cond_88
    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget-object p1, p1, Lcom/unity3d/player/UnityPlayer$g;->k:Lcom/unity3d/player/UnityPlayer;

    invoke-virtual {p1}, Lcom/unity3d/player/UnityPlayer;->isFinishing()Z

    move-result p1

    if-nez p1, :cond_130

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget-object p1, p1, Lcom/unity3d/player/UnityPlayer$g;->k:Lcom/unity3d/player/UnityPlayer;

    # invokes: Lcom/unity3d/player/UnityPlayer;->nativeRender()Z
    invoke-static {p1}, Lcom/unity3d/player/UnityPlayer;->access$600(Lcom/unity3d/player/UnityPlayer;)Z

    move-result p1

    if-nez p1, :cond_130

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget-object p1, p1, Lcom/unity3d/player/UnityPlayer$g;->k:Lcom/unity3d/player/UnityPlayer;

    # invokes: Lcom/unity3d/player/UnityPlayer;->finish()V
    invoke-static {p1}, Lcom/unity3d/player/UnityPlayer;->access$700(Lcom/unity3d/player/UnityPlayer;)V

    goto/16 :goto_130

    :cond_a5
    sget-object v0, Lcom/unity3d/player/UnityPlayer$f;->c:Lcom/unity3d/player/UnityPlayer$f;

    if-ne p1, v0, :cond_b2

    invoke-static {}, Landroid/os/Looper;->myLooper()Landroid/os/Looper;

    move-result-object p1

    invoke-virtual {p1}, Landroid/os/Looper;->quit()V

    goto/16 :goto_130

    :cond_b2
    sget-object v0, Lcom/unity3d/player/UnityPlayer$f;->b:Lcom/unity3d/player/UnityPlayer$f;

    if-ne p1, v0, :cond_bc

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iput-boolean v3, p1, Lcom/unity3d/player/UnityPlayer$g;->b:Z

    goto/16 :goto_130

    :cond_bc
    sget-object v0, Lcom/unity3d/player/UnityPlayer$f;->a:Lcom/unity3d/player/UnityPlayer$f;

    if-ne p1, v0, :cond_c6

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iput-boolean v2, p1, Lcom/unity3d/player/UnityPlayer$g;->b:Z

    goto/16 :goto_130

    :cond_c6
    sget-object v0, Lcom/unity3d/player/UnityPlayer$f;->d:Lcom/unity3d/player/UnityPlayer$f;

    if-ne p1, v0, :cond_cf

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iput-boolean v2, p1, Lcom/unity3d/player/UnityPlayer$g;->c:Z

    goto :goto_130

    :cond_cf
    sget-object v0, Lcom/unity3d/player/UnityPlayer$f;->e:Lcom/unity3d/player/UnityPlayer$f;

    if-ne p1, v0, :cond_db

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iput-boolean v3, p1, Lcom/unity3d/player/UnityPlayer$g;->c:Z

    :goto_d7
    invoke-direct {p0}, Lcom/unity3d/player/UnityPlayer$g$1;->a()V

    goto :goto_130

    :cond_db
    sget-object v0, Lcom/unity3d/player/UnityPlayer$f;->f:Lcom/unity3d/player/UnityPlayer$f;

    if-ne p1, v0, :cond_f5

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget p1, p1, Lcom/unity3d/player/UnityPlayer$g;->d:I

    sget v0, Lcom/unity3d/player/UnityPlayer$b;->a:I

    if-ne p1, v0, :cond_ee

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget-object p1, p1, Lcom/unity3d/player/UnityPlayer$g;->k:Lcom/unity3d/player/UnityPlayer;

    # invokes: Lcom/unity3d/player/UnityPlayer;->nativeFocusChanged(Z)V
    invoke-static {p1, v2}, Lcom/unity3d/player/UnityPlayer;->access$000(Lcom/unity3d/player/UnityPlayer;Z)V

    :cond_ee
    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    sget v0, Lcom/unity3d/player/UnityPlayer$b;->b:I

    iput v0, p1, Lcom/unity3d/player/UnityPlayer$g;->d:I

    goto :goto_130

    :cond_f5
    sget-object v0, Lcom/unity3d/player/UnityPlayer$f;->g:Lcom/unity3d/player/UnityPlayer$f;

    if-ne p1, v0, :cond_100

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    sget v0, Lcom/unity3d/player/UnityPlayer$b;->c:I

    iput v0, p1, Lcom/unity3d/player/UnityPlayer$g;->d:I

    goto :goto_d7

    :cond_100
    sget-object v0, Lcom/unity3d/player/UnityPlayer$f;->i:Lcom/unity3d/player/UnityPlayer$f;

    if-ne p1, v0, :cond_114

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget-object p1, p1, Lcom/unity3d/player/UnityPlayer$g;->k:Lcom/unity3d/player/UnityPlayer;

    iget-object v0, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget-object v0, v0, Lcom/unity3d/player/UnityPlayer$g;->k:Lcom/unity3d/player/UnityPlayer;

    invoke-virtual {v0}, Lcom/unity3d/player/UnityPlayer;->getLaunchURL()Ljava/lang/String;

    move-result-object v0

    # invokes: Lcom/unity3d/player/UnityPlayer;->nativeSetLaunchURL(Ljava/lang/String;)V
    invoke-static {p1, v0}, Lcom/unity3d/player/UnityPlayer;->access$800(Lcom/unity3d/player/UnityPlayer;Ljava/lang/String;)V

    goto :goto_130

    :cond_114
    sget-object v0, Lcom/unity3d/player/UnityPlayer$f;->j:Lcom/unity3d/player/UnityPlayer$f;

    if-ne p1, v0, :cond_128

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget-object p1, p1, Lcom/unity3d/player/UnityPlayer$g;->k:Lcom/unity3d/player/UnityPlayer;

    iget-object v0, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget v0, v0, Lcom/unity3d/player/UnityPlayer$g;->f:I

    iget-object v2, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget v2, v2, Lcom/unity3d/player/UnityPlayer$g;->g:I

    # invokes: Lcom/unity3d/player/UnityPlayer;->nativeOrientationChanged(II)V
    invoke-static {p1, v0, v2}, Lcom/unity3d/player/UnityPlayer;->access$900(Lcom/unity3d/player/UnityPlayer;II)V

    goto :goto_130

    :cond_128
    sget-object v0, Lcom/unity3d/player/UnityPlayer$f;->k:Lcom/unity3d/player/UnityPlayer$f;

    if-ne p1, v0, :cond_130

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iput-boolean v3, p1, Lcom/unity3d/player/UnityPlayer$g;->h:Z

    :cond_130
    :goto_130
    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget-boolean p1, p1, Lcom/unity3d/player/UnityPlayer$g;->b:Z

    if-eqz p1, :cond_150

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget p1, p1, Lcom/unity3d/player/UnityPlayer$g;->e:I

    if-gtz p1, :cond_150

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget-object p1, p1, Lcom/unity3d/player/UnityPlayer$g;->a:Landroid/os/Handler;

    sget-object v0, Lcom/unity3d/player/UnityPlayer$f;->h:Lcom/unity3d/player/UnityPlayer$f;

    invoke-static {p1, v1, v0}, Landroid/os/Message;->obtain(Landroid/os/Handler;ILjava/lang/Object;)Landroid/os/Message;

    move-result-object p1

    invoke-virtual {p1}, Landroid/os/Message;->sendToTarget()V

    iget-object p1, p0, Lcom/unity3d/player/UnityPlayer$g$1;->a:Lcom/unity3d/player/UnityPlayer$g;

    iget v0, p1, Lcom/unity3d/player/UnityPlayer$g;->e:I

    add-int/2addr v0, v3

    iput v0, p1, Lcom/unity3d/player/UnityPlayer$g;->e:I

    :cond_150
    return v3
.end method
