package com.prolificinteractive.materialcalendarview.format;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;

/**
 * Used to format a {@linkplain com.prolificinteractive.materialcalendarview.CalendarDay} to a string for the month/year title
 */
public interface TitleFormatter {

  String DEFAULT_MONTH_FORMAT = "LLLL yyyy";
  String DEFAULT_YEAR_FORMAT = "yyyy";

  TitleFormatter DEFAULT = new DateFormatTitleFormatter();

  /**
   * Converts the supplied day to a suitable month/year title
   *
   * @param day the day containing relevant month and year information
   * @return a label to display for the given month/year
   */
  CharSequence format(CalendarDay day, CalendarMode mode, CalendarDay minDate);
}
