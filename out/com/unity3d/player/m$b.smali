.class final Lcom/unity3d/player/m$b;
.super Landroid/database/ContentObserver;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/unity3d/player/m;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "b"
.end annotation


# instance fields
.field final synthetic a:Lcom/unity3d/player/m;

.field private b:Lcom/unity3d/player/m$a;


# direct methods
.method public constructor <init>(Lcom/unity3d/player/m;Landroid/os/Handler;Lcom/unity3d/player/m$a;)V
    .registers 4

    iput-object p1, p0, Lcom/unity3d/player/m$b;->a:Lcom/unity3d/player/m;

    invoke-direct {p0, p2}, Landroid/database/ContentObserver;-><init>(Landroid/os/Handler;)V

    iput-object p3, p0, Lcom/unity3d/player/m$b;->b:Lcom/unity3d/player/m$a;

    return-void
.end method


# virtual methods
.method public final deliverSelfNotifications()Z
    .registers 2

    invoke-super {p0}, Landroid/database/ContentObserver;->deliverSelfNotifications()Z

    move-result v0

    return v0
.end method

.method public final onChange(Z)V
    .registers 2

    iget-object p1, p0, Lcom/unity3d/player/m$b;->b:Lcom/unity3d/player/m$a;

    if-eqz p1, :cond_7

    invoke-interface {p1}, Lcom/unity3d/player/m$a;->b()V

    :cond_7
    return-void
.end method
