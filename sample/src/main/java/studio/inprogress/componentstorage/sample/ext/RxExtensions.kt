package studio.inprogress.componentstorage.sample.ext

import io.reactivex.Completable
import io.reactivex.CompletableTransformer
import io.reactivex.Maybe
import io.reactivex.MaybeTransformer
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RxComposer {
    companion object {
        fun <T> applySingleSchedulers(): SingleTransformer<T, T> = SingleTransformer { upstream ->
            upstream.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun <T> applyObservableSchedulers(): ObservableTransformer<T, T> = ObservableTransformer { upstream ->
            upstream.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun <T> applyMaybeSchedulers(): MaybeTransformer<T, T> = MaybeTransformer { upstream ->
            upstream.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun applyCompletableSchedulers(): CompletableTransformer = CompletableTransformer { upstream ->
            upstream.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }
    }
}

fun Disposable?.safeDispose() {
    this?.let {
        if (!isDisposed) {
            dispose()
        }
    }
}

fun <T> Observable<T>.applyObservableSchedulers() = compose { upstream ->
    upstream.observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}

fun <T> Maybe<T>.applyMaybeSchedulers() = compose { upstream ->
    upstream.observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}

fun Completable.applyCompletableSchedulers() = compose { upstream ->
    upstream.observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}

fun <T> Single<T>.applySingleSchedulers(): Single<T> = compose { upstream ->
    upstream.observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}