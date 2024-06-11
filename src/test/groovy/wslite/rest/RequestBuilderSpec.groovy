/* Copyright 2011 the original author or authors.
 *
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
package wslite.rest

import spock.lang.*
import wslite.http.*

class RequestBuilderSpec extends Specification {

    void 'requires a method'() {
        when:
        new RequestBuilder().build(null, 'http://ws.org', null, null)

        then: thrown(IllegalArgumentException)
    }

    void 'requires a url'() {
        when:
        new RequestBuilder().build(HTTPMethod.GET, null, null, null)

        then: thrown(IllegalArgumentException)
    }

    void 'adding path to url'() {
        when:
        def request = new RequestBuilder().build(HTTPMethod.GET, 'http://ws.org/services',
                [path: '/users/123'], null)

        then:
        'http://ws.org/services/users/123' ==  request.url.toString()
    }

    void 'path with slash and url with trailing slash'() {
        when:
        def request = new RequestBuilder().build(HTTPMethod.GET, 'http://ws.org/services/',
                [path: '/users/123'], null)

        then:
        'http://ws.org/services/users/123' ==  request.url.toString()
    }

    void 'path with no beginning slash and url with no trailing slash'() {
        when:
        def request = new RequestBuilder().build(HTTPMethod.GET, 'http://ws.org/services',
                [path: 'users/123'], null)

        then:
        'http://ws.org/services/users/123' ==  request.url.toString()
    }

    void 'map to querystring'() {
        when:
        def request = new RequestBuilder().build(HTTPMethod.GET, 'http://ws.org/services',
                [path: '/users', query: [deptid: '6900', managerid: '123']], null)

        then:
        'http://ws.org/services/users?deptid=6900&managerid=123' == request.url.toString()
    }

    void 'map to querystring with encoded strings'() {
        when:
        def request = new RequestBuilder().build(HTTPMethod.GET, 'http://ws.org/services',
                [path: '/users', query: ['hire_date': '06/19/2009', homepage: 'http://geocities.com/users/jansmith']],
                null)

        then:
        'http://ws.org/services/users?' +
                'hire_date=06%2F19%2F2009&' +
                'homepage=http%3A%2F%2Fgeocities.com%2Fusers%2Fjansmith' == request.url.toString()
    }

    void 'headers added to request'() {
        when:
        def request = new RequestBuilder().build(HTTPMethod.GET, 'http://ws.org/services',
                [headers: [Accept: 'text/plain', 'X-Foo': '123']], null)

        then:
        'text/plain' == request.headers.Accept
        '123' == request.headers.'X-Foo'
    }

    void 'sets http connection parameters'() {
        when:
        HTTPRequest request = new RequestBuilder().build(HTTPMethod.GET, 'http://ws.org/services',
                [path: '/foo', readTimeout: 9876, sslTrustAllCerts: false], null)

        then:
        9876 == request.readTimeout
        !request.sslTrustAllCerts
    }

    void 'original params not modified'() {
        given:
        def params = [path: '/users/123', readTimeout: 9876, sslTrustAllCerts: false]

        when:
        def request = new RequestBuilder().build(HTTPMethod.GET, 'http://ws.org/services', params, null)
        params.remove('path')

        then:
        null == params.path
        'http://ws.org/services/users/123' == request.url.toString()
    }

    void 'accept parameter can be set using enum or string'() {
        expect:
        def response = new RequestBuilder().build(HTTPMethod.GET, 'http://ws.org/services', [accept: accept], null)
        response.headers.Accept == contentType

        where:
        accept                      | contentType
        ContentType.XML            | ContentType.XML.getAcceptHeader()
        'text/plain'                | 'text/plain'
        "${ContentType.JSON}"      | ContentType.JSON.toString()
    }

}
