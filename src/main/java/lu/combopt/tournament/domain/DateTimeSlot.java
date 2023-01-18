package lu.combopt.tournament.domain;

import java.time.LocalDateTime;

public class DateTimeSlot {
    private LocalDateTime dateTimeStart;

    public DateTimeSlot(){};

    public DateTimeSlot(LocalDateTime dateTimeStart){
        this.setDateTimeStart(dateTimeStart);
    }

    public LocalDateTime getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(LocalDateTime dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }
}
