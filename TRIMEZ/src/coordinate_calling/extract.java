package coordinate_calling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Emp {
	public Integer ref_coor;
	public String trg_orient;
	public Integer length;
	public Emp(int ref_coor, String trg_orient, int length){
        this.ref_coor = ref_coor;
        this.trg_orient = trg_orient;
        this.length = length;
    }
    public void get_ref_coor() {
    	System.out.print(ref_coor+" ");
    }
    public void get_trg_orient() {
    	System.out.print(trg_orient+" ");
    }
    public void get_length() {
    	System.out.print(length+" "+"\n");
    }
}
public class extract{

	static HashMap<Integer,Emp> dic=new HashMap<Integer,Emp>();
	static List<extract> list = new ArrayList<extract>();
	
	public static void main(String s,Integer m) throws NumberFormatException{
		if (m>1) {
			String[] s_split = s.split("\t");
			
			int ref_crd = Integer.parseInt(s_split[1]);
			String trg_ori=s_split[5];
			int trg_crd=Integer.parseInt(s_split[2]);
			int trg_coor=Integer.parseInt(s_split[4]);
			dic.put(trg_coor,new Emp(ref_crd,trg_ori,trg_crd));
			return;
			///dic구조는 trg_coordinate:Object인데 Object의 하위그룹으로 ref_coor, trg_orient, length가 있다.
			///System.out.println(list);
		}
	}
	
	public static void map_print(HashMap hashmap) {
        ///System.out.println(dic.entrySet());
		dic=hashmap;
       for(Integer key : dic.keySet()){
        	System.out.print(key+":");
        	dic.get(key).get_ref_coor();
        	dic.get(key).get_trg_orient();
        	dic.get(key).get_length();
        }
	}
}