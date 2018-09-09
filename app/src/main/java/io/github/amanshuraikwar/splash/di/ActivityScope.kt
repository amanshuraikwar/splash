package io.github.amanshuraikwar.splash.di

import javax.inject.Scope

/**
 * In Dagger, an unscoped component cannot depend on a scoped component. As
 * [AppComponent] is a scoped component (`@Singleton`, we create a custom
 * scope to be used by all fragment components. Additionally, a component with a specific scope
 * cannot have a sub component with the same scope.
 *
 * Created by amanshuraikwar on 07/03/18.
 */
@Scope @Retention annotation class ActivityScope