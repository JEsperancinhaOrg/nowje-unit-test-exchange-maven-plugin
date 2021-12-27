package org.jesperancinha.plugins.unit

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test


internal class ConversionExpressionsKtTest{

    @Test
    fun `should return test shouldBeTrue`() {
        val test = """
            $PACKAGE
            import org.junit.Test
            Assert.assertTrue(RacoonsBonobos(raboSloppy, badMojo).racoonsBonobos!!.kanjer.isDom)
            """.trimIndent()


        val processTests = test.processTests
        processTests shouldBe """
            $PACKAGE
            import org.junit.jupiter.api.Test
            import io.kotest.matchers.booleans.shouldBeTrue
            RacoonsBonobos(raboSloppy, badMojo).racoonsBonobos!!.kanjer.isDom.shouldBeTrue()
            """.trimIndent()
    }

    @Test
    fun `should generate test with right order shouldBe`(){
        val test = """
            $PACKAGE
            import org.junit.Test
            Assert.assertEquals(ex.cause!!.javaClass, GoodException::class.java)
        """.trimIndent()
        val processTests = test.processTests
        processTests shouldBe """
            $PACKAGE
            import org.junit.jupiter.api.Test
            import io.kotest.matchers.shouldBe
            ex.cause!!.javaClass shouldBe GoodException::class.java
        """.trimIndent()
    }

    @Test
    fun `should generate test from Rule`(){
        val test = """
            $PACKAGE
            import org.junit.Test
            import org.junit.rules.TemporaryFolder
            import org.junit.Rule
            @Rule
            var wilderness = TemporaryFolder()
            bonobo = wilderness.newFile()
        """.trimIndent()

        val processeTests = test.processTests
        processeTests shouldBe """
            $PACKAGE
            import org.junit.jupiter.api.Test
            import kotlin.io.path.createFile
            import kotlin.io.path.createTempDirectory
            val wilderness = createTempDirectory()
            bonobo = wilderness.createFile().toFile()
        """.trimIndent()
    }

    @Test
    fun `should convert to late init var`(){
        val test = """
            $PACKAGE
            import org.junit.jupiter.api.Test
            val racoonService: RacoonService? = null
            private val bonoboService: BonoboService? = null
        """.trimIndent()

        val processeTests = test.processTests
        processeTests shouldBe """
            $PACKAGE
            import org.junit.jupiter.api.Test
            lateinit var racoonService: RacoonService
            private lateinit var bonoboService: BonoboService
        """.trimIndent()
    }

    @Test
    fun `should convert expected Exception Test`(){
        val test = """
            $PACKAGE
            import org.junit.jupiter.api.Test
            @Test(expected = CreationException::class)
            fun testCreateParkFailTest() {
                createRacoonWildPark().doItNow(
                    now)
            }
        """.trimIndent()

        val processeTests = test.processTests
        processeTests shouldBe """
            $PACKAGE
            import org.junit.jupiter.api.Test
            import io.kotest.assertions.throwables.shouldThrow
            @Test
            fun testCreateParkFailTest() {
                shouldThrow<CreationException> { createRacoonWildPark().doItNow(now) }
            }
        """.trimIndent()
    }

    companion object {

        const val PACKAGE = "package org.jesperancinha.plugins.unit"
    }
}