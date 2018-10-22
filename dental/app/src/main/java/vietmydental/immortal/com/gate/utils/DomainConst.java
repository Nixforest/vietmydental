package vietmydental.immortal.com.gate.utils;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vietmydental.immortal.com.vietmydental.R;

public final class DomainConst {
    /**
     * Submit customer info
     */
    public static final String PATH_CUSTOMER_HGD_CREATE = "customer/hgdCreate";
    /**
     * Update customer info
     */
    public static final String PATH_CUSTOMER_HGD_UPDATE = "customer/hgdUpdate";
    /**
     * Get customer list
     */
    public static final String PATH_CUSTOMER_HGD_LIST = "customer/hgdList";
    /**
     * Get customer list
     */
    public static final String PATH_ORDER_GET_CONFIG = "order/getConfig";
    /**
     * Get customer list
     */
    public static final String PATH_CUSTOMER_HGD_VIEW = "customer/hgdView";
    /**
     * Autocomplete user
     */
    public static final String PATH_SITE_AUTOCOMPLETE_USER = "site/autocompleteUser";
    /**
     * Confirm notify
     */
    public static final String PATH_SITE_CONFIRM_NOTIFY = "site/confirmNotify";
    /**
     * Issue close
     */
    public static final String PATH_SITE_ISSUE_CLOSE = "site/issueClose";
    /**
     * Issue create
     */
    public static final String PATH_SITE_ISSUE_CREATE = "site/issueCreate";
    /**
     * Issue list
     */
    public static final String PATH_SITE_ISSUE_LIST = "site/issueList";
    /**
     * Issue Reopen
     */
    public static final String PATH_SITE_ISSUE_REOPEN = "site/issueReopen";
    /**
     * Issue Reply
     */
    public static final String PATH_SITE_ISSUE_REPLY = "site/issueReply";
    /**
     * Issue View
     */
    public static final String PATH_SITE_ISSUE_VIEW = "site/issueView";
    /**
     * Login page
     */
    public static final String PATH_SITE_LOGIN = "default/login";
    /**
     * Logout page
     */
    public static final String PATH_SITE_LOGOUT = "default/logout";
    /**
     * Update config
     */
    public static final String PATH_SITE_UPDATE_CONFIG = "default/updateConfig";
    /**
     * News list
     */
    public static final String PATH_SITE_NEWS_LIST = "site/newsList";
    /**
     * News view
     */
    public static final String PATH_SITE_NEWS_VIEW = "site/newsView";
    /**
     * Notify count
     */
    public static final String PATH_SITE_NOTIFY_COUNT = "site/notifyCount";
    /**
     * Notify list
     */
    public static final String PATH_SITE_NOTIFY_LIST = "site/notifyList";
    /**
     * Notify view
     */
    public static final String PATH_SITE_NOTIFY_VIEW = "site/notifyView";
    /**
     * Order create
     */
    public static final String PATH_SITE_ORDER_CREATE = "site/orderCreate";
    /**
     * Order create data label
     */
    public static final String PATH_SITE_ORDER_CREATE_DATA_LABEL = "site/orderCreateDataLabel";
    /**
     * Order list
     */
    public static final String PATH_SITE_ORDER_LIST = "site/orderList";
    /**
     * Sign up
     */
    public static final String PATH_SITE_SIGN_UP = "site/signup";
    /**
     * Sign up data label
     */
    public static final String PATH_SITE_SIGN_UP_DATA_LABEL = "site/signupDataLabel";
    /**
     * Sign up get District
     */
    public static final String PATH_SITE_SIGN_UP_GET_DISTRICT = "site/signupGetDistrict";
    /**
     * Sign up get Ward
     */
    public static final String PATH_SITE_SIGN_UP_GET_WARD = "site/signupGetWard";
    /**
     * Uphold create
     */
    public static final String PATH_SITE_UPHOLD_CREATE = "site/upholdCreate";
    /**
     * Uphold list
     */
    public static final String PATH_SITE_UPHOLD_LIST = "site/upholdList";
    /**
     * Uphold reply
     */
    public static final String PATH_SITE_UPHOLD_REPLY = "site/upholdReply";
    public static final String PATH_SITE_BOMOI_EMPLOYEE_CASHBOOK_CREATE = "boMoi/employeeCashbookCreate";
    public static final String PATH_SITE_BOMOI_EMPLOYEE_CASHBOOK_UPDATE = "boMoi/employeeCashbookUpdate";
    public static final String PATH_STORE_CARD_CREATE = "boMoi/storecardCreate";
    public static final String PATH_STORE_CARD_UPDATE = "boMoi/storecardUpdate";
    public static final String PATH_BOMOI_SET_DEBIT = "boMoi/boMoiSetDebit";
    public static final String PATH_BOMOI_DRIVER_UPDATE = "boMoi/boMoiDriverUpdate";
    public static final String PATH_BOMOI_SET_EVENT = "boMoi/boMoiSetEvent";
    public static final String PATH_SITE_ORDER_TRANSACTION_SET_EVENT = "order/transactionSetEvent";

    /**
     * Uphold view
     */
    public static final String PATH_SITE_UPHOLD_VIEW = "site/upholdView";
    /**
     * Uphold reply
     */
    public static final String PATH_USER_WORKKING_REPORT_LIST = "user/workingReportList";
    /**
     * Uphold reply
     */
    public static final String PATH_USER_WORKKING_REPORT_CREATE = "user/workingReportCreate";
    /**
     * Uphold reply
     */
    public static final String PATH_USER_WORKKING_REPORT_VIEW = "user/workingReportView";
    /**
     * Uphold Customer rating
     */
    public static final String PATH_SITE_UPHOLD_CUSTOMER_RATING = "site/upholdCustomerRating";
    /**
     * Change password
     */
    public static final String PATH_USER_CHANGE_PASS = "user/changePass";
    /**
     * User profile
     */
    public static final String PATH_USER_PROFILE = "user/profile";
    /** Request list streets*/
    public static final String PATH_LIST_STREETS                 = "default/listStreets";
    /** Path to connect with PHP server */
    public static final String PATH_USER_CHANGE_PROFILE          = "user/update";
    //++ BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
    /** Path to connect with PHP server */
    public static final String PATH_GET_STATISTICS               = "report/getStatistic";
    /** Path to connect with PHP server */
    public static final String PATH_LIST_RECEIPTS                = "report/listReceipts";
    //-- BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
    /** -----List of keys----- */
    /**
     * Token
     */
    public static final String KEY_TOKEN = "token";
    public static final String KEY_PLATFORM = "platform";
    public static final String KEY_LIST_ID_IMAGE = "list_id_image";

    /**
     * Keyword
     */
    public static final String KEY_KEYWORD = "keyword";
    /**
     * Q
     */
    public static final String KEY_Q = "q";
    /**
     * Notify Id
     */
    public static final String KEY_NOTIFY_ID = "notify_id";
    /**
     * Type
     */
    public static final String KEY_TYPE = "type";
    /**
     * Object Id
     */
    public static final String KEY_OBJECT_ID = "obj_id";
    /**
     * Issue Id
     */
    public static final String KEY_ISSUE_ID = "issue_id";
    /**
     * Customer Id
     */
    public static final String KEY_CUSTOMER_ID = "customer_id";
    /**
     * Title
     */
    public static final String KEY_TITLE = "title";
    /**
     * Problem
     */
    public static final String KEY_PROBLEM = "problem";
    /**
     * Page
     */
    public static final String KEY_PAGE = "page";
    /**
     * From date
     */
    public static final String KEY_DATE_FROM    = "date_from";
    /**
     * To date
     */
    public static final String KEY_DATE_TO      = "date_to";
    /**
     * Date
     */
    public static final String KEY_DATE         = "date";
    /**
     * Buying
     */
    public static final String KEY_BUYING = "buying";
    /**
     * Chief monitor id
     */
    public static final String KEY_CHIEF_MONITOR_ID = "chief_monitor_id";
    /**
     * Monitor agent id
     */
    public static final String KEY_MONITOR_AGENT_ID = "monitor_agent_id";
    /**
     * Accounting id
     */
    public static final String KEY_ACCOUNTING_ID = "accounting_id";
    /**
     * gas24h
     */
    public static final String KEY_APP_TYPE = "app_type";
    /**
     * Username
     */
    public static final String KEY_USERNAME = "username";
    /**
     * Password
     */
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_OTP_CODE = "otp_code";
    /**
     * GCM device token
     */
    public static final String KEY_GCM_DEVICE_TOKEN = "gcm_device_token";
    /**
     * APNS device token
     */
    public static final String KEY_APNS_DEVICE_TOKEN = "apns_device_token";
    /**
     * News Id
     */
    public static final String KEY_NEWS_ID = "news_id";
    /**
     * Id
     */
    public static final String KEY_ID = "id";
    /** Gender */
    public static final String KEY_GENDER                        = "gender";
    /** Start date */
    public static final String KEY_START_DATE                    = "start_date";
    /** End date */
    public static final String KEY_END_DATE                      = "end_date";
    /** Diagnosis */
    public static final String KEY_DIAGNOSIS                     = "diagnosis";
    /** Diagnosis id */
    public static final String KEY_DIAGNOSIS_ID                  = "diagnosis_id";
    /** Diagnosis other id */
    public static final String KEY_DIAGNOSIS_OTHER_ID            = "diagnosis_other_id";
    /** Teeth */
    public static final String KEY_TEETH                         = "teeth";
    /** Teeth */
    public static final String KEY_TEETH_ID                      = "teeth_id";
    /** List */
    public static final String KEY_LIST                          = "list";
    public static final String KEY_PATHOLOGICAL                  = "pathological";
    public static final String KEY_PATHOLOGICAL_ID               = "pathological_id";
    public static final String KEY_HEALTHY                       = "healthy";
    public static final String KEY_STATUS_TREATMENT              = "status_treatment";
    public static final String KEY_ADDRESS_CONFIG                = "address_config";
    public static final String KEY_TREATMENT                     = "treatment";
    public static final String KEY_TIME                          = "time";
    public static final String KEY_DOCTOR_ID                     = "doctor_id";
    public static final String KEY_TREATMENT_TYPE_ID             = "treatment_type_id";
    public static final String KEY_TIMER                         = "timer";
    public static final String KEY_TEETH_INFO                    = "teeth_info";
    /** Schedule id */
    public static final String KEY_SCHEDULE_ID                   = "schedule_id";
    /**
     * Note Customer
     */
    public static final String KEY_NOTE_CUSTOMER = "note_customer";
    /**
     * total
     */
    //++ BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
    public static final String KEY_TOTAL = "total";

    /**
     * total quantity
     */
    public static final String KEY_TOTAL_QTY = "total_qty";
    //-- BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
    /**
     * qty_12
     */
    public static final String KEY_QTY_12 = "qty_12";
    /**
     * qty_50
     */
    public static final String KEY_QTY_50 = "qty_50";
    /**
     * qty_12_list
     */
    public static final String KEY_QTY_12_LIST = "qty_12_list";
    /**
     * qty_50_list
     */
    public static final String KEY_QTY_50_LIST = "qty_50_list";
    /**
     * Phone
     */
    public static final String KEY_PHONE = "phone";
    /**
     * Password
     */
    public static final String KEY_PASSWOaRD_CONFIRM = "password_confirm";
    /**
     * First name
     */
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_ACTION_TYPE = "action_type";
    public static final String KEY_CHANGE_TYPE = "change_type";
    public static final String KEY_STATUS_CANCEL = "status_cancel";
    public static final String KEY_TRANSACTION_HISTORY_ID = "transaction_history_id";
    public static final String KEY_RATING = "rating";
    public static final String KEY_RATING_COMMENT = "rating_comment";
    public static final String KEY_CONFIRM_CODE = "confirm_code";
    public static final String KEY_MASTER_LOOKUP_ID = "master_lookup_id";
    public static final String KEY_DATE_INPUT = "date_input";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_APP_ORDER_ID = "app_order_id";
    public static final String KEY_PTTT_CODE = "pttt_code";
    public static final String KEY_INPUT_PROMOTION_AMOUNT = "input_promotion_amount";

