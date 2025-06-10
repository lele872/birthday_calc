package com.calc.birthday.services;

import com.calc.birthday.consts.BirthdayConstan;
import com.calc.birthday.exceptions.FiscalCodeFormatException;
import com.calc.birthday.exceptions.FiscalCodeLengthException;
import com.calc.birthday.exceptions.FiscalCodeWrongMonthException;
import org.springframework.stereotype.Service;

@Service
public class BirthdayService {

    /**
     * this method validate only length and format(letter or number) of italian fiscal code
     * not check the fiscal code if ti is empty or null because is mandatory for the request of API
     */
    public void validateFiscalCode(String fiscalCode) {

        String descError = "";

        if (fiscalCode.length() != 16) {
            descError = BirthdayConstan.WRONG_LENGTH + fiscalCode.length();
            throw new FiscalCodeLengthException(descError);
        }

        for (int i = 0; i < 6; i++) {
            if (String.valueOf(fiscalCode.charAt(i)).matches("^[0-9]")) {
                descError = BirthdayConstan.WRONG_FORMAT + "the first six index must be the characters";
                throw new FiscalCodeFormatException(descError);
            }
        }

        for (int i = 6; i < 8; i++) {
            if (!String.valueOf(fiscalCode.charAt(i)).matches("^[0-9]")) {
                descError = BirthdayConstan.WRONG_FORMAT + "the seven and eighth index must be numbers";
                throw new FiscalCodeFormatException(descError);
            }
        }

        if (String.valueOf(fiscalCode.charAt(8)).matches("^[0-9]")) {
            descError = BirthdayConstan.WRONG_FORMAT + "the ninth index must be character";
            throw new FiscalCodeFormatException(descError);
        }

        for (int i = 9; i < 11; i++) {
            if (!String.valueOf(fiscalCode.charAt(i)).matches("^[0-9]")) {
                descError = BirthdayConstan.WRONG_FORMAT + "the ten and eleventh must be numbers";
                throw new FiscalCodeFormatException(descError);
            }
        }

        if (String.valueOf(fiscalCode.charAt(11)).matches("^[0-9]")) {
            descError = BirthdayConstan.WRONG_FORMAT + "the twelfth index must be character";
            throw new FiscalCodeFormatException(descError);
        }

        for (int i = 12; i < 15; i++) {
            if (!String.valueOf(fiscalCode.charAt(i)).matches("^[0-9]")) {
                descError = BirthdayConstan.WRONG_FORMAT + "the thirteenth and fourteen must be numbers";
                throw new FiscalCodeFormatException(descError);
            }
        }

        if (String.valueOf(fiscalCode.charAt(15)).matches("^[0-9]")) {
            descError = BirthdayConstan.WRONG_FORMAT + "the last one index must be character";
            throw new FiscalCodeFormatException(descError);
        }
    }


    public int calculateAgeFromCF(String fiscalCode) {

        int finalAge = 0;

        int age = calculateYearBirthday(fiscalCode);

        int currentYear = BirthdayConstan.CURRENT_YEAR;

        finalAge = currentYear - age;

        return finalAge;
    }

    public String calculateMonthBirthday(String fiscalCode) {

        String counterMonthToString = "";

        String month = String.valueOf(fiscalCode.charAt(8));

        String[] valueOfMonths = BirthdayConstan.MONTHS;

        int counterMonths = 1;
        boolean isMatched = false;

        for (String monthValue : valueOfMonths) {
            if (monthValue.equals(month)) {
                isMatched = true;
                break;
            }
            counterMonths++;
        }

        if (isMatched) {
            counterMonthToString = Integer.toString(counterMonths);

            if (counterMonthToString.length() != 2) {
                counterMonthToString = "0" + counterMonthToString;
            }

        } else {
            throw new FiscalCodeWrongMonthException(BirthdayConstan.WRONG_MONTH + "cannot not be " + month);
        }

        return counterMonthToString;
    }

    public int calculateYearBirthday(String fiscalCode) {

        String yearString = "" + fiscalCode.charAt(6) + fiscalCode.charAt(7);

        String currentYearTostring = Integer.toString(BirthdayConstan.CURRENT_YEAR);
        currentYearTostring = currentYearTostring.substring(currentYearTostring.length() - 2);

        //is limited can not calculate year >= 100
        if (Integer.parseInt(yearString) > Integer.parseInt(currentYearTostring)) {
            yearString = "19" + yearString;
        } else {
            yearString = "20" + yearString;
        }

        return Integer.parseInt(yearString);
    }

    public String calculateDayBirthday(String fiscalCode) {

        return "" + (fiscalCode.charAt(9)) + (fiscalCode.charAt(10));
    }

    public String calculateCompleteBirthday(String fiscalCode) {

        String dayBirthday = calculateDayBirthday(fiscalCode);
        String monthBirthday = calculateMonthBirthday(fiscalCode);
        int yearBirthday = calculateYearBirthday(fiscalCode);

        return dayBirthday + "/" + monthBirthday + "/" + yearBirthday;
    }
}
