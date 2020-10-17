package com.example.foodapp.repositorycategory

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody

class CategoryRepositoryImpl(
    private val okHttpClient: OkHttpClient,
    private val categoryListDataModelMapper: (String) -> List<CategoryListDataModel>
) : CategoryRepository {

    override fun getCategoryList(): Single<List<CategoryListDataModel>> {
        val url = "https://www.themealdb.com/api/json/v1/1/list.php?c=list"
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
            .map { jsonData -> categoryListDataModelMapper(jsonData) }
    }
}




