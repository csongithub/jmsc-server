package com.jmsc.app;

import java.io.File;  
import java.io.FileInputStream;  
import java.util.Iterator;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
public class Main {  
	public static void main(String[] args)   
	{  
		try{  
			File file = new File("C:\\Users\\Chandan\\Desktop\\input\\input.xlsx");   
			FileInputStream fis = new FileInputStream(file);   
			
			XSSFWorkbook wb = new XSSFWorkbook(fis);   
			XSSFSheet sheet = wb.getSheetAt(0);       
			Iterator<Row> itr = sheet.iterator();  
			while (itr.hasNext()){  
				Row row = itr.next();  
				Iterator<Cell> cellIterator = row.cellIterator();
				String strValue = null;
				int countCell = 1;
				while (cellIterator.hasNext()){
					
					Cell cell = cellIterator.next();
					switch(cell.getCellType()){
						case STRING:
							strValue = cell.getStringCellValue();
							
							break;
						case NUMERIC:
							Double doubleValue = cell.getNumericCellValue();
							strValue = doubleValue.toString();
							break;
					}
					
					System.out.print(strValue);
				}  
				System.out.println("");  
			}  
		}  
		catch(Exception e){  
			e.printStackTrace();  
		}  
	}  
} 