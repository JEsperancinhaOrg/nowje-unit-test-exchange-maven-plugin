# Manual for Option 1 - unit-test-exchange-maven-plugin - 

## 1. Introduction

With option 1, we can automate the following conversions:

- `Junit` to `Junit Jupiter`
- `Hamcrest` to `Kotest`
- `AssertJ` to `Kotest`
- `Wiremock for Junit` to `Wiremock for Jupiter`

## 2. Dependencies

You need first to add the following dependencies:

```xml

<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>${junit-jupiter.version}</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>${junit-jupiter.version}</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>io.mockk</groupId>
        <artifactId>mockk</artifactId>
        <scope>test</scope>
        <version>${mockk.version}</version>
    </dependency>
    <dependency>
        <groupId>io.kotest</groupId>
        <artifactId>kotest-runner-junit5-jvm</artifactId>
        <scope>test</scope>
        <version>${kotest.version}</version>
    </dependency>
    <dependency>
        <groupId>io.kotest</groupId>
        <artifactId>kotest-assertions-core-jvm</artifactId>
        <scope>test</scope>
        <version>${kotest.version}</version>
    </dependency>
</dependencies>
```

The chosen versions may influence the way the conversion occurs

## 3. Unit tests in Kotlin

You may not have the unit tests in Kotlin yet. If you don't then you can try and run the automatic[ conversion tool from Intellij](https://www.jetbrains.com/help/idea/get-started-with-kotlin.html#5f5d8c7). The results really depend on the Intellij version you are using. It is very likely that the automated conversion from Java to Kotlin isn't perfect, but this is because Kotlin makes a clear distinction between nullables and non-nullables. In Java, everything is nullable by default. In Kotlin, you always have to specify if you want your types to be nullable. This creates a wide array of possibilities when making the conversion. My best advice to you is to just make the automated conversion to Kotlin, make sure the code compiles and that the unit tests run. Also, if you can, remove all the non-null assertions `!!`. They are not meant to be actually used, and they only bridge a temporary gap between Java and Kotlin. The latter is not mandatory but it can reduce the amount of
compiling errors that may be generated. Some of you readers may have the opinion that the automated tool of Intellij to convert Java code to Kotlin is very cumbersome and shouldn't be used. I disagree. Although the resulting code may not be perfect, it is still an excellent baseline for a conversion to Kotlin process. In my experience, the resulting code always works and the unit test always work. It is always a lot less work than doing everything manually.
 
General rule of thumb is: make sure your code is in Kotlin, formatted in a standard way and that the imports are optimized and clean.

## 4. Running the conversion tool

Run the conversion tool as mentioned in the instructions on the [Readme.md](https://github.com/JEsperancinhaOrg/unit-test-exchange-maven-plugin/blob/main/unit-test-exchange-maven-plugin-runner/Readme.md) file.

## 5. Aftermath

In the aftermath you may encounter a few problems:

#### 5.1. MockK relaxed mocks

In order to provide a smooth transition, the converter migrates the Mock's from Mockito to the relaxed counter-part of MockK. The upside of this is that these won't complain about unimplemented mock methods. The downside is that unfortunately a MockK does not behave in exactly the same way as the Mocks from Mockito. If you are asserting null values from unimplemented mock methods, you will find differences in behaviour and your tests will fail. The fix is simple to do, of course, but not yet automated. So this means that in this case you'll have to change the code manually and add the missing implementations. However, if you are using this kind of expectations, it may just be that you unit test needs some kind of improvement purely because in that case it is not explicitly implemented what that unit test should do. This is very prone to errors in any case. 


#### 5.2. Compile errors and unit test failures

I will always try my best to avoid having compile errors as a result. They may occur and they are expected. Test failures are more difficult to solve. Current project still does not use a separate compiler during runtime and it still does not execute the resulting unit tests after conversion.
All of these are expected. Running the compiler works for you and makes sure some actions are automated. You definitely get work done much quicker. However you still need to do some manual work to correct issues that may occur after running the converter.

#### 5.3. Commit the code

Once you've compared your resulting code to your CVS last commit, have performed all potential resulting errors, made sure that everything builds and that the new unit tests run as expected, you are ready to commit.
