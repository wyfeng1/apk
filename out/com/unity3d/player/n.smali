.class final Lcom/unity3d/player/n;
.super Ljava/lang/Object;

# interfaces
.implements Ljava/lang/Thread$UncaughtExceptionHandler;


# instance fields
.field private volatile a:Ljava/lang/Thread$UncaughtExceptionHandler;


# direct methods
.method constructor <init>()V
    .registers 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method final declared-synchronized a()Z
    .registers 2

    monitor-enter p0

    :try_start_1
    invoke-static {}, Ljava/lang/Thread;->getDefaultUncaughtExceptionHandler()Ljava/lang/Thread$UncaughtExceptionHandler;

    move-result-object v0
    :try_end_5
    .catchall {:try_start_1 .. :try_end_5} :catchall_11

    if-ne v0, p0, :cond_a

    const/4 v0, 0x0

    :goto_8
    monitor-exit p0

    return v0

    :cond_a
    :try_start_a
    iput-object v0, p0, Lcom/unity3d/player/n;->a:Ljava/lang/Thread$UncaughtExceptionHandler;

    invoke-static {p0}, Ljava/lang/Thread;->setDefaultUncaughtExceptionHandler(Ljava/lang/Thread$UncaughtExceptionHandler;)V
    :try_end_f
    .catchall {:try_start_a .. :try_end_f} :catchall_11

    const/4 v0, 0x1

    goto :goto_8

    :catchall_11
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public final declared-synchronized uncaughtException(Ljava/lang/Thread;Ljava/lang/Throwable;)V
    .registers 10

    monitor-enter p0

    :try_start_1
    new-instance v0, Ljava/lang/Error;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "FATAL EXCEPTION [%s]\n"

    const/4 v3, 0x1

    new-array v4, v3, [Ljava/lang/Object;

    invoke-virtual {p1}, Ljava/lang/Thread;->getName()Ljava/lang/String;

    move-result-object v5

    const/4 v6, 0x0

    aput-object v5, v4, v6

    invoke-static {v2, v4}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, "Unity version     : %s\n"

    new-array v4, v3, [Ljava/lang/Object;

    const-string v5, "2021.3.11f1c2"

    aput-object v5, v4, v6

    invoke-static {v2, v4}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, "Device model      : %s %s\n"

    const/4 v4, 0x2

    new-array v4, v4, [Ljava/lang/Object;

    sget-object v5, Landroid/os/Build;->MANUFACTURER:Ljava/lang/String;

    aput-object v5, v4, v6

    sget-object v5, Landroid/os/Build;->MODEL:Ljava/lang/String;

    aput-object v5, v4, v3

    invoke-static {v2, v4}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, "Device fingerprint: %s\n"

    new-array v4, v3, [Ljava/lang/Object;

    sget-object v5, Landroid/os/Build;->FINGERPRINT:Ljava/lang/String;

    aput-object v5, v4, v6

    invoke-static {v2, v4}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, "Build Type        : %s\n"

    new-array v4, v3, [Ljava/lang/Object;

    const-string v5, "Release"

    aput-object v5, v4, v6

    invoke-static {v2, v4}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, "Scripting Backend : %s\n"

    new-array v4, v3, [Ljava/lang/Object;

    const-string v5, "IL2CPP"

    aput-object v5, v4, v6

    invoke-static {v2, v4}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, "ABI               : %s\n"

    new-array v4, v3, [Ljava/lang/Object;

    sget-object v5, Landroid/os/Build;->CPU_ABI:Ljava/lang/String;

    aput-object v5, v4, v6

    invoke-static {v2, v4}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, "Strip Engine Code : %s\n"

    new-array v4, v3, [Ljava/lang/Object;

    invoke-static {v3}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v3

    aput-object v3, v4, v6

    invoke-static {v2, v4}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/lang/Error;-><init>(Ljava/lang/String;)V

    new-array v1, v6, [Ljava/lang/StackTraceElement;

    invoke-virtual {v0, v1}, Ljava/lang/Error;->setStackTrace([Ljava/lang/StackTraceElement;)V

    invoke-virtual {v0, p2}, Ljava/lang/Error;->initCause(Ljava/lang/Throwable;)Ljava/lang/Throwable;

    iget-object v1, p0, Lcom/unity3d/player/n;->a:Ljava/lang/Thread$UncaughtExceptionHandler;

    invoke-interface {v1, p1, v0}, Ljava/lang/Thread$UncaughtExceptionHandler;->uncaughtException(Ljava/lang/Thread;Ljava/lang/Throwable;)V
    :try_end_9f
    .catchall {:try_start_1 .. :try_end_9f} :catchall_a1

    monitor-exit p0

    return-void

    :catchall_a1
    :try_start_a1
    iget-object v0, p0, Lcom/unity3d/player/n;->a:Ljava/lang/Thread$UncaughtExceptionHandler;

    invoke-interface {v0, p1, p2}, Ljava/lang/Thread$UncaughtExceptionHandler;->uncaughtException(Ljava/lang/Thread;Ljava/lang/Throwable;)V
    :try_end_a6
    .catchall {:try_start_a1 .. :try_end_a6} :catchall_a8

    monitor-exit p0

    return-void

    :catchall_a8
    move-exception p1

    monitor-exit p0

    throw p1
.end method