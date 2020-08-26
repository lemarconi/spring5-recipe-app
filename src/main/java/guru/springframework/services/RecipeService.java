package guru.springframework.services;

import guru.springframework.domain.Recipe;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * created by lgm on 08/26/20
 */
public interface RecipeService {

    Set<Recipe> getRecipes();
}
