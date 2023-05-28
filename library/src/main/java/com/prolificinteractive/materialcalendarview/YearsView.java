package com.prolificinteractive.materialcalendarview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Locale;

@SuppressLint("ViewConstructor")
public class YearsView extends DayView{

    public YearsView(Context context, CalendarDay day) {
        super(context, day);
    }

    @NonNull
    @Override
    public String getLabel() {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy", Locale.getDefault());
        return LocalDate.of(date.getYear(), date.getMonth(), date.getDay()).format(parser);
    }

}
