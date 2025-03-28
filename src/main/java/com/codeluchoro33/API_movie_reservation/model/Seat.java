package com.codeluchoro33.API_movie_reservation.model;

import com.codeluchoro33.API_movie_reservation.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "row_num")  // ✅ Evita conflicto con MySQL
    private int rowNumber;
    @Column(name = "seat_num")  // ✅ Evita conflicto con MySQL
    private int seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    @ManyToOne
    @JoinColumn(name = "showtime_id", nullable = false)
    private ShowTime showTime;

    @OneToMany(mappedBy = "seat",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SeatReservation> reservations;
}
