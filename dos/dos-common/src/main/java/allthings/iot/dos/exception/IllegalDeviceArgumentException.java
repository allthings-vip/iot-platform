package allthings.iot.dos.exception;

import allthings.iot.dos.dto.IotDeviceErrorMsgDTO;

/**
 * @author :  luhao
 * @FileName :  IllegalDeviceArgumentException
 * @CreateDate :  2018-5-11
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class IllegalDeviceArgumentException extends RuntimeException {
    private int errorCode;

    private IotDeviceErrorMsgDTO iotDeviceErrorMsgDTO;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public IllegalDeviceArgumentException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public IllegalDeviceArgumentException(IotDeviceErrorMsgDTO iotDeviceErrorMsgDTO) {
        this.iotDeviceErrorMsgDTO = iotDeviceErrorMsgDTO;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public IotDeviceErrorMsgDTO getIotDeviceErrorMsgDTO() {
        return iotDeviceErrorMsgDTO;
    }
}
