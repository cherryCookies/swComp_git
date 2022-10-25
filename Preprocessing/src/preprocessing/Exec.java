package preprocessing;
import java.io.*;
import java.util.*;


public class Exec {
	public static String commands,resAddress;
	
	public static void main(String args[]) throws IOException{

		Scanner sc=new Scanner(System.in);
		
		//sequence file의 address를 요구하는 출력문
		System.out.print("[lastz응용실행파일 주소] [reference seq주소] [target seq주소] [parameters] without format setting\n");
		commands=sc.nextLine();
		String trimmedString = commands.trim();
		commands=trimmedString+ " --format=general:name1,start1,length1,name2,start2,strand2";
		System.out.print("[저장하고 싶은 로컬 주소] \n");
		resAddress = sc.nextLine();
		sc.close();
		
		//alignment 결과 출력(콘솔&파일)
		MultiOutputStream.main(commands, resAddress);
		
		//저장한 hashmap return함
	}
}


