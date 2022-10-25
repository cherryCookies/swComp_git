package preprocessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class MultiOutputStream
{
	public static void main(String commands,String resAddress) throws IOException {
		
		try{
			int m = 0;
			Runtime operator=Runtime.getRuntime();
			Process oProcess=operator.exec(commands);
			
			BufferedReader stdOut   = new BufferedReader(new InputStreamReader(oProcess.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(oProcess.getErrorStream()));

	       File file = new File(resAddress); // File객체 생성
	       if(!file.exists()){ // 파일이 존재하지 않으면
	            file.createNewFile(); // 신규생성
	        }
	       else {
	    	   System.out.println("이미 파일이 존재합니다.");
	    	   System.exit(oProcess.exitValue());
	       }
	       String s;
	       while ((s =   stdOut.readLine()) != null) {
	    	   m++;
	    	   System.out.println(s);
	    	   Extract.main(s,m);
	    	   BufferedWriter bw=new BufferedWriter(new FileWriter(file,true));
	    	   bw.write(s);
	    	   bw.newLine();
	    	   bw.flush();
	    	   bw.close();
			}
			while ((s = stdError.readLine()) != null) {
				System.err.println(s);
				System.exit(oProcess.exitValue());
			}
			System.out.println("Exit Code: " + oProcess.exitValue());
			System.exit(oProcess.exitValue()); // 외부 프로그램의 반환값을, 이 자바 프로그램 자체의 반환값으로 삼기
		}
		catch(IOException e){
			System.err.println("에러! 외부 명령 실행에 실패했습니다.\n" + e.getMessage());
			System.exit(-1);
		}
	}	
}