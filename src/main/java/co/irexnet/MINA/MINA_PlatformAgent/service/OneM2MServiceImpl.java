package co.irexnet.MINA.MINA_PlatformAgent.service;

import co.irexnet.MINA.MINA_PlatformAgent.onem2m.*;
import org.apache.http.HttpResponse;
import org.json.simple.JSONObject;

public class OneM2MServiceImpl implements IOneM2MService
{
    private String c_strUri, c_strCSEBase;
	private String c_token;

    public OneM2MServiceImpl(String uri, String cseBase,String token)
    {
        this.c_strUri = uri;
        this.c_strCSEBase = cseBase;
        this.c_token = token;
    }

    //////////////////////////////////////////////////////////////////////
    // CSEBase
    //////////////////////////////////////////////////////////////////////
    @Override
    public int CSEBaseRetrieve()
    {
        ICSEBase cseBase = new CSEBaseImpl();
        HttpResponse response = cseBase.Retrieve(c_strUri, c_strCSEBase);

        if(response == null)
        {
            return -1;
        }
        else
        {
            return response.getStatusLine().getStatusCode();
        }
    }

    //////////////////////////////////////////////////////////////////////
    // AE
    //////////////////////////////////////////////////////////////////////
    @Override
    public int AECreate(String oid, String poa, String lbl)
    {
        IAE ae = new AEImpl();
        HttpResponse response = ae.Create(c_strUri, c_strCSEBase, oid, poa, lbl);

        if(response == null)
        {
            return -1;
        }
        else
        {
            return response.getStatusLine().getStatusCode();
        }
    }

    @Override
    public int AERetrieve(String oid)
    {
        IAE ae = new AEImpl();
        HttpResponse response = ae.Retrieve(c_strUri, c_strCSEBase, oid);

        if(response == null)
        {
            return -1;
        }
        else
        {
            return response.getStatusLine().getStatusCode();
        }
    }

    @Override
    public int AEUpdatePoa(String oid, String poa)
    {
        IAE ae = new AEImpl();
        HttpResponse response = ae.UpdatePoa(c_strUri, c_strCSEBase, oid, poa);

        if(response == null)
        {
            return -1;
        }
        else
        {
            return response.getStatusLine().getStatusCode();
        }
    }

    @Override
    public int AEDelete(String oid)
    {
        IAE ae = new AEImpl();
        HttpResponse response = ae.Delete(c_strUri, c_strCSEBase, oid);

        if(response == null)
        {
            return -1;
        }
        else
        {
            return response.getStatusLine().getStatusCode();
        }
    }

    //////////////////////////////////////////////////////////////////////
    // container
    //////////////////////////////////////////////////////////////////////

    @Override
    public int ContainerCreate(String oid, String rn, String lbl)
    {
        IContainer container = new ContainerImpl();
        HttpResponse response = container.Create(c_strUri, c_strCSEBase, oid, rn, lbl);

        if(response == null)
        {
            return -1;
        }
        else
        {
            return response.getStatusLine().getStatusCode();
        }
    }

    @Override
    public int ContainerRetrieve(String oid, String rn)
    {
        IContainer container = new ContainerImpl();
        HttpResponse response = container.Retrieve(c_strUri, c_strCSEBase, oid, rn);

        if(response == null)
        {
            return -1;
        }
        else
        {
            return response.getStatusLine().getStatusCode();
        }
    }

    @Override
    public int ContainerDelete(String oid, String rn)
    {
        IContainer container = new ContainerImpl();
        HttpResponse response = container.Delete(c_strUri, c_strCSEBase, oid, rn);

        if(response == null)
        {
            return -1;
        }
        else
        {
            return response.getStatusLine().getStatusCode();
        }
    }

    //////////////////////////////////////////////////////////////////////
    // contentInstance
    //////////////////////////////////////////////////////////////////////
    @Override
    public int ContentInstanceCreate(String nodeId, String message) {
    	// TODO Auto-generated method stub
    	JSONObject jsondata = null;
    	IContentInstance contentInstance = new ContentInstanceImpl();
    	try {
			jsondata=contentInstance.CreateFire(c_strUri, c_strCSEBase,c_token, nodeId,  message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	 //HttpResponse response = contentInstance.CreateFire(c_strUri, c_strCSEBase,c_token, nodeId,  message);
    
         if(jsondata == null)
         {
             return -1;
         }
         else
         {
             return  Integer.parseInt(String.valueOf(jsondata.get("errorCode")));
         }
   		
    
   
    }
    
    
    @Override
    public int ContentInstanceCreate(String oid, String rn, String message)
    {
        IContentInstance contentInstance = new ContentInstanceImpl();
        HttpResponse response = contentInstance.Create(c_strUri, c_strCSEBase, oid, rn, message);

        if(response == null)
        {
            return -1;
        }
        else
        {
            return response.getStatusLine().getStatusCode();
        }
    }

    @Override
    public int ContentInstanceLatest(String oid, String rn)
    {
        IContentInstance contentInstance = new ContentInstanceImpl();
        HttpResponse response = contentInstance.Latest(c_strUri, c_strCSEBase, oid, rn);

        if(response == null)
        {
            return -1;
        }
        else
        {
            return response.getStatusLine().getStatusCode();
        }
    }

    //////////////////////////////////////////////////////////////////////
    // subscription
    //////////////////////////////////////////////////////////////////////

    @Override
    public int SubscriptionCreate(String oid, String cnt, String nu)
    {
        ISubscription subscription = new SubscriptionImpl();
        HttpResponse response = subscription.Create(c_strUri, c_strCSEBase, oid, cnt, nu);

        if(response == null)
        {
            return -1;
        }
        else
        {
            return response.getStatusLine().getStatusCode();
        }
    }

    @Override
    public int SubscriptionRetrieve(String oid, String cnt)
    {
        ISubscription subscription = new SubscriptionImpl();
        HttpResponse response = subscription.Retrieve(c_strUri, c_strCSEBase, oid, cnt);

        if(response == null)
        {
            return -1;
        }
        else
        {
            return response.getStatusLine().getStatusCode();
        }
    }

    @Override
    public int SubscriptionUpdateNu(String oid, String cnt, String nu)
    {
        ISubscription subscription = new SubscriptionImpl();
        HttpResponse response = subscription.UpdateNu(c_strUri, c_strCSEBase, oid, cnt, nu);

        if(response == null)
        {
            return -1;
        }
        else
        {
            return response.getStatusLine().getStatusCode();
        }
    }

    @Override
    public int SubscriptionDelete(String oid, String cnt)
    {
        ISubscription subscription = new SubscriptionImpl();
        HttpResponse response = subscription.Delete(c_strUri, c_strCSEBase, oid, cnt);

        if(response == null)
        {
            return -1;
        }
        else
        {
            return response.getStatusLine().getStatusCode();
        }
    }


}
