package org.jesperancinha.plugins.unit


/**
 * Created by jofisaes on 25/12/2021
 */
class ConversionExpressions {
    companion object {
        private const val GENERIC_SEARCH_CHARACTERS = "0-9a-zA-Z(_\\-\":,/\\.)\\!\\[\\]\\+<>{}#\\?\\\\"
        private const val GENERIC_SEARCH_CHARACTERS_AND_SPACE = "$GENERIC_SEARCH_CHARACTERS "
        private const val GENERIC_GROUP_ALL = "([$GENERIC_SEARCH_CHARACTERS_AND_SPACE]*)"
        private const val GENERIC_GROUP = "([$GENERIC_SEARCH_CHARACTERS_AND_SPACE]*[$GENERIC_SEARCH_CHARACTERS]+)"
        private const val GENERIC_GROUP_WITH_NEWLINE = "([$GENERIC_SEARCH_CHARACTERS_AND_SPACE\\n]*)"
        private const val GENERIC_GROUP_WITH_NEWLINE_STAR = "([$GENERIC_SEARCH_CHARACTERS_AND_SPACE\\n\\*]*)"
        private const val GENERIC_GROUP_WITH_STAR = "([$GENERIC_SEARCH_CHARACTERS_AND_SPACE\\*]*)"
        private const val CONSTANT_GROUP = "(\"[$GENERIC_SEARCH_CHARACTERS_AND_SPACE]*\"|[0-9]*|[A-Z_]*)"
        private const val CONSTANT_GROUP1 = "(([A-Z]+[a-z]*(\\.)?)*)"
        private const val VARIABLE_GROUP = "([a-zA-Z_]*)"

        private val MOCK_FROM_MOCKITO_TO_MOCKK_REGEX =
            Regex("Mockito\\.mock\\((\n)?(\\s)*$GENERIC_GROUP::class\\.java\\)")
        private const val MOCK_FROM_MOCKITO_TO_MOCKK_REPLACEMENT = "mockk<\$3>()"

        private val DISABLE_FROM_JUNIT_TO_KOTEST_REGEX =
            Regex("@Ignore\\(\"$GENERIC_GROUP_ALL\"\\)")
        private const val DISABLE_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "@Disabled(\"\$1\")"

        private val ASSERT_ARRAY_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX =
            Regex("Assert\\.assertArrayEquals\\($GENERIC_GROUP, $GENERIC_GROUP\\)")
        private const val ASSERT_ARRAY_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$2.shouldContainExactly(\$1)"

        private val ASSERT_FALSE_FROM_JUNIT_TO_KOTEST_REGEX =
            Regex("Assert\\.assertFalse\\($GENERIC_GROUP_WITH_STAR\\)")
        private const val ASSERT_FALSE_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$1.shouldBeFalse()"

        private val ASSERT_GREATER_THAN_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REGEX =
            Regex("Assert\\.assertTrue\\($GENERIC_GROUP_WITH_STAR, $GENERIC_GROUP_WITH_STAR > $CONSTANT_GROUP\\)")
        private const val ASSERT_GREATER_THAN_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$2 shouldBeGreaterThan \$3"

        private val ASSERT_EQUAL_TO_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REGEX =
            Regex("Assert\\.assertTrue\\($GENERIC_GROUP_WITH_STAR, $GENERIC_GROUP_WITH_STAR == $CONSTANT_GROUP\\)")
        private const val ASSERT_EQUAL_TO_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$2 shouldBe \$3"

        private val ASSERT_TRUE_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REGEX =
            Regex("Assert\\.assertTrue\\($GENERIC_GROUP_WITH_STAR, $GENERIC_GROUP_WITH_STAR\\)")
        private const val ASSERT_TRUE_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$2.shouldBeTrue()"

        private val ASSERT_TRUE_FROM_JUNIT_TO_KOTEST_REGEX =
            Regex("Assert\\.assertTrue\\($GENERIC_GROUP_WITH_STAR\\)")
        private const val ASSERT_TRUE_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$1.shouldBeTrue()"

        private val ASSERT_FALSE_BUT_NOTEQUALS_FROM_JUNIT_TO_KOTEST_REGEX0 =
            Regex("Assert\\.assertFalse\\($CONSTANT_GROUP\\s*(==(=)?|\\.equals)\\s*$GENERIC_GROUP\\)")
        private const val ASSERT_FALSE_BUT_NOTEQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT0 = "\$4 shouldNotBe \$1"

        private val ASSERT_FALSE_BUT_NOTEQUALS_FROM_JUNIT_TO_KOTEST_REGEX =
            Regex("Assert\\.assertFalse\\($GENERIC_GROUP\\s*(==(=)?|\\.equals)\\s*$GENERIC_GROUP\\)")
        private const val ASSERT_FALSE_BUT_NOTEQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$1 shouldNotBe \$4"

        private val ASSERT_FALSE_BUT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX0 =
            Regex("Assert\\.assertTrue\\($CONSTANT_GROUP\\s*(==(=)?|\\.equals)\\s*$GENERIC_GROUP\\)")
        private const val ASSERT_FALSE_BUT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT0 = "\$4 shouldBe \$1"

        private val ASSERT_FALSE_BUT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX =
            Regex("Assert\\.assertTrue\\($GENERIC_GROUP\\s*(==(=)?|\\.equals)\\s*$GENERIC_GROUP\\)")
        private const val ASSERT_FALSE_BUT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$1 shouldBe \$4"

        private val ASSERT_EMPTY_FROM_HAMCREST_TO_KOTEST_REGEX =
            Regex("Assert\\.assertThat\\($GENERIC_GROUP, Matchers.`is`\\(Matchers\\.empty\\(\\)\\)\\)")
        private const val ASSERT_EMPTY_FROM_HAMCREST_TO_KOTEST_REPLACEMENT = "\$1.shouldBeEmpty()"

        private val ASSERT_IS_FROM_HAMCREST_TO_KOTEST_REGEX =
            Regex("Assert\\.assertThat\\($GENERIC_GROUP, Matchers.`is`\\($GENERIC_GROUP\\)\\)")
        private const val ASSERT_IS_FROM_HAMCREST_TO_KOTEST_REPLACEMENT = "\$1 shouldBe \$2"

        private val ASSERT_HAS_SIZE_FROM_HAMCREST_TO_KOTEST_REGEX =
            Regex("Assert\\.assertThat\\($GENERIC_GROUP, Matchers.hasSize\\($GENERIC_GROUP\\)\\)")
        private const val ASSERT_HAS_SIZE_FROM_HAMCREST_TO_KOTEST_REPLACEMENT = "\$1.shouldHaveSize(\$2)"

        private val ASSERT_NULL_VALUE_IS_IS_FROM_HAMCREST_TO_KOTEST_REGEX =
            Regex("MatcherAssert\\.assertThat\\($GENERIC_GROUP, Is.`is`\\(IsNull\\.nullValue\\(\\)\\)\\)")
        private const val ASSERT_NULL_VALUE_IS_IS_FROM_HAMCREST_TO_KOTEST_REPLACEMENT = "\$1.shouldBeNull()"

        private val ASSERT_NULL_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REGEX = Regex("Assert\\.assertNull\\($GENERIC_GROUP, $GENERIC_GROUP\\)")
        private const val ASSERT_NULL_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$2.shouldBeNull()"

        private val ASSERT_NULL_FROM_JUNIT_TO_KOTEST_REGEX = Regex("Assert\\.assertNull\\($GENERIC_GROUP\\)")
        private const val ASSERT_NULL_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$1.shouldBeNull()"

        private val ASSERT_NOTNULL_FROM_JUNIT_TO_KOTEST_REGEX = Regex("Assert\\.assertNotNull\\($GENERIC_GROUP\\)")
        private const val ASSERT_NOTNULL_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$1.shouldNotBeNull()"

        private val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX0 =
            Regex("Assert\\.assertEquals\\($CONSTANT_GROUP, $GENERIC_GROUP\\)")
        private const val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT0 = "\$2 shouldBe \$1"

        private val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX4 =
            Regex("Assert\\.assertEquals\\($CONSTANT_GROUP1, $GENERIC_GROUP_ALL\\)")
        private const val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT4 = "\$4 shouldBe \$1"

        private val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX =
            Regex("Assert\\.assertEquals\\($GENERIC_GROUP, $GENERIC_GROUP\\)")
        private const val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$1 shouldBe \$2"

        private val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX1 =
            Regex("assertEquals\\($GENERIC_GROUP, $GENERIC_GROUP\\)")
        private const val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT1 = "\$1 shouldBe \$2"

        private val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX2 =
            Regex("Assert\\.assertEquals\\($GENERIC_GROUP, $GENERIC_GROUP_WITH_NEWLINE\\)")
        private const val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT2 = "\$2 shouldBe \$1"

        private val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX3 =
            Regex("Assert\\.assertEquals\\($GENERIC_GROUP, $GENERIC_GROUP_WITH_STAR\\)")
        private const val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT3 = "\$2 shouldBe \$1"

        private val ASSERT_SAME_FROM_JUNIT_TO_KOTEST_REGEX0 =
            Regex("Assert\\.assertSame\\($VARIABLE_GROUP, $GENERIC_GROUP\\)")
        private const val ASSERT_SAME_FROM_JUNIT_TO_KOTEST_REPLACEMENT0 = "\$1 shouldBeSameInstanceAs \$2"

        private val ASSERT_SAME_FROM_JUNIT_TO_KOTEST_REGEX =
            Regex("Assert\\.assertSame\\($GENERIC_GROUP, $GENERIC_GROUP\\)")
        private const val ASSERT_SAME_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$1 shouldBeSameInstanceAs \$2"

        private val ASSERT_CONTAINS_STRING_FROM_JUNIT_TO_KOTEST_REGEX =
            Regex("Assert\\.assertThat\\($GENERIC_GROUP, Matchers.containsString\\($GENERIC_GROUP\\)\\)")
        private const val ASSERT_CONTAINS_STRING_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$1 shouldContain \$2"

        private val EVERY_TRUE_FROM_MOCKITO_TO_MOCKK_REGEX =
            Regex("Mockito\\.`when`\\($GENERIC_GROUP\\)(\n)?(\\s*)?\\.thenReturn\\($GENERIC_GROUP\\)")
        private const val EVERY_TRUE_FROM_MOCKITO_TO_MOCKK_REPLACEMENT = "every { \$1 } returns \$4"

        private val EVERY_THROWS_FROM_MOCKITO_TO_MOCKK_REGEX0 =
            Regex("Mockito\\.`when`\\($GENERIC_GROUP\\)(\n)?(\\s*)?\\.thenThrow\\((\n)?(\\s*)?$GENERIC_GROUP::class\\.java\\)")
        private const val EVERY_THROWS_FROM_MOCKITO_TO_MOCKK_REPLACEMENT0 = "every { \$1 } throws mockk<\$6>()"

        private val EVERY_THROWS_FROM_MOCKITO_TO_MOCKK_REGEX =
            Regex("Mockito\\.`when`\\($GENERIC_GROUP\\)(\n)?(\\s*)?\\.thenThrow\\($GENERIC_GROUP\\)")
        private const val EVERY_THROWS_FROM_MOCKITO_TO_MOCKK_REPLACEMENT = "every { \$1 } throws \$4"

        private val EVERY_THROWS_FROM_MOCKITO_TO_MOCKK_REGEX1 =
            Regex("(\\s*)try \\{\n" +
                    "(\\s*)${GENERIC_GROUP_WITH_NEWLINE}\n" +
                    "(\\s*)Assert\\.fail\\($GENERIC_GROUP\\)\n" +
                    "(\\s*)} catch \\($GENERIC_GROUP\\)(\\s*)\\{\n" +
                    "(\\s*)Assert\\.assertEquals\\($GENERIC_GROUP, $GENERIC_GROUP::class\\.java\\)\n" +
                    "(\\s*)}")
        private const val EVERY_THROWS_FROM_MOCKITO_TO_MOCKK_REPLACEMENT1 = "\$1shouldThrow<\$11> { \$3 }"

        private val VERIFY_TIMES_FROM_MOCKITO_TO_MOCKK_REGEX =
            Regex("Mockito\\.verify\\($GENERIC_GROUP, (Mockito\\.)?times\\($CONSTANT_GROUP\\)\\)\\.$GENERIC_GROUP")
        private const val VERIFY_TIMES_FROM_MOCKITO_TO_MOCKK_REPLACEMENT = "verify\\(exactly = \$3\\) { \$1.\$4 }"

        private val VERIFY_SIMPLE_FROM_MOCKITO_TO_MOCKK_REGEX0 =
            Regex("Mockito\\.verify\\($GENERIC_GROUP\\)\\.$GENERIC_GROUP")
        private const val VERIFY_SIMPLE_FROM_MOCKITO_TO_MOCKK_REPLACEMENT0 = "verify { \$1.\$2 }"

        private val VERIFY_SIMPLE_FROM_MOCKITO_TO_MOCKK_REGEX =
            Regex("Mockito\\.verify\\($GENERIC_GROUP_WITH_NEWLINE\\)(\n)?(\\s*)?\\.$GENERIC_GROUP_WITH_NEWLINE\"\\)\n")
        private const val VERIFY_SIMPLE_FROM_MOCKITO_TO_MOCKK_REPLACEMENT = "verify { \$1.\$4\"\\) }\n"

        private val VERIFY_SIMPLE_FROM_MOCKITO_TO_MOCKK_REGEX1 =
            Regex("Mockito\\.verify\\($GENERIC_GROUP_WITH_NEWLINE\\)(\n)?(\\s*)?\\.$GENERIC_GROUP_WITH_NEWLINE_STAR\"\\)\n")

        private val FIND_EXCEPTION_ANNOTATION_REGEX = Regex("@Test\\(expected")
        private val REPLACE_EXCEPTION_ANNOTATION_REGEX = Regex("@Test\\(expected = $GENERIC_GROUP::class\\)")

        private val ANY_TYPE_REGEX =
            Regex("ArgumentMatchers\\.any\\((\n)?(\\s*)?$GENERIC_GROUP::class\\.java\\)")
        private const val ANY_TYPE_REPLACEMENT = "any<\$3>()"

        private val ANY_STRING_REGEX =
            Regex("ArgumentMatchers.anyString\\(\\)")
        private const val ANY_STRING_REPLACEMENT = "any<String>()"

        private val ANY_SAME_REGEX =
            Regex("ArgumentMatchers\\.same\\($GENERIC_GROUP\\)")
        private const val ANY_SAME_REPLACEMENT = "$1"

        private val CORRECTION1_REGEX =
            Regex("$GENERIC_GROUP shouldNotBe \\($GENERIC_GROUP\\)")
        private const val CORRECTION1_REPLACEMENT = "\$1 shouldNotBe \$2"

        private val CORRECTION2_REGEX =
            Regex("$GENERIC_GROUP shouldBe \\($GENERIC_GROUP\\)")
        private const val CORRECTION2_REPLACEMENT = "\$1 shouldBe \$2"

        private val ASSERT_REPLACE_IMPORT_JUNIT_TO_JUPITER = mutableListOf(
            ANY_STRING_REGEX to (ANY_STRING_REPLACEMENT to arrayOf("import io.kotest.assertions.any")),
            ANY_TYPE_REGEX to (ANY_TYPE_REPLACEMENT to arrayOf("import io.kotest.assertions.any")),
            ANY_SAME_REGEX to (ANY_SAME_REPLACEMENT to emptyArray()),
            MOCK_FROM_MOCKITO_TO_MOCKK_REGEX to (MOCK_FROM_MOCKITO_TO_MOCKK_REPLACEMENT to arrayOf("import io.mockk.mockk")),
            ASSERT_EQUAL_TO_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_EQUAL_TO_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf("import io.kotest.matchers.shouldBe")),
            ASSERT_GREATER_THAN_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_GREATER_THAN_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf("import io.kotest.matchers.ints.shouldBeGreaterThan")),
            ASSERT_TRUE_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_TRUE_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf("import io.kotest.matchers.booleans.shouldBeTrue")),
            ASSERT_ARRAY_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_ARRAY_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf(
                "import io.kotest.matchers.collections.shouldContainExactly")),
            ASSERT_SAME_FROM_JUNIT_TO_KOTEST_REGEX0 to (ASSERT_SAME_FROM_JUNIT_TO_KOTEST_REPLACEMENT0 to arrayOf("import io.kotest.matchers.types.shouldBeSameInstanceAs")),
            ASSERT_SAME_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_SAME_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf("import io.kotest.matchers.types.shouldBeSameInstanceAs")),
            DISABLE_FROM_JUNIT_TO_KOTEST_REGEX to (DISABLE_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf("import org.junit.jupiter.api.Disabled")),
            ASSERT_FALSE_BUT_NOTEQUALS_FROM_JUNIT_TO_KOTEST_REGEX0 to (ASSERT_FALSE_BUT_NOTEQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT0 to arrayOf(
                "import io.kotest.matchers.shouldNotBe")),
            ASSERT_FALSE_BUT_NOTEQUALS_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_FALSE_BUT_NOTEQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf(
                "import io.kotest.matchers.shouldNotBe")),
            ASSERT_FALSE_BUT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX0 to (ASSERT_FALSE_BUT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT0 to arrayOf(
                "import io.kotest.matchers.shouldBe")),
            ASSERT_FALSE_BUT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_FALSE_BUT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf(
                "import io.kotest.matchers.shouldBe")),
            EVERY_THROWS_FROM_MOCKITO_TO_MOCKK_REGEX1 to (EVERY_THROWS_FROM_MOCKITO_TO_MOCKK_REPLACEMENT1 to arrayOf("import io.kotest.assertions.throwables.shouldThrow")),
            ASSERT_EMPTY_FROM_HAMCREST_TO_KOTEST_REGEX to (ASSERT_EMPTY_FROM_HAMCREST_TO_KOTEST_REPLACEMENT to arrayOf("import io.kotest.matchers.collections.shouldBeEmpty")),
            ASSERT_IS_FROM_HAMCREST_TO_KOTEST_REGEX to (ASSERT_IS_FROM_HAMCREST_TO_KOTEST_REPLACEMENT to arrayOf("import io.kotest.matchers.shouldBe")),
            ASSERT_HAS_SIZE_FROM_HAMCREST_TO_KOTEST_REGEX to (ASSERT_HAS_SIZE_FROM_HAMCREST_TO_KOTEST_REPLACEMENT to arrayOf(
                "import io.kotest.matchers.collections.shouldHaveSize")),
            ASSERT_NULL_VALUE_IS_IS_FROM_HAMCREST_TO_KOTEST_REGEX to (ASSERT_NULL_VALUE_IS_IS_FROM_HAMCREST_TO_KOTEST_REPLACEMENT to arrayOf(
                "import io.kotest.matchers.nulls.shouldBeNull")),
            ASSERT_NULL_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_NULL_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf("import io.kotest.matchers.nulls.shouldBeNull")),
            ASSERT_NULL_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_NULL_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf("import io.kotest.matchers.nulls.shouldBeNull")),
            ASSERT_NOTNULL_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_NOTNULL_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf("import io.kotest.matchers.nulls.shouldNotBeNull")),
            ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX4 to (ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT4 to arrayOf("import io.kotest.matchers.shouldBe")),
            ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX3 to (ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT3 to arrayOf("import io.kotest.matchers.shouldBe")),
            ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX0 to (ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT0 to arrayOf("import io.kotest.matchers.shouldBe")),
            ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf("import io.kotest.matchers.shouldBe")),
            ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX2 to (ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT2 to arrayOf("import io.kotest.matchers.shouldBe")),
            ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX1 to (ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT1 to arrayOf("import io.kotest.matchers.shouldBe")),
            ASSERT_CONTAINS_STRING_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_CONTAINS_STRING_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf(
                "import io.kotest.matchers.string.shouldContain")),
            EVERY_TRUE_FROM_MOCKITO_TO_MOCKK_REGEX to (EVERY_TRUE_FROM_MOCKITO_TO_MOCKK_REPLACEMENT to arrayOf("import io.mockk.every")),
            EVERY_THROWS_FROM_MOCKITO_TO_MOCKK_REGEX0 to (EVERY_THROWS_FROM_MOCKITO_TO_MOCKK_REPLACEMENT0 to arrayOf("import io.mockk.every",
                "import io.mockk.mockk")),
            EVERY_THROWS_FROM_MOCKITO_TO_MOCKK_REGEX to (EVERY_THROWS_FROM_MOCKITO_TO_MOCKK_REPLACEMENT to arrayOf("import io.mockk.every")),
            VERIFY_TIMES_FROM_MOCKITO_TO_MOCKK_REGEX to (VERIFY_TIMES_FROM_MOCKITO_TO_MOCKK_REPLACEMENT to arrayOf("import io.mockk.verify")),
            VERIFY_SIMPLE_FROM_MOCKITO_TO_MOCKK_REGEX0 to (VERIFY_SIMPLE_FROM_MOCKITO_TO_MOCKK_REPLACEMENT0 to arrayOf("import io.mockk.verify")),
            VERIFY_SIMPLE_FROM_MOCKITO_TO_MOCKK_REGEX to (VERIFY_SIMPLE_FROM_MOCKITO_TO_MOCKK_REPLACEMENT to arrayOf("import io.mockk.verify")),
            VERIFY_SIMPLE_FROM_MOCKITO_TO_MOCKK_REGEX1 to (VERIFY_SIMPLE_FROM_MOCKITO_TO_MOCKK_REPLACEMENT to arrayOf("import io.mockk.verify")),
            ASSERT_FALSE_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_FALSE_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf("import io.kotest.matchers.booleans.shouldBeFalse")),
            ASSERT_TRUE_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_TRUE_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf("import io.kotest.matchers.booleans.shouldBeTrue")),
            CORRECTION1_REGEX to (CORRECTION1_REPLACEMENT to emptyArray()),
            CORRECTION2_REGEX to (CORRECTION2_REPLACEMENT to emptyArray()),
        )
        private val IMPORT_REPLACEMENT_JUNIT_TO_JUPITER = mapOf(
            Regex("import org.junit.Before") to "import org.junit.jupiter.api.BeforeEach",
            Regex("import org.junit.Test") to "import org.junit.jupiter.api.Test",
            Regex("import org.junit.runner.RunWith") to "import org.junit.jupiter.api.extension.ExtendWith",
            Regex("import org.mockito.junit.MockitoJUnitRunner") to "import io.mockk.junit5.MockKExtension",
            Regex("import org.mockito.Mock\n") to "import io.mockk.impl.annotations.MockK\n",
            Regex("import org.junit.Assert(;)?\n") to "",
            Regex("import org.junit.runner.RunWith(;)?\n") to "",
            Regex("import org.hamcrest.Matchers(;)?\n") to "",
            Regex("import org.hamcrest.core.IsNull(;)?\n") to "",
            Regex("import org.hamcrest.MatcherAssert(;)?\n") to "",
            Regex("import org.hamcrest.core.Is(;)?\n") to "",
            Regex("import org.mockito.Mockito(;)?\n") to "",
            Regex("import org.junit.Ignore(;)?\n") to "",
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
                    val (replace, imports) = instructions.second
                    val contains = acc.contains(find)
                    if (contains) {
                        val replacedText =
                            acc.replace(find,
                                replace)
                        val stringList = replacedText.split("\n").toMutableList()
                        imports.forEach { import ->
                            if (import !== "") {
                                stringList.add(2, import)
                            }
                        }

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

