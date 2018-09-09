package io.github.amanshuraikwar.splash.di

import javax.inject.Qualifier

interface Qualifiers {

    @Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
    @Qualifier
    @Retention annotation class NetworkExecutor

    @Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
    @Qualifier
    @Retention annotation class CuratedPhotosListing
}