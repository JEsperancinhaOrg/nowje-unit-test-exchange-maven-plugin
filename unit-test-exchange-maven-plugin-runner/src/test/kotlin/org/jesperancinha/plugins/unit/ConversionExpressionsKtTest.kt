package org.jesperancinha.plugins.unit

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test


internal class ConversionExpressionsKtTest {

    @Test
    fun `should make imports unique`() {
        val test = """
                $PACKAGE
                import io.kotest.matchers.shouldBe
                import io.kotest.matchers.shouldBe
                import io.kotest.matchers.nulls.shouldBeNull
                import io.kotest.matchers.shouldBe
                import io.kotest.matchers.shouldNotBe
                import org.junit.jupiter.api.Disabled
                import io.kotest.matchers.collections.shouldContainExactly
                import io.kotest.assertions.throwables.shouldThrow
                import org.junit.jupiter.api.Test
                
                anything
            """.trimIndent()
        val processTests = test.processTests
        processTests shouldBe """
            $PACKAGE
            import io.kotest.assertions.throwables.shouldThrow
            import io.kotest.matchers.collections.shouldContainExactly
            import io.kotest.matchers.nulls.shouldBeNull
            import io.kotest.matchers.shouldBe
            import io.kotest.matchers.shouldNotBe
            import org.junit.jupiter.api.Disabled
            import org.junit.jupiter.api.Test
            
            anything
        """.trimIndent()
    }
    @Test
    fun `should convert slot variables from loops to single usage`() {
        val test = """
                $PACKAGE
                import org.junit.jupiter.api.Test
                @Test
                fun testRacoonsAndBonobos() {
                    val fruitCaptor = slot<Chunky>()
                    for (food in fruitCaptor.allValues) {
                        sendFood.onHunger(food)
                        sendFood.onHunger(fooddie)
                    }
                }
            """.trimIndent()
        val processTests = test.processTests
        processTests shouldBe """
            $PACKAGE
            import org.junit.jupiter.api.Test
            @Test
            fun testRacoonsAndBonobos() {
                val fruitCaptor = slot<Chunky>()
                sendFood.onHunger(fruitCaptor.captured)
                sendFood.onHunger(fooddie)
            }
        """.trimIndent()
    }

    @Test
    fun `should convert wiremock declarations`() {
        val test = """
            $PACKAGE
            import org.junit.jupiter.api.Test
            import com.github.tomakehurst.wiremock.junit.WireMockRule
            var racoonsSever = WireMockRule(WIREMOCK_PORT)
        """.trimIndent()

        val processTests = test.processTests
        processTests shouldBe """
            $PACKAGE
            import com.github.tomakehurst.wiremock.WireMockServer
            import com.github.tomakehurst.wiremock.core.WireMockConfiguration.options
            import org.junit.jupiter.api.Test
            var racoonsSever = WireMockServer(options().port(WIREMOCK_PORT))
        """.trimIndent()
    }

    @Test
    fun `should convert returns`() {
        val test = """
            $PACKAGE
            import org.junit.jupiter.api.Test
            Mockito.`when`(racoons.eatFromTrash()).thenReturn(Bonobos.getUpset(
                racoonResources.pickUp()))
        """.trimIndent()

        val processTests = test.processTests
        processTests shouldBe """
            $PACKAGE
            import io.mockk.every
            import org.junit.jupiter.api.Test
            every { racoons.eatFromTrash() } returns Bonobos.getUpset(
                racoonResources.pickUp())
        """.trimIndent()
    }

    @Test
    fun `should convert new file invocations`() {
        val test = """
            $PACKAGE
            import org.junit.jupiter.api.Test
            val racoonFile = racoonFileCabinet.newFile(fileName)
            val bonoboFile = bonoboFileCabinet.newFile()
        """.trimIndent()

        val processTests = test.processTests
        processTests shouldBe """
            $PACKAGE
            import kotlin.io.path.createTempFile
            import org.junit.jupiter.api.Test
            val racoonFile = Path(racoonFileCabinet.absolutePathString(), fileName).toFile()
            val bonoboFile = createTempFile(bonoboFileCabinet).toFile()
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

        val processTests = test.processTests
        processTests shouldBe """
            $PACKAGE
            import io.kotest.assertions.any
            import io.mockk.every
            import org.junit.jupiter.api.Test
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
            import io.kotest.matchers.booleans.shouldBeTrue
            import org.junit.jupiter.api.Test
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
            import io.kotest.matchers.shouldBe
            import org.junit.jupiter.api.Test
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

        val processTests = test.processTests
        processTests shouldBe """
            $PACKAGE
            import kotlin.io.path.createTempDirectory
            import kotlin.io.path.createTempFile
            import org.junit.jupiter.api.Test
            val wilderness = createTempDirectory()
            bonobo = createTempFile(wilderness).toFile()
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

        val processTests = test.processTests
        processTests shouldBe """
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

        val processTests = test.processTests
        processTests shouldBe """
            $PACKAGE
            import io.kotest.assertions.throwables.shouldThrow
            import org.junit.jupiter.api.Test
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

        val processTests = test.processTests
        processTests shouldBe """
            $PACKAGE
            import io.mockk.every
            import org.junit.jupiter.api.Test
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

        val processTests = test.processTests
        processTests shouldBe """
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

        val processTests = test.processTests
        processTests shouldBe """
            $PACKAGE
            import io.mockk.every
            import kotlin.io.path.Path
            import kotlin.io.path.absolutePathString
            import kotlin.io.path.createDirectory
            import org.junit.jupiter.api.Test
            every { bonono.camp } returns Path(archive.absolutePathString(), "racoon").createDirectory().toFile()
        """.trimIndent()
    }

    companion object {

        const val PACKAGE = "package org.jesperancinha.plugins.unit"
    }
}