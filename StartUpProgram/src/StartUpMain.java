import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class StartUpMain {

	public static void main(String[] args) {
		Boolean battery = isOnBattery();

		try {
			Path path = FileSystems.getDefault().getPath("","settings");
			InputStream in = Files.newInputStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			String type = null;
			String line = null;
			while ((line = reader.readLine()) != null) {
				if(line.contains("<>")){
					type = reader.readLine();
				}else if(type.equalsIgnoreCase("All Starting Commands")){
					runCommand(line); 
				}else if(type.equalsIgnoreCase("Plugged In Commands") && battery == false){
					runCommand(line); 
				}else if(type.equalsIgnoreCase("Battery Commands") && battery == true){
					runCommand(line); 
				}
			}

			reader.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Boolean runCommand(String command){
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(command);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}



	private static Boolean isOnBattery(){
		try{
			Boolean out = false;

			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec("powercfg /batteryreport");

			Path path = FileSystems.getDefault().getPath("","battery-report.html");
			InputStream in = Files.newInputStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			String line = null;
			while ((line = reader.readLine()) != null) {
				// System.out.println(line);
				if(line.contains("Report generated")){
					reader.readLine();
					reader.readLine();
					reader.readLine();
					if(reader.readLine().contains("Battery")){
						out = true;
					}
				}
			}
			reader.close();
			in.close();
			Files.delete(path);
			return out;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

}
