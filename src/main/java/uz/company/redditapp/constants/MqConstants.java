package uz.company.redditapp.constants;

public interface MqConstants {

    String DELAY_EXCHANGE_NAME = "x-delayed-message";

    String X_DELAY = "x-delay";
    String TYPE_ID = "__TypeId__";

    Integer DEFAULT_DELAY = 1000;

    String MAIL_SEND_QUEUE = "SMS_SEND_QUEUE";
    String MAIL_SEND_PAYLOAD = "SMS_SEND_PAYLOAD";

}
