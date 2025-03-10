/**
 * Copyright (c) 2021 EmeraldPay, Inc
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
package io.emeraldpay.dshackle.upstream.rpcclient

import io.emeraldpay.dshackle.reader.JsonRpcReader
import io.emeraldpay.dshackle.upstream.ethereum.WsConnectionImpl
import io.emeraldpay.etherjar.rpc.RpcResponseError
import reactor.core.publisher.Mono

class JsonRpcWsClient(
    private val ws: WsConnectionImpl
) : JsonRpcReader {

    override fun read(key: JsonRpcRequest): Mono<JsonRpcResponse> {
        if (!ws.isConnected) {
            return Mono.error(
                JsonRpcException(
                    JsonRpcResponse.NumberId(key.id),
                    JsonRpcError(
                        RpcResponseError.CODE_UPSTREAM_CONNECTION_ERROR,
                        "WebSocket is not connected"
                    )
                )
            )
        }
        return ws.callRpc(key)
    }
}
