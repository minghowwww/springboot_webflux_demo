package com.nio.webfluxdemo.Router;

import com.nio.webfluxdemo.handler.CityHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CityRouter {

    @Bean
    public RouterFunction<ServerResponse> routeCity(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
                                                            CityHandler cityHandler){
        return RouterFunctions.route(RequestPredicates.GET("/hello")
//                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN))
                ,cityHandler :: helloCity)
                .andRoute(RequestPredicates.GET("/time"), cityHandler::wsTest);
    }
}
