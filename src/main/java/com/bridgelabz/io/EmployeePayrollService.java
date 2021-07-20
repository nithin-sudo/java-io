package com.bridgelabz.io;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService
{
    public enum IOService {CONSOLE_IO, FILE_IO, DB_IO, REST_IO}
    private List<EmployeePayrollData> employeePayrollList;
    public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList)
    {
        this.employeePayrollList = employeePayrollList;
    }
    public static void main(String[] args) {
        ArrayList<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
        Scanner consoleInputReader = new Scanner(System.in);
        employeePayrollService.readEmployeePayrollData(consoleInputReader);
        employeePayrollService.writeEmployeePayrollData(IOService.FILE_IO);
    }
    public void readEmployeePayrollData(IOService consoleInputReader)
    {
        System.out.println("Enter Employee ID: ");
        int id = consoleInputReader.nextInt();
        System.out.println("Enter Employee Name: ");
        String name = consoleInputReader.next();
        System.out.println("Enter Employee salary: ");
        double salary = consoleInputReader.nextDouble();
        employeePayrollList.add(new EmployeePayrollData(id,name,salary));
    }
    public void writeEmployeePayrollData(IOService ioService)
    {
        if (ioService.equals(IOService.CONSOLE_IO))
        {
            System.out.println("\nWriting Employee Payroll To Console:\n" + employeePayrollList);
        }
        else if (ioService.equals(IOService.FILE_IO))
        {
            new EmployeePayrollFileService().writeData(employeePayrollList);
        }
    }
    public void printData(IOService ioService)
    {
        if (ioService.equals(IOService.FILE_IO))
        {
            new EmployeePayrollFileService().printData();
        }
    }
    public long countEntries(IOService ioService)
    {
        if (ioService.equals(IOService.FILE_IO))
        {
            return new EmployeePayrollFileService().countEntries();
        }
        return 0;
    }

}
