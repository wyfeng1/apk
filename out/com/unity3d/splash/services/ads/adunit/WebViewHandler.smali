.class public Lcom/unity3d/splash/services/ads/adunit/WebViewHandler;
.super Ljava/lang/Object;

# interfaces
.implements Lcom/unity3d/splash/services/ads/adunit/IAdUnitViewHandler;


# direct methods
.method public constructor <init>()V
    .registers 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public create(Lcom/unity3d/splash/services/ads/adunit/AdUnitActivity;)Z
    .registers 2

    const/4 p1, 0x1

    return p1
.end method

.method public destroy()Z
    .registers 2

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v0

    if-eqz v0, :cond_1b

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v0

    invoke-virtual {v0}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getWebView()Lcom/unity3d/splash/services/core/webview/WebView;

    move-result-object v0

    if-eqz v0, :cond_1b

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v0

    invoke-virtual {v0}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getWebView()Lcom/unity3d/splash/services/core/webview/WebView;

    move-result-object v0

    invoke-static {v0}, Lcom/unity3d/splash/services/core/misc/ViewUtilities;->removeViewFromParent(Landroid/view/View;)V

    :cond_1b
    const/4 v0, 0x1

    return v0
.end method

.method public getView()Landroid/view/View;
    .registers 2

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v0

    if-eqz v0, :cond_f

    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getCurrentApp()Lcom/unity3d/splash/services/core/webview/WebViewApp;

    move-result-object v0

    invoke-virtual {v0}, Lcom/unity3d/splash/services/core/webview/WebViewApp;->getWebView()Lcom/unity3d/splash/services/core/webview/WebView;

    move-result-object v0

    return-object v0

    :cond_f
    const/4 v0, 0x0

    return-object v0
.end method

.method public onCreate(Lcom/unity3d/splash/services/ads/adunit/AdUnitActivity;Landroid/os/Bundle;)V
    .registers 3

    return-void
.end method

.method public onDestroy(Lcom/unity3d/splash/services/ads/adunit/AdUnitActivity;)V
    .registers 2

    invoke-virtual {p0}, Lcom/unity3d/splash/services/ads/adunit/WebViewHandler;->destroy()Z

    return-void
.end method

.method public onPause(Lcom/unity3d/splash/services/ads/adunit/AdUnitActivity;)V
    .registers 2

    return-void
.end method

.method public onResume(Lcom/unity3d/splash/services/ads/adunit/AdUnitActivity;)V
    .registers 2

    return-void
.end method

.method public onStart(Lcom/unity3d/splash/services/ads/adunit/AdUnitActivity;)V
    .registers 2

    return-void
.end method

.method public onStop(Lcom/unity3d/splash/services/ads/adunit/AdUnitActivity;)V
    .registers 2

    return-void
.end method