    /**
     * First name
     */
    public static final String KEY_EMAIL = "email";
    /**
     * Province Id
     */
    public static final String KEY_PROVINCE_ID = "province_id";
    /**
     * District Id
     */
    public static final String KEY_DISTRICT_ID = "district_id";
    /**
     * Ward Id
     */
    public static final String KEY_WARD_ID = "ward_id";
    /**
     * Street
     */
    public static final String KEY_STREET_ID = "street_id";
    /**
     * House Number
     */
    public static final String KEY_HOUSE_NUMBER = "house_numbers";
    /**
     * Sign up code
     */
    public static final String KEY_SIGN_UP_CODE = "signup_code";
    /**
     * Serial
     */
    public static final String KEY_SERIAL = "serial";
    /**
     * Sign up code
     */
    public static final String KEY_BRAND = "hgd_thuong_hieu";
    /**
     * Sign up code
     */
    public static final String KEY_ENEMY = "hgd_doi_thu";
    /**
     * Sign up code
     */
    public static final String KEY_FREQUENTLY = "hgd_time_use";
    /**
     * Sign up code
     */
    public static final String KEY_HGD_TYPE = "hgd_type";
    /**
     * Latitude
     */
    public static final String KEY_LATITUDE = "latitude";
    /**
     * Version code
     */
    public static final String KEY_VERSION_CODE = "version_code";
    /**
     * Longitude
     */
    public static final String KEY_LONGITUDE = "longitude";
    /**
     * Employee Id
     */
    public static final String KEY_EMPLOYEE_ID = "employee_id";
    /**
     * Uphold type
     */
    public static final String KEY_UPHOLD_TYPE = "type_uphold";
    /**
     * Content
     */
    public static final String KEY_CONTENT = "content";
    /**
     * Contact person
     */
    public static final String KEY_CONTACT_PERSON = "contact_person";
    /**
     * Contact telephone number
     */
    public static final String KEY_CONTACT_TEL = "contact_tel";
    /**
     * Status
     */
    public static final String KEY_STATUS = "status";
    /**
     * Message
     */
    public static final String KEY_MESSAGE = "message";
    /**
     * Uphold Id
     */
    public static final String KEY_UPHOLD_ID = "uphold_id";
    /**
     * Hours handle
     */
    public static final String KEY_HOURS_HANDLE = "hours_handle";
    /**
     * Note
     */
    public static final String KEY_NOTE = "note";
    /**
     * Contact phone
     */
    public static final String KEY_CONTACT_PHONE = "contact_phone";
    /**
     * Report wrong
     */
    public static final String KEY_REPORT_WRONG = "report_wrong";
    /**
     * Note internal
     */
    public static final String KEY_NOTE_INTERNAL = "note_internal";
    /**
     * Reply Id
     */
    public static final String KEY_REPLY_ID = "reply_id";
    /**
     * Old password
     */
    public static final String KEY_OLD_PASSWORD = "old_password";
    /**
     * New password
     */
    public static final String KEY_NEW_PASSWORD = "new_password";
    /**
     * New password confirm
     */
    public static final String KEY_NEW_PASSWORD_CONFIRM = "new_password_confirm";
    /**
     * Code
     */
    public static final String KEY_CODE = "code";
    /**
     * Record
     */
    public static final String KEY_RECORD = "record";
    /**
     * Record number
     */
    public static final String KEY_RECORD_NUMBER = "record_number";
    /**
     * Medical history
     */
    public static final String KEY_MEDICAL_HISTORY = "medical_history";
    /**
     * Record
     */
    public static final String KEY_HGD_SHORT_REPORT = "hgd_short_report";
    /**
     * Notify count text
     */
    public static final String KEY_NOTIFY_COUNT_TEXT = "NotifyCountText";
    /**
     * Notify count text
     */
    public static final String KEY_NOTIFY_VERSION_CODE = "app_version_code";
    /**
     * Issue create
     */
    public static final String KEY_ISSUE_CREATE = "issue_create";
    /**
     * Total page
     */
    public static final String KEY_TOTAL_PAGE = "total_page";
    /**
     * Total record
     */
    public static final String KEY_TOTAL_RECORD = "total_record";
    /**
     * LIST_CHIEF_MONITOR
     */
    public static final String KEY_LIST_CHIEF_MONITOR = "LIST_CHIEF_MONITOR";
    /**
     * LIST_MONITOR_AGENT
     */
    public static final String KEY_LIST_MONITOR_AGENT = "LIST_MONITOR_AGENT";
    /**
     * LIST_ACCOUNTING
     */
    public static final String KEY_LIST_ACCOUNTING = "LIST_ACCOUNTING";
    /**
     * Model issue
     */
    public static final String KEY_MODEL_ISSUE = "model_issue";
    /**
     * Model record
     */
    public static final String KEY_MODEL_RECORD = "model_record";
    /**
     * Menu
     */
    public static final String KEY_MENU                 = "menu";
    /**
     * Data uphold
     */
    public static final String KEY_DATA_UPHOLD          = "data_uphold";
    /**
     * Maximum upload size
     */
    public static final String KEY_MAX_UPLOAD           = "max_upload";
    /**
     * Data issue
     */
    public static final String KEY_DATA_ISSUE           = "data_issue";
    /**
     * Data
     */
    public static final String KEY_DATA                 = "data";
    /**
     * Role id
     */
    public static final String KEY_ROLE_ID              = "role_id";
    /**
     * User id
     */
    public static final String KEY_USER_ID              = "user_id";
    /**
     * User information
     */
    public static final String KEY_USER_INFO = "user_info";
    /**
     * Check menu
     */
    public static final String KEY_CHECK_MENU = "check_menu";
    /**
     * Province list
     */
    public static final String KEY_PROVINCE_LIST = "province_list";
    /**
     * District list
     */
    public static final String KEY_DISTRICT_LIST = "district_list";
    /**
     * Ward list
     */
    public static final String KEY_WARD_LIST = "ward_list";
    /**
     * Detail id
     */
    public static final String KEY_DETAIL_ID = "detail_id";
    /**
     * Model uphold
     */
    public static final String KEY_MODEL_UPHOLD = "model_uphold";
    /**
     * Address
     */
    public static final String KEY_ADDRESS                      = "address";
    /** Key birthday */
    public static final String KEY_BIRTHDAY                     = "birthday";
    /** Key birthday */
    public static final String KEY_BODY                         = "body";
    /** Key birthday */
    public static final String KEY_CATEGORY                     = "category";
    /** Key birthday */
    public static final String KEY_OBJECT_ID_NEW                = "object_id";
    /**
     * Image avatar
     */
    public static final String KEY_IMG_AVATAR = "image_avatar";
    /**
     * Notify type
     */
    public static final String KEY_NOTIFY_TYPE = "notify_type";
    /**
     * Request type
     */
    public static final String KEY_REQUEST_TYPE = "request_by";
    /**
     * Request type
     */
    public static final String KEY_RATING_STATUS = "rating_status";
    /**
     * Request type
     */
    public static final String KEY_RATING_TYPE = "rating_type";
    /**
     * Request type
     */
    public static final String KEY_RATING_NOTE = "rating_note";
    /**
     * Other information
     */
    public static final String KEY_OTHER_INFO = "OtherInfo";
    /**
     * Uphold last id
     */
    public static final String KEY_UPHOLD_ID_LASTEST = "uphold_id_lastest";
    /**
     * Need change pass
     */
    public static final String KEY_NEED_CHANGE_PASS = "need_change_pass";
    /**
     * Detail reply id
     */
    public static final String KEY_DETAIL_REPLY_ID = "detail_reply_id";
    /**
     * Uphold create
     */
    public static final String KEY_UPHOLD_CREATE = "uphold_create";
    /**
     * List street for register customer
     */
    public static final String KEY_LIST_STREET = "list_street";
    // List shell
    public static final String KEY_LIST_SHELL = "material_vo";
    // List order type
    public static final String KEY_LIST_ORDER_TYPE = "order_list_type";
    public static final String KEY_LIST_ORDER_DISCOUNT_TYPE = "order_list_discount_type";
    public static final String KEY_LIST_SUPPORT_EMPLOYEE = "list_support_employee";
    // List order status
    public static final String KEY_ORDER_CANCEL_STATUS = "order_status_cancel";
    public static final String KEY_STORE_CARD_CANCEL_STATUS = "storecard_status_cancel";

    public static final String KEY_TIME_PUT_LOCATION = "time_put_location";
    public static final String KEY_TIME_CHECK_ORDER = "gas24h_time_check_order";
    public static final String KEY_PROMOTION_POPUP_ID = "KEY_PROMOTION_POPUP_ID";


    // List order status
    public static final String KEY_ORDER_TYPE = "order_type";
    // List order status
    public static final String KEY_DISCOUNT_TYPE = "discount_type";
    // List order status
    //++ BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
    public static final String KEY_DISCOUNT_AMOUNT      = "discount_amount";
    //-- BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
    public static final String KEY_DISCOUNT             = "discount";
    public static final String KEY_FINAL                = "final";
    //++ BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
    public static final String KEY_DEBT                 = "debt";
    //-- BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
    public static final String KEY_CUSTOMER_CONFIRM     = "customer_confirm";
    public static final String KEY_RECEIPTIONIST_ID     = "receiptionist_id";
    public static final String KEY_CHANGE_TO_AGENT = "change_to_agent";
    // List order status
    public static final String KEY_TYPE_AMOUNT = "type_amount";
    public static final String KEY_HOT_LINE = "hotline";
    public static final String KEY_CALL_CENTER = "call_center_uphold";
    public static final String KEY_MY_INVITE_CODE = "my_invite_code";
    public static final String KEY_LIMIT_FAVORITE = "limit_favorite";
    public static final String KEY_MATERIAL_HGD = "material_hgd";
    public static final String KEY_STORE_CARD_TYPE_INOUT = "type_in_out";
    public static final String KEY_SUPPORT_ID = "support_id";
    public static final String KEY_DATE_DELIVERY = "date_delivery";
    public static final String KEY_ORDER_DETAIL = "order_detail";
    public static final String KEY_RESIZE_WIDTH = "resize_width";
    public static final String KEY_RESIZE_HEIGHT = "resize_height";
    public static final String KEY_CUSTOMER_CHAIN_STORE = "customer_chain_store";
    public static final String KEY_GAS24H_MENU_TEXT = "gas24h_menu_text";
    //++
    public static final String KEY_IMAGE = "image";
    //--

    public interface GAS_REMAIN_TYPE {
        String KEY = "gas_remain_type";
        int VALUE_CREATE_NORMAL = 1;
        int VALUE_CREATE_THU_KHO = 2;
    }

    public interface GAS_REMAIN_CAR {
        String KEY = "car_id";
        String UPDATE_CONFIG_KEY = "gas_remain_car";
    }

