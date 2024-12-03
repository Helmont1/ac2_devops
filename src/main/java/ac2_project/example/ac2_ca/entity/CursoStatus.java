package ac2_project.example.ac2_ca.entity;

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
public class CursoStatus {
    
    @Column(name = "curso_status_value")
    private String value;

    private CursoStatus(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!value.equals("NOT_STARTED") && !value.equals("IN_PROGRESS") && !value.equals("COMPLETED")) {
            throw new IllegalArgumentException("Invalid curso status: " + value);
        }
    }

    public static CursoStatus of(String value) {
        return new CursoStatus(value);
    }
}
