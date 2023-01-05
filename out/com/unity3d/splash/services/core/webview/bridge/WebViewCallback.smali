.class public Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;
.super Ljava/lang/Object;

# interfaces
.implements Landroid/os/Parcelable;


# static fields
.field public static final CREATOR:Landroid/os/Parcelable$Creator;


# instance fields
.field private _callbackId:Ljava/lang/String;

.field private _invocationId:I

.field private _invoked:Z


# direct methods
.method static constructor <clinit>()V
    .registers 1

    new-instance v0, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback$1;

    invoke-direct {v0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback$1;-><init>()V

    sput-object v0, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->CREATOR:Landroid/os/Parcelable$Creator;

    return-void
.end method

.method public constructor <init>(Landroid/os/Parcel;)V
    .registers 3

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    invoke-virtual {p1}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->_callbackId:Ljava/lang/String;

    invoke-virtual {p1}, Landroid/os/Parcel;->readByte()B

    move-result v0

    if-eqz v0, :cond_11

    const/4 v0, 0x1

    goto :goto_12

    :cond_11
    const/4 v0, 0x0

    :goto_12
    iput-boolean v0, p0, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->_invoked:Z

    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result p1

    iput p1, p0, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->_invocationId:I

    return-void
.end method

.method public constructor <init>(Ljava/lang/String;I)V
    .registers 3

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->_callbackId:Ljava/lang/String;

    iput p2, p0, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->_invocationId:I

    return-void
.end method

.method private varargs invoke(Lcom/unity3d/splash/services/core/webview/bridge/CallbackStatus;Ljava/lang/Enum;[Ljava/lang/Object;)V
    .registers 6

    iget-boolean v0, p0, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->_invoked:Z

    if-nez v0, :cond_49

    iget-object v0, p0, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->_callbackId:Ljava/lang/String;

    if-eqz v0, :cond_49

    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v0

    if-nez v0, :cond_f

    goto :goto_49

    :cond_f
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->_invoked:Z

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    invoke-static {p3}, Ljava/util/Arrays;->asList([Ljava/lang/Object;)Ljava/util/List;

    move-result-object p3

    invoke-virtual {v0, p3}, Ljava/util/ArrayList;->addAll(Ljava/util/Collection;)Z

    const/4 p3, 0x0

    iget-object v1, p0, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->_callbackId:Ljava/lang/String;

    invoke-virtual {v0, p3, v1}, Ljava/util/ArrayList;->add(ILjava/lang/Object;)V

    iget p3, p0, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->_invocationId:I

    invoke-static {p3}, Lcom/unity3d/splash/services/core/webview/bridge/Invocation;->getInvocationById(I)Lcom/unity3d/splash/services/core/webview/bridge/Invocation;

    move-result-object p3

    if-nez p3, :cond_42

    new-instance p1, Ljava/lang/StringBuilder;

    const-string p2, "Couldn\'t get batch with id: "

    invoke-direct {p1, p2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {p0}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->getInvocationId()I

    move-result p2

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p1}, Lcom/unity3d/splash/services/core/log/DeviceLog;->error(Ljava/lang/String;)V

    return-void

    :cond_42
    invoke-virtual {v0}, Ljava/util/ArrayList;->toArray()[Ljava/lang/Object;

    move-result-object v0

    invoke-virtual {p3, p1, p2, v0}, Lcom/unity3d/splash/services/core/webview/bridge/Invocation;->setInvocationResponse(Lcom/unity3d/splash/services/core/webview/bridge/CallbackStatus;Ljava/lang/Enum;[Ljava/lang/Object;)V

    :cond_49
    :goto_49
    return-void
.end method


# virtual methods
.method public describeContents()I
    .registers 2

    const v0, 0xb26e

    return v0
.end method

.method public varargs error(Ljava/lang/Enum;[Ljava/lang/Object;)V
    .registers 4

    sget-object v0, Lcom/unity3d/splash/services/core/webview/bridge/CallbackStatus;->ERROR:Lcom/unity3d/splash/services/core/webview/bridge/CallbackStatus;

    invoke-direct {p0, v0, p1, p2}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke(Lcom/unity3d/splash/services/core/webview/bridge/CallbackStatus;Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void
.end method

.method public getCallbackId()Ljava/lang/String;
    .registers 2

    iget-object v0, p0, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->_callbackId:Ljava/lang/String;

    return-object v0
.end method

.method public getInvocationId()I
    .registers 2

    iget v0, p0, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->_invocationId:I

    return v0
.end method

.method public varargs invoke([Ljava/lang/Object;)V
    .registers 4

    sget-object v0, Lcom/unity3d/splash/services/core/webview/bridge/CallbackStatus;->OK:Lcom/unity3d/splash/services/core/webview/bridge/CallbackStatus;

    const/4 v1, 0x0

    invoke-direct {p0, v0, v1, p1}, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->invoke(Lcom/unity3d/splash/services/core/webview/bridge/CallbackStatus;Ljava/lang/Enum;[Ljava/lang/Object;)V

    return-void
.end method

.method public writeToParcel(Landroid/os/Parcel;I)V
    .registers 3

    iget-object p2, p0, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->_callbackId:Ljava/lang/String;

    invoke-virtual {p1, p2}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    iget-boolean p2, p0, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->_invoked:Z

    int-to-byte p2, p2

    invoke-virtual {p1, p2}, Landroid/os/Parcel;->writeByte(B)V

    iget p2, p0, Lcom/unity3d/splash/services/core/webview/bridge/WebViewCallback;->_invocationId:I

    invoke-virtual {p1, p2}, Landroid/os/Parcel;->writeInt(I)V

    return-void
.end method
