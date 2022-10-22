package preprocessing;
import java.io.*;
import java.util.*;

public class Exec {
	static String refPath;
	
	public static void main(String args[]){
		String s;
		Scanner sc=new Scanner(System.in);//System.in 과 Scanner 객체 연결
		
		System.out.print("Enter the file address of your sequence file\n");//sequence file의 address를 요구하는 출력문
		refPath=sc.nextLine();
		sc.close();

		try{
			Runtime operator=Runtime.getRuntime();
			Process oProcess=operator.exec(refPath);
			
			BufferedReader stdOut   = new BufferedReader(new InputStreamReader(oProcess.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(oProcess.getErrorStream()));
			while ((s =   stdOut.readLine()) != null) System.out.println(s);
			while ((s = stdError.readLine()) != null) System.err.println(s);
			System.out.println("Exit Code: " + oProcess.exitValue());
			System.exit(oProcess.exitValue()); // 외부 프로그램의 반환값을, 이 자바 프로그램 자체의 반환값으로 삼기
			}
		catch(IOException e){
			System.err.println("에러! 외부 명령 실행에 실패했습니다.\n" + e.getMessage());
			System.exit(-1);
		}
	}
}
///System.out.println(refPath);
/*
ProcessBuilder builder=new ProcessBuilder(refPath);
Process program=builder.start();
*/
