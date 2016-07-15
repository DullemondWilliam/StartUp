import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StartUpMain {

	public static void main(String[] args) {
		isOnBattery();
		
		/*try{
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec("notePad");
		}catch(Exception e){
			e.printStackTrace();
		}*/
	}

	private static Boolean isOnBattery(){
		try{
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec("powercfg /L");
			BufferedReader stdInput = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			
			String s;
			while((s = stdInput.readLine()) != null){
				if(s.contains("Balanced") && s.contains("*")){
					System.out.println("We are on Battery Now");
				}
				System.out.println(s);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
}
