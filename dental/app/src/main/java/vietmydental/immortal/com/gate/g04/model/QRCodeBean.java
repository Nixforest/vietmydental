package vietmydental.immortal.com.gate.g04.model;

//++ BUG0132-IMT (KhoiVT20180910) [Android] Fix scan QRCode get wrong patient.
public class QRCodeBean {
    private String qrCode;
    private String customerId;

    public QRCodeBean(String qrCode, String customerId) {
        this.qrCode = qrCode;
        this.customerId = customerId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
//-- BUG0132-IMT (KhoiVT20180910) [Android] Fix scan QRCode get wrong patient.
