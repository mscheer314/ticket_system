package com.markscheer.ticket_system.controller;

import com.markscheer.ticket_system.model.Ticket;
import com.markscheer.ticket_system.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// I don't know what this line does.
//@CrossOrigin(origins = "https://localhost:8081")
@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping()
    public ResponseEntity<List<Ticket>> getTickets(@RequestParam(required = false) Optional<Long> id) {
        try {

            List<Ticket> tickets = new ArrayList<>(ticketRepository.findAll());

            if (tickets.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tickets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<List<Ticket>> getTicketByID(@PathVariable Long id) {
        try {
            List<Ticket> tickets = new ArrayList<>();

            Optional<Ticket> ticket = ticketRepository.findById(id);
            if (ticket.isPresent()) {
                tickets.add(ticket.get());
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tickets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        try {
            Ticket _ticket = ticketRepository
                    .save(new Ticket(ticket.getTitle(), ticket.getDescription(), ticket.getCreatedOn()
                            , ticket.getResolutionDate()));
            return new ResponseEntity<>(_ticket, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
