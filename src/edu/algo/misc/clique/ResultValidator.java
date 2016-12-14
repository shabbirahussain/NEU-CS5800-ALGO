package edu.algo.misc.clique;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static edu.algo.misc.clique.Constants.*;

public class ResultValidator {
	/**
	 * Runs clique finder given the full file path
	 * @param fullFilePath is the path of the mtx file to run
	 * @return
	 */
	public static int runValidation(String fullFilePath){
		String s;
		int cnt = 0;
		try {
            
			Process p = Runtime.getRuntime().exec(FMC_EXEC + fullFilePath);
            
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            // read the output from the command
            while ((s = stdInput.readLine()) != null) {
            	if(s.startsWith("Max clique Size :")){
                	cnt = Integer.parseInt(s.split(":")[1].trim());
            	}
                System.out.println(s);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	
		return cnt;
	}
}
