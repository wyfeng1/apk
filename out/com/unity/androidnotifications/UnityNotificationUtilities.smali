.class public Lcom/unity/androidnotifications/UnityNotificationUtilities;
.super Ljava/lang/Object;
.source "UnityNotificationUtilities.java"


# static fields
.field private static final INTENT_SERIALIZATION_VERSION:I = 0x0

.field private static final NOTIFICATION_SERIALIZATION_VERSION:I = 0x0

.field private static final SAVED_NOTIFICATION_FALLBACK_KEY:Ljava/lang/String; = "fallback.data"

.field private static final SAVED_NOTIFICATION_PRIMARY_KEY:Ljava/lang/String; = "data"

.field private static final UNITY_MAGIC_NUMBER:[B

.field private static final UNITY_MAGIC_NUMBER_PARCELLED:[B


# direct methods
.method static constructor <clinit>()V
    .registers 2

    const/4 v0, 0x4

    new-array v1, v0, [B

    .line 0
    fill-array-data v1, :array_10

    sput-object v1, Lcom/unity/androidnotifications/UnityNotificationUtilities;->UNITY_MAGIC_NUMBER:[B

    .line 37
    new-array v0, v0, [B

    fill-array-data v0, :array_16

    sput-object v0, Lcom/unity/androidnotifications/UnityNotificationUtilities;->UNITY_MAGIC_NUMBER_PARCELLED:[B

    return-void

    :array_10
    .array-data 1
        0x55t
        0x4dt
        0x4et
        0x4et
    .end array-data

    :array_16
    .array-data 1
        0x55t
        0x4dt
        0x4et
        0x50t
    .end array-data
.end method

.method public constructor <init>()V
    .registers 1

    .line 0
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method protected static deserializeNotification(Landroid/content/Context;Landroid/content/SharedPreferences;)Ljava/lang/Object;
    .registers 6

    const-string v0, "data"

    const-string v1, ""

    .line 0
    invoke-interface {p1, v0, v1}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    .line 200
    move-result-object v0

    const/4 v2, 0x0

    if-eqz v0, :cond_36

    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v3

    .line 202
    if-gtz v3, :cond_12

    goto :goto_36

    :cond_12
    const/4 v3, 0x0

    .line 203
    invoke-static {v0, v3}, Landroid/util/Base64;->decode(Ljava/lang/String;I)[B

    move-result-object v0

    invoke-static {p0, v0}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->deserializeNotification(Landroid/content/Context;[B)Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_1e

    .line 206
    return-object v0

    :cond_1e
    const-string v0, "fallback.data"

    invoke-interface {p1, v0, v1}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    .line 207
    move-result-object p1

    if-eqz p1, :cond_36

    invoke-virtual {p1}, Ljava/lang/String;->length()I

    .line 209
    move-result v0

    if-gtz v0, :cond_2d

    goto :goto_36

    .line 210
    :cond_2d
    invoke-static {p1, v3}, Landroid/util/Base64;->decode(Ljava/lang/String;I)[B

    move-result-object p1

    invoke-static {p0, p1}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->deserializeNotification(Landroid/content/Context;[B)Ljava/lang/Object;

    move-result-object p0

    return-object p0

    :cond_36
    :goto_36
    return-object v2
.end method

.method private static deserializeNotification(Landroid/content/Context;[B)Ljava/lang/Object;
    .registers 4

    .line 0
    new-instance p0, Ljava/io/ByteArrayInputStream;

    invoke-direct {p0, p1}, Ljava/io/ByteArrayInputStream;-><init>([B)V

    .line 218
    new-instance v0, Ljava/io/DataInputStream;

    invoke-direct {v0, p0}, Ljava/io/DataInputStream;-><init>(Ljava/io/InputStream;)V

    .line 219
    invoke-static {v0}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->deserializeNotificationParcelable(Ljava/io/DataInputStream;)Landroid/app/Notification;

    move-result-object v1

    if-eqz v1, :cond_11

    return-object v1

    .line 222
    :cond_11
    invoke-virtual {p0}, Ljava/io/ByteArrayInputStream;->reset()V

    .line 223
    invoke-static {v0}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->deserializeNotificationCustom(Ljava/io/DataInputStream;)Landroid/app/Notification$Builder;

    move-result-object p0

    if-nez p0, :cond_1e

    .line 225
    invoke-static {p1}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->deserializedFromOldIntent([B)Landroid/app/Notification$Builder;

    move-result-object p0

    :cond_1e
    return-object p0
.end method

.method private static deserializeNotificationCustom(Ljava/io/DataInputStream;)Landroid/app/Notification$Builder;
    .registers 40

    const-string v1, "Failed to deserialize notification"

    const-string v2, "UnityNotifications"

    const/4 v3, 0x0

    .line 0
    :try_start_5
    sget-object v0, Lcom/unity/androidnotifications/UnityNotificationUtilities;->UNITY_MAGIC_NUMBER:[B

    move-object/from16 v4, p0

    invoke-static {v4, v0}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->readAndCheckMagicNumber(Ljava/io/DataInputStream;[B)Z

    .line 270
    move-result v0

    if-nez v0, :cond_10

    return-object v3

    :cond_10
    invoke-virtual/range {p0 .. p0}, Ljava/io/DataInputStream;->readInt()I

    move-result v0
    :try_end_14
    .catch Ljava/lang/Exception; {:try_start_5 .. :try_end_14} :catch_1cd
    .catch Ljava/lang/OutOfMemoryError; {:try_start_5 .. :try_end_14} :catch_1c3

    if-ltz v0, :cond_1c1

    .line 281
    if-lez v0, :cond_1a

    goto/16 :goto_1c1

    :cond_1a
    :try_start_1a
    invoke-static/range {p0 .. p0}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->deserializeParcelable(Ljava/io/DataInputStream;)Landroid/os/Parcelable;

    move-result-object v0

    check-cast v0, Landroid/os/Bundle;
    :try_end_20
    .catch Ljava/lang/ClassCastException; {:try_start_1a .. :try_end_20} :catch_21
    .catch Ljava/lang/Exception; {:try_start_1a .. :try_end_20} :catch_1cd
    .catch Ljava/lang/OutOfMemoryError; {:try_start_1a .. :try_end_20} :catch_1c3

    .line 283
    goto :goto_28

    :catch_21
    move-exception v0

    :try_start_22
    const-string v5, "Unexpect type of deserialized object"

    invoke-static {v2, v5, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    :try_end_27
    .catch Ljava/lang/Exception; {:try_start_22 .. :try_end_27} :catch_1cd
    .catch Ljava/lang/OutOfMemoryError; {:try_start_22 .. :try_end_27} :catch_1c3

    move-object v0, v3

    :goto_28
    const-string v5, "data"

    const-string v6, "repeatInterval"

    const-string v7, "fireTime"

    const-string v8, "largeIcon"

    const-string v9, "smallIcon"

    .line 288
    const-wide/16 v10, -0x1

    const/4 v12, 0x0

    if-nez v0, :cond_7a

    .line 289
    :try_start_37
    invoke-virtual/range {p0 .. p0}, Ljava/io/DataInputStream;->readInt()I

    .line 290
    move-result v13

    invoke-static/range {p0 .. p0}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->deserializeString(Ljava/io/DataInputStream;)Ljava/lang/String;

    .line 291
    move-result-object v14

    invoke-static/range {p0 .. p0}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->deserializeString(Ljava/io/DataInputStream;)Ljava/lang/String;

    .line 292
    move-result-object v15

    invoke-static/range {p0 .. p0}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->deserializeString(Ljava/io/DataInputStream;)Ljava/lang/String;

    .line 293
    move-result-object v16

    invoke-static/range {p0 .. p0}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->deserializeString(Ljava/io/DataInputStream;)Ljava/lang/String;

    .line 294
    move-result-object v17

    invoke-virtual/range {p0 .. p0}, Ljava/io/DataInputStream;->readLong()J

    .line 295
    move-result-wide v18

    invoke-virtual/range {p0 .. p0}, Ljava/io/DataInputStream;->readLong()J

    .line 296
    move-result-wide v20

    invoke-static/range {p0 .. p0}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->deserializeString(Ljava/io/DataInputStream;)Ljava/lang/String;

    .line 297
    move-result-object v22

    invoke-virtual/range {p0 .. p0}, Ljava/io/DataInputStream;->readBoolean()Z

    .line 298
    move-result v23

    invoke-virtual/range {p0 .. p0}, Ljava/io/DataInputStream;->readBoolean()Z

    move-result v24

    invoke-static/range {p0 .. p0}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->deserializeString(Ljava/io/DataInputStream;)Ljava/lang/String;

    move-result-object v25

    move-object/from16 v3, v16

    move-object/from16 v12, v17

    move-wide/from16 v10, v18

    move/from16 v26, v23

    move-object/from16 v4, v25

    move-object/from16 v19, v1

    move-object/from16 v23, v15

    move-wide/from16 v37, v20

    move-object/from16 v20, v2

    move-wide/from16 v1, v37

    .line 300
    move-object/from16 v21, v22

    goto :goto_cb

    :cond_7a
    const-string v13, "android.title"

    invoke-virtual {v0, v13}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    .line 301
    move-result-object v14

    const-string v13, "android.text"

    .line 302
    invoke-virtual {v0, v13}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    .line 303
    move-result-object v15

    invoke-virtual {v0, v9}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    .line 304
    move-result-object v16

    invoke-virtual {v0, v8}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    .line 305
    move-result-object v17

    invoke-virtual {v0, v7, v10, v11}, Landroid/os/Bundle;->getLong(Ljava/lang/String;J)J

    .line 306
    move-result-wide v18

    invoke-virtual {v0, v6, v10, v11}, Landroid/os/Bundle;->getLong(Ljava/lang/String;J)J

    move-result-wide v20

    sget v13, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v3, 0x15

    if-ge v13, v3, :cond_9e

    const/4 v3, 0x0

    goto :goto_a4

    :cond_9e
    const-string v3, "android.bigText"

    invoke-virtual {v0, v3}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    .line 307
    move-result-object v3

    :goto_a4
    const-string v13, "android.showChronometer"

    invoke-virtual {v0, v13, v12}, Landroid/os/Bundle;->getBoolean(Ljava/lang/String;Z)Z

    .line 308
    move-result v23

    const-string v13, "android.showWhen"

    .line 309
    invoke-virtual {v0, v13, v12}, Landroid/os/Bundle;->getBoolean(Ljava/lang/String;Z)Z

    move-result v24

    invoke-virtual {v0, v5}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v25
    :try_end_b4
    .catch Ljava/lang/Exception; {:try_start_37 .. :try_end_b4} :catch_1cd
    .catch Ljava/lang/OutOfMemoryError; {:try_start_37 .. :try_end_b4} :catch_1c3

    move-object/from16 v12, v17

    move-wide/from16 v10, v18

    move/from16 v26, v23

    move-object/from16 v4, v25

    const/4 v13, 0x0

    move-object/from16 v19, v1

    move-object/from16 v23, v15

    move-wide/from16 v37, v20

    move-object/from16 v20, v2

    move-object/from16 v21, v3

    .line 312
    move-object/from16 v3, v16

    move-wide/from16 v1, v37

    .line 313
    :goto_cb
    :try_start_cb
    invoke-static/range {p0 .. p0}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->deserializeString(Ljava/io/DataInputStream;)Ljava/lang/String;

    move-result-object v15

    invoke-virtual/range {p0 .. p0}, Ljava/io/DataInputStream;->readBoolean()Z

    .line 316
    move-result v25

    if-eqz v25, :cond_de

    invoke-virtual/range {p0 .. p0}, Ljava/io/DataInputStream;->readInt()I

    move-result v16

    move/from16 v27, v16

    move-object/from16 v16, v14

    .line 317
    goto :goto_e2

    :cond_de
    move-object/from16 v16, v14

    const/16 v27, 0x0

    :goto_e2
    invoke-virtual/range {p0 .. p0}, Ljava/io/DataInputStream;->readInt()I

    .line 318
    move-result v14

    move/from16 v28, v14

    invoke-virtual/range {p0 .. p0}, Ljava/io/DataInputStream;->readBoolean()Z

    .line 319
    move-result v14

    move/from16 v29, v14

    invoke-static/range {p0 .. p0}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->deserializeString(Ljava/io/DataInputStream;)Ljava/lang/String;

    .line 320
    move-result-object v14

    move-object/from16 v30, v14

    invoke-virtual/range {p0 .. p0}, Ljava/io/DataInputStream;->readBoolean()Z

    .line 321
    move-result v14

    move/from16 v31, v14

    invoke-virtual/range {p0 .. p0}, Ljava/io/DataInputStream;->readInt()I

    .line 322
    move-result v14

    move/from16 v32, v14

    invoke-static/range {p0 .. p0}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->deserializeString(Ljava/io/DataInputStream;)Ljava/lang/String;

    .line 323
    move-result-object v14

    if-eqz v24, :cond_10b

    invoke-virtual/range {p0 .. p0}, Ljava/io/DataInputStream;->readLong()J

    move-result-wide v33

    goto :goto_10d

    :cond_10b
    const-wide/16 v33, 0x0

    .line 325
    :goto_10d
    move-wide/from16 v35, v33

    move-object/from16 v33, v14

    sget-object v14, Lcom/unity/androidnotifications/UnityNotificationManager;->mUnityNotificationManager:Lcom/unity/androidnotifications/UnityNotificationManager;

    invoke-virtual {v14, v15}, Lcom/unity/androidnotifications/UnityNotificationManager;->createNotificationBuilder(Ljava/lang/String;)Landroid/app/Notification$Builder;

    .line 327
    move-result-object v14

    if-eqz v0, :cond_11d

    .line 329
    invoke-virtual {v14, v0}, Landroid/app/Notification$Builder;->setExtras(Landroid/os/Bundle;)Landroid/app/Notification$Builder;

    goto :goto_14f

    :cond_11d
    invoke-virtual {v14}, Landroid/app/Notification$Builder;->getExtras()Landroid/os/Bundle;

    move-result-object v0

    .line 330
    const-string v15, "id"

    invoke-virtual {v0, v15, v13}, Landroid/os/Bundle;->putInt(Ljava/lang/String;I)V

    .line 331
    invoke-static {v14, v9, v3}, Lcom/unity/androidnotifications/UnityNotificationManager;->setNotificationIcon(Landroid/app/Notification$Builder;Ljava/lang/String;Ljava/lang/String;)V

    invoke-static {v14, v8, v12}, Lcom/unity/androidnotifications/UnityNotificationManager;->setNotificationIcon(Landroid/app/Notification$Builder;Ljava/lang/String;Ljava/lang/String;)V

    const-wide/16 v8, -0x1

    .line 333
    cmp-long v0, v10, v8

    if-eqz v0, :cond_139

    invoke-virtual {v14}, Landroid/app/Notification$Builder;->getExtras()Landroid/os/Bundle;

    move-result-object v0

    invoke-virtual {v0, v7, v10, v11}, Landroid/os/Bundle;->putLong(Ljava/lang/String;J)V

    :cond_139
    const-wide/16 v7, -0x1

    .line 335
    cmp-long v0, v1, v7

    if-eqz v0, :cond_146

    invoke-virtual {v14}, Landroid/app/Notification$Builder;->getExtras()Landroid/os/Bundle;

    move-result-object v0

    .line 337
    invoke-virtual {v0, v6, v1, v2}, Landroid/os/Bundle;->putLong(Ljava/lang/String;J)V

    :cond_146
    if-eqz v4, :cond_14f

    invoke-virtual {v14}, Landroid/app/Notification$Builder;->getExtras()Landroid/os/Bundle;

    move-result-object v0

    invoke-virtual {v0, v5, v4}, Landroid/os/Bundle;->putString(Ljava/lang/String;Ljava/lang/String;)V

    .line 340
    :cond_14f
    :goto_14f
    if-eqz v16, :cond_156

    move-object/from16 v0, v16

    invoke-virtual {v14, v0}, Landroid/app/Notification$Builder;->setContentTitle(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    .line 342
    :cond_156
    if-eqz v23, :cond_15d

    move-object/from16 v15, v23

    .line 344
    invoke-virtual {v14, v15}, Landroid/app/Notification$Builder;->setContentText(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    :cond_15d
    if-eqz v21, :cond_16d

    new-instance v0, Landroid/app/Notification$BigTextStyle;

    invoke-direct {v0}, Landroid/app/Notification$BigTextStyle;-><init>()V

    move-object/from16 v3, v21

    invoke-virtual {v0, v3}, Landroid/app/Notification$BigTextStyle;->bigText(Ljava/lang/CharSequence;)Landroid/app/Notification$BigTextStyle;

    move-result-object v0

    invoke-virtual {v14, v0}, Landroid/app/Notification$Builder;->setStyle(Landroid/app/Notification$Style;)Landroid/app/Notification$Builder;

    .line 346
    :cond_16d
    if-eqz v25, :cond_174

    move/from16 v0, v27

    invoke-static {v14, v0}, Lcom/unity/androidnotifications/UnityNotificationManager;->setNotificationColor(Landroid/app/Notification$Builder;I)V

    .line 348
    :cond_174
    if-ltz v28, :cond_17b

    move/from16 v0, v28

    .line 349
    invoke-virtual {v14, v0}, Landroid/app/Notification$Builder;->setNumber(I)Landroid/app/Notification$Builder;

    :cond_17b
    move/from16 v0, v29

    .line 350
    invoke-virtual {v14, v0}, Landroid/app/Notification$Builder;->setAutoCancel(Z)Landroid/app/Notification$Builder;

    move/from16 v0, v26

    .line 351
    invoke-static {v14, v0}, Lcom/unity/androidnotifications/UnityNotificationManager;->setNotificationUsesChronometer(Landroid/app/Notification$Builder;Z)V

    if-eqz v30, :cond_192

    invoke-virtual/range {v30 .. v30}, Ljava/lang/String;->length()I

    .line 352
    move-result v0

    if-lez v0, :cond_192

    move-object/from16 v0, v30

    .line 353
    invoke-virtual {v14, v0}, Landroid/app/Notification$Builder;->setGroup(Ljava/lang/String;)Landroid/app/Notification$Builder;

    :cond_192
    move/from16 v0, v31

    .line 354
    invoke-virtual {v14, v0}, Landroid/app/Notification$Builder;->setGroupSummary(Z)Landroid/app/Notification$Builder;

    move/from16 v0, v32

    .line 355
    invoke-static {v14, v0}, Lcom/unity/androidnotifications/UnityNotificationManager;->setNotificationGroupAlertBehavior(Landroid/app/Notification$Builder;I)V

    if-eqz v33, :cond_1a9

    invoke-virtual/range {v33 .. v33}, Ljava/lang/String;->length()I

    .line 356
    move-result v0

    if-lez v0, :cond_1a9

    move-object/from16 v0, v33

    invoke-virtual {v14, v0}, Landroid/app/Notification$Builder;->setSortKey(Ljava/lang/String;)Landroid/app/Notification$Builder;

    .line 358
    :cond_1a9
    if-eqz v24, :cond_1b4

    const/4 v0, 0x1

    .line 359
    invoke-virtual {v14, v0}, Landroid/app/Notification$Builder;->setShowWhen(Z)Landroid/app/Notification$Builder;

    move-wide/from16 v0, v35

    invoke-virtual {v14, v0, v1}, Landroid/app/Notification$Builder;->setWhen(J)Landroid/app/Notification$Builder;
    :try_end_1b4
    .catch Ljava/lang/Exception; {:try_start_cb .. :try_end_1b4} :catch_1bb
    .catch Ljava/lang/OutOfMemoryError; {:try_start_cb .. :try_end_1b4} :catch_1b5

    :cond_1b4
    return-object v14

    :catch_1b5
    move-exception v0

    move-object/from16 v2, v19

    move-object/from16 v1, v20

    goto :goto_1c9

    :catch_1bb
    move-exception v0

    move-object/from16 v2, v19

    move-object/from16 v1, v20

    goto :goto_1d3

    :cond_1c1
    :goto_1c1
    move-object v1, v3

    return-object v1

    :catch_1c3
    move-exception v0

    .line 366
    move-object/from16 v37, v2

    move-object v2, v1

    move-object/from16 v1, v37

    :goto_1c9
    invoke-static {v1, v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_1d6

    :catch_1cd
    move-exception v0

    .line 364
    move-object/from16 v37, v2

    move-object v2, v1

    move-object/from16 v1, v37

    :goto_1d3
    invoke-static {v1, v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :goto_1d6
    const/4 v1, 0x0

    return-object v1
.end method

.method private static deserializeNotificationParcelable(Ljava/io/DataInputStream;)Landroid/app/Notification;
    .registers 5

    const-string v0, "Failed to deserialize notification intent"

    const-string v1, "UnityNotifications"

    const/4 v2, 0x0

    .line 0
    :try_start_5
    sget-object v3, Lcom/unity/androidnotifications/UnityNotificationUtilities;->UNITY_MAGIC_NUMBER_PARCELLED:[B

    invoke-static {p0, v3}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->readAndCheckMagicNumber(Ljava/io/DataInputStream;[B)Z

    .line 251
    move-result v3

    if-nez v3, :cond_e

    return-object v2

    :cond_e
    invoke-virtual {p0}, Ljava/io/DataInputStream;->readInt()I

    move-result v3

    .line 254
    if-ltz v3, :cond_26

    if-lez v3, :cond_17

    goto :goto_26

    :cond_17
    invoke-static {p0}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->deserializeParcelable(Ljava/io/DataInputStream;)Landroid/os/Parcelable;

    .line 255
    move-result-object p0

    check-cast p0, Landroid/content/Intent;

    const-string v3, "unityNotification"

    invoke-virtual {p0, v3}, Landroid/content/Intent;->getParcelableExtra(Ljava/lang/String;)Landroid/os/Parcelable;

    move-result-object p0

    .line 260
    check-cast p0, Landroid/app/Notification;
    :try_end_25
    .catch Ljava/lang/Exception; {:try_start_5 .. :try_end_25} :catch_2c
    .catch Ljava/lang/OutOfMemoryError; {:try_start_5 .. :try_end_25} :catch_27

    return-object p0

    :cond_26
    :goto_26
    return-object v2

    :catch_27
    move-exception p0

    .line 258
    invoke-static {v1, v0, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_30

    :catch_2c
    move-exception p0

    invoke-static {v1, v0, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :goto_30
    return-object v2
.end method

.method private static deserializeParcelable(Ljava/io/DataInputStream;)Landroid/os/Parcelable;
    .registers 7
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T::",
            "Landroid/os/Parcelable;",
            ">(",
            "Ljava/io/DataInputStream;",
            ")TT;"
        }
    .end annotation

    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    const-string v0, "Failed to deserialize parcelable"

    const-string v1, "UnityNotifications"

    .line 0
    invoke-virtual {p0}, Ljava/io/DataInputStream;->readInt()I

    move-result v2

    .line 450
    const/4 v3, 0x0

    if-gtz v2, :cond_c

    .line 451
    return-object v3

    :cond_c
    new-array v4, v2, [B

    invoke-virtual {p0, v4}, Ljava/io/DataInputStream;->read([B)I

    .line 456
    move-result p0

    if-ne p0, v2, :cond_41

    :try_start_14
    invoke-static {}, Landroid/os/Parcel;->obtain()Landroid/os/Parcel;

    .line 457
    move-result-object p0

    .line 458
    const/4 v5, 0x0

    invoke-virtual {p0, v4, v5, v2}, Landroid/os/Parcel;->unmarshall([BII)V

    .line 459
    invoke-virtual {p0, v5}, Landroid/os/Parcel;->setDataPosition(I)V

    const-class v2, Lcom/unity/androidnotifications/UnityNotificationUtilities;

    invoke-virtual {v2}, Ljava/lang/Class;->getClassLoader()Ljava/lang/ClassLoader;

    move-result-object v2

    invoke-virtual {p0, v2}, Landroid/os/Parcel;->readParcelable(Ljava/lang/ClassLoader;)Landroid/os/Parcelable;

    .line 460
    move-result-object v2

    check-cast v2, Landroid/os/Bundle;

    invoke-virtual {p0}, Landroid/os/Parcel;->recycle()V

    .line 462
    if-eqz v2, :cond_40

    const-string p0, "obj"

    invoke-virtual {v2, p0}, Landroid/os/Bundle;->getParcelable(Ljava/lang/String;)Landroid/os/Parcelable;

    .line 467
    move-result-object p0
    :try_end_36
    .catch Ljava/lang/Exception; {:try_start_14 .. :try_end_36} :catch_3c
    .catch Ljava/lang/OutOfMemoryError; {:try_start_14 .. :try_end_36} :catch_37

    return-object p0

    :catch_37
    move-exception p0

    invoke-static {v1, v0, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 465
    goto :goto_40

    :catch_3c
    move-exception p0

    .line 453
    invoke-static {v1, v0, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :cond_40
    :goto_40
    return-object v3

    :cond_41
    new-instance p0, Ljava/io/IOException;

    const-string v0, "Insufficient amount of bytes read"

    invoke-direct {p0, v0}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw p0
.end method

.method private static deserializeString(Ljava/io/DataInputStream;)Ljava/lang/String;
    .registers 3
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 0
    invoke-virtual {p0}, Ljava/io/DataInputStream;->readInt()I

    move-result v0

    if-gtz v0, :cond_8

    const/4 p0, 0x0

    return-object p0

    .line 439
    :cond_8
    new-array v1, v0, [B

    .line 440
    invoke-virtual {p0, v1}, Ljava/io/DataInputStream;->read([B)I

    move-result p0

    if-ne p0, v0, :cond_18

    .line 443
    new-instance p0, Ljava/lang/String;

    sget-object v0, Ljava/nio/charset/StandardCharsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-direct {p0, v1, v0}, Ljava/lang/String;-><init>([BLjava/nio/charset/Charset;)V

    return-object p0

    .line 442
    :cond_18
    new-instance p0, Ljava/io/IOException;

    const-string v0, "Insufficient amount of bytes read"

    invoke-direct {p0, v0}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw p0
.end method

.method private static deserializedFromOldIntent([B)Landroid/app/Notification$Builder;
    .registers 29

    move-object/from16 v0, p0

    const-string v1, "data"

    .line 375
    const-string v2, "repeatInterval"

    const-string v3, "fireTime"

    const-string v4, "id"

    .line 376
    const-string v5, "Failed to deserialize old style notification"

    .line 377
    const-string v6, "UnityNotifications"

    .line 0
    :try_start_e
    invoke-static {}, Landroid/os/Parcel;->obtain()Landroid/os/Parcel;

    .line 378
    move-result-object v7

    array-length v8, v0

    const/4 v9, 0x0

    invoke-virtual {v7, v0, v9, v8}, Landroid/os/Parcel;->unmarshall([BII)V

    .line 380
    invoke-virtual {v7, v9}, Landroid/os/Parcel;->setDataPosition(I)V

    new-instance v0, Landroid/os/Bundle;

    .line 381
    invoke-direct {v0}, Landroid/os/Bundle;-><init>()V

    invoke-virtual {v0, v7}, Landroid/os/Bundle;->readFromParcel(Landroid/os/Parcel;)V

    .line 382
    const/4 v7, -0x1

    invoke-virtual {v0, v4, v7}, Landroid/os/Bundle;->getInt(Ljava/lang/String;I)I

    move-result v8

    .line 383
    const-string v10, "channelID"

    invoke-virtual {v0, v10}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v10

    .line 384
    const-string v11, "textTitle"

    invoke-virtual {v0, v11}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v11

    .line 385
    const-string v12, "textContent"

    invoke-virtual {v0, v12}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v12

    .line 386
    const-string v13, "smallIconStr"

    invoke-virtual {v0, v13}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v13

    const-string v14, "autoCancel"

    invoke-virtual {v0, v14, v9}, Landroid/os/Bundle;->getBoolean(Ljava/lang/String;Z)Z

    .line 387
    move-result v14

    const-string v15, "usesChronometer"

    .line 388
    invoke-virtual {v0, v15, v9}, Landroid/os/Bundle;->getBoolean(Ljava/lang/String;Z)Z

    move-result v15

    move-object/from16 p0, v10

    const-wide/16 v9, -0x1

    .line 389
    move/from16 v17, v8

    invoke-virtual {v0, v3, v9, v10}, Landroid/os/Bundle;->getLong(Ljava/lang/String;J)J

    move-result-wide v7

    invoke-virtual {v0, v2, v9, v10}, Landroid/os/Bundle;->getLong(Ljava/lang/String;J)J

    move-result-wide v9
    :try_end_59
    .catch Ljava/lang/Exception; {:try_start_e .. :try_end_59} :catch_158
    .catch Ljava/lang/OutOfMemoryError; {:try_start_e .. :try_end_59} :catch_151

    move-object/from16 v18, v5

    .line 390
    :try_start_5b
    const-string v5, "largeIconStr"

    invoke-virtual {v0, v5}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5
    :try_end_61
    .catch Ljava/lang/Exception; {:try_start_5b .. :try_end_61} :catch_14c
    .catch Ljava/lang/OutOfMemoryError; {:try_start_5b .. :try_end_61} :catch_147

    move-object/from16 v19, v6

    .line 391
    :try_start_63
    const-string v6, "style"

    move-object/from16 v20, v5

    const/4 v5, -0x1

    invoke-virtual {v0, v6, v5}, Landroid/os/Bundle;->getInt(Ljava/lang/String;I)I

    .line 392
    move-result v6

    const-string v5, "color"

    move/from16 v21, v6

    .line 393
    const/4 v6, 0x0

    invoke-virtual {v0, v5, v6}, Landroid/os/Bundle;->getInt(Ljava/lang/String;I)I

    move-result v5

    move/from16 v22, v5

    .line 394
    const-string v5, "number"

    invoke-virtual {v0, v5, v6}, Landroid/os/Bundle;->getInt(Ljava/lang/String;I)I

    move-result v5

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v6

    move-object/from16 v23, v1

    .line 395
    const-string v1, "group"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    .line 396
    move-result-object v1

    move-object/from16 v24, v1

    const-string v1, "groupSummary"

    move-object/from16 v25, v6

    const/4 v6, 0x0

    invoke-virtual {v0, v1, v6}, Landroid/os/Bundle;->getBoolean(Ljava/lang/String;Z)Z

    .line 397
    move-result v1

    const-string v6, "sortKey"

    invoke-virtual {v0, v6}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v6

    move-object/from16 v26, v6

    .line 398
    const-string v6, "groupAlertBehaviour"

    move/from16 v27, v1

    .line 400
    const/4 v1, -0x1

    invoke-virtual {v0, v6, v1}, Landroid/os/Bundle;->getInt(Ljava/lang/String;I)I

    move-result v1

    const-string v6, "showTimestamp"

    move/from16 v16, v1

    .line 401
    const/4 v1, 0x0

    invoke-virtual {v0, v6, v1}, Landroid/os/Bundle;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    sget-object v1, Lcom/unity/androidnotifications/UnityNotificationManager;->mUnityNotificationManager:Lcom/unity/androidnotifications/UnityNotificationManager;

    move-object/from16 v6, p0

    invoke-virtual {v1, v6}, Lcom/unity/androidnotifications/UnityNotificationManager;->createNotificationBuilder(Ljava/lang/String;)Landroid/app/Notification$Builder;

    .line 402
    move-result-object v1

    .line 403
    invoke-virtual {v1}, Landroid/app/Notification$Builder;->getExtras()Landroid/os/Bundle;

    move-result-object v6

    move/from16 p0, v0

    .line 404
    move/from16 v0, v17

    .line 405
    invoke-virtual {v6, v4, v0}, Landroid/os/Bundle;->putInt(Ljava/lang/String;I)V

    .line 406
    invoke-virtual {v1, v11}, Landroid/app/Notification$Builder;->setContentTitle(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    .line 407
    invoke-virtual {v1, v12}, Landroid/app/Notification$Builder;->setContentText(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    const-string v0, "smallIcon"

    invoke-static {v1, v0, v13}, Lcom/unity/androidnotifications/UnityNotificationManager;->setNotificationIcon(Landroid/app/Notification$Builder;Ljava/lang/String;Ljava/lang/String;)V

    .line 408
    invoke-virtual {v1, v14}, Landroid/app/Notification$Builder;->setAutoCancel(Z)Landroid/app/Notification$Builder;

    invoke-virtual {v1, v15}, Landroid/app/Notification$Builder;->setUsesChronometer(Z)Landroid/app/Notification$Builder;

    invoke-virtual {v1}, Landroid/app/Notification$Builder;->getExtras()Landroid/os/Bundle;

    move-result-object v0

    .line 409
    invoke-virtual {v0, v3, v7, v8}, Landroid/os/Bundle;->putLong(Ljava/lang/String;J)V

    invoke-virtual {v1}, Landroid/app/Notification$Builder;->getExtras()Landroid/os/Bundle;

    move-result-object v0

    invoke-virtual {v0, v2, v9, v10}, Landroid/os/Bundle;->putLong(Ljava/lang/String;J)V

    .line 411
    const-string v0, "largeIcon"

    move-object/from16 v2, v20

    invoke-static {v1, v0, v2}, Lcom/unity/androidnotifications/UnityNotificationManager;->setNotificationIcon(Landroid/app/Notification$Builder;Ljava/lang/String;Ljava/lang/String;)V

    const/4 v0, 0x2

    move/from16 v2, v21

    if-ne v2, v0, :cond_f8

    new-instance v0, Landroid/app/Notification$BigTextStyle;

    .line 413
    invoke-direct {v0}, Landroid/app/Notification$BigTextStyle;-><init>()V

    invoke-virtual {v0, v12}, Landroid/app/Notification$BigTextStyle;->bigText(Ljava/lang/CharSequence;)Landroid/app/Notification$BigTextStyle;

    .line 415
    move-result-object v0

    invoke-virtual {v1, v0}, Landroid/app/Notification$Builder;->setStyle(Landroid/app/Notification$Style;)Landroid/app/Notification$Builder;

    .line 417
    :cond_f8
    if-eqz v22, :cond_ff

    move/from16 v0, v22

    invoke-static {v1, v0}, Lcom/unity/androidnotifications/UnityNotificationManager;->setNotificationColor(Landroid/app/Notification$Builder;I)V

    :cond_ff
    if-ltz v5, :cond_104

    invoke-virtual {v1, v5}, Landroid/app/Notification$Builder;->setNumber(I)Landroid/app/Notification$Builder;

    :cond_104
    if-eqz v25, :cond_111

    .line 418
    invoke-virtual {v1}, Landroid/app/Notification$Builder;->getExtras()Landroid/os/Bundle;

    move-result-object v0

    move-object/from16 v3, v23

    move-object/from16 v2, v25

    .line 419
    invoke-virtual {v0, v3, v2}, Landroid/os/Bundle;->putString(Ljava/lang/String;Ljava/lang/String;)V

    :cond_111
    if-eqz v24, :cond_11e

    .line 420
    invoke-virtual/range {v24 .. v24}, Ljava/lang/String;->length()I

    move-result v0

    .line 421
    if-lez v0, :cond_11e

    move-object/from16 v0, v24

    invoke-virtual {v1, v0}, Landroid/app/Notification$Builder;->setGroup(Ljava/lang/String;)Landroid/app/Notification$Builder;

    :cond_11e
    move/from16 v0, v27

    .line 422
    invoke-virtual {v1, v0}, Landroid/app/Notification$Builder;->setGroupSummary(Z)Landroid/app/Notification$Builder;

    if-eqz v26, :cond_130

    .line 423
    invoke-virtual/range {v26 .. v26}, Ljava/lang/String;->length()I

    move-result v0

    .line 424
    if-lez v0, :cond_130

    move-object/from16 v0, v26

    invoke-virtual {v1, v0}, Landroid/app/Notification$Builder;->setSortKey(Ljava/lang/String;)Landroid/app/Notification$Builder;

    :cond_130
    move/from16 v0, v16

    invoke-static {v1, v0}, Lcom/unity/androidnotifications/UnityNotificationManager;->setNotificationGroupAlertBehavior(Landroid/app/Notification$Builder;I)V

    move/from16 v0, p0

    invoke-virtual {v1, v0}, Landroid/app/Notification$Builder;->setShowWhen(Z)Landroid/app/Notification$Builder;
    :try_end_13a
    .catch Ljava/lang/Exception; {:try_start_63 .. :try_end_13a} :catch_141
    .catch Ljava/lang/OutOfMemoryError; {:try_start_63 .. :try_end_13a} :catch_13b

    return-object v1

    :catch_13b
    move-exception v0

    move-object/from16 v2, v18

    move-object/from16 v1, v19

    goto :goto_154

    :catch_141
    move-exception v0

    move-object/from16 v2, v18

    move-object/from16 v1, v19

    .line 429
    goto :goto_15b

    :catch_147
    move-exception v0

    move-object v1, v6

    move-object/from16 v2, v18

    goto :goto_154

    :catch_14c
    move-exception v0

    .line 427
    move-object v1, v6

    move-object/from16 v2, v18

    goto :goto_15b

    :catch_151
    move-exception v0

    move-object v2, v5

    move-object v1, v6

    :goto_154
    invoke-static {v1, v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_15e

    :catch_158
    move-exception v0

    move-object v2, v5

    move-object v1, v6

    :goto_15b
    invoke-static {v1, v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :goto_15e
    const/4 v0, 0x0

    return-object v0
.end method

.method protected static findResourceIdInContextByName(Landroid/content/Context;Ljava/lang/String;)I
    .registers 6

    const/4 v0, 0x0

    if-nez p1, :cond_4

    return v0

    .line 0
    :cond_4
    :try_start_4
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    .line 51
    if-eqz v1, :cond_22

    const-string v2, "mipmap"

    invoke-virtual {p0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v1, p1, v2, v3}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v2

    .line 53
    if-nez v2, :cond_21

    const-string v2, "drawable"

    invoke-virtual {p0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v1, p1, v2, p0}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p0
    :try_end_20
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_4 .. :try_end_20} :catch_22

    return p0

    :cond_21
    return v2

    :catch_22
    :cond_22
    return v0
.end method

.method protected static getOpenAppActivity(Landroid/content/Context;Z)Ljava/lang/Class;
    .registers 7
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/content/Context;",
            "Z)",
            "Ljava/lang/Class<",
            "*>;"
        }
    .end annotation

    const/4 v0, 0x0

    .line 0
    :try_start_1
    invoke-virtual {p0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v1

    invoke-virtual {p0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v2

    const/16 v3, 0x80

    invoke-virtual {v1, v2, v3}, Landroid/content/pm/PackageManager;->getApplicationInfo(Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;

    move-result-object v1
    :try_end_f
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_1 .. :try_end_f} :catch_10

    goto :goto_15

    .line 478
    :catch_10
    move-exception v1

    invoke-virtual {v1}, Landroid/content/pm/PackageManager$NameNotFoundException;->printStackTrace()V

    .line 480
    move-object v1, v0

    :goto_15
    iget-object v1, v1, Landroid/content/pm/ApplicationInfo;->metaData:Landroid/os/Bundle;

    const-string v2, "custom_notification_android_activity"

    .line 485
    invoke-virtual {v1, v2}, Landroid/os/Bundle;->containsKey(Ljava/lang/String;)Z

    move-result v3

    if-eqz v3, :cond_29

    .line 486
    invoke-virtual {v1, v2}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    .line 489
    move-result-object v1

    :try_start_23
    invoke-static {v1}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0
    :try_end_27
    .catch Ljava/lang/ClassNotFoundException; {:try_start_23 .. :try_end_27} :catch_28

    goto :goto_29

    :catch_28
    nop

    :cond_29
    :goto_29
    if-nez v0, :cond_6a

    if-eqz p1, :cond_6a

    const-string p1, "UnityNotifications"

    const-string v1, "No custom_notification_android_activity found, attempting to find app activity class"

    .line 496
    invoke-static {p1, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    const-string v1, "com.unity3d.player.UnityPlayerActivity"

    .line 500
    :try_start_36
    invoke-static {v1}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object p0
    :try_end_3a
    .catch Ljava/lang/ClassNotFoundException; {:try_start_36 .. :try_end_3a} :catch_3b

    return-object p0

    :catch_3b
    const/4 v2, 0x1

    new-array v3, v2, [Ljava/lang/Object;

    const/4 v4, 0x0

    aput-object v1, v3, v4

    const-string v1, "Attempting to find : %s, failed!"

    .line 502
    invoke-static {v1, v3}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    invoke-static {p1, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    new-array v1, v2, [Ljava/lang/Object;

    .line 503
    invoke-virtual {p0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object p0

    aput-object p0, v1, v4

    const-string p0, "%s.UnityPlayerActivity"

    invoke-static {p0, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    .line 505
    move-result-object p0

    :try_start_58
    invoke-static {p0}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object p0
    :try_end_5c
    .catch Ljava/lang/ClassNotFoundException; {:try_start_58 .. :try_end_5c} :catch_5d

    return-object p0

    :catch_5d
    new-array v1, v2, [Ljava/lang/Object;

    aput-object p0, v1, v4

    const-string p0, "Attempting to find class based on package name: %s, failed!"

    .line 507
    invoke-static {p0, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p0

    invoke-static {p1, p0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    :cond_6a
    return-object v0
.end method

.method private static readAndCheckMagicNumber(Ljava/io/DataInputStream;[B)Z
    .registers 6

    const/4 v0, 0x0

    const/4 v1, 0x0

    .line 0
    :goto_2
    :try_start_2
    array-length v2, p1

    .line 234
    if-ge v1, v2, :cond_11

    invoke-virtual {p0}, Ljava/io/DataInputStream;->readByte()B

    .line 235
    move-result v2

    aget-byte v3, p1, v1
    :try_end_b
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_b} :catch_12

    if-eq v2, v3, :cond_e

    goto :goto_12

    :cond_e
    add-int/lit8 v1, v1, 0x1

    goto :goto_2

    :cond_11
    const/4 v0, 0x1

    :catch_12
    :goto_12
    return v0
.end method

.method protected static recoverBuilder(Landroid/content/Context;Landroid/app/Notification;)Landroid/app/Notification$Builder;
    .registers 4

    .line 0
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x18

    if-lt v0, v1, :cond_10

    .line 517
    invoke-static {p0, p1}, Landroid/app/Notification$Builder;->recoverBuilder(Landroid/content/Context;Landroid/app/Notification;)Landroid/app/Notification$Builder;

    move-result-object p0

    .line 519
    iget-object p1, p1, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    invoke-virtual {p0, p1}, Landroid/app/Notification$Builder;->setExtras(Landroid/os/Bundle;)Landroid/app/Notification$Builder;

    return-object p0

    .line 523
    :cond_10
    invoke-static {p0, p1}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->recoverBuilderPreNougat(Landroid/content/Context;Landroid/app/Notification;)Landroid/app/Notification$Builder;

    move-result-object p0

    return-object p0
.end method

.method private static recoverBuilderPreNougat(Landroid/content/Context;Landroid/app/Notification;)Landroid/app/Notification$Builder;
    .registers 9

    .line 0
    iget-object v0, p1, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v1, "channelID"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 529
    invoke-static {p0, v0}, Lcom/unity/androidnotifications/UnityNotificationManager;->createNotificationBuilder(Landroid/content/Context;Ljava/lang/String;)Landroid/app/Notification$Builder;

    move-result-object p0

    .line 530
    iget-object v0, p1, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v1, "smallIcon"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {p0, v1, v0}, Lcom/unity/androidnotifications/UnityNotificationManager;->setNotificationIcon(Landroid/app/Notification$Builder;Ljava/lang/String;Ljava/lang/String;)V

    .line 531
    iget-object v0, p1, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v1, "largeIcon"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_2a

    .line 532
    invoke-virtual {v0}, Ljava/lang/String;->isEmpty()Z

    move-result v2

    if-nez v2, :cond_2a

    .line 533
    invoke-static {p0, v1, v0}, Lcom/unity/androidnotifications/UnityNotificationManager;->setNotificationIcon(Landroid/app/Notification$Builder;Ljava/lang/String;Ljava/lang/String;)V

    .line 534
    :cond_2a
    iget-object v0, p1, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v1, "android.title"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0, v0}, Landroid/app/Notification$Builder;->setContentTitle(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    .line 535
    iget-object v0, p1, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v1, "android.text"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0, v0}, Landroid/app/Notification$Builder;->setContentText(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    .line 536
    iget v0, p1, Landroid/app/Notification;->flags:I

    and-int/lit8 v0, v0, 0x10

    const/4 v1, 0x1

    const/4 v2, 0x0

    if-eqz v0, :cond_4a

    const/4 v0, 0x1

    goto :goto_4b

    :cond_4a
    const/4 v0, 0x0

    :goto_4b
    invoke-virtual {p0, v0}, Landroid/app/Notification$Builder;->setAutoCancel(Z)Landroid/app/Notification$Builder;

    .line 537
    iget v0, p1, Landroid/app/Notification;->number:I

    if-ltz v0, :cond_57

    .line 538
    iget v0, p1, Landroid/app/Notification;->number:I

    invoke-virtual {p0, v0}, Landroid/app/Notification$Builder;->setNumber(I)Landroid/app/Notification$Builder;

    .line 539
    :cond_57
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v3, 0x15

    if-lt v0, v3, :cond_73

    .line 540
    iget-object v0, p1, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v3, "android.bigText"

    invoke-virtual {v0, v3}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_73

    .line 542
    new-instance v3, Landroid/app/Notification$BigTextStyle;

    invoke-direct {v3}, Landroid/app/Notification$BigTextStyle;-><init>()V

    invoke-virtual {v3, v0}, Landroid/app/Notification$BigTextStyle;->bigText(Ljava/lang/CharSequence;)Landroid/app/Notification$BigTextStyle;

    move-result-object v0

    invoke-virtual {p0, v0}, Landroid/app/Notification$Builder;->setStyle(Landroid/app/Notification$Style;)Landroid/app/Notification$Builder;

    .line 545
    :cond_73
    iget-wide v3, p1, Landroid/app/Notification;->when:J

    invoke-virtual {p0, v3, v4}, Landroid/app/Notification$Builder;->setWhen(J)Landroid/app/Notification$Builder;

    .line 546
    invoke-virtual {p1}, Landroid/app/Notification;->getGroup()Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_87

    .line 547
    invoke-virtual {v0}, Ljava/lang/String;->isEmpty()Z

    move-result v3

    if-nez v3, :cond_87

    .line 548
    invoke-virtual {p0, v0}, Landroid/app/Notification$Builder;->setGroup(Ljava/lang/String;)Landroid/app/Notification$Builder;

    .line 549
    :cond_87
    iget v0, p1, Landroid/app/Notification;->flags:I

    and-int/lit16 v0, v0, 0x200

    if-eqz v0, :cond_8e

    goto :goto_8f

    :cond_8e
    const/4 v1, 0x0

    :goto_8f
    invoke-virtual {p0, v1}, Landroid/app/Notification$Builder;->setGroupSummary(Z)Landroid/app/Notification$Builder;

    .line 550
    invoke-virtual {p1}, Landroid/app/Notification;->getSortKey()Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_a1

    .line 551
    invoke-virtual {v0}, Ljava/lang/String;->isEmpty()Z

    move-result v1

    if-nez v1, :cond_a1

    .line 552
    invoke-virtual {p0, v0}, Landroid/app/Notification$Builder;->setSortKey(Ljava/lang/String;)Landroid/app/Notification$Builder;

    .line 553
    :cond_a1
    iget-object v0, p1, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v1, "android.showWhen"

    invoke-virtual {v0, v1, v2}, Landroid/os/Bundle;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    invoke-virtual {p0, v0}, Landroid/app/Notification$Builder;->setShowWhen(Z)Landroid/app/Notification$Builder;

    .line 554
    invoke-static {p1}, Lcom/unity/androidnotifications/UnityNotificationManager;->getNotificationColor(Landroid/app/Notification;)Ljava/lang/Integer;

    move-result-object v0

    if-eqz v0, :cond_b9

    .line 556
    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    invoke-static {p0, v0}, Lcom/unity/androidnotifications/UnityNotificationManager;->setNotificationColor(Landroid/app/Notification$Builder;I)V

    .line 557
    :cond_b9
    iget-object v0, p1, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v1, "android.showChronometer"

    invoke-virtual {v0, v1, v2}, Landroid/os/Bundle;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    invoke-static {p0, v0}, Lcom/unity/androidnotifications/UnityNotificationManager;->setNotificationUsesChronometer(Landroid/app/Notification$Builder;Z)V

    .line 558
    invoke-static {p1}, Lcom/unity/androidnotifications/UnityNotificationManager;->getNotificationGroupAlertBehavior(Landroid/app/Notification;)I

    move-result v0

    invoke-static {p0, v0}, Lcom/unity/androidnotifications/UnityNotificationManager;->setNotificationGroupAlertBehavior(Landroid/app/Notification$Builder;I)V

    .line 560
    invoke-virtual {p0}, Landroid/app/Notification$Builder;->getExtras()Landroid/os/Bundle;

    move-result-object v0

    iget-object v1, p1, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v3, "id"

    invoke-virtual {v1, v3, v2}, Landroid/os/Bundle;->getInt(Ljava/lang/String;I)I

    move-result v1

    invoke-virtual {v0, v3, v1}, Landroid/os/Bundle;->putInt(Ljava/lang/String;I)V

    .line 561
    invoke-virtual {p0}, Landroid/app/Notification$Builder;->getExtras()Landroid/os/Bundle;

    move-result-object v0

    iget-object v1, p1, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v2, "repeatInterval"

    const-wide/16 v3, 0x0

    invoke-virtual {v1, v2, v3, v4}, Landroid/os/Bundle;->getLong(Ljava/lang/String;J)J

    move-result-wide v5

    invoke-virtual {v0, v2, v5, v6}, Landroid/os/Bundle;->putLong(Ljava/lang/String;J)V

    .line 562
    invoke-virtual {p0}, Landroid/app/Notification$Builder;->getExtras()Landroid/os/Bundle;

    move-result-object v0

    iget-object v1, p1, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v2, "fireTime"

    invoke-virtual {v1, v2, v3, v4}, Landroid/os/Bundle;->getLong(Ljava/lang/String;J)J

    move-result-wide v3

    invoke-virtual {v0, v2, v3, v4}, Landroid/os/Bundle;->putLong(Ljava/lang/String;J)V

    .line 563
    iget-object p1, p1, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v0, "data"

    invoke-virtual {p1, v0}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    if-eqz p1, :cond_111

    .line 564
    invoke-virtual {p1}, Ljava/lang/String;->isEmpty()Z

    move-result v1

    if-nez v1, :cond_111

    .line 565
    invoke-virtual {p0}, Landroid/app/Notification$Builder;->getExtras()Landroid/os/Bundle;

    move-result-object v1

    invoke-virtual {v1, v0, p1}, Landroid/os/Bundle;->putString(Ljava/lang/String;Ljava/lang/String;)V

    :cond_111
    return-object p0
.end method

.method protected static serializeNotification(Landroid/content/SharedPreferences;Landroid/app/Notification;)V
    .registers 8

    const/4 v0, 0x0

    .line 0
    :try_start_1
    new-instance v1, Ljava/io/ByteArrayOutputStream;

    invoke-direct {v1}, Ljava/io/ByteArrayOutputStream;-><init>()V

    .line 78
    new-instance v2, Ljava/io/DataOutputStream;

    invoke-direct {v2, v1}, Ljava/io/DataOutputStream;-><init>(Ljava/io/OutputStream;)V

    .line 79
    invoke-static {p1, v2}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->serializeNotificationCustom(Landroid/app/Notification;Ljava/io/DataOutputStream;)Z

    move-result v3

    const/4 v4, 0x0

    if-eqz v3, :cond_1e

    .line 80
    invoke-virtual {v2}, Ljava/io/DataOutputStream;->flush()V

    .line 81
    invoke-virtual {v1}, Ljava/io/ByteArrayOutputStream;->toByteArray()[B

    .line 82
    move-result-object v0

    array-length v3, v0

    invoke-static {v0, v4, v3, v4}, Landroid/util/Base64;->encodeToString([BIII)Ljava/lang/String;

    .line 84
    move-result-object v0

    :cond_1e
    invoke-virtual {v1}, Ljava/io/ByteArrayOutputStream;->reset()V

    .line 85
    new-instance v3, Landroid/content/Intent;

    invoke-direct {v3}, Landroid/content/Intent;-><init>()V

    const-string v5, "unityNotification"

    .line 86
    invoke-virtual {v3, v5, p1}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Landroid/os/Parcelable;)Landroid/content/Intent;

    .line 87
    invoke-static {v3, v2}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->serializeNotificationParcel(Landroid/content/Intent;Ljava/io/DataOutputStream;)Z

    move-result p1

    if-eqz p1, :cond_3e

    .line 88
    invoke-virtual {v2}, Ljava/io/DataOutputStream;->close()V

    .line 89
    invoke-virtual {v1}, Ljava/io/ByteArrayOutputStream;->toByteArray()[B

    .line 90
    move-result-object p1

    array-length v1, p1

    invoke-static {p1, v4, v1, v4}, Landroid/util/Base64;->encodeToString([BIII)Ljava/lang/String;

    move-result-object p1

    goto :goto_3f

    .line 95
    :cond_3e
    move-object p1, v0

    :goto_3f
    invoke-interface {p0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object p0

    invoke-interface {p0}, Landroid/content/SharedPreferences$Editor;->clear()Landroid/content/SharedPreferences$Editor;

    move-result-object p0

    const-string v1, "data"

    .line 96
    invoke-interface {p0, v1, p1}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    const-string p1, "fallback.data"

    .line 97
    invoke-interface {p0, p1, v0}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    .line 98
    invoke-interface {p0}, Landroid/content/SharedPreferences$Editor;->apply()V
    :try_end_54
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_54} :catch_55

    goto :goto_5d

    :catch_55
    move-exception p0

    const-string p1, "UnityNotifications"

    const-string v0, "Failed to serialize notification"

    .line 100
    invoke-static {p1, v0, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :goto_5d
    return-void
.end method

.method private static serializeNotificationCustom(Landroid/app/Notification;Ljava/io/DataOutputStream;)Z
    .registers 11

    const/4 v0, 0x0

    .line 0
    :try_start_1
    sget-object v1, Lcom/unity/androidnotifications/UnityNotificationUtilities;->UNITY_MAGIC_NUMBER:[B

    invoke-virtual {p1, v1}, Ljava/io/DataOutputStream;->write([B)V

    .line 126
    invoke-virtual {p1, v0}, Ljava/io/DataOutputStream;->writeInt(I)V

    .line 129
    iget-object v1, p0, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v2, "android.showWhen"

    invoke-virtual {v1, v2, v0}, Landroid/os/Bundle;->getBoolean(Ljava/lang/String;Z)Z

    .line 130
    move-result v1

    iget-object v2, p0, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    invoke-static {v2}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->serializeParcelable(Landroid/os/Parcelable;)[B

    move-result-object v2

    if-nez v2, :cond_1b

    const/4 v3, 0x0

    .line 131
    goto :goto_1c

    :cond_1b
    array-length v3, v2

    :goto_1c
    invoke-virtual {p1, v3}, Ljava/io/DataOutputStream;->writeInt(I)V

    const/4 v3, 0x0

    if-eqz v2, :cond_2a

    .line 132
    array-length v4, v2

    if-lez v4, :cond_2a

    .line 133
    invoke-virtual {p1, v2}, Ljava/io/DataOutputStream;->write([B)V

    goto/16 :goto_a5

    .line 136
    :cond_2a
    iget-object v2, p0, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v4, "id"

    invoke-virtual {v2, v4}, Landroid/os/Bundle;->getInt(Ljava/lang/String;)I

    move-result v2

    invoke-virtual {p1, v2}, Ljava/io/DataOutputStream;->writeInt(I)V

    .line 137
    iget-object v2, p0, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v4, "android.title"

    invoke-virtual {v2, v4}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    invoke-static {p1, v2}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->serializeString(Ljava/io/DataOutputStream;Ljava/lang/String;)V

    .line 138
    iget-object v2, p0, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v4, "android.text"

    invoke-virtual {v2, v4}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    invoke-static {p1, v2}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->serializeString(Ljava/io/DataOutputStream;Ljava/lang/String;)V

    .line 139
    iget-object v2, p0, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v4, "smallIcon"

    invoke-virtual {v2, v4}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    invoke-static {p1, v2}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->serializeString(Ljava/io/DataOutputStream;Ljava/lang/String;)V

    .line 140
    iget-object v2, p0, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v4, "largeIcon"

    invoke-virtual {v2, v4}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    invoke-static {p1, v2}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->serializeString(Ljava/io/DataOutputStream;Ljava/lang/String;)V

    .line 141
    iget-object v2, p0, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v4, "fireTime"

    const-wide/16 v5, -0x1

    invoke-virtual {v2, v4, v5, v6}, Landroid/os/Bundle;->getLong(Ljava/lang/String;J)J

    move-result-wide v7

    invoke-virtual {p1, v7, v8}, Ljava/io/DataOutputStream;->writeLong(J)V

    .line 142
    iget-object v2, p0, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v4, "repeatInterval"

    invoke-virtual {v2, v4, v5, v6}, Landroid/os/Bundle;->getLong(Ljava/lang/String;J)J

    move-result-wide v4

    invoke-virtual {p1, v4, v5}, Ljava/io/DataOutputStream;->writeLong(J)V

    .line 143
    sget v2, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v4, 0x15

    if-ge v2, v4, :cond_81

    move-object v2, v3

    goto :goto_89

    :cond_81
    iget-object v2, p0, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v4, "android.bigText"

    invoke-virtual {v2, v4}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    :goto_89
    invoke-static {p1, v2}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->serializeString(Ljava/io/DataOutputStream;Ljava/lang/String;)V

    .line 144
    iget-object v2, p0, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v4, "android.showChronometer"

    invoke-virtual {v2, v4, v0}, Landroid/os/Bundle;->getBoolean(Ljava/lang/String;Z)Z

    move-result v2

    invoke-virtual {p1, v2}, Ljava/io/DataOutputStream;->writeBoolean(Z)V

    .line 145
    invoke-virtual {p1, v1}, Ljava/io/DataOutputStream;->writeBoolean(Z)V

    .line 146
    iget-object v2, p0, Landroid/app/Notification;->extras:Landroid/os/Bundle;

    const-string v4, "data"

    invoke-virtual {v2, v4}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    invoke-static {p1, v2}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->serializeString(Ljava/io/DataOutputStream;Ljava/lang/String;)V

    .line 149
    :goto_a5
    sget v2, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v4, 0x1a

    if-ge v2, v4, :cond_ac

    goto :goto_b0

    :cond_ac
    invoke-virtual {p0}, Landroid/app/Notification;->getChannelId()Ljava/lang/String;

    move-result-object v3

    :goto_b0
    invoke-static {p1, v3}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->serializeString(Ljava/io/DataOutputStream;Ljava/lang/String;)V

    .line 150
    invoke-static {p0}, Lcom/unity/androidnotifications/UnityNotificationManager;->getNotificationColor(Landroid/app/Notification;)Ljava/lang/Integer;

    move-result-object v2

    const/4 v3, 0x1

    if-eqz v2, :cond_bc

    const/4 v4, 0x1

    goto :goto_bd

    .line 151
    :cond_bc
    const/4 v4, 0x0

    :goto_bd
    invoke-virtual {p1, v4}, Ljava/io/DataOutputStream;->writeBoolean(Z)V

    if-eqz v2, :cond_c9

    .line 153
    invoke-virtual {v2}, Ljava/lang/Integer;->intValue()I

    move-result v2

    invoke-virtual {p1, v2}, Ljava/io/DataOutputStream;->writeInt(I)V

    .line 154
    :cond_c9
    iget v2, p0, Landroid/app/Notification;->number:I

    invoke-virtual {p1, v2}, Ljava/io/DataOutputStream;->writeInt(I)V

    .line 155
    iget v2, p0, Landroid/app/Notification;->flags:I

    and-int/lit8 v2, v2, 0x10

    if-eqz v2, :cond_d6

    const/4 v2, 0x1

    goto :goto_d7

    :cond_d6
    const/4 v2, 0x0

    :goto_d7
    invoke-virtual {p1, v2}, Ljava/io/DataOutputStream;->writeBoolean(Z)V

    .line 156
    invoke-virtual {p0}, Landroid/app/Notification;->getGroup()Ljava/lang/String;

    move-result-object v2

    invoke-static {p1, v2}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->serializeString(Ljava/io/DataOutputStream;Ljava/lang/String;)V

    .line 157
    iget v2, p0, Landroid/app/Notification;->flags:I

    and-int/lit16 v2, v2, 0x200

    if-eqz v2, :cond_e9

    const/4 v2, 0x1

    goto :goto_ea

    :cond_e9
    const/4 v2, 0x0

    :goto_ea
    invoke-virtual {p1, v2}, Ljava/io/DataOutputStream;->writeBoolean(Z)V

    .line 158
    invoke-static {p0}, Lcom/unity/androidnotifications/UnityNotificationManager;->getNotificationGroupAlertBehavior(Landroid/app/Notification;)I

    move-result v2

    invoke-virtual {p1, v2}, Ljava/io/DataOutputStream;->writeInt(I)V

    .line 159
    invoke-virtual {p0}, Landroid/app/Notification;->getSortKey()Ljava/lang/String;

    move-result-object v2

    invoke-static {p1, v2}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->serializeString(Ljava/io/DataOutputStream;Ljava/lang/String;)V

    if-eqz v1, :cond_102

    .line 161
    iget-wide v1, p0, Landroid/app/Notification;->when:J

    invoke-virtual {p1, v1, v2}, Ljava/io/DataOutputStream;->writeLong(J)V
    :try_end_102
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_102} :catch_103

    :cond_102
    return v3

    :catch_103
    move-exception p0

    const-string p1, "UnityNotifications"

    const-string v1, "Failed to serialize notification"

    .line 165
    invoke-static {p1, v1, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    return v0
.end method

.method private static serializeNotificationParcel(Landroid/content/Intent;Ljava/io/DataOutputStream;)Z
    .registers 6

    const-string v0, "Failed to serialize notification as Parcel"

    const-string v1, "UnityNotifications"

    const/4 v2, 0x0

    .line 0
    :try_start_5
    invoke-static {p0}, Lcom/unity/androidnotifications/UnityNotificationUtilities;->serializeParcelable(Landroid/os/Parcelable;)[B

    .line 107
    move-result-object p0

    if-eqz p0, :cond_20

    .line 109
    array-length v3, p0

    if-nez v3, :cond_f

    goto :goto_20

    .line 110
    :cond_f
    sget-object v3, Lcom/unity/androidnotifications/UnityNotificationUtilities;->UNITY_MAGIC_NUMBER_PARCELLED:[B

    invoke-virtual {p1, v3}, Ljava/io/DataOutputStream;->write([B)V

    .line 111
    invoke-virtual {p1, v2}, Ljava/io/DataOutputStream;->writeInt(I)V

    .line 112
    array-length v3, p0

    invoke-virtual {p1, v3}, Ljava/io/DataOutputStream;->writeInt(I)V

    invoke-virtual {p1, p0}, Ljava/io/DataOutputStream;->write([B)V
    :try_end_1e
    .catch Ljava/lang/Exception; {:try_start_5 .. :try_end_1e} :catch_26
    .catch Ljava/lang/OutOfMemoryError; {:try_start_5 .. :try_end_1e} :catch_21

    .line 117
    const/4 p0, 0x1

    return p0

    :cond_20
    :goto_20
    return v2

    :catch_21
    move-exception p0

    .line 115
    invoke-static {v1, v0, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_2a

    :catch_26
    move-exception p0

    invoke-static {v1, v0, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :goto_2a
    return v2
.end method

.method private static serializeParcelable(Landroid/os/Parcelable;)[B
    .registers 6

    const-string v0, "Failed to serialize Parcelable"

    const-string v1, "UnityNotifications"

    .line 0
    .line 183
    :try_start_4
    invoke-static {}, Landroid/os/Parcel;->obtain()Landroid/os/Parcel;

    move-result-object v2

    new-instance v3, Landroid/os/Bundle;

    invoke-direct {v3}, Landroid/os/Bundle;-><init>()V

    .line 184
    const-string v4, "obj"

    .line 185
    invoke-virtual {v3, v4, p0}, Landroid/os/Bundle;->putParcelable(Ljava/lang/String;Landroid/os/Parcelable;)V

    .line 186
    const/4 p0, 0x0

    invoke-virtual {v2, v3, p0}, Landroid/os/Parcel;->writeParcelable(Landroid/os/Parcelable;I)V

    .line 187
    invoke-virtual {v2}, Landroid/os/Parcel;->marshall()[B

    move-result-object p0

    invoke-virtual {v2}, Landroid/os/Parcel;->recycle()V
    :try_end_1d
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_1d} :catch_23
    .catch Ljava/lang/OutOfMemoryError; {:try_start_4 .. :try_end_1d} :catch_1e

    .line 192
    return-object p0

    :catch_1e
    move-exception p0

    invoke-static {v1, v0, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 190
    goto :goto_27

    :catch_23
    move-exception p0

    invoke-static {v1, v0, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :goto_27
    const/4 p0, 0x0

    return-object p0
.end method

.method private static serializeString(Ljava/io/DataOutputStream;Ljava/lang/String;)V
    .registers 3
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    if-eqz p1, :cond_17

    .line 0
    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v0

    if-nez v0, :cond_9

    .line 174
    goto :goto_17

    :cond_9
    sget-object v0, Ljava/nio/charset/StandardCharsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-virtual {p1, v0}, Ljava/lang/String;->getBytes(Ljava/nio/charset/Charset;)[B

    .line 175
    move-result-object p1

    array-length v0, p1

    invoke-virtual {p0, v0}, Ljava/io/DataOutputStream;->writeInt(I)V

    .line 176
    invoke-virtual {p0, p1}, Ljava/io/DataOutputStream;->write([B)V

    .line 172
    goto :goto_1b

    :cond_17
    :goto_17
    const/4 p1, 0x0

    invoke-virtual {p0, p1}, Ljava/io/DataOutputStream;->writeInt(I)V

    :goto_1b
    return-void
.end method
