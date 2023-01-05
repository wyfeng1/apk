.class final Lcom/unity3d/player/q;
.super Ljava/lang/Object;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/unity3d/player/q$a;
    }
.end annotation


# instance fields
.field private a:Lcom/unity3d/player/UnityPlayer;

.field private b:Landroid/content/Context;

.field private c:Lcom/unity3d/player/q$a;

.field private final d:Ljava/util/concurrent/Semaphore;

.field private final e:Ljava/util/concurrent/locks/Lock;

.field private f:Lcom/unity3d/player/p;

.field private g:I

.field private h:Z

.field private i:Z


# direct methods
.method constructor <init>(Lcom/unity3d/player/UnityPlayer;)V
    .registers 5

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/unity3d/player/q;->a:Lcom/unity3d/player/UnityPlayer;

    iput-object v0, p0, Lcom/unity3d/player/q;->b:Landroid/content/Context;

    new-instance v1, Ljava/util/concurrent/Semaphore;

    const/4 v2, 0x0

    invoke-direct {v1, v2}, Ljava/util/concurrent/Semaphore;-><init>(I)V

    iput-object v1, p0, Lcom/unity3d/player/q;->d:Ljava/util/concurrent/Semaphore;

    new-instance v1, Ljava/util/concurrent/locks/ReentrantLock;

    invoke-direct {v1}, Ljava/util/concurrent/locks/ReentrantLock;-><init>()V

    iput-object v1, p0, Lcom/unity3d/player/q;->e:Ljava/util/concurrent/locks/Lock;

    iput-object v0, p0, Lcom/unity3d/player/q;->f:Lcom/unity3d/player/p;

    const/4 v0, 0x2

    iput v0, p0, Lcom/unity3d/player/q;->g:I

    iput-boolean v2, p0, Lcom/unity3d/player/q;->h:Z

    iput-boolean v2, p0, Lcom/unity3d/player/q;->i:Z

    iput-object p1, p0, Lcom/unity3d/player/q;->a:Lcom/unity3d/player/UnityPlayer;

    return-void
.end method

.method static synthetic a(Lcom/unity3d/player/q;I)I
    .registers 2

    iput p1, p0, Lcom/unity3d/player/q;->g:I

    return p1
.end method

.method static synthetic a(Lcom/unity3d/player/q;)Lcom/unity3d/player/p;
    .registers 1

    iget-object p0, p0, Lcom/unity3d/player/q;->f:Lcom/unity3d/player/p;

    return-object p0
.end method

.method static synthetic a(Lcom/unity3d/player/q;Lcom/unity3d/player/p;)Lcom/unity3d/player/p;
    .registers 2

    iput-object p1, p0, Lcom/unity3d/player/q;->f:Lcom/unity3d/player/p;

    return-object p1
.end method

.method static synthetic b(Lcom/unity3d/player/q;)Ljava/util/concurrent/Semaphore;
    .registers 1

    iget-object p0, p0, Lcom/unity3d/player/q;->d:Ljava/util/concurrent/Semaphore;

    return-object p0
.end method

.method static synthetic c(Lcom/unity3d/player/q;)Landroid/content/Context;
    .registers 1

    iget-object p0, p0, Lcom/unity3d/player/q;->b:Landroid/content/Context;

    return-object p0
.end method

.method static synthetic d(Lcom/unity3d/player/q;)Ljava/util/concurrent/locks/Lock;
    .registers 1

    iget-object p0, p0, Lcom/unity3d/player/q;->e:Ljava/util/concurrent/locks/Lock;

    return-object p0
.end method

.method private d()V
    .registers 3

    iget-object v0, p0, Lcom/unity3d/player/q;->f:Lcom/unity3d/player/p;

    if-eqz v0, :cond_1b

    iget-object v1, p0, Lcom/unity3d/player/q;->a:Lcom/unity3d/player/UnityPlayer;

    invoke-virtual {v1, v0}, Lcom/unity3d/player/UnityPlayer;->removeViewFromPlayer(Landroid/view/View;)V

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/unity3d/player/q;->i:Z

    iget-object v0, p0, Lcom/unity3d/player/q;->f:Lcom/unity3d/player/p;

    invoke-virtual {v0}, Lcom/unity3d/player/p;->destroyPlayer()V

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/unity3d/player/q;->f:Lcom/unity3d/player/p;

    iget-object v0, p0, Lcom/unity3d/player/q;->c:Lcom/unity3d/player/q$a;

    if-eqz v0, :cond_1b

    invoke-interface {v0}, Lcom/unity3d/player/q$a;->a()V

    :cond_1b
    return-void
