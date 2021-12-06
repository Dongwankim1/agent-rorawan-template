package co.irexnet.MINA.MINA_PlatformAgent.ns;

public interface ICallback
{
	String setCallback(String url, String callbackUrl, String token);
	String getCallback(String url, String token);
	String clearCallback(String url, String token);
}
