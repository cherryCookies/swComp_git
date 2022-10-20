package preprocessing;
import java.io.*;
import java.lang.ProcessBuilder;
import java.util.*;

public class Exec {
	
	static String refPath, queryPath;
	static int resolution[];
	
	public static void WhatsIn(){
		Scanner sc=new Scanner(System.in);//System.in 과 Scanner 객체 연결
		
		System.out.print("Enter the file address of your sequence file");//sequence file의 address를 요구하는 출력문
		refPath=sc.nextLine();
		
		System.out.print("Enter the reference species");//sequence file의 address를 요구하는 출력문
		queryPath=sc.nextLine();
		/*
		System.out.print("Enter the resolution for synteny");//synteny를 만들기 위한 resolution을 요구하는 출력문
		int resolution=sc.nextInt();//나중에 GUI로 reference를 받을 때 여기다가 연결해줘야해!!!
		*/
		
		sc.close();
	}
	
	
	public static String call() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("C:/home/bioinfo/lastz-distrib/bin",refPath,queryPath);
       
        Process process = processBuilder.start();
        int waitFlag = process.waitFor();
        
        StringBuilder sb = new StringBuilder("");
        if (waitFlag == 0) {
                if (process.exitValue()==0) {
                        System.out.println("This is me " + process.info());

                        BufferedInputStream in = (BufferedInputStream) process.getInputStream();
                        byte[] contents = new byte[1024];

                        int bytesRead = 0;

                        while ((bytesRead = in.read(contents)) != -1) {
                                sb.append(new String(contents, 0, bytesRead));
                        }
                }

        }
        return sb.toString();
	}
	public static void main(String[] args) {
		 WhatsIn();
		 call();
		 processBuilder=ProcessBuilder("C:/home/bioinfo/lastz-distrib/bin",refPath,queryPath);
	}
	
}
