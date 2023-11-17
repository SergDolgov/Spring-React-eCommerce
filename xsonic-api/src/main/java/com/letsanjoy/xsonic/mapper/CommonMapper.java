package com.letsanjoy.xsonic.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.letsanjoy.xsonic.dto.HeaderResponse;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommonMapper {

    private final ModelMapper modelMapper;

    <T, S> S convertToEntity(T data, Class<S> type) {
        return modelMapper.map(data, type);
    }

    <T, S> S convertToResponse(T data, Class<S> type) {
        return modelMapper.map(data, type);
    }

    <T, S> List<S> convertToResponseList(List<T> lists, Class<S> type) {
        return lists.stream()
                .map(list -> convertToResponse(list, type))
                .collect(Collectors.toList());
    }

    <T, S> HeaderResponse<S> getHeaderResponse(List<T> orders, Integer totalPages, Long totalElements, Class<S> type) {
        List<S> orderResponses = convertToResponseList(orders, type);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("page-total-count", String.valueOf(totalPages));
        responseHeaders.add("page-total-elements", String.valueOf(totalElements));
        return new HeaderResponse<S>(orderResponses, responseHeaders);
    }
}
