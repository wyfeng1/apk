.class final Lcom/unity3d/splash/services/core/request/WebRequestThread$1;
.super Ljava/lang/Object;

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/unity3d/splash/services/core/request/WebRequestThread;->init()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = null
.end annotation


# direct methods
.method constructor <init>()V
    .registers 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public final run()V
    .registers 3

    const/4 v0, 0x1

    # setter for: Lcom/unity3d/splash/services/core/request/WebRequestThread;->_ready:Z
    invoke-static {v0}, Lcom/unity3d/splash/services/core/request/WebRequestThread;->access$002(Z)Z

    # getter for: Lcom/unity3d/splash/services/core/request/WebRequestThread;->_readyLock:Ljava/lang/Object;
    invoke-static {}, Lcom/unity3d/splash/services/core/request/WebRequestThread;->access$100()Ljava/lang/Object;

    move-result-object v0

    monitor-enter v0

    :try_start_9
    # getter for: Lcom/unity3d/splash/services/core/request/WebRequestThread;->_readyLock:Ljava/lang/Object;
    invoke-static {}, Lcom/unity3d/splash/services/core/request/WebRequestThread;->access$100()Ljava/lang/Object;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Object;->notify()V

    monitor-exit v0

    return-void

    :catchall_12
    move-exception v1

    monitor-exit v0
    :try_end_14
    .catchall {:try_start_9 .. :try_end_14} :catchall_12

    throw v1
.end method
