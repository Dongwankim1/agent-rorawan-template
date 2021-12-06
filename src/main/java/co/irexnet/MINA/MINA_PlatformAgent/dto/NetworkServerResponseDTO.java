package co.irexnet.MINA.MINA_PlatformAgent.dto;

public class NetworkServerResponseDTO 
{
	private String body;
	private int errorCode;
	private String errorString;

	public String getBody()
	{
		return body;
	}
	public void setBody(String body)
	{
		this.body = body;
	}
	public int getErrorCode()
	{
		return errorCode;
	}
	public void setErrorCode(int errorCode)
	{
		this.errorCode = errorCode;
	}
	public String getErrorString()
	{
		return errorString;
	}
	public void setErrorString(String errorString)
	{
		this.errorString = errorString;
	}


}
