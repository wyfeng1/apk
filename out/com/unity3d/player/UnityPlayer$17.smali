.class final Lcom/unity3d/player/UnityPlayer$17;
.super Ljava/lang/Object;

# interfaces
.implements Lcom/unity3d/player/q$a;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/unity3d/player/UnityPlayer;->showVideoPlayer(Ljava/lang/String;IIIZII)Z
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:Lcom/unity3d/player/UnityPlayer;


# direct methods
.method constructor <init>(Lcom/unity3d/player/UnityPlayer;)V
    .registers 2

    iput-object p1, p0, Lcom/unity3d/player/UnityPlayer$17;->a:Lcom/unity3d/player/UnityPlayer;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public final a()V
    .registers 3

    iget-object v0, p0, Lcom/unity3d/player/UnityPlayer$17;->a:Lcom/unity3d/player/UnityPlayer;

    const/4 v1, 0x0

    # setter for: Lcom/unity3d/player/UnityPlayer;->mVideoPlayerProxy:Lcom/unity3d/player/q;
    invoke-static {v0, v1}, Lcom/unity3d/player/UnityPlayer;->access$3702(Lcom/unity3d/player/UnityPlayer;Lcom/unity3d/player/q;)Lcom/unity3d/player/q;

    return-void
.end method
