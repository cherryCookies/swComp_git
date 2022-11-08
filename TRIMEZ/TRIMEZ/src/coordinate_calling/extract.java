package coordinate_calling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class extract{

	static HashMap<Integer,List> alignment_dic=new HashMap<Integer,List>();
	static HashMap<Integer,List> cooridnates_dic=new HashMap<Integer,List>();
	///static List<String> list;
	/*
	public extract(){
		this.alignment_dic=alignment_dic;
		this.cooridnates_dic=cooridnates_dic;
	}
	*/
	public static void addAlignmentMap(String alignments) throws NumberFormatException{
		List<Integer> lens=new ArrayList<Integer>();
		if (alignments.length()>1) {
			String[] alignment_list=alignments.split("\t");
			List<String> lists = Arrays.asList(alignment_list);
			Integer ref_coor=Integer.parseInt(lists.get(1));
			Integer trg_coor=Integer.parseInt(lists.get(4));
			Integer len=Integer.parseInt(lists.get(2));
			lens.add(trg_coor);
			lens.add(len);
			alignment_dic.put(ref_coor,lists);
			cooridnates_dic.put(ref_coor, lens);
		}
	}
	public List getMapValue(Integer ref_coordinates) {
		List mapValue = alignment_dic.get(ref_coordinates);
		return mapValue;
	}
	/*
	public static Integer getTrgCoor(Integer ref_coordinates) {
		Integer trg_coor = cooridnates_dic.get(ref_coordinates);
		return trg_coor;
	}
	*/
	
	public static void map_print() {
       for(Integer key : alignment_dic.keySet()){
    	   System.out.print(key+": ");
    	   System.out.println(alignment_dic.get(key));
        }
	}
	public static void map_print2(HashMap<Integer,List> mapsInput) {
	       for(Integer key2: mapsInput.keySet()){
	    	   System.out.print(key2+": ");
	    	   System.out.print(mapsInput.get(key2).get(0).toString());
	    	   System.out.println(mapsInput.get(key2).get(1).toString());
	        }
		}
	public static HashMap<Integer, List> returnCoordinates_dic() {
		return cooridnates_dic;
	}
	/*
	public static void main() {
		extract hashmaps =new extract();
	}
	*/
}