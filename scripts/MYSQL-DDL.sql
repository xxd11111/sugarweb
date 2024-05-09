DROP TABLE IF EXISTS API_ERROR_LOG;
CREATE TABLE API_ERROR_LOG(
                              `ID` VARCHAR(255) NOT NULL  COMMENT '' ,
                              `API_NAME` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                              `USER_ID` VARCHAR(255)  DEFAULT NULL COMMENT '' ,
                              `USERNAME` VARCHAR(255)  DEFAULT NULL COMMENT '' ,
                              `REQUEST_IP` VARCHAR(255)  DEFAULT NULL COMMENT '' ,
                              `REQUEST_METHOD` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                              `REQUEST_PARAMS` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                              `USER_AGENT` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                              `REQUEST_URL` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                              `REQUEST_TIME` DATETIME   COMMENT '' ,
                              `DURATION` INT   COMMENT '' ,
                              `RESULT_CODE` VARCHAR(255)  DEFAULT NULL COMMENT '' ,
                              `RESULT_DATA` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                              `RESULT_MSG` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                              `EXCEPTION_TIME` VARCHAR(255)   COMMENT '' ,
                              `EXCEPTION_NAME` VARCHAR(255)   COMMENT '' ,
                              `EXCEPTION_CLASS_NAME` VARCHAR(255)   COMMENT '' ,
                              `EXCEPTION_FILE_NAME` VARCHAR(255)   COMMENT '' ,
                              `EXCEPTION_METHOD_NAME` VARCHAR(255)   COMMENT '' ,
                              `EXCEPTION_LINE_NUMBER` INT   COMMENT '' ,
                              `EXCEPTION_STACK_TRACE` VARCHAR(255)   COMMENT '' ,
                              `EXCEPTION_ROOT_CAUSE_MESSAGE` VARCHAR(255)   COMMENT '' ,
                              `EXCEPTION_MESSAGE` VARCHAR(255)   COMMENT '' ,
                              PRIMARY KEY (ID)
)  COMMENT = '';

DROP TABLE IF EXISTS API_CALL_LOG;
CREATE TABLE API_CALL_LOG(
                             `ID` VARCHAR(255) NOT NULL  COMMENT '' ,
                             `API_NAME` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                             `USER_ID` VARCHAR(255)  DEFAULT NULL COMMENT '' ,
                             `USERNAME` VARCHAR(255)  DEFAULT NULL COMMENT '' ,
                             `REQUEST_IP` VARCHAR(255)  DEFAULT NULL COMMENT '' ,
                             `REQUEST_METHOD` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                             `REQUEST_PARAMS` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                             `USER_AGENT` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                             `REQUEST_URL` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                             `REQUEST_TIME` DATETIME   COMMENT '' ,
                             `DURATION` INT   COMMENT '' ,
                             `RESULT_CODE` INT  DEFAULT NULL COMMENT '' ,
                             `RESULT_DATA` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                             `RESULT_MSG` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                             PRIMARY KEY (ID)
)  COMMENT = '';

DROP TABLE IF EXISTS DICTIONARY;
CREATE TABLE DICTIONARY(
                           `ID` VARCHAR(32) NOT NULL  COMMENT '' ,
                           `DICT_GROUP` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                           `DICT_CODE` VARCHAR(255)  DEFAULT NULL COMMENT '' ,
                           `DICT_NAME` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                           `DICT_TYPE` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                           PRIMARY KEY (ID)
)  COMMENT = '';

DROP TABLE IF EXISTS MENU;
CREATE TABLE MENU(
                     `ID` VARCHAR(255) NOT NULL  COMMENT '' ,
                     `PID` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                     `MENU_CODE` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                     `MENU_NAME` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                     `MENU_TYPE` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                     `ICON` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                     `MENU_URL` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                     `MENU_URL_TYPE` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                     `API_CODE` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                     `SEQUENCE` INT  DEFAULT NULL COMMENT '' ,
                     `STATUS` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                     PRIMARY KEY (ID)
)  COMMENT = '';

DROP TABLE IF EXISTS PARAM;
CREATE TABLE PARAM(
                      `ID` VARCHAR(32) NOT NULL  COMMENT '' ,
                      `CODE` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                      `NAME` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                      `VALUE` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                      `COMMENT` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                      PRIMARY KEY (ID)
)  COMMENT = '';

