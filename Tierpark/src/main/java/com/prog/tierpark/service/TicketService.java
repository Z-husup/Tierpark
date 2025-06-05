package com.prog.tierpark.service;

import java.util.List;

import com.prog.tierpark.model.Ticket;
import com.prog.tierpark.repository.TicketRepository;

public class TicketService {
    TicketRepository ticketRepository;
    
    public Ticket addTicket(Ticket ticket) {
        return ticket; // Возвращаем добавленный билет
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.getAllTickets();
    }

    public Ticket getTicketById(long id) {
        return ticketRepository.getTicketById(id);
    }
    public void saveTicket(Ticket ticket) {
        ticketRepository.saveTicket(ticket);
    }
    public void changeTicket(Ticket ticket) {
        ticketRepository.updateTicket(ticket);
    }
}
