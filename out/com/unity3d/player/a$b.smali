.class final Lcom/unity3d/player/a$b;
.super Ljava/lang/Object;

# interfaces
.implements Lcom/google/android/play/core/assetpacks/AssetPackStateUpdateListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/unity3d/player/a;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "b"
.end annotation


# instance fields
.field final synthetic a:Lcom/unity3d/player/a;

.field private b:Ljava/util/HashSet;

.field private c:Landroid/os/Looper;


# direct methods
.method public constructor <init>(Lcom/unity3d/player/a;Lcom/unity3d/player/IAssetPackManagerDownloadStatusCallback;)V
    .registers 4

    invoke-static {}, Landroid/os/Looper;->myLooper()Landroid/os/Looper;

    move-result-object v0

    invoke-direct {p0, p1, p2, v0}, Lcom/unity3d/player/a$b;-><init>(Lcom/unity3d/player/a;Lcom/unity3d/player/IAssetPackManagerDownloadStatusCallback;Landroid/os/Looper;)V

    return-void
.end method

.method public constructor <init>(Lcom/unity3d/player/a;Lcom/unity3d/player/IAssetPackManagerDownloadStatusCallback;Landroid/os/Looper;)V
    .registers 4

    iput-object p1, p0, Lcom/unity3d/player/a$b;->a:Lcom/unity3d/player/a;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance p1, Ljava/util/HashSet;

    invoke-direct {p1}, Ljava/util/HashSet;-><init>()V

    iput-object p1, p0, Lcom/unity3d/player/a$b;->b:Ljava/util/HashSet;

    invoke-virtual {p1, p2}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z

    iput-object p3, p0, Lcom/unity3d/player/a$b;->c:Landroid/os/Looper;

    return-void
.end method

.method private static a(Ljava/util/HashSet;)Ljava/util/Set;
    .registers 1

    invoke-virtual {p0}, Ljava/util/HashSet;->clone()Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/util/Set;

    return-object p0
.end method

