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
· [Modulation vs Monolith](#modulation-or-monolith)\
· [Navigation](#navigation)\
· [Testing](#testing)

# Architecture.

Build under the SOLID principles and Clean Architecture values, inspired on the new official [recommended architecture](https://developer.android.com/jetpack/guide) from Google along with the most interesting concepts I’ve been acquiring throughout my career. 

Always [YAGNI](https://es.wikipedia.org/wiki/YAGNI) principle in mind, one of the most important for me.

### Package organisation.

- core → Base classes and utilities.
- features → All the app capabilities separated in specific packages.
- theme → Compose themming stuff.

Every feature may have 3 main packages corresponding to Clean Architecture layers concept, I’ve logically divided into: **DATA, DOMAIN** and **PRESENTATION.**

<img width="600" alt="Screenshot 2022-02-27 at 18 31 35" src="https://user-images.githubusercontent.com/12541369/155892990-8206b4c0-2cfe-4281-8c94-5cc9d9016c2f.png">

## DATA Layer.

WHERE and HOW the Data is managed, retrieved and saved. Has 2 important pieces.

### Data source.

Knows HOW to retrieve the data from remote or local sources and maps it to **DOMAIN** objects (may have a companion mapper class to help in this process), simple and respecting the **S**ingle object responsability principle of SOLID. 

An important thing here is to make it implementation-agnostic from the other classes:

I.e. in the App, `CoinRetrofitDataSource` is using [Retrofit](https://square.github.io/retrofit/) to retrieve the crypto markets, if tomorrow I want to change the API and use [CoinMarketCap](https://coinmarketcap.com/) or [Coinpaprika](https://coinpaprika.com/) I will only need to change the implementation of this class without breaking any other part of the app or any side effects!
In order to do that, I’ve applied the **D**ependency inversion principle of SOLID, Imp