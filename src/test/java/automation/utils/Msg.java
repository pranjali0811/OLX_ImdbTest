/*
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automation.utils;

import org.testng.Reporter;

/**
 *
 * @author pranjaliJaiswal
 */
public class Msg {

	protected static final String fail = "[ASSERT FAIL]: ";
	protected static final String info = "[INFO]: ";
	protected static final String pass = "[ASSERT PASS]: ";
	protected static final String scripterror = "[SCRIPTING ERROR]: ";
	protected static final String actions = "[ACTION]: ";
	protected static final String verified = "[Verified]: ";
	protected static final String failure = "[FAILED]: ";
	protected static final String alert = "[ALERT]: ";

	public String failForAssert(String message) {
		return reportMsgForAssert(fail, message, true);
	}

	public String fail(String message) {
		return reportMsg(fail, message);
	}
	
	public String verified(String message) {
		return reportMsg(verified, message);
	}

	public String pass(String message) {
		return reportMsg(pass, message);
	}

	public String scripterror(String message) {
		return reportMsg(scripterror, message);
	}

	public String log(String message) {
		return reportMsg(info, message);
	}
	
	public String alert(String message) {
		return reportMsg(alert, message);
	}

	public String logAssertion(String message) {
		return reportMsg("", message);
	}

	public String actions(String message) {
		return reportMsg(actions, message);
	}

	private String reportMsg(String prefix, String message) {
		Reporter.log(prefix + message, true);
		return prefix + message;
	}
	public static void reportInfo(String message) {
		Reporter.log(info + message, true);
	}
	public static void reportAction(String message) {
		Reporter.log(actions + message, true);
	}
	public static void reportPass(String message) {
		Reporter.log(pass + message, true);
	}
	public static String reportFailForAssert(String message) {
		 return fail + message;
	}


	private String reportMsgForAssert(String prefix, String message, boolean flag) {
		return prefix + message;
	}
	
	// bBelow methods are static so that can be used across the classes
	
	private static String reportMessage(String prefix, String message) {
		Reporter.log(prefix + message, true);
		return prefix + message;
	}
	
	
	public static  String logMessage(String message) {
		return reportMessage(info, message);
	}
	
	public static  String logFailure(String message) {
		return reportMessage(failure, message);
	}

	public static  String logAction(String message) {
		return reportMessage(actions, message);
	}
}
