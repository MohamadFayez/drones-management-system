
CREATE TABLE DRONES
        (
        SERIAL_NUMBER  VARCHAR(100) NOT NULL,
        MODEL VARCHAR(50) NOT NULL,
        WEIGHT_LIMIT INTEGER  NOT NULL,
        BATTERY_CAPACITY INTEGER NOT NULL,
        STATE VARCHAR(50) NOT NULL,
        PRIMARY KEY(SERIAL_NUMBER)
        );
                
CREATE TABLE MEDICATION_LOAD
        (
        LOAD_ID INTEGER  NOT NULL,
        SOURCE VARCHAR(100) NOT NULL,
		DESTINATION VARCHAR(100) NOT NULL,
		CREATION_DATE DATE NOT NULL,
		SERIAL_NUMBER_FK VARCHAR(100) NOT NULL,
		CODE_FK VARCHAR(100) NOT NULL,
        PRIMARY KEY(LOAD_ID)
        );
       
CREATE TABLE MEDICATION
        (
        NAME  VARCHAR(100) NOT NULL,
        WEIGHT INTEGER  NOT NULL,
        CODE VARCHAR(100) NOT NULL,
        IMAGE BINARY LARGE OBJECT ,
        PRIMARY KEY(CODE)
        );       
        
CREATE TABLE MEDICATION_DELIVERY
        (
        DELIVERY_ID INTEGER  NOT NULL,
        DELIVERY_TIME  DATE  NOT NULL,
        LOAD_ID INTEGER  NOT NULL,
        PRIMARY KEY(DELIVERY_ID)
        );
        