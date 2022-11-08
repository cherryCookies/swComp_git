package DB;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import coordinate_calling.extract;

public class tracks {
	///HashMap {target_coordinate_start:[target_coordiante_end,gene_annotation]}-->얘들은 exon 위치와 유전자 이름을 알려준다.
	static Integer res_srt;
	static Integer res_end;
	static String option;
	public static HashMap<Integer, List> refGene(String species, String chrom) {
		List<String[]> raw_str=new ArrayList<String[]>();
		///List<List> raw_list=new ArrayList<List>();
		List<String> hashMapValue=new ArrayList<String>();
		HashMap<Integer,List> hashMap=new HashMap<Integer,List>();

		String[] exonStarts;
		String[] exonEnds;
		String[] sbf_str = DB_connect2.DBUse(species,"refGene","exonStarts,exonEnds,name2","chrom="+chrom);
		///System.out.println(sbf_str);
		//String[] sbf_str=sbf.toString()split("\n");
		HashMap<Integer, List> coordiantes_dic = extract.returnCoordinates_dic();
		
		for (int i=0;i<sbf_str.length;i++) {//DB에서 한row씩 가져오기
			String each_row=sbf_str[i];
			String[] row_list=each_row.split("\t\t");//3개("exonStarts,exonEnds,name2")로 split
			for (int j=0;j<3;j++) {
				String cell=row_list[j];
				String[] cell_list=cell.split(",");
				raw_str.add(cell_list);
			}
			///int k=0;
				//ref의 exon 기준으로 coordinate의 시작과 끝
			for (int k=0;k<raw_str.get(0).length;k++){
				///raw_str.get(k);
				Integer ref_coor_srt=Integer.parseInt(raw_str.get(0)[k]);//exon시작 coor
				Integer ref_coor_end=Integer.parseInt(raw_str.get(1)[k]);//exon 끝 coor
				String annotation=raw_str.get(2)[0];//exon 기능
				Integer ref_span=ref_coor_end-ref_coor_srt;///exon길이
				///System.out.println(ref_coor_srt.toString()+ref_coor_end.toString()+annotation+ref_span.toString());
			
					///여기서 Key는 trgt이 무조건 갖는 ref상의 coordinate 
				for(Integer key : coordiantes_dic.keySet()){
					List ests = coordiantes_dic.get(key);
					Integer trg_coor_srt = Integer.parseInt(ests.get(0).toString());
					Integer trg_spanning=Integer.parseInt(ests.get(1).toString());
					Integer trg_coor_end = trg_coor_srt+trg_spanning;
					Integer key_end=key+trg_spanning;
					///System.out.println(trg_coor_srt.toString()+trg_coor_end.toString()+annotation+trg_spanning.toString());
					
						//trg의 시작이 ref exon 지역 중간일때
						if (ref_coor_srt>key) {
							res_srt=trg_coor_srt+(ref_coor_srt-key);
							if (key_end<=ref_coor_end){
								res_end=trg_coor_end;
							}else {
								res_end=trg_coor_srt+(ref_coor_end-key);
							}
							hashMapValue.add(res_end.toString());
							hashMapValue.add(annotation);
							hashMap.put(trg_coor_srt, hashMapValue);
							///System.out.println("1");
						 }
						//trg의 시작은 ref exon 지역 보다 앞인데 trgt의 끝이 ref exon 지역의 중간일때
						else {
							res_srt=trg_coor_srt;
							if(key_end<ref_coor_end && key_end>=trg_coor_srt) {
								res_end=trg_coor_end;
							}else if(key_end>ref_coor_end && key_end>=trg_coor_srt){
								res_end=trg_coor_end-(ref_coor_end-key_end);
							}
							hashMapValue.add(res_end.toString());
							hashMapValue.add(annotation);
							hashMap.put(trg_coor_srt, hashMapValue);
							///System.out.println("2");
						 }
					 }
				}
		}
		return hashMap;
	}
	/*
	public static HashMap<Integer, String> simpleRepeat(String species, String chrom){
		///List<String> hashMapValue=new ArrayList<String>();
		///HashMap<Integer,List> hashMap=new HashMap<Integer,List>();
		
		String[] sbf_str = DB_connect2.DBUse(species,"simpleRepeat","chromStart,chromEnd","chrom="+chrom);
		HashMap<Integer, List> coordiantes_dic = extract.returnCoordinates_dic();
		
		for(Integer key : coordiantes_dic.keySet()){
			List ests = coordiantes_dic.get(key);
			Integer trg_coor_srt = Integer.parseInt(ests.get(0).toString());
			Integer trg_spanning=Integer.parseInt(ests.get(1).toString());
			Integer trg_coor_end = trg_coor_srt+trg_spanning;
				
				//trg의 시작이 ref exon 지역 중간일때
				if ((ref_coor_srt-1)<key && key<(ref_coor_end+1)) {
					if (ref_coor_end-key>=trg_spanning){
						hashMapValue.add(trg_coor_end.toString());
					}else{
						trg_coor_end=trg_coor_srt+(ref_coor_end-key);
					}
					hashMapValue.add(trg_coor_end.toString());
					hashMapValue.add(annotation);
					hashMap.put(trg_coor_srt, hashMapValue);						 
				 }
				//trg의 시작은 ref exon 지역 보다 앞인데 trgt의 끝이 ref exon 지역의 중간일때
				else if((ref_coor_srt-1)>=key && key+trg_spanning>(ref_coor_srt)) {
					trg_coor_srt=trg_coor_srt+(ref_coor_srt-key);
					
					if(key+trg_spanning>ref_coor_end) {
						trg_coor_end=ref_coor_end;
						hashMapValue.add(trg_coor_end.toString());
					}else{
						trg_coor_end=trg_coor_srt+(trg_spanning-ref_coor_srt-key);
					}
					hashMapValue.add(trg_coor_end.toString());
					hashMapValue.add(annotation);
					hashMap.put(trg_coor_srt, hashMapValue);	
				 }
			 }
		}
	}
	*/
	
	
	public static void HashMapToFile(HashMap<Integer,List> hashMaps,String TABLE_NAME) {
		File file = new File("output/"+TABLE_NAME+".txt");
		for(Integer key : hashMaps.keySet()) {
			List listToFile=hashMaps.get(key);
			String strToFile=key.toString();
			for (int i=0;i<listToFile.size();i++) {
				strToFile=strToFile+"\t"+listToFile.get(i);
			}
			BufferedWriter bw;
			try {
				bw = new BufferedWriter(new FileWriter(file,true));
				bw.write(strToFile);
				bw.newLine();
				bw.flush();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
