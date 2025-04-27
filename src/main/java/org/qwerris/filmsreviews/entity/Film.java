package org.qwerris.filmsreviews.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Integer id;

    @Column(nullable = false, name = "name")
    private String title;

    private String description;

    @Column(name = "release_day")
    private LocalDate releaseDate;

    private LocalTime length;

    @Getter
    @ToString.Exclude
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    public double getScore() {
        return reviews.stream().mapToDouble(Review::getScore).average().orElse(0);
    }
}
