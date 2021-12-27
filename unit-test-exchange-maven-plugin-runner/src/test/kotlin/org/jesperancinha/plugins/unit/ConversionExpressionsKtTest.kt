package org.jesperancinha.plugins.unit

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test


internal class ConversionExpressionsKtTest {

    @Test
    fun `should convert new file invocations`() {
        val test = """
            $PACKAGE
            import org.junit.jupiter.api.Test
            val racoonFile = racoonFileCabinet.newFile(fileName)
            val bonoboFile = bonoboFileCabinet.newFile()
        """.trimIndent()

        val processeTests = test.processTests
        processeTests shouldBe """
            $PACKAGE
            import org.junit.jupiter.api.Test
            import kotlin.io.path.createFile
            val racoonFile = Path(racoonFileCabinet.absolutePathString(), fileName).toFile()
            val bonoboFile = bonoboFileCabinet.createFile().toFile()
        """.trimIndent()
    }

    @Test
    fun `should convert invocation args mockito method`() {
        val test = """
            $PACKAGE
            import org.junit.jupiter.api.Test
            Mockito.`when`(bananaService.getPackagePerRef(ArgumentMatchers.anyString())).then { invocation ->
                val crates = invocation.arguments[0].toString()
                val bananas = openCrates(crates)
                Contents(crates, bananas, nBonobos)
            }
        """.trimIndent()

        val processeTests = test.processTests
        processeTests shouldBe """
            $PACKAGE
            import org.junit.jupiter.api.Test
            import io.mockk.every
            import io.kotest.assertions.any
            every { bananaService.getPackagePerRef(any<String>()) } answers {
                val crates = it.invocation.args[0].toString()
                val bananas = openCrates(crates)
                Contents(crates, bananas, nBonobos)
            }
        """.trimIndent()
    }

    @Test
    fun `should return test shouldBeTrue`() {
        val test = """
            $PACKAGE
            import org.junit.Test
            Assert.assertTrue(RacoonsBonobos(badRacoons, badMojo).racoonsBonobos!!.kanjer.isDom)
            """.trimIndent()


        val processTests = test.processTests
        processTests shouldBe """
            $PACKAGE
            import org.junit.jupiter.api.Test
            import io.kotest.matchers.booleans.shouldBeTrue
            RacoonsBonobos(badRacoons, badMojo).racoonsBonobos!!.kanjer.isDom.shouldBeTrue()
            """.trimIndent()
    }

    @Test
    fun `should generate test with right order shouldBe`() {
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
    fun `should generate test from Rule`() {
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
    fun `should convert to late init var`() {
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
    fun `should convert expected Exception Test`() {
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

    @Test
    fun `should convert Mockito then answers`() {
        val test = """
            $PACKAGE
            import org.junit.jupiter.api.Test
            Mockito.`when`(racoonMock!!.create(nest!!)).then(createMockNest(mockMaterials))
        """.trimIndent()

        val processeTests = test.processTests
        processeTests shouldBe """
            $PACKAGE
            import org.junit.jupiter.api.Test
            import io.mockk.every
            every { racoonMock!!.create(nest!!) } answers { createMockNest(mockMaterials) }
        """.trimIndent()
    }

    @Test
    fun `should convert answer implementation method`() {
        val test = """
            $PACKAGE
            import org.junit.jupiter.api.Test
            private fun bonoboAnswer(name: String?): Answer<Bonobo> {
                return Answer { invocation: InvocationOnMock? ->
                    Bonobo(
                         banana, game, clock))
                }
            }
        """.trimIndent()

        val processeTests = test.processTests
        processeTests shouldBe """
            $PACKAGE
            import org.junit.jupiter.api.Test
            private fun bonoboAnswer(name: String?): Bonobo = Bonobo(banana, game, clock))
            """.trimIndent()
    }

    @Test
    fun `should convert new Folder creation`() {
        val test = """
            $PACKAGE
            import org.junit.jupiter.api.Test
            Mockito.`when`(bonono.camp).thenReturn(archive.newFolder("racoon"))
        """.trimIndent()

        val processeTests = test.processTests
        processeTests shouldBe """
            $PACKAGE
            import org.junit.jupiter.api.Test
            import kotlin.io.path.createDirectory
            import kotlin.io.path.absolutePathString
            import kotlin.io.path.Path
            import io.mockk.every
            every { bonono.camp } returns Path(archive.absolutePathString(), "racoon").createDirectory().toFile()
        """.trimIndent()
    }

    companion object {

        const val PACKAGE = "package org.jesperancinha.plugins.unit"
    }
}