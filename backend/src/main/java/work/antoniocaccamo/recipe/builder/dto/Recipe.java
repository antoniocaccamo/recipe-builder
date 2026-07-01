package work.antoniocaccamo.recipe.builder.dto;

import java.util.List;

public class Recipe {
    private String recipeId;
    private String userId;
    private String title;
    private Integer totalTimeMinutes;
    private List<String> ingredientsUsed;
    private List<String> requiredPantryItems; // Things like salt; oil; water
    private List<String> instructions;
    private Long cookedCount;
    private Double averageRating;
    private Long ratingCount;

    public String getRecipeId() {
        return recipeId;
    }
    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }
    public String getUserId() {
        return userId;
    }
    public Recipe setUserId(String userId) {
        this.userId = userId;
        return this;
    }
    public String getTitle() {
        return title;
    }
    public Recipe setTitle(String title) {
        this.title = title;
        return this;
    }
    public Integer getTotalTimeMinutes() {
        return totalTimeMinutes;
    }
    public Recipe setTotalTimeMinutes(Integer totalTimeMinutes) {
        this.totalTimeMinutes = totalTimeMinutes;
        return this;
    }
    public List<String> getIngredientsUsed() {
        return ingredientsUsed;
    }
    public Recipe setIngredientsUsed(List<String> ingredientsUsed) {
        this.ingredientsUsed = ingredientsUsed;
        return this;
    }
    public List<String> getRequiredPantryItems() {
        return requiredPantryItems;
    }
    public Recipe setRequiredPantryItems(List<String> requiredPantryItems) {
        this.requiredPantryItems = requiredPantryItems;
        return this;
    }
    public List<String> getInstructions() {
        return instructions;
    }
    public Recipe setInstructions(List<String> instructions) {
        this.instructions = instructions;
        return this;
    }
    public Long getCookedCount() {
        return cookedCount;
    }
    public Recipe setCookedCount(Long cookedCount) {
        this.cookedCount = cookedCount;
        return this;
    }
    public Double getAverageRating() {
        return averageRating;
    }
    public Recipe setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
        return this;
    }
    public Long getRatingCount() {
        return ratingCount;
    }
    public Recipe setRatingCount(Long ratingCount) {
        this.ratingCount = ratingCount;
        return this;
    }
    @Override
    public String toString() {
        return "Recipe [recipeId=" + recipeId + ", userId=" + userId + ", title=" + title + ", totalTimeMinutes=" + totalTimeMinutes + "]";
    }


    



}