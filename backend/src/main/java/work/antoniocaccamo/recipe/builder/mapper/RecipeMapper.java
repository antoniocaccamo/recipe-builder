
package work.antoniocaccamo.recipe.builder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import work.antoniocaccamo.recipe.builder.dto.Recipe;
import work.antoniocaccamo.recipe.builder.model.RecipeEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface RecipeMapper extends IMapper<RecipeEntity, Recipe> {

    
    Recipe toDTO(RecipeEntity source) ;

    RecipeEntity toEntity(Recipe target);
}
