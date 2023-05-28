package com.prolificinteractive.materialcalendarview;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.util.Collection;

@SuppressLint("ViewConstructor")
class YearView extends CalendarPagerView {

    public YearView(
            @NonNull final MaterialCalendarView view,
            final CalendarDay firstViewDay,
            final DayOfWeek firstDayOfWeek) {
        super(view, firstViewDay, firstDayOfWeek, false);
    }

    @Override protected void buildDayViews(
            final Collection<DayView> dayViews,
            final LocalDate calendar,
            int position, CalendarDay firstViewDay) {
        LocalDate temp = LocalDate.of(calendar.getYear(),1,calendar.getDayOfMonth());
        for (int i = 0; i < 12; i++) {
            addMonthView(dayViews, temp);
            temp = temp.plusMonths(1);
        }
    }

    @Override protected boolean isDayEnabled(CalendarDay day) {
        return true;
    }

    @Override protected int getRows() {
        return 3;
    }

    @Override
    protected int getColumns() {
        return 4;
    }
}