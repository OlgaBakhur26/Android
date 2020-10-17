package com.example.foodapp.repositorydishbyid

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody

class DishByIdRepositoryImpl(
    private val okHttpClient: OkHttpClient,
    private val dishByIdDataModelMapper: (String) -> DishByIdDataModel
) : DishByIdRepository {

    override fun getDishById(id: String): Single<DishByIdDataModel> {
        val url = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=$id"
        val request = Request.Builder()
            .url(url)
            .build()

        return Single.create<String> { emitter ->
            okHttpClient.newCall(request).execute()
                .use { response ->
                    if (!response.isSuccessful) {
                        emitter.onError(Throwable(response.code.toString()))
                    }
                    if (response.body == null) {
                        emitter.onError(NullPointerException("Body is null"))
                    }
                    emitter.onSuccess((response.body as ResponseBody).string())
                }
        }
            .subscribeOn(Schedulers.io())
            .map { jsonData -> dishByIdDataModelMapper(jsonData) }
    }
}




