package frandz.api_test.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@Entity
@Table(name = "jwt")
@NoArgsConstructor
@AllArgsConstructor
public class Jwt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String valeur;
    private Boolean desactive;
    private Boolean expire;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name="utilisateur_id")
    private User user;

}
