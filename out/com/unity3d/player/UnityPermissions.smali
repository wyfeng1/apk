.class public Lcom/unity3d/player/UnityPermissions;
.super Ljava/lang/Object;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/unity3d/player/UnityPermissions$ModalWaitForPermissionResponse;
    }
.end annotation


# static fields
.field private static final SKIP_DIALOG_METADATA_NAME:Ljava/lang/String; = "unityplayer.SkipPermissionsDialog"


# direct methods
.method public constructor <init>()V
    .registers 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method private static checkInfoForMetadata(Landroid/content/pm/PackageItemInfo;)Z
    .registers 2

    :try_start_0
    iget-object p0, p0, Landroid/content/pm/PackageItemInfo;->metaData:Landroid/os/Bundle;

    const-string v0, "unityplayer.SkipPermissionsDialog"

    invoke-virtual {p0, v0}, Landroid/os/Bundle;->getBoolean(Ljava/lang/String;)Z

    move-result p0
    :try_end_8
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_8} :catch_9

    return p0

    :catch_9
    const/4 p0, 0x0

    return p0
.end method

.method public static hasUserAuthorizedPermission(Landroid/app/Activity;Ljava/lang/String;)Z
    .registers 2

    invoke-virtual {p0, p1}, Landroid/app/Activity;->checkCallingOrSelfPermission(Ljava/lang/String;)I

    move-result p0

    if-nez p0, :cond_8

    const/4 p0, 0x1

    return p0

    :cond_8
    const/4 p0, 0x0

    return p0
.end method

.method public static requestUserPermissions(Landroid/app/Activity;[Ljava/lang/String;Lcom/unity3d/player/IPermissionRequestCallbacks;)V
    .registers 6

    sget-boolean v0, Lcom/unity3d/player/PlatformSupport;->MARSHMALLOW_SUPPORT:Z

    if-nez v0, :cond_5

    return-void

    :cond_5
    if-eqz p0, :cond_33

    if-nez p1, :cond_a

    goto :goto_33

    :cond_a
    invoke-virtual {p0}, Landroid/app/Activity;->getFragmentManager()Landroid/app/FragmentManager;

    move-result-object v0

    const-string v1, "96489"

    invoke-virtual {v0, v1}, Landroid/app/FragmentManager;->findFragmentByTag(Ljava/lang/String;)Landroid/app/Fragment;

    move-result-object v2

    if-nez v2, :cond_33

    new-instance v2, Lcom/unity3d/player/g;

    invoke-direct {v2, p0, p2}, Lcom/unity3d/player/g;-><init>(Landroid/app/Activity;Lcom/unity3d/player/IPermissionRequestCallbacks;)V

    new-instance p0, Landroid/os/Bundle;

    invoke-direct {p0}, Landroid/os/Bundle;-><init>()V

    const-string p2, "PermissionNames"

    invoke-virtual {p0, p2, p1}, Landroid/os/Bundle;->putStringArray(Ljava/lang/String;[Ljava/lang/String;)V

    invoke-virtual {v2, p0}, Lcom/unity3d/player/g;->setArguments(Landroid/os/Bundle;)V

    invoke-virtual {v0}, Landroid/app/FragmentManager;->beginTransaction()Landroid/app/FragmentTransaction;

    move-result-object p0

    const/4 p1, 0x0

    invoke-virtual {p0, p1, v2, v1}, Landroid/app/FragmentTransaction;->add(ILandroid/app/Fragment;Ljava/lang/String;)Landroid/app/FragmentTransaction;

    invoke-virtual {p0}, Landroid/app/FragmentTransaction;->commit()I

    :cond_33
    :goto_33
    return-void
.end method

.method public static skipPermissionsDialog(Landroid/app/Activity;)Z
    .registers 5

    sget-boolean v0, Lcom/unity3d/player/PlatformSupport;->MARSHMALLOW_SUPPORT:Z

    const/4 v1, 0x0

    if-nez v0, :cond_6

    return v1

    :cond_6
    :try_start_6
    invoke-virtual {p0}, Landroid/app/Activity;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v0

    invoke-virtual {p0}, Landroid/app/Activity;->getComponentName()Landroid/content/ComponentName;

    move-result-object v2

    const/16 v3, 0x80

    invoke-virtual {v0, v2, v3}, Landroid/content/pm/PackageManager;->getActivityInfo(Landroid/content/ComponentName;I)Landroid/content/pm/ActivityInfo;

    move-result-object v2

    invoke-virtual {p0}, Landroid/app/Activity;->getPackageName()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v0, p0, v3}, Landroid/content/pm/PackageManager;->getApplicationInfo(Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;

    move-result-object p0

    invoke-static {v2}, Lcom/unity3d/player/UnityPermissions;->checkInfoForMetadata(Landroid/content/pm/PackageItemInfo;)Z

    move-result v0

    if-nez v0, :cond_28

    invoke-static {p0}, Lcom/unity3d/player/UnityPermissions;->checkInfoForMetadata(Landroid/content/pm/PackageItemInfo;)Z

    move-result p0
    :try_end_26
    .catch Ljava/lang/Exception; {:try_start_6 .. :try_end_26} :catch_2a

    if-eqz p0, :cond_2a

    :cond_28
    const/4 p0, 0x1

    return p0

    :catch_2a
    :cond_2a
    return v1
.end method
