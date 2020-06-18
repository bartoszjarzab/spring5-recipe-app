package guru.springframework.commands;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class CategoryCommand {
    @Id
    private String id;
    private String description;

    @Override
    public String toString() {
        return description;
    }
}