    /**
     * List enemies
     */
    public static final String KEY_LIST_ENEMY = "list_enemy";
    /**
     * List brands
     */
    public static final String KEY_LIST_BRAND = "list_brand";
    /**
     * List street for register customer
     */
    public static final String KEY_LIST_AGENT = "list_agent";
    /**
     * List street for register customer
     */
    public static final String KEY_AGENT_LIST = "agent_list";
    /**
     * Agent id
     */
    public static final String KEY_AGENT_ID = "agent_id";
    public static final String KEY_AGENT_NAME = "agent_name";
    public static final String KEY_AGENT = "agent";
    public static final String KEY_NAME = "name";
    public static final String KEY_CITY_ID = "city_id";
    /** Description */
    public static final String KEY_DESCRIPTION                   = "description";
    /** Age */
    public static final String KEY_AGE                           = "age";
    /** Career */
    public static final String KEY_CAREER                        = "career";
    /** Characteristics */
    public static final String KEY_CHARACTERISTICS               = "characteristics";
    /**
     * List hgd type
     */
    public static final String KEY_LIST_HGD_TYPE = "list_hgd_type";
    /**
     * List hgd invest
     */
    public static final String KEY_LIST_HGD_INVEST = "list_hgd_invest";
    /**
     * List hgd invest
     */
    public static final String KEY_NEED_UPDATE_PROFILE = "update_profile";
    public static final String KEY_CODE_COMPLETE = "code_complete";

    // For store transaction start
    public static final String KEY_STORE_TRANSACTION_START = "transaction start";
    public static final String KEY_STORE_ORDER_DATA = "order_data";
    public static final String KEY_MATERIAL_GAS = "material_gas";

    //++
    public static final String KEY_QR = "qr";
    //--

    /** -----Specified constant----- */
    /**
     * File parameter: file_name[ + ]
     */
    public static final String FILE_PARAM_FILE_NAME = "file_name[";
    public static final String FILE_PARAM_FILE_NAME_CLOSE = "]";
    /**
     * Character encoding: UTF-8
     */
    public static final String CHARACTER_ENCODING_UTF8 = "UTF-8";
    /**
     * Http request header: Content-Type
     */
    public static final String HTTP_REQ_HEADER = "Content-Type";
    /**
     * Http request header: application
     */
    public static final String HTTP_REQ_HEADER_CONTENT = "application/x-www-form-urlencoded";
    /**
     * Specified constant
     */
    public static final String MSG_PRESS_OK = "Press OK";
    /**
     * Specified constant
     */
    public static final String MSG_PRESS_REOPEN = "Press reopen";
    /**
     * Specified constant
     */
    public static final String MSG_DEVICE_NOT_SUPPORT = "This device is not supported.";
    /**
     * Specified constant
     */
    public static final String MSG_NOT_RECEIVE_TOKEN = "Không thể nhận được device token!";
    /**
     * Specified constant
     */
    public static final String MSG_RECEIVE_TOKEN = "Đã nhận được device token!!";
    /**
     * Request method: POST
     */
    public static final int REQUEST_POST = 1;
    /**
     * Request method: GET
     */
    public static final int REQUEST_GET = 2;
    /**
     * Request method: PUT
     */
    public static final int REQUEST_PUT = 3;
    /**
     * Request method: DELETE
     */
    public static final int REQUEST_DELETE = 4;
    /**
     * Certificate exception
     */
    public static final String CERTIFICATE_NOT_VALID_OR_TRUSTED = "Certificate not valid or trusted.";
    /**
     * Http scheme
     */
    public static final String SCHEME_HTTP = "http";
    /**
     * Https scheme
     */
    public static final String SCHEME_HTTPS = "https";
    /**
     * Port: 80
     */
    public static final int PORT_80 = 80;
    /**
     * Port: 443
     */
    public static final int PORT_443 = 443;
    /**
     * Application mode: Running
     */
    public static final int MODE_RUNNING = 0;
    /**
     * Application mode: Training
     */
    public static final int MODE_TRAINING = 1;
    /**
     * Issue type: open
     */
    public static final int TYPE_OPEN = 1;
    /**
     * Issue type: close
     */
    public static final int TYPE_CLOSE = 2;
    /**
     * News type: new
     */
    public static final int TYPE_NEW = 1;
    /**
     * News type: view
     */
    public static final int TYPE_VIEW = 2;
    /**
     * Google play service resolution request
     */
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    /**
     * Maximum of click on Logo to show hidden Activity
     */
    public static final int MAX_CLICK_NUMBER = 7;
    /**
     * Result load image
     */
    public static final int RESULT_LOAD_IMG = 1000;
    /**
     * Result take picture
     */
    public static final int RESULT_TAKE_PICTURE = 1001;
    /**
     * Limit number of image can select 1 time
     */
    public static final int MAX_SELECTED_PICTURE = 6;
    /**
     * Touch image state: None
     */
    public static final int TOUCH_IMG_NONE = 0;
    /**
     * Touch image state: Dragging
     */
    public static final int TOUCH_IMG_DRAG = 1;
    /**
     * Touch image state: Zoom
     */
    public static final int TOUCH_IMG_ZOOM = 2;
    /**
     * Touch image state: Click
     */
    public static final int TOUCH_IMG_CLICK = 3;
    /**
     * Auto search interval
     */
    public static final int SEARCH_INTERVAL = 1000;
    /**
     * Minimum number of characters begin search
     */
    public static final int SEARCH_MIN_LENGTH = 3;
    /**
     * Pattern to remove accent
     */
    public static final String PATTERN_REMOVE_ACCENT = "\\p{InCombiningDiacriticalMarks}+";

    /**
     * Uphold type: Periodically
     */
    public static final String UPHOLD_TYPE_PERIODICALLY = "1";
    /**
     * Uphold type: Trouble
     */
    public static final String UPHOLD_TYPE_TROUBLE = "2";
    /**
     * Uphold type: Periodically
     */
    public static final int TYPE_PERIODICALLY = 1;
    /**
     * Uphold type: Trouble
     */
    public static final int TYPE_TROUBLE = 2;

    /**
     * Uphold status: Complete
     */
    public static final String UPHOLD_STATUS_COMPLETE = "3";
    /**
     * Uphold status: New
     */
    public static final String UPHOLD_STATUS_NEW = "1";
    /**
     * Uphold status: Handling
     */
    public static final String UPHOLD_STATUS_HANDLE = "2";
    /**
     * Uphold status: Other
     */
    public static final String UPHOLD_TYPE_OTHER = "6";
    /**
     * Uphold contact: Other
     */
    public static final String UPHOLD_CONTACT_OTHER = "4";
    /**
     * Role id: Customer
     */
    public static final String ROLE_CUSTOMER = "4";
    /**
     * Role id: Coordinator
     */
    public static final String ROLE_COORDINATOR = "17";
    /**
     * Role id: Audit
     */
    public static final String ROLE_AUDIT = "54";
    /**
     * Role id: Chief monitor
     */
    public static final String ROLE_CHIEF_MONITOR = "28";
    /**
     * Role id: Director
     */
    public static final String ROLE_DIRECTOR = "19";
    /**
     * Flag change pass: need change
     */
    public static final String NEED_CHANGE_PASS = "1";
    /**
     * Flag change pass: no need change
     */
    public static final String NO_NEED_CHANGE_PASS = "0";
    /**
     * Notify type: View Uphold
     */
    public static final String NOTIFY_VIEW_UPHOLD = "VIEW_UPHOLD";
    /**
     * Notify type: View Uphold
     */
    public static final String NOTIFY_VIEW_TRANSACTION = "VIEW_TRANSACTION";
    public static final String NOTIFY_ORDER_BO_MOI = "TYPE_ORDER_BO_MOI";
    public static final String BROWSER_REF_CODE = "BROWSER_REF_CODE";
    public static final String TYPE_UPHOLD_HGD = "TYPE_UPHOLD_HGD";
    public static final String TYPE_TICKET_EVENT = "TYPE_TICKET_EVENT";
    public static final String TYPE_SPJ_CODE_GOBACK = "TYPE_SPJ_CODE_GOBACK";

    /**
     * Notify type: View Issue
     */
    public static final String NOTIFY_VIEW_ISSUE = "VIEW_ISSUE";
    /**
     * Notify type: Uphold alert 10
     */
    public static final String NOTIFY_UPHOLD_ALERT_10 = "1";
    /**
     * Notify type: Uphold periodically
     */
    public static final String NOTIFY_UPHOLD_PERIODICALLY_1_DAY = "2";
    /**
     * Notify type: Issue ticket
     */
    public static final String NOTIFY_ISSUE_TICKET = "3";
    /**
     * Notify type: Uphold create
     */
    public static final String NOTIFY_UPHOLD_CREATE = "4";

    /** -----Log message----- */
    /**
     * Log tag: error.
     */
    public static final String LOG_TAG_ERROR = "harpy.error";
    /**
     * Log tag: warn.
     */
    public static final String LOG_TAG_WARN = "harpy.warn";
    /**
     * Log tag: info.
     */
    public static final String LOG_TAG_INFO = "harpy.info";
    /** Log tag: debug. */
    //public static final String LOG_TAG_DEBUG = "harpy.debug";
    /**
     * Log message.
     */
    public static final String LOG_MSG_HTTP_ACCESS_FAILED = "httpAccessPost fail:";
    /**
     * Log message.
     */
    public static final String LOG_MSG_GET_DATA_FROM_URL = "Get data from URL: ";

    /**
     * Blank character
     */
    public static final String BLANK = "";
    /**
     * Server URL (Training mode)
     */
    public static final String SERVER_URL_TRAINING  = "http://vietmy.immortal.vn/index.php/api/";
    /**
     * Server URL
     */
    public static final String SERVER_URL           = "http://nkvietmy.com/index.php/api/";

    /**
     * -----Define id of fragment-----
     */

