.class public Lcom/unity3d/player/HFPStatus;
.super Ljava/lang/Object;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/unity3d/player/HFPStatus$a;
    }
.end annotation


# instance fields
.field private a:Landroid/content/Context;

.field private b:Landroid/content/BroadcastReceiver;

.field private c:Landroid/content/Intent;

.field private d:Z

.field private e:Landroid/media/AudioManager;

.field private f:Z

.field private g:I


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .registers 4

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/unity3d/player/HFPStatus;->b:Landroid/content/BroadcastReceiver;

    iput-object v0, p0, Lcom/unity3d/player/HFPStatus;->c:Landroid/content/Intent;

    const/4 v1, 0x0

    iput-boolean v1, p0, Lcom/unity3d/player/HFPStatus;->d:Z

    iput-object v0, p0, Lcom/unity3d/player/HFPStatus;->e:Landroid/media/AudioManager;

    iput-boolean v1, p0, Lcom/unity3d/player/HFPStatus;->f:Z

    sget v0, Lcom/unity3d/player/HFPStatus$a;->a:I

    iput v0, p0, Lcom/unity3d/player/HFPStatus;->g:I

    iput-object p1, p0, Lcom/unity3d/player/HFPStatus;->a:Landroid/content/Context;

    const-string v0, "audio"

    invoke-virtual {p1, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Landroid/media/AudioManager;

    iput-object p1, p0, Lcom/unity3d/player/HFPStatus;->e:Landroid/media/AudioManager;

    invoke-direct {p0}, Lcom/unity3d/player/HFPStatus;->initHFPStatusJni()V

    return-void
.end method

.method static synthetic a(Lcom/unity3d/player/HFPStatus;I)I
    .registers 2

    iput p1, p0, Lcom/unity3d/player/HFPStatus;->g:I

    return p1
.end method

.method static synthetic a(Lcom/unity3d/player/HFPStatus;)V
    .registers 1

    invoke-direct {p0}, Lcom/unity3d/player/HFPStatus;->c()V

    return-void
.end method

.method private b()V
    .registers 3

    iget-object v0, p0, Lcom/unity3d/player/HFPStatus;->b:Landroid/content/BroadcastReceiver;

    if-eqz v0, :cond_e

    iget-object v1, p0, Lcom/unity3d/player/HFPStatus;->a:Landroid/content/Context;

    invoke-virtual {v1, v0}, Landroid/content/Context;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/unity3d/player/HFPStatus;->b:Landroid/content/BroadcastReceiver;

    iput-object v0, p0, Lcom/unity3d/player/HFPStatus;->c:Landroid/content/Intent;

    :cond_e
    sget v0, Lcom/unity3d/player/HFPStatus$a;->a:I

    iput v0, p0, Lcom/unity3d/player/HFPStatus;->g:I

    return-void
.end method

.method static synthetic b(Lcom/unity3d/player/HFPStatus;)Z
    .registers 1

    iget-boolean p0, p0, Lcom/unity3d/player/HFPStatus;->d:Z

    return p0
.end method

.method static synthetic c(Lcom/unity3d/player/HFPStatus;)Landroid/media/AudioManager;
    .registers 1

    iget-object p0, p0, Lcom/unity3d/player/HFPStatus;->e:Landroid/media/AudioManager;

    return-object p0
.end method

.method private c()V
    .registers 2

    iget-boolean v0, p0, Lcom/unity3d/player/HFPStatus;->f:Z

    if-eqz v0, :cond_c

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/unity3d/player/HFPStatus;->f:Z

    iget-object v0, p0, Lcom/unity3d/player/HFPStatus;->e:Landroid/media/AudioManager;

    invoke-virtual {v0}, Landroid/media/AudioManager;->stopBluetoothSco()V

    :cond_c
    return-void
.end method

.method private final native deinitHFPStatusJni()V
.end method

.method private final native initHFPStatusJni()V
.end method


# virtual methods
.method public final a()V
    .registers 1

    invoke-virtual {p0}, Lcom/unity3d/player/HFPStatus;->clearHFPStat()V

    invoke-direct {p0}, Lcom/unity3d/player/HFPStatus;->deinitHFPStatusJni()V

    return-void
.end method

.method protected clearHFPStat()V
    .registers 1

    invoke-direct {p0}, Lcom/unity3d/player/HFPStatus;->b()V

    invoke-direct {p0}, Lcom/unity3d/player/HFPStatus;->c()V

    return-void
.end method

.method protected getHFPStat()Z
    .registers 3

    iget v0, p0, Lcom/unity3d/player/HFPStatus;->g:I

    sget v1, Lcom/unity3d/player/HFPStatus$a;->b:I

    if-ne v0, v1, :cond_8

    const/4 v0, 0x1

    return v0

    :cond_8
    const/4 v0, 0x0

    return v0
.end method

.method protected requestHFPStat()V
    .registers 5

    invoke-virtual {p0}, Lcom/unity3d/player/HFPStatus;->clearHFPStat()V

    new-instance v0, Lcom/unity3d/player/HFPStatus$1;

    invoke-direct {v0, p0}, Lcom/unity3d/player/HFPStatus$1;-><init>(Lcom/unity3d/player/HFPStatus;)V

    iput-object v0, p0, Lcom/unity3d/player/HFPStatus;->b:Landroid/content/BroadcastReceiver;

    iget-object v1, p0, Lcom/unity3d/player/HFPStatus;->a:Landroid/content/Context;

    new-instance v2, Landroid/content/IntentFilter;

    const-string v3, "android.media.ACTION_SCO_AUDIO_STATE_UPDATED"

    invoke-direct {v2, v3}, Landroid/content/IntentFilter;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, v0, v2}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    move-result-object v0

    iput-object v0, p0, Lcom/unity3d/player/HFPStatus;->c:Landroid/content/Intent;

    const/4 v0, 0x1

    :try_start_1a
    iput-boolean v0, p0, Lcom/unity3d/player/HFPStatus;->f:Z

    iget-object v0, p0, Lcom/unity3d/player/HFPStatus;->e:Landroid/media/AudioManager;

    invoke-virtual {v0}, Landroid/media/AudioManager;->startBluetoothSco()V
    :try_end_21
    .catch Ljava/lang/NullPointerException; {:try_start_1a .. :try_end_21} :catch_22

    return-void

    :catch_22
    const/4 v0, 0x5

    const-string v1, "startBluetoothSco() failed. no bluetooth device connected."

    invoke-static {v0, v1}, Lcom/unity3d/player/f;->Log(ILjava/lang/String;)V

    return-void
.end method

.method protected setHFPRecordingStat(Z)V
    .registers 3

    iput-boolean p1, p0, Lcom/unity3d/player/HFPStatus;->d:Z

    if-nez p1, :cond_a

    iget-object p1, p0, Lcom/unity3d/player/HFPStatus;->e:Landroid/media/AudioManager;

    const/4 v0, 0x0

    invoke-virtual {p1, v0}, Landroid/media/AudioManager;->setMode(I)V

    :cond_a
    return-void
.end method