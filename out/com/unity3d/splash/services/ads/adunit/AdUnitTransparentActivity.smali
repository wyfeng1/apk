.class public Lcom/unity3d/splash/services/ads/adunit/AdUnitTransparentActivity;
.super Lcom/unity3d/splash/services/ads/adunit/AdUnitActivity;


# direct methods
.method public constructor <init>()V
    .registers 1

    invoke-direct {p0}, Lcom/unity3d/splash/services/ads/adunit/AdUnitActivity;-><init>()V

    return-void
.end method


# virtual methods
.method protected createLayout()V
    .registers 4

    invoke-super {p0}, Lcom/unity3d/splash/services/ads/adunit/AdUnitActivity;->createLayout()V

    iget-object v0, p0, Lcom/unity3d/splash/services/ads/adunit/AdUnitTransparentActivity;->_layout:Lcom/unity3d/splash/services/ads/adunit/AdUnitRelativeLayout;

    new-instance v1, Landroid/graphics/drawable/ColorDrawable;

    const/4 v2, 0x0

    invoke-direct {v1, v2}, Landroid/graphics/drawable/ColorDrawable;-><init>(I)V

    invoke-static {v0, v1}, Lcom/unity3d/splash/services/core/misc/ViewUtilities;->setBackground(Landroid/view/View;Landroid/graphics/drawable/Drawable;)V

    return-void
.end method

.method protected onCreate(Landroid/os/Bundle;)V
    .registers 4

    invoke-super {p0, p1}, Lcom/unity3d/splash/services/ads/adunit/AdUnitActivity;->onCreate(Landroid/os/Bundle;)V

    iget-object p1, p0, Lcom/unity3d/splash/services/ads/adunit/AdUnitActivity;->_layout:Lcom/unity3d/splash/services/ads/adunit/AdUnitRelativeLayout;

    new-instance v0, Landroid/graphics/drawable/ColorDrawable;

    const/4 v1, 0x0

    invoke-direct {v0, v1}, Landroid/graphics/drawable/ColorDrawable;-><init>(I)V

    invoke-static {p1, v0}, Lcom/unity3d/splash/services/core/misc/ViewUtilities;->setBackground(Landroid/view/View;Landroid/graphics/drawable/Drawable;)V

    return-void
.end method
