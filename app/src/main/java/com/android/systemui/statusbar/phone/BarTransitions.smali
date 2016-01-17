.class public Lcom/android/systemui/statusbar/phone/BarTransitions;
.super Ljava/lang/Object;
.source "BarTransitions.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;
    }
.end annotation


# static fields
.field public static final HIGH_END:Z


# instance fields
.field private final mBarBackground:Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;

.field private mMode:I

.field private final mTag:Ljava/lang/String;

.field private final mView:Landroid/view/View;


# direct methods
.method public constructor <init>(ZLandroid/view/View;I)V
    .registers 6
    .param p1, "var1"    # Z
    .param p2, "var2"    # Landroid/view/View;
    .param p3, "var3"    # I

    .prologue
    .line 26
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 27
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "BarTransitions."

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {p2}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Class;->getSimpleName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions;->mTag:Ljava/lang/String;

    .line 28
    iput-object p2, p0, Lcom/android/systemui/statusbar/phone/BarTransitions;->mView:Landroid/view/View;

    .line 29
    new-instance v0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/BarTransitions;->mView:Landroid/view/View;

    invoke-direct {v0, p1, v1, p3}, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;-><init>(ZLandroid/view/View;I)V

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions;->mBarBackground:Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;

    .line 34
    return-void
.end method

.method public static modeToString(I)Ljava/lang/String;
    .registers 4
    .param p0, "var0"    # I

    .prologue
    .line 38
    if-nez p0, :cond_5

    .line 39
    const-string v0, "MODE_OPAQUE"

    .line 60
    .local v0, "var1":Ljava/lang/String;
    :goto_4
    return-object v0

    .line 40
    .end local v0    # "var1":Ljava/lang/String;
    :cond_5
    const/4 v1, 0x1

    if-ne p0, v1, :cond_b

    .line 41
    const-string v0, "MODE_SEMI_TRANSPARENT"

    .restart local v0    # "var1":Ljava/lang/String;
    goto :goto_4

    .line 42
    .end local v0    # "var1":Ljava/lang/String;
    :cond_b
    const/4 v1, 0x2

    if-ne p0, v1, :cond_11

    .line 43
    const-string v0, "MODE_TRANSLUCENT"

    .restart local v0    # "var1":Ljava/lang/String;
    goto :goto_4

    .line 44
    .end local v0    # "var1":Ljava/lang/String;
    :cond_11
    const/4 v1, 0x3

    if-ne p0, v1, :cond_17

    .line 45
    const-string v0, "MODE_LIGHTS_OUT"

    .restart local v0    # "var1":Ljava/lang/String;
    goto :goto_4

    .line 46
    .end local v0    # "var1":Ljava/lang/String;
    :cond_17
    const/4 v1, 0x4

    if-ne p0, v1, :cond_1d

    .line 47
    const-string v0, "MODE_TRANSPARENT"

    .restart local v0    # "var1":Ljava/lang/String;
    goto :goto_4

    .line 48
    .end local v0    # "var1":Ljava/lang/String;
    :cond_1d
    const/4 v1, 0x5

    if-ne p0, v1, :cond_23

    .line 49
    const-string v0, "MODE_WARNING"

    .restart local v0    # "var1":Ljava/lang/String;
    goto :goto_4

    .line 50
    .end local v0    # "var1":Ljava/lang/String;
    :cond_23
    const/4 v1, 0x6

    if-ne p0, v1, :cond_29

    .line 51
    const-string v0, "MODE_SNAPVIEW_OBVIOUS"

    .restart local v0    # "var1":Ljava/lang/String;
    goto :goto_4

    .line 52
    .end local v0    # "var1":Ljava/lang/String;
    :cond_29
    const/4 v1, 0x7

    if-ne p0, v1, :cond_2f

    .line 53
    const-string v0, "MODE_SNAPVIEW_SUBTLE"

    .restart local v0    # "var1":Ljava/lang/String;
    goto :goto_4

    .line 54
    .end local v0    # "var1":Ljava/lang/String;
    :cond_2f
    const/16 v1, 0x8

    if-ne p0, v1, :cond_36

    .line 55
    const-string v0, "MODE_RECORD_WARNING"

    .restart local v0    # "var1":Ljava/lang/String;
    goto :goto_4

    .line 57
    .end local v0    # "var1":Ljava/lang/String;
    :cond_36
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "UNKNOWN_MODE (value:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, ")"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .restart local v0    # "var1":Ljava/lang/String;
    goto :goto_4
.end method


# virtual methods
.method protected applyModeBackground(IIZ)V
    .registers 5
    .param p1, "var1"    # I
    .param p2, "var2"    # I
    .param p3, "var3"    # Z

    .prologue
    .line 64
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions;->mBarBackground:Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;

    invoke-virtual {v0, p1, p2, p3}, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->applyModeBackground(IIZ)V

    .line 65
    return-void
.end method

.method public finishAnimations()V
    .registers 2

    .prologue
    .line 68
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions;->mBarBackground:Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;

    invoke-virtual {v0}, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->finishAnimation()V

    .line 69
    return-void
.end method

.method public getMode()I
    .registers 2

    .prologue
    .line 72
    iget v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions;->mMode:I

    return v0
.end method

.method protected onTransition(IIZ)V
    .registers 4
    .param p1, "var1"    # I
    .param p2, "var2"    # I
    .param p3, "var3"    # Z

    .prologue
    .line 80
    return-void
.end method

.method public setSnapViewColor(I)V
    .registers 3
    .param p1, "var1"    # I

    .prologue
    .line 83
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions;->mBarBackground:Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;

    invoke-virtual {v0, p1}, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->setSnapViewColor(I)V

    .line 84
    return-void
.end method

.method public transitionTo(IZ)V
    .registers 5
    .param p1, "var1"    # I
    .param p2, "var2"    # Z

    .prologue
    .line 92
    move v0, p1

    .line 95
    .local v0, "var3":I
    const/4 v1, 0x1

    if-eq p1, v1, :cond_19

    const/4 v1, 0x2

    if-eq p1, v1, :cond_19

    .line 96
    move v0, p1

    .line 97
    const/4 v1, 0x4

    if-eq p1, v1, :cond_19

    .line 106
    :goto_b
    iget v1, p0, Lcom/android/systemui/statusbar/phone/BarTransitions;->mMode:I

    if-eq v1, v0, :cond_18

    .line 107
    iget p1, p0, Lcom/android/systemui/statusbar/phone/BarTransitions;->mMode:I

    .line 108
    iput v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions;->mMode:I

    .line 109
    iget v1, p0, Lcom/android/systemui/statusbar/phone/BarTransitions;->mMode:I

    invoke-virtual {p0, p1, v1, p2}, Lcom/android/systemui/statusbar/phone/BarTransitions;->onTransition(IIZ)V

    .line 112
    :cond_18
    return-void

    .line 102
    :cond_19
    const/4 v0, 0x0

    goto :goto_b
.end method

.method public updateBarBackground()V
    .registers 2

    .prologue
    .line 115
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions;->mBarBackground:Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;

    invoke-virtual {v0}, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->invalidateSelf()V

    .line 116
    return-void
.end method

.method public updateBattery(Lcom/android/systemui/AbstractBatteryView;)V
    .registers 2
    .param p1, "battery"    # Lcom/android/systemui/AbstractBatteryView;

    .prologue
    .line 88
    return-void
.end method