    public static class MENU_ID_LIST {
        public static final String ORDER_LIST = "order_list";
        public static final String ORDER_CREATE = "order_create";
        public static final String UPHOLD_LIST = "uphold_list";
        public static final String UPHOLD_RATING = "uphold_rating";
        public static final String NEWS_LIST = "news_list";
        public static final String USER_PROFILE = "account";
        public static final String USER_PROFILE_EDIT = "Cập nhật tài khoản";
        //++ BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh
        public static final String USER_PROFILE_CHANGE_PASS = "Cập nhật mật khẩu";
        //-- BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh
        public static final String MAP_CHECKER = "map_checker";
        public static final String FAMILY_LIST = "customer_list";
        public static final String FAMILY_VIEW = "Thông tin khách hàng";
        public static final String FAMILY_CREATE = "Tạo mới khách hàng";
        public static final String FAMILY_UPDATE = "Cập nhật khách hàng";
        public static final String AGENT_LIST = "bomoi_list";
        public static final String GAS_REMAIN_LIST = "gasremain_list";
        public static final String GAS_REMAIN_CREATE = "Tạo gas dư";
        public static final String GAS_REMAIN_UPDATE = "Cập nhật gas dư";
        public static final String UPHOLD_HGD_LIST = "uphold_hgd_list";
        public static final String STORE_CARD_LIST = "store_card_list";
        public static final String SUPPORT_TICKET_LIST = "ticket_list";
        public static final String PTTT_CODE_LIST = "pttt_code_list";
        public static final String PAGE_PROMOTION = "id_page_promotion";
        public static final String PAGE_GUIDE = "id_page_guide";
        public static final String CASH_BOOK_LIST = "cashbook_list";
        public static final String CASH_BOOK_SCHEDULE_LIST = "cashbook_schedule";
        public static final String CASH_BOOK_CREATE = "Tạo thu chi";
        public static final String CASH_BOOK_UPDATE = "Cập nhật thu chi";
        public static final String CASH_BOOK_VIEW = "Thông tin thu chi";
        public static final String AGENT_VIEW = "Chi tiết đơn hàng khách hàng";
        public static final String UPHOLD_HGD_VIEW = "Chi tiết bảo trì HGD";
        public static final String SUPPORT_TICKET_VIEW = "Chi tiết hỗ trợ";
        public static final String AGENT_CREATE = "Đặt Gas";
        public static final String STORE_CARD_VIEW = "Chi tiết thẻ kho";
        public static final String STORE_CARD_CREATE = "Tạo thẻ kho";
        public static final String SUPPORT_TICKET_CREATE = "Tạo yêu cầu hỗ trợ";
        public static final String WORING_REPORT_LIST = "working_report_list";
        public static final String WORING_REPORT_VIEW = "Xem báo cáo công việc";
        public static final String CUSTOMER_ORDER_VIEW = "Đơn hàng";
        public static final String CUSTOMER_REFERRAL = "referral";
        public static final String CUSTOMER_ORDER_CHANGE_AGENT = "Chuyển đại lý";
        public static final String WORING_REPORT_CREATE = "Tạo báo cáo công việc";
        public static final String HOME = "home";
        public static final String HOME_CUSTOMER = "Gas24h";
        public static final String CUSTOMER_DISCOUNT_LIST = "promotion_list";
        public static final String EMPLOYEE_TRANSACTION_LIST = "transaction_list";
        public static final String CUSTOMER_CONTACT_NOW = "Liên hệ ngay";
        public static final String ISSUE_LIST = "issue_list";
        public static final String ISSUE_VIEW = "Chi tiết phản ánh";
        public static final String NEWS_VIEW = "Chi tiết tin tức";
        public static final String UPHOLD_VIEW = "Chi tiết bảo trì";
        public static final String ISSUE_REPLY = "Trả lời phản ánh";
        public static final String UPHOLD_REPLY = "Trả lời bảo trì";
        public static final String USER_CHANGE_PASS = "Đổi mật khẩu";
        public static final String UPHOLD_CREATE = "Yêu cầu bảo trì";
        public static final String REPORT_LIST = "report";
        public static final String GOOGLE_MAP = "google_map";
        public static final String REPORT_HGD = "Báo cáo hộ gia đình";
        public static final String REPORT_INVENTORY = "Báo cáo vật tư";
        public static final String REPORT_CASHBOOK = "Báo cáo quỹ tiền mặt";
        public static final String LOGOUT = "logout";
        public static final String LOGIN = "login";
        public static final String MESSAGE = "message";
        public static final String KEY_MESSAGE = "message";
        public static final String CUSTOMER_LIST = "customer_list";
        public static final String CONFIGURATION = "configuration";
        //++ BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
        public static final String REPORT_REVENUE = "report_revenue";
        public static final String COLLECTED_REVENUE = "Danh sách đã thu";
        public static final String NO_COLLECTED_REVENUE = "Danh sách chưa thu";
        public static final String STATISTIC = "Thống kê";
        //-- BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
        //++
        public static final String QRCODE = "Danh sách QR Code";
        //--
    }

    public static class WEB_PAGE_ID {
        public static final long PAGE_PROMOTION = 3;
        public static final long PAGE_GUIDE = 4;
    }

    /**
     * Default zoom when we see map view
     */
    public static final float DEFAULT_MAP_ZOOM = 13;

    /**
     * Define max upload width/height
     */
    public static final int MAX_UPLOAD_FILE_HEIGHT = 1400;
    public static final int MAX_UPLOAD_FILE_WIDTH = 1600;
    public static final long MAX_UPLOAD_FILE_SIZE = 512 * 1024; //KB

    /**
     * Layout level
     */
    public static final int LAYOUT_LEVEL_1 = 1;
    public static final int LAYOUT_LEVEL_2 = 2;

    // For delivery item
    public static final int EMPLOYEE_FREE = 0;// new chưa có nhân viên nào nhận
    public static final int EMPLOYEE_NHAN_GIAO_HANG = 1;// nhận đơn hàng
    public static final int EMPLOYEE_HUY_GIAO_HANG = 2;// HỦy nhận đơn hàng
    public static final int EMPLOYEE_DROP = 3;// Hủy không lấy gas
    public static final int EMPLOYEE_CHANGE = 4;// change đơn hàng gas
    public static final int EMPLOYEE_COMPLETE = 5;// done thu tiền
    public static final int EMPLOYEE_CHANGE_AGENT = -2;// change agent

    // Material Type
    public static final String MATERIAL_VO_12 = "1";
    public static final String MATERIAL_TYPE_DAY = "2";
    public static final String MATERIAL_TYPE_VAL = "3";
    public static final String MATERIAL_BINH_12KG = "4";
    public static final String MATERIAL_TYPE_BEP = "5";
    public static final String MATERIAL_TYPE_PROMOTION = "6";
    public static final String MATERIAL_BINH_45KG = "7";
    public static final String MATERIAL_BINH_6KG = "9";
    public static final String MATERIAL_VO_45 = "10";
    public static final String MATERIAL_BINH_50KG = "11";
    public static final String MATERIAL_VO_50 = "12";
    public static final String MATERIAL_GAS_BON = "13";
    public static final String MATERIAL_VO_6 = "14";
    public static final String MATERIAL_BINH_4KG = "19";

    // Change type
    public static final String EMPLOYEE_CHANGE_NOTHING = "0";// change GAs
    public static final String EMPLOYEE_CHANGE_GAS = "1";// change GAs
    public static final String EMPLOYEE_CHANGE_GIFT = "2";// change quà
    public static final String EMPLOYEE_CHANGE_VO = "3";// vỏ

    // Time format
    public static final String FORMAT_CREATED_DATE = "dd/MM/yyyy HH:mm";// vỏ

    // For status number
    public static final String ORDER_NEW = "1";
    public static final String ORDER_PROCESSING = "3";
    public static final String ORDER_COMPLETE = "4";
    public static final String ORDER_CANCEL = "5";

    // For agent booking
    public static final int MAX_AGENT_BOOKING_AMOUNT = 9999;


    // For employee HGD
    public static final int STATUS_NEW = 1;
    public static final int STATUS_CONFIRM = 2;
    public static final int STATUS_UNCONFIRM = 3;
    public static final int STATUS_CANCEL = 4;
    public static final int STATUS_COMPLETE = 5;
    public static final String KEY_REFERRAL_FROM_GOOGLE = "KEY_REFERRAL_FROM_GOOGLE";

    public static String getMaterialTypeName(String key) {
        String typeName = "";
        if (MATERIAL_VO_6.equals(key)) {
            typeName = "Vỏ 6";
        } else if (MATERIAL_VO_12.equals(key)) {
            typeName = "Vỏ 12";
        } else if (MATERIAL_VO_45.equals(key)) {
            typeName = "Vỏ 45";
        } else if (MATERIAL_VO_50.equals(key)) {
            typeName = "Vỏ 50";
        } else if (MATERIAL_BINH_4KG.equals(key)) {
            typeName = "Bình 4";
        } else if (MATERIAL_BINH_6KG.equals(key)) {
            typeName = "Bình 6";
        } else if (MATERIAL_BINH_12KG.equals(key)) {
            typeName = "Bình 12";
        } else if (MATERIAL_BINH_45KG.equals(key)) {
            typeName = "Bình 45";
        } else if (MATERIAL_BINH_50KG.equals(key)) {
            typeName = "Bình 50";
        } else if (MATERIAL_GAS_BON.equals(key)) {
            typeName = "Gas bồn";
        } else if (MATERIAL_TYPE_DAY.equals(key)) {
            typeName = "Dây";
        } else if (MATERIAL_TYPE_VAL.equals(key)) {
            typeName = "Van";
        } else if (MATERIAL_TYPE_BEP.equals(key)) {
            typeName = "Bếp";
        } else if (MATERIAL_TYPE_PROMOTION.equals(key)) {
            typeName = "Khuyến mãi";
        }
        return typeName;
    }

    public static class REPORT_TYPE {
        public static final String REPORT_INVENTORY = "report_inventory";
        public static final String REPORT_HGD = "report_hgd";
        public static final String REPORT_CASHBOOK = "report_cashbook";
    }

    public static final String ID_MASTER_LOOKUP_CHI_GAS_DU = "24";
    public static final String ID_MASTER_LOOKUP_PO_MOI_THU = "43";

    public static class TICKET_TYPE {
        public static final String CUSTOMER_ORDER = "Đơn hàng HGD";
        public static final String STORE_CARD = "Thẻ kho";
        public static final String AGENT = "Bò mối";
        public static final String UPHOLD_HGD = "Bảo trì HGD";
        public static final String CASHBOOK = "Quỹ tiền mặt";
        public static final String GAS_REMAIN = "Gas dư";
    }

    public static class PREF_TYPE {
        public static final String LAST_LOGIN_USERNAME = "LAST_LOGIN_USERNAME";
        public static final String FIRST_ORDERED = "FIRST_ORDERED";
        public static final String IS_INVITED_USER = "g24_can_input_invite_code";
    }

    /** Date time format */
    public static final String DATE_TIME_FORMAT_1                    = "dd/MM/yyyy";
    /** Date time format */
    public static final String DATE_TIME_FORMAT_2                    = "yyyy/MM/dd";
    /** Group id */
    public static final String GROUP_MEDICAL_RECORD              = "1";
    public static final String GROUP_TREATMENT                   = "2";
    /** Item id */
    public static final String ITEM_UPDATE_DATA                  = "0";
    public static final String ITEM_NAME                         = "1";
    public static final String ITEM_BIRTHDAY                     = "2";
    public static final String ITEM_MEDICAL_HISTORY              = "3";
    public static final String ITEM_GENDER                       = "4";
    public static final String ITEM_AGE                          = "5";
    public static final String ITEM_PHONE                        = "6";
    public static final String ITEM_ADDRESS                      = "7";
    public static final String ITEM_EMAIL                        = "8";
    public static final String ITEM_AGENT                        = "9";
    public static final String ITEM_CAREER                       = "10";
    public static final String ITEM_CHARACTERISTICS              = "11";
    public static final String ITEM_RECORD_NUMBER                = "12";
    public static final String ITEM_START_DATE                   = "13";
    public static final String ITEM_END_DATE                     = "14";
    public static final String ITEM_DIAGNOSIS                    = "15";
    public static final String ITEM_PATHOLOGICAL                 = "16";
    public static final String ITEM_DOCTOR                       = "17";
    public static final String ITEM_HEALTHY                      = "18";
    public static final String ITEM_STATUS                       = "19";
    public static final String ITEM_DETAILS                      = "20";
    public static final String ITEM_TEETH                        = "21";
    public static final String ITEM_TREATMENT                    = "22";
    public static final String ITEM_NOTE                         = "23";
    public static final String ITEM_TYPE                         = "24";
    public static final String ITEM_CAN_UPDATE                   = "25";
    public static final String ITEM_ID                           = "26";
    public static final String ITEM_DIAGNOSIS_ID                 = "27";
    public static final String ITEM_PATHOLOGICAL_ID              = "28";
    public static final String ITEM_TEETH_ID                     = "29";
    public static final String ITEM_TREATMENT_TYPE_ID            = "30";
    public static final String ITEM_DESCRIPTION                  = "31";
    public static final String ITEM_TIME_ID                      = "32";
    public static final String ITEM_TIME                         = "33";
    /** Item id: Receipt */
    public static final String ITEM_RECEIPT                      = "34";
    /** Item id: Discount */
    public static final String ITEM_DISCOUNT                     = "35";
    /** Item id: Need approve */
    public static final String ITEM_NEED_APPROVE                 = "36";
    /** Item id: Customer confirmed */
    public static final String ITEM_CUSTOMER_CONFIRMED           = "37";
    /* Item id: Final */
    public static final String ITEM_FINAL                        = "38";
    /* Item id: Insurance */
    public static final String ITEM_INSURANCE                    = "39";
    /* Item id: Teeth information */
    public static final String ITEM_TEETH_INFO                   = "40";
    /* Item id: Customer debt */
    public static final String ITEM_CUSTOMER_DEBT                = "41";
    //++ BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
    /* Item id: Customer debt */
    public static final String ITEM_IMAGE                        = "42";
    /* Item id: Image */
    public static final String ITEM_IMAGE_TREATMENT              = "43";
    /* Item id: Image */
    public static final String ITEM_AGENT_ID                     = "44";
    /* Item id: Image */
    public static final String ITEM_PRICE                        = "45";
    /* Item id: Image */
    public static final String ITEM_QUANTITY                     = "46";
    /* Item id: Image */
    public static final String ITEM_TOTAL                        = "47";
    /* Item id: Image */
    public static final String ITEM_DEBT                         = "48";
    /* Item id: Image */
    public static final String ITEM_RECEIPTIONIST                = "49";
    //-- BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.

