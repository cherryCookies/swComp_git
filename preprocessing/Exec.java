package preprocessing;
import java.io.*;
import java.util.*;


public class Exec {
	public static String commands,resAddress;
	
	public static void main(String args[]){

		String s;
		Scanner sc=new Scanner(System.in);//System.in 과 Scanner 객체 연결
		System.out.print("Enter the file address of your sequence file with parameters\n");//sequence file의 address를 요구하는 출력문
		commands=sc.nextLine();
		//sc.nextLine();
		System.out.print("Where do you want to save the results? \n");//sequence file의 address를 요구하는 출력문
		resAddress = sc.nextLine();
		sc.close();

		try{
			Runtime operator=Runtime.getRuntime();
			Process oProcess=operator.exec(commands);
			
			BufferedReader stdOut   = new BufferedReader(new InputStreamReader(oProcess.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(oProcess.getErrorStream()));
			while ((s =   stdOut.readLine()) != null) {
				///System.out.println(s);
				MultiOutputStream.main(s,resAddress);
			}
			while ((s = stdError.readLine()) != null) {
				//System.err.println(s);
				return;
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


