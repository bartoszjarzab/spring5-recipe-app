package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Autowired
    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if(ingredient==null){
            return null;
        }
        IngredientCommand command = new IngredientCommand();
        command.setId(ingredient.getId());
        if(ingredient.getRecipe()!=null){
            command.setRecipeId(ingredient.getRecipe().getId());
        }
        command.setAmount(ingredient.getAmount());
        command.setDescription(ingredient.getDescription());
        command.setUom(
                unitOfMeasureToUnitOfMeasureCommand.convert(ingredient.getUom())
        );
        return command;
    }
}
