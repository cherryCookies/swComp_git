package preprocessing;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class MultiOutputStream extends OutputStream
{
	OutputStream[] outputStreams;
	
	public MultiOutputStream(OutputStream... outputStreams)
	{
		this.outputStreams= outputStreams; 
	}
	
	@Override
	public void write(int b) throws IOException
	{
		for (OutputStream out: outputStreams)
			out.write(b);			
	}
	
	@Override
	public void write(byte[] b) throws IOException
	{
		for (OutputStream out: outputStreams)
			out.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException
	{
		for (OutputStream out: outputStreams)
			out.write(b, off, len);
	}

	@Override
	public void flush() throws IOException
	{
		for (OutputStream out: outputStreams)
			out.flush();
	}

	@Override
	public void close() throws IOException
	{
		for (OutputStream out: outputStreams)
			out.close();
	}
	public static void main(String s,String resAddress) {
		try
		{
			FileOutputStream fout= new FileOutputStream(resAddress);
			FileOutputStream ferr= new FileOutputStream(resAddress);
			
			MultiOutputStream multiOut= new MultiOutputStream(System.out, fout);
			MultiOutputStream multiErr= new MultiOutputStream(System.err, ferr);
			
			PrintStream stdout= new PrintStream(multiOut);
			PrintStream stderr= new PrintStream(multiErr);
			
			System.setOut(stdout);
			System.setErr(stderr);
			
			System.out.println(s);
			System.err.println(s);
		}
		catch (FileNotFoundException ex)
		{
			//Could not create/open the file
		}
	}
}