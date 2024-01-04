import com.polimi.ckb.tournament.repository.StudentRepository;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class StudentExistsValidator implements ConstraintValidator<StudentExists, Long> {
    private final StudentRepository studentRepository;

    @Override
    public boolean isValid(Long studentId, ConstraintValidatorContext constraintValidatorContext) {
        return studentRepository.existsById(studentId);
    }
}
