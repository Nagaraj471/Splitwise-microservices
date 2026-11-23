
package EXPENSE_SERVICE.expense_service.config;

import EXPENSE_SERVICE.expense_service.dtos.ErrorResponse;
import EXPENSE_SERVICE.expense_service.exceptions.GroupNotFoundException;
import EXPENSE_SERVICE.expense_service.exceptions.GroupServiceUnavailableException;
import EXPENSE_SERVICE.expense_service.exceptions.MemberNotFoundException;

import feign.Response;
import feign.codec.ErrorDecoder;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();
    private final ObjectMapper objectMapper ;

    public CustomFeignErrorDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }    @Override
    public Exception decode(String methodKey, Response response) {

        int status = response.status();

        if (status == 503) {
            return new GroupServiceUnavailableException(
                    "Group service is unavailable"
            );
        }

        if (status != 404 || response.body() == null) {
            return defaultDecoder.decode(methodKey, response);
        }

        byte[] bodyData;

        try {
            bodyData = response.body().asInputStream().readAllBytes();

            ErrorResponse error = objectMapper.readValue(
                    bodyData, ErrorResponse.class
            );

            if ("GROUP_NOT_FOUND".equals(error.code())) {
                return new GroupNotFoundException("Group not found");
            }

            if ("MEMBER_NOT_FOUND".equals(error.code())) {
                return new MemberNotFoundException("Member not found");
            }

        } catch (IOException | RuntimeException e) {
            return new RuntimeException("Unable to decode Group Service error response", e);
        }

        Response rebuiltResponse = response.toBuilder()
                .body(bodyData)
                .build();

        return defaultDecoder.decode(methodKey, rebuiltResponse);
    }
}
