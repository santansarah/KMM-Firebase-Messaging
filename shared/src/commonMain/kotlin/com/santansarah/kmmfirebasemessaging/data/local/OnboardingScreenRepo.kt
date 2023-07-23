package com.santansarah.kmmfirebasemessaging.data.local

import com.santansarah.kmmfirebasemessaging.SharedRes
import com.santansarah.kmmfirebasemessaging.domain.models.OnboardingScreen

object OnboardingScreenRepo {
    val screens = listOf(

        OnboardingScreen(
            1,
            SharedRes.images.logo,
            SharedRes.strings.onboarding_one_text
        )
    )
}
