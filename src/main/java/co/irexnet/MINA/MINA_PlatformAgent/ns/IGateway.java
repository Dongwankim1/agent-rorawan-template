package co.irexnet.MINA.MINA_PlatformAgent.ns;

public interface IGateway
{
	String getList(String url, String token);
	String getInfo(String url, String gateway_id, String token);
}
