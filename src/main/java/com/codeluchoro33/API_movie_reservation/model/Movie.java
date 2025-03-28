package com.codeluchoro33.API_movie_reservation.model;

import com.codeluchoro33.API_movie_reservation.enums.GenreMovie;
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
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String posterUrl;

    @Enumerated(EnumType.STRING)
    private GenreMovie genreMovie;

    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL)
    private  List<ShowTime> showTimes;// 💡 Asegurar que coincida con el atributo en Showtime

}
