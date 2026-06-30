
package work.antoniocaccamo.recipe.builder.mapper;

public interface IMapper<Source, Target> {
    Target toDTO(Source source);

    Source toEntity(Target target);
}
