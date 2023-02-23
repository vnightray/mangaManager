package com.github.vnightray.acgnmanager.config;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.ConnectionFactory;

@Configuration
public class ArtemisConfig implements ArtemisConfigurationCustomizer {

    private static final String TOPIC_FACTORY = "topicJmsListenerContainerFactory";
    private static final String QUEUE_FACTORY = "queueJmsListenerContainerFactory";

    @Override
    public void customize(org.apache.activemq.artemis.core.config.Configuration configuration) {
        configuration.setMaxDiskUsage(90);
    }

    @Bean(TOPIC_FACTORY)
    public DefaultJmsListenerContainerFactory topicJmsListenerContainerFactory(
            ConnectionFactory factory, DefaultJmsListenerContainerFactoryConfigurer configurer){
        DefaultJmsListenerContainerFactory listenerContainerFactory = new DefaultJmsListenerContainerFactory();
        configurer.configure(listenerContainerFactory, factory);
        listenerContainerFactory.setPubSubDomain(true);
        return listenerContainerFactory;
    }

    @Bean(QUEUE_FACTORY)
    public DefaultJmsListenerContainerFactory queueJmsListenerContainerFactory(
            ConnectionFactory factory, DefaultJmsListenerContainerFactoryConfigurer configurer
    ){
        DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
        configurer.configure(containerFactory, factory);
        containerFactory.setPubSubDomain(false);
        return containerFactory;
    }

}
