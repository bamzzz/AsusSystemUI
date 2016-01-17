.class public Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;
.super Landroid/graphics/drawable/Drawable;
.source "BarTransitions.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/systemui/statusbar/phone/BarTransitions;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0xc
    name = "BarBackgroundDrawable"
.end annotation


# instance fields
.field private mAnimating:Z

.field private mColor:I

.field private mColorStart:I

.field private mEndTime:J

.field private final mGradient:Landroid/graphics/drawable/Drawable;

.field private mGradientAlpha:I

.field private mGradientAlphaStart:I

.field private final mInterpolator:Landroid/animation/TimeInterpolator;

.field private mIsNav:Z

.field private mMode:I

.field private final mOpaque:I

.field private mPrivate:I

.field private final mRecordWarning:I

.field private final mSemiTransparent:I

.field private mStartTime:J

.field private final mTranslucent:I

.field private final mTransparent:I

.field private mView:Landroid/view/View;

.field private final mWarning:I


# direct methods
.method public constructor <init>(ZLandroid/view/View;I)V
    .registers 8
    .param p1, "var1"    # Z
    .param p2, "var2"    # Landroid/view/View;
    .param p3, "var3"    # I

    .prologue
    .line 140
    invoke-direct {p0}, Landroid/graphics/drawable/Drawable;-><init>()V

    .line 128
    const/4 v1, -0x1

    iput v1, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mMode:I

    .line 141
    invoke-virtual {p2}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    .line 142
    .local v0, "var4":Landroid/content/res/Resources;
    invoke-virtual {p2}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v1

    const-string v2, "color"

    const-string v3, "battery_saver_mode_color"

    invoke-static {v1, v2, v3}, Lcom/asus/systemui/util/SystemUiUtil;->getIdentifier(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getColor(I)I

    move-result v1

    iput v1, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mOpaque:I

    .line 143
    invoke-virtual {p2}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v1

    const-string v2, "color"

    const-string v3, "battery_saver_mode_color"

    invoke-static {v1, v2, v3}, Lcom/asus/systemui/util/SystemUiUtil;->getIdentifier(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getColor(I)I

    move-result v1

    iput v1, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mSemiTransparent:I

    .line 144
    invoke-virtual {p2}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v1

    const-string v2, "color"

    const-string v3, "battery_saver_mode_color"

    invoke-static {v1, v2, v3}, Lcom/asus/systemui/util/SystemUiUtil;->getIdentifier(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getColor(I)I

    move-result v1

    iput v1, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mTranslucent:I

    .line 145
    invoke-virtual {p2}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v1

    const-string v2, "color"

    const-string v3, "battery_saver_mode_color"

    invoke-static {v1, v2, v3}, Lcom/asus/systemui/util/SystemUiUtil;->getIdentifier(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getColor(I)I

    move-result v1

    iput v1, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mTransparent:I

    .line 146
    invoke-virtual {p2}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v1

    const-string v2, "color"

    const-string v3, "battery_saver_mode_color"

    invoke-static {v1, v2, v3}, Lcom/asus/systemui/util/SystemUiUtil;->getIdentifier(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getColor(I)I

    move-result v1

    iput v1, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mWarning:I

    .line 147
    invoke-virtual {p2}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v1

    const-string v2, "color"

    const-string v3, "battery_saver_mode_color"

    invoke-static {v1, v2, v3}, Lcom/asus/systemui/util/SystemUiUtil;->getIdentifier(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getColor(I)I

    move-result v1

    iput v1, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mPrivate:I

    .line 148
    invoke-virtual {p2}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v1

    const-string v2, "color"

    const-string v3, "battery_saver_mode_color"

    invoke-static {v1, v2, v3}, Lcom/asus/systemui/util/SystemUiUtil;->getIdentifier(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getColor(I)I

    move-result v1

    iput v1, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mRecordWarning:I

    .line 149
    invoke-virtual {v0, p3}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    iput-object v1, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mGradient:Landroid/graphics/drawable/Drawable;

    .line 150
    new-instance v1, Landroid/view/animation/LinearInterpolator;

    invoke-direct {v1}, Landroid/view/animation/LinearInterpolator;-><init>()V

    iput-object v1, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mInterpolator:Landroid/animation/TimeInterpolator;

    .line 151
    iput-object p2, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mView:Landroid/view/View;

    .line 152
    iput-boolean p1, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mIsNav:Z

    .line 153
    return-void
.end method


# virtual methods
.method public applyModeBackground(IIZ)V
    .registers 8
    .param p1, "var1"    # I
    .param p2, "var2"    # I
    .param p3, "var3"    # Z

    .prologue
    .line 156
    iget v2, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mMode:I

    if-eq v2, p2, :cond_20

    .line 157
    iput p2, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mMode:I

    .line 158
    iput-boolean p3, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mAnimating:Z

    .line 159
    if-eqz p3, :cond_1d

    .line 160
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v0

    .line 161
    .local v0, "var4":J
    iput-wide v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mStartTime:J

    .line 162
    const-wide/16 v2, 0xc8

    add-long/2addr v2, v0

    iput-wide v2, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mEndTime:J

    .line 163
    iget v2, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mGradientAlpha:I

    iput v2, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mGradientAlphaStart:I

    .line 164
    iget v2, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mColor:I

    iput v2, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mColorStart:I

    .line 167
    .end local v0    # "var4":J
    :cond_1d
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->invalidateSelf()V

    .line 170
    :cond_20
    return-void
.end method

.method public draw(Landroid/graphics/Canvas;)V
    .registers 16
    .param p1, "var1"    # Landroid/graphics/Canvas;

    .prologue
    const/4 v13, 0x7

    const/4 v4, 0x0

    const/high16 v12, 0x3f800000    # 1.0f

    const/4 v1, 0x0

    .line 174
    iget v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mMode:I

    const/16 v2, 0x8

    if-ne v0, v2, :cond_5d

    .line 175
    iget v7, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mRecordWarning:I

    .line 190
    .local v7, "var3":I
    :goto_d
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mAnimating:Z

    if-nez v0, :cond_8c

    .line 191
    iput v7, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mColor:I

    .line 192
    iput v4, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mGradientAlpha:I

    .line 207
    :goto_15
    const/16 v0, 0xff

    iput v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mGradientAlpha:I

    .line 208
    iget v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mGradientAlpha:I

    if-lez v0, :cond_29

    .line 209
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mGradient:Landroid/graphics/drawable/Drawable;

    iget v2, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mGradientAlpha:I

    invoke-virtual {v0, v2}, Landroid/graphics/drawable/Drawable;->setAlpha(I)V

    .line 210
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mGradient:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v0, p1}, Landroid/graphics/drawable/Drawable;->draw(Landroid/graphics/Canvas;)V

    .line 213
    :cond_29
    iget v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mColor:I

    invoke-static {v0}, Landroid/graphics/Color;->alpha(I)I

    move-result v0

    if-lez v0, :cond_55

    .line 214
    iget v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mMode:I

    if-ne v0, v13, :cond_130

    .line 215
    new-instance v5, Landroid/graphics/Paint;

    invoke-direct {v5}, Landroid/graphics/Paint;-><init>()V

    .line 216
    .local v5, "var6":Landroid/graphics/Paint;
    iget v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mColor:I

    invoke-virtual {v5, v0}, Landroid/graphics/Paint;->setColor(I)V

    .line 217
    const/high16 v0, 0x40a00000    # 5.0f

    invoke-virtual {v5, v0}, Landroid/graphics/Paint;->setStrokeWidth(F)V

    .line 218
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mIsNav:Z

    if-nez v0, :cond_115

    .line 219
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mView:Landroid/view/View;

    invoke-virtual {v0}, Landroid/view/View;->getWidth()I

    move-result v0

    int-to-float v3, v0

    move-object v0, p1

    move v2, v1

    move v4, v1

    invoke-virtual/range {v0 .. v5}, Landroid/graphics/Canvas;->drawLine(FFFFLandroid/graphics/Paint;)V

    .line 228
    .end local v5    # "var6":Landroid/graphics/Paint;
    :cond_55
    :goto_55
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mAnimating:Z

    if-eqz v0, :cond_5c

    .line 229
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->invalidateSelf()V

    .line 232
    :cond_5c
    return-void

    .line 176
    .end local v7    # "var3":I
    :cond_5d
    iget v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mMode:I

    const/4 v2, 0x5

    if-ne v0, v2, :cond_65

    .line 177
    iget v7, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mWarning:I

    .restart local v7    # "var3":I
    goto :goto_d

    .line 178
    .end local v7    # "var3":I
    :cond_65
    iget v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mMode:I

    const/4 v2, 0x2

    if-ne v0, v2, :cond_6d

    .line 179
    iget v7, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mTranslucent:I

    .restart local v7    # "var3":I
    goto :goto_d

    .line 180
    .end local v7    # "var3":I
    :cond_6d
    iget v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mMode:I

    const/4 v2, 0x1

    if-ne v0, v2, :cond_75

    .line 181
    iget v7, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mSemiTransparent:I

    .restart local v7    # "var3":I
    goto :goto_d

    .line 182
    .end local v7    # "var3":I
    :cond_75
    iget v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mMode:I

    const/4 v2, 0x4

    if-ne v0, v2, :cond_7d

    .line 183
    iget v7, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mTransparent:I

    .restart local v7    # "var3":I
    goto :goto_d

    .line 184
    .end local v7    # "var3":I
    :cond_7d
    iget v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mMode:I

    const/4 v2, 0x6

    if-eq v0, v2, :cond_89

    iget v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mMode:I

    if-eq v0, v13, :cond_89

    .line 185
    iget v7, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mOpaque:I

    .restart local v7    # "var3":I
    goto :goto_d

    .line 187
    .end local v7    # "var3":I
    :cond_89
    iget v7, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mPrivate:I

    .restart local v7    # "var3":I
    goto :goto_d

    .line 194
    :cond_8c
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v8

    .line 195
    .local v8, "var4":J
    iget-wide v2, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mEndTime:J

    cmp-long v0, v8, v2

    if-ltz v0, :cond_9e

    .line 196
    iput-boolean v4, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mAnimating:Z

    .line 197
    iput v7, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mColor:I

    .line 198
    iput v4, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mGradientAlpha:I

    goto/16 :goto_15

    .line 200
    :cond_9e
    iget-wide v2, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mStartTime:J

    sub-long v2, v8, v2

    long-to-float v0, v2

    iget-wide v2, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mEndTime:J

    iget-wide v10, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mStartTime:J

    sub-long/2addr v2, v10

    long-to-float v2, v2

    div-float v6, v0, v2

    .line 201
    .local v6, "var2":F
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mInterpolator:Landroid/animation/TimeInterpolator;

    invoke-interface {v0, v6}, Landroid/animation/TimeInterpolator;->getInterpolation(F)F

    move-result v0

    invoke-static {v0, v12}, Ljava/lang/Math;->min(FF)F

    move-result v0

    invoke-static {v1, v0}, Ljava/lang/Math;->max(FF)F

    move-result v6

    .line 202
    mul-float v0, v1, v6

    iget v2, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mGradientAlphaStart:I

    int-to-float v2, v2

    sub-float v3, v12, v6

    mul-float/2addr v2, v3

    add-float/2addr v0, v2

    float-to-int v0, v0

    iput v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mGradientAlpha:I

    .line 203
    invoke-static {v7}, Landroid/graphics/Color;->alpha(I)I

    move-result v0

    int-to-float v0, v0

    mul-float/2addr v0, v6

    iget v2, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mColorStart:I

    invoke-static {v2}, Landroid/graphics/Color;->alpha(I)I

    move-result v2

    int-to-float v2, v2

    sub-float v3, v12, v6

    mul-float/2addr v2, v3

    add-float/2addr v0, v2

    float-to-int v0, v0

    invoke-static {v7}, Landroid/graphics/Color;->red(I)I

    move-result v2

    int-to-float v2, v2

    mul-float/2addr v2, v6

    iget v3, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mColorStart:I

    invoke-static {v3}, Landroid/graphics/Color;->red(I)I

    move-result v3

    int-to-float v3, v3

    sub-float v4, v12, v6

    mul-float/2addr v3, v4

    add-float/2addr v2, v3

    float-to-int v2, v2

    invoke-static {v7}, Landroid/graphics/Color;->green(I)I

    move-result v3

    int-to-float v3, v3

    mul-float/2addr v3, v6

    iget v4, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mColorStart:I

    invoke-static {v4}, Landroid/graphics/Color;->green(I)I

    move-result v4

    int-to-float v4, v4

    sub-float v10, v12, v6

    mul-float/2addr v4, v10

    add-float/2addr v3, v4

    float-to-int v3, v3

    invoke-static {v7}, Landroid/graphics/Color;->blue(I)I

    move-result v4

    int-to-float v4, v4

    mul-float/2addr v4, v6

    iget v10, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mColorStart:I

    invoke-static {v10}, Landroid/graphics/Color;->blue(I)I

    move-result v10

    int-to-float v10, v10

    sub-float v11, v12, v6

    mul-float/2addr v10, v11

    add-float/2addr v4, v10

    float-to-int v4, v4

    invoke-static {v0, v2, v3, v4}, Landroid/graphics/Color;->argb(IIII)I

    move-result v0

    iput v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mColor:I

    goto/16 :goto_15

    .line 221
    .end local v6    # "var2":F
    .end local v8    # "var4":J
    .restart local v5    # "var6":Landroid/graphics/Paint;
    :cond_115
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mView:Landroid/view/View;

    invoke-virtual {v0}, Landroid/view/View;->getHeight()I

    move-result v0

    int-to-float v2, v0

    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mView:Landroid/view/View;

    invoke-virtual {v0}, Landroid/view/View;->getWidth()I

    move-result v0

    int-to-float v3, v0

    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mView:Landroid/view/View;

    invoke-virtual {v0}, Landroid/view/View;->getHeight()I

    move-result v0

    int-to-float v4, v0

    move-object v0, p1

    invoke-virtual/range {v0 .. v5}, Landroid/graphics/Canvas;->drawLine(FFFFLandroid/graphics/Paint;)V

    goto/16 :goto_55

    .line 224
    .end local v5    # "var6":Landroid/graphics/Paint;
    :cond_130
    iget v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mColor:I

    invoke-virtual {p1, v0}, Landroid/graphics/Canvas;->drawColor(I)V

    goto/16 :goto_55
.end method

.method public finishAnimation()V
    .registers 2

    .prologue
    .line 235
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mAnimating:Z

    if-eqz v0, :cond_a

    .line 236
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mAnimating:Z

    .line 237
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->invalidateSelf()V

    .line 240
    :cond_a
    return-void
.end method

.method public getOpacity()I
    .registers 2

    .prologue
    .line 243
    const/4 v0, -0x3

    return v0
.end method

.method protected onBoundsChange(Landroid/graphics/Rect;)V
    .registers 3
    .param p1, "var1"    # Landroid/graphics/Rect;

    .prologue
    .line 247
    invoke-super {p0, p1}, Landroid/graphics/drawable/Drawable;->onBoundsChange(Landroid/graphics/Rect;)V

    .line 248
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mGradient:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v0, p1}, Landroid/graphics/drawable/Drawable;->setBounds(Landroid/graphics/Rect;)V

    .line 249
    return-void
.end method

.method public setAlpha(I)V
    .registers 2
    .param p1, "var1"    # I

    .prologue
    .line 252
    return-void
.end method

.method public setColorFilter(Landroid/graphics/ColorFilter;)V
    .registers 2
    .param p1, "var1"    # Landroid/graphics/ColorFilter;

    .prologue
    .line 255
    return-void
.end method

.method public setSnapViewColor(I)V
    .registers 3
    .param p1, "var1"    # I

    .prologue
    .line 258
    const/high16 v0, -0x1000000

    add-int/2addr v0, p1

    iput v0, p0, Lcom/android/systemui/statusbar/phone/BarTransitions$BarBackgroundDrawable;->mPrivate:I

    .line 259
    return-void
.end method
