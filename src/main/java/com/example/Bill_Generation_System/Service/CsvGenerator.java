package com.example.Bill_Generation_System.Service;

import com.example.Bill_Generation_System.DTO.DailyProductSalesReport;
import com.example.Bill_Generation_System.Model.Product;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CsvGenerator {

    public static File generateProductDetailsReport(List<Product> products){

        try{

            File directory = new File("C:\\Users\\Margish\\Desktop\\Files");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File file = new File(directory, "ProductDetails(" + LocalDate.now() + ").csv");
            FileWriter writer = new FileWriter(file);


            writer.write("Product Id , Product Name , Quantity, Price\n");

            for(Product  product :products){
                writer.write(product.getId() + " , " + product.getName() + " , " + product.getQuantityInStock() + " , " + product.getPrice() + " \n");
            }

            writer.flush();
            writer.close();

            return file;
        }catch (Exception e){
            throw new RuntimeException("Failed to generate CSV: " + e.getMessage(), e);
        }

    }


    public static File generateDailySalesReport(List<DailyProductSalesReport> sales) throws IOException {


        try{
            File directory = new File("C:\\Users\\Margish\\Desktop\\Files");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File file = new File(directory, "DailySalesReport(" + LocalDate.now() + ").csv");
            FileWriter writer = new FileWriter(file);

            writer.write("Product ID,Product Name,Product Price,Quantity Sold,Total Sales Amount\n");

            for (DailyProductSalesReport report : sales) {
                writer.write(
                        report.getProductId() + "," +
                                report.getProductName() + "," +
                                report.getProductPrice() + "," +
                                report.getQuantitySold() + "," +
                                report.getTotalAmount() + "\n"
                );
            }
            writer.flush();
            writer.close();
            return file;
        }catch (Exception e){
            throw new RuntimeException("Failed to generate CSV: " + e.getMessage(), e);
        }

    }


    public static File generateLowStockReport(List<Product> products){


        try{
            File directory = new File("C:\\Users\\Margish\\Desktop\\Files");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File file = new File(directory, "LowStockReport(" + LocalDate.now() + ").csv");
            FileWriter writer = new FileWriter(file);

            writer.write("Product ID,Product Name,lowStockThreshold,QuantityInStock,\n");

            for (Product product:products){
                writer.write(
                        product.getId() + "," +
                                product.getName() + "," +
                                product.getLowStockThreshold() + "," +
                                product.getQuantityInStock() + "\n"
                );
            }
            writer.flush();
            writer.close();
            return file;
        }catch (Exception e){
            throw new RuntimeException("Failed to generate CSV: " + e.getMessage(), e);
        }

    }
}
