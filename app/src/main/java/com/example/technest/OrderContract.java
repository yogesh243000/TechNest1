package com.example.technest;

import android.provider.BaseColumns;

public class OrderContract {
    public static final class OrderEntry implements BaseColumns {
        public static final String TABLE_NAME = "orders";
        public static final String COLUMN_NAME_ORDER_ID = "order_id";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TOTAL_AMOUNT = "total_amount";
        public static final String COLUMN_NAME_STATUS = "status";

        public static final String COLUMN_NAME_PRODUCT_NAME = "product_name";

    }
}