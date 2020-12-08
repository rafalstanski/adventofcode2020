package com.hurricane.kata.adventofcode2020.day4

import com.hurricane.kata.adventofcode2020.day4.PassportFieldType.*

private val requiredFieldTypes = PassportFieldType.values().toList() - PassportFieldType.CID

interface PassportValidator {

    fun valid(passport: Passport): Boolean
}


class RequiredFieldsPassportValidator : PassportValidator {

    override fun valid(passport: Passport): Boolean {
        return passport.fields.map { it.type }.containsAll(requiredFieldTypes)
    }

}

class RequiredAndValidFieldsPassportValidator(
        private val requiredFieldsPassportValidator: RequiredFieldsPassportValidator,
        private val passportFieldValidator: PassportFieldValidator
) : PassportValidator {

    override fun valid(passport: Passport): Boolean {
        return hasRequiredFields(passport) && allFieldsAreValid(passport)
    }

    private fun hasRequiredFields(passport: Passport): Boolean =
            requiredFieldsPassportValidator.valid(passport)

    private fun allFieldsAreValid(passport: Passport): Boolean {
        return passport.fields.all { isValidField(it) }
    }

    private fun isValidField(passportField: PassportField): Boolean {
        return passportFieldValidator.validateField(passportField)
    }

}

class PassportFieldValidator {

    fun validateField(passportField: PassportField): Boolean = with(passportField) {
        return when (type) {
            BYR -> validateByr(value)
            IYR -> validateIyr(value)
            EYR -> validateEyr(value)
            HGT -> validateHgt(value)
            HCL -> validateHcl(value)
            ECL -> validateEcl(value)
            PID -> validatePid(value)
            CID -> true
        }
    }

    private fun validateByr(value: String) =
            validateIfNumberInRange(value, 1920..2002)

    private fun validateIyr(value: String) =
            validateIfNumberInRange(value, 2010..2020)

    private fun validateEyr(value: String) =
            validateIfNumberInRange(value, 2020..2030)

    private fun validateHgt(value: String) = when {
        matchesHgtPattern(value) -> when {
            value.endsWith("cm") -> {
                val cmValue = value.removeSuffix("cm")
                validateIfNumberInRange(cmValue, 150..193)
            }
            else -> {
                val inValue = value.removeSuffix("in")
                validateIfNumberInRange(inValue, 59..76)
            }
        }
        else -> false
    }

    private fun matchesHgtPattern(value: String) = value.matches(Regex("[0-9]+(cm|in)"))

    private fun validateHcl(value: String) =
            value.matches(Regex("#[0-9a-f]{6}"))

    private fun validateEcl(value: String) =
            value.matches(Regex("amb|blu|brn|gry|grn|hzl|oth"))

    private fun validatePid(value: String) =
            value.matches(Regex("[0-9]{9}"))

    private fun validateIfNumberInRange(value: String, range: IntRange): Boolean =
            value
                    .toIntOrNull()
                    ?.let { it in range }
                    ?: false

}