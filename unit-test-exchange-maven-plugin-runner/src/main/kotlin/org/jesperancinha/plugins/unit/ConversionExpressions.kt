package org.jesperancinha.plugins.unit

/**
 * Created by jofisaes on 25/12/2021
 */
class ConversionExpressions {
    companion object {
        private const val GENERIC_GROUP = "([0-9a-zA-Z(_\\-\":, /\\.)\\!]*)"

        private val ASSERT_NOTNULL_FROM_ASSERTJ_TO_KOTEST_REGEX = Regex("Assert\\.assertNotNull\\($GENERIC_GROUP\\)")
        private const val ASSERT_NOTNULL_FROM_ASSERTJ_TO_KOTEST_REPLACEMENT = "\$1.shouldNotBeNull()"

        private val ASSERT_EQUALS_FROM_ASSERTJ_TO_KOTEST_REGEX =
            Regex("Assert\\.assertEquals\\($GENERIC_GROUP, $GENERIC_GROUP\\)")
        private const val ASSERT_EQUALS_FROM_ASSERTJ_TO_KOTEST_REPLACEMENT = "\$1 shouldBe \$2"

        private val ASSERT_EQUALS_FROM_ASSERTJ_TO_KOTEST_REGEX1 =
            Regex("assertEquals\\($GENERIC_GROUP, $GENERIC_GROUP\\)")
        private const val ASSERT_EQUALS_FROM_ASSERTJ_TO_KOTEST_REPLACEMENT1 = "\$1 shouldBe \$2"

        private val EVERY_TRUE_FROM_MOCKITO_TO_MOCKK_REGEX =
            Regex("Mockito\\.`when`\\($GENERIC_GROUP\\)\\.thenReturn\\($GENERIC_GROUP\\)")
        private const val EVERY_TRUE_FROM_MOCKITO_TO_MOCKK_REPLACEMENT = "every { \$1 } returns \$2"


        private val FIND_EXCEPTION_ANNOTATION_REGEX = Regex("@Test\\(expected")
        private val REPLACE_EXCEPTION_ANNOTATION_REGEX = Regex("@Test\\(expected = $GENERIC_GROUP::class\\)")

        private val ASSERT_REPLACE_IMPORT_JUNIT_TO_JUPITER = mutableListOf(
            ASSERT_NOTNULL_FROM_ASSERTJ_TO_KOTEST_REGEX to (ASSERT_NOTNULL_FROM_ASSERTJ_TO_KOTEST_REPLACEMENT to "import io.kotest.matchers.nulls.shouldNotBeNull"),
            EVERY_TRUE_FROM_MOCKITO_TO_MOCKK_REGEX to (EVERY_TRUE_FROM_MOCKITO_TO_MOCKK_REPLACEMENT to "import io.mockk.every"),
            ASSERT_EQUALS_FROM_ASSERTJ_TO_KOTEST_REGEX to (ASSERT_EQUALS_FROM_ASSERTJ_TO_KOTEST_REPLACEMENT to "import io.kotest.matchers.shouldBe"),
            ASSERT_EQUALS_FROM_ASSERTJ_TO_KOTEST_REGEX1 to (ASSERT_EQUALS_FROM_ASSERTJ_TO_KOTEST_REPLACEMENT1 to "import io.kotest.matchers.shouldBe")
        )
        private val IMPORT_REPLACEMENT_JUNIT_TO_JUPITER = mapOf(
            Regex("import org.junit.Before") to "import org.junit.jupiter.api.BeforeEach",
            Regex("import org.junit.Test") to "import org.junit.jupiter.api.Test",
            Regex("import org.junit.runner.RunWith") to "import org.junit.jupiter.api.extension.ExtendWith",
            Regex("import org.mockito.junit.MockitoJUnitRunner") to "import io.mockk.junit5.MockKExtension",
            Regex("import org.mockito.Mock\n") to "import io.mockk.impl.annotations.MockK\n",
            Regex("import org.junit.Assert(;)?") to "",
            Regex("import org.junit.runner.RunWith(;)?") to "",
        )

        private val ANNOTATIONS_REPLACEMENT_JUNIT_TO_JUPITER = mapOf(
            Regex("@Before\n") to "@BeforeEach\n",
            Regex("@Mock\n") to "@MockK\n",
            Regex("@RunWith\\(MockitoJUnitRunner::class\\)") to "@ExtendWith(MockKExtension::class)",
            Regex("@Test\\(expected = $GENERIC_GROUP::class\\)") to "@Test"
        )

        val OPERATIONS = listOf(
            { text: String -> processImports(text) },
            { text: String -> processExceptionExpectingTestMethods(text) },
            { text: String -> processAssertionsFindReplaceImport(text) },
            { text: String -> procesAnnotations(text) }
        )

        private fun processExceptionExpectingTestMethods(originalText: String): String {
            var stringList = originalText.split("\n").toMutableList()
            var indexOfFirst = stringList.indexOfFirst { it.contains(FIND_EXCEPTION_ANNOTATION_REGEX) }
            val firstIndex = indexOfFirst
            while (indexOfFirst > -1) {
                stringList = processExceptionTest(stringList, indexOfFirst)
                indexOfFirst = stringList.indexOfFirst(indexOfFirst) { it.contains(FIND_EXCEPTION_ANNOTATION_REGEX) }
            }
            if (firstIndex > -1) {
                stringList.add(2, "import io.kotest.assertions.throwables.shouldThrow")
            }
            return stringList.joinToString("\n")
        }

        private fun processExceptionTest(
            stringList: MutableList<String>,
            indexOfFirst: Int,
        ): MutableList<String> {
            var change = false
            var count: Int = 0
            val className =
                REPLACE_EXCEPTION_ANNOTATION_REGEX.find(stringList[indexOfFirst])?.destructured?.component1()
            for (i in indexOfFirst until stringList.size) {
                val currentLine = stringList[i]
                currentLine.forEach { c ->
                    when (c) {
                        '{' -> {
                            if (count == 0) {
                                change = true
                            }
                            count++
                        }
                        '}' -> count--
                    }
                    if (change && count == 0) {
                        val trimmedOldText = stringList[i - 1].trim()
                        stringList[i - 1] =
                            stringList[i - 1].replace(trimmedOldText, "shouldThrow<$className> { $trimmedOldText }")
                        return stringList
                    }
                }
            }
            return stringList
        }


        private fun processImports(originalText: String): String =
            IMPORT_REPLACEMENT_JUNIT_TO_JUPITER
                .entries.fold(originalText) { acc, entry ->
                    acc.replace(entry.key, entry.value)
                }

        private fun processAssertionsFindReplaceImport(originalText: String): String =
            ASSERT_REPLACE_IMPORT_JUNIT_TO_JUPITER
                .fold(originalText) { acc, instructions ->
                    val find = instructions.first
                    val (replace, import) = instructions.second
                    val contains = acc.contains(find)
                    if (contains) {
                        val replacedText =
                            acc.replace(find,
                                replace)
                        val stringList = replacedText.split("\n").toMutableList()
                        stringList.add(2, import)
                        stringList.joinToString("\n")
                    } else acc
                }

        private fun procesAnnotations(originalText: String) = ANNOTATIONS_REPLACEMENT_JUNIT_TO_JUPITER
            .entries.fold(originalText) { acc, entry ->
                acc.replace(entry.key, entry.value)
            }
    }

}

private fun <E> MutableList<E>.indexOfFirst(from: Int, predicate: (E) -> Boolean): Int {
    forEachIndexed { index, it ->
        if (predicate(it) && index > from)
            return index
    }
    return -1
}

val String.processTests: String
    get() = ConversionExpressions.OPERATIONS.fold(this) { acc, operation -> operation(acc) }

val String.isSupported: Boolean
    get() = equals("kt") || equals("scala") || equals("java")

