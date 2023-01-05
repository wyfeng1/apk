.class public Lcom/unity/androidnotifications/UnityNotificationRestartOnBootReceiver;
.super Landroid/content/BroadcastReceiver;
.source "UnityNotificationRestartOnBootReceiver.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .line 0
    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method

.method private static rescheduleSavedNotifications(Landroid/content/Context;)V
    .registers 11

    .line 0
    invoke-static {p0}, Lcom/unity/androidnotifications/UnityNotificationManager;->loadSavedNotifications(Landroid/content/Context;)Ljava/util/List;

    move-result-object v0

    .line 28
    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_8
    :goto_8
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_46

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/app/Notification$Builder;

    .line 29
    invoke-virtual {v1}, Landroid/app/Notification$Builder;->getExtras()Landroid/os/Bundle;

    move-result-object v2

    const-string v3, "repeatInterval"

    const-wide/16 v4, 0x0

    .line 30
    invoke-virtual {v2, v3, v4, v5}, Landroid/os/Bundle;->getLong(Ljava/lang/String;J)J

    move-result-wide v6

    const-string v3, "fireTime"

    .line 31
    invoke-virtual {v2, v3, v4, v5}, Landroid/os/Bundle;->getLong(Ljava/lang/String;J)J

    move-result-wide v2

    .line 32
    invoke-static {}, Ljava/util/Calendar;->getInstance()Ljava/util/Calendar;

    move-result-object v8

    invoke-virtual {v8}, Ljava/util/Calendar;->getTime()Ljava/util/Date;

    move-result-object v8

    .line 33
    new-instance v9, Ljava/util/Date;

    invoke-direct {v9, v2, v3}, Ljava/util/Date;-><init>(J)V

    cmp-long v2, v6, v4

    if-lez v2, :cond_39

    const/4 v2, 0x1

    goto :goto_3a

    :cond_39
    const/4 v2, 0x0

    .line 37
    :goto_3a
    invoke-virtual {v9, v8}, Ljava/util/Date;->after(Ljava/util/Date;)Z

    move-result v3

    if-nez v3, :cond_42

    if-eqz v2, :cond_8

    .line 38
    :cond_42
    invoke-static {v1, p0}, Lcom/unity/androidnotifications/UnityNotificationManager;->scheduleAlarmWithNotification(Landroid/app/Notification$Builder;Landroid/content/Context;)V

    goto :goto_8

    :cond_46
    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .registers 4

    .line 0
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object p2

    const-string v0, "android.intent.action.BOOT_COMPLETED"

    invoke-virtual {v0, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p2

    if-eqz p2, :cond_f

    .line 21
    invoke-static {p1}, Lcom/unity/androidnotifications/UnityNotificationRestartOnBootReceiver;->rescheduleSavedNotifications(Landroid/content/Context;)V

    :cond_f
    return-void
.end method
