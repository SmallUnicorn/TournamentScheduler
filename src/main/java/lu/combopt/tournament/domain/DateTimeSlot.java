package lu.combopt.tournament.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class DateTimeSlot {
    private Integer id;
    private LocalDateTime dateTimeStart;

    public DateTimeSlot(){}

    public DateTimeSlot(Integer id, LocalDateTime dateTimeStart){
        this.setDateTimeStart(dateTimeStart);
        this.setId(id);
    }

    public LocalDateTime getDateTimeStart() {

        return dateTimeStart;
    }

    public void setDateTimeStart(LocalDateTime dateTimeStart) {

        this.dateTimeStart = dateTimeStart;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Override
    public String toString(){
        return this.getDateTimeStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
    public String timeToString(){
        return this.getDateTimeStart().format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
