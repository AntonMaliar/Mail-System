package anton.maliar.email.repository.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "letters")
public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String body;
    private LocalDate date;
    private LocalTime time;
    private String status;
    @ManyToOne
    @JoinColumn(name = "sender")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "receiver")
    private User receiver;
    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;

    public Letter() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Letter(String body, LocalDate date, LocalTime time, String status, User sender, User receiver, User owner) {
        this.body = body;
        this.date = date;
        this.time = time;
        this.status = status;
        this.sender = sender;
        this.receiver = receiver;
        this.owner = owner;
    }
}
