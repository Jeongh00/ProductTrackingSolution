package com.skplanet.shopadvisor.campaign

import com.skplanet.shopadvisor.member.ShopAdvisorMemberAutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@AutoConfiguration(after = [ShopAdvisorMemberAutoConfiguration::class])
@EntityScan("com.skplanet.shopadvisor.campaign")
@ComponentScan("com.skplanet.shopadvisor.campaign")
@EnableJpaRepositories("com.skplanet.shopadvisor.campaign")
class CampaignAutoConfiguration
