/*
 * MIT License
 *
 * Copyright (c) 2019 Crafter Software Corporation. All Rights Reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.craftercms.sites.ecommerce.service.commercetools

import io.sphere.sdk.client.BlockingSphereClient
import io.sphere.sdk.client.SphereClient
import io.sphere.sdk.client.SphereClientConfigBuilder
import io.sphere.sdk.client.SphereClientFactory
import org.springframework.beans.factory.FactoryBean

import java.util.concurrent.TimeUnit

@Grapes([
   @Grab('com.commercetools.sdk.jvm.core:commercetools-models:1.44.0'),
   @Grab('com.commercetools.sdk.jvm.core:commercetools-java-client:1.44.0')
])
class ClientFactory implements FactoryBean<SphereClient> {

  String projectKey
  String clientId
  String clientSecret
  String authUrl
  String apiUrl
  List<String> scopes

  def config

  def init() {
    config = SphereClientConfigBuilder
      .ofKeyIdSecret(projectKey, clientId, clientSecret)
      .authUrl(authUrl)
      .apiUrl(apiUrl)
      .scopeStrings(scopes)
      .build()
      println("Init , scopes---->" + scopes)

     String result = scopes.join(",")
     println("SCOPES "+result)

  }

  SphereClient getObject() {
    def asyncClient = SphereClientFactory.of().createClient(config)
    return BlockingSphereClient.of(asyncClient, 10, TimeUnit.SECONDS)
  }

  Class<?> getObjectType() {
    return SphereClient.class
  }

  boolean isSingleton() {
    return true
  }

}
