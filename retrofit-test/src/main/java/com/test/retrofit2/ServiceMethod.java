/*
 * Copyright (C) 2015 Square, Inc.
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
package com.test.retrofit2;

import static com.test.retrofit2.Utils.methodError;

import androidx.annotation.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.test.retrofit2.RequestFactory;
import com.test.retrofit2.Retrofit;
import com.test.retrofit2.Utils;

import com.test.retrofit2.HttpServiceMethod;

abstract class ServiceMethod<T> {
  static <T> ServiceMethod<T> parseAnnotations(Retrofit retrofit, Method method) {
    RequestFactory requestFactory = RequestFactory.parseAnnotations(retrofit, method);

    Type returnType = method.getGenericReturnType();
    if (Utils.hasUnresolvableType(returnType)) {
      throw methodError(
          method,
          "Method return type must not include a type variable or wildcard: %s",
          returnType);
    }
    if (returnType == void.class) {
      throw methodError(method, "Service methods cannot return void.");
    }

    return HttpServiceMethod.parseAnnotations(retrofit, method, requestFactory);
  }

  abstract @Nullable T invoke(Object[] args);
}
