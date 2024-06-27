package com.tlecoders.photoapp.api_gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalFilterConfiguration {
     private Logger log= LoggerFactory.getLogger(GlobalFilterConfiguration.class);

     @Bean
     @Order(1)
    public GlobalFilter secondFilter()
     {
         return (exchange,chain)->{
             log.info("second pre filter being executed");
             return chain.filter(exchange).then(Mono.fromRunnable(()->{
                 log.info("second post filter executed");
             }));
         };
     }
    @Bean
    @Order(2)
    public GlobalFilter thirdFilter()
    {
        return (exchange,chain)->{
            log.info("third pre filter being executed");
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                log.info("third post filter executed");
            }));
        };
    }
    @Bean
    @Order(3)
    public GlobalFilter fourthFilter()
    {
        return (exchange,chain)->{
            log.info("fourth pre filter being executed");
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                log.info("fourth post filter executed");
            }));
        };
    }
}
