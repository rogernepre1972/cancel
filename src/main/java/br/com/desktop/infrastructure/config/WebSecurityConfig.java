package br.com.desktop.infrastructure.config;

//@EnableWebFluxSecurity
//@EnableReactiveMethodSecurity
//public class WebSecurityConfig {
//    private static final String ACTUATOR_ENDPOINT_PATTERN = "/actuator/*";
//    private static final String ROLE_ADMIN = "ADMIN";
//
//    @Value("${app.security.customer-cancellation.setup.basic_auth.username}")
//    private String setupBasicAuthUsername;
//    @Value("${app.security.customer-cancellation.setup.basic_auth.password}")
//    private String setupBasicAuthPassword;
//
//    @Bean
//    public MapReactiveUserDetailsService userDetailsService() {
//        UserDetails paymentSetupeUserDetails = User.builder()
//                .username(setupBasicAuthUsername)
//                .password(setupBasicAuthPassword)
//                .roles(ROLE_ADMIN)
//                .build();
//        return new MapReactiveUserDetailsService(paymentSetupeUserDetails);
//    }
//
//    @Bean
//    public SecurityWebFilterChain configure(ServerHttpSecurity http) {
//        http
//                .csrf()
//                .disable()
//                .headers()
//                .frameOptions().disable()
//                .cache().disable()
//                .and()
//                .authorizeExchange()
//
//                .and()
//                .authorizeExchange()
//                .pathMatchers(ACTUATOR_ENDPOINT_PATTERN).permitAll()
////                Remove comment to skip authentication
//                .pathMatchers(HttpMethod.POST, "/**").permitAll()
//                .pathMatchers(HttpMethod.PATCH, "/**").permitAll()
//                .pathMatchers(HttpMethod.GET, "/**").permitAll()
//                .pathMatchers(HttpMethod.PUT, "/**").permitAll()
//                .pathMatchers(HttpMethod.DELETE, "/**").permitAll()
//                .pathMatchers(HttpMethod.GET, "/management/health").permitAll()
//                .and()
//                .authorizeExchange()
//                .pathMatchers(HttpMethod.POST, "/setup/**")
//                .hasRole(ROLE_ADMIN)
//                .and().httpBasic();
////                .anyExchange().authenticated()
////                .and()
////                .oauth2ResourceServer()
////                .jwt();
//        return http.build();
//    }

//}
