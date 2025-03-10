/**
 * Copyright (c) 2020 EmeraldPay, Inc
 * Copyright (c) 2019 ETCDEV GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.emeraldpay.dshackle.upstream

import io.emeraldpay.dshackle.upstream.calls.CallMethods
import io.emeraldpay.grpc.Chain
import reactor.core.publisher.Flux
import reactor.util.function.Tuple2

/**
 * Holds Multistreams configured for a chain.
 */
interface MultistreamHolder {
    fun getUpstream(chain: Chain): Multistream?
    fun getAvailable(): List<Chain>
    fun observeChains(): Flux<Chain>
    fun observeAddedUpstreams(): Flux<Tuple2<Chain, Upstream>>
    fun getDefaultMethods(chain: Chain): CallMethods
    fun isAvailable(chain: Chain): Boolean
}
