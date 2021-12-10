package com.agent.scheduler;

import com.agent.dto.*;
import com.agent.service.INetworkServerService;
import com.agent.service.IOneM2MService;
import com.agent.service.NetworkServerServiceImpl;
import com.agent.service.OneM2MServiceImpl;
import com.agent.util.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Getter
public class Scheduler
{
    @Autowired
    PropertiesServiceOid propertiesServiceOid;

    @Autowired
    PropertiesOneM2M propertiesOneM2M;

    @Autowired
    PropertiesNetworkServer propertiesNetworkServer;

    @Autowired
    NetworkServerGatewayList networkServerGatewayList;

    @Autowired
    NetworkServerNodeList networkServerNodeList;

    @Value("${server.port}")
    private int serverPort;

    private IOneM2MService oneM2MService;
    private INetworkServerService networkServerService;

    @Scheduled(fixedDelayString = "${properties.network_server.period}")
    public void enrollProcess()
    {
    	
        log.info("nsCheckProcess Start...Thread[{}]", Thread.currentThread().getName());
        log.info("service-oid:[{}]", propertiesServiceOid);
        log.info("onem2m:[{}]", propertiesOneM2M);
        log.info("NetworkServer:[{}]", propertiesNetworkServer);

        oneM2MService = new OneM2MServiceImpl(propertiesOneM2M.getUri(), propertiesOneM2M.getCseBase(),propertiesNetworkServer.getToken());
        int nOneM2MStatus = oneM2MService.CSEBaseRetrieve();
        log.info("CSEBase Retrieve:[{}]", nOneM2MStatus);

        networkServerService = new NetworkServerServiceImpl(propertiesNetworkServer.getUri(), propertiesNetworkServer.getToken());
        int nNetworkServerStatus = networkServerService.getConnection();
        log.info("Network Server Connection:[{}]", nNetworkServerStatus);

        if(nNetworkServerStatus == HttpStatus.SC_OK)
        {
            // Check Network Server Callback Information
            NetworkServerCallbackDTO networkServerCallbackDTO = networkServerService.getCallback();
            log.info("Network Server [GET]CallBack:[{}]", networkServerCallbackDTO);

            String strCallback = "http://" + propertiesNetworkServer.getCallback() + ":" + serverPort + "/callback";
            if(networkServerCallbackDTO.getCallbackUrl() == null ||
                    networkServerCallbackDTO.getCallbackUrl().equalsIgnoreCase("") == true)
            {
                networkServerCallbackDTO = networkServerService.setCallback(strCallback);
                log.info("Network Server [SET]Callback:[{}]", networkServerCallbackDTO);
            }
            else if(networkServerCallbackDTO.getCallbackUrl().equalsIgnoreCase(strCallback) == false)
            {
                networkServerCallbackDTO = networkServerService.setCallback(strCallback);
                log.info("Network Server [SET]Callback:[{}]", networkServerCallbackDTO);
            }

            // Get Network Server Gateway List
            NetworkServerGatewaysDTO tempGatewayList = networkServerService.getGatewayList();
            if(tempGatewayList != null && tempGatewayList.getGateways().size() > 0)
            {
                networkServerGatewayList.setNetworkServerGatewayList(tempGatewayList.getGateways());
            }

            // Get Network Server Node List
            NetworkServerNodesDTO tempNodeList = networkServerService.getNodeList();
            if(tempNodeList != null && tempNodeList.getNodes().size() > 0)
            {
                networkServerNodeList.setNetworkServerNodeList(tempNodeList.getNodes());
            }
        }

        if(nOneM2MStatus == HttpStatus.SC_OK)
        {
            for(NetworkServerNodeInfo node : networkServerNodeList.getNetworkServerNodeList())
            {
                for(String oidType : propertiesServiceOid.getService_oid())
                {
                    if(node.getNode_type().indexOf(oidType) > -1)
                    {
                        String strPoa = "http://" + propertiesOneM2M.getPoa() + ":" + serverPort + "/poa/" + node.getNode_type();
                        int nStatusCode = oneM2MService.AERetrieve(node.getNode_type());
                        log.info("OID:[{}] AE Retrieve:[{}]", node.getNode_type(), nStatusCode);

                        if(nStatusCode == HttpStatus.SC_NOT_FOUND)
                        {
                            log.info("OID:[{}], AE Create:[{}]",
                                    node.getNode_type(), oneM2MService.AECreate(node.getNode_type(), strPoa, "MINA"));
                            log.info("OID:[{}], container Create:[{}]",
                                    node.getNode_type(),
                                    oneM2MService.ContainerCreate(node.getNode_type(), node.getNode_type(), "status-container"));
                        }
                        else if(nStatusCode == HttpStatus.SC_OK)
                        {
                            log.info("OID:[{}] AE Update poa:[{}]",
                                    node.getNode_type(), oneM2MService.AEUpdatePoa(node.getNode_type(), strPoa));
//                            log.info("OID:[{}] AE Delete:[{}]",
//                                    node.getNode_type(), oneM2MService.AEDelete(node.getNode_type()));
                        }
                        break;
                    }
                }
            }
        }
    }
}
