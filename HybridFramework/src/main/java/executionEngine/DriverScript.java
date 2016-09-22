package executionEngine;

import static executionEngine.DriverScript.OR;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;
import org.testng.annotations.Test;

import config.ActionKeywords;
import config.Constants;
import utility.ExcelUtils;
import utility.Log;
 
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DriverScript {
	
	public static Properties OR;
	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	public static String sPageObject;
	public static Method method[];
		
	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public static String sTestStepID;
	public static String sRunMode;
	public static String sData;
	public static boolean bResult;
	public static String fileLocdir;
	
	
	public DriverScript() throws NoSuchMethodException, SecurityException{
		actionKeywords = new ActionKeywords();
		method = actionKeywords.getClass().getMethods();	
		
	}
	
	@Test(priority=1)
    public void main1() throws Exception {
		System.out.println(Constants.Path_TestData);
    	ExcelUtils.setExcelFile(Constants.Path_TestData);
    	DOMConfigurator.configure("log4j.xml");
    	String Path_OR = Constants.Path_OR;
		FileInputStream fs = new FileInputStream(Path_OR);
		OR= new Properties(System.getProperties());
		OR.load(fs);
		
	/*	String value="";
		Enumeration e = OR.keys();

		System.out.println("Enumeration:"+e);
		
		while(e.hasMoreElements())
		{
			//System.out.println(e.nextElement());
		String param = (String) e.nextElement();
		
		//System.out.println(OR.get(param));
		
		if (param.equals("txtbx_Appln-Yahoo_Password"))
		{
			value=(String) OR.get(param);
			//System.out.println(value);
			break;
	
		}
		//break;
		}
		System.out.println(value);
		*/
		DriverScript startEngine = new DriverScript();
	startEngine.execute_TestCase();
		
    }
		
    private void execute_TestCase() throws Exception {
	    	int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
			for(int iTestcase=1;iTestcase<iTotalTestCases;iTestcase++){
				bResult = true;
				sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases); 
				sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases);
				if (sRunMode.equals("Yes")){
					
					Log.startTestCase(sTestCaseID);
					   
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH mm ss");
				       Date dateobj = new Date();
				       String date=df.format(dateobj).toString();
				       
					fileLocdir="C:\\TestExecutionRuns\\"+df.format(dateobj)+"\\"+sTestCaseID;
					
					new File(fileLocdir).mkdirs();
					
					iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
					iTestLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
					bResult=true;
					for (;iTestStep<iTestLastStep;iTestStep++){
						
						sTestStepID=ExcelUtils.getCellData(iTestStep, Constants.Col_TestScenarioID,Constants.Sheet_TestSteps);
						System.out.println("sTestStepID:"+sTestStepID);
						
			    		sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,Constants.Sheet_TestSteps);
			    		System.out.println("sActionKeyword:"+sActionKeyword);
			    		
			    		sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, Constants.Sheet_TestSteps);
			    		System.out.println("sPageObject:"+sPageObject);
			    		
			    		sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, Constants.Sheet_TestSteps);
			    		System.out.println("sData:"+sData);
			    		
			    		execute_Actions();
						if(bResult==false){
							ExcelUtils.setCellData(Constants.KEYWORD_FAIL,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
							Log.endTestCase(sTestCaseID);
							break;
							}						
						}
					if(bResult==true){
					ExcelUtils.setCellData(Constants.KEYWORD_PASS,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
					Log.endTestCase(sTestCaseID);	
						}					
					}
				}
    		}	
     
     private static void execute_Actions() throws Exception {
	
		for(int i=0;i<method.length;i++){
			
			if(method[i].getName().equals(sActionKeyword)){
				method[i].invoke(actionKeywords,sPageObject, sData);
				if(bResult==true){
					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
					break;
				}else{
					ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
					ActionKeywords.closeBrowser("","");
					break;
					}
				}
			}
       }
     }
		
     
