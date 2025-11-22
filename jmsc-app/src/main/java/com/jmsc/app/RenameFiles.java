package com.jmsc.app;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class RenameFiles {


    public static void main(String[] argv) throws IOException {

		String filePath = "C:\\Users\\anuhr\\Desktop\\RRSMP\\Aurangabad 04 NIT 10\\all";
        File folder = new File(filePath);
        
        File[] files = folder.listFiles();
        List<File>	list = Arrays.asList(files);
        list.sort(new GraduationCeremonyComparator());
        
        int index = 0;
        for(File file: list) {
        	 if (file.isFile()) {

        		 String oldFileName = file.getName();
        		 String newFileName = "JMSC_" + oldFileName;
        		 File f = new File(filePath + "\\" + oldFileName); 
                 f.renameTo(new File(filePath + "\\" + newFileName));
             }
        }
        System.out.println("conversion is done");
    }
}


class GraduationCeremonyComparator implements Comparator<File> {
    public int compare(File o1, File o2) {
        int value1 = o1.getName().compareTo(o2.getName());
        return value1;
    }
}