.class Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;
.super Landroid/webkit/WebChromeClient;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "WebPlayerChromeClient"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;


# direct methods
.method private constructor <init>(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;)V
    .registers 2

    iput-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    invoke-direct {p0}, Landroid/webkit/WebChromeClient;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$1;)V
    .registers 3

    invoke-direct {p0, p1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;-><init>(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;)V

    return-void
.end method


# virtual methods
.method public onCloseWindow(Landroid/webkit/WebView;)V
    .registers 7

    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-string v1, "onCloseWindow"

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldCallSuper(Ljava/lang/String;)Z
    invoke-static {v0, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$400(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_d

    invoke-super {p0, p1}, Landroid/webkit/WebChromeClient;->onCloseWindow(Landroid/webkit/WebView;)V

    :cond_d
    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldSendEvent(Ljava/lang/String;)Z
    invoke-static {p1, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$500(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result p1

    if-eqz p1, :cond_2c

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object p1

    sget-object v0, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->WEBPLAYER:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v1, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;->CLOSE_WINDOW:Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;

    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/Object;

    const/4 v3, 0x0

    iget-object v4, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # getter for: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->viewId:Ljava/lang/String;
    invoke-static {v4}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$600(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;)Ljava/lang/String;

    move-result-object v4

    aput-object v4, v2, v3

    invoke-virtual {p1, v0, v1, v2}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_2c
    return-void
.end method

.method public onConsoleMessage(Landroid/webkit/ConsoleMessage;)Z
    .registers 10

    const/4 v0, 0x0

    invoke-static {v0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    iget-object v2, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-string v3, "onConsoleMessage"

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldCallSuper(Ljava/lang/String;)Z
    invoke-static {v2, v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$400(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_17

    invoke-super {p0, p1}, Landroid/webkit/WebChromeClient;->onConsoleMessage(Landroid/webkit/ConsoleMessage;)Z

    move-result v1

    invoke-static {v1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    :cond_17
    iget-object v2, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldSendEvent(Ljava/lang/String;)Z
    invoke-static {v2, v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$500(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result v2

    const/4 v4, 0x1

    if-eqz v2, :cond_41

    if-eqz p1, :cond_27

    invoke-virtual {p1}, Landroid/webkit/ConsoleMessage;->message()Ljava/lang/String;

    move-result-object p1

    goto :goto_29

    :cond_27
    const-string p1, ""

    :goto_29
    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v2

    sget-object v5, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->WEBPLAYER:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v6, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;->CONSOLE_MESSAGE:Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;

    const/4 v7, 0x2

    new-array v7, v7, [Ljava/lang/Object;

    aput-object p1, v7, v0

    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # getter for: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->viewId:Ljava/lang/String;
    invoke-static {p1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$600(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;)Ljava/lang/String;

    move-result-object p1

    aput-object p1, v7, v4

    invoke-virtual {v2, v5, v6, v7}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_41
    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->hasReturnValue(Ljava/lang/String;)Z
    invoke-static {p1, v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$700(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result p1

    if-eqz p1, :cond_58

    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-class v0, Ljava/lang/Boolean;

    invoke-static {v4}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->getReturnValue(Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;
    invoke-static {p1, v3, v0, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$800(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    move-object v1, p1

    check-cast v1, Ljava/lang/Boolean;

    :cond_58
    invoke-virtual {v1}, Ljava/lang/Boolean;->booleanValue()Z

    move-result p1

    return p1
.end method

.method public onCreateWindow(Landroid/webkit/WebView;ZZLandroid/os/Message;)Z
    .registers 12

    const/4 v0, 0x0

    invoke-static {v0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    iget-object v2, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-string v3, "onCreateWindow"

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldCallSuper(Ljava/lang/String;)Z
    invoke-static {v2, v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$400(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_18

    invoke-super {p0, p1, p2, p3, p4}, Landroid/webkit/WebChromeClient;->onCreateWindow(Landroid/webkit/WebView;ZZLandroid/os/Message;)Z

    move-result p1

    invoke-static {p1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object p1

    goto :goto_19

    :cond_18
    move-object p1, v1

    :goto_19
    iget-object v2, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldSendEvent(Ljava/lang/String;)Z
    invoke-static {v2, v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$500(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_48

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v2

    sget-object v4, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->WEBPLAYER:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v5, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;->CREATE_WINDOW:Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;

    const/4 v6, 0x4

    new-array v6, v6, [Ljava/lang/Object;

    invoke-static {p2}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object p2

    aput-object p2, v6, v0

    const/4 p2, 0x1

    invoke-static {p3}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object p3

    aput-object p3, v6, p2

    const/4 p2, 0x2

    aput-object p4, v6, p2

    const/4 p2, 0x3

    iget-object p3, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # getter for: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->viewId:Ljava/lang/String;
    invoke-static {p3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$600(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;)Ljava/lang/String;

    move-result-object p3

    aput-object p3, v6, p2

    invoke-virtual {v2, v4, v5, v6}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_48
    iget-object p2, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->hasReturnValue(Ljava/lang/String;)Z
    invoke-static {p2, v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$700(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result p2

    if-eqz p2, :cond_5a

    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-class p2, Ljava/lang/Boolean;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->getReturnValue(Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;
    invoke-static {p1, v3, p2, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$800(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/Boolean;

    :cond_5a
    invoke-virtual {p1}, Ljava/lang/Boolean;->booleanValue()Z

    move-result p1

    return p1
.end method

.method public onGeolocationPermissionsShowPrompt(Ljava/lang/String;Landroid/webkit/GeolocationPermissions$Callback;)V
    .registers 7

    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-string v1, "onGeolocationPermissionsShowPrompt"

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldCallSuper(Ljava/lang/String;)Z
    invoke-static {v0, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$400(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_d

    invoke-super {p0, p1, p2}, Landroid/webkit/WebChromeClient;->onGeolocationPermissionsShowPrompt(Ljava/lang/String;Landroid/webkit/GeolocationPermissions$Callback;)V

    :cond_d
    iget-object p2, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldSendEvent(Ljava/lang/String;)Z
    invoke-static {p2, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$500(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result p2

    if-eqz p2, :cond_2f

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object p2

    sget-object v0, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->WEBPLAYER:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v1, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;->GEOLOCATION_PERMISSIONS_SHOW:Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;

    const/4 v2, 0x2

    new-array v2, v2, [Ljava/lang/Object;

    const/4 v3, 0x0

    aput-object p1, v2, v3

    const/4 p1, 0x1

    iget-object v3, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # getter for: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->viewId:Ljava/lang/String;
    invoke-static {v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$600(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;)Ljava/lang/String;

    move-result-object v3

    aput-object v3, v2, p1

    invoke-virtual {p2, v0, v1, v2}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_2f
    return-void
.end method

.method public onHideCustomView()V
    .registers 7

    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-string v1, "onHideCustomView"

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldCallSuper(Ljava/lang/String;)Z
    invoke-static {v0, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$400(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_d

    invoke-super {p0}, Landroid/webkit/WebChromeClient;->onHideCustomView()V

    :cond_d
    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldSendEvent(Ljava/lang/String;)Z
    invoke-static {v0, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$500(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_2c

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v0

    sget-object v1, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->WEBPLAYER:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v2, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;->HIDE_CUSTOM_VIEW:Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;

    const/4 v3, 0x1

    new-array v3, v3, [Ljava/lang/Object;

    const/4 v4, 0x0

    iget-object v5, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # getter for: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->viewId:Ljava/lang/String;
    invoke-static {v5}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$600(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;)Ljava/lang/String;

    move-result-object v5

    aput-object v5, v3, v4

    invoke-virtual {v0, v1, v2, v3}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_2c
    return-void
.end method

.method public onJsAlert(Landroid/webkit/WebView;Ljava/lang/String;Ljava/lang/String;Landroid/webkit/JsResult;)Z
    .registers 12

    const/4 v0, 0x0

    invoke-static {v0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    iget-object v2, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-string v3, "onJsAlert"

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldCallSuper(Ljava/lang/String;)Z
    invoke-static {v2, v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$400(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_17

    invoke-super {p0, p1, p2, p3, p4}, Landroid/webkit/WebChromeClient;->onJsAlert(Landroid/webkit/WebView;Ljava/lang/String;Ljava/lang/String;Landroid/webkit/JsResult;)Z

    move-result p1

    invoke-static {p1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    :cond_17
    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldSendEvent(Ljava/lang/String;)Z
    invoke-static {p1, v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$500(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result p1

    const/4 v2, 0x1

    if-eqz p1, :cond_3e

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object p1

    sget-object v4, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->WEBPLAYER:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v5, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;->JS_ALERT:Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;

    const/4 v6, 0x4

    new-array v6, v6, [Ljava/lang/Object;

    aput-object p2, v6, v0

    aput-object p3, v6, v2

    const/4 p2, 0x2

    aput-object p4, v6, p2

    const/4 p2, 0x3

    iget-object p3, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # getter for: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->viewId:Ljava/lang/String;
    invoke-static {p3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$600(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;)Ljava/lang/String;

    move-result-object p3

    aput-object p3, v6, p2

    invoke-virtual {p1, v4, v5, v6}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_3e
    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->hasReturnValue(Ljava/lang/String;)Z
    invoke-static {p1, v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$700(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result p1

    if-eqz p1, :cond_55

    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-class p2, Ljava/lang/Boolean;

    invoke-static {v2}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object p3

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->getReturnValue(Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;
    invoke-static {p1, v3, p2, p3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$800(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    move-object v1, p1

    check-cast v1, Ljava/lang/Boolean;

    :cond_55
    invoke-virtual {v1}, Ljava/lang/Boolean;->booleanValue()Z

    move-result p1

    return p1
.end method

.method public onJsConfirm(Landroid/webkit/WebView;Ljava/lang/String;Ljava/lang/String;Landroid/webkit/JsResult;)Z
    .registers 11

    const/4 v0, 0x0

    invoke-static {v0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    iget-object v2, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-string v3, "onJsConfirm"

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldCallSuper(Ljava/lang/String;)Z
    invoke-static {v2, v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$400(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_17

    invoke-super {p0, p1, p2, p3, p4}, Landroid/webkit/WebChromeClient;->onJsConfirm(Landroid/webkit/WebView;Ljava/lang/String;Ljava/lang/String;Landroid/webkit/JsResult;)Z

    move-result p1

    invoke-static {p1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    :cond_17
    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldSendEvent(Ljava/lang/String;)Z
    invoke-static {p1, v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$500(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result p1

    const/4 p4, 0x1

    if-eqz p1, :cond_3b

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object p1

    sget-object v2, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->WEBPLAYER:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v4, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;->JS_CONFIRM:Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;

    const/4 v5, 0x3

    new-array v5, v5, [Ljava/lang/Object;

    aput-object p2, v5, v0

    aput-object p3, v5, p4

    const/4 p2, 0x2

    iget-object p3, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # getter for: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->viewId:Ljava/lang/String;
    invoke-static {p3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$600(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;)Ljava/lang/String;

    move-result-object p3

    aput-object p3, v5, p2

    invoke-virtual {p1, v2, v4, v5}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_3b
    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->hasReturnValue(Ljava/lang/String;)Z
    invoke-static {p1, v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$700(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result p1

    if-eqz p1, :cond_52

    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-class p2, Ljava/lang/Boolean;

    invoke-static {p4}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object p3

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->getReturnValue(Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;
    invoke-static {p1, v3, p2, p3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$800(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    move-object v1, p1

    check-cast v1, Ljava/lang/Boolean;

    :cond_52
    invoke-virtual {v1}, Ljava/lang/Boolean;->booleanValue()Z

    move-result p1

    return p1
.end method

.method public onJsPrompt(Landroid/webkit/WebView;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/webkit/JsPromptResult;)Z
    .registers 12

    const/4 v0, 0x0

    invoke-static {v0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    iget-object v2, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-string v3, "onJsPrompt"

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldCallSuper(Ljava/lang/String;)Z
    invoke-static {v2, v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$400(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_17

    invoke-super/range {p0 .. p5}, Landroid/webkit/WebChromeClient;->onJsPrompt(Landroid/webkit/WebView;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/webkit/JsPromptResult;)Z

    move-result p1

    invoke-static {p1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    :cond_17
    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldSendEvent(Ljava/lang/String;)Z
    invoke-static {p1, v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$500(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result p1

    const/4 p5, 0x1

    if-eqz p1, :cond_3e

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object p1

    sget-object v2, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->WEBPLAYER:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v4, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;->JS_PROMPT:Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;

    const/4 v5, 0x4

    new-array v5, v5, [Ljava/lang/Object;

    aput-object p2, v5, v0

    aput-object p3, v5, p5

    const/4 p2, 0x2

    aput-object p4, v5, p2

    const/4 p2, 0x3

    iget-object p3, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # getter for: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->viewId:Ljava/lang/String;
    invoke-static {p3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$600(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;)Ljava/lang/String;

    move-result-object p3

    aput-object p3, v5, p2

    invoke-virtual {p1, v2, v4, v5}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_3e
    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->hasReturnValue(Ljava/lang/String;)Z
    invoke-static {p1, v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$700(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result p1

    if-eqz p1, :cond_55

    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-class p2, Ljava/lang/Boolean;

    invoke-static {p5}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object p3

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->getReturnValue(Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;
    invoke-static {p1, v3, p2, p3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$800(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    move-object v1, p1

    check-cast v1, Ljava/lang/Boolean;

    :cond_55
    invoke-virtual {v1}, Ljava/lang/Boolean;->booleanValue()Z

    move-result p1

    return p1
.end method

.method public onPermissionRequest(Landroid/webkit/PermissionRequest;)V
    .registers 7

    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-string v1, "onPermissionRequest"

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldCallSuper(Ljava/lang/String;)Z
    invoke-static {v0, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$400(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_d

    invoke-super {p0, p1}, Landroid/webkit/WebChromeClient;->onPermissionRequest(Landroid/webkit/PermissionRequest;)V

    :cond_d
    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldSendEvent(Ljava/lang/String;)Z
    invoke-static {v0, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$500(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_42

    if-eqz p1, :cond_26

    invoke-virtual {p1}, Landroid/webkit/PermissionRequest;->getOrigin()Landroid/net/Uri;

    move-result-object v0

    if-eqz v0, :cond_26

    invoke-virtual {p1}, Landroid/webkit/PermissionRequest;->getOrigin()Landroid/net/Uri;

    move-result-object p1

    invoke-virtual {p1}, Landroid/net/Uri;->toString()Ljava/lang/String;

    move-result-object p1

    goto :goto_28

    :cond_26
    const-string p1, ""

    :goto_28
    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v0

    sget-object v1, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->WEBPLAYER:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v2, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;->PERMISSION_REQUEST:Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;

    const/4 v3, 0x2

    new-array v3, v3, [Ljava/lang/Object;

    const/4 v4, 0x0

    aput-object p1, v3, v4

    const/4 p1, 0x1

    iget-object v4, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # getter for: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->viewId:Ljava/lang/String;
    invoke-static {v4}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$600(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;)Ljava/lang/String;

    move-result-object v4

    aput-object v4, v3, p1

    invoke-virtual {v0, v1, v2, v3}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_42
    return-void
.end method

.method public onProgressChanged(Landroid/webkit/WebView;I)V
    .registers 7

    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-string v1, "onProgressChanged"

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldCallSuper(Ljava/lang/String;)Z
    invoke-static {v0, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$400(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_d

    invoke-super {p0, p1, p2}, Landroid/webkit/WebChromeClient;->onProgressChanged(Landroid/webkit/WebView;I)V

    :cond_d
    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldSendEvent(Ljava/lang/String;)Z
    invoke-static {p1, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$500(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result p1

    if-eqz p1, :cond_33

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object p1

    sget-object v0, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->WEBPLAYER:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v1, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;->PROGRESS_CHANGED:Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;

    const/4 v2, 0x2

    new-array v2, v2, [Ljava/lang/Object;

    const/4 v3, 0x0

    invoke-static {p2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p2

    aput-object p2, v2, v3

    const/4 p2, 0x1

    iget-object v3, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # getter for: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->viewId:Ljava/lang/String;
    invoke-static {v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$600(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;)Ljava/lang/String;

    move-result-object v3

    aput-object v3, v2, p2

    invoke-virtual {p1, v0, v1, v2}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_33
    return-void
.end method

.method public onReceivedIcon(Landroid/webkit/WebView;Landroid/graphics/Bitmap;)V
    .registers 7

    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-string v1, "onReceivedIcon"

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldCallSuper(Ljava/lang/String;)Z
    invoke-static {v0, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$400(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_d

    invoke-super {p0, p1, p2}, Landroid/webkit/WebChromeClient;->onReceivedIcon(Landroid/webkit/WebView;Landroid/graphics/Bitmap;)V

    :cond_d
    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldSendEvent(Ljava/lang/String;)Z
    invoke-static {p1, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$500(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result p1

    if-eqz p1, :cond_2c

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object p1

    sget-object p2, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->WEBPLAYER:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;->RECEIVED_ICON:Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    iget-object v3, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # getter for: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->viewId:Ljava/lang/String;
    invoke-static {v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$600(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;)Ljava/lang/String;

    move-result-object v3

    aput-object v3, v1, v2

    invoke-virtual {p1, p2, v0, v1}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_2c
    return-void
.end method

.method public onReceivedTitle(Landroid/webkit/WebView;Ljava/lang/String;)V
    .registers 7

    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-string v1, "onReceivedTitle"

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldCallSuper(Ljava/lang/String;)Z
    invoke-static {v0, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$400(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_d

    invoke-super {p0, p1, p2}, Landroid/webkit/WebChromeClient;->onReceivedTitle(Landroid/webkit/WebView;Ljava/lang/String;)V

    :cond_d
    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldSendEvent(Ljava/lang/String;)Z
    invoke-static {p1, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$500(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result p1

    if-eqz p1, :cond_2f

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object p1

    sget-object v0, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->WEBPLAYER:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v1, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;->RECEIVED_TITLE:Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;

    const/4 v2, 0x2

    new-array v2, v2, [Ljava/lang/Object;

    const/4 v3, 0x0

    aput-object p2, v2, v3

    const/4 p2, 0x1

    iget-object v3, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # getter for: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->viewId:Ljava/lang/String;
    invoke-static {v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$600(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;)Ljava/lang/String;

    move-result-object v3

    aput-object v3, v2, p2

    invoke-virtual {p1, v0, v1, v2}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_2f
    return-void
.end method

.method public onReceivedTouchIconUrl(Landroid/webkit/WebView;Ljava/lang/String;Z)V
    .registers 8

    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-string v1, "onReceivedTouchIconUrl"

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldCallSuper(Ljava/lang/String;)Z
    invoke-static {v0, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$400(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_d

    invoke-super {p0, p1, p2, p3}, Landroid/webkit/WebChromeClient;->onReceivedTouchIconUrl(Landroid/webkit/WebView;Ljava/lang/String;Z)V

    :cond_d
    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldSendEvent(Ljava/lang/String;)Z
    invoke-static {p1, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$500(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result p1

    if-eqz p1, :cond_36

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object p1

    sget-object v0, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->WEBPLAYER:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v1, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;->RECEIVED_TOUCH_ICON_URL:Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;

    const/4 v2, 0x3

    new-array v2, v2, [Ljava/lang/Object;

    const/4 v3, 0x0

    aput-object p2, v2, v3

    const/4 p2, 0x1

    invoke-static {p3}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object p3

    aput-object p3, v2, p2

    const/4 p2, 0x2

    iget-object p3, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # getter for: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->viewId:Ljava/lang/String;
    invoke-static {p3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$600(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;)Ljava/lang/String;

    move-result-object p3

    aput-object p3, v2, p2

    invoke-virtual {p1, v0, v1, v2}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_36
    return-void
.end method

.method public onRequestFocus(Landroid/webkit/WebView;)V
    .registers 7

    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-string v1, "onRequestFocus"

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldCallSuper(Ljava/lang/String;)Z
    invoke-static {v0, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$400(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_d

    invoke-super {p0, p1}, Landroid/webkit/WebChromeClient;->onRequestFocus(Landroid/webkit/WebView;)V

    :cond_d
    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldSendEvent(Ljava/lang/String;)Z
    invoke-static {p1, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$500(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result p1

    if-eqz p1, :cond_2c

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object p1

    sget-object v0, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->WEBPLAYER:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v1, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;->REQUEST_FOCUS:Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;

    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/Object;

    const/4 v3, 0x0

    iget-object v4, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # getter for: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->viewId:Ljava/lang/String;
    invoke-static {v4}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$600(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;)Ljava/lang/String;

    move-result-object v4

    aput-object v4, v2, v3

    invoke-virtual {p1, v0, v1, v2}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_2c
    return-void
.end method

.method public onShowCustomView(Landroid/view/View;Landroid/webkit/WebChromeClient$CustomViewCallback;)V
    .registers 7

    iget-object v0, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-string v1, "onShowCustomView"

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldCallSuper(Ljava/lang/String;)Z
    invoke-static {v0, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$400(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_d

    invoke-super {p0, p1, p2}, Landroid/webkit/WebChromeClient;->onShowCustomView(Landroid/view/View;Landroid/webkit/WebChromeClient$CustomViewCallback;)V

    :cond_d
    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldSendEvent(Ljava/lang/String;)Z
    invoke-static {p1, v1}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$500(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result p1

    if-eqz p1, :cond_2c

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object p1

    sget-object p2, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->WEBPLAYER:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;->SHOW_CUSTOM_VIEW:Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    iget-object v3, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # getter for: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->viewId:Ljava/lang/String;
    invoke-static {v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$600(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;)Ljava/lang/String;

    move-result-object v3

    aput-object v3, v1, v2

    invoke-virtual {p1, p2, v0, v1}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_2c
    return-void
.end method

.method public onShowFileChooser(Landroid/webkit/WebView;Landroid/webkit/ValueCallback;Landroid/webkit/WebChromeClient$FileChooserParams;)Z
    .registers 11

    const/4 v0, 0x0

    invoke-static {v0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    iget-object v2, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-string v3, "onShowFileChooser"

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldCallSuper(Ljava/lang/String;)Z
    invoke-static {v2, v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$400(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_17

    invoke-super {p0, p1, p2, p3}, Landroid/webkit/WebChromeClient;->onShowFileChooser(Landroid/webkit/WebView;Landroid/webkit/ValueCallback;Landroid/webkit/WebChromeClient$FileChooserParams;)Z

    move-result p1

    invoke-static {p1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    :cond_17
    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->shouldSendEvent(Ljava/lang/String;)Z
    invoke-static {p1, v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$500(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result p1

    const/4 p3, 0x1

    if-eqz p1, :cond_35

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object p1

    sget-object v2, Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;->WEBPLAYER:Lcom/unity3d/splash/services/core/webview/WebViewEventCategory;

    sget-object v4, Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;->SHOW_FILE_CHOOSER:Lcom/unity3d/splash/services/ads/webplayer/WebPlayerEvent;

    new-array v5, p3, [Ljava/lang/Object;

    iget-object v6, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # getter for: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->viewId:Ljava/lang/String;
    invoke-static {v6}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$600(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;)Ljava/lang/String;

    move-result-object v6

    aput-object v6, v5, v0

    invoke-virtual {p1, v2, v4, v5}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->sendEvent(Ljava/lang/Enum;Ljava/lang/Enum;[Ljava/lang/Object;)Z

    :cond_35
    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->hasReturnValue(Ljava/lang/String;)Z
    invoke-static {p1, v3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$700(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;)Z

    move-result p1

    if-eqz p1, :cond_56

    iget-object p1, p0, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer$WebPlayerChromeClient;->this$0:Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;

    const-class v0, Ljava/lang/Boolean;

    invoke-static {p3}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object p3

    # invokes: Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->getReturnValue(Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;
    invoke-static {p1, v3, v0, p3}, Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;->access$800(Lcom/unity3d/splash/services/ads/webplayer/WebPlayer;Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    move-object v1, p1

    check-cast v1, Ljava/lang/Boolean;

    invoke-virtual {v1}, Ljava/lang/Boolean;->booleanValue()Z

    move-result p1

    if-eqz p1, :cond_56

    const/4 p1, 0x0

    invoke-interface {p2, p1}, Landroid/webkit/ValueCallback;->onReceiveValue(Ljava/lang/Object;)V

    :cond_56
    invoke-virtual {v1}, Ljava/lang/Boolean;->booleanValue()Z

    move-result p1

    return p1
.end method
