.class public final Lcom/unity3d/player/k;
.super Landroid/widget/RelativeLayout;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/unity3d/player/k$a;
    }
.end annotation


# instance fields
.field a:Lcom/unity3d/player/UnityPlayer;

.field b:Lcom/unity3d/player/j;

.field c:Landroid/content/Context;

.field d:I

.field e:Landroid/widget/VideoView;

.field f:Landroid/widget/ImageView;

.field g:Landroid/widget/ImageView;

.field h:Landroid/widget/TextView;

.field i:Ljava/util/Timer;

.field j:I

.field k:I

.field l:Z

.field m:Ljava/util/TimerTask;


# direct methods
.method public constructor <init>(Landroid/content/Context;Lcom/unity3d/player/UnityPlayer;Lcom/unity3d/player/j;)V
    .registers 6

    invoke-direct {p0, p1}, Landroid/widget/RelativeLayout;-><init>(Landroid/content/Context;)V

    const/4 v0, 0x5

    iput v0, p0, Lcom/unity3d/player/k;->d:I

    const/4 v1, 0x0

    iput v1, p0, Lcom/unity3d/player/k;->j:I

    iput v0, p0, Lcom/unity3d/player/k;->k:I

    new-instance v0, Lcom/unity3d/player/k$4;

    invoke-direct {v0, p0}, Lcom/unity3d/player/k$4;-><init>(Lcom/unity3d/player/k;)V

    iput-object v0, p0, Lcom/unity3d/player/k;->m:Ljava/util/TimerTask;

    iput-object p1, p0, Lcom/unity3d/player/k;->c:Landroid/content/Context;

    iput-object p2, p0, Lcom/unity3d/player/k;->a:Lcom/unity3d/player/UnityPlayer;

    iput-object p3, p0, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-direct {p0}, Lcom/unity3d/player/k;->d()V

    invoke-direct {p0}, Lcom/unity3d/player/k;->e()V

    invoke-direct {p0}, Lcom/unity3d/player/k;->g()V

    return-void
.end method

.method static synthetic a(Ljava/lang/String;)Ljava/lang/String;
    .registers 1

    invoke-static {p0}, Lcom/unity3d/player/k;->f(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method private static a(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .registers 8

    const/4 v0, 0x0

    if-nez p0, :cond_4

    return-object v0

    :cond_4
    const/16 v1, 0x3f

    invoke-virtual {p0, v1}, Ljava/lang/String;->indexOf(I)I

    move-result v1

    const/4 v2, -0x1

    if-ne v1, v2, :cond_e

    return-object v0

    :cond_e
    const-string v1, "\\?"

    invoke-virtual {p0, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    const/4 v1, 0x1

    aget-object p0, p0, v1

    const-string v2, "&"

    invoke-virtual {p0, v2}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    const/4 v2, 0x0

    const/4 v3, 0x0

    :goto_1f
    array-length v4, p0

    if-ge v3, v4, :cond_38

    aget-object v4, p0, v3

    const-string v5, "="

    invoke-virtual {v4, v5}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v4

    aget-object v5, v4, v2

    invoke-virtual {p1, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_35

    aget-object p0, v4, v1

    return-object p0

    :cond_35
    add-int/lit8 v3, v3, 0x1

    goto :goto_1f

    :cond_38
    return-object v0
.end method

.method static synthetic a(Lcom/unity3d/player/k;)V
    .registers 1

    invoke-direct {p0}, Lcom/unity3d/player/k;->h()V

    return-void
.end method

.method static synthetic a(Lcom/unity3d/player/k;Ljava/lang/String;)V
    .registers 2

    invoke-direct {p0, p1}, Lcom/unity3d/player/k;->b(Ljava/lang/String;)V

    return-void
.end method

.method static synthetic b(Lcom/unity3d/player/k;)V
    .registers 1

    invoke-direct {p0}, Lcom/unity3d/player/k;->i()V

    return-void
.end method

.method private b(Ljava/lang/String;)V
    .registers 4

    const-string v0, "deeplinker"

    invoke-virtual {p1, v0}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_66

    const-string v0, "primaryUrl"

    invoke-static {p1, v0}, Lcom/unity3d/player/k;->a(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/unity3d/player/k;->f(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "fallbackUrl"

    invoke-static {p1, v1}, Lcom/unity3d/player/k;->a(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-static {p1}, Lcom/unity3d/player/k;->f(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, v0}, Lcom/unity3d/player/k;->d(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_44

    if-eqz v0, :cond_44

    invoke-static {v0}, Lcom/unity3d/player/k;->e(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_44

    iget-object p1, p0, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-virtual {p1}, Lcom/unity3d/player/j;->h()[Ljava/lang/String;

    move-result-object p1

    if-eqz p1, :cond_40

    new-instance p1, Lcom/unity3d/player/k$a;

    invoke-direct {p1, p0}, Lcom/unity3d/player/k$a;-><init>(Lcom/unity3d/player/k;)V

    iget-object v1, p0, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-virtual {v1}, Lcom/unity3d/player/j;->h()[Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1, v1}, Lcom/unity3d/player/k$a;->execute([Ljava/lang/Object;)Landroid/os/AsyncTask;

    :cond_40
    invoke-direct {p0, v0}, Lcom/unity3d/player/k;->c(Ljava/lang/String;)V

    return-void

    :cond_44
    if-eqz p1, :cond_65

    invoke-static {p1}, Lcom/unity3d/player/k;->e(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_65

    iget-object v0, p0, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-virtual {v0}, Lcom/unity3d/player/j;->i()[Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_62

    new-instance v0, Lcom/unity3d/player/k$a;

    invoke-direct {v0, p0}, Lcom/unity3d/player/k$a;-><init>(Lcom/unity3d/player/k;)V

    iget-object v1, p0, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-virtual {v1}, Lcom/unity3d/player/j;->i()[Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/unity3d/player/k$a;->execute([Ljava/lang/Object;)Landroid/os/AsyncTask;

    :cond_62
    invoke-direct {p0, p1}, Lcom/unity3d/player/k;->c(Ljava/lang/String;)V

    :cond_65
    return-void

    :cond_66
    invoke-static {p1}, Lcom/unity3d/player/k;->e(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_6f

    invoke-direct {p0, p1}, Lcom/unity3d/player/k;->c(Ljava/lang/String;)V

    :cond_6f
    return-void
.end method

.method private c(Ljava/lang/String;)V
    .registers 4

    if-nez p1, :cond_3

    return-void

    :cond_3
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    const-string v1, "android.intent.action.VIEW"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    invoke-static {p1}, Landroid/net/Uri;->parse(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object p1

    invoke-virtual {v0, p1}, Landroid/content/Intent;->setData(Landroid/net/Uri;)Landroid/content/Intent;

    iget-object p1, p0, Lcom/unity3d/player/k;->c:Landroid/content/Context;

    invoke-virtual {p1, v0}, Landroid/content/Context;->startActivity(Landroid/content/Intent;)V

    iget-object p1, p0, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-virtual {p1}, Lcom/unity3d/player/j;->e()[Ljava/lang/String;

    move-result-object p1

    if-eqz p1, :cond_2f

    new-instance p1, Lcom/unity3d/player/k$a;

    invoke-direct {p1, p0}, Lcom/unity3d/player/k$a;-><init>(Lcom/unity3d/player/k;)V

    iget-object v0, p0, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-virtual {v0}, Lcom/unity3d/player/j;->e()[Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Lcom/unity3d/player/k$a;->execute([Ljava/lang/Object;)Landroid/os/AsyncTask;

    :cond_2f
    return-void
.end method

.method private d()V
    .registers 7

    iget-object v0, p0, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-virtual {v0}, Lcom/unity3d/player/j;->j()Ljava/lang/String;

    move-result-object v0

    const-string v1, "VIDEO"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    const/16 v1, 0x9

    const/16 v2, 0xb

    const-string v3, ""

    const/4 v4, -0x1

    if-eqz v0, :cond_82

    iget-object v0, p0, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-virtual {v0}, Lcom/unity3d/player/j;->k()Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_81

    iget-object v0, p0, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-virtual {v0}, Lcom/unity3d/player/j;->k()Ljava/lang/String;

    move-result-object v0

    if-ne v0, v3, :cond_26

    goto :goto_81

    :cond_26
    iget-object v0, p0, Lcom/unity3d/player/k;->e:Landroid/widget/VideoView;

    if-nez v0, :cond_db

    new-instance v0, Landroid/widget/RelativeLayout$LayoutParams;

    invoke-direct {v0, v4, v4}, Landroid/widget/RelativeLayout$LayoutParams;-><init>(II)V

    invoke-virtual {v0, v2}, Landroid/widget/RelativeLayout$LayoutParams;->addRule(I)V

    invoke-virtual {v0, v1}, Landroid/widget/RelativeLayout$LayoutParams;->addRule(I)V

    new-instance v1, Landroid/media/MediaMetadataRetriever;

    invoke-direct {v1}, Landroid/media/MediaMetadataRetriever;-><init>()V

    iget-object v2, p0, Lcom/unity3d/player/k;->c:Landroid/content/Context;

    iget-object v3, p0, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-virtual {v3}, Lcom/unity3d/player/j;->k()Ljava/lang/String;

    move-result-object v3

    invoke-static {v3}, Landroid/net/Uri;->parse(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v3

    invoke-virtual {v1, v2, v3}, Landroid/media/MediaMetadataRetriever;->setDataSource(Landroid/content/Context;Landroid/net/Uri;)V

    const-wide/16 v2, 0x0

    const/4 v4, 0x2

    invoke-virtual {v1, v2, v3, v4}, Landroid/media/MediaMetadataRetriever;->getFrameAtTime(JI)Landroid/graphics/Bitmap;

    move-result-object v1

    new-instance v2, Landroid/widget/ImageView;

    iget-object v3, p0, Lcom/unity3d/player/k;->c:Landroid/content/Context;

    invoke-direct {v2, v3}, Landroid/widget/ImageView;-><init>(Landroid/content/Context;)V

    invoke-virtual {v2, v0}, Landroid/widget/ImageView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    invoke-virtual {v2, v1}, Landroid/widget/ImageView;->setImageBitmap(Landroid/graphics/Bitmap;)V

    sget-object v1, Landroid/widget/ImageView$ScaleType;->CENTER_CROP:Landroid/widget/ImageView$ScaleType;

    invoke-virtual {v2, v1}, Landroid/widget/ImageView;->setScaleType(Landroid/widget/ImageView$ScaleType;)V

    invoke-virtual {p0, v2}, Lcom/unity3d/player/k;->addView(Landroid/view/View;)V

    new-instance v1, Landroid/widget/VideoView;

    iget-object v2, p0, Lcom/unity3d/player/k;->c:Landroid/content/Context;

    invoke-direct {v1, v2}, Landroid/widget/VideoView;-><init>(Landroid/content/Context;)V

    iput-object v1, p0, Lcom/unity3d/player/k;->e:Landroid/widget/VideoView;

    invoke-virtual {v1, v0}, Landroid/widget/VideoView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    iget-object v0, p0, Lcom/unity3d/player/k;->e:Landroid/widget/VideoView;

    iget-object v1, p0, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-virtual {v1}, Lcom/unity3d/player/j;->k()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/VideoView;->setVideoPath(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/unity3d/player/k;->e:Landroid/widget/VideoView;

    invoke-virtual {p0, v0}, Lcom/unity3d/player/k;->addView(Landroid/view/View;)V

    :cond_81
    :goto_81
    return-void

    :cond_82
    iget-object v0, p0, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-virtual {v0}, Lcom/unity3d/player/j;->b()Ljava/lang/String;

    move-result-object v0

    iget-object v5, p0, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-virtual {v5}, Lcom/unity3d/player/j;->b()Ljava/lang/String;

    move-result-object v5

    if-eqz v5, :cond_db

    iget-object v5, p0, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-virtual {v5}, Lcom/unity3d/player/j;->b()Ljava/lang/String;

    move-result-object v5

    if-ne v5, v3, :cond_99

    goto :goto_db

    :cond_99
    iget-object v3, p0, Lcom/unity3d/player/k;->f:Landroid/widget/ImageView;

    if-nez v3, :cond_db

    const-string v3, "file://"

    invoke-virtual {v0, v3}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v3

    if-eqz v3, :cond_aa

    const/4 v3, 0x7

    invoke-virtual {v0, v3}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v0

    :cond_aa
    invoke-static {v0}, Landroid/graphics/BitmapFactory;->decodeFile(Ljava/lang/String;)Landroid/graphics/Bitmap;

    move-result-object v0

    if-nez v0, :cond_b1

    return-void

    :cond_b1
    new-instance v3, Landroid/widget/ImageView;

    iget-object v5, p0, Lcom/unity3d/player/k;->c:Landroid/content/Context;

    invoke-direct {v3, v5}, Landroid/widget/ImageView;-><init>(Landroid/content/Context;)V

    iput-object v3, p0, Lcom/unity3d/player/k;->f:Landroid/widget/ImageView;

    new-instance v3, Landroid/widget/RelativeLayout$LayoutParams;

    invoke-direct {v3, v4, v4}, Landroid/widget/RelativeLayout$LayoutParams;-><init>(II)V

    invoke-virtual {v3, v2}, Landroid/widget/RelativeLayout$LayoutParams;->addRule(I)V

    invoke-virtual {v3, v1}, Landroid/widget/RelativeLayout$LayoutParams;->addRule(I)V

    iget-object v1, p0, Lcom/unity3d/player/k;->f:Landroid/widget/ImageView;

    invoke-virtual {v1, v3}, Landroid/widget/ImageView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    iget-object v1, p0, Lcom/unity3d/player/k;->f:Landroid/widget/ImageView;

    invoke-virtual {v1, v0}, Landroid/widget/ImageView;->setImageBitmap(Landroid/graphics/Bitmap;)V

    iget-object v0, p0, Lcom/unity3d/player/k;->f:Landroid/widget/ImageView;

    sget-object v1, Landroid/widget/ImageView$ScaleType;->CENTER_CROP:Landroid/widget/ImageView$ScaleType;

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setScaleType(Landroid/widget/ImageView$ScaleType;)V

    iget-object v0, p0, Lcom/unity3d/player/k;->f:Landroid/widget/ImageView;

    invoke-virtual {p0, v0}, Lcom/unity3d/player/k;->addView(Landroid/view/View;)V

    :cond_db
    :goto_db
    return-void
.end method

.method private d(Ljava/lang/String;)Z
    .registers 5

    const/4 v0, 0x0

    if-nez p1, :cond_4

    return v0

    :cond_4
    new-instance v1, Landroid/content/Intent;

    invoke-direct {v1}, Landroid/content/Intent;-><init>()V

    const-string v2, "android.intent.action.VIEW"

    invoke-virtual {v1, v2}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    invoke-static {p1}, Landroid/net/Uri;->parse(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object p1

    invoke-virtual {v1, p1}, Landroid/content/Intent;->setData(Landroid/net/Uri;)Landroid/content/Intent;

    iget-object p1, p0, Lcom/unity3d/player/k;->c:Landroid/content/Context;

    invoke-virtual {p1}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object p1

    invoke-virtual {p1, v1, v0}, Landroid/content/pm/PackageManager;->resolveActivity(Landroid/content/Intent;I)Landroid/content/pm/ResolveInfo;

    move-result-object p1

    if-eqz p1, :cond_23

    const/4 p1, 0x1

    return p1

    :cond_23
    return v0
.end method

.method private e()V
    .registers 5

    iget-object v0, p0, Lcom/unity3d/player/k;->a:Lcom/unity3d/player/UnityPlayer;

    invoke-virtual {v0}, Lcom/unity3d/player/UnityPlayer;->getShowSplashSlogan()Ljava/lang/Boolean;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v0

    if-eqz v0, :cond_6a

    invoke-virtual {p0}, Lcom/unity3d/player/k;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    iget-object v1, p0, Lcom/unity3d/player/k;->c:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v1

    const-string v2, "unity_splash_slogan"

    const-string v3, "drawable"

    invoke-virtual {v0, v2, v3, v1}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0}, Lcom/unity3d/player/k;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    new-instance v2, Landroid/graphics/BitmapFactory$Options;

    invoke-direct {v2}, Landroid/graphics/BitmapFactory$Options;-><init>()V

    invoke-static {v1, v0, v2}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;ILandroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;

    move-result-object v0

    new-instance v1, Landroid/widget/ImageView;

    iget-object v2, p0, Lcom/unity3d/player/k;->c:Landroid/content/Context;

    invoke-direct {v1, v2}, Landroid/widget/ImageView;-><init>(Landroid/content/Context;)V

    iput-object v1, p0, Lcom/unity3d/player/k;->g:Landroid/widget/ImageView;

    new-instance v1, Landroid/widget/RelativeLayout$LayoutParams;

    iget-object v2, p0, Lcom/unity3d/player/k;->a:Lcom/unity3d/player/UnityPlayer;

    invoke-virtual {v2}, Lcom/unity3d/player/UnityPlayer;->getShowSplashSloganHeight()I

    move-result v2

    const/4 v3, -0x1

    invoke-direct {v1, v3, v2}, Landroid/widget/RelativeLayout$LayoutParams;-><init>(II)V

    const/16 v2, 0xb

    invoke-virtual {v1, v2}, Landroid/widget/RelativeLayout$LayoutParams;->addRule(I)V

    const/16 v2, 0x9

    invoke-virtual {v1, v2}, Landroid/widget/RelativeLayout$LayoutParams;->addRule(I)V

    const/16 v2, 0xc

    invoke-virtual {v1, v2}, Landroid/widget/RelativeLayout$LayoutParams;->addRule(I)V

    iget-object v2, p0, Lcom/unity3d/player/k;->g:Landroid/widget/ImageView;

    invoke-virtual {v2, v1}, Landroid/widget/ImageView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    iget-object v1, p0, Lcom/unity3d/player/k;->g:Landroid/widget/ImageView;

    invoke-virtual {v1, v0}, Landroid/widget/ImageView;->setImageBitmap(Landroid/graphics/Bitmap;)V

    iget-object v0, p0, Lcom/unity3d/player/k;->g:Landroid/widget/ImageView;

    sget-object v1, Landroid/widget/ImageView$ScaleType;->CENTER:Landroid/widget/ImageView$ScaleType;

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setScaleType(Landroid/widget/ImageView$ScaleType;)V

    iget-object v0, p0, Lcom/unity3d/player/k;->g:Landroid/widget/ImageView;

    invoke-virtual {v0, v3}, Landroid/widget/ImageView;->setBackgroundColor(I)V

    iget-object v0, p0, Lcom/unity3d/player/k;->g:Landroid/widget/ImageView;

    invoke-virtual {p0, v0}, Lcom/unity3d/player/k;->addView(Landroid/view/View;)V

    :cond_6a
    return-void
.end method

.method private static e(Ljava/lang/String;)Z
    .registers 2

    if-nez p0, :cond_4

    const/4 p0, 0x0

    return p0

    :cond_4
    const-string v0, "http"

    invoke-virtual {p0, v0}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result p0

    return p0
.end method

.method private static f(Ljava/lang/String;)Ljava/lang/String;
    .registers 3

    const/4 v0, 0x0

    if-nez p0, :cond_4

    return-object v0

    :cond_4
    :try_start_4
    const-string v1, "UTF-8"

    invoke-static {p0, v1}, Ljava/net/URLDecoder;->decode(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    const-string v1, "@#&=*+-_.,:!?()/~\'%"

    invoke-static {p0, v1}, Landroid/net/Uri;->encode(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0
    :try_end_10
    .catch Ljava/io/UnsupportedEncodingException; {:try_start_4 .. :try_end_10} :catch_11

    return-object p0

    :catch_11
    return-object v0
.end method

.method private f()V
    .registers 4

    new-instance v0, Landroid/widget/TextView;

    iget-object v1, p0, Lcom/unity3d/player/k;->c:Landroid/content/Context;

    invoke-direct {v0, v1}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/unity3d/player/k;->h:Landroid/widget/TextView;

    new-instance v0, Ljava/lang/StringBuilder;

    const-string v1, "Skip "

    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    iget v1, p0, Lcom/unity3d/player/k;->d:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/unity3d/player/k;->e:Landroid/widget/VideoView;

    if-eqz v1, :cond_1f

    const-string v0, "Skip"

    :cond_1f
    new-instance v1, Landroid/widget/RelativeLayout$LayoutParams;

    const/4 v2, -0x2

    invoke-direct {v1, v2, v2}, Landroid/widget/RelativeLayout$LayoutParams;-><init>(II)V

    const/16 v2, 0xb

    invoke-virtual {v1, v2}, Landroid/widget/RelativeLayout$LayoutParams;->addRule(I)V

    const/16 v2, 0xa

    invoke-virtual {v1, v2}, Landroid/widget/RelativeLayout$LayoutParams;->addRule(I)V

    const/16 v2, 0x30

    iput v2, v1, Landroid/widget/RelativeLayout$LayoutParams;->rightMargin:I

    const/16 v2, 0x48

    iput v2, v1, Landroid/widget/RelativeLayout$LayoutParams;->topMargin:I

    iget-object v2, p0, Lcom/unity3d/player/k;->h:Landroid/widget/TextView;

    invoke-virtual {v2, v1}, Landroid/widget/TextView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    iget-object v1, p0, Lcom/unity3d/player/k;->h:Landroid/widget/TextView;

    invoke-virtual {v1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object v0, p0, Lcom/unity3d/player/k;->h:Landroid/widget/TextView;

    const/high16 v1, 0x41700000

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setTextSize(F)V

    iget-object v0, p0, Lcom/unity3d/player/k;->h:Landroid/widget/TextView;

    const/4 v1, -0x1

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setTextColor(I)V

    new-instance v0, Landroid/graphics/drawable/GradientDrawable;

    invoke-direct {v0}, Landroid/graphics/drawable/GradientDrawable;-><init>()V

    const v2, -0x777778

    invoke-virtual {v0, v2}, Landroid/graphics/drawable/GradientDrawable;->setColor(I)V

    const/high16 v2, 0x41400000

    invoke-virtual {v0, v2}, Landroid/graphics/drawable/GradientDrawable;->setCornerRadius(F)V

    const/4 v2, 0x3

    invoke-virtual {v0, v2, v1}, Landroid/graphics/drawable/GradientDrawable;->setStroke(II)V

    iget-object v1, p0, Lcom/unity3d/player/k;->h:Landroid/widget/TextView;

    invoke-virtual {v1, v0}, Landroid/widget/TextView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    iget-object v0, p0, Lcom/unity3d/player/k;->h:Landroid/widget/TextView;

    const/16 v1, 0x14

    const/4 v2, 0x5

    invoke-virtual {v0, v1, v2, v1, v2}, Landroid/widget/TextView;->setPadding(IIII)V

    iget-object v0, p0, Lcom/unity3d/player/k;->h:Landroid/widget/TextView;

    const v1, 0x3f4ccccd

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setAlpha(F)V

    iget-object v0, p0, Lcom/unity3d/player/k;->h:Landroid/widget/TextView;

    new-instance v1, Lcom/unity3d/player/k$1;

    invoke-direct {v1, p0}, Lcom/unity3d/player/k$1;-><init>(Lcom/unity3d/player/k;)V

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    iget-object v0, p0, Lcom/unity3d/player/k;->h:Landroid/widget/TextView;

    invoke-virtual {p0, v0}, Lcom/unity3d/player/k;->addView(Landroid/view/View;)V

    return-void
.end method

.method private g()V
    .registers 5

    new-instance v0, Landroid/widget/TextView;

    iget-object v1, p0, Lcom/unity3d/player/k;->c:Landroid/content/Context;

    invoke-direct {v0, v1}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    new-instance v1, Landroid/widget/RelativeLayout$LayoutParams;

    const/4 v2, -0x2

    invoke-direct {v1, v2, v2}, Landroid/widget/RelativeLayout$LayoutParams;-><init>(II)V

    const/16 v2, 0x9

    invoke-virtual {v1, v2}, Landroid/widget/RelativeLayout$LayoutParams;->addRule(I)V

    const/16 v2, 0xa

    invoke-virtual {v1, v2}, Landroid/widget/RelativeLayout$LayoutParams;->addRule(I)V

    const/16 v3, 0x20

    iput v3, v1, Landroid/widget/RelativeLayout$LayoutParams;->leftMargin:I

    const/16 v3, 0x40

    iput v3, v1, Landroid/widget/RelativeLayout$LayoutParams;->topMargin:I

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    const-string v1, "ADS"

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    const/high16 v1, 0x41000000

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setTextSize(F)V

    const v1, -0x777778

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setTextColor(I)V

    const v1, 0x3f4ccccd

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setAlpha(F)V

    const/4 v1, 0x5

    invoke-virtual {v0, v2, v1, v2, v1}, Landroid/widget/TextView;->setPadding(IIII)V

    invoke-virtual {p0, v0}, Lcom/unity3d/player/k;->addView(Landroid/view/View;)V

    return-void
.end method

.method private h()V
    .registers 2

    iget-object v0, p0, Lcom/unity3d/player/k;->e:Landroid/widget/VideoView;

    if-eqz v0, :cond_7

    invoke-virtual {v0}, Landroid/widget/VideoView;->stopPlayback()V

    :cond_7
    iget-object v0, p0, Lcom/unity3d/player/k;->i:Ljava/util/Timer;

    if-eqz v0, :cond_e

    invoke-virtual {v0}, Ljava/util/Timer;->cancel()V

    :cond_e
    iget-object v0, p0, Lcom/unity3d/player/k;->a:Lcom/unity3d/player/UnityPlayer;

    invoke-virtual {v0}, Lcom/unity3d/player/UnityPlayer;->NotifySplashAdsFinished()V

    return-void
.end method

.method private i()V
    .registers 3

    iget-object v0, p0, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-virtual {v0}, Lcom/unity3d/player/j;->l()[Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_16

    new-instance v0, Lcom/unity3d/player/k$a;

    invoke-direct {v0, p0}, Lcom/unity3d/player/k$a;-><init>(Lcom/unity3d/player/k;)V

    iget-object v1, p0, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-virtual {v1}, Lcom/unity3d/player/j;->l()[Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/unity3d/player/k$a;->execute([Ljava/lang/Object;)Landroid/os/AsyncTask;

    :cond_16
    invoke-direct {p0}, Lcom/unity3d/player/k;->h()V

    return-void
.end method


# virtual methods
.method public final a()V
    .registers 8

    iget-object v0, p0, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-virtual {v0}, Lcom/unity3d/player/j;->f()I

    move-result v0

    if-lez v0, :cond_12

    iget-object v0, p0, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-virtual {v0}, Lcom/unity3d/player/j;->f()I

    move-result v0

    iput v0, p0, Lcom/unity3d/player/k;->d:I

    iput v0, p0, Lcom/unity3d/player/k;->k:I

    :cond_12
    invoke-direct {p0}, Lcom/unity3d/player/k;->f()V

    iget-object v0, p0, Lcom/unity3d/player/k;->e:Landroid/widget/VideoView;

    if-eqz v0, :cond_27

    new-instance v1, Lcom/unity3d/player/k$2;

    invoke-direct {v1, p0}, Lcom/unity3d/player/k$2;-><init>(Lcom/unity3d/player/k;)V

    invoke-virtual {v0, v1}, Landroid/widget/VideoView;->setOnCompletionListener(Landroid/media/MediaPlayer$OnCompletionListener;)V

    iget-object v0, p0, Lcom/unity3d/player/k;->e:Landroid/widget/VideoView;

    invoke-virtual {v0}, Landroid/widget/VideoView;->start()V

    goto :goto_3a

    :cond_27
    new-instance v1, Ljava/util/Timer;

    invoke-direct {v1}, Ljava/util/Timer;-><init>()V

    iput-object v1, p0, Lcom/unity3d/player/k;->i:Ljava/util/Timer;

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/unity3d/player/k;->l:Z

    iget-object v2, p0, Lcom/unity3d/player/k;->m:Ljava/util/TimerTask;

    const-wide/16 v3, 0x3e8

    const-wide/16 v5, 0x3e8

    invoke-virtual/range {v1 .. v6}, Ljava/util/Timer;->schedule(Ljava/util/TimerTask;JJ)V

    :goto_3a
    iget-object v0, p0, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-virtual {v0}, Lcom/unity3d/player/j;->d()[Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_50

    new-instance v0, Lcom/unity3d/player/k$a;

    invoke-direct {v0, p0}, Lcom/unity3d/player/k$a;-><init>(Lcom/unity3d/player/k;)V

    iget-object v1, p0, Lcom/unity3d/player/k;->b:Lcom/unity3d/player/j;

    invoke-virtual {v1}, Lcom/unity3d/player/j;->d()[Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/unity3d/player/k$a;->execute([Ljava/lang/Object;)Landroid/os/AsyncTask;

    :cond_50
    new-instance v0, Lcom/unity3d/player/k$3;

    invoke-direct {v0, p0}, Lcom/unity3d/player/k$3;-><init>(Lcom/unity3d/player/k;)V

    invoke-virtual {p0, v0}, Lcom/unity3d/player/k;->setOnTouchListener(Landroid/view/View$OnTouchListener;)V

    return-void
.end method

.method public final b()V
    .registers 2

    iget-object v0, p0, Lcom/unity3d/player/k;->e:Landroid/widget/VideoView;

    if-eqz v0, :cond_f

    invoke-virtual {v0}, Landroid/widget/VideoView;->pause()V

    iget-object v0, p0, Lcom/unity3d/player/k;->e:Landroid/widget/VideoView;

    invoke-virtual {v0}, Landroid/widget/VideoView;->getCurrentPosition()I

    move-result v0

    iput v0, p0, Lcom/unity3d/player/k;->j:I

    :cond_f
    iget-object v0, p0, Lcom/unity3d/player/k;->i:Ljava/util/Timer;

    if-eqz v0, :cond_16

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/unity3d/player/k;->l:Z

    :cond_16
    return-void
.end method

.method public final c()V
    .registers 3

    iget-object v0, p0, Lcom/unity3d/player/k;->e:Landroid/widget/VideoView;

    if-eqz v0, :cond_e

    invoke-virtual {v0}, Landroid/widget/VideoView;->start()V

    iget-object v0, p0, Lcom/unity3d/player/k;->e:Landroid/widget/VideoView;

    iget v1, p0, Lcom/unity3d/player/k;->j:I

    invoke-virtual {v0, v1}, Landroid/widget/VideoView;->seekTo(I)V

    :cond_e
    iget-object v0, p0, Lcom/unity3d/player/k;->i:Ljava/util/Timer;

    if-eqz v0, :cond_15

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/unity3d/player/k;->l:Z

    :cond_15
    return-void
.end method
