.class public Lcom/unity3d/player/UnityPermissions$ModalWaitForPermissionResponse;
.super Ljava/lang/Object;

# interfaces
.implements Lcom/unity3d/player/IPermissionRequestCallbacks;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/unity3d/player/UnityPermissions;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "ModalWaitForPermissionResponse"
.end annotation


# instance fields
.field private haveResponse:Z


# direct methods
.method public constructor <init>()V
    .registers 2

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/unity3d/player/UnityPermissions$ModalWaitForPermissionResponse;->haveResponse:Z

    return-void
.end method


# virtual methods
.method public declared-synchronized onPermissionDenied(Ljava/lang/String;)V
    .registers 2

    monitor-enter p0

    const/4 p1, 0x1

    :try_start_2
    iput-boolean p1, p0, Lcom/unity3d/player/UnityPermissions$ModalWaitForPermissionResponse;->haveResponse:Z

    invoke-virtual {p0}, Ljava/lang/Object;->notify()V
    :try_end_7
    .catchall {:try_start_2 .. :try_end_7} :catchall_9

    monitor-exit p0

    return-void

    :catchall_9
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public declared-synchronized onPermissionDeniedAndDontAskAgain(Ljava/lang/String;)V
    .registers 2

    monitor-enter p0

    const/4 p1, 0x1

    :try_start_2
    iput-boolean p1, p0, Lcom/unity3d/player/UnityPermissions$ModalWaitForPermissionResponse;->haveResponse:Z

    invoke-virtual {p0}, Ljava/lang/Object;->notify()V
    :try_end_7
    .catchall {:try_start_2 .. :try_end_7} :catchall_9

    monitor-exit p0

    return-void

    :catchall_9
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public declared-synchronized onPermissionGranted(Ljava/lang/String;)V
    .registers 2

    monitor-enter p0

    const/4 p1, 0x1

    :try_start_2
    iput-boolean p1, p0, Lcom/unity3d/player/UnityPermissions$ModalWaitForPermissionResponse;->haveResponse:Z

    invoke-virtual {p0}, Ljava/lang/Object;->notify()V
    :try_end_7
    .catchall {:try_start_2 .. :try_end_7} :catchall_9

    monitor-exit p0

    return-void

    :catchall_9
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public declared-synchronized waitForResponse()V
    .registers 2

    monitor-enter p0

    :try_start_1
    iget-boolean v0, p0, Lcom/unity3d/player/UnityPermissions$ModalWaitForPermissionResponse;->haveResponse:Z
    :try_end_3
    .catch Ljava/lang/InterruptedException; {:try_start_1 .. :try_end_3} :catch_f
    .catchall {:try_start_1 .. :try_end_3} :catchall_c

    if-eqz v0, :cond_7

    monitor-exit p0

    return-void

    :cond_7
    :try_start_7
    invoke-virtual {p0}, Ljava/lang/Object;->wait()V
    :try_end_a
    .catch Ljava/lang/InterruptedException; {:try_start_7 .. :try_end_a} :catch_f
    .catchall {:try_start_7 .. :try_end_a} :catchall_c

    monitor-exit p0

    return-void

    :catchall_c
    move-exception v0

    monitor-exit p0

    throw v0

    :catch_f
    monitor-exit p0

    return-void
.end method
