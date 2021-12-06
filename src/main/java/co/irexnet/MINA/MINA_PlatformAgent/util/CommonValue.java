package co.irexnet.MINA.MINA_PlatformAgent.util;

public class CommonValue
{
    public static int START_INDEX = 0;

    public static int START_FLAG = 0x55;

    public static final int SENSOR_TEMP = 0x13;
    public static final int SENSOR_HUMIDITY = 0x14;
    public static final int SENSOR_LUMINOSITY = 0x16;
    
    public static final int SENSOR_HIGH_CLASS_C = 0x3E;
    public static final int SENSOR_LOW_CLASS_A = 0x3D;
    
    public static final int[] SENSOR_CLASS_A_ARRAY = {2,4,1,1,2,2,2,2,2,2,2,2,1,2};
    public static final int[] SENSOR_CLASS_C_ARRAY = {2,4,1,1,2,2,2,2,2,2,2,1,2};
    
    
    public static final String[] A_MEASURE_STATE_COLUNM_GROUP = {"accelerationSensorState","smokeSensorState","tempSensorState"};
    public static final String[] A_MEASURE_JUDGE_COLUNM_GROUP = {"accelerationFttJudge","accelerationPeakJudge","smokeJudge","tempJudge","tempAccJudge"};
    
    public static final String[] C_MEASURE_STATE_COLUNM_GROUP = {"accelerationSensorState","smokeSensorState","waveSensorState"};
    public static final String[] C_MEASURE_JUDGE_COLUNM_GROUP = {"accelerationFttJudge","accelerationPeakJudge","smokeJudge","waveJudge"};
    
    public static final String[] ACLASS_DEIVCE_INFO = {"rqstSetTransFreq","cycleRepTransFreq","dataMeasureFreq","tempThreshold","tempAccRange","accelerationXPeak","accelerationYPeak","accelerationZPeak","accelerationXthreshold","accelerationYthreshold","accelerationZthreshold","resetFlag"};
    public static final String[] CCLASS_DEIVCE_INFO = {"rqstSetTransFreq","cycleRepTransFreq","dataMeasureFreq","accelerationXPeak","accelerationYPeak","accelerationZPeak","accelerationXthreshold","accelerationYthreshold","accelerationZthreshold","waveThreshold","resetFlag"};
    public static final String[] RES_STATUS_INFO = {"FUNC_CODE","TRANS_STATE","POWER_STATE"};

}
