package com.calc.birthday.controllers;

import com.calc.birthday.beans.ErrorResponseBean;
import com.calc.birthday.dto.BirthdayDTO;
import com.calc.birthday.exceptions.FiscalCodeFormatException;
import com.calc.birthday.exceptions.FiscalCodeLengthException;
import com.calc.birthday.exceptions.FiscalCodeWrongMonthException;
import com.calc.birthday.services.BirthdayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;

@RestController
@RequestMapping("/api/calculateBD")
@Slf4j
public class BirthdayController {

    protected BirthdayService birthdayService;

    @Autowired
    public BirthdayController(BirthdayService birthdayService) {
        this.birthdayService = birthdayService;
    }

    @GetMapping(value = "/calculate-birthday/{fiscalCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Calculate birthday", description = "Calculate birthday from fiscal code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Birthday calculates correctly",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BirthdayDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Wrong input",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBean.class))}),
            @ApiResponse(responseCode = "500", description = "General Technical Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBean.class))}),
            @ApiResponse(responseCode = "404", description = "service not found",
                    content = @Content),
    })
    public ResponseEntity<BirthdayDTO> calculateBirthday(@PathVariable(name="fiscalCode") String fiscalCode) {

        BirthdayDTO responseBody = new BirthdayDTO();

        birthdayService.validateFiscalCode(fiscalCode);

        int age = birthdayService.calculateAgeFromCF(fiscalCode);
        String birthday = birthdayService.calculateCompleteBirthday(fiscalCode);

        responseBody.setAge(age);
        responseBody.setBirthdayDate(birthday);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