.end method

.method static synthetic e(Lcom/unity3d/player/q;)Z
    .registers 1

    iget-boolean p0, p0, Lcom/unity3d/player/q;->i:Z

    return p0
.end method

.method static synthetic f(Lcom/unity3d/player/q;)V
    .registers 1

    invoke-direct {p0}, Lcom/unity3d/player/q;->d()V

    return-void
.end method

.method static synthetic g(Lcom/unity3d/player/q;)Lcom/unity3d/player/UnityPlayer;
    .registers 1

    iget-object p0, p0, Lcom/unity3d/player/q;->a:Lcom/unity3d/player/UnityPlayer;

    return-object p0
.end method

.method static synthetic h(Lcom/unity3d/player/q;)Z
    .registers 2

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/unity3d/player/q;->i:Z

    return v0
.end method


# virtual methods
.method public final a()V
    .registers 3

    iget-object v0, p0, Lcom/unity3d/player/q;->e:Ljava/util/concurrent/locks/Lock;

    invoke-interface {v0}, Ljava/util/concurrent/locks/Lock;->lock()V

    iget-object v0, p0, Lcom/unity3d/player/q;->f:Lcom/unity3d/player/p;

    if-eqz v0, :cond_22

    iget v1, p0, Lcom/unity3d/player/q;->g:I

    if-nez v1, :cond_11

    invoke-virtual {v0}, Lcom/unity3d/player/p;->CancelOnPrepare()V

    goto :goto_22

    :cond_11
    iget-boolean v1, p0, Lcom/unity3d/player/q;->i:Z

    if-eqz v1, :cond_22

    invoke-virtual {v0}, Lcom/unity3d/player/p;->a()Z

    move-result v0

    iput-boolean v0, p0, Lcom/unity3d/player/q;->h:Z

    if-nez v0, :cond_22

    iget-object v0, p0, Lcom/unity3d/player/q;->f:Lcom/unity3d/player/p;

    invoke-virtual {v0}, Lcom/unity3d/player/p;->pause()V

    :cond_22
    :goto_22
    iget-object v0, p0, Lcom/unity3d/player/q;->e:Ljava/util/concurrent/locks/Lock;

    invoke-interface {v0}, Ljava/util/concurrent/locks/Lock;->unlock()V

    return-void
.end method

.method public final a(Landroid/content/Context;Ljava/lang/String;IIIZJJLcom/unity3d/player/q$a;)Z
    .registers 26

    move-object v11, p0

    iget-object v0, v11, Lcom/unity3d/player/q;->e:Ljava/util/concurrent/locks/Lock;

    invoke-interface {v0}, Ljava/util/concurrent/locks/Lock;->lock()V

    move-object/from16 v0, p11

    iput-object v0, v11, Lcom/unity3d/player/q;->c:Lcom/unity3d/player/q$a;

    move-object v0, p1

    iput-object v0, v11, Lcom/unity3d/player/q;->b:Landroid/content/Context;

    iget-object v0, v11, Lcom/unity3d/player/q;->d:Ljava/util/concurrent/Semaphore;

    invoke-virtual {v0}, Ljava/util/concurrent/Semaphore;->drainPermits()I

    const/4 v12, 0x2

    iput v12, v11, Lcom/unity3d/player/q;->g:I

    new-instance v13, Lcom/unity3d/player/q$1;

    move-object v0, v13

    move-object v1, p0

    move-object/from16 v2, p2

    move/from16 v3, p3

    move/from16 v4, p4

    move/from16 v5, p5

    move/from16 v6, p6

    move-wide/from16 v7, p7

    move-wide/from16 v9, p9

    invoke-direct/range {v0 .. v10}, Lcom/unity3d/player/q$1;-><init>(Lcom/unity3d/player/q;Ljava/lang/String;IIIZJJ)V

    invoke-virtual {p0, v13}, Lcom/unity3d/player/q;->runOnUiThread(Ljava/lang/Runnable;)V

    const/4 v0, 0x0

    :try_start_2e
    iget-object v1, v11, Lcom/unity3d/player/q;->e:Ljava/util/concurrent/locks/Lock;

    invoke-interface {v1}, Ljava/util/concurrent/locks/Lock;->unlock()V

    iget-object v1, v11, Lcom/unity3d/player/q;->d:Ljava/util/concurrent/Semaphore;

    invoke-virtual {v1}, Ljava/util/concurrent/Semaphore;->acquire()V

    iget-object v1, v11, Lcom/unity3d/player/q;->e:Ljava/util/concurrent/locks/Lock;

    invoke-interface {v1}, Ljava/util/concurrent/locks/Lock;->lock()V

    iget v1, v11, Lcom/unity3d/player/q;->g:I
    :try_end_3f
    .catch Ljava/lang/InterruptedException; {:try_start_2e .. :try_end_3f} :catch_43

    if-eq v1, v12, :cond_44

    const/4 v0, 0x1

    goto :goto_44

    :catch_43
    nop

    :cond_44
    :goto_44
    new-instance v1, Lcom/unity3d/player/q$2;

    invoke-direct {v1, p0}, Lcom/unity3d/player/q$2;-><init>(Lcom/unity3d/player/q;)V

    invoke-virtual {p0, v1}, Lcom/unity3d/player/q;->runOnUiThread(Ljava/lang/Runnable;)V

    if-eqz v0, :cond_59

    iget v1, v11, Lcom/unity3d/player/q;->g:I

    const/4 v2, 0x3

    if-eq v1, v2, :cond_59

    new-instance v1, Lcom/unity3d/player/q$3;

    invoke-direct {v1, p0}, Lcom/unity3d/player/q$3;-><init>(Lcom/unity3d/player/q;)V

    goto :goto_5e

    :cond_59
    new-instance v1, Lcom/unity3d/player/q$4;

    invoke-direct {v1, p0}, Lcom/unity3d/player/q$4;-><init>(Lcom/unity3d/player/q;)V

    :goto_5e
    invoke-virtual {p0, v1}, Lcom/unity3d/player/q;->runOnUiThread(Ljava/lang/Runnable;)V

    iget-object v1, v11, Lcom/unity3d/player/q;->e:Ljava/util/concurrent/locks/Lock;

    invoke-interface {v1}, Ljava/util/concurrent/locks/Lock;->unlock()V

    return v0
