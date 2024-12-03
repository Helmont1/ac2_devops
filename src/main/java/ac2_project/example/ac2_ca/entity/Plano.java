package ac2_project.example.ac2_ca.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plano {
    
    @Column(name = "plano_value")
    private String value;

    private Plano(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!value.equals("BASICO") && !value.equals("PREMIUM")) {
            throw new IllegalArgumentException("Invalid plano type: " + value);
        }
    }

    public static Plano of(String value) {
        return new Plano(value);
    }
}