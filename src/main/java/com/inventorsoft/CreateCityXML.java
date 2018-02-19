package com.inventorsoft;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateCityXML {

    public static void main(String[] args) throws IOException {
        int i = 0;
        String inputFILE = "src/main/resources/cityCodes.txt";
        String outputFILE = "src/main/resources/cityCodes.xml";
        File file = new File(inputFILE);
        try {
            FileWriter writer = new FileWriter(outputFILE, false);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null ) {
                String[] s = line.split("[\\s   ]", 10);
                writer.append("<city>" + "\n"+ "<code>"+s[8]+"</code>" + "\n" +  "<name>" + delimiter(s[9]) + "</name>" +"\n" + "</city>" + "\n");
                System.out.println(i++);
            }
            br.close();
            fr.close();
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String delimiter(String s) {
        char[] chars = s.toCharArray();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (validateLocation(String.valueOf(chars[i]))) {
                result.append(chars[i]);
            }
            if (!validateLocation(String.valueOf(chars[i])) && validateLocation(String.valueOf(chars[i+1]))) {
                result.append(chars[i]);
            }
            if (!validateLocation(String.valueOf(chars[i])) && !validateLocation(String.valueOf(chars[i+1]))) {
                return result.toString();
            }
        }

        return result.toString();
    }

    public static boolean validateLocation(String qwe) {
        Pattern p = Pattern.compile("([а-яА-Я]|-)");
        Matcher m = p.matcher(qwe);
        return m.matches();
    }

}