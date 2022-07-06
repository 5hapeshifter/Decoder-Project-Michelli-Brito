package com.ead.authuser.clients;

import com.ead.authuser.dtos.CourseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class UserClient {

    // Aqui sera implementada a requisicao para o CourseMicroservice
    @Autowired
    RestTemplate restTemplate;

    private String REQUEST_URI = "http://localhost:8082";

    public Page<CourseDto> getAllCoursesByUser(UUID userId, Pageable pageable) {
        List<CourseDto> searchResult = null;
        // url do outro microservice
        String url = REQUEST_URI + "/courses?userid=" + userId + "$page=" + pageable.getPageNumber() + "&size"
                + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
        log.debug("Request URL: {} ", url);
        log.info("Request URL: {} ", url);
        try {
            log.debug("Response Number of Elements: {}", searchResult.size());
        } catch (HttpStatusCodeException e) {
            log.error("Error request /courses {} ", e);
        }
        log.info("Ending request /courses userId {} ", userId);
        return ;
    }

}
