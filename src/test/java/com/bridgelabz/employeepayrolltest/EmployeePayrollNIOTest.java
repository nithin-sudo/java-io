package com.bridgelabz.employeepayrolltest;
import com.bridgelabz.io.EmployeePayrollData;
import com.bridgelabz.io.EmployeePayrollService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
public class  EmployeePayrollNIOTest
{
        @Test
        public void given3EmployeesWhenWrittenToFileShouldMatchEmployeeEntries()
        {
                    EmployeePayrollData[] arrayOfEmps = {
                    new EmployeePayrollData(1, "nithin", 100055000.0),
                    new EmployeePayrollData(2, "ambani", 200005500.0),
                    new EmployeePayrollData(3, "adani", 300000550.0)
            };
            EmployeePayrollService employeePayrollService;
            employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
            employeePayrollService.writeEmployeePayrollData(EmployeePayrollService.IOService.FILE_IO);
            employeePayrollService.printData(EmployeePayrollService.IOService.FILE_IO);
            long entries = employeePayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO);
            Assertions.assertEquals(3,entries);
        }
    @Test
    public void givenFileOnReadingFromFileShouldMatchEmployeeCount()
    {
        EmployeePayrollData[] arrayOfEmps = {
                new EmployeePayrollData(1, "Nithin", 1000000.0),
                new EmployeePayrollData(2, "billgates", 2000000.0),
                new EmployeePayrollData(3, "elon", 3000000.0)
        };
        EmployeePayrollService employeePayrollService;
        employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
        //EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        long entries = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.FILE_IO);
        Assertions.assertEquals(3, entries);
    }
}
