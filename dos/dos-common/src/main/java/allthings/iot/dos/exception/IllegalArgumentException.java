package allthings.iot.dos.exception;

/**
 * @author :  luhao
 * @FileName :  IllegalArgumentException
 * @CreateDate :  2018-5-10
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
public class IllegalArgumentException extends RuntimeException {
    private int errorCode;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public IllegalArgumentException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }


    public int getErrorCode() {
        return errorCode;
    }
}
