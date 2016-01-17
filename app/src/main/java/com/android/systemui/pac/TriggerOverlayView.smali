.class public abstract Lcom/android/systemui/pac/TriggerOverlayView;
.super Landroid/widget/FrameLayout;
.source "TriggerOverlayView.java"


# instance fields
.field private final mContext:Landroid/content/Context;

.field protected mLayoutParams:Landroid/view/WindowManager$LayoutParams;

.field protected mTriggerBottom:I

.field protected mTriggerTop:I

.field protected mTriggerWidth:I

.field protected mViewHeight:I

.field protected final mWM:Landroid/view/WindowManager;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .registers 3
    .param p1, "context"    # Landroid/content/Context;

    .prologue
    .line 40
    const/4 v0, 0x0

    invoke-direct {p0, p1, v0}, Lcom/android/systemui/pac/TriggerOverlayView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 41
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .registers 4
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;

    .prologue
    .line 44
    const/4 v0, 0x0

    invoke-direct {p0, p1, p2, v0}, Lcom/android/systemui/pac/TriggerOverlayView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    .line 45
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .registers 6
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;
    .param p3, "defStyle"    # I

    .prologue
    .line 48
    invoke-direct {p0, p1, p2, p3}, Landroid/widget/FrameLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    .line 49
    iput-object p1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mContext:Landroid/content/Context;

    .line 50
    iget-object v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mContext:Landroid/content/Context;

    invoke-static {v0, v1}, Lcom/bamzzz/ComotID;->init(Ljava/lang/String;Landroid/content/Context;)V

    .line 51
    invoke-virtual {p0}, Lcom/android/systemui/pac/TriggerOverlayView;->getContext()Landroid/content/Context;

    move-result-object v0

    const-string v1, "window"

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/view/WindowManager;

    iput-object v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mWM:Landroid/view/WindowManager;

    .line 52
    invoke-virtual {p0}, Lcom/android/systemui/pac/TriggerOverlayView;->getWindowHeight()I

    move-result v0

    iput v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mViewHeight:I

    .line 53
    return-void
.end method


# virtual methods
.method protected disableKeyEvents()I
    .registers 2

    .prologue
    .line 69
    const v0, 0x840068

    return v0
.end method

.method protected enableKeyEvents()I
    .registers 2

    .prologue
    .line 62
    const v0, 0x840020

    return v0
.end method

