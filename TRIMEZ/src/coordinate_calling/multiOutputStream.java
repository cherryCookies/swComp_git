package coordinate_calling;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.net.URL;

public class multiOutputStream{
	static BufferedInputStream bi = null;
	static FileOutputStream fo = null;
	
	public static void multiOutput(String lastz_add,String ref_seq_add, String trg_seq_add) throws IOException {
		String commands;
		String resAddress = null;

		commands=lastz_add+" "+ref_seq_add+" "+trg_seq_add+" --notransition --step=20 --nogapped --format=general:name1,start1,length1,name2,start2,strand2";
		
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
	    	   	main.main(null);
	       }
	       String s;
	       while ((s =   stdOut.readLine()) != null) {
	    	   m++;
	    	   ///System.out.println(s);
	    	   extract.main(s,m);
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
			///System.out.println("Exit Code: " + oProcess.exitValue());
			///System.exit(oProcess.exitValue()); // 외부 프로그램의 반환값을, 이 자바 프로그램 자체의 반환값으로 삼기
		}
		catch(IOException e){
			System.err.println("에러! 외부 명령 실행에 실패했습니다.\n" + e.getMessage());
			System.exit(-1);
		}
	}

	public static String streamUsingURL(String species, String chromosome, String url) {			
			String DOWNLOAD_URL = url;
			String UPLOAD_PATH = "output";
			String ref_add = null;
			final int MAX_SIZE = 50*1024*1024*1024; // 50GB
			
			try {
				File file = new File(UPLOAD_PATH);
				if(!file.exists()) { // 폴더가 존재하는지 확인
					file.mkdirs(); // 폴더 경로가 없을 경우 폴더 경로 생성
				}
				
				String fileName = species+".fa"; 
				
				bi = new BufferedInputStream(new URL(DOWNLOAD_URL).openStream());
				fo = new FileOutputStream(UPLOAD_PATH + "/" + fileName); // 파일이 저장될 위치
				ref_add=fo.toString();
				
				byte buffer[] = new byte[1024]; // 최대 1KB씩 저장
				int len;
				long fileSize = 0;
				
				while((len = bi.read(buffer, 0, buffer.length)) != -1) { // 파일을 읽으며 buffer에 최대 buffer길이만큼 저장
					fo.write(buffer, 0, len); // buffer의 0~len에 담긴 파일 내용을 저장
					fileSize = fileSize + len;
				
					if(fileSize > MAX_SIZE) { // 최대 범위를 초과할 경우 파일 업로드 종료
						break;
					}
				}
				
				fo.close();
				bi.close();
				
				if(fileSize <= MAX_SIZE) {
					System.out.println("파일 업로드 완료!");
					System.out.println("파일 크기: " + fileSize + "Byte");
				} else {
					new File(UPLOAD_PATH + "/" + fileName).delete();
					System.out.println("파일 최대 사이즈 초과로 파일 삭제 처리!");
				}
				return ref_add;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ref_add;
	}
}