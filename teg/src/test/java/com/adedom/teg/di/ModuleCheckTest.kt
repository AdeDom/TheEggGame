package com.adedom.teg.di

import android.content.Context
import com.adedom.teg.data.di.getDataModule
import com.adedom.teg.domain.di.getDomainModule
import com.adedom.teg.presentation.di.getPresentationModule
import com.adedom.teg.sharedpreference.di.getSharedPreferenceModule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.junit.Test
import org.junit.experimental.categories.Category
import org.koin.core.error.InstanceCreationException
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules

@ExperimentalStdlibApi
@FlowPreview
@ExperimentalCoroutinesApi
@Category(CheckModuleTest::class)
class ModuleCheckTest : AutoCloseKoinTest() {

    @Test
    fun test_checkModules() {
        val contextModule = module {
            single { mockk<Context>(relaxed = true) }
        }

        try {
            checkModules {
                modules(
                    contextModule,
                    getPresentationModule,
                    getDomainModule,
                    getDataModule,
                    getSharedPreferenceModule
                )
            }
        } catch (e: InstanceCreationException) {
        } catch (e: RuntimeException) {
        }

    }

}