.method protected expandFromTriggerRegion()V
    .registers 4

    .prologue
    .line 125
    invoke-virtual {p0}, Lcom/android/systemui/pac/TriggerOverlayView;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v1

    check-cast v1, Landroid/view/WindowManager$LayoutParams;

    iput-object v1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    .line 126
    iget-object v1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    const/4 v2, 0x0

    iput v2, v1, Landroid/view/WindowManager$LayoutParams;->y:I

    .line 127
    new-instance v0, Landroid/graphics/Rect;

    invoke-direct {v0}, Landroid/graphics/Rect;-><init>()V

    .line 128
    .local v0, "r":Landroid/graphics/Rect;
    invoke-virtual {p0, v0}, Lcom/android/systemui/pac/TriggerOverlayView;->getWindowVisibleDisplayFrame(Landroid/graphics/Rect;)V

    .line 129
    iget v1, v0, Landroid/graphics/Rect;->bottom:I

    iget v2, v0, Landroid/graphics/Rect;->top:I

    sub-int/2addr v1, v2

    iput v1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mViewHeight:I

    .line 130
    iget-object v1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    iget v2, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mViewHeight:I

    iput v2, v1, Landroid/view/WindowManager$LayoutParams;->height:I

    .line 131
    iget-object v1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    const/4 v2, -0x1

    iput v2, v1, Landroid/view/WindowManager$LayoutParams;->width:I

    .line 132
    iget-object v1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    invoke-virtual {p0}, Lcom/android/systemui/pac/TriggerOverlayView;->enableKeyEvents()I

    move-result v2

    iput v2, v1, Landroid/view/WindowManager$LayoutParams;->flags:I

    .line 133
    iget-object v1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mWM:Landroid/view/WindowManager;

    iget-object v2, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    invoke-interface {v1, p0, v2}, Landroid/view/WindowManager;->updateViewLayout(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 134
    return-void
.end method

.method protected getWindowHeight()I
    .registers 4

    .prologue
    .line 56
    new-instance v0, Landroid/graphics/Rect;

    invoke-direct {v0}, Landroid/graphics/Rect;-><init>()V

    .line 57
    .local v0, "r":Landroid/graphics/Rect;
    invoke-virtual {p0, v0}, Lcom/android/systemui/pac/TriggerOverlayView;->getWindowVisibleDisplayFrame(Landroid/graphics/Rect;)V

    .line 58
    iget v1, v0, Landroid/graphics/Rect;->bottom:I

    iget v2, v0, Landroid/graphics/Rect;->top:I

    sub-int/2addr v1, v2

    return v1
.end method

.method protected hideTriggerRegion()V
    .registers 2

    .prologue
    .line 90
    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lcom/android/systemui/pac/TriggerOverlayView;->setBackgroundColor(I)V

    .line 91
    return-void
.end method

.method protected isKeyguardEnabled()Z
    .registers 4

    .prologue
    .line 146
    iget-object v1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mContext:Landroid/content/Context;

    const-string v2, "keyguard"

    invoke-virtual {v1, v2}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/KeyguardManager;

    .line 147
    .local v0, "km":Landroid/app/KeyguardManager;
    invoke-virtual {v0}, Landroid/app/KeyguardManager;->inKeyguardRestrictedInputMode()Z

    move-result v1

    return v1
.end method

.method protected reduceToTriggerRegion()V
    .registers 3

    .prologue
    .line 137
    invoke-virtual {p0}, Lcom/android/systemui/pac/TriggerOverlayView;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/view/WindowManager$LayoutParams;

    iput-object v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    .line 138
    iget-object v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    iget v1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mTriggerTop:I

    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->y:I

    .line 139
    iget-object v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    iget v1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mTriggerBottom:I

    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->height:I

    .line 140
    iget-object v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    iget v1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mTriggerWidth:I

    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->width:I

    .line 141
    iget-object v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    invoke-virtual {p0}, Lcom/android/systemui/pac/TriggerOverlayView;->disableKeyEvents()I

    move-result v1

    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->flags:I

    .line 142
    iget-object v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mWM:Landroid/view/WindowManager;

    iget-object v1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    invoke-interface {v0, p0, v1}, Landroid/view/WindowManager;->updateViewLayout(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 143
    return-void
.end method

.method protected setBottomPercentage(F)V
    .registers 4
    .param p1, "value"    # F

    .prologue
    .line 105
    invoke-virtual {p0}, Lcom/android/systemui/pac/TriggerOverlayView;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/view/WindowManager$LayoutParams;

    iput-object v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    .line 106
    iget v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mViewHeight:I

    int-to-float v0, v0

    mul-float/2addr v0, p1

    float-to-int v0, v0

    iput v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mTriggerBottom:I

    .line 107
    iget-object v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    iget v1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mTriggerBottom:I

    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->height:I

    .line 109
    :try_start_15
    iget-object v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mWM:Landroid/view/WindowManager;

    iget-object v1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    invoke-interface {v0, p0, v1}, Landroid/view/WindowManager;->updateViewLayout(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V
    :try_end_1c
    .catch Ljava/lang/Exception; {:try_start_15 .. :try_end_1c} :catch_1d

    .line 112
    :goto_1c
    return-void

    .line 110
    :catch_1d
    move-exception v0

    goto :goto_1c
.end method

.method protected setPosition(I)V
    .registers 4
    .param p1, "gravity"    # I

    .prologue
    .line 78
    invoke-virtual {p0}, Lcom/android/systemui/pac/TriggerOverlayView;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/view/WindowManager$LayoutParams;

    iput-object v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    .line 79
    iget-object v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    or-int/lit8 v1, p1, 0x30

    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->gravity:I

    .line 80
    iget-object v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mWM:Landroid/view/WindowManager;

    iget-object v1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    invoke-interface {v0, p0, v1}, Landroid/view/WindowManager;->updateViewLayout(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 81
    invoke-virtual {p0}, Lcom/android/systemui/pac/TriggerOverlayView;->invalidate()V

    .line 82
    return-void
.end method

.method protected setTopPercentage(F)V
    .registers 4
    .param p1, "value"    # F

    .prologue
    .line 94
    invoke-virtual {p0}, Lcom/android/systemui/pac/TriggerOverlayView;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/view/WindowManager$LayoutParams;

    iput-object v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    .line 95
    iget v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mViewHeight:I

    int-to-float v0, v0

    mul-float/2addr v0, p1

    float-to-int v0, v0

    iput v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mTriggerTop:I

    .line 96
    iget-object v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    iget v1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mTriggerTop:I

    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->y:I

    .line 97
    iget-object v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    iget v1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mTriggerBottom:I

    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->height:I

    .line 99
    :try_start_1b
    iget-object v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mWM:Landroid/view/WindowManager;

    iget-object v1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    invoke-interface {v0, p0, v1}, Landroid/view/WindowManager;->updateViewLayout(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V
    :try_end_22
    .catch Ljava/lang/Exception; {:try_start_1b .. :try_end_22} :catch_23

    .line 102
    :goto_22
    return-void

    .line 100
    :catch_23
    move-exception v0

    goto :goto_22
.end method

.method protected setTriggerWidth(I)V
    .registers 4
    .param p1, "value"    # I

    .prologue
    .line 115
    invoke-virtual {p0}, Lcom/android/systemui/pac/TriggerOverlayView;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v0

    check-cast v0, Landroid/view/WindowManager$LayoutParams;

    iput-object v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    .line 116
    iput p1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mTriggerWidth:I

    .line 117
    iget-object v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    iget v1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mTriggerWidth:I

    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->width:I

    .line 119
    :try_start_10
    iget-object v0, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mWM:Landroid/view/WindowManager;

    iget-object v1, p0, Lcom/android/systemui/pac/TriggerOverlayView;->mLayoutParams:Landroid/view/WindowManager$LayoutParams;

    invoke-interface {v0, p0, v1}, Landroid/view/WindowManager;->updateViewLayout(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V
    :try_end_17
    .catch Ljava/lang/Exception; {:try_start_10 .. :try_end_17} :catch_18

    .line 122
    :goto_17
    return-void

    .line 120
    :catch_18
    move-exception v0

    goto :goto_17
.end method

.method protected showTriggerRegion()V
    .registers 3

    .prologue
    .line 86
    const-string v0, "trigger_region"

    const-string v1, "drawable"

    invoke-static {v0, v1}, Lcom/bamzzz/ComotID;->Get(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/android/systemui/pac/TriggerOverlayView;->setBackgroundResource(I)V

    .line 87
    return-void
.end method
