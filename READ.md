# G-News

An open-source Android app listing headlines written in Kotlin

Introduction:
=====

>  G-News implement an Architecture which is testable and maintainable many way to enhance ;)

G-News is my play project, where test my learning and other architectures with my current stable tech-stack <br>It's pretty close implementation of full-fledged application, dependency Injection, complete with Network Requests, Local DB, Material Design.

It developed with well tested code cherries like **Kotlin**, **RxJava**, **Jetpack** & **AndroidX**.
<br>
It's a well packaged code base

G-News is built to some well maintainable project code base . I'm not a UX guy ,bt still try to design application for ease of use inspired from day-today web-sites/apps. :D

have some play to try around with different library to get reach in more developer ease :B

Build Instructions:
=====

1. Just Clone the Project

2. Gradle Sync and Rebuild

Design Decisions & Dependencies:
=====

### [Kotlin](https://kotlinlang.org/):

As android world is in love with Kotlin, for its elegant and evolving patterns

### [JetPack](https://developer.android.com/jetpack) - Architecture Components & AndroidX:
Would be a loss to build an application without these libraries. With Google advocating MVVM, and these libraries working so flawlessly with each other, it really leaves you no choice.
<br>**Room** - Database Layer
<br>**ViewModel** - Data safeguarding across system configuration changes
<br>**Lifecycle** - surfing the issues with Activities / Fragments namely when pushing data
<br>**Paging** - to load the news efficiently called to be lazy :)
<br>**AndroidX, Material Components** - For embracing Material Design and backporting API features to minSdk

### [JUnit](https://junit.org/) + [Mockito](https://site.mockito.org/) - Unit Testing:
A gold combination for unit testing and mocking dependencies. Since the app follows SOLID, dependent layers can be easily mocked and tested in isolation.
<br>I'm using [Mockito-Kotlin](https://github.com/nhaarman/mockito-kotlin) for a simpler DSL (and not writing ``when`` because â€˜whenâ€™ is a Kotlin keyword)

### some Gems

- [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
- [Dependency Updates Plugin](https://github.com/ben-manes/gradle-versions-plugin)