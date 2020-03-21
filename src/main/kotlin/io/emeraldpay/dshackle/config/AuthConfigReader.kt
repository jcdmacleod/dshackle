/**
 * Copyright (c) 2020 ETCDEV GmbH
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
package io.emeraldpay.dshackle.config

import org.slf4j.LoggerFactory
import org.yaml.snakeyaml.nodes.MappingNode

class AuthConfigReader : YamlConfigReader() {

    companion object {
        private val log = LoggerFactory.getLogger(AuthConfigReader::class.java)
    }

    fun readClientBasicAuth(node: MappingNode?): AuthConfig.ClientBasicAuth? {
        return getMapping(node, "basic-auth")?.let { authNode ->
            val username = getValueAsString(authNode, "username")
            val password = getValueAsString(authNode, "password")
            if (username != null && password != null) {
                AuthConfig.ClientBasicAuth(username, password)
            } else {
                log.warn("Basic auth is not fully configured")
                null
            }
        }
    }

    fun readClientTls(node: MappingNode?): AuthConfig.ClientTlsAuth? {
        return getMapping(node, "tls")?.let { authNode ->
            val auth = AuthConfig.ClientTlsAuth()
            auth.ca = getValueAsString(authNode, "ca")
            auth.certificate = getValueAsString(authNode, "certificate")
            auth.key = getValueAsString(authNode, "key")
            auth
        }
    }

    /**
     * Example config:
     * ```
     * enabled: false
     * server:
     *   certificate: "127.0.0.1.crt"
     *   key: "127.0.0.1.p8.key"
     * client:
     *   require: false
     *   ca: "ca.dshackle.test.crt"
     * ```
     */
    fun readServerTls(node: MappingNode?): AuthConfig.ServerTlsAuth? {
        return getMapping(node, "tls")?.let { node ->
            val auth = AuthConfig.ServerTlsAuth()
            getValueAsBool(node, "enabled")?.let {
                auth.enabled = it
            }
            getMapping(node, "server")?.let { node ->
                auth.certificate = getValueAsString(node, "certificate")
                auth.key = getValueAsString(node, "key")
            }
            getMapping(node, "client")?.let { node ->
                getValueAsBool(node, "require")?.let {
                    auth.clientRequire = it
                }
                auth.clientCa = getValueAsString(node, "ca")
            }
            auth
        }
    }

}