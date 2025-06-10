package com.calc.birthday.consts;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@NoArgsConstructor
public class BirthdayConstan {

    public static final int CURRENT_YEAR = LocalDateTime.now().getYear();

    public static final String WRONG_LENGTH = "Length is wrong. Must be 16 actual is: ";

    public static final String WRONG_FORMAT = "Format is not valid";

    public static final String WRONG_MONTH = "Month in fiscal code is not valid";

    public static final String[] MONTHS = {"A","B","C","D","E","H","L","M","P","R","S","T"};

    public static final String FORMAT_DATE = "dd/MM/yyyy";

}
