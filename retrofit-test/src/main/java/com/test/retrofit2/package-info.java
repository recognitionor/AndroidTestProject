// Copyright 2014 Square, Inc.

/**
 * Retrofit turns your REST API into a Java interface.
 *
 * <pre>
 * public interface GitHubService {
 *   &#64;GET("/users/{user}/repos")
 *   List&lt;Repo&gt; listRepos(@Path("user") String user);
 * }
 * </pre>
 */
package com.test.retrofit2;
