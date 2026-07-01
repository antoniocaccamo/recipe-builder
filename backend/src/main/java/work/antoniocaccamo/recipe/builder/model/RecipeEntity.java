package work.antoniocaccamo.recipe.builder.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "recipes", indexes = {
    @Index(name = "idx_cooked_count", columnList = "cookedCount")
})
public class RecipeEntity extends PanacheEntityBase {

    @Id 
	@GeneratedValue
    @Column(name = "recipe_id")
    private UUID recipeId;
    
    @Column(name = "user_id")
    private String userId;
    
    private String title;

    @Column(name = "total_time_minutes")
    private int totalTimeMinutes;
    
    // Tracks how many times this specific recipe variant has been cooked
    private long cookedCount = 1L; 

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "recipe_ingredients", joinColumns = {@JoinColumn(name = "recipe_id")})
    private List<String> ingredientsUsed;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "recipe_required_pantry_items", joinColumns = {@JoinColumn(name = "recipe_id")})
    private List<String> requiredPantryItems;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "recipe_instructions", joinColumns = {@JoinColumn(name = "recipe_id")})
    private List<String> instructions;


    private double averageRating = 0.0;
    
    private long ratingCount = 0L;

	public UUID getRecipeId() {
		return recipeId;
	}

	public RecipeEntity setRecipeId(UUID recipeId) {
		this.recipeId = recipeId;
		return this;
	}

	public String getUserId() {
		return userId;
	}

	public RecipeEntity setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public RecipeEntity setTitle(String title) {
		this.title = title;
		return this;
	}

	public int getTotalTimeMinutes() {
		return totalTimeMinutes;
	}

	public RecipeEntity setTotalTimeMinutes(int totalTimeMinutes) {
		this.totalTimeMinutes = totalTimeMinutes;
		return this;
	}

	public long getCookedCount() {
		return cookedCount;
	}

	public RecipeEntity setCookedCount(long cookedCount) {
		this.cookedCount = cookedCount;
		return this;
	}

	public List<String> getIngredientsUsed() {
		return ingredientsUsed;
	}

	public RecipeEntity setIngredientsUsed(List<String> ingredientsUsed) {
		this.ingredientsUsed = ingredientsUsed;
		return this;
	}

	public List<String> getRequiredPantryItems() {
		return requiredPantryItems;
	}

	public RecipeEntity setRequiredPantryItems(List<String> requiredPantryItems) {
		this.requiredPantryItems = requiredPantryItems;
		return this;
	}

	public List<String> getInstructions() {
		return instructions;
	}

	public RecipeEntity setInstructions(List<String> instructions) {
		this.instructions = instructions;
		return this;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public RecipeEntity setAverageRating(double averageRating) {
		this.averageRating = averageRating;
		return this;
	}

	public long getRatingCount() {
		return ratingCount;
	}

	public RecipeEntity setRatingCount(long ratingCount) {
		this.ratingCount = ratingCount;
		return this;
	}



    
}