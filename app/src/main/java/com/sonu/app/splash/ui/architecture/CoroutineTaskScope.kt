package com.sonu.app.splash.ui.architecture

import java.util.concurrent.Callable
import java.util.function.Consumer
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Java-callable coroutine boundary for the remaining legacy presenters.
 * New Kotlin ViewModels should use lifecycleScope/viewModelScope directly.
 */
class CoroutineTaskScope {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    @JvmOverloads
    fun <T> launch(
        task: Callable<T>,
        onSuccess: Consumer<T> = Consumer {},
        onError: Consumer<Throwable> = Consumer {},
    ): Job = scope.launch {
        try {
            val result = withContext(Dispatchers.IO) { task.call() }
            onSuccess.accept(result)
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (throwable: Throwable) {
            onError.accept(throwable)
        }
    }

    fun cancel(job: Job?) {
        job?.cancel()
    }

    fun cancelAll() {
        scope.coroutineContext[Job]?.cancel()
    }
}
