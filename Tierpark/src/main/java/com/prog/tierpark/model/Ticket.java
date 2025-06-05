package com.prog.tierpark.model;

import java.util.Date;

import com.prog.tierpark.model.enums.TicketStatus;
import com.prog.tierpark.model.enums.TicketType;

import lombok.Data;

@Data
public class Ticket {
    long id;
    TicketType type;
    Date day;
    double price;
    TicketStatus status = TicketStatus.active;
    public Ticket(TicketType type, Date day) {
        this.type = type;
        this.day = day;
        switch (type) {
            case child:
                this.price = 5.0;
                break;
            case student:
                this.price = 7.0;
                break;
            case adult:
                this.price = 10.0;
                break;
            case senior:
                this.price = 8.0;
                break;
            default:
                break;
        }
    }
    public Ticket(long id, TicketType type, Date day, double price, TicketStatus status) {
        this.id = id;
        this.type = type;
        this.day = day;
        this.price = price;
        this.status = status;
    }


}
