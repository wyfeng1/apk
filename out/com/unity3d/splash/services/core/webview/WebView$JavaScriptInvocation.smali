.class Lcom/unity3d/splash/services/core/webview/WebView$JavaScriptInvocation;
.super Ljava/lang/Object;

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/unity3d/splash/services/core/webview/WebView;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "JavaScriptInvocation"
.end annotation


# instance fields
.field private _jsString:Ljava/lang/String;

.field private _webView:Landroid/webkit/WebView;

.field final synthetic this$0:Lcom/unity3d/splash/services/core/webview/WebView;


# direct methods
.method public constructor <init>(Lcom/unity3d/splash/services/core/webview/WebView;Ljava/lang/String;Landroid/webkit/WebView;)V
    .registers 4

    iput-object p1, p0, Lcom/unity3d/splash/services/core/webview/WebView$JavaScriptInvocation;->this$0:Lcom/unity3d/splash/services/core/webview/WebView;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 p1, 0x0

    iput-object p1, p0, Lcom/unity3d/splash/services/core/webview/WebView$JavaScriptInvocation;->_jsString:Ljava/lang/String;

    iput-object p1, p0, Lcom/unity3d/splash/services/core/webview/WebView$JavaScriptInvocation;->_webView:Landroid/webkit/WebView;

    iput-object p2, p0, Lcom/unity3d/splash/services/core/webview/WebView$JavaScriptInvocation;->_jsString:Ljava/lang/String;

    iput-object p3, p0, Lcom/unity3d/splash/services/core/webview/WebView$JavaScriptInvocation;->_webView:Landroid/webkit/WebView;

    return-void
.end method


# virtual methods
.method public run()V
    .registers 6

    iget-object v0, p0, Lcom/unity3d/splash/services/core/webview/WebView$JavaScriptInvocation;->_jsString:Ljava/lang/String;

    if-eqz v0, :cond_2f

    :try_start_4
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x13

    if-lt v0, v1, :cond_20

    # getter for: Lcom/unity3d/splash/services/core/webview/WebView;->_evaluateJavascript:Ljava/lang/reflect/Method;
    invoke-static {}, Lcom/unity3d/splash/services/core/webview/WebView;->access$000()Ljava/lang/reflect/Method;

    move-result-object v0

    iget-object v1, p0, Lcom/unity3d/splash/services/core/webview/WebView$JavaScriptInvocation;->_webView:Landroid/webkit/WebView;

    const/4 v2, 0x2

    new-array v2, v2, [Ljava/lang/Object;

    const/4 v3, 0x0

    iget-object v4, p0, Lcom/unity3d/splash/services/core/webview/WebView$JavaScriptInvocation;->_jsString:Ljava/lang/String;

    aput-object v4, v2, v3

    const/4 v3, 0x1

    const/4 v4, 0x0

    aput-object v4, v2, v3

    invoke-virtual {v0, v1, v2}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    return-void

    :cond_20
    iget-object v0, p0, Lcom/unity3d/splash/services/core/webview/WebView$JavaScriptInvocation;->this$0:Lcom/unity3d/splash/services/core/webview/WebView;

    iget-object v1, p0, Lcom/unity3d/splash/services/core/webview/WebView$JavaScriptInvocation;->_jsString:Ljava/lang/String;

    invoke-virtual {v0, v1}, Lcom/unity3d/splash/services/core/webview/WebView;->loadUrl(Ljava/lang/String;)V
    :try_end_27
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_27} :catch_28

    return-void

    :catch_28
    move-exception v0

    const-string v1, "Error while processing JavaScriptString"

    invoke-static {v1, v0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->exception(Ljava/lang/String;Ljava/lang/Exception;)V

    return-void

    :cond_2f
    const-string v0, "Could not process JavaScript, the string is NULL"

    invoke-static {v0}, Lcom/unity3d/splash/services/core/log/DeviceLog;->error(Ljava/lang/String;)V

    return-void
.end method
