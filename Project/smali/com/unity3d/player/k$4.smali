.class final Lcom/unity3d/player/k$4;
.super Ljava/util/TimerTask;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/unity3d/player/k;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:Lcom/unity3d/player/k;


# direct methods
.method constructor <init>(Lcom/unity3d/player/k;)V
    .locals 0

    iput-object p1, p0, Lcom/unity3d/player/k$4;->a:Lcom/unity3d/player/k;

    invoke-direct {p0}, Ljava/util/TimerTask;-><init>()V

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 2

    iget-object v0, p0, Lcom/unity3d/player/k$4;->a:Lcom/unity3d/player/k;

    iget-object v0, v0, Lcom/unity3d/player/k;->a:Lcom/unity3d/player/UnityPlayer;

    new-instance v1, Lcom/unity3d/player/k$4$1;

    invoke-direct {v1, p0}, Lcom/unity3d/player/k$4$1;-><init>(Lcom/unity3d/player/k$4;)V

    invoke-virtual {v0, v1}, Lcom/unity3d/player/UnityPlayer;->runOnUiThread(Ljava/lang/Runnable;)V

    return-void
.end method
