package be.technocite.constraint;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first(); //return "email"
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext context) {
        try {
            final Object firstObj = BeanUtils.getProperty(object, firstFieldName); //"email"
            final Object secondObj = BeanUtils.getProperty(object, secondFieldName);
            return firstObj == null && secondObj == null
                    || firstObj != null && firstObj.equals(secondObj);
        } catch (final Exception ignore) {}
        return true;
    }
}