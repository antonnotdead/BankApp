package com.clevertecbank.interestcalc;

import com.clevertecbank.jdbc.DBconnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class InterestCalculationThread implements Runnable {
    private int MonthlyInterest = 1;
    private boolean isAccrued = false;
    private final DBconnector DBCONNECTOR = DBconnector.getInstance();
    public void run() {
        while(true) {
            if (checkIsLastDayOfMonth()) {
                if (!this.isAccrued) {
                    accureMonthlyInterest();
                }
            } else if (this.checkIsFirstDayOfMonth()) {
                this.isAccrued = false;
            }
            try {
                System.out.println("check!");
                Thread.sleep(30000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }

    private boolean checkIsLastDayOfMonth() {
        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return dayOfMonth == maxDayOfMonth;
    }

    private boolean checkIsFirstDayOfMonth() {
        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int minDayOfMonth = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        return dayOfMonth == minDayOfMonth;
    }

    private void accureMonthlyInterest() {
        try(Connection connection = this.DBCONNECTOR.getConnection()) {
                String query = "UPDATE bankapp.account_data SET money_value = monay_value * ?";
                try(PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setDouble(1,(1 + this.MonthlyInterest / 100));
                    this.isAccrued = true;
                    statement.executeUpdate();
                }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
