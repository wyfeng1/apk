.class final Lcom/unity3d/player/o;
.super Ljava/lang/Object;


# static fields
.field private static a:Z = false


# instance fields
.field private b:Z

.field private c:Z

.field private d:Z

.field private e:Z


# direct methods
.method static constructor <clinit>()V
    .registers 0

    return-void
.end method

.method constructor <init>()V
    .registers 3

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/unity3d/player/o;->b:Z

    iput-boolean v0, p0, Lcom/unity3d/player/o;->c:Z

    const/4 v1, 0x1

    iput-boolean v1, p0, Lcom/unity3d/player/o;->d:Z

    iput-boolean v0, p0, Lcom/unity3d/player/o;->e:Z

    return-void
.end method

.method static a()V
    .registers 1

    const/4 v0, 0x1

    sput-boolean v0, Lcom/unity3d/player/o;->a:Z

    return-void
.end method

.method static b()V
    .registers 1

    const/4 v0, 0x0

    sput-boolean v0, Lcom/unity3d/player/o;->a:Z

    return-void
.end method

.method static c()Z
    .registers 1

    sget-boolean v0, Lcom/unity3d/player/o;->a:Z

    return v0
.end method


# virtual methods
.method final a(Z)V
    .registers 2

    iput-boolean p1, p0, Lcom/unity3d/player/o;->b:Z

    return-void
.end method

.method final b(Z)V
    .registers 2

    iput-boolean p1, p0, Lcom/unity3d/player/o;->d:Z

    return-void
.end method

.method final c(Z)V
    .registers 2

    iput-boolean p1, p0, Lcom/unity3d/player/o;->e:Z

    return-void
.end method

.method final d(Z)V
    .registers 2

    iput-boolean p1, p0, Lcom/unity3d/player/o;->c:Z

    return-void
.end method

.method final d()Z
    .registers 2

    iget-boolean v0, p0, Lcom/unity3d/player/o;->d:Z

    return v0
.end method

.method final e()Z
    .registers 2

    iget-boolean v0, p0, Lcom/unity3d/player/o;->e:Z

    return v0
.end method

.method final e(Z)Z
    .registers 3

    sget-boolean v0, Lcom/unity3d/player/o;->a:Z

    if-eqz v0, :cond_14

    if-nez p1, :cond_a

    iget-boolean p1, p0, Lcom/unity3d/player/o;->b:Z

    if-eqz p1, :cond_14

    :cond_a
    iget-boolean p1, p0, Lcom/unity3d/player/o;->d:Z

    if-nez p1, :cond_14

    iget-boolean p1, p0, Lcom/unity3d/player/o;->c:Z

    if-nez p1, :cond_14

    const/4 p1, 0x1

    return p1

    :cond_14
    const/4 p1, 0x0

    return p1
.end method

.method final f()Z
    .registers 2

    iget-boolean v0, p0, Lcom/unity3d/player/o;->c:Z

    return v0
.end method

.method public final toString()Ljava/lang/String;
    .registers 2

    invoke-super {p0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method
