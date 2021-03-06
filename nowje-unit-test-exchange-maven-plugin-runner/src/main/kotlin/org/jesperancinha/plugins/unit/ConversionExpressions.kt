package org.jesperancinha.plugins.unit


/**
 * Created by jofisaes on 25/12/2021
 */
class ConversionExpressions {
    companion object {
        private const val GENERIC_SEARCH_CHARACTERS2 = "0-9a-zA-Z_\\-\":,/\\.\\!\\[\\]\\+<>{}#\\?\\\\\\$"
        private const val GENERIC_SEARCH_CHARACTERS = "0-9a-zA-Z(_\\-\":,/\\.)\\!\\[\\]\\+<>{}#\\?\\\\\\$"
        private const val GENERIC_SEARCH_CHARACTERS_AND_SPACE = "$GENERIC_SEARCH_CHARACTERS "
        private const val GENERIC_GROUP_ALL = "([$GENERIC_SEARCH_CHARACTERS_AND_SPACE]*)"
        private const val GENERIC_GROUP = "([$GENERIC_SEARCH_CHARACTERS_AND_SPACE]*[$GENERIC_SEARCH_CHARACTERS]+)"
        private const val GENERIC_GROUP_ESCAPE_CHARS = "([$GENERIC_SEARCH_CHARACTERS_AND_SPACE`]*)"
        private const val GENERIC_GROUP_WITH_NEWLINE = "([$GENERIC_SEARCH_CHARACTERS_AND_SPACE\\n]*)"
        private const val GENERIC_GROUP_WITH_NEWLINE2 = "([$GENERIC_SEARCH_CHARACTERS2\\n (]*)"
        private const val GENERIC_GROUP_WITH_NEWLINE_STAR = "([$GENERIC_SEARCH_CHARACTERS_AND_SPACE\\n\\*]*)"
        private const val GENERIC_GROUP_WITH_NEWLINE_EQUALS = "([$GENERIC_SEARCH_CHARACTERS_AND_SPACE\\n\\=]*)"
        private const val GENERIC_GROUP_WITH_STAR = "([$GENERIC_SEARCH_CHARACTERS_AND_SPACE\\*]*)"
        private const val CONSTANT_GROUP = "(\"[$GENERIC_SEARCH_CHARACTERS_AND_SPACE]*\"|[0-9]*|[A-Z_]*)"
        private const val CONSTANT_GROUP1 = "(([A-Z]+[a-z]*(\\.)?)*)"
        private const val CONSTANT_GROUP2 = "([a-zA-Z]*)"
        private const val VARIABLE_GROUP = "([a-zA-Z_]*)"

        private val FAIL_FROM_JUNIT_TO_EXCEPTION_REGEX =
            Regex("Assert\\.fail\\($GENERIC_GROUP\\)")
        private const val FAIL_FROM_JUNIT_TO_EXCEPTION_REPLACEMENT = "throw RuntimeException(\$1)"

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
        private const val ASSERT_GREATER_THAN_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REPLACEMENT =
            "\$2 shouldBeGreaterThan \$3"

        private val ASSERT_EQUAL_TO_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REGEX =
            Regex("Assert\\.assertTrue\\($GENERIC_GROUP_WITH_STAR, $GENERIC_GROUP_WITH_STAR == $CONSTANT_GROUP\\)")
        private const val ASSERT_EQUAL_TO_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$2 shouldBe \$3"

        private val ASSERT_TRUE_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REGEX =
            Regex("Assert\\.assertTrue\\($CONSTANT_GROUP, $GENERIC_GROUP_WITH_STAR\\)")
        private const val ASSERT_TRUE_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$2.shouldBeTrue()"

        private val ASSERT_TRUE_FROM_JUNIT_TO_KOTEST_REGEX =
            Regex("Assert\\.assertTrue\\($GENERIC_GROUP_WITH_STAR\\)")
        private const val ASSERT_TRUE_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$1.shouldBeTrue()"

        /**
         * Same
         */
        private val ASSERT_FALSE_BUT_NOTSAME_FROM_JUNIT_TO_KOTEST_REGEX0 =
            Regex("Assert\\.assertFalse\\($CONSTANT_GROUP\\s*(===?|\\.equals)\\s*$GENERIC_GROUP\\)")
        private const val ASSERT_FALSE_BUT_NOTSAME_FROM_JUNIT_TO_KOTEST_REPLACEMENT0 =
            "\$3 shouldNotBeSameInstanceAs \$1"

        private val ASSERT_FALSE_BUT_NOTSAME_FROM_JUNIT_TO_KOTEST_REGEX =
            Regex("Assert\\.assertFalse\\($GENERIC_GROUP\\s*(===?|\\.equals)\\s*$GENERIC_GROUP\\)")
        private const val ASSERT_FALSE_BUT_NOTSAME_FROM_JUNIT_TO_KOTEST_REPLACEMENT =
            "\$1 shouldNotBeSameInstanceAs \$3"

        private val ASSERT_FALSE_BUT_SAME_FROM_JUNIT_TO_KOTEST_REGEX0 =
            Regex("Assert\\.assertTrue\\($CONSTANT_GROUP\\s*(===?|\\.equals)\\s*$GENERIC_GROUP\\)")
        private const val ASSERT_FALSE_BUT_SAME_FROM_JUNIT_TO_KOTEST_REPLACEMENT0 = "\$3 shouldBeSameInstanceAs \$1"

        private val ASSERT_FALSE_BUT_SAME_FROM_JUNIT_TO_KOTEST_REGEX =
            Regex("Assert\\.assertTrue\\($GENERIC_GROUP\\s*(===?|\\.equals)\\s*$GENERIC_GROUP\\)")
        private const val ASSERT_FALSE_BUT_SAME_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$1 shouldBeSameInstanceAs \$3"

        /**
         * Equals
         */
        private val ASSERT_FALSE_BUT_NOTEQUALS_FROM_JUNIT_TO_KOTEST_REGEX0 =
            Regex("Assert\\.assertFalse\\($CONSTANT_GROUP\\s*(==?|\\.equals)\\s*$GENERIC_GROUP\\)")
        private const val ASSERT_FALSE_BUT_NOTEQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT0 = "\$3 shouldNotBe \$1"

        private val ASSERT_FALSE_BUT_NOTEQUALS_FROM_JUNIT_TO_KOTEST_REGEX =
            Regex("Assert\\.assertFalse\\($GENERIC_GROUP\\s*(==?|\\.equals)\\s*$GENERIC_GROUP\\)")
        private const val ASSERT_FALSE_BUT_NOTEQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$1 shouldNotBe \$3"

        private val ASSERT_FALSE_BUT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX0 =
            Regex("Assert\\.assertTrue\\($CONSTANT_GROUP\\s*(==?|\\.equals)\\s*$GENERIC_GROUP\\)")
        private const val ASSERT_FALSE_BUT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT0 = "\$3 shouldBe \$1"

        private val ASSERT_FALSE_BUT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX =
            Regex("Assert\\.assertTrue\\($GENERIC_GROUP\\s*(==?|\\.equals)\\s*$GENERIC_GROUP\\)")
        private const val ASSERT_FALSE_BUT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$1 shouldBe \$3"

        /**
         *
         */
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

        private val ASSERT_NULL_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REGEX =
            Regex("Assert\\.assertNull\\($GENERIC_GROUP, $GENERIC_GROUP\\)")
        private const val ASSERT_NULL_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$2.shouldBeNull()"

        private val ASSERT_NULL_FROM_JUNIT_TO_KOTEST_REGEX = Regex("Assert\\.assertNull\\($GENERIC_GROUP\\)")
        private const val ASSERT_NULL_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$1.shouldBeNull()"

        private val ASSERT_NOTNULL_FROM_JUNIT_TO_KOTEST_REGEX = Regex("Assert\\.assertNotNull\\($GENERIC_GROUP\\)")
        private const val ASSERT_NOTNULL_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$1.shouldNotBeNull()"

        private val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX0 =
            Regex("Assert\\.assertEquals\\($CONSTANT_GROUP,(\n*)(\\s*)$GENERIC_GROUP\\)")
        private const val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT0 = "\$4 shouldBe \$1"

        private val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX5 =
            Regex("Assert\\.assertEquals\\($GENERIC_GROUP, $CONSTANT_GROUP2::class\\.java\\)")
        private const val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT5 = "\$1 shouldBe \$2::class.java"

        private val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX4 =
            Regex("Assert\\.assertEquals\\($CONSTANT_GROUP1, $GENERIC_GROUP_ALL\\)")
        private const val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT4 = "\$4 shouldBe \$1"

        private val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX =
            Regex("Assert\\.assertEquals\\($GENERIC_GROUP, $GENERIC_GROUP\\)")
        private const val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT = "\$1 shouldBe \$2"

        private val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX6 =
            Regex("Assert\\.assertEquals\\($GENERIC_GROUP, $GENERIC_GROUP_ESCAPE_CHARS\\)")
        private const val ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT6 = "\$2 shouldBe \$1"

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

        private val EVERY_THEN_FROM_MOCKITO_TO_MOCKK_REGEX =
            Regex("Mockito\\.`when`\\($GENERIC_GROUP\\)(\n)?(\\s*)?\\.then\\($GENERIC_GROUP\\)")
        private const val EVERY_THEN_FROM_MOCKITO_TO_MOCKK_REPLACEMENT = "every { \$1 } answers { \$4 }"

        private val EVERY_TRUE_FROM_MOCKITO_TO_MOCKK_REGEX =
            Regex("Mockito\\.`when`\\($GENERIC_GROUP\\)(\n)?(\\s*)?\\.thenReturn\\($GENERIC_GROUP\\)")
        private const val EVERY_TRUE_FROM_MOCKITO_TO_MOCKK_REPLACEMENT = "every { \$1 } returns \$4"

        private val EVERY_TRUE_FROM_MOCKITO_TO_MOCKK_REGEX1 =
            Regex("Mockito\\.`when`\\($GENERIC_GROUP\\)(\n)?(\\s*)?\\.thenReturn\\($GENERIC_GROUP_WITH_NEWLINE2\\)")
        private const val EVERY_TRUE_FROM_MOCKITO_TO_MOCKK_REPLACEMENT1 = "every { \$1 } returns \$4"

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

        private val EVERY_THEN_WITH_INVOCATION_ARGS_FROM_MOCKITO_TO_MOCKK_REGEX =
            Regex("Mockito.`when`\\($GENERIC_GROUP\\)\\.then(\\s*)\\{(\\s*)$VARIABLE_GROUP(\\s*)->\n" +
                    "(\\s*)(val|var) $VARIABLE_GROUP = $VARIABLE_GROUP\\.arguments\\[$CONSTANT_GROUP]\\.toString\\(\\)\n" +
                    GENERIC_GROUP_WITH_NEWLINE_EQUALS)
        private const val EVERY_THEN_WITH_INVOCATION_ARGS_FROM_MOCKITO_TO_MOCKK_REPLACEMENT =
            "every { \$1 } answers {\n" +
                    "\$6\$7 \$8 = it.invocation.args[\$10].toString()\n" +
                    "\$11"

        private val VERIFY_ATLEAST_FROM_MOCKITO_TO_MOCKK_REGEX =
            Regex("Mockito.verify\\($GENERIC_GROUP, Mockito\\.atLeast\\($GENERIC_GROUP\\)\\)(\n)?(\\s)*\\.$GENERIC_GROUP_ALL")
        private const val VERIFY_ATLEAST_FROM_MOCKITO_TO_MOCKK_REPLACEMENT = "verify(atLeast = \$2) { \$1.\$5 }"

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
        private val FIND_FUN_REGEX = Regex("fun $VARIABLE_GROUP")
        private val REPLACE_EXCEPTION_ANNOTATION_REGEX = Regex("@Test\\(expected = $GENERIC_GROUP::class\\)")
        private val SLOT_EXPRESSION = Regex("val $GENERIC_GROUP = slot<$VARIABLE_GROUP>\\(\\)")

        private val ANY_TYPE_REGEX =
            Regex("(ArgumentMatchers|Mockito)\\.any\\((\n)?(\\s*)?$GENERIC_GROUP::class\\.java\\)")
        private const val ANY_TYPE_REPLACEMENT = "any<\$4>()"

        private val ANY_STRING_REGEX =
            Regex("ArgumentMatchers.anyString\\(\\)")
        private const val ANY_STRING_REPLACEMENT = "any<String>()"

        private val ANY_SAME_REGEX =
            Regex("ArgumentMatchers\\.same\\($GENERIC_GROUP\\)")
        private const val ANY_SAME_REPLACEMENT = "$1"

        private val SLOT_REGEX =
            Regex("ArgumentCaptor.forClass\\((\n)?(\\s)*$GENERIC_GROUP::class\\.java\\)")
        private const val SLOT_REPLACEMENT = "slot<\$3>()"

        private val RULE_TEMP_FOLDER_FROM_JUNIT_REGEX =
            Regex("var $GENERIC_GROUP = TemporaryFolder\\(\\)")
        private const val RULE_TEMP_FOLDER_FROM_JUNIT_REPLACEMENT = "val \$1 = createTempDirectory()"

        private val RULE_TEMP_FILE_FROM_JUNIT_REGEX =
            Regex("$GENERIC_GROUP = $GENERIC_GROUP\\.newFile\\(\\)")
        private const val RULE_TEMP_FILE_FROM_JUNIT_REPLACEMENT = "\$1 = createTempFile(\$2).toFile()"

        private val RULE_TEMP_FILE_FROM_JUNIT_REGEX1 =
            Regex("$GENERIC_GROUP = $GENERIC_GROUP\\.newFile\\((\"$GENERIC_GROUP\"|$VARIABLE_GROUP)\\)")
        private const val RULE_TEMP_FILE_FROM_JUNIT_REPLACEMENT1 = "\$1 = Path(\$2.absolutePathString(), \$3).toFile()"

        private val KOTLIN_INJECTION_TEST_REGEX =
            Regex("(private )?(val|var) $GENERIC_GROUP: $GENERIC_GROUP\\? = null")
        private const val KOTLIN_INJECTION_TEST_REPLACE = "\$1lateinit var \$3: \$4"

        private val ANSWER_IMPLEMENTATION_FROM_MOCKITO_TO_MOCKK_TEST_REGEX =
            Regex("(\\s)*(protected )?(private )?fun $GENERIC_GROUP\\($GENERIC_GROUP\\): Answer<$GENERIC_GROUP> \\{\n*" +
                    "(\\s)*return Answer \\{ $GENERIC_GROUP: InvocationOnMock\\? ->\n*" +
                    "(\\s)*$GENERIC_GROUP\\(\n*" +
                    "(\\s)*$GENERIC_GROUP\\)\n*" +
                    "(\\s)*}\n*" +
                    "(\\s)*}")
        private const val ANSWER_IMPLEMENTATION_FROM_MOCKITO_TO_MOCKK_TEST_REPLACEMENT =
            "\$1\$2\$3fun \$4(\$5): \$6 = $6(\$12)"

        private val WIREMOCK_DECLARATION_FROM_RULE_TO_INSTANCE_REGEX =
            Regex("(private )?(protected )?(var|val) $GENERIC_GROUP = WireMockRule\\($CONSTANT_GROUP\\)")
        private const val WIREMOCK_DECLARATION_FROM_RULE_TO_INSTANCE_REPLACEMENT =
            "\$1\$2\$3 \$4 = WireMockServer(options().port(\$5))"

        private val CORRECTION1_REGEX =
            Regex("$GENERIC_GROUP shouldNotBe \\($GENERIC_GROUP\\)")
        private const val CORRECTION1_REPLACEMENT = "\$1 shouldNotBe \$2"

        private val CORRECTION2_REGEX =
            Regex("$GENERIC_GROUP shouldBe \\($GENERIC_GROUP\\)")
        private const val CORRECTION2_REPLACEMENT = "\$1 shouldBe \$2"

        private val CORRECTION3_REGEX =
            Regex("$CONSTANT_GROUP2\\.capture\\(\\)")
        private const val CORRECTION3_REPLACEMENT = "capture\\(\$1\\)"

        private val CORRECTION4_REGEX =
            Regex("$GENERIC_GROUP\\.$GENERIC_GROUP\\.absolutePath\n")
        private const val CORRECTION4_REPLACEMENT = "\$1.\$2.absolutePathString()\n"

        private val CORRECTION5_REGEX =
            Regex("$GENERIC_GROUP\\.$GENERIC_GROUP\\.absolutePath\\)")
        private const val CORRECTION5_REPLACEMENT = "\$1.\$2.absolutePathString()\\)"

        private val CORRECTION60_REGEX =
            Regex("$VARIABLE_GROUP\\.newFolder\\(\\)")
        private const val CORRECTION60_REPLACEMENT = "createTempDirectory(\$1).toFile()"

        private val CORRECTION61_REGEX =
            Regex("$VARIABLE_GROUP\\.newFolder\\($CONSTANT_GROUP, $CONSTANT_GROUP, $CONSTANT_GROUP\\)")
        private const val CORRECTION61_REPLACEMENT =
            "createDirectory(\$1.resolve(\$2).createDirectory().resolve(\$3).createDirectory().resolve(\$4)).toFile()"

        private val CORRECTION62_REGEX =
            Regex("$VARIABLE_GROUP\\.newFolder\\($CONSTANT_GROUP, $CONSTANT_GROUP\\)")
        private const val CORRECTION62_REPLACEMENT =
            "createDirectory(\$1.resolve(\$2).createDirectory().resolve(\$3)).toFile()"

        private val CORRECTION6_REGEX =
            Regex("$VARIABLE_GROUP\\.newFolder\\($CONSTANT_GROUP\\)")
        private const val CORRECTION6_REPLACEMENT = "createDirectory(\$1.resolve(\$2)).toFile()"

        private val CORRECTION7_REGEX =
            Regex("$VARIABLE_GROUP\\.newFile\\($CONSTANT_GROUP\\)")
        private const val CORRECTION7_REPLACEMENT = "createFile(\$1.resolve(\$2)).toFile()"

        private val CORRECTION8_REGEX =
            Regex("$CONSTANT_GROUP2\\.root(?!\\.)")
        private const val CORRECTION8_REPLACEMENT = "\$1.toFile()"

        private val CORRECTION9_REGEX =
            Regex("root\\.toURI\\(\\).toURL\\(\\)")
        private const val CORRECTION9_REPLACEMENT = "toUri().toURL()"

        private val ASSERT_REPLACE_IMPORT_JUNIT_TO_JUPITER = mutableListOf(
            FAIL_FROM_JUNIT_TO_EXCEPTION_REGEX to (FAIL_FROM_JUNIT_TO_EXCEPTION_REPLACEMENT to arrayOf("import io.kotest.assertions.any")),
            ANY_STRING_REGEX to (ANY_STRING_REPLACEMENT to arrayOf("import io.kotest.assertions.any")),
            ANY_TYPE_REGEX to (ANY_TYPE_REPLACEMENT to arrayOf("import io.kotest.assertions.any")),
            ANY_SAME_REGEX to (ANY_SAME_REPLACEMENT to emptyArray()),
            SLOT_REGEX to (SLOT_REPLACEMENT to arrayOf("import io.mockk.slot")),
            MOCK_FROM_MOCKITO_TO_MOCKK_REGEX to (MOCK_FROM_MOCKITO_TO_MOCKK_REPLACEMENT to arrayOf("import io.mockk.mockk")),
            ASSERT_EQUAL_TO_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_EQUAL_TO_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf(
                "import io.kotest.matchers.shouldBe")),
            ASSERT_GREATER_THAN_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_GREATER_THAN_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf(
                "import io.kotest.matchers.ints.shouldBeGreaterThan")),
            ASSERT_TRUE_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_TRUE_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf(
                "import io.kotest.matchers.booleans.shouldBeTrue")),
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
            ASSERT_FALSE_BUT_NOTSAME_FROM_JUNIT_TO_KOTEST_REGEX0 to (ASSERT_FALSE_BUT_NOTSAME_FROM_JUNIT_TO_KOTEST_REPLACEMENT0 to arrayOf(
                "import io.kotest.matchers.types.shouldNotBeSameInstanceAs")),
            ASSERT_FALSE_BUT_NOTSAME_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_FALSE_BUT_NOTSAME_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf(
                "import io.kotest.matchers.types.shouldNotBeSameInstanceAs")),
            ASSERT_FALSE_BUT_SAME_FROM_JUNIT_TO_KOTEST_REGEX0 to (ASSERT_FALSE_BUT_SAME_FROM_JUNIT_TO_KOTEST_REPLACEMENT0 to arrayOf(
                "import io.kotest.matchers.types.shouldBeSameInstanceAs")),
            ASSERT_FALSE_BUT_SAME_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_FALSE_BUT_SAME_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf(
                "import io.kotest.matchers.types.shouldBeSameInstanceAs")),
            EVERY_THROWS_FROM_MOCKITO_TO_MOCKK_REGEX1 to (EVERY_THROWS_FROM_MOCKITO_TO_MOCKK_REPLACEMENT1 to arrayOf("import io.kotest.assertions.throwables.shouldThrow")),
            ASSERT_EMPTY_FROM_HAMCREST_TO_KOTEST_REGEX to (ASSERT_EMPTY_FROM_HAMCREST_TO_KOTEST_REPLACEMENT to arrayOf("import io.kotest.matchers.collections.shouldBeEmpty")),
            ASSERT_IS_FROM_HAMCREST_TO_KOTEST_REGEX to (ASSERT_IS_FROM_HAMCREST_TO_KOTEST_REPLACEMENT to arrayOf("import io.kotest.matchers.shouldBe")),
            ASSERT_HAS_SIZE_FROM_HAMCREST_TO_KOTEST_REGEX to (ASSERT_HAS_SIZE_FROM_HAMCREST_TO_KOTEST_REPLACEMENT to arrayOf(
                "import io.kotest.matchers.collections.shouldHaveSize")),
            ASSERT_NULL_VALUE_IS_IS_FROM_HAMCREST_TO_KOTEST_REGEX to (ASSERT_NULL_VALUE_IS_IS_FROM_HAMCREST_TO_KOTEST_REPLACEMENT to arrayOf(
                "import io.kotest.matchers.nulls.shouldBeNull")),
            ASSERT_NULL_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_NULL_WITH_MESSAGE_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf(
                "import io.kotest.matchers.nulls.shouldBeNull")),
            ASSERT_NULL_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_NULL_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf("import io.kotest.matchers.nulls.shouldBeNull")),
            ASSERT_NOTNULL_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_NOTNULL_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf("import io.kotest.matchers.nulls.shouldNotBeNull")),
            ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX5 to (ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT5 to arrayOf("import io.kotest.matchers.shouldBe")),
            ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX4 to (ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT4 to arrayOf("import io.kotest.matchers.shouldBe")),
            ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX3 to (ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT3 to arrayOf("import io.kotest.matchers.shouldBe")),
            ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX0 to (ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT0 to arrayOf("import io.kotest.matchers.shouldBe")),
            ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf("import io.kotest.matchers.shouldBe")),
            ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX2 to (ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT2 to arrayOf("import io.kotest.matchers.shouldBe")),
            ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX1 to (ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT1 to arrayOf("import io.kotest.matchers.shouldBe")),
            ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REGEX6 to (ASSERT_EQUALS_FROM_JUNIT_TO_KOTEST_REPLACEMENT6 to arrayOf("import io.kotest.matchers.shouldBe")),
            ASSERT_CONTAINS_STRING_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_CONTAINS_STRING_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf(
                "import io.kotest.matchers.string.shouldContain")),
            EVERY_THEN_FROM_MOCKITO_TO_MOCKK_REGEX to (EVERY_THEN_FROM_MOCKITO_TO_MOCKK_REPLACEMENT to arrayOf("import io.mockk.every")),
            EVERY_TRUE_FROM_MOCKITO_TO_MOCKK_REGEX to (EVERY_TRUE_FROM_MOCKITO_TO_MOCKK_REPLACEMENT to arrayOf("import io.mockk.every")),
            EVERY_TRUE_FROM_MOCKITO_TO_MOCKK_REGEX1 to (EVERY_TRUE_FROM_MOCKITO_TO_MOCKK_REPLACEMENT1 to arrayOf("import io.mockk.every")),
            EVERY_THROWS_FROM_MOCKITO_TO_MOCKK_REGEX0 to (EVERY_THROWS_FROM_MOCKITO_TO_MOCKK_REPLACEMENT0 to arrayOf("import io.mockk.every",
                "import io.mockk.mockk")),
            EVERY_THROWS_FROM_MOCKITO_TO_MOCKK_REGEX to (EVERY_THROWS_FROM_MOCKITO_TO_MOCKK_REPLACEMENT to arrayOf("import io.mockk.every")),
            VERIFY_ATLEAST_FROM_MOCKITO_TO_MOCKK_REGEX to (VERIFY_ATLEAST_FROM_MOCKITO_TO_MOCKK_REPLACEMENT to arrayOf("import io.mockk.verify")),
            VERIFY_TIMES_FROM_MOCKITO_TO_MOCKK_REGEX to (VERIFY_TIMES_FROM_MOCKITO_TO_MOCKK_REPLACEMENT to arrayOf("import io.mockk.verify")),
            VERIFY_SIMPLE_FROM_MOCKITO_TO_MOCKK_REGEX0 to (VERIFY_SIMPLE_FROM_MOCKITO_TO_MOCKK_REPLACEMENT0 to arrayOf("import io.mockk.verify")),
            VERIFY_SIMPLE_FROM_MOCKITO_TO_MOCKK_REGEX to (VERIFY_SIMPLE_FROM_MOCKITO_TO_MOCKK_REPLACEMENT to arrayOf("import io.mockk.verify")),
            VERIFY_SIMPLE_FROM_MOCKITO_TO_MOCKK_REGEX1 to (VERIFY_SIMPLE_FROM_MOCKITO_TO_MOCKK_REPLACEMENT to arrayOf("import io.mockk.verify")),
            RULE_TEMP_FOLDER_FROM_JUNIT_REGEX to (RULE_TEMP_FOLDER_FROM_JUNIT_REPLACEMENT to arrayOf("import kotlin.io.path.createTempDirectory")),
            RULE_TEMP_FILE_FROM_JUNIT_REGEX to (RULE_TEMP_FILE_FROM_JUNIT_REPLACEMENT to arrayOf("import kotlin.io.path.createTempFile")),
            RULE_TEMP_FILE_FROM_JUNIT_REGEX1 to (RULE_TEMP_FILE_FROM_JUNIT_REPLACEMENT1 to emptyArray()),
            KOTLIN_INJECTION_TEST_REGEX to (KOTLIN_INJECTION_TEST_REPLACE to emptyArray()),
            ASSERT_FALSE_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_FALSE_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf("import io.kotest.matchers.booleans.shouldBeFalse")),
            ASSERT_TRUE_FROM_JUNIT_TO_KOTEST_REGEX to (ASSERT_TRUE_FROM_JUNIT_TO_KOTEST_REPLACEMENT to arrayOf("import io.kotest.matchers.booleans.shouldBeTrue")),
            ANSWER_IMPLEMENTATION_FROM_MOCKITO_TO_MOCKK_TEST_REGEX to (ANSWER_IMPLEMENTATION_FROM_MOCKITO_TO_MOCKK_TEST_REPLACEMENT to emptyArray()),
            EVERY_THEN_WITH_INVOCATION_ARGS_FROM_MOCKITO_TO_MOCKK_REGEX to (EVERY_THEN_WITH_INVOCATION_ARGS_FROM_MOCKITO_TO_MOCKK_REPLACEMENT to arrayOf(
                "import io.mockk.every")),
            WIREMOCK_DECLARATION_FROM_RULE_TO_INSTANCE_REGEX to (WIREMOCK_DECLARATION_FROM_RULE_TO_INSTANCE_REPLACEMENT to arrayOf(
                "import com.github.tomakehurst.wiremock.WireMockServer",
                "import com.github.tomakehurst.wiremock.core.WireMockConfiguration.options")),
            CORRECTION1_REGEX to (CORRECTION1_REPLACEMENT to emptyArray()),
            CORRECTION2_REGEX to (CORRECTION2_REPLACEMENT to emptyArray()),
            CORRECTION3_REGEX to (CORRECTION3_REPLACEMENT to emptyArray()),
            CORRECTION4_REGEX to (CORRECTION4_REPLACEMENT to arrayOf("import kotlin.io.path.absolutePathString")),
            CORRECTION5_REGEX to (CORRECTION5_REPLACEMENT to arrayOf("import kotlin.io.path.absolutePathString")),
            CORRECTION60_REGEX to (CORRECTION60_REPLACEMENT to arrayOf("import kotlin.io.path.createTempDirectory")),
            CORRECTION61_REGEX to (CORRECTION61_REPLACEMENT to arrayOf("import java.nio.file.Files.createDirectory",
                "import java.nio.file.Files.createDirectory",
                "import kotlin.io.path.createDirectory")),
            CORRECTION62_REGEX to (CORRECTION62_REPLACEMENT to arrayOf("import java.nio.file.Files.createDirectory",
                "import java.nio.file.Files.createDirectory","import kotlin.io.path.createDirectory")),
            CORRECTION6_REGEX to (CORRECTION6_REPLACEMENT to arrayOf("import kotlin.io.path.Path",
                "import kotlin.io.path.absolutePathString", "import java.nio.file.Files.createDirectory")),
            CORRECTION7_REGEX to (CORRECTION7_REPLACEMENT to arrayOf("import kotlin.io.path.Path",
                "import kotlin.io.path.absolutePathString", "import java.nio.file.Files.createFile")),
            CORRECTION8_REGEX to (CORRECTION8_REPLACEMENT to emptyArray()),
            CORRECTION9_REGEX to (CORRECTION9_REPLACEMENT to emptyArray()),
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
            Regex("import org.mockito.ArgumentCaptor(;)?\n") to "",
            Regex("import org.junit.Rule(;)?\n") to "",
            Regex("import org.junit.rules.TemporaryFolder(;)?\n") to "",
            Regex("import com.github.tomakehurst.wiremock.junit.WireMockRule(;)?\n") to "",
            Regex("(\\s*)Mockito.verifyNoMoreInteractions\\($GENERIC_GROUP\\)(;)?\n") to "",
        )

        private val ANNOTATIONS_REPLACEMENT_JUNIT_TO_JUPITER = mapOf(
            Regex("@Before\n") to "@BeforeEach\n",
            Regex("@Mock\n") to "@MockK(relaxed = true)\n",
            Regex("@RunWith\\(MockitoJUnitRunner::class\\)") to "@ExtendWith(MockKExtension::class)",
            Regex("@Test\\(expected = $GENERIC_GROUP::class\\)") to "@Test",
            Regex("\n(\\s)*@Rule\n") to "\n"
        )

        val OPERATIONS = listOf(
            { text: String -> processImports(text) },
            { text: String -> processExceptionExpectingTestMethods(text) },
            { text: String -> processAssertionsFindReplaceImport(text) },
            { text: String -> procesAnnotations(text) },
            { text: String -> processCapturedSlotValues(text) },
            { text: String -> processDistinctImports(text) },
        )

        private fun processDistinctImports(originalText: String): String {
            val stringList = originalText.split("\n").toMutableList()
            val indexOfFirst = stringList.indexOfFirst { it.startsWith("import") }
            val indexOfLast = stringList.indexOfLast { it.startsWith("import") }
            val allImports = mutableListOf<String>()
            if (indexOfFirst > -1) {
                for (i in indexOfFirst..indexOfLast) {
                    allImports.add(stringList.removeAt(indexOfFirst))
                }
                stringList.addAll(indexOfFirst, allImports.sorted().distinct())
            }
            return stringList.joinToString("\n")
        }

        private fun processCapturedSlotValues(originalText: String): String {
            var stringList = originalText.split("\n").toMutableList()
            var indexOfFunction = stringList.indexOfFirst { it.contains(FIND_FUN_REGEX) }
            var indexOfFirst = stringList.indexOfFirst(indexOfFunction) { it.contains(SLOT_EXPRESSION) }
            while (indexOfFunction > -1) {
                val allSlots = mutableListOf<String>()
                val indexOfNextClosure = stringList.indexOfNextClosure(indexOfFirst)
                while (indexOfFirst > -1 && indexOfFirst <= indexOfNextClosure) {
                    allSlots.add(stringList[indexOfFirst].replace(SLOT_EXPRESSION, "\$1").trim())
                    indexOfFirst = stringList.indexOfFirst(indexOfFirst) { it.contains(SLOT_EXPRESSION) }
                }
                allSlots.forEach {
                    val forExpression =
                        Regex("(\\s*)for(\\s*)\\($VARIABLE_GROUP(\\s*)in(\\s*)$it\\.allValues\\)(\\s*)\\{")
                    indexOfFirst = stringList.indexOfFirst(indexOfFunction) { test -> test.contains(forExpression) }
                    if (indexOfFirst > -1) {
                        val indexOfNextClosureForBlock = stringList.indexOfNextClosure(indexOfFirst)
                        val tab = stringList[indexOfFirst].replace(forExpression, "\$1")
                        val subValue = stringList[indexOfFirst].replace(forExpression, "\$3")
                        for (i in (indexOfFirst + 1) until indexOfNextClosureForBlock) {
                            if (stringList[i].contains(Regex("(, |\\(|\\.)$subValue(\\))"))) {
                                stringList[i] = "$tab${stringList[i].replace(subValue, "$it.captured").trim()}"
                            } else {
                                stringList[i] = "$tab${stringList[i].trim()}"
                            }
                        }
                        stringList.removeAt(indexOfFirst)
                        stringList.removeAt(stringList.indexOfNextClosure(indexOfFirst))
                    }
                }
                indexOfFunction = stringList.indexOfFirst(indexOfFunction) { it.contains(FIND_FUN_REGEX) }
            }
            return stringList.joinToString("\n")
        }

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
                        val j = getStartOfInstructionIndex(stringList, i - 1)
                        val sb = StringBuilder()
                        sb.append(stringList.removeAt(j))
                        for (k in j until i - 1) {
                            sb.append(stringList.removeAt(j).trim())
                        }
                        val toString = sb.toString()
                        val trimmedOldText = toString.trim()
                        stringList.add(j,
                            toString.replace(trimmedOldText, "shouldThrow<$className> { $trimmedOldText }"))
                        return stringList
                    }
                }
            }
            return stringList
        }

        private fun getStartOfInstructionIndex(stringList: MutableList<String>, startingPoint: Int): Int {
            var parCount = 0
            var maybe = false
            var valCharCheck = ' '
            var i = startingPoint
            while (i in 0..startingPoint) {
                val substring = stringList[i]
                val subStringLength = substring.length - 1
                var j = subStringLength
                while (j in 0..subStringLength) {
                    val currentChar = substring[j]
                    if (currentChar == ')') {
                        parCount++
                    }
                    if (currentChar == '(') {
                        parCount--
                    }
                    if (maybe && currentChar != '.' && parCount == 0) {
                        return i
                    }

                    maybe = parCount == 0 && (currentChar == ' ' || currentChar == '\n')

                    if (valCharCheck == ' ' && currentChar == 'l') {
                        valCharCheck = currentChar
                    } else if (valCharCheck == 'l' && currentChar == 'a') {
                        valCharCheck = currentChar
                    } else if (valCharCheck == 'a' && currentChar == 'v') {
                        valCharCheck = currentChar
                    } else valCharCheck = currentChar

                    if (valCharCheck == 'v') {
                        return i
                    }
                    j--
                }
                i--
            }
            return startingPoint

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

private fun MutableList<String>.indexOfNextClosure(indexOfFirst: Int): Int {
    var closureCount = 1
    forEachIndexed { index, it ->
        if (index >= indexOfFirst) {
            for (i: Int in it.indices) {
                if (it[i] == '{') {
                    closureCount++
                } else if (it[i] == '}') {
                    closureCount--
                }
            }
            if (closureCount == 0) {
                return index
            }
        }
    }
    return -1
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

