.class final Lcom/unity3d/player/k$4$1;
.super Ljava/lang/Object;

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/unity3d/player/k$4;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:Lcom/unity3d/player/k$4;


# direct methods
.method constructor <init>(Lcom/unity3d/player/k$4;)V
    .registers 2

    iput-object p1, p0, Lcom/unity3d/player/k$4$1;->a:Lcom/unity3d/player/k$4;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public final run()V
    .registers 4

    iget-object v0, p0, Lcom/unity3d/player/k$4$1;->a:Lcom/unity3d/player/k$4;

    iget-object v0, v0, Lcom/unity3d/player/k$4;->a:Lcom/unity3d/player/k;

    iget-boolean v0, v0, Lcom/unity3d/player/k;->l:Z

    if-nez v0, :cond_9

    return-void

    :cond_9
    iget-object v0, p0, Lcom/unity3d/player/k$4$1;->a:Lcom/unity3d/player/k$4;

    iget-object v0, v0, Lcom/unity3d/player/k$4;->a:Lcom/unity3d/player/k;

    iget v1, v0, Lcom/unity3d/player/k;->k:I

    add-int/lit8 v1, v1, -0x1

    iput v1, v0, Lcom/unity3d/player/k;->k:I

    iget-object v0, p0, Lcom/unity3d/player/k$4$1;->a:Lcom/unity3d/player/k$4;

    iget-object v0, v0, Lcom/unity3d/player/k$4;->a:Lcom/unity3d/player/k;

    iget-object v0, v0, Lcom/unity3d/player/k;->h:Landroid/widget/TextView;

    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "Skip "

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    iget-object v2, p0, Lcom/unity3d/player/k$4$1;->a:Lcom/unity3d/player/k$4;

    iget-object v2, v2, Lcom/unity3d/player/k$4;->a:Lcom/unity3d/player/k;

    iget v2, v2, Lcom/unity3d/player/k;->k:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object v0, p0, Lcom/unity3d/player/k$4$1;->a:Lcom/unity3d/player/k$4;

    iget-object v0, v0, Lcom/unity3d/player/k$4;->a:Lcom/unity3d/player/k;

    iget v0, v0, Lcom/unity3d/player/k;->k:I

    if-gtz v0, :cond_3f

    iget-object v0, p0, Lcom/unity3d/player/k$4$1;->a:Lcom/unity3d/player/k$4;

    iget-object v0, v0, Lcom/unity3d/player/k$4;->a:Lcom/unity3d/player/k;

    invoke-static {v0}, Lcom/unity3d/player/k;->b(Lcom/unity3d/player/k;)V

    :cond_3f
    return-void
.end method
