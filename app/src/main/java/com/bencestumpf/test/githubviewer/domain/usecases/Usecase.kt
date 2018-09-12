package com.bencestumpf.test.githubviewer.domain.usecases

import io.reactivex.Single

interface Usecase<ReturnType> {

    fun getSubscribable(): Single<ReturnType>

}