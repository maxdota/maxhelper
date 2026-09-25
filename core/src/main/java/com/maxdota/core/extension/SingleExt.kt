package com.maxdota.core.extension

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.addSchedulers(): Single<T> =
  this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.addSchedulersSync(): Single<T> =
  this.subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())