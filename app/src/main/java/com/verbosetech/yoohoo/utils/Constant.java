package com.verbosetech.yoohoo.utils;

/**
 * Created by Ravi on 1/9/2018.
 */

public class Constant {


    public static final int REQUEST_CURRENCY = 441;

    public static enum Dialogcode {
        ONLINE_STATUS,MESSAGE_READ
    }

    public static enum Sortcode {
        SORT_BY_DATE_CREATED, SORT_BY_NAME, SORT_BY_CATEGORIES, SORT_BY_PUBLISHED_UNPUBLISHED, DATE, MOST_VIEWED
    }

    public static final int GET_IMAGE_FROM_GALLERY = 0;
    public static final int GET_IMAGE_BY_CAMERS = 1;
    public static String MyPREFERENCES = "MyPREFERENCES";

    public static String REMOVEINSTUCT = "REMOVEINSTUCT";

    public static String FORWARD_MESSAGE = "forward_message";
    public static String USER = "user";
    public static String IS_GROUP = "isgroup";


    public static final String REGULAR_EXPRESSION_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d$&+,:;=?@#|'<>.-^*()%!]{8,}$";

    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

//    public static final String YOUTUBE_PATTERN = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";


//    public static final String YOUTUBE_PATTERN = "/^.*(?:(?:youtu\\.be\\/|v\\/|vi\\/|u\\/\\w\\/|embed\\/)|(?:(?:watch)?\\?v(?:i)?=|\\&v(?:i)?=))([^#\\&\\?]*).*/";

    public static final String YOUTUBE_PATTERN = "^(https?\\:\\/\\/)?(www\\.)?(youtube\\.com|youtu\\.?be)\\/.+$";
//    public static final String YOUTUBE_PATTERN ="^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";

    public static final String DEVELOPER_KEY = "AIzaSyCo55ByjQIyBP9BibA3YCEl4FOG31V0C0c";
    public static final String VIDEO_ID = "VideoId";



    public static final String YOUTUBE_VIDEO_ID = "/(?:https?:\\/{2})?(?:w{3}\\.)?youtu(?:be)?\\.(?:com|be)(?:\\/watch\\?v=|\\/)([^\\s&]+)/";


    public static final String EVENTNAME = "eventName";
    public static final String FULLNAME = "fullname";
    public static final String EMAIL_ID = "email_id";
    public static final String VALUE = "value";
    public static final String PHONE_NUMBER_EXTENSION = "phone_number_extension";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String PHONE_MODEL = "phone_model";
    public static final String DEVICE_TOKEN = "device_token";
    public static final String USER_ID = "userId";

    public static final String OTP_NUMBER = "otpNumber";
    public static final String PASSWORD = "password";

    public static final String USERDATA = "userdata";

    public static final String COUNTRY_CODE = "country_code";
    public static final String COUNTRY_NAME = "country_name";
    public static final String COUNTRY_CATEGORY = "country";
    public static final String NAME_CODE = "name_code";
    public static final String FLAG_IMG = "flag_image";
    public static final String CATEGORTY_ID = "category_id";
    public static final String SUB_CATEGORTY_ID = "subcategory_id";
    public static final String SELECTED_CATEGORIES = "selectedCategory";
    public static final String LANGUAGE = "language";
    public static final String SORT_CODE = "sortCode";

    public static final String MAIN_CATEGORY = "main_category";
    public static final String SUB_CATEGORY = "sub_category";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String BUSINESS_DETAILS = "business_detail";

    public static final String BUSINESS_MENU = "business_menu";
    public static final String SECTION_LIST = "section_list";

    public static final String MENULISTID = "menuListId";



    public static final String IMAGE_CAT_ID = "image_cat_id";

    public static final String IMAGE_LIST = "image_list";








    //for bulk action
    public static final String BUSINESSIDS = "businessIds";
    public static final String LOGO_IMAGE = "logo_image";
    public static final String BANNER_IMAGE = "banner_image";

    //change password
    public static final String OLD_PASSWORD = "old_password";
    public static final String USER_PASSWORD = "user_password";

    //Businesss Seting
    public static final String EDITOR = "editor";
    public static final String EDITOR_ID = "editorId";



