package co.irexnet.MINA.MINA_PlatformAgent.service;

public interface IOneM2MService
{
    // CSEBase
    int CSEBaseRetrieve();

    // AE
    int AECreate(String oid, String poa, String lbl);
    int AERetrieve(String oid);
    int AEUpdatePoa(String oid, String poa);
    int AEDelete(String oid);

    // Container
    int ContainerCreate(String oid, String rn, String lbl);
    int ContainerRetrieve(String oid, String rn);
    int ContainerDelete(String oid, String rn);

    // ContentInstance
    int ContentInstanceCreate(String nodeId, String message);
    int ContentInstanceCreate(String oid, String rn, String message);
    int ContentInstanceLatest(String oid, String rn);

    // Subscription
    int SubscriptionCreate(String oid, String cnt, String nu);
    int SubscriptionRetrieve(String oid, String cnt);
    int SubscriptionUpdateNu(String oid, String cnt, String nu);
    int SubscriptionDelete(String oid, String cnt);
}
