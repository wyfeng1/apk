.class final Lcom/unity3d/player/UnityPlayer$13;
.super Lcom/unity3d/player/UnityPlayer$h;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/unity3d/player/UnityPlayer;->reportSoftInputStr(Ljava/lang/String;IZ)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:Z

.field final synthetic b:Ljava/lang/String;

.field final synthetic c:I

.field final synthetic d:Lcom/unity3d/player/UnityPlayer;


# direct methods
.method constructor <init>(Lcom/unity3d/player/UnityPlayer;ZLjava/lang/String;I)V
    .registers 5

    iput-object p1, p0, Lcom/unity3d/player/UnityPlayer$13;->d:Lcom/unity3d/player/UnityPlayer;

    iput-boolean p2, p0, Lcom/unity3d/player/UnityPlayer$13;->a:Z

    iput-object p3, p0, Lcom/unity3d/player/UnityPlayer$13;->b:Ljava/lang/String;

    iput p4, p0, Lcom/unity3d/player/UnityPlayer$13;->c:I

    const/4 p2, 0x0

    invoke-direct {p0, p1, p2}, Lcom/unity3d/player/UnityPlayer$h;-><init>(Lcom/unity3d/player/UnityPlayer;B)V

    return-void
.end method


# virtual methods
.method public final a()V
    .registers 3

    iget-boolean v0, p0, Lcom/unity3d/player/UnityPlayer$13;->a:Z

    if-eqz v0, :cond_a

    iget-object v0, p0, Lcom/unity3d/player/UnityPlayer$13;->d:Lcom/unity3d/player/UnityPlayer;

    # invokes: Lcom/unity3d/player/UnityPlayer;->nativeSoftInputCanceled()V
    invoke-static {v0}, Lcom/unity3d/player/UnityPlayer;->access$3100(Lcom/unity3d/player/UnityPlayer;)V

    goto :goto_13

    :cond_a
    iget-object v0, p0, Lcom/unity3d/player/UnityPlayer$13;->b:Ljava/lang/String;

    if-eqz v0, :cond_13

    iget-object v1, p0, Lcom/unity3d/player/UnityPlayer$13;->d:Lcom/unity3d/player/UnityPlayer;

    # invokes: Lcom/unity3d/player/UnityPlayer;->nativeSetInputString(Ljava/lang/String;)V
    invoke-static {v1, v0}, Lcom/unity3d/player/UnityPlayer;->access$3200(Lcom/unity3d/player/UnityPlayer;Ljava/lang/String;)V

    :cond_13
    :goto_13
    iget v0, p0, Lcom/unity3d/player/UnityPlayer$13;->c:I

    const/4 v1, 0x1

    if-ne v0, v1, :cond_1d

    iget-object v0, p0, Lcom/unity3d/player/UnityPlayer$13;->d:Lcom/unity3d/player/UnityPlayer;

    # invokes: Lcom/unity3d/player/UnityPlayer;->nativeSoftInputClosed()V
    invoke-static {v0}, Lcom/unity3d/player/UnityPlayer;->access$3300(Lcom/unity3d/player/UnityPlayer;)V

    :cond_1d
    return-void
.end method
