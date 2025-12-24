package com.example.kakaoapi.core.annotation

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [ValidEnumValidator::class])
annotation class ValidEnum(
    val message: String = "Invalid enum value",
    val groups: Array<KClass<*>> = [], // Kotlin 리플렉션 클래스 타입
    val payload: Array<KClass<out Payload>> = [],
    val enumClass: KClass<out Enum<*>> // specify the enum class to be validated
)

class ValidEnumValidator : ConstraintValidator<ValidEnum, Any> {
    private lateinit var enumValues: Array<out Enum<*>>
    // lateinit 키워드: 변수를 선언과 동시에 초기화하지 않고, 나중에(initialize 메서드 호출 시) 초기화하겠다고 선언해놓는 것. 컴파일러가 보고 알 수 있도록.

    override fun initialize(annotation: ValidEnum) {
        enumValues = annotation.enumClass.java.enumConstants
    }

    override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
        if (value == null) {
            return true // null values are validated with the @NotNull annotation
        }
        return enumValues.any { it.name == value.toString() }
    }
}