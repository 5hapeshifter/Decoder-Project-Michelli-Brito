package com.ead.authuser.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ResponsePageDto<T> extends PageImpl {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES) // Anotacao para informar o Jackson que utilize esse dto na desserializacao
    public ResponsePageDto(@JsonProperty("content") List<T> content, // nomes dos parametros que virao na resposta
                           @JsonProperty("number") int number,
                           @JsonProperty("size") int size,
                           @JsonProperty("totalElements") Long totalElements,
                           @JsonProperty("pageable") JsonNode pageable,
                           @JsonProperty("last") boolean last,
                           @JsonProperty("totalPages") int totalPages,
                           @JsonProperty("sort") JsonNode sort,
                           @JsonProperty("first") boolean first,
                           @JsonProperty("empty") boolean empty
    ) {
        super(content, PageRequest.of(number,size), totalElements);
    }

    public ResponsePageDto(List content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public ResponsePageDto(List content) {
        super(content);
    }
}
