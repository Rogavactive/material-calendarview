package com.prolificinteractive.materialcalendarview.format;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;

import org.threeten.bp.format.DateTimeFormatter;

/**
 * Format using a {@linkplain java.text.DateFormat} instance.
 */
public class DateFormatTitleFormatter implements TitleFormatter {

  private final DateTimeFormatter monthDateFormat;
  private final DateTimeFormatter yearDateFormat;

  /**
   * Format using {@link TitleFormatter#DEFAULT_MONTH_FORMAT} for formatting.
   */
  public DateFormatTitleFormatter() {
    this(DateTimeFormatter.ofPattern(DEFAULT_MONTH_FORMAT), DateTimeFormatter.ofPattern(DEFAULT_YEAR_FORMAT));
  }

  /**
   * Format using a specified {@linkplain DateTimeFormatter}
   *
   * @param monthFormat the monthFormat to use
   * @param yearFormat the yearFormat to use
   */
  public DateFormatTitleFormatter(final DateTimeFormatter monthFormat,
                                  final DateTimeFormatter yearFormat) {
    this.monthDateFormat = monthFormat;
    this.yearDateFormat = yearFormat;
  }

  /**
   * {@inheritDoc}
   */
  @Override public CharSequence format(final CalendarDay day, CalendarMode mode, CalendarDay minDate) {
    switch (mode) {
      case YEARS:
        return yearDateFormat.format(day.getDate());
      case DECADES:
        int yearDiff = (day.getYear() - minDate.getYear()) % 12;
        int startYear = day.getYear() - yearDiff;
        int endYear = startYear + 11;
        return startYear + " - " + endYear;
      default:
        return monthDateFormat.format(day.getDate());
    }
  }
}
