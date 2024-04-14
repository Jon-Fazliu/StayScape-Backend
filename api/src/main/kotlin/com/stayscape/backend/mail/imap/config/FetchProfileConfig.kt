
package com.stayscape.backend.mail.imap.config

import jakarta.mail.FetchProfile
import jakarta.mail.UIDFolder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FetchProfileConfig {

    @Bean
    fun fetchProfile(): FetchProfile {
        val fetchProfile = FetchProfile()
        fetchProfile.add(FetchProfile.Item.ENVELOPE)
        fetchProfile.add(FetchProfile.Item.FLAGS)
        fetchProfile.add(UIDFolder.FetchProfileItem.UID)
        return fetchProfile
    }

}