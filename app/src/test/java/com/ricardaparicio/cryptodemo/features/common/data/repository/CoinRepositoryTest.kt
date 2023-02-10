
/*
 * Copyright 2022 Ricard Aparicio

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ricardaparicio.cryptodemo.features.common.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.ricardaparicio.cryptodemo.core.Failure
import com.ricardaparicio.cryptodemo.core.LocalError
import com.ricardaparicio.cryptodemo.core.NetworkingError
import com.ricardaparicio.cryptodemo.features.COIN_ID
import com.ricardaparicio.cryptodemo.features.coin
import com.ricardaparicio.cryptodemo.features.coinSummary
import com.ricardaparicio.cryptodemo.features.common.data.datasource.CoinLocalDataSource
import com.ricardaparicio.cryptodemo.features.common.data.datasource.CoinRemoteDataSource
import com.ricardaparicio.cryptodemo.features.common.domain.model.CoinListState
import com.ricardaparicio.cryptodemo.features.fiatCurrency
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CoinRepositoryTest {
    @MockK
    private lateinit var localDataSource: CoinLocalDataSource

    @MockK
    private lateinit var remoteDataSource: CoinRemoteDataSource
    private lateinit var coinRepository: CoinRepository

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        coinRepository = CoinRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun `when fiat currency is requested then return the current one from local DataSource`() =
        runTest {
            coEvery { localDataSource.fiatCurrencyFlow() } returns flowOf(fiatCurrency.right())

            val result = coinRepository.getFiatCurrency()

            coVerify(exactly = 1) { localDataSource.fiatCurrencyFlow() }
            assert(result.isRight())
            assert((result as Either.Right).value == fiatCurrency)
        }
