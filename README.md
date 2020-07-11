# About the App.

This is a demo App where I put my last programming knowledges and architecture concepts.

It’s a simple list-detail App listing the top 20 cryptocurrencies from [CoinGeko](https://www.coingecko.com/) API. (HODL!)

I kept it simple with the idea to focus on the architecture concepts instead of the implementation details of specific library. I considere much more important to have a good architecture understanding, at the end of the day libraries change, upgrade and new ones appear.

I've tried to touch the most trending frameworks, design paradigms like functional or reactive programming and last approaches which I've been working on in the last months and years. To name a few of them:

- Kotlin Coroutines → Language-native for asynchronous tasks, concurrency and reactive programming (Flow).
- Arrow (concretely the Either class) → Return a result with either success or error in a functional way.
- Redux (custom approach) → Transfor/map datasets from one type to another through different Actions.
- Dagger Hilt → Android-specific library from Dagger to solve Dependency Injection more easily.
- Compose UI → Most recent and modern toolkit for render UI in Android, a game changer! 🤯
- MockK -> To replace real classes with mocks when testing, also has tools to help in the given-when-then formula.

![](demogif.gif)

### Index
· [Architecture](#architecture)\
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;· [DATA Layer](#data-layer)\
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;· [DOMAIN Layer](#domain-layer)\
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;· [PRESENTATION Layer](#presentation-layer)\
· [Dependency Injection](#dependency-injection)\
· [Modulation vs Monolith](#m