.end method

.method public final b()V
    .registers 3

    iget-object v0, p0, Lcom/unity3d/player/q;->e:Ljava/util/concurrent/locks/Lock;

    invoke-interface {v0}, Ljava/util/concurrent/locks/Lock;->lock()V

    iget-object v0, p0, Lcom/unity3d/player/q;->f:Lcom/unity3d/player/p;

    if-eqz v0, :cond_14

    iget-boolean v1, p0, Lcom/unity3d/player/q;->i:Z

    if-eqz v1, :cond_14

    iget-boolean v1, p0, Lcom/unity3d/player/q;->h:Z

    if-nez v1, :cond_14

    invoke-virtual {v0}, Lcom/unity3d/player/p;->start()V

    :cond_14
    iget-object v0, p0, Lcom/unity3d/player/q;->e:Ljava/util/concurrent/locks/Lock;

    invoke-interface {v0}, Ljava/util/concurrent/locks/Lock;->unlock()V

    return-void
.end method

.method public final c()V
    .registers 2

    iget-object v0, p0, Lcom/unity3d/player/q;->e:Ljava/util/concurrent/locks/Lock;

    invoke-interface {v0}, Ljava/util/concurrent/locks/Lock;->lock()V

    iget-object v0, p0, Lcom/unity3d/player/q;->f:Lcom/unity3d/player/p;

    if-eqz v0, :cond_c

    invoke-virtual {v0}, Lcom/unity3d/player/p;->updateVideoLayout()V

    :cond_c
    iget-object v0, p0, Lcom/unity3d/player/q;->e:Ljava/util/concurrent/locks/Lock;

    invoke-interface {v0}, Ljava/util/concurrent/locks/Lock;->unlock()V

    return-void
.end method

.method protected final runOnUiThread(Ljava/lang/Runnable;)V
    .registers 4

    iget-object v0, p0, Lcom/unity3d/player/q;->b:Landroid/content/Context;

    instance-of v1, v0, Landroid/app/Activity;

    if-eqz v1, :cond_c

    check-cast v0, Landroid/app/Activity;

    invoke-virtual {v0, p1}, Landroid/app/Activity;->runOnUiThread(Ljava/lang/Runnable;)V

    return-void

    :cond_c
    const/4 p1, 0x5

    const-string v0, "Not running from an Activity; Ignoring execution request..."

    invoke-static {p1, v0}, Lcom/unity3d/player/f;->Log(ILjava/lang/String;)V

    return-void
.end method