    public static final String BUSINESS_USER_ID = "business_user_id";
    public static final String BUSINESS_TITLE = "bussiness_title";
    public static final String BUSINESS_CAT = "bussiness_cat";
    public static final String BUSINESS_SUBCAT = "bussiness_subcat";
    public static final String BUSINESS_LOGO = "business_logo";
    public static final String BUSINESS_BANNER_IMAGE = "business_banner_image";
    public static final String BUSINESS_COUNTRY = "bussiness_country";
    public static final String BUSINESS_CITY = "bussiness_city";
    public static final String BUSINESS_LATITUDE = "bussiness_latitude";
    public static final String BUSINESS_LONGITUDE = "bussiness_longitude";
    public static final String BUSINESS_ADDRESS = "bussiness_address";
    public static final String BUSINESS_DESCRIPTION = "bussiness_description";
    public static final String BUSINESS_KEYWORD = "bussiness_keyword";
    public static final String BUSINESS_STATUS = "status";

    public static final String BUSINESSSTATUSS = "businessStatus";


    public static final String YOUTUBELINK = "youtubelink";




    public static final String BUSINESS_PUBLISH = "publish"; //1
    public static final String BUSINESS_DRAFT = "draft"; //2
    public static final String BUSINESS_GALLERY = "businessGallery";


    //Contact info

    public static final String PHONE = "phone";
    public static final String WHATSAPP_ADDRESS = "whatsappAddress";
    public static final String VIBER_ADDRESS = "viberAddress";
    public static final String WEBSITE_ADDRESS = "websiteAddress";
    public static final String EMAIL_ADDRESS = "emailAddress";
    public static final String FACEBOOK_ADDRESS = "facebookAddress";
    public static final String INSTAGRAM_ADDRESS = "instagramAddress";
    public static final String TWITTER_ADDRESS = "twitterAddress";
    public static final String LINKED_IN_ADDRESS = "linkedinAddress";

    public static final String SELECTED_CASE = "selected_case";
    public static final String INDEX = "index";
    public static final String PAGE = "page";
    public static final String SEARCH_BUSINESS = "searchBusiness";

    //Working Hours
    public static final String START_DAY = "startDay";
    public static final String MORNING_START = "morningStart";
    public static final String MORNING_END = "morningEnd";
    public static final String EVENING_START = "eveningStart";
    public static final String EVENING_END = "eveningEnd";
    public static final String IS_MORNING_OPEN = "isMorningOpen";
    public static final String IS_EVENING_OPEN = "isEveningOpen";
    public static final String MORNING_START_FORMAT = "morningStartFormat";
    public static final String MORNING_END_FORMAT = "morningEndFormat";
    public static final String EVENING_START_FORMAT = "eveningStartFormat";
    public static final String EVENING_END_FORMAT = "eveningEndFormat";

    //update profile
    public static final String USERID = "user_id";
    public static final String USER_FULLNAME = "user_fullname";
    public static final String USER_PHOTO = "user_photo";
    public static final String USER_COVER = "user_cover";
    public static final String USER_NOTES = "user_notes";

    //menu
    public static final String CURRENCYCODE = "currenyCode";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String BUSINESS_ID = "businessId";
    public static final String MENUID = "menuId";
    public static final String PRICE = "price";
    public static final String SECTIONID = "sectionId";
    public static final String MENU_IMAGE = "menuImage";

    public static final String BUSINESSID = "bussiness_id";

    public static final String BUSSINESSID = "bussinessId";

    //Change menulist order
    public static final String FROM = "from";
    public static final String TO = "to";





    //sorting
    public static final String SORT_BY_DATE_CREATED = "sort_by_date_created";
    public static final String SORT_BY_NAME = "name";
    public static final String SORT_BY_CATEGORIES = "sort_by_categories";
    public static final String SORT_BY_PUBLISHED_UNPUBLISHED = "sort_by_published_ubpunlished";

    public static String SELECTED_BUSINESS_BULK_EDIT = "selected_business_bulk_edit";

    public static String API_KEY_GOOGLE_PLACES = "AIzaSyDISb2f-DyKlwkozBfW9R_ebZXFIWSvQG4";


    public static String URL = "https://midlal.com/prowebservice";
    public static final String IMAGE_PATH = "https://midlal.com/";

    //Custom Gallery

    public static final int PERMISSION_REQUEST_CODE = 1000;
    public static final int PERMISSION_GRANTED = 1001;
    public static final int REQUEST_CODE = 2000;
    public static final int FETCH_STARTED = 2001;
    public static final int FETCH_COMPLETED = 2002;
    public static final int ERROR = 2005;
    public static final String INTENT_EXTRA_ALBUM = "album";
    public static final String DEFAULT_LIMIT = "default_limit";
    public static final int LIMIT_7 = 7;
    public static final int LIMIT_3 = 3;
    public static final String IS_IMAGES = "is_images";
    public static final String CATEGORY_NAME = "category_name";

    public static final String USER_LIST = "user_list";
    public static final String CONTACT_LIST = "contect_list";
    public static final String LINKMAP_LIST = "linkmap_list";




}
