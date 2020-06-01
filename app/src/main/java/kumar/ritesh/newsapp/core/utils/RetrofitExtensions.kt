package kumar.ritesh.newsapp.core.utils

import retrofit2.Retrofit

inline fun <reified T> Retrofit.create(): T = create(T::class.java)