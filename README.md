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
In order to do that, I’ve applied the **D**ependency inversion principle of SOLID, Implementing and `Interface` and exposing that contract to the `Repository`.

### Repository.

It has the business-logic, knowns WHERE to ask for the data, it is the entry point of the **DATA** layer and acts as single source of truth for the **DOMAIN** layer. May contain 1-N data sources.

I.e in the App, `CoinRepository` is subscribed to the current selected fiat currency [Flow](https://developer.android.com/kotlin/flow) from the local DataSource `CoinLocalDataSource`. Every time fiat currency changes, a new item is emitted and Repository reacts requesting the data from remote DataSource `CoinRemoteDataSource`. 

Repository is responsable to fetch data from remote and save it locally if required, or retrieve data only from a specific datasource in some situations, whatever it is business-logic.

![image](https://user-images.githubusercontent.com/12541369/155104082-9fbdf862-8967-46aa-b151-e67255078c94.png)\
<em>Request coins flow. (CoinLocalDataSource part is omitted)</em>

## DOMAIN Layer.

Simple layer but not less important, It contains all the Dataset shared among all the App. In here exist the UseCase’s. 

I understand the UseCase's as a bridge between **PRESENTATION ↔ DATA,** it’s an access point that **PRESENTATION layer** uses to communicate with the business-logic.
Pure Kotlin classes should be what we find here, being isolated from framework this layer remainds immutable when we change teconologies during the development (e.g. Start with SharedPreferences and then migrate to a SQL DB like Room or migrating from classic Views to Compose UI).

Here is where I change the current execution to an IO thread through [Coroutines Dispatchers](https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html) by default. All the UseCase’s will go to background when executed before consulting **DATA** layer via repository. This will prevent any accidental long task execution in the MainThread afecting App performance and the UX. 

UseCase's can also help to abstract common logic between features if necessary, e.g. requesting data from 2 different Repositories.

💡 PUBLIC METHODS OF DOMAIN AND DATA LAYERS ARE...

- `suspend`. I consider [Kotlin Corroutines](https://developer.android.com/kotlin/coroutines) sufficiently integrated into our day to day to use them anywhere in the App.
- Returning `Either` classes (from [Arrow](https://github.com/arrow-kt/arrow)). `Either` class has 2 values, in this case a `Failure` object representing an specific error and the expected result. Helps a lot to test happy and unhappy paths! 
The idea behind that it is wrapping possible `Throwables` into something more explicit in a functional way, so we can treat each error differently later in the **UI.** 
A more functional and exception-free solution to return stuff 🙃.

## PRESENTATION Layer.

The Views are built with [Jetpack Compose UI](https://developer.android.com/jetpack/compose). 

The Presentation Pattern would be a Model View ViewModel (MVVM) but since Compose UI is a new paradigm I’m not sure this is the most accurate definition. I prefer to call it **Unidirectional data flow (UDF)** design pattern to be more precise (in ViewModel section I explain this in more detail).

This layer is made of the following classes:

### ViewModel.

Extending from [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) of Android Architecure Components (Jetpack). Fits pretty well with the presentation pattern I’m using since it is Lifecycle-aware and survives configuration changes, has a build-in corou