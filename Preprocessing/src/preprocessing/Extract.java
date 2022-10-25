package preprocessing;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
public class Extract{
	
	public static Integer ref_coor;
	public static String trg_orient;
	public static Integer length;
	
	public void set_ref_coor(Integer ref_coor) {
		this.ref_coor = ref_coor;
	}
	public void set_trg_orient(String trg_orient) {
		this.trg_orient = trg_orient;
	}
	public void set_length(Integer length) {
		this.length = length;
	}
    public Integer get_ref_coor() {
        return ref_coor;
    }
    public String get_trg_orient() {
        return trg_orient;
    }
    public Integer get_length() {
        return length;
    }
	
	
	static HashMap<Integer,Object> dic=new HashMap<Integer,Object>();
	static List<Extract> list = new ArrayList<Extract>();
	
	public static void main(String s,Integer m) throws NumberFormatException{
		if (m>1) {
			String[] s_split = s.split("\t");
			Extract extract = new Extract();
			///ref_coor=Integer.parseInt(s_split[1]);
			///System.out.println(Integer.parseInt(s_split[1]));
			extract.set_ref_coor(Integer.parseInt(s_split[1]));
			extract.set_length(Integer.parseInt(s_split[2]));
			extract.set_trg_orient(s_split[5]);
			list.add(extract);
			int trg_coor = Integer.parseInt(s_split[4]);
			dic.put(trg_coor,list);
			System.out.println(list);
			System.out.println(dic);
		}
	}
	
	public static void map_print() {
		System.out.println(dic);
	}
}
