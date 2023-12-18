package br.com.desktop.infrastructure.web.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestControllerUrlBase.BASE_URL)
public class RestControllerUrlBase {

        public static final String BASE_URL = "/customer_cancellation";

}
