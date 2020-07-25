package com.raka.newslisttest.di

import com.raka.newslisttest.data.api.ApiService
import com.raka.newslisttest.data.repository.ArticleRepositoryImpl
import com.raka.newslisttest.data.repository.CategoryRepositoryImpl
import com.raka.newslisttest.data.repository.NewsSourceRepositoryImpl
import com.raka.newslisttest.domain.*
import com.raka.newslisttest.domain.usecase.ArticleUsescase
import com.raka.newslisttest.domain.usecase.CategoryUsecase
import com.raka.newslisttest.domain.usecase.NewsSourceUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class ActivityModules {
    @Provides
    @ActivityScoped
    fun provideCategoryUsecase(categoryRepository: CategoryRepository): CategoryUsecase =
        CategoryUsecase(categoryRepository)
    @Provides
    @ActivityScoped
    fun provideCategoryRepository():CategoryRepository=CategoryRepositoryImpl()
    @Provides
    @ActivityScoped
    fun provideNewsSourceRepository(apiService: ApiService):NewsSourceRepository=NewsSourceRepositoryImpl(apiService)
    @Provides
    @ActivityScoped
    fun provideNewsSourceUsecase(repository: NewsSourceRepository): NewsSourceUsecase =
        NewsSourceUsecase(repository)
    @Provides
    @ActivityScoped
    fun provideArticleRepository(apiService: ApiService):ArticleRepository=ArticleRepositoryImpl(apiService)
    @Provides
    @ActivityScoped
    fun provideArticleUsecase(repository: ArticleRepository): ArticleUsescase =
        ArticleUsescase(repository)
}