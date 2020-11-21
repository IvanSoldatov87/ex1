package ex1.src;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ex1.src.WGraph_DS.NodeInfo;


public class WGraph_Algo implements weighted_graph_algorithms {
	WGraph_DS g;
	NodeInfo nodInfo;
	final int maxInt=Integer.MAX_VALUE;
	Queue<node_info> queue =new LinkedList<node_info>();
	@Override
	public void init(weighted_graph g) {
		this.g= (WGraph_DS) g;	
	}
	@Override
	public weighted_graph getGraph() {
		if(g==null) return null;// be careful by null
		return g;
	}
	@Override
	public weighted_graph copy() {
		WGraph_DS newG=new WGraph_DS();
		Iterator<node_info> it=g.getV().iterator();
		while(it.hasNext()) {
			nodInfo= (NodeInfo) it.next();
			//newG.nodeHashMap.put(nodInfo.getKey(), new NodeInfo(nodInfo)); <---- problem in inject inside class can't implement new object, need to check the problem// the copy can be implement in one row/one command
			newG.addNode(nodInfo.getKey());
			newG.getNode(nodInfo.getKey()).setInfo(nodInfo.getInfo());
			newG.getNode(nodInfo.getKey()).setTag(nodInfo.getTag());	
		}
		Collection<Edge> edges=g.getAllEdge();
		for(Edge i:edges) {
			newG.connect(i.getStart(),i.getEnd(), i.getVal());
		}
		return newG;
	}

	@Override
	public boolean isConnected() {
		Iterator<node_info> itt=g.getV().iterator();
		if(g.getV().size()<2)return true;
		bfs(itt.next());
		Iterator<node_info> ittt=g.getV().iterator();
		while(ittt.hasNext()) {
			if(ittt.next().getTag()!=1)return false;
		}
        return true;     
	}

	public void bfs(node_info node )
    {

        for(node_info i :this.g.getV()){
            i.setTag(0);
        }
        node_info nodeInfo;
        node.setTag(1);
        queue.add(node);
        while (!queue.isEmpty())
        {
        	nodeInfo = queue.remove();
        	if(!g.getV(nodeInfo.getKey()).isEmpty()) {
         //  if(!nd.getNi().isEmpty()) {
            Iterator<node_info> it=g.getV(nodeInfo.getKey()).iterator();
            while (it.hasNext())
            {
            	node_info nTampe=it.next();
                if (nTampe.getTag()==0)
                {
                    queue.add(nTampe);
                    nTampe.setTag(1);
                }
            }
            }
        }
    }
	
	
	@Override
	public double shortestPathDist(int src, int dest) {
		if(g.getNode(src)==null || g.getNode(dest)==null) return 0;   
		return bfs2(g.getNode(src),g.getNode(dest));
	}
	public double bfs2(node_info src,node_info dest )
    {
		String visit="V";
        for(node_info i :this.g.getV()){
            i.setTag(maxInt);
        }
        node_info nodeInfo;
        src.setTag(0);
        src.setInfo(visit);
        queue.add(src);
        while (!queue.isEmpty())
        {
        	nodeInfo = queue.remove();
        	nodeInfo.setInfo(visit);
        	if(!g.getV(nodeInfo.getKey()).isEmpty()) {
            Iterator<node_info> it=g.getV(nodeInfo.getKey()).iterator();
            while (it.hasNext())
            {
            	node_info nTampe=it.next();
                if (nTampe.getInfo().isEmpty())
                {
                    queue.add(nTampe);
                    if(nTampe.getTag()>g.getEdge(nodeInfo.getKey(), nTampe.getKey())+nodeInfo.getTag()) {
                    nTampe.setTag(g.getEdge(nodeInfo.getKey(), nTampe.getKey())+nodeInfo.getTag());
                    }
                }
                if(nTampe.getTag()>g.getEdge(nodeInfo.getKey(), nTampe.getKey())+nodeInfo.getTag()) {
                	nTampe.setTag(nodeInfo.getTag()+g.getEdge(nodeInfo.getKey(), nTampe.getKey()));
                }
            }
            }
        }
        if(dest.getTag()==maxInt)return 0;
        return dest.getTag();
    }
	
	
	@Override
	public List<node_info> shortestPath(int src, int dest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(String file) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean load(String file) {
		// TODO Auto-generated method stub
		return false;
	}

}