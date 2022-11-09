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
	static HashMap<Integer,List> hashMap;
	static HashMap<Integer,List<String>> rawDBmap;
	
	public static HashMap<Integer, List> refGene(String species, String chrom) {
		List<String[]> raw_strArray;
		List<String> raw_str;
		List<String> hashMapValue;
		hashMap=new HashMap<Integer,List>();
		rawDBmap=new HashMap<Integer,List<String>>();

		String[] sbf_str = DB_connect2.DBUse(species,"refGene","exonStarts,exonEnds,name2","chrom="+chrom);
		///System.out.println(sbf_str);
		//String[] sbf_str=sbf.toString()split("\n");
		HashMap<Integer, List> coordiantes_dic = extract.returnCoordinates_dic();
		
		String each_row,cell;
		String[] row_list,cell_list;
		
		for (int i=0;i<sbf_str.length;i++) {//DB에서 한row씩 가져오기
			each_row=sbf_str[i];
			row_list=each_row.split(",\t\t");//3개("exonStarts,exonEnds,name2")로 split
			raw_strArray=new ArrayList<String[]>();
			
			for (int j=0;j<3;j++) {
				cell=row_list[j];
				cell_list=cell.split(",");
				raw_strArray.add(cell_list);
			}
				//raw_strArray=[exonStarts[],exonEnds[],names[]]

				//ref의 exon 기준으로 coordinate의 시작과 끝
			for (int k=0;k<raw_strArray.get(0).length;k++){
				///raw_str.get(k);
				Integer ref_coor_srt=Integer.parseInt(raw_strArray.get(0)[k]);//exon시작 coor
				String ref_coor_end=raw_strArray.get(1)[k];//exon 끝 coor
				String annotation=raw_strArray.get(2)[0];//exon 기능
				Integer ref_span=Integer.parseInt(ref_coor_end)-ref_coor_srt;///exon길이
				///System.out.println(ref_coor_srt.toString()+ref_coor_end.toString()+annotation+ref_span.toString());
				raw_str=new ArrayList<String>();
				raw_str.add(ref_coor_end);
				///raw_str.add(ref_span.toString());
				raw_str.add(annotation);
				///System.out.println(raw_str);
				rawDBmap.put(ref_coor_srt, raw_str);
			}
		}
		/*
		for(Integer key : coordiantes_dic.keySet()){
			List ests = coordiantes_dic.get(key);
			System.out.println(ests);
		}
		*/
		for(Integer key : coordiantes_dic.keySet()){
			List<Integer> ests = coordiantes_dic.get(key);
			//{ref_coor:[trg_coor,len], ref_coor2:[trg_coor2,len2]}
			for(Integer key2 : rawDBmap.keySet()) {
				List<String> ests_ref=rawDBmap.get(key2);
				//{ref_coor_srt:[ref_coor_end,annotation],ref_coor_srt2:[ref_coor_end2,annotation2], ... }
				Integer ref_coor_srt=key2;
				
				Integer trg_coor_srt = Integer.parseInt(ests.get(0).toString());
				Integer trg_spanning=Integer.parseInt(ests.get(1).toString());
				Integer trg_coor_end = trg_coor_srt+trg_spanning;
				Integer key_end=key+trg_spanning;
				//exon시작 coor
				Integer ref_coor_end=Integer.parseInt(ests_ref.get(0).toString());//exon 끝 coor
				String annotation=ests_ref.get(1).toString();//exon 기능				
				Integer ref_span=trg_spanning;///exon길이
		
				//trg의 시작이 ref exon 지역 앞일때
				if (ref_coor_srt>=key && key_end>ref_coor_srt) { 
					///System.out.println(trg_coor_srt.toString()+trg_coor_end.toString()+annotation+trg_spanning.toString());
					res_srt=trg_coor_srt;
					///res_srt=trg_coor_srt+(ref_coor_srt-key);
					//Case1
					if (key_end<=ref_coor_end){
						res_end=trg_coor_end-(ref_coor_end-key_end);
						hashMapValue=new ArrayList<String>();
						hashMapValue.add(res_end.toString());
						hashMapValue.add(annotation);
						hashMapValue.add("case1");
						hashMap.put(res_srt, hashMapValue);
					}
					//Case2
					else if(ref_coor_end>key_end){
						res_end=trg_coor_end;
						hashMapValue=new ArrayList<String>();
						hashMapValue.add(res_end.toString());
						hashMapValue.add(annotation);
						hashMapValue.add("case2");
						hashMap.put(res_srt, hashMapValue);
					}
				}
				//trg의 시작은 ref exon 지역 보다 앞인데 trgt의 끝이 ref exon 지역의 중간일때
				else if (ref_coor_srt<=key && key<ref_coor_end) {
					///System.out.println(trg_coor_srt.toString()+trg_coor_end.toString()+annotation+trg_spanning.toString());
					//exon시작 coor
					res_srt=trg_coor_srt+(key-ref_coor_srt);
					//Case3
					if(key_end<ref_coor_end) {
						res_end=trg_coor_end-(ref_coor_end-key_end);
						hashMapValue=new ArrayList<String>();
						hashMapValue.add(res_end.toString());
						hashMapValue.add(annotation);
						hashMapValue.add("case3");
						hashMap.put(res_srt, hashMapValue);
					}
					//Case4
					else if(key_end>ref_coor_end){
						res_end=trg_coor_end;
						hashMapValue=new ArrayList<String>();
						hashMapValue.add(res_end.toString());
						hashMapValue.add(annotation);
						hashMapValue.add("case4");
						hashMap.put(res_srt, hashMapValue);
					}
				}
			}
		}
		return hashMap;
	}
	
	public static void HashMapToFile(HashMap<Integer,List> hashmaps,String TABLE_NAME) {
		File file = new File("output/"+TABLE_NAME+".txt");
		for(Integer key : hashmaps.keySet()) {
			List listToFile=hashmaps.get(key);
			///String strToFile=key.toString();
			String res=key.toString()+"\t"+listToFile.get(0).toString();
			for (int i=1;i<listToFile.size();i++) {
				res=res+"\t"+listToFile.get(i).toString();
			}
			BufferedWriter bw;
			try {
				bw = new BufferedWriter(new FileWriter(file,true));
				bw.write(res);
				bw.newLine();
				bw.flush();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
	
	
}
