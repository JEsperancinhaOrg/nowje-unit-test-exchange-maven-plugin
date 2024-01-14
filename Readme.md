# nowje-unit-test-exchange-maven-plugin

[![Twitter URL](https://img.shields.io/twitter/url?logoColor=blue&style=social&url=https%3A%2F%2Fimg.shields.io%2Ftwitter%2Furl%3Fstyle%3Dsocial)](https://twitter.com/intent/tweet?text=%20Checkout%20this%20%40github%20repo%20by%20%40joaofse%20%F0%9F%91%A8%F0%9F%8F%BD%E2%80%8D%F0%9F%92%BB%3A%20https%3A//github.com/JEsperancinhaOrg/nowje-unit-test-exchange-maven-plugin)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=nowje-unit-test-exchange-maven-plugin&color=informational)](https://github.com/JEsperancinhaOrg/nowje-unit-test-exchange-maven-plugin)

[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

[![Status badge](https://img.shields.io/static/v1.svg?label=Status&message=Under%20Construction%20🚧&color=informational)](https://github.com/JEsperancinhaOrg/nowje-unit-test-exchange-maven-plugin)
[![nowje-unit-test-exchange-maven-plugin](https://github.com/JEsperancinhaOrg/nowje-unit-test-exchange-maven-plugin/actions/workflows/nowje-unit-test-exchange-maven-plugin.yml/badge.svg)](https://github.com/JEsperancinhaOrg/nowje-unit-test-exchange-maven-plugin/actions/workflows/nowje-unit-test-exchange-maven-plugin.yml)

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/611a6a9698a94cfb99dd44c02e11417e)](https://www.codacy.com/gh/JEsperancinhaOrg/nowje-unit-test-exchange-maven-plugin/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=JEsperancinhaOrg/nowje-unit-test-exchange-maven-plugin&amp;utm_campaign=Badge_Grade)

[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/611a6a9698a94cfb99dd44c02e11417e)](https://www.codacy.com/gh/JEsperancinhaOrg/nowje-unit-test-exchange-maven-plugin/dashboard?utm_source=github.com&utm_medium=referral&utm_content=JEsperancinhaOrg/nowje-unit-test-exchange-maven-plugin&utm_campaign=Badge_Coverage)
[![Coverage Status](https://coveralls.io/repos/github/JEsperancinhaOrg/nowje-unit-test-exchange-maven-plugin/badge.svg?branch=main)](https://coveralls.io/github/JEsperancinhaOrg/nowje-unit-test-exchange-maven-plugin?branch=main)
[![codecov](https://codecov.io/gh/JEsperancinhaOrg/nowje-unit-test-exchange-maven-plugin/branch/main/graph/badge.svg?token=Zy8Z6jcYW0)](https://codecov.io/gh/JEsperancinhaOrg/nowje-unit-test-exchange-maven-plugin)

[![GitHub language count](https://img.shields.io/github/languages/count/jesperancinhaorg/unit-test-exchange-maven-plugin.svg)](#)
[![GitHub top language](https://img.shields.io/github/languages/top/jesperancinhaorg/unit-test-exchange-maven-plugin.svg)](#)
[![GitHub top language](https://img.shields.io/github/languages/code-size/jesperancinhaorg/unit-test-exchange-maven-plugin.svg)](#)

This plugin works as a tool to convert unit tests from one standard to another. The goal is to support conversion between many types of unit tests.
Although the goal is to having it working flawlessly, it is actually, very hard to do it in practice. Think of this project as a helper if you want to convert between unit test, assertions and mock frameworks.
This project will, however suffer multiple improvements as I go along, and so your input is very valuable and please feel free to open issues if you find bugs. I'm constantly trying to fix bugs I find and if I have yours, I can make this project event better for all of us.

## Release Dates

--- Stand By ---

There are no official dates planned yet. The code is still very experimental and there are no snapshots yet. You can, however, checkout the code already, compile it, and run it locally against your unit tests. The maven-plugin is not ready yet, but you can already try the runner. Check the [Readme.md](./nowje-unit-test-exchange-maven-plugin-runner/Readme.md) file on how to use it. I don't offer any guarantees at this point though, so make sure to check the results before committing anything. Checkout the [Roadmap](./Roadmap.md) for more detais.

## Options

| Option | Function                                                                                                                      | Source Code |Target Code| Status                                                                                               | Manuals                            |
|--------|-------------------------------------------------------------------------------------------------------------------------------|-------------|---|------------------------------------------------------------------------------------------------------|------------------------------------|
| 1      | Converts from JUnit, Mockito, Hamcrest, AssertJ, Wiremock Rules to MockK, Jupiter, Wiremock for Jupiter and Kotest assertions | Kotlin      |Kotlin| InDevelopment (Mostly working. Wiremock not available yet. Still buggy in some Mockito corner cases) | [Option 1 Manual](https://jesperancinhaorg.github.io/nowje-unit-test-exchange-maven-plugin/manuals/Manual1.html) |
| 2      | Converts from JUnit, Mockito, Hamcrest, AssertJ to MockK, Jupiter and Kotest assertions                                       | Java        |Kotlin| On Hold                                                                                              | N/A                                |

## Before running this plugin

Make sure you have your code formatted according initial expectations as much as possible. The rules are:

1.  Annotated methods in separate lines
2.  Annotated test methods should have no space between parenthesis if they receive value directly as the following example:

```kotlin
@Test(expected = IOException::class)
fun testThisStuff() {
}
```

3.  Make sure that the source code and target code match the choice you make
4.  Clean imports and make sure you removed all code not being used.
5.  Remember to always have a space after a comma. This is usually standard, and you don't have to think about this.

```kotlin
Assert.assertThat(text, Matchers.containsString(test))
```

6.  Take special note on manually hard-coded error messages. This converter does not keep the error messages in most cases. Frameworks like Kotest already describe with enough depth what happens to a test when it fails. I will, however provide support for this in the future. (see table above)
7.  There are no 100% guarantees that the resulting code will compile and run immediately after you run this. Manual intervention afterwards may be necessary.
8.  Always check your code before committing. Check your SCM(GIT/SVN) for code errors. Compare the local version with the latest commit before making a new commit
9.  If there are compiling errors, make sure to fix them. If there are running errors make sure to fix that as well.

## FAQ

1.  <b>Why did you built this in a hardcoded way? Wouldn't it be better to have a configuration file and use that with a Regex expression list?</b>   
R. Short answer to this question is because it is not useful. A Long answer is much more complicated than that. This engine is implemented with search and replace functions which make heavy usage of ReGex expression. This demands processing time and for the most part it is a very reliable process. Not very efficient, but very reliable. However, the sequence of regular expressions change the code per executed expression. Some search expressions require extra processing such as the case of migrating a `shouldThrow` from annotated unit tests. On the other hand I'm developing with this project a reliable way to convert unit tests to whatever version you want them to be converted. This is especially useful for companies that want to update their code, have thousands of lines written in JUnit 3 and 4 and now want to move on to other frameworks like Kotest, Jupiter 5 or even Kotest engine, just to name a few. Making this reliable is a major concern for mine in this project. This implies the implementation of unit, integration and regression tests which will test if the sequence of regular expressions and the code processing on the fly works as it is meant to be. Allowing external configurations would get in the way of achieving an increasing reliability for every release.

## About me

[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
