package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;

import com.prolificinteractive.materialcalendarview.format.DayFormatter;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MonthsView extends DayView{

    public MonthsView(Context context, CalendarDay day) {
        super(context, day);
    }

    @NonNull
    @Override
    public String getLabel() {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM", Locale.getDefault());
        return LocalDate.of(date.getYear(), date.getMonth(), date.getDay()).format(parser);
    }

}
