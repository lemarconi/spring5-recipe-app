package guru.springframework.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * created by lgm on 08/25/20
 */

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

    {
        recipes = new HashSet<>();
    }

}
