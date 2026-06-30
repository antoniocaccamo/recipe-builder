package work.antoniocaccamo.recipe.builder.model;

import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "recipe_ratings", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"userId", "recipeId"})
})
public class RecipeRatingEntity extends PanacheEntity {
    
    public String userId;
    public UUID recipeId;
    public int ratingValue; // 1 to 5 stars
}