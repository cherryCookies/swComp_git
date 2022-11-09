package coordinate_calling;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import DB.DB_connect2;
import DB.begin_window;
import DB.tracks;



public class main {
	static String target,species,Chromosome,TABLE_NAME;

	public static void main(String[] args) throws IOException{
		//Swing interface
		/*
		begin_window p = new begin_window();
		while(target==null || species==null || Chromosome==null) {
	    	target = p.getDirectory();
	    	species = p.getDbName();
	    	Chromosome = p.getChrNme().toString();
	    	System.out.println("Hello--------------");
		}
		///System.out.println(species);
		///System.out.println(target);
		 * */
		///System.out.println(begin_window_res);
		String[] begin_window_res = null;
		/*
		if (begin_window.main()!=null) {
			begin_window_res=begin_window.main();
		};
		*/
		begin_window_res=begin_window.main();
		TABLE_NAME=begin_window_res[3];
		Chromosome=begin_window_res[2]+"'";
		species=begin_window_res[1];
		target=begin_window_res[0];
		String where = "chrom='"+Chromosome;
		System.out.println(where);
		System.out.println(species);
		System.out.println(target);
		System.out.println(TABLE_NAME);
		
		String url ="https://hgdownload.soe.ucsc.edu/goldenPath/hg38/chromosomes/"+begin_window_res[2]+".fa.gz";
		System.out.println(url);
		String ref_add = multiOutputStream.streamUsingURL(species,begin_window_res[2],url);
		System.out.println(ref_add);
		String fo = "output/output.fa";
		GZipExample.main(ref_add, fo);
		System.out.println("?");
		///multiOutputStream.multiOutput("/home/bioinfo/SW_comp/TRIMEZ/TRIMEZ/lastz/lastz",fo,target);
		multiOutputStream.multiOutput("/home/bioinfo/lastz",fo,target);
		System.out.println("??");
		///multiOutputStream.multiOutput("lastz/lastz",fo,target);
		///extract hashmaps =new extract();
		///HashMap<Integer, List> alignment_dic = extract.main();
		extract.map_print();
		
		HashMap exonsAndGene = tracks.refGene(species, "'"+Chromosome);
		System.out.println("???");
		extract.map_print2(exonsAndGene);
		System.out.println("????");
		tracks.HashMapToFile(exonsAndGene,"refGene");
		System.out.println("?????");
		/*
		if (TABLE_NAME=="refGene"){
			HashMap exonsAndGene = tracks.refGene(species, Chromosome);
			System.out.println("???");
			extract.map_print2(exonsAndGene);
			System.out.println("????");
			tracks.HashMapToFile(exonsAndGene,"refGene");
		}
		*/
		
	}	
}