.method private declared-synchronized a(Lcom/google/android/play/core/assetpacks/AssetPackState;)V
    .registers 14

    monitor-enter p0

    :try_start_1
    invoke-virtual {p1}, Lcom/google/android/play/core/assetpacks/AssetPackState;->status()I

    move-result v0

    const/4 v1, 0x4

    if-eq v0, v1, :cond_15

    invoke-virtual {p1}, Lcom/google/android/play/core/assetpacks/AssetPackState;->status()I

    move-result v0

    const/4 v1, 0x5

    if-eq v0, v1, :cond_15

    invoke-virtual {p1}, Lcom/google/android/play/core/assetpacks/AssetPackState;->status()I

    move-result v0

    if-nez v0, :cond_44

    :cond_15
    invoke-static {}, Lcom/unity3d/player/a;->a()Lcom/unity3d/player/a;

    move-result-object v0

    monitor-enter v0
    :try_end_1a
    .catchall {:try_start_1 .. :try_end_1a} :catchall_81

    :try_start_1a
    iget-object v1, p0, Lcom/unity3d/player/a$b;->a:Lcom/unity3d/player/a;

    invoke-static {v1}, Lcom/unity3d/player/a;->a(Lcom/unity3d/player/a;)Ljava/util/HashSet;

    move-result-object v1

    invoke-virtual {p1}, Lcom/google/android/play/core/assetpacks/AssetPackState;->name()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/util/HashSet;->remove(Ljava/lang/Object;)Z

    iget-object v1, p0, Lcom/unity3d/player/a$b;->a:Lcom/unity3d/player/a;

    invoke-static {v1}, Lcom/unity3d/player/a;->a(Lcom/unity3d/player/a;)Ljava/util/HashSet;

    move-result-object v1

    invoke-virtual {v1}, Ljava/util/HashSet;->isEmpty()Z

    move-result v1

    if-eqz v1, :cond_43

    iget-object v1, p0, Lcom/unity3d/player/a$b;->a:Lcom/unity3d/player/a;

    iget-object v2, p0, Lcom/unity3d/player/a$b;->a:Lcom/unity3d/player/a;

    invoke-static {v2}, Lcom/unity3d/player/a;->b(Lcom/unity3d/player/a;)Ljava/lang/Object;

    move-result-object v2

    invoke-virtual {v1, v2}, Lcom/unity3d/player/a;->a(Ljava/lang/Object;)V

    iget-object v1, p0, Lcom/unity3d/player/a$b;->a:Lcom/unity3d/player/a;

    invoke-static {v1}, Lcom/unity3d/player/a;->c(Lcom/unity3d/player/a;)Ljava/lang/Object;

    :cond_43
    monitor-exit v0
    :try_end_44
    .catchall {:try_start_1a .. :try_end_44} :catchall_7e

    :cond_44
    :try_start_44
    iget-object v0, p0, Lcom/unity3d/player/a$b;->b:Ljava/util/HashSet;

    invoke-virtual {v0}, Ljava/util/HashSet;->size()I

    move-result v0
    :try_end_4a
    .catchall {:try_start_44 .. :try_end_4a} :catchall_81

    if-nez v0, :cond_4e

    monitor-exit p0

    return-void

    :cond_4e
    :try_start_4e
    new-instance v0, Landroid/os/Handler;

    iget-object v1, p0, Lcom/unity3d/player/a$b;->c:Landroid/os/Looper;

    invoke-direct {v0, v1}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    new-instance v1, Lcom/unity3d/player/a$a;

    iget-object v2, p0, Lcom/unity3d/player/a$b;->b:Ljava/util/HashSet;

    invoke-static {v2}, Lcom/unity3d/player/a$b;->a(Ljava/util/HashSet;)Ljava/util/Set;

    move-result-object v3

    invoke-virtual {p1}, Lcom/google/android/play/core/assetpacks/AssetPackState;->name()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {p1}, Lcom/google/android/play/core/assetpacks/AssetPackState;->status()I

    move-result v5

    invoke-virtual {p1}, Lcom/google/android/play/core/assetpacks/AssetPackState;->totalBytesToDownload()J

    move-result-wide v6

    invoke-virtual {p1}, Lcom/google/android/play/core/assetpacks/AssetPackState;->bytesDownloaded()J

    move-result-wide v8

    invoke-virtual {p1}, Lcom/google/android/play/core/assetpacks/AssetPackState;->transferProgressPercentage()I

    move-result v10

    invoke-virtual {p1}, Lcom/google/android/play/core/assetpacks/AssetPackState;->errorCode()I

    move-result v11

    move-object v2, v1

    invoke-direct/range {v2 .. v11}, Lcom/unity3d/player/a$a;-><init>(Ljava/util/Set;Ljava/lang/String;IJJII)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z
    :try_end_7c
    .catchall {:try_start_4e .. :try_end_7c} :catchall_81

    monitor-exit p0

    return-void

    :catchall_7e
    move-exception p1

    :try_start_7f
    monitor-exit v0
    :try_end_80
    .catchall {:try_start_7f .. :try_end_80} :catchall_7e

    :try_start_80
    throw p1
    :try_end_81
    .catchall {:try_start_80 .. :try_end_81} :catchall_81

    :catchall_81
    move-exception p1

    monitor-exit p0

    throw p1
.end method


# virtual methods
.method public final declared-synchronized a(Lcom/unity3d/player/IAssetPackManagerDownloadStatusCallback;)V
    .registers 3

    monitor-enter p0

    :try_start_1
    iget-object v0, p0, Lcom/unity3d/player/a$b;->b:Ljava/util/HashSet;

    invoke-virtual {v0, p1}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z
    :try_end_6
    .catchall {:try_start_1 .. :try_end_6} :catchall_8

    monitor-exit p0

    return-void

    :catchall_8
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public final synthetic onStateUpdate(Ljava/lang/Object;)V
    .registers 2

    check-cast p1, Lcom/google/android/play/core/assetpacks/AssetPackState;

    invoke-direct {p0, p1}, Lcom/unity3d/player/a$b;->a(Lcom/google/android/play/core/assetpacks/AssetPackState;)V

    return-void
.end method
