.class final Lcom/unity3d/player/k$3;
.super Ljava/lang/Object;

# interfaces
.implements Landroid/view/View$OnTouchListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/unity3d/player/k;->a()V
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

    iput-object p1, p0, Lcom/unity3d/player/k$3;->a:Lcom/unity3d/player/k;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public final onTouch(Landroid/view/View;Landroid/view/MotionEvent;)Z
    .locals 0

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getAction()I

    move-result p1

    if-nez p1, :cond_0

    iget-object p1, p0, Lcom/unity3d/player/k$3;->a:Lcom/unity3d/player/k;

    iget-object p2, p1, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-virtual {p2}, Lcom/unity3d/player/j;->c()Ljava/lang/String;

    move-result-object p2

    invoke-static {p1, p2}, Lcom/unity3d/player/k;->a(Lcom/unity3d/player/k;Ljava/lang/String;)V

    :cond_0
    const/4 p1, 0x1

    return p1
.end method
