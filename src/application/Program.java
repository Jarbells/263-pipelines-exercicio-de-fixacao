package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Employee;

public class Program {

	public static void main(String[] args) {
	
		Locale.setDefault(Locale.US);
		
		List<Employee> employeeList = new ArrayList<>();
		
		try (Scanner sc = new Scanner(System.in)) {		
			System.out.print("Enter full file path: ");
			String path = sc.next();
			System.out.print("Enter salary: ");
			double salary = sc.nextDouble();
			
			try (BufferedReader br = new BufferedReader(new FileReader(path))) {
				String line = br.readLine();				
				while (line != null) {
					String[] fields = line.split(",");
					employeeList.add(new Employee(fields[0], fields[1], Double.parseDouble(fields[2])));
					line = br.readLine();
				}
				
				// Ordenando os emails de quem ganha acima do salário declarado.
				Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
				List<String> emails = employeeList.stream()
						.filter(emp -> emp.getSalary() > 2000.0) 
						.map(Employee::getEmail)
						.sorted(comp)
						.collect(Collectors.toList());
				System.out.printf("Email of people whose salary is more than %.2f:%n", salary);
				emails.forEach(System.out::println);
				
				// Soma dos salários de quem começa com a letra declarada abaixo.
				char letter = 'M';
				double sum = employeeList.stream()
						.filter(emp -> emp.getName().charAt(0) == letter)
						.mapToDouble(Employee::getSalary)
						.reduce(0.0, Double::sum);
				System.out.printf("Sum of salary of people whose name starts with '%c': %.2f%n", letter, sum);
				
			}
			catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
		catch (InputMismatchException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
