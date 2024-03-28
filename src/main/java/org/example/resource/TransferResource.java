package org.example.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.dto.ResponseDto;
import org.example.dto.TransferRequestDto;
import org.example.service.TransferService;

import java.util.HashMap;

import static org.example.dto.ResponseDto.badRequest;
import static org.example.dto.ResponseDto.ok;
import static org.example.utils.MapperHelper.asInteger;
import static org.example.utils.MapperHelper.asString;

@Path("transfer")
public class TransferResource {

    @Inject
    private ObjectMapper objectMapper;
    @Inject
    private TransferService transferService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String transfer(String transferRequestString) {
        try {
            transferService.transfer(toTransferRequestDto(transferRequestString));
            return toResponseString(ok());
        } catch (Exception e) {
            return toResponseString(badRequest(e.getMessage()));
        }
    }

    private TransferRequestDto toTransferRequestDto(String transferRequestString) throws JsonProcessingException {
        final var properties = objectMapper.readValue(transferRequestString, HashMap.class);
        return new TransferRequestDto(
                asString(properties.get("accountFrom")),
                asString(properties.get("accountTo")),
                asInteger(properties.get("amount"))
        );
    }

    private String toResponseString(ResponseDto responseDto) {
        return String.format("""
                {
                    "status": "%s",
                    "errorMessage":"%s"
                }""", responseDto.getStatus(), responseDto.getErrorMessage());
    }
}
