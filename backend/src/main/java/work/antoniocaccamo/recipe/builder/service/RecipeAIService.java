package work.antoniocaccamo.recipe.builder.service;

import java.util.List;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;
import work.antoniocaccamo.recipe.builder.dto.Recipe;

/**
 * RecipeService
 */
@ApplicationScoped
@RegisterAiService
@SystemMessage("""
        You are 'Smart Chef'. Create a delicious recipe using the provided ingredients.
        Constraints:
        1. Total prep + cook time must be 30 minutes or less.
        2. Output MUST strictly match the requested JSON schema structure.
        3. Do not include markdown code fences like ```json in your response text, output raw valid JSON only.
        """)
public interface RecipeAIService {

    @UserMessage("Create a 30-minute recipe using these ingredients: {ingredients}")
    Recipe generateRecipe(List<String> ingredients);
}
