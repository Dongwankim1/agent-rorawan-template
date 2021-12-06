package co.irexnet.MINA.MINA_PlatformAgent.ns;

public class NodeImpl implements INode {

	@Override
	public String getList(String url, String token) 
	{
		String strUrl = url + "/api/v1.0/nodes";
		
		return new RootImpl().send("GET", strUrl, token, null);
	}

	@Override
	public String getInfo(String url, String node_id, String token) 
	{
		String strUrl = url + "/api/v1.0/node/" + node_id;
		
		return new RootImpl().send("GET", strUrl, token, null);
	}
}
