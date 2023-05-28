package com.prolificinteractive.materialcalendarview;

import android.animation.Animator;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

class TitleChanger {

  public static final int DEFAULT_ANIMATION_DELAY = 400;
  public static final int DEFAULT_Y_TRANSLATION_DP = 20;

  private final TextView title;
  @NonNull private TitleFormatter titleFormatter = TitleFormatter.DEFAULT;

  private final int animDelay;
  private final int animDuration;
  private final int translate;
  private final Interpolator interpolator = new DecelerateInterpolator(2f);

  private int orientation = MaterialCalendarView.VERTICAL;

  private long lastAnimTime = 0;
  private CalendarDay previousDate = null;
  private CalendarMode previousMode = null;

  public TitleChanger(TextView title) {
    this.title = title;

    Resources res = title.getResources();

    animDelay = DEFAULT_ANIMATION_DELAY;

    animDuration = res.getInteger(android.R.integer.config_shortAnimTime) / 2;

    translate = (int) TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, DEFAULT_Y_TRANSLATION_DP, res.getDisplayMetrics()
    );
  }

  public void change(final CalendarDay currentDate, CalendarMode mode, CalendarDay minDate) {
    long currentTime = System.currentTimeMillis();

    if (currentDate == null) {
      return;
    }

    if (minDate == null) {
      CalendarDay today = CalendarDay.today();
      minDate = CalendarDay.from(today.getYear() - 200, today.getMonth(), today.getDay());
    }

    if (TextUtils.isEmpty(title.getText()) || (currentTime - lastAnimTime) < animDelay) {
      doChange(currentTime, currentDate, false, mode, minDate);
    }

    if (mode == CalendarMode.MONTHS && (currentDate.getMonth() == previousDate.getMonth()
                    && currentDate.getYear() == previousDate.getYear() && previousMode == mode)) {
      return;
    }

    if (mode == CalendarMode.YEARS && (currentDate.getYear() == previousDate.getYear()
            && previousMode == mode)) {
      return;
    }

    if (mode == CalendarMode.DECADES
            && (currentDate.getYear() - minDate.getYear())/12 == (previousDate.getYear() - minDate.getYear())/12
            && previousMode == mode) {
      return;
    }

    doChange(currentTime, currentDate, true, mode, minDate);
  }

  private void doChange(final long now, final CalendarDay currentDate, boolean animate, CalendarMode mode, CalendarDay minDate) {

    title.animate().cancel();
    doTranslation(title, 0);

    title.setAlpha(1);
    lastAnimTime = now;

    final CharSequence newTitle = titleFormatter.format(currentDate, mode, minDate);

    if (!animate) {
      title.setText(newTitle);
    } else {
      final int translation = translate * (previousDate.isBefore(currentDate) ? 1 : -1);
      final ViewPropertyAnimator viewPropertyAnimator = title.animate();

      if (orientation == MaterialCalendarView.HORIZONTAL) {
        viewPropertyAnimator.translationX(translation * -1);
      } else {
        viewPropertyAnimator.translationY(translation * -1);
      }

      viewPropertyAnimator
          .alpha(0)
          .setDuration(animDuration)
          .setInterpolator(interpolator)
          .setListener(new AnimatorListener() {

            @Override
            public void onAnimationCancel(Animator animator) {
              doTranslation(title, 0);
              title.setAlpha(1);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
              title.setText(newTitle);
              doTranslation(title, translation);

              final ViewPropertyAnimator viewPropertyAnimator = title.animate();
              if (orientation == MaterialCalendarView.HORIZONTAL) {
                viewPropertyAnimator.translationX(0);
              } else {
                viewPropertyAnimator.translationY(0);
              }

              viewPropertyAnimator
                  .alpha(1)
                  .setDuration(animDuration)
                  .setInterpolator(interpolator)
                  .setListener(new AnimatorListener())
                  .start();
            }
          }).start();
    }

    previousDate = currentDate;
    previousMode = mode;
  }

  private void doTranslation(final TextView title, final int translate) {
    if (orientation == MaterialCalendarView.HORIZONTAL) {
      title.setTranslationX(translate);
    } else {
      title.setTranslationY(translate);
    }
  }

  public void setTitleFormatter(@Nullable final TitleFormatter titleFormatter) {
    this.titleFormatter = titleFormatter == null ? TitleFormatter.DEFAULT : titleFormatter;
  }

  public void setOrientation(int orientation) {
    this.orientation = orientation;
  }

  public int getOrientation() {
    return orientation;
  }

  public void setPreviousMonth(CalendarDay previousMonth) {
    this.previousDate = previousMonth;
  }
}
