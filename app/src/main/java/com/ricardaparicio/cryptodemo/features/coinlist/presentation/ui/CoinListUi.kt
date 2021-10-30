
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
package com.ricardaparicio.cryptodemo.features.coinlist.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.ricardaparicio.cryptodemo.R
import com.ricardaparicio.cryptodemo.core.util.Block
import com.ricardaparicio.cryptodemo.core.util.TypedBlock
import com.ricardaparicio.cryptodemo.features.common.domain.model.FiatCurrency
import com.ricardaparicio.cryptodemo.features.common.ui.AlertError
import com.ricardaparicio.cryptodemo.features.common.ui.model.CoinSummaryUiModel
import com.ricardaparicio.cryptodemo.features.coinlist.presentation.viewmodel.CoinListViewModel

@Composable
fun CoinListScreen(onClickCoin: TypedBlock<CoinSummaryUiModel>) {
    val viewModel = hiltViewModel<CoinListViewModel>()
    CoinList(
        uiState = viewModel.uiState,
        onClickCoin = onClickCoin,
        onClickCurrency = { currency -> viewModel.onFiatCurrencyClicked(currency) },
        onClickDismissError = { viewModel.onDismissDialogRequested() }
    )
}

@Composable
private fun CoinList(
    uiState: CoinListUiState,
    onClickCoin: TypedBlock<CoinSummaryUiModel>,
    onClickCurrency: TypedBlock<FiatCurrency>,
    onClickDismissError: Block,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        CoinLazyColumn(
            uiState = uiState,
            onClickCoin = onClickCoin
        )