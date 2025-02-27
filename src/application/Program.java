package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		List<Product> products = new ArrayList<>();
		
		System.out.println("Enter a path: ");
		String sourceFileStr = sc.nextLine();
		
		File file = new File(sourceFileStr);
		String sourceFolderStr = file.getParent();
		
		boolean success = new File(sourceFolderStr + "/out").mkdir();
		
		String targetFileStr = sourceFolderStr + "/out/summary.csv";
		
		try (BufferedReader br = new BufferedReader(new FileReader (sourceFileStr))){
			
			String itemCsv = br.readLine();
			
			while (itemCsv != null) {
				String [] splitCsv = itemCsv.split(",");
				String name = splitCsv[0];
				double price = Double.valueOf(splitCsv[1]) ;
				int quantity = Integer.parseInt(splitCsv[2]);
				products.add(new Product(name, price, quantity));
				
				itemCsv = br.readLine();
			}
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))) {
				
				for (Product item: products) {
					bw.write(item.getName() + "," + String.format("%.2f", item.total()));
					bw.newLine();
				}
				
				System.out.println(targetFileStr + " CREATED!");
				
			} 
			catch(IOException e) {
				System.out.println("Error writing file: " + e.getMessage());
			}
			
		}
		catch(IOException e) {
			System.out.println("Error writing file: " + e.getMessage());
		}
		
		
		
		
		sc.close();

	}

}
