package co.irexnet.MINA.MINA_PlatformAgent.ns;

public interface ICommand
{
	String sendCommand(String url, String node_id, String type, String command, String token);
}
