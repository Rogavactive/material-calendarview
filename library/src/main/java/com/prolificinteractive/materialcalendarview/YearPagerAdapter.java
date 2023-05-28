package com.prolificinteractive.materialcalendarview;

import android.support.annotation.NonNull;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.temporal.ChronoUnit;
import org.threeten.bp.temporal.WeekFields;

public class YearPagerAdapter extends CalendarPagerAdapter<YearView> {

    public YearPagerAdapter(MaterialCalendarView mcv) { super(mcv); }

    @Override
    protected YearView createView(int position) {
        return new YearView(mcv, getItem(position), mcv.getFirstDayOfWeek());
    }

    @Override
    protected int indexOf(YearView view) {
        CalendarDay year = view.getFirstViewDay();
        return getRangeIndex().indexOf(year);
    }

    @Override
    protected boolean isInstanceOfView(Object object) {
        return object instanceof WeekView;
    }

    @Override
    protected DateRangeIndex createRangeIndex(CalendarDay min, CalendarDay max) {
        return new Yearly(min, max, mcv.getFirstDayOfWeek());
    }

    public static class Yearly implements DateRangeIndex {

        /**
         * Minimum day of the first week to display.
         */
        private final CalendarDay min;

        /**
         * Number of weeks to show.
         */
        private final int count;

        /**
         * First day of the week to base the weeks on.
         */
        private final DayOfWeek firstDayOfWeek;

        public Yearly(
                @NonNull final CalendarDay min,
                @NonNull final CalendarDay max,
                final DayOfWeek firstDayOfWeek) {
            this.firstDayOfWeek = firstDayOfWeek;
            this.min = getFirstDayOfWeek(min);
            this.count = indexOf(max) + 1;
        }

        @Override public int getCount() {
            return count;
        }

        @Override public int indexOf(final CalendarDay day) {
            return day.getYear() - min.getYear();
        }

        @Override public CalendarDay getItem(final int position) {
            return CalendarDay.from(min.getDate().plusYears(position));
        }

        /**
         * Getting the first day of a week for a specific date based on a specific week day as first
         * day.
         */
        private CalendarDay getFirstDayOfWeek(@NonNull final CalendarDay day) {
            final LocalDate temp = day.getDate().with(WeekFields.of(firstDayOfWeek, 1).dayOfWeek(), 1L);
            return CalendarDay.from(temp);
        }
    }

}