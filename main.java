import java.io.*;
import java.util.*;
import java.util.concurrent.*;


class graph
{
	Map<Long,ArrayList<Long>> G;
	public graph()
	{
		G = new HashMap<Long,ArrayList<Long>>();
	}
	public int nodes()
	{
		return G.keySet().size();
	}

	public ArrayList<Long> random_walk(int path_length, Long start)
	{
		ArrayList<Long> path = new ArrayList<Long>();
		path.add(start);
		while (path.size() < path_length)
		{
			Long cur = path.get(path.size()-1);
			Random rand = new Random();
			int num = rand.nextInt(2);
			if (this.G.get(cur).size()>0)
			{
				path.add(this.G.get(cur).get(rand.nextInt(this.G.get(cur).size())));
			}
			else
			{
				break;
			}
		}
		
		System.out.println("path size"+" "+path.size());
		return path;
	}

	/*
	Creator :- 0:post 1:user
	Consumer :- 0:post 1:creator
	Post :- 0:consumer 1:creator
	*/
	public void load_edgelist(String file_,Map<Long,String> G1) throws IOException
	{
		ArrayList<Long> keys = new ArrayList<Long>(G1.keySet());
		System.out.println(keys.size());
		for (int i=0;i<keys.size();i++)
		{
			//this.G.put(G1);
			this.G.put(keys.get(i),new ArrayList<Long>());
		}
		System.out.println(this.G.size());
		File dir = new File(file_);
		File[] fileslist = dir.listFiles();
		for (File file : fileslist)
		{
			System.out.println(file.getName());
			if(file.isFile() && file.getName().endsWith("edgelist.txt"))
			{
				FileReader fw = new FileReader(file);
				BufferedReader br = new BufferedReader(fw);
				String s = br.readLine();
				while(s!=null)
				{
					String [] x = s.trim().split(",");
					//System.out.println(x[0]+" "+x[1]);
					//System.out.println(x[0]);
					this.G.get(Long.parseLong(x[0])).add(Long.parseLong(x[1]));
					this.G.get(Long.parseLong(x[1])).add(Long.parseLong(x[0]));
					s = br.readLine();

				}
			}
		}


	}
}

public class main implements Runnable
{
	static graph main_graph = new graph();
	static Map <Long,String> node_mapping = new HashMap<Long,String>();
	static int num_paths = 10;
	static int path_length =40;
	//static ArrayList<String> nodes;
	String file;
	String temp;
	public main()
	{

	}
	public main(String temp, String file)
	{
		this.file = file;
		this.temp = temp;
	}
	public void build_deepwalk_corpus(String temp, String file)
	{

		ArrayList<ArrayList<Long>> walks = new ArrayList<ArrayList<Long>>();
		int count = 0;
		ArrayList<Long> nodes = new ArrayList<Long>(this.main_graph.G.keySet());
		Collections.sort(nodes);
		for (int i=0;i<nodes.size();i++)
		{
			System.out.println("nodes" +" "+nodes.get(i));
		}
		//System.out.println(nodes.size());
		String [] ar1 = new String[2];
		int [] ar = new int[2];
		ar1 = temp.split(" ");
		ar[0] = Integer.parseInt(ar1[0]);
		ar[1] = Integer.parseInt(ar1[1]);
		for(int i=0;i<this.num_paths;i++)
		{
			for(int j = ar[0]-1;j<ar[1];j++)
			{
				walks.add(main_graph.random_walk(this.path_length,nodes.get(j)));
				count += 1;
				System.out.println(count);
			}
		}
		System.out.println("Writing to FIle");
		try
		{
			File file1 = new File(file);
			FileWriter fr = new FileWriter(file1);
			BufferedWriter br = new BufferedWriter(fr);
			for (int i=0;i<walks.size();i++)
			{
				//System.out.print(walks.get(i).get(0));
				System.out.println(1);
				br.write(String.valueOf(walks.get(i).get(0)));
				for(int j=1;j<walks.get(i).size();j++)
				{
					br.write(" "+walks.get(i).get(j));
					//System.out.print(" "+walks.get(i).get(j));
				}
				br.write("\n");
				//System.out.println();
			}
			System.out.println("done");
			br.close();
		}
		catch(IOException e)
		{
			System.out.println("error");
		}
		
	}

	public void run()
	{
		this.build_deepwalk_corpus(this.temp,this.file);
	}
	public static void main(String [] args) throws IOException,InterruptedException
	{
		Runnable m1 = new main("1 486","walk.txt");
		main m = new main();
		File f = new File("nodeidmap.txt");
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String s =  br.readLine();
		while(s!=null)
		{
			//System.out.println(1);
			String [] temp = s.trim().split(",");
			//System.out.println(temp[0]);
			m.node_mapping.put(Long.parseLong(temp[1]),temp[0]);
			s = br.readLine();
		}
		m.main_graph.load_edgelist("./",m.node_mapping);
		System.out.println("graph_loaded");
		// m.nodes =  new ArrayList<Long>(m.main_graph.G.keySet());
		// Collections.sort(m.nodes);s
		System.out.println(m.main_graph.G.keySet().size()+" wbvgeryvhejrwnkmre");
		int count = 0;
		//System.out.println(m.toString());
		/*for (String key : m.main_graph.G.keySet())
		{
			System.out.println("new_node");
			System.out.println(key);
			for(int i=0;i<2;i++)
			{
				for (int k=0;k<2;k++)
				{
					for (int j=0;j<m.main_graph.G.get(key).get(i).get(k).size();j++)
					{
						System.out.print(m.main_graph.G.get(key).get(i).get(k).get(j) + " ");
					}
					//System.out.println("hi");
					System.out.println();
				}

			}
		}*/
		//System.out.println(m.main_graph.G.keySet().toString());
		ExecutorService exec = Executors.newFixedThreadPool(1);
		//exec.execute(m14);exec.execute(m15);
		exec.execute(m1);//  exec.execute(m2);exec.execute(m3);exec.execute(m4);exec.execute(m5);exec.execute(m6);
		// exec.execute(m7); exec.execute(m8);exec.execute(m9);exec.execute(m10);exec.execute(m11);exec.execute(m12);exec.execute(m13);
		// if (!exec.isTerminated())
		{
			exec.shutdown();
			exec.awaitTermination(5L,TimeUnit.SECONDS);
		}
	}
}
