package com.stayscape.backend.domain.validators

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass


@MustBeDocumented
@Constraint(validatedBy = [NullOrNotBlankValidator::class])
@Target(AnnotationTarget.TYPE, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class NullOrNotBlank(
    val message: String = "Field must not be blank if provided",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class NullOrNotBlankValidator : ConstraintValidator<NullOrNotBlank?, String?> {
    override fun initialize(parameters: NullOrNotBlank?) {
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        return value == null || value.trim().isNotEmpty()
    }
}
