.class public final Lcom/unity3d/player/j;
.super Ljava/lang/Object;


# instance fields
.field private a:Ljava/lang/String;

.field private b:Ljava/lang/String;

.field private c:[Ljava/lang/String;

.field private d:[Ljava/lang/String;

.field private e:I

.field private f:J

.field private g:[Ljava/lang/String;

.field private h:[Ljava/lang/String;

.field private i:Ljava/lang/String;

.field private j:Ljava/lang/String;

.field private k:[Ljava/lang/String;


# direct methods
.method public constructor <init>(Lorg/json/JSONObject;)V
    .registers 5

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const-string v0, "imageUrl"

    invoke-virtual {p1, v0}, Lorg/json/JSONObject;->optString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/unity3d/player/j;->a:Ljava/lang/String;

    const-string v0, "clickUrl"

    const-string v1, ""

    invoke-virtual {p1, v0, v1}, Lorg/json/JSONObject;->optString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/unity3d/player/j;->b:Ljava/lang/String;

    const-string v0, "duration"

    const/4 v1, 0x5

    invoke-virtual {p1, v0, v1}, Lorg/json/JSONObject;->optInt(Ljava/lang/String;I)I

    move-result v0

    iput v0, p0, Lcom/unity3d/player/j;->e:I

    const-string v0, "expiration"

    const-wide/16 v1, 0x0

    invoke-virtual {p1, v0, v1, v2}, Lorg/json/JSONObject;->optLong(Ljava/lang/String;J)J

    move-result-wide v0

    iput-wide v0, p0, Lcom/unity3d/player/j;->f:J

    const-string v0, "impression"

    invoke-virtual {p1, v0}, Lorg/json/JSONObject;->optJSONArray(Ljava/lang/String;)Lorg/json/JSONArray;

    move-result-object v0

    invoke-static {v0}, Lcom/unity3d/player/j;->a(Lorg/json/JSONArray;)[Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/unity3d/player/j;->c:[Ljava/lang/String;

    const-string v0, "clickImpression"

    invoke-virtual {p1, v0}, Lorg/json/JSONObject;->optJSONArray(Ljava/lang/String;)Lorg/json/JSONArray;

    move-result-object v0

    invoke-static {v0}, Lcom/unity3d/player/j;->a(Lorg/json/JSONArray;)[Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/unity3d/player/j;->d:[Ljava/lang/String;

    const-string v0, "primaryClickImpression"

    invoke-virtual {p1, v0}, Lorg/json/JSONObject;->optJSONArray(Ljava/lang/String;)Lorg/json/JSONArray;

    move-result-object v0

    invoke-static {v0}, Lcom/unity3d/player/j;->a(Lorg/json/JSONArray;)[Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/unity3d/player/j;->g:[Ljava/lang/String;

    const-string v0, "fallbackClickImpression"

    invoke-virtual {p1, v0}, Lorg/json/JSONObject;->optJSONArray(Ljava/lang/String;)Lorg/json/JSONArray;

    move-result-object v0

    invoke-static {v0}, Lcom/unity3d/player/j;->a(Lorg/json/JSONArray;)[Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/unity3d/player/j;->h:[Ljava/lang/String;

    const-string v0, "mediaType"

    invoke-virtual {p1, v0}, Lorg/json/JSONObject;->optString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/unity3d/player/j;->i:Ljava/lang/String;

    const-string v0, "videoUrl"

    invoke-virtual {p1, v0}, Lorg/json/JSONObject;->optString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/unity3d/player/j;->j:Ljava/lang/String;

    const-string v0, "completeClickImpression"

    invoke-virtual {p1, v0}, Lorg/json/JSONObject;->optJSONArray(Ljava/lang/String;)Lorg/json/JSONArray;

    move-result-object p1

    invoke-static {p1}, Lcom/unity3d/player/j;->a(Lorg/json/JSONArray;)[Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/unity3d/player/j;->k:[Ljava/lang/String;

    return-void
.end method

.method private static a(Lorg/json/JSONArray;)[Ljava/lang/String;
    .registers 5

    if-nez p0, :cond_4

    const/4 p0, 0x0

    return-object p0

    :cond_4
    invoke-virtual {p0}, Lorg/json/JSONArray;->length()I

    move-result v0

    new-array v1, v0, [Ljava/lang/String;

    const/4 v2, 0x0

    :goto_b
    if-ge v2, v0, :cond_16

    invoke-virtual {p0, v2}, Lorg/json/JSONArray;->optString(I)Ljava/lang/String;

    move-result-object v3

    aput-object v3, v1, v2

    add-int/lit8 v2, v2, 0x1

    goto :goto_b

    :cond_16
    return-object v1
.end method


# virtual methods
.method public final a()Z
    .registers 4

    invoke-virtual {p0}, Lcom/unity3d/player/j;->j()Ljava/lang/String;

    move-result-object v0

    const-string v1, "VIDEO"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    const-string v1, ""

    const/4 v2, 0x0

    if-eqz v0, :cond_1c

    invoke-virtual {p0}, Lcom/unity3d/player/j;->k()Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_1b

    invoke-virtual {p0}, Lcom/unity3d/player/j;->k()Ljava/lang/String;

    move-result-object v0

    if-ne v0, v1, :cond_39

    :cond_1b
    return v2

    :cond_1c
    invoke-virtual {p0}, Lcom/unity3d/player/j;->b()Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_3b

    if-ne v0, v1, :cond_25

    goto :goto_3b

    :cond_25
    const-string v1, "file://"

    invoke-virtual {v0, v1}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_32

    const/4 v1, 0x7

    invoke-virtual {v0, v1}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v0

    :cond_32
    invoke-static {v0}, Landroid/graphics/BitmapFactory;->decodeFile(Ljava/lang/String;)Landroid/graphics/Bitmap;

    move-result-object v0

    if-nez v0, :cond_39

    return v2

    :cond_39
    const/4 v0, 0x1

    return v0

    :cond_3b
    :goto_3b
    return v2
.end method

.method public final b()Ljava/lang/String;
    .registers 2

    iget-object v0, p0, Lcom/unity3d/player/j;->a:Ljava/lang/String;

    return-object v0
.end method

.method public final c()Ljava/lang/String;
    .registers 2

    iget-object v0, p0, Lcom/unity3d/player/j;->b:Ljava/lang/String;

    return-object v0
.end method

.method public final d()[Ljava/lang/String;
    .registers 2

    iget-object v0, p0, Lcom/unity3d/player/j;->c:[Ljava/lang/String;

    return-object v0
.end method

.method public final e()[Ljava/lang/String;
    .registers 2

    iget-object v0, p0, Lcom/unity3d/player/j;->d:[Ljava/lang/String;

    return-object v0
.end method

.method public final f()I
    .registers 2

    iget v0, p0, Lcom/unity3d/player/j;->e:I

    return v0
.end method

.method public final g()J
    .registers 3

    iget-wide v0, p0, Lcom/unity3d/player/j;->f:J

    return-wide v0
.end method

.method public final h()[Ljava/lang/String;
    .registers 2

    iget-object v0, p0, Lcom/unity3d/player/j;->g:[Ljava/lang/String;

    return-object v0
.end method

.method public final i()[Ljava/lang/String;
    .registers 2

    iget-object v0, p0, Lcom/unity3d/player/j;->h:[Ljava/lang/String;

    return-object v0
.end method

.method public final j()Ljava/lang/String;
    .registers 2

    iget-object v0, p0, Lcom/unity3d/player/j;->i:Ljava/lang/String;

    return-object v0
.end method

.method public final k()Ljava/lang/String;
    .registers 2

    iget-object v0, p0, Lcom/unity3d/player/j;->j:Ljava/lang/String;

    return-object v0
.end method

.method public final l()[Ljava/lang/String;
    .registers 2

    iget-object v0, p0, Lcom/unity3d/player/j;->k:[Ljava/lang/String;

    return-object v0
.end method
