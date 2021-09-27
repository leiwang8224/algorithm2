package AlgoExpert.VeryHard;

import java.util.*;
public class CalendarMatching {
    // O(c1 + c2) time | O(c1 + c2) space - where c1 and c2 are the respective numbers of meetings
    // in calendar1 and calendar2
    List<StringMeeting> calendarMatching (List<StringMeeting> calendar1,
                                          StringMeeting dailyBounds1,
                                          List<StringMeeting> calendar2,
                                          StringMeeting dailyBounds2,
                                          int meetingDuration) {
        List<Meeting> updatedCalendar1InMinutes = updateCalendarTimeToMinutes(calendar1, dailyBounds1);
        List<Meeting> updatedCalendar2InMinutes = updateCalendarTimeToMinutes(calendar2, dailyBounds2);
        List<Meeting> mergedCalendar = mergeCalendarsTakeEarliestStartTime(updatedCalendar1InMinutes, updatedCalendar2InMinutes);
        List<Meeting> flattenedCalendar = flattenCalendarTakeLatestEndTime(mergedCalendar);
        return getMatchingAvailabilities(flattenedCalendar, meetingDuration);
    }

    private List<StringMeeting> getMatchingAvailabilities(List<Meeting> calendar, int meetingDuration) {
        List<Meeting> matchingAvailability = new ArrayList<>();
        for (int i = 1; i < calendar.size(); i++) {
            int start = calendar.get(i - 1).end;
            int end = calendar.get(i).start;
            int availabilityDuration = end - start;
            if (availabilityDuration >= meetingDuration) {
                matchingAvailability.add(new Meeting(start, end));
            }
        }
        List<StringMeeting> matchingAvailabilityInHours = new ArrayList<>();
        for (int i = 0; i <matchingAvailability.size(); i++) {
            matchingAvailabilityInHours.add(
                    new StringMeeting(minutesToTime(matchingAvailability.get(i).start),
                            minutesToTime(matchingAvailability.get(i).end)));
        }
        return matchingAvailabilityInHours;
    }

    private String minutesToTime(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;
        String hoursString = Integer.toString(hours);
        String minutesString = mins < 10 ? "0" + Integer.toString(mins) : Integer.toString(mins);
        return hoursString + ":" + minutesString;
    }

    private List<Meeting> flattenCalendarTakeLatestEndTime(List<Meeting> calendar) {
        List<Meeting> flattened = new ArrayList<>();
        flattened.add(calendar.get(0));
        for (int i = 1; i < calendar.size(); i++) {
            Meeting curMeeting = calendar.get(i);
            Meeting prevMeetingFlattened = flattened.get(flattened.size() -1);
            if (prevMeetingFlattened.end >= curMeeting.start) {
                Meeting newPrevMeetingFlattened = new Meeting(prevMeetingFlattened.start,
                                                    Math.max(prevMeetingFlattened.end, curMeeting.end));
                flattened.set(flattened.size() -1, newPrevMeetingFlattened);
            } else {
                flattened.add(curMeeting);
            }
        }
        return flattened;
    }

    private List<Meeting> mergeCalendarsTakeEarliestStartTime(List<Meeting> calendar1, List<Meeting> calendar2) {
        List<Meeting> merged = new ArrayList<>();
        int calendar1Idx = 0;
        int calendar2Idx = 0;
        while (calendar1Idx < calendar1.size() && calendar2Idx < calendar2.size()) {
            Meeting meeting1 = calendar1.get(calendar1Idx);
            Meeting meeting2 = calendar2.get(calendar2Idx);
            if (meeting1.start < meeting2.start) {
                merged.add(meeting1);
                calendar1Idx++;
            } else {
                merged.add(meeting2);
                calendar2Idx++;
            }
        }
        while (calendar1Idx < calendar1.size()) merged.add(calendar1.get(calendar1Idx++));
        while (calendar2Idx < calendar2.size()) merged.add(calendar2.get(calendar2Idx++));
        return merged;
    }

    private List<Meeting> updateCalendarTimeToMinutes(List<StringMeeting> calendar, StringMeeting dailyBounds) {
        List<StringMeeting> updatedCalendar = new ArrayList<>();
        updatedCalendar.add(new StringMeeting("0:00", dailyBounds.start));
        updatedCalendar.addAll(calendar);
        updatedCalendar.add(new StringMeeting(dailyBounds.end, "23:59"));
        List<Meeting> calendarInMinutes = new ArrayList<>();

        for (int i = 0; i < updatedCalendar.size(); i++) {
            calendarInMinutes.add(new Meeting(timeToMinutes(updatedCalendar.get(i).start),
                                              timeToMinutes(updatedCalendar.get(i).end)));
        }
        return calendarInMinutes;
    }

    private int timeToMinutes(String time) {
        int delimiterPos = time.indexOf(":");
        int hours = Integer.parseInt(time.substring(0, delimiterPos));
        int minutes = Integer.parseInt(time.substring(delimiterPos+ 1, time.length()));
        return hours * 60 + minutes;
    }

    class StringMeeting {
        String start;
        String end;

        StringMeeting(String start, String end) {
            this.start = start;
            this.end = end;
        }
    }

    class Meeting {
        int start;
        int end;

        Meeting(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
