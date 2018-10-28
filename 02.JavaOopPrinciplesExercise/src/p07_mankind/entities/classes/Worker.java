package p07_mankind.entities.classes;

import p07_mankind.entities.abstract_classes.BaseHuman;
import p07_mankind.entities.exceptions.InvalidNameLengthException;
import p07_mankind.entities.exceptions.InvalidUpperCaseException;
import p07_mankind.entities.exceptions.ValueMismatchException;

public class Worker extends BaseHuman {
    private Double weekSalary;
    private Double workHoursPerDay;

    public Worker(String firstName, String lastName, Double weekSalary, Double workHoursPerDay)
            throws InvalidUpperCaseException, InvalidNameLengthException, ValueMismatchException {
        super(firstName, lastName);
        this.setWeekSalary(weekSalary);
        this.setWorkHoursPerDay(workHoursPerDay);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        /*
        Week Salary: 1590.00
        Hours per day: 10.00
        Salary per hour: 22.71
         */
        sb.append(super.toString()).append(System.lineSeparator());
        sb.append("Week Salary: ").append(String.format("%.2f", this.weekSalary)).append(System.lineSeparator());
        sb.append("Hours per day: ").append(String.format("%.2f", this.workHoursPerDay)).append(System.lineSeparator());
        sb.append("Salary per hour: ").append(String.format("%.2f", this.calculateSalaryPerHour()));

        return sb.toString();
    }

    private Double calculateSalaryPerHour() {
        return this.weekSalary / 7.0 / this.workHoursPerDay;
    }

    private void setWeekSalary(Double weekSalary) throws ValueMismatchException {
        if (weekSalary <= 10) {
            throw new ValueMismatchException("weekSalary");
        } else {
            this.weekSalary = weekSalary;
        }
    }

    private void setWorkHoursPerDay(Double workHoursPerDay) throws ValueMismatchException {
        if (workHoursPerDay < 1 || workHoursPerDay > 12) {
            throw new ValueMismatchException("workHoursPerDay");
        } else {
            this.workHoursPerDay = workHoursPerDay;
        }
    }
}
