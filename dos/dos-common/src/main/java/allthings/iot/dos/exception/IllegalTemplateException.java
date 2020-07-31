package allthings.iot.dos.exception;

/**
 * @author :  luhao
 * @FileName :  IllegalTemplateException
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
public class IllegalTemplateException extends IllegalArgumentException {
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     * @param errorCode
     * @param message
     */
    public IllegalTemplateException(int errorCode, String message) {
        super(errorCode, message);
    }
}
