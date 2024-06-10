package com.example.newMock.Controller;

import com.example.newMock.Model.RequestDTO;
import com.example.newMock.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Random;

@RestController
public class MainController {

    private Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO) {
        try {
            String clientId = requestDTO.getClientId();
            char firstDigit = clientId.charAt(0);
            BigDecimal maxlimit;
            String currency;
            String RqUID = requestDTO.getRqUID();
            Random random = new Random();
            double balance;

            if (firstDigit == '8') {
                maxlimit = new BigDecimal(2000);
                currency ="US";
                balance = random.nextDouble() * maxlimit.intValue();
            } else if (firstDigit == '9') {
                maxlimit = new BigDecimal(1000);
                currency ="EU";
                balance = random.nextDouble() * maxlimit.intValue();
            } else {
                maxlimit = new BigDecimal(10000);
                currency ="RUB";
                balance = random.nextDouble() * maxlimit.intValue();
            }

            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setRqUID(RqUID);
            responseDTO.setClientId(requestDTO.getClientId());
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(Double.valueOf(String.format("%.2f", balance)));
            responseDTO.setMaxLimit(maxlimit);

            log.error("******* RequestDTO *******" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("***** Responce *****" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return responseDTO;

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}