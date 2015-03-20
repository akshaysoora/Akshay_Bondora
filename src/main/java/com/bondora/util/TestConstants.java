package com.bondora.util;

public class TestConstants {

	public static final String BONDORA_URL = "https://www.bondora.com";
	public static final String CONFIG_PROPERTY_FILE_PATH = "src/test/resources/config.properties";

	public static final int MINI_WAIT = 5;
	public static final int MEDIUM_WAIT = 30;
	public static final int LONG_WAIT = 60;
	public static final int ONE_SECOND = 1;

	public static enum PORTFOLIO_MANAGER {
		CONSERVATIVE, BALANCED, PROGRESSIVE, DYNAMIC
	}
}
