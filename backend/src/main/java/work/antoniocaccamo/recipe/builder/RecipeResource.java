import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

import org.eclipse.microprofile.jwt.JsonWebToken;
import work.antoniocaccamo.recipe.builder.dto.IngredientRequest;
import work.antoniocaccamo.recipe.builder.dto.Recipe;
import work.antoniocaccamo.recipe.builder.model.RecipeEntity;
import work.antoniocaccamo.recipe.builder.model.RecipeRatingEntity;
import work.antoniocaccamo.recipe.builder.service.RecipeService;

@Path("/api/v1/recipes")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecipeResource {

    @Inject
    RecipeService recipeService;

    @Inject
    JsonWebToken jwt; // Injected automatically by quarkus-oidc

    @POST
    @Path("/generate")
    @Transactional // Required for writing data to the DB
    public Recipe generateAndSaveRecipe(IngredientRequest request) {
        if (request.ingredients() == null || request.ingredients().isEmpty()) {
            throw new BadRequestException("Ingredient list cannot be empty");
        }

       return recipeService.generateRecipe(jwt.getSubject(), request);
    }

    @GET
    @Path("/history")
    public List<Recipe> getUserRecipeHistory() {
        String currentUserId = jwt.getSubject();
        // Panache allows SQL-like queries right inside the entity class!
        return recipeService.getUserRecipeHistory(currentUserId);
        
    }

    @GET
    @Path("/trending")
    // We can omit @Authenticated if we want anonymous users to see trending recipes
    public List<Recipe> getTopFiveMostCooked() {
        
        return recipeService.getTopFiveMostCooked();
        
       
    }

    @PUT
    @Path("/{id}/cook")
    @Transactional
    @Authenticated
    public Recipe incrementCookCount(@PathParam("id") UUID id) {

        return recipeService.recipeCooked(id);
    }

    @GET
    @Path("/ingredients")
    @Authenticated // Optional: remove if you want unauthenticated users to search ingredients
    public List<String> searchIngredients(@QueryParam("search") String search) {
        // If no search term is provided, return the most common distinct ingredients
        if (search == null || search.trim().isEmpty()) {
            return RecipeEntity.getEntityManager()
                .createQuery(
                    "SELECT DISTINCT i FROM RecipeEntity r JOIN r.ingredientsUsed i",
                    String.class
                )
                .setMaxResults(20) // Limit to top 20 to keep it snappy
                .getResultList();
        }

        // If a search term exists, perform a case-insensitive partial match (LIKE)
        return RecipeEntity.getEntityManager()
            .createQuery(
                "SELECT DISTINCT i FROM RecipeEntity r JOIN r.ingredientsUsed i WHERE LOWER(i) LIKE LOWER(:search)",
                String.class
            )
            .setParameter("search", "%" + search.trim() + "%")
            .setMaxResults(10) // Limit autocomplete results to 10
            .getResultList();
    }

    @PUT
    @Path("/{id}/rate")
    @Transactional
    @Authenticated
    public Recipe rateRecipe(
        @PathParam("id") UUID id,
        @QueryParam("score") int score
    ) {
        if (score < 1 || score > 5) {
            throw new BadRequestException(
                "Rating score must be between 1 and 5"
            );
        }

        return recipeService.rateRecipe(jwt.getSubject(), id, score);

        
    }
}