DROP TABLE IF EXISTS ROLE;
CREATE TABLE ROLE(
                     `ID` BIGINT NOT NULL  COMMENT '' ,
                     `ROLE_CODE` VARCHAR(32)  DEFAULT 'NULL' COMMENT '' ,
                     `ROLE_NAME` VARCHAR(90)  DEFAULT 'NULL' COMMENT '' ,
                     `STATUS` VARCHAR(32)  DEFAULT 'NULL' COMMENT '' ,
                     PRIMARY KEY (ID)
)  COMMENT = '';

DROP TABLE IF EXISTS ROLE_MENU;
CREATE TABLE ROLE_MENU(
                          `ROLE_ID` VARCHAR(32) NOT NULL  COMMENT '' ,
                          `MENU_ID` VARCHAR(32) NOT NULL  COMMENT ''
)  COMMENT = '';


CREATE INDEX FK3SQ5XKTHR6ICWCYOHTDOJE586 ON ROLE_MENU(MENU_ID);
CREATE INDEX FK65H6SD1KUD5KLYMYGBFS9CRNN ON ROLE_MENU(ROLE_ID);

DROP TABLE IF EXISTS SECURITY_LOG;
CREATE TABLE SECURITY_LOG(
                             `ID` VARCHAR(255) NOT NULL  COMMENT '' ,
                             `ACTION_IP` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                             `ACTION_TYPE` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                             `EVENT_TYPE` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                             `MESSAGE` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                             `USER_ID` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                             `USERNAME` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                             PRIMARY KEY (ID)
)  COMMENT = '';

DROP TABLE IF EXISTS USER;
CREATE TABLE USER(
                     `ID` VARCHAR(255) NOT NULL  COMMENT '' ,
                     `USERNAME` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                     `EMAIL` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                     `MOBILE_PHONE` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                     `NICK_NAME` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                     `PASSWORD` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                     `SALT` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                     `STATUS` VARCHAR(255)  DEFAULT 'NULL' COMMENT '' ,
                     PRIMARY KEY (ID)
)  COMMENT = '';

DROP TABLE IF EXISTS USER_ROLE;
CREATE TABLE USER_ROLE(
                          `USER_ID` VARCHAR(32) NOT NULL  COMMENT '' ,
                          `ROLE_ID` VARCHAR(32) NOT NULL  COMMENT ''
)  COMMENT = '';


CREATE INDEX FKJ9553ASS9UCTJRMH0GKQSMV0D ON USER_ROLE(ROLE_ID);

DROP TABLE IF EXISTS FILE_INFO;
CREATE TABLE FILE_INFO(
                          `ID` VARCHAR(255) NOT NULL  COMMENT '' ,
                          `KEY` VARCHAR(255)   COMMENT '' ,
                          `FILENAME` VARCHAR(255)   COMMENT '' ,
                          `FILE_TYPE` VARCHAR(255)   COMMENT '' ,
                          `CONTENT_TYPE` VARCHAR(255)   COMMENT '' ,
                          `FILE_SIZE` VARCHAR(255)   COMMENT '' ,
                          `UPLOADTIME` VARCHAR(255)   COMMENT '' ,
                          `FILE_GROUP` VARCHAR(255)   COMMENT '' ,
                          PRIMARY KEY (ID)
)  COMMENT = '';

DROP TABLE IF EXISTS FILE_LINK_INFO;
CREATE TABLE FILE_LINK_INFO(
                               `ID` VARCHAR(255) NOT NULL  COMMENT '' ,
                               `FILE_ID` VARCHAR(255)   COMMENT '' ,
                               `BIZ_ID` VARCHAR(255)   COMMENT '' ,
                               `BIZ_TYPE` VARCHAR(255)   COMMENT '' ,
                               PRIMARY KEY (ID)
)  COMMENT = '';

DROP TABLE IF EXISTS SCHEDULER_TASK;
CREATE TABLE SCHEDULER_TASK(
                               `ID` VARCHAR(255) NOT NULL  COMMENT '' ,
                               `TASK_NAME` VARCHAR(255)   COMMENT '' ,
                               `BEAN_NAME` VARCHAR(255)   COMMENT '' ,
                               `METHOD_NAME` VARCHAR(255)   COMMENT '' ,
                               `CRON` VARCHAR(255)   COMMENT '' ,
                               `DEFAULT_CRON` VARCHAR(255)   COMMENT '' ,
                               `PARAMS_LENGTH` VARCHAR(255)   COMMENT '' ,
                               `PARAMS` VARCHAR(255)   COMMENT '' ,
                               `DEFAULT_PARAMS` VARCHAR(255)   COMMENT '' ,
                               `STATUS` VARCHAR(255)   COMMENT '' ,
                               PRIMARY KEY (ID)
)  COMMENT = '';
