package com.technobel.restapiroomequipment.models.forms;

import com.technobel.restapiroomequipment.models.entities.Request;
import com.technobel.restapiroomequipment.validation.constraints.MaxTime;
import com.technobel.restapiroomequipment.validation.constraints.MinFuture;
import com.technobel.restapiroomequipment.validation.constraints.MinTime;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Data
public class RequestForm {

    @NotBlank
    private String username;
    @NotBlank
    private String justification;
    @Min(1) @Max(300)
    @NotNull
    private Integer neededCapacity;
    @MinFuture(amount = 3, unit = ChronoUnit.DAYS)
    private LocalDate date;
    @MinTime(h = 8, m = 30, s = 0)
    @NotBlank
    private LocalTime beginAt;
    @MaxTime(h = 20, m = 0, s = 0)
    private LocalTime endAt;
    private List<Long> equipmentIds;



    /**
     * map RequestForm to Request entity.
     * Doesn't handle materials and  status
     * @return a Request entity
     */
    public Request toEntity(){
        Request request = new Request();

        request.setDate(this.date);
        request.setJustification(this.justification);
        request.setBeginTime(this.beginAt);
        request.setEndTime(this.endAt);
        request.setNeededCapacity(this.neededCapacity);

        return request;
    }


}
