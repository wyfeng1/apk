.class final Lcom/unity3d/player/UnityPlayer$18;
.super Ljava/lang/Object;

# interfaces
.implements Ljava/lang/Runnable;


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

    iput-object p1, p0, Lcom/unity3d/player/UnityPlayer$18;->a:Lcom/unity3d/player/UnityPlayer;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public final run()V
    .registers 3

    iget-object v0, p0, Lcom/unity3d/player/UnityPlayer$18;->a:Lcom/unity3d/player/UnityPlayer;

    # invokes: Lcom/unity3d/player/UnityPlayer;->nativeIsAutorotationOn()Z
    invoke-static {v0}, Lcom/unity3d/player/UnityPlayer;->access$3800(Lcom/unity3d/player/UnityPlayer;)Z

    move-result v0

    if-eqz v0, :cond_21

    iget-object v0, p0, Lcom/unity3d/player/UnityPlayer$18;->a:Lcom/unity3d/player/UnityPlayer;

    # getter for: Lcom/unity3d/player/UnityPlayer;->mActivity:Landroid/app/Activity;
    invoke-static {v0}, Lcom/unity3d/player/UnityPlayer;->access$3900(Lcom/unity3d/player/UnityPlayer;)Landroid/app/Activity;

    move-result-object v0

    if-eqz v0, :cond_21

    iget-object v0, p0, Lcom/unity3d/player/UnityPlayer$18;->a:Lcom/unity3d/player/UnityPlayer;

    # getter for: Lcom/unity3d/player/UnityPlayer;->mContext:Landroid/content/Context;
    invoke-static {v0}, Lcom/unity3d/player/UnityPlayer;->access$2700(Lcom/unity3d/player/UnityPlayer;)Landroid/content/Context;

    move-result-object v0

    check-cast v0, Landroid/app/Activity;

    iget-object v1, p0, Lcom/unity3d/player/UnityPlayer$18;->a:Lcom/unity3d/player/UnityPlayer;

    # getter for: Lcom/unity3d/player/UnityPlayer;->mInitialScreenOrientation:I
    invoke-static {v1}, Lcom/unity3d/player/UnityPlayer;->access$4000(Lcom/unity3d/player/UnityPlayer;)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/app/Activity;->setRequestedOrientation(I)V

    :cond_21
    return-void
.end method
