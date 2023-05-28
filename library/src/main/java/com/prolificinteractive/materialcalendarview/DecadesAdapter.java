package com.prolificinteractive.materialcalendarview;

import android.support.annotation.NonNull;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.temporal.WeekFields;

public class DecadesAdapter extends CalendarPagerAdapter<DecadeView> {

    public DecadesAdapter(MaterialCalendarView mcv) { super(mcv); }

    @Override
    protected DecadeView createView(int position) {
        return new DecadeView(mcv, getItem(position), mcv.getFirstDayOfWeek());
    }

    @Override
    protected int indexOf(DecadeView view) {
        CalendarDay year = view.getFirstViewDay();
        return getRangeIndex().indexOf(year);
    }

    @Override
    protected boolean isInstanceOfView(Object object) {
        return object instanceof DecadeView;
    }

    @Override
    protected DateRangeIndex createRangeIndex(CalendarDay min, CalendarDay max) {
        return new Decadely(min, max, mcv.getFirstDayOfWeek());
    }

    public static class Decadely implements DateRangeIndex {

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

        public Decadely(
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
            return (day.getYear() - min.getYear()) / 12;
        }

        @Override public CalendarDay getItem(final int position) {
            return CalendarDay.from(min.getDate().plusYears(position * 12L));
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