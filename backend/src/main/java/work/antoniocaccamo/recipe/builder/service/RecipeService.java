package work.antoniocaccamo.recipe.builder.service;

import java.net.ConnectException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import work.antoniocaccamo.recipe.builder.dto.IngredientRequest;
import work.antoniocaccamo.recipe.builder.dto.Recipe;
import work.antoniocaccamo.recipe.builder.mapper.RecipeMapper;
import work.antoniocaccamo.recipe.builder.model.RecipeEntity;
import work.antoniocaccamo.recipe.builder.model.RecipeRatingEntity;

/**
 * RecipeService
 */

@ApplicationScoped
public class RecipeService {

    private static final Logger logger = Logger.getLogger(RecipeService.class);

    @Inject
    RecipeMapper recipeMapper;

    @Inject
    RecipeAIService recipeAIService;

    public Recipe generateRecipe(String userId, IngredientRequest request) {

        try {
            Recipe recipe = recipeAIService.generateRecipe(request.ingredients());
            logger.info("Recipe generated for user: %s".formatted(recipe.getUserId()));
            return saveRecipe(userId, recipe);
        } catch ( RuntimeException ce  ) {
            logger.error("error occurred" ,  ce);
            throw new RuntimeException(ce.getLocalizedMessage());
        }
    }

    @Transactional
    public Recipe saveRecipe(String userId, Recipe recipe) {

        RecipeEntity recipeEntity = recipeMapper.toEntity(recipe);
        recipeEntity.setRecipeId(UUID.randomUUID());
        recipeEntity.setUserId(userId);
        RecipeEntity.persist(recipeEntity);
        return recipeMapper.toDTO(recipeEntity);
    }


    ///
    /// 
    ///
    @Transactional
    public Recipe recipeCooked(UUID id) {

        Optional<RecipeEntity> oentity = RecipeEntity.findByIdOptional(id);

        return oentity.map(entity -> {
            entity.setCookedCount(entity.getCookedCount() + 1);
            RecipeEntity.persist(entity);
            return recipeMapper.toDTO(entity);
        }).orElseThrow(  () ->  new NotFoundException("Recipe not found with ID: " + id));

    }

    public Recipe rateRecipe(String currentUserId, UUID recipeId, int score) {
        RecipeEntity recipe = RecipeEntity.findById(recipeId);
        if (recipe == null) {
            throw new NotFoundException("Recipe not found");
        }
        // Check if this user has already rated this recipe
        RecipeRatingEntity existingRating = RecipeRatingEntity.find(
            "userId = ?1 and recipeId = ?2",
            currentUserId,
            recipeId
        ).firstResult();

        if (existingRating != null) {
            // Recalculate average by swapping the old score out for the new score
            double currentTotalScore = recipe.getAverageRating() * recipe.getRatingCount();
            currentTotalScore = currentTotalScore - existingRating.ratingValue + score;
            recipe.setAverageRating(currentTotalScore / recipe.getRatingCount());

            existingRating.ratingValue = score; // Update database record
        } else {
            // First time rating: increment the count and append the score
            double currentTotalScore = recipe.getAverageRating() * recipe.getRatingCount();
            recipe.setRatingCount ( recipe.getRatingCount() + 1 );
            recipe.setAverageRating( (currentTotalScore + score) / recipe.getRatingCount() );

            RecipeRatingEntity newRating = new RecipeRatingEntity();
            newRating.userId = currentUserId;
            newRating.recipeId = recipeId;
            newRating.ratingValue = score;
            newRating.persist();
        }

        return recipeMapper.toDTO(recipe);
    }

    public List<Recipe> getTopFiveMostCooked() {
         // Query reads: Find all records, order by cookedCount descending, take the first 5 records
        return RecipeEntity.find("order by cookedCount desc").page(0, 5)
            .list().stream()
                .map(x -> (RecipeEntity) x )
                .map(recipeMapper::toDTO)
                .collect(Collectors.toList())
        ;
    }

    public List<Recipe> getUserRecipeHistory(String currentUserId) {
        return  RecipeEntity.list("userId", currentUserId).stream()
                .map(x -> (RecipeEntity) x )
                .map(recipeMapper::toDTO)
                .collect(Collectors.toList());
    }
}
