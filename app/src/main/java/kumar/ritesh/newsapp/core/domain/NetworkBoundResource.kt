package kumar.ritesh.newsapp.core.domain

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.*
import kumar.ritesh.newsapp.core.ui.ViewState
import retrofit2.Response

abstract class NetworkBoundResource<ResultType, RequestType> {

    fun asFlow() = flow {
        emit(ViewState.loading())

        val dbValue = loadFromDb().first()
        if (shouldFetch(dbValue)) {
            emit(ViewState.success(dbValue))
            try {
                val apiResponse = fetchFromNetwork()
                when {
                    apiResponse.isSuccessful && apiResponse.body() != null -> {
                        apiResponse.body()?.let { saveNetworkResult(it) }
                        emitAll(loadFromDb().map { ViewState.success(it) })
                    }
                    else -> {
                        emit(ViewState.error(apiResponse.message()))
                    }
                }
            } catch (e: Exception) {
                emit(ViewState.error("Unable to fetch from network"))
            }
        } else {
            emitAll(loadFromDb().map { ViewState.success(it) })
        }
    }

    @WorkerThread
    protected open fun processResponse(response: Response<RequestType>) = response

    @WorkerThread
    protected abstract suspend fun saveNetworkResult(response: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): Flow<ResultType>

    @MainThread
    protected abstract suspend fun fetchFromNetwork(): Response<RequestType>
}