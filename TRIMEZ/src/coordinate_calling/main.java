package coordinate_calling;
import java.io.IOException;
import java.util.HashMap;
import DB.DB_connect2;
import DB.begin_window;



public class main {
	///public static String commands,resAddress;

	public static void main(String[] args) throws IOException{
/*
		Scanner sc=new Scanner(System.in);
		//sequence file의 address를 요구하는 출력문
		System.out.print("[lastz응용실행파일 주소] [reference seq주소] [target seq주소] [parameters] without format setting\n");
		commands=sc.nextLine();
		String trimmedString = commands.trim();
		commands=trimmedString+ " --format=general:name1,start1,length1,name2,start2,strand2";
		System.out.print("[저장하고 싶은 로컬 주소] \n");
		resAddress = sc.nextLine();
		sc.close();
*/
		//Swing interface
		begin_window p = new begin_window();
		String target = p.fdirectory.toString();
		String species = p.DB_NAME;
		String where = "chrom="+p.Chromosome;

		//reference sequence가져오기
		String URL = DB_connect2.DBUse(species,"chromInfo","fileName",where);
		String url = "https://hgdownload.soe.ucsc.edu"+URL;
		String ref_add = multiOutputStream.streamUsingURL(species,p.Chromosome,url);
		multiOutputStream.multiOutput("../../lastz/lastz",ref_add,target);
		
		///alignment 결과 출력(콘솔&파일)
		extract extract = new extract();
		HashMap<Integer, Emp> coordinates = extract.dic;
		extract.map_print(coordinates);
		
		///DB.DB_query.main(hashdic);
		
	}
}

