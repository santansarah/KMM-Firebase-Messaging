package com.santansarah.kmmfirebasemessaging.domain.models

import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

/**
 * All of the data elements for an Onboarding screen.
 * This data comes from app resources.
 */
data class OnboardingScreen(
    val currentScreen: Int,
    val headingIcon: ImageResource,
    val headingText: StringResource,
)