    public static final String ITEM_CREATE_NEW                   = "XXX";
    // MARK: Status
    public static final String TREATMENT_SCHEDULE_INACTIVE           = "0";
    public static final String TREATMENT_SCHEDULE_ACTIVE             = "1";
    public static final String TREATMENT_SCHEDULE_SCHEDULE           = "2";
    public static final String TREATMENT_SCHEDULE_COMPLETED          = "3";
    public static final String TREATMENT_SCHEDULE_DETAIL_INACTIVE    = "0";
    public static final String TREATMENT_SCHEDULE_DETAIL_ACTIVE      = "1";
    public static final String TREATMENT_SCHEDULE_DETAIL_COMPLETED   = "2";
    public static final String TREATMENT_SCHEDULE_DETAIL_SCHEDULE    = "3";
    public static final String TREATMENT_SCHEDULE_PROCESS_INACTIVE   = "0";
    public static final String TREATMENT_SCHEDULE_PROCESS_ACTIVE     = "1";
    public static final String TREATMENT_SCHEDULE_PROCESS_COMPLETED  = "2";

    public static final Map<String, Integer> VMD_IMG_LIST = new HashMap<String, Integer>() {
        {
            put(ITEM_UPDATE_DATA,       R.drawable.update_info);
            put(ITEM_NAME,              R.drawable.patient);
            put(ITEM_BIRTHDAY,          R.drawable.birthday);
            put(ITEM_MEDICAL_HISTORY,   R.drawable.medical_history_parent);
            put(ITEM_GENDER,            R.drawable.vmd_gender);
            put(ITEM_AGE,               R.drawable.age);
            put(ITEM_PHONE,             R.drawable.phone);
            put(ITEM_ADDRESS,           R.drawable.address);
            put(ITEM_EMAIL,             R.drawable.email);
            put(ITEM_AGENT,             R.drawable.agent);
            put(ITEM_CAREER,            R.drawable.update_info);
            put(ITEM_CHARACTERISTICS,   R.drawable.sum);
            put(ITEM_RECORD_NUMBER,     R.drawable.record_number);
            put(ITEM_START_DATE,        R.drawable.start_date);
            put(ITEM_END_DATE,          R.drawable.start_date);
            put(ITEM_DIAGNOSIS,         R.drawable.diagnosis);
            put(ITEM_PATHOLOGICAL,      R.drawable.sympton);
            put(ITEM_DOCTOR,            R.drawable.update_info);
            put(ITEM_HEALTHY,           R.drawable.healthy);
            put(ITEM_STATUS,            R.drawable.update_info);
            put(ITEM_DETAILS,           R.drawable.update_info);
            put(ITEM_TEETH,             R.drawable.teeth);
            put(ITEM_TREATMENT,         R.drawable.treatment);
            put(ITEM_NOTE,              R.drawable.describe);
            put(ITEM_TYPE,              R.drawable.type);
            put(ITEM_ID,                R.drawable.update_info);
            put(ITEM_DESCRIPTION,       R.drawable.work_detail);
            put(ITEM_TIME_ID,           R.drawable.time);
            put(ITEM_TIME,              R.drawable.time);
            put(ITEM_DISCOUNT,          R.drawable.discount);
            put(ITEM_FINAL,             R.drawable.actually_collected);
            put(ITEM_INSURANCE,         R.drawable.insurance);
            put(ITEM_TEETH_INFO,        R.drawable.teeth);
            put(ITEM_CUSTOMER_DEBT,     R.drawable.debt);
            put(ITEM_IMAGE,             R.drawable.xray);
        }
    };
    // MARK: -----Public static strings-----
    public static final String CONTENT00001 = "Harpy Framework";
    public static final String CONTENT00002 = "Chưa chọn khách hàng";
    public static final String CONTENT00003 = "Chưa chọn nguyên nhân!";
    public static final String CONTENT00004 = "Chưa nhập tiêu đề!";
    public static final String CONTENT00005 = "Chưa nhập nội dung!";
    public static final String CONTENT00006 = "Bạn chỉ được nhập tối đa %1$d hình!";
    public static final String CONTENT00007 = "Vui lòng kiểm tra lại dữ liệu!";
    public static final String CONTENT00008 = "OK";
    public static final String CONTENT00009 = "Cancel";
    public static final String CONTENT00010 = "Bạn chắc chắn muốn close Issue";
    public static final String CONTENT00011 = "Bạn chắc chắn muốn Reopen Issue";
    public static final String CONTENT00012 = "Mã số:";
    public static final String CONTENT00013 = "Khách hàng:";
    public static final String CONTENT00014 = "NV kinh doanh:";
    public static final String CONTENT00015 = "Địa chỉ:";
    public static final String CONTENT00016 = "Tiêu đề:";
    public static final String CONTENT00017 = "Người tạo:";
    public static final String CONTENT00018 = "Ngày tạo:";
    public static final String CONTENT00019 = "Trạng thái:";
    public static final String CONTENT00020 = "Nguyên nhân:";
    public static final String CONTENT00021 = "Điện thoại:";
    public static final String CONTENT00022 = "Người liên hệ:";
    public static final String CONTENT00023 = "Tài khoản và mật khẩu không được trống!";
    public static final String CONTENT00024 = "(%1$s)";
    public static final String CONTENT00025 = "Bạn vui lòng điền đầy đủ thông tin!";
    public static final String CONTENT00026 = "Mật khẩu nhập lại không chính xác!";
    public static final String CONTENT00027 = "Chưa chọn NV bảo trì!";
    public static final String CONTENT00028 = "Chưa chọn loại sự cố!";
    public static final String CONTENT00029 = "Chưa nhập người liên hệ!";
    public static final String CONTENT00030 = "Chưa nhập số điện thoại!";
    public static final String CONTENT00031 = "Nội dung: \n";
    public static final String CONTENT00032 = "Liên hệ:";
    public static final String CONTENT00033 = "Sự cố:";
    public static final String CONTENT00034 = "Mức độ:";
    public static final String CONTENT00035 = "Ngày bảo trì:";
    public static final String CONTENT00036 = "Lịch bảo trì:";
    public static final String CONTENT00037 = "Nhân viên bảo trì:";
    public static final String CONTENT00038 = "Số điện thoại:";
    public static final String CONTENT00039 = "Báo cáo kết quả: \n";
    public static final String CONTENT00040 = "Bảo trì định kỳ";
    public static final String CONTENT00041 = "Yêu cầu bảo trì";
    public static final String CONTENT00042 = "Thời gian xử lý:";
    public static final String CONTENT00043 = "Chưa chọn trạng thái!";
    public static final String CONTENT00044 = "Tin nhắn từ Gas Service";
    public static final String CONTENT00045 = "Nội dung:";
    public static final String CONTENT00046 = "Mất kết nối mạng, vui lòng thử lại!";
    public static final String CONTENT00047 = "Có lỗi xảy ra, vui lòng thử lại!";
    public static final String CONTENT00048 = "Có lỗi xảy ra";
    public static final String CONTENT00049 = "Tài khoản / Số điện thoại";
    public static final String CONTENT00050 = "Mật khẩu";
    public static final String CONTENT00051 = "Đăng nhập";
    public static final String CONTENT00052 = "Đăng ký";
    public static final String CONTENT00053 = "Welcome to gas app!";
    public static final String CONTENT00054 = "Số điện thoại";
    public static final String CONTENT00055 = "Họ và tên";
    public static final String CONTENT00056 = "Nhập lại mật khẩu";
    public static final String CONTENT00057 = "Số nhà";
    public static final String CONTENT00058 = "Tên đường";
    public static final String CONTENT00059 = "Title";
    public static final String CONTENT00060 = "Tìm kiếm KH, nhập tối thiểu 5 ký tự";
    public static final String CONTENT00061 = "Mã khách hàng";
    public static final String CONTENT00062 = "Tiêu đề";
    public static final String CONTENT00063 = "Nội dung";
    public static final String CONTENT00064 = "Thêm hình";
    public static final String CONTENT00065 = "Tạo mới";
    public static final String CONTENT00066 = "Xoá hình";
    public static final String CONTENT00067 = "Open";
    public static final String CONTENT00068 = "Close";
    public static final String CONTENT00069 = "Không có dữ liệu";
    public static final String CONTENT00070 = "Gửi trả lời";
    public static final String CONTENT00071 = "Lịch sử trả lời";
    public static final String CONTENT00072 = "Thông tin";
    public static final String CONTENT00073 = "Đóng lại";
    public static final String CONTENT00074 = "Mới";
    public static final String CONTENT00075 = "Đã xem";
    public static final String CONTENT00076 = "Người liên hệ";
    public static final String CONTENT00077 = "Bảo trì sự cố";
    public static final String CONTENT00078 = "Bảo trì định kỳ";
    public static final String CONTENT00079 = "Tên khách hàng";
    public static final String CONTENT00080 = "Báo sai sự cố";
    public static final String CONTENT00081 = "Ghi chú";
    public static final String CONTENT00082 = "Ghi chú nội bộ";
    public static final String CONTENT00083 = "Mật khẩu cũ";
    public static final String CONTENT00084 = "Mật khẩu mới";
    public static final String CONTENT00085 = "Xác nhận mật khẩu mới";
    public static final String CONTENT00086 = "Lưu";
    public static final String CONTENT00087 = "Họ tên";
    public static final String CONTENT00088 = "Địa chỉ";
    public static final String CONTENT00089 = "Đổi mật khẩu";
    public static final String CONTENT00090 = "Thoát";
    public static final String CONTENT00091 = "Mã";
    public static final String CONTENT00092 = "Trạng thái";
    public static final String CONTENT00093 = "Nguyên nhân";
    public static final String CONTENT00094 = "Nhân viên sale";
    public static final String CONTENT00095 = "Người tạo";
    public static final String CONTENT00096 = "Ngày tạo";
    public static final String CONTENT00097 = "Mở lại";
    public static final String CONTENT00098 = "Đánh giá dịch vụ";
    public static final String CONTENT00099 = "Danh sách bảo trì";
    public static final String CONTENT00100 = "Tài khoản";
    public static final String CONTENT00101 = "Chưa chọn hình ảnh";
    public static final String CONTENT00102 = "Hiện mật khẩu";
    public static final String CONTENT00103 = "Nội dung đánh giá &amp; góp ý";
    public static final String CONTENT00104 = "Khách hàng: %1$s";
    public static final String CONTENT00105 = "Ngày tạo: $1%s";
    public static final String CONTENT00106 = "%1$sID: %2$s";
    public static final String CONTENT00107 = "%1$s - %2$s";
    public static final String CONTENT00108 = "VietMy Dental";
    public static final String CONTENT00109 = "Số serial bình";
    public static final String CONTENT00110 = "Loại định kỳ:";
    public static final String CONTENT00111 = "Loại khách hàng:";
    public static final String CONTENT00112 = "Vị trí";
    public static final String CONTENT00113 = "Số serial bình:";
    public static final String CONTENT00114 = "Chưa cập nhật được vị trí hiện tại!";
    public static final String CONTENT00115 = "Thương hiệu khách hàng đã dùng";
    public static final String CONTENT00116 = "Tên dịch vụ đối thủ";
    public static final String CONTENT00117 = "Thông tin thêm: ";
    public static final String CONTENT00118 = "Loại điểm: ";
    public static final String CONTENT00119 = "Thời gian dự kiến hết gas: ";
    public static final String CONTENT00120 = "Thương hiệu đối thủ:";
    public static final String CONTENT00121 = "Tên đối thủ:";
    public static final String CONTENT00122 = "Tạo Khách Hàng";
    public static final String CONTENT00123 = "Điện thoại liên hệ";
    public static final String CONTENT00124 = "Version code hiện tại:";
    public static final String CONTENT00125 = "Xem thêm";
    public static final String CONTENT00126 = "Cập nhật thông tin thành công";
    public static final String CONTENT00127 = "Home";
    public static final String CONTENT00128 = "Cài đặt";
    public static final String CONTENT00129 = "Bảo trì";
    public static final String CONTENT00130 = "Đặt gas";
    public static final String CONTENT00131 = "Quản lý sự việc";
    public static final String CONTENT00132	= "Đăng xuất";
    public static final String CONTENT00133	= "Nhập mã xác thực";
    public static final String CONTENT00134	= "Một mã xác thực đã được gửi đến số điện thoại của bạn dưới dạng tin nhắn, hãy nhập nó vào ô bên dưới";
    public static final String CONTENT00135 = "Mã xác thực";
    public static final String CONTENT00136 = "Để sau";
    public static final String CONTENT00137 = "Thông tin đăng ký còn thiếu";
    public static final String CONTENT00138 = "Chế độ training";
    public static final String CONTENT00139 = "Thông tin";
    public static final String CONTENT00140 = "vd: 0123456789-0908070605";
    public static final String CONTENT00141 = "Cập nhật";
    public static final String CONTENT00142 = "Đã có phiên bản mới, vui lòng cập nhật!";
    public static final String CONTENT00143 = "Chi tiết bảo trì";
    public static final String CONTENT00144 = "NVKD";
    public static final String CONTENT00145 = "NV Bảo trì";
    public static final String CONTENT00146 = "Liên hệ";
    public static final String CONTENT00147 = "Sự cố";
    public static final String CONTENT00148 = "Mã số";
    public static final String CONTENT00149 = "Tạo trả lời";
    public static final String CONTENT00150 = "Ngày xử lý";
    public static final String CONTENT00151 = "Nội bộ";
    public static final String CONTENT00152 = "Điện thoại";
    public static final String CONTENT00153 = "Đánh giá nhân viên bảo trì";
    public static final String CONTENT00154 = "Cập nhật khách hàng";
    public static final String CONTENT00155 = "Góp ý";
    public static final String CONTENT00156 = "Nhân viên bảo trì";
    public static final String CONTENT00157 = "Tgian xử lý";
    public static final String CONTENT00158 = "Kết quả";
    public static final String CONTENT00159 = "Báo cáo";
    public static final String CONTENT00160 = "Ngày bảo trì";
    public static final String CONTENT00161 = "Lịch bảo trì";
    public static final String CONTENT00162 = "Thông báo";
    public static final String CONTENT00163 = "Đầu tư";
    public static final String CONTENT00168 = "Gửi báo cáo công việc";
    public static final String CONTENT00170 = "Số điện thoại người nhận";
    public static final String CONTENT00176 = "Không tìm thấy đại lý quanh vị trí hiện tại!";
    public static final String CONTENT00177 = "Loại định kỳ";
    public static final String CONTENT00178 = "Tạo mới bảo trì";
    public static final String CONTENT00180 = "Gửi";
    public static final String CONTENT00181 = "Xin vui lòng chọn Trạng thái sự cố";
    public static final String CONTENT00182 = "Xin vui lòng chọn Thời lượng xử lý";
    public static final String CONTENT00183 = "Xin vui lòng đánh giá thông tin KH";
    public static final String CONTENT00184 = "Khách hàng báo ĐÚNG sự cố";
    public static final String CONTENT00185 = "Khách hàng báo SAI sự cố";
    public static final String CONTENT00186 = "Trả lời bảo trì";
    public static final String CONTENT00187 = "Xin nhập thông tin Người nghiệm thu";
    public static final String CONTENT00188 = "Xin nhập thông tin Ghi chú nội bộ";
    public static final String CONTENT00189 = "Xin bổ sung Hình ảnh liên quan";
    public static final String CONTENT00190 = "Bạn đang gửi thông tin trả lời như bên dưới cho chúng tôi. Xin hãy kiểm tra lại các thông tin cho thật chính xác và nhấn nút Gửi nếu bạn đồng ý.";
    public static final String CONTENT00191 = "Khách hàng báo sai sự cố";
    public static final String CONTENT00194 = "Thời gian";
    public static final String CONTENT00195 = "Nghiệm thu";
    //public static final String CONTENT00196 = "Lỗi kết nối đến máy chủ";
    public static final String CONTENT00196 = "Lỗi kết nối internet. Bạn có muốn tải lại không?";
    public static final String CONTENT00197 = "Chức năng đã bị khoá, vui lòng thử lại sau!";
    public static final String CONTENT00198 = "Thông tin phiên bản";
    public static final String CONTENT00199 = "Email góp ý";
    public static final String CONTENT00200 = "Trang chủ";
    public static final String CONTENT00201 = "Xin vui lòng chọn Loại sự cố";
    public static final String CONTENT00202 = "Hủy";
    public static final String CONTENT00203 = "Sự cố khác";
    public static final String CONTENT00204 = "Xin vui lòng chọn Người liên hệ";
    public static final String CONTENT00205 = "Bạn đang gửi thông tin sự cố như bên dưới cho chúng tôi. Xin hãy kiểm tra lại các thông tin cho thật chính xác và nhấn nút Gửi nếu bạn đồng ý.";
    public static final String CONTENT00206 = "Xin vui lòng đánh giá Mức độ hài lòng";
    public static final String CONTENT00207 = "Xin vui lòng đánh giá Nhân viên bảo trì";
    public static final String CONTENT00208 = "Nội dung cần đánh giá và góp ý";
    public static final String CONTENT00209 = "Xin cảm ơn quý khách hàng đã đánh giá dịch vụ của chúng tôi. Xin hãy kiểm tra lại các thông tin cho thật chính xác và nhấn nút Gửi nếu quý khách hàng đồng ý.";
    public static final String CONTENT00210 = "Mức hài lòng";
    public static final String CONTENT00212 = "Xem ảnh";
    public static final String CONTENT00217 = "Xác nhận";
    public static final String CONTENT00218 = "Tổng cộng";
    public static final String CONTENT00219 = "Khuyến mãi";
    public static final String CONTENT00220 = "Hủy bỏ";
    public static final String CONTENT00222 = "Thêm mã khuyến mại";
    public static final String CONTENT00223 = "Xem";
    public static final String CONTENT00224 = "Để sau";
    public static final String CONTENT00225 = "Bảo trì miễn phí";
    public static final String CONTENT00226 = "Gas24h";
    public static final String CONTENT00227 = "Quên mật khẩu?";
    public static final String CONTENT00228 = "Tạo tài khoản mới";
    public static final String CONTENT00229 = "Lưu thông tin";
    public static final String CONTENT00230 = "Xác thực";
    public static final String CONTENT00231 = "Đơn hàng";
    public static final String CONTENT00232 = "Chi tiết đơn hàng";
    public static final String CONTENT00233 = "Nhân viên giao hàng";
    public static final String CONTENT00234 = "Camera";
    public static final String CONTENT00235 = "Thư viện";
    public static final String CONTENT00236 = "Mua hàng";
    public static final String CONTENT00237 = "Chọn loại gas";
    public static final String CONTENT00238 = "Chọn quà tặng";
    public static final String CONTENT00239 = "Chiết khấu";
    public static final String CONTENT00240 = "Đại lý";
    public static final String CONTENT00241 = "Gọi GAS";
    public static final String CONTENT00242 = "Hỗ trợ Khách hàng";
    public static final String CONTENT00243 = "Địa chỉ người nhận";
    public static final String CONTENT00244 = "Không lấy quà";
    public static final String CONTENT00245 = "Mã nhân viên";
    public static final String CONTENT00246 = "Bù vỏ";
    public static final String CONTENT00247 = "Khuyến mãi";
    public static final String CONTENT00248 = "Hết hạn vào";
    public static final String CONTENT00249 = "Nhập mã khuyến mãi";
    public static final String CONTENT00250 = "Mã khuyến mãi";
    public static final String CONTENT00251 = "Thử lại";
    public static final String CONTENT00252 = "Đặt hàng";
    public static final String CONTENT00253 = "Thông tin đơn hàng";
    public static final String CONTENT00254 = "Loại bình";
    public static final String CONTENT00255 = "Số lượng";
    public static final String CONTENT00256 = "Quý khách chắc chắn muốn hủy đơn hàng?";
    public static final String CONTENT00257 = "Mã đơn hàng";
    public static final String CONTENT00258 = "Số xe";
    public static final String CONTENT00259 = "Hình thức thanh toán";
    public static final String CONTENT00260 = "Tiền gas";
    public static final String CONTENT00261 = "Tiền gas dư";
    public static final String CONTENT00262 = "Tổng thanh toán";
    public static final String CONTENT00263 = "Thông tin vỏ";
    public static final String CONTENT00279 = "Bạn không có quyền sử dụng chức năng này";
    public static final String CONTENT00280 = "Đăng xuất thành công";
    public static final String CONTENT00281 = "KH Hộ gia đình";
    public static final String CONTENT00282 = "Từ ngày";
    public static final String CONTENT00283 = "Đến ngày";
    public static final String CONTENT00284 = "Tất cả";
    public static final String CONTENT00285 = "Đã mua";
    public static final String CONTENT00286 = "Chưa mua";
    public static final String CONTENT00287 = "Tìm kiếm";
    public static final String CONTENT00288 = "Thông tin khách hàng";
    public static final String CONTENT00289 = "Loại khách hàng";
    public static final String CONTENT00290 = "Vị trí";
    public static final String CONTENT00291 = "Loại điểm";
    public static final String CONTENT00292 = "Thời gian dự kiến hết gas";
    public static final String CONTENT00293 = "Thương hiệu đối thủ";
    public static final String CONTENT00294 = "Tên đối thủ";
    public static final String CONTENT00295 = "Báo cáo công việc";
    public static final String CONTENT00296 = "Chi tiết báo cáo";
    public static final String CONTENT00297 = "Bạn đang gửi thông tin Khách hàng như bên dưới. Xin hãy kiểm tra lại các thông tin cho thật chính xác và nhấn nút Gửi nếu bạn đồng ý.";
    public static final String CONTENT00298 = "Tỉnh/Thành Phố";
    public static final String CONTENT00299 = "Quận/Huyện";
    public static final String CONTENT00300 = "Phường/Xã";
    public static final String CONTENT00301 = "Xin vui lòng nhập Địa chỉ của Khách hàng";
    public static final String CONTENT00302 = "Xin vui lòng nhập Thông tin thêm";
    public static final String CONTENT00303 = "Thương hiệu";
    public static final String CONTENT00304 = "T.gian sử dụng";
    public static final String CONTENT00305 = "Xin vui lòng chọn Loại khách hàng";
    public static final String CONTENT00306 = "Xin vui lòng chọn Vật tư đầu tư";
    public static final String CONTENT00307 = "Tạo Báo cáo công việc";
    public static final String CONTENT00308 = "Xin vui lòng nhập Nội dung báo cáo";
    public static final String CONTENT00309 = "Bạn đang gửi thông tin Báo cáo như bên dưới. Xin hãy kiểm tra lại các thông tin cho thật chính xác và nhấn nút Gửi nếu bạn đồng ý.";
    public static final String CONTENT00310 = "Đơn hàng Hộ gia đình";
    public static final String CONTENT00311 = "Hoàn thành";
    public static final String CONTENT00312 = "Thêm sản phẩm";
    public static final String CONTENT00313 = "Quà khuyến mãi";
    public static final String CONTENT00314 = "Chọn loại sản phẩm";
    public static final String CONTENT00315 = "Vật tư Vỏ";
    public static final String CONTENT00316 = "Vật tư Khác";
    public static final String CONTENT00317 = "Bạn muốn xoá vật tư này?";
    public static final String CONTENT00318 = "Thu tiền";
    public static final String CONTENT00319 = "Chọn lý do hủy đơn hàng";
    public static final String CONTENT00320 = "Hủy đơn hàng";
    public static final String CONTENT00321 = "Chọn vật tư";
    public static final String CONTENT00322 = "Xác nhận đơn hàng thành công. Bạn có muốn xem chi tiết đơn hàng hay không?";
    public static final String CONTENT00323 = "Hủy xác nhận đơn hàng thành công.";
    public static final String CONTENT00324 = "Bạn chưa nhập vỏ thu về cho đơn hàng này. Bạn có chắc tiếp tục thao tác thu tiền?";
    public static final String CONTENT00325 = "Thêm vỏ";
    public static final String CONTENT00326 = "Tiếp tục";
    public static final String CONTENT00327 = "Xác nhận hủy đơn hàng?";
    public static final String CONTENT00328 = "Đang giao hàng";
    public static final String CONTENT00329 = "Đang xử lý đơn hàng";
    public static final String CONTENT00330 = "Đã giao";
    public static final String CONTENT00331 = "Đã huỷ";
    public static final String CONTENT00332 = "Đơn hàng Bò Mối";
    public static final String CONTENT00333 = "Gas";
    public static final String CONTENT00334 = "Thực tế";
    public static final String CONTENT00335 = "Tên";
    public static final String CONTENT00336 = "Serial";
    public static final String CONTENT00337 = "Vỏ";
    public static final String CONTENT00338 = "Cân";
    public static final String CONTENT00339 = "Dư";
    public static final String CONTENT00340 = "Ngày giao hàng";
    public static final String CONTENT00341 = "Thêm vật tư";
    public static final String CONTENT00342 = "Tiền mặt";
    public static final String CONTENT00343 = "Xoá vật tư";
    public static final String CONTENT00344 = "Cập nhật số lượng giao";
    public static final String CONTENT00345 = "Cập nhật thông tin vỏ";
    public static final String CONTENT00346 = "Khối lượng vỏ bình";
    public static final String CONTENT00347 = "Khối lượng vỏ bình + gas dư";
    public static final String CONTENT00348 = "Xác nhận hoàn thành đơn hàng?";
    public static final String CONTENT00349 = "Xác nhận hủy đơn hàng với lý do: %@?";
    public static final String CONTENT00350 = "Giá gas";
    public static final String CONTENT00351 = "Cập nhật số lượng đặt hàng";
    public static final String CONTENT00352 = "Danh sách Thẻ kho";
    public static final String CONTENT00353 = "Tạo Thẻ kho";
    public static final String CONTENT00354 = "Chi tiết Thẻ kho";
    public static final String CONTENT00355 = "Ngày nhập xuất";
    public static final String CONTENT00356 = "Mã Thẻ kho";
    public static final String CONTENT00357 = "Loại Thẻ kho";
    public static final String CONTENT00358 = "Tổng số";
    public static final String CONTENT00359 = "Xin vui lòng chọn đối tượng để tạo Thẻ kho";
    public static final String CONTENT00360 = "Khách hàng";
    public static final String CONTENT00361 = "Xoá thông tin";
    public static final String CONTENT00362 = "Chức năng hiện đang hoàn thiện. Xin vui lòng thử lại sau";
    public static final String CONTENT00363 = "Xin vui lòng chọn Ngày nhập xuất";
    public static final String CONTENT00364 = "Ngày nhập xuất";
    public static final String CONTENT00365 = "Hôm nay";
    public static final String CONTENT00366 = "Bạn đang gửi thông tin Thẻ kho như bên dưới. Xin hãy kiểm tra lại các thông tin cho thật chính xác và nhấn nút Gửi nếu bạn đồng ý.";
    public static final String CONTENT00367 = "Xin vui lòng chọn Vật tư";
    public static final String CONTENT00368 = "Xin nhập thông tin Ghi chú";
    public static final String CONTENT00369 = "Mật khẩu mới phải khác mật khẩu cũ!";
    public static final String CONTENT00370 = "Loại hỗ trợ";
    public static final String CONTENT00371 = "Loại bán hàng";
    public static final String CONTENT00372 = "Thu";
    public static final String CONTENT00373 = "Chi";
    public static final String CONTENT00374 = "Xin vui lòng chọn khách hàng";
    public static final String CONTENT00375 = "Bạn đang gửi thông tin Đơn hàng Bò/Mối như bên dưới. Xin hãy kiểm tra lại các thông tin cho thật chính xác và nhấn nút Gửi nếu bạn đồng ý.";
    public static final String CONTENT00376 = "Tạo đơn hàng";
    public static final String CONTENT00377 = "Xin vui lòng chọn Đại lý giao hàng";
    public static final String CONTENT00378 = "Bạn có muốn thoát ra không?";
    public static final String CONTENT00379 = "Bình gas 50Kg";
    public static final String CONTENT00380 = "Bình gas 45Kg";
    public static final String CONTENT00381 = "Bình gas 12Kg";
    public static final String CONTENT00382 = "Bình gas 6Kg";
    public static final String CONTENT00383 = "Số điện thoại mới";
    public static final String CONTENT00384 = "Bình 50kg";
    public static final String CONTENT00385 = "Bình 45kg";
    public static final String CONTENT00386 = "Bình 12kg";
    public static final String CONTENT00387 = "Bình 6kg";
    public static final String CONTENT00388 = "Quỹ tiền mặt";
    public static final String CONTENT00389 = "Bạn đang gửi thông tin Thu-Chi như bên dưới. Xin hãy kiểm tra lại các thông tin cho thật chính xác và nhấn nút Gửi nếu bạn đồng ý.";
    public static final String CONTENT00390 = "Tạo Thu chi";
    public static final String CONTENT00391 = "Xin vui lòng chọn đối tượng để tạo Thu chi";
    public static final String CONTENT00392 = "Loại Thu chi";
    public static final String CONTENT00393 = "Xin vui lòng chọn số điện thoại";
    public static final String CONTENT00394 = "Số tiền";
    public static final String CONTENT00395 = "Xin vui lòng nhập Số tiền";
    public static final String CONTENT00396 = "Cập nhật Thẻ kho";
    public static final String CONTENT00397 = "Cập nhật Thu chi";
    public static final String CONTENT00398 = "Xin vui lòng chọn Loại Thu chi";
    public static final String CONTENT00399 = "Chi tiết Thu chi";
    public static final String CONTENT00400 = "Lịch thu tiền";
    public static final String CONTENT00401 = "Chọn thao tác";
    public static final String CONTENT00402 = "Tạo Ticket";
    public static final String CONTENT00403 = "Báo cáo vật tư";
    public static final String CONTENT00404 = "Báo cáo hộ gia đình";
    public static final String CONTENT00405 = "Báo cáo Quỹ tiền mặt";
    public static final String CONTENT00406 = "T.Đầu";
    public static final String CONTENT00407 = "Nhập";
    public static final String CONTENT00408 = "Xuất";
    public static final String CONTENT00409 = "T.Cuối";
    public static final String CONTENT00410 = "Refresh";
    public static final String CONTENT00411 = "Vật tư";
    public static final String CONTENT00412 = "Từ";
    public static final String CONTENT00413 = "Đến";
    public static final String CONTENT00414 = "Doanh thu";
    public static final String CONTENT00415 = "SL";
    public static final String CONTENT00416 = "Giá";
    public static final String CONTENT00417 = "Thành tiền";
    public static final String CONTENT00418 = "Tồn";
    public static final String CONTENT00419 = "Loại";
    public static final String CONTENT00420 = "Bảo trì HGĐ";
    public static final String CONTENT00421 = "Mã bảo trì";
    public static final String CONTENT00422 = "Đang xử lý";
    public static final String CONTENT00423 = "Đã xử lý xong";
    public static final String CONTENT00424 = "Ticket";
    public static final String CONTENT00425 = "Thông tin hỗ trợ";
    public static final String CONTENT00426 = "Chi tiết hỗ trợ";
    public static final String CONTENT00427 = "Trả lời Ticket";
    public static final String CONTENT00428 = "Nội dung trả lời";
    public static final String CONTENT00429 = "Bạn chưa nhập nội dung trả lời";
    public static final String CONTENT00430 = "Xác nhận đóng ticket?";
    public static final String CONTENT00431 = "Xin nhập thông tin cần hỗ trợ";
    public static final String CONTENT00432 = "Bạn đang gửi thông tin cần hỗ trợ như bên dưới. Xin hãy kiểm tra lại các thông tin cho thật chính xác và nhấn nút Gửi nếu bạn đồng ý.";
    public static final String CONTENT00433 = "Người xử lý";
    public static final String CONTENT00434 = "Nợ";
    public static final String CONTENT00435 = "Tìm kiếm Khách hàng";
    public static final String CONTENT00436 = "Tác vụ khác";
    public static final String CONTENT00437 = "Xin chọn tác vụ cần thực hiện";
    public static final String CONTENT00438 = "Xác nhận đơn hàng nợ";
    public static final String CONTENT00439 = "Bạn đang gửi thông tin Xác nhận đơn hàng nợ như bên dưới. Xin hãy kiểm tra lại các thông tin cho thật chính xác và nhấn nút Gửi nếu bạn đồng ý.";
    public static final String CONTENT00440 = "Chi gas dư";
    public static final String CONTENT00441 = "Cập nhật phiên bản";
    public static final String CONTENT00442 = "Cập nhật tài khoản";
    public static final String CONTENT00443 = "Email";
    public static final String CONTENT00444 = "Bạn đang gửi thông tin Cập nhật tài khoản như bên dưới. Xin hãy kiểm tra lại các thông tin cho thật chính xác và nhấn nút Gửi nếu bạn đồng ý.";
    public static final String CONTENT00445 = "PTTT Code";
    public static final String CONTENT00446 = "Chọn chi nhánh";
    public static final String CONTENT00447 = "Id khách hàng đang rỗng";
    public static final String CONTENT00448 = "Vui lòng đánh giá dịch vụ của chúng tôi";
    public static final String CONTENT00449 = "Bạn cần chúng tôi cải thiện về?";
    public static final String CONTENT00450 = "Rất vui vì bạn hài lòng với dịch vụ của chúng tôi. Bán có thêm nhận xét gì không nào?";
    public static final String CONTENT00451 = "Thái độ";
    public static final String CONTENT00452 = "Tay nghề";
    public static final String CONTENT00453 = "Đúng giờ";
    public static final String CONTENT00454 = "An toàn";
    public static final String CONTENT00455 = "Vật tư";
    public static final String CONTENT00456 = "Khác";
    public static final String CONTENT00457 = "Bổ sung ý kiến của bạn";
    public static final String CONTENT00458 = "Chuyển đại lý giao hàng";
    public static final String CONTENT00459 = "Bạn đang gửi thông tin Thay đổi đại lý giao hàng như bên dưới. Xin hãy kiểm tra lại các thông tin cho thật chính xác và nhấn nút Gửi nếu bạn đồng ý.";
    public static final String CONTENT00460 = "Đại lý hiện tại";
    public static final String CONTENT00461 = "Đại lý mới";
    public static final String CONTENT00462 = "Bạn chưa chọn Đại lý cần chuyển";
    public static final String CONTENT00463 = "Bạn vừa chọn Đại lý cần chuyển là Đại lý hiện tại. Xin hãy kiểm tra lại lần nữa.";
    public static final String CONTENT00464 = "Xoá tất cả";
    public static final String CONTENT00465 = "Bạn có muốn xoá tất cả vỏ?";
    public static final String CONTENT00466 = "Seri";
    //public static final String CONTENT00467 = "Trừ tiền thiếu đầu vào";
    public static final String CONTENT00467 = "Số Kg thiếu";
    public static final String CONTENT00468 = "Giảm giá";
    public static final String CONTENT00469 = "Điều hướng";
    public static final String CONTENT00470 = "Thêm hình từ camera";
    public static final String CONTENT00471 = "Thêm hình từ thư viện";
    public static final String CONTENT00472 = "---------- hoặc ----------";
    public static final String CONTENT00473 = "Sử dụng tài khoản Facebook";
    public static final String CONTENT00474 = "Sử dụng tài khoản Zalo";
    public static final String CONTENT00475 = "Nhập mã pin";
    public static final String CONTENT00476 = "Mã pin";
    public static final String CONTENT00477 = "Mã pin vừa được gửi đến số điện thoại";
    public static final String CONTENT00478 = "Vui lòng xem tin nhắn";
    public static final String CONTENT00479 = "và nhập mã pin vào ô bên trên để";
    public static final String CONTENT00480 = "hoàn tất đăng ký sử dụng ứng dụng";
    public static final String CONTENT00481 = "Chưa nhận được mã PIN?";
    public static final String CONTENT00482 = "Gửi lại";
    public static final String CONTENT00483 = "Chạm vào biểu tượng để đặt gas";
    public static final String CONTENT00484 = "Hỗ trợ";
    public static final String CONTENT00485 = "Chọn gas";
    public static final String CONTENT00486 = "Chọn quà";
    public static final String CONTENT00487 = "Đơn hàng của bạn đang được xử lý";
    public static final String CONTENT00488 = "vui lòng chờ trong giây lát...";
    public static final String CONTENT00489 = "Đơn hàng hoàn tất!";
    public static final String CONTENT00490 = "Chạm vào biểu tượng để xem hoá đơn.";
    public static final String CONTENT00491 = "Cám ơn Quý Khách";
    public static final String CONTENT00492 = "Giới thiệu người thân cài đặt ứng dụng, NHẬN MÃ GIẢM GIÁ";
    public static final String CONTENT00493 = "Nhân viên giao gas";
    public static final String CONTENT00494 = "Chưa nhập mã pin";
    public static final String CONTENT00495 = "Bạn có thể chọn loại Gas và Quà trước khi đặt Gas\nHoặc chờ nhân viên Gas24h liên hệ để xác nhận";
    public static final String CONTENT00496 = "Quý khách vui lòng chờ trong giây lát\nNhân viên Gas24h sẽ liên hệ Quý khách trong vài phút";
    public static final String CONTENT00497 = "Quý khách vừa hoàn tất đơn hàng đầu tiên\nĐiểm thưởng +300. Tổng số điểm hiện tại 4300";
    public static final String CONTENT00498 = "Chạm vào đơn hàng XXXXXX để xem lại chi tiết\nLộ trình giao hàng khoảng 3km";
    public static final String CONTENT00499 = "Giới thiệu";
    public static final String CONTENT00500 = "Sử dụng mã";
    public static final String CONTENT00501 = "Mã code";
    public static final String CONTENT00502 = "QR code";
    public static final String CONTENT00503 = "Chia sẻ mã KM";
    public static final String CONTENT00504 = "Thêm mã khuyến mãi";
    public static final String CONTENT00505 = "Đơn hàng của bạn đã bị huỷ";
    public static final String CONTENT00506 = "Hoá đơn mua hàng";
    public static final String CONTENT00507 = "Bình chọn";
    public static final String CONTENT00508 = "Sai số điện thoại?";
    public static final String CONTENT00509 = "Trở về";
    public static final String CONTENT00510 = "Xin quý khách vui lòng nhập số điện thoại để nhân viên Gas24h liên hệ với quý khách";
    public static final String CONTENT00511 = "Người dùng không cho phép";
    public static final String CONTENT00512 = "Trạng thái đơn hàng";
    public static final String CONTENT00513 = "Chờ\nxác nhận";
    public static final String CONTENT00514 = "Đã\nxác nhận";
    public static final String CONTENT00515 = "Đang\ngiao gas";
    public static final String CONTENT00516 = "Xem trước đơn hàng";
    public static final String CONTENT00517 = "Thay đổi";
    public static final String CONTENT00518 = "Thông tin giao hàng";
    public static final String CONTENT00519 = "Tiếp tục đơn hàng";
    public static final String CONTENT00520 = "Đơn hàng\nhoàn tất";
    public static final String CONTENT00521 = "Đơn hàng của bạn đã được xác nhận.";
    public static final String CONTENT00522 = "Chúng tôi sẽ gọi cho bạn trong giây lát!";
    public static final String CONTENT00523 = "Loại gas được chọn sẽ được mặc định trong đơn hàng nếu Quý khách không thay đổi";
    public static final String CONTENT00524 = "Chọn loại gas";
    public static final String CONTENT00525 = "Điểm thưởng hiện tại: 500 điểm\nĐặt 1 bình gas mới, điểm thưởng 300 điểm\nBạn có thể chọn quà đến 800 điểm";
    //    public static final String CONTENT00526 = "Chia sẻ mã giới thiệu của bạn: %@ hoặc truy cập địa chỉ: %@";
    public static final String CONTENT00526 = "Dùng mã giới thiệu %@ trong ứng dụng Gas24h để nhận ưu đãi. Truy cập địa chỉ: %@ để tải ứng dụng.";
    public static final String CONTENT00527 = "Lịch sử đơn hàng";
    public static final String CONTENT00528 = "Thông tin tài khoản";
    public static final String CONTENT00529 = "Bật dịch vụ định vị để ứng dụng tự động tìm thấy địa chỉ giao hàng";
    public static final String CONTENT00530 = "Thông tin chưa được lưu lại, bạn chắc chắn muốn huỷ?";
    public static final String CONTENT00531 = "Quà tặng";
    public static final String CONTENT00532 = "Không lấy quà";
    public static final String CONTENT00533 = "Nội dung đang được cập nhật";
    public static final String CONTENT00534 = "Bạn đã giới thiệu %d người sử dụng";
    public static final String CONTENT00535 = "Bạn đang có %@ điểm thưởng";
    public static final String CONTENT00536 = "Chính sách khuyến mãi";
    public static final String CONTENT00537 = "Hướng dẫn sử dụng";
    public static final String CONTENT00538 = "Chia sẻ với bạn bè";
    public static final String CONTENT00539 = "---------- Hỗ trợ ----------";
    public static final String CONTENT00540 = "Chưa nhập mã pin";
    public static final String CONTENT00541 = "Danh sách Bệnh nhân";
    public static final String CONTENT00542 = "Tên người dùng";
    public static final String CONTENT00543 = "Thông tin bệnh nhân";
    public static final String CONTENT00544 = "Hồ sơ bệnh án";
    public static final String CONTENT00545 = "Tiền sử bệnh";
    public static final String CONTENT00546 = "Bạn muốn xoá bệnh lý này?";
    public static final String CONTENT00547 = "Xoá";
    public static final String CONTENT00548 = "Thêm tiền sử bệnh";
    public static final String CONTENT00549 = "Số bệnh án";
    public static final String CONTENT00550 = "Xin vui lòng nhập số bệnh án";
    public static final String CONTENT00551 = "Thông tin không hợp lệ. Xin vui lòng nhập lại";
    public static final String CONTENT00552 = "Thông tin đợt điều trị";
    public static final String CONTENT00553 = "Tạo mới đợt điều trị";
    public static final String CONTENT00554 = "Thông tin Lần điều trị";
    public static final String CONTENT00555 = "Đang cập nhật";
    public static final String CONTENT00556 = "Tiến trình mới";
    public static final String CONTENT00557 = "Lần điều trị mới";
    public static final String CONTENT00558 = "✓";
    public static final String CONTENT00559 = "Chọn ngày giờ";
    public static final String CONTENT00560 = "Bổ sung số bệnh án";
    public static final String CONTENT00561 = "Bạn có chắc chắn muốn hoàn thành Chi tiết đợt điều trị này không?";
    public static final String CONTENT00562 = "Giờ hẹn";
    public static final String CONTENT00563 = "Ngày hẹn";
    public static final String CONTENT00564 = "Hình thức";
    public static final String CONTENT00565 = "Chi tiết công việc";
    public static final String CONTENT00566 = "Răng số";
    public static final String CONTENT00567 = "Chẩn đoán";
    public static final String CONTENT00568 = "Loại điều trị";
    public static final String CONTENT00569 = "Nam";
    public static final String CONTENT00570 = "Tổng tiền";
    public static final String CONTENT00571 = "Trang chủ";
    public static final String CONTENT00572 = "Giảm";
    public static final String CONTENT00573 = "Thực thu";
    public static final String CONTENT00574 = "Thanh toán";
    public static final String CONTENT00575 = "Thông tin răng";
    public static final String CONTENT00576 = "Dữ liệu chưa đầy đủ, bạn có muốn tiếp tục không?";
    public static final String CONTENT00577 = "Nợ cũ";
    public static final String CONTENT00578 = "Bạn có chắc chắn muốn tạo mới Đợt điều trị này không?";
    //++ BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh
    public static final String CONTENT00579 = "Thay mật khẩu thành công";
    public static final String CONTENT00580 = "Cập nhật tài khoản thành công";
    //-- BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh
}
