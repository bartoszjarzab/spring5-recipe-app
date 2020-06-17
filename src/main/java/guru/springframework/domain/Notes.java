package guru.springframework.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;


@Data
@EqualsAndHashCode(exclude={"recipe"})
public class Notes {
    @Id
    private String id;
    //private Recipe recipe;
    private String recipeNotes;

}
