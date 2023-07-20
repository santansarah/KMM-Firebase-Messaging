package com.santansarah.kmmfirebasemessaging.utils

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.desc

fun mrString(resource: StringResource) =
    resource.desc().localized()