import javafx.application.Application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class test2 {
	public static void main(String[] args)   {
		Application.launch(test.class, args);
		test attempt = new test();
		String thisLine = null;
		double a = 1;
		try
        {
            FileReader fR = new FileReader("values.txt");
			BufferedReader br = new BufferedReader(fR);
		    while ((thisLine = br.readLine()) != null) {
            // System.out.println(thisLine);
				a = Double.parseDouble(thisLine);
		    }       
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(a);
	}
}
