package api.utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;




public class DataProviders {
	
	
	@DataProvider(name="PetData")
	public static String[][] getPetData() throws IOException //data provider method should always be static.
	{
		String petDataFilePath = "F:\\WorkSpace\\RestAssuredPetStoreAutomation-main\\testData\\excelDataFiles\\PetData.xlsx";
		XLUtility xlutil = new XLUtility(petDataFilePath);
		
		int rownum = xlutil.getRowCount("petDetails"); 
		int colcount = xlutil.getCellCount("petDetails", 0); 
		
		String petData [][] = new String[rownum][colcount];
		for(int i=1; i<=rownum; i++) 
		{
			for(int j=0; j<colcount; j++) 
			{
				petData[i-1][j]= xlutil.getCellData("petDetails", i, j);
			}
		}
		return petData;
	}
	
	
	
	@DataProvider(name="UpdatedPetData")
	public static String[][] getUpdatedPetData() throws IOException //data provider method should always be static.
	{
		String petDataFilePath = "F:\\WorkSpace\\RestAssuredPetStoreAutomation-main\\testData\\excelDataFiles\\PetData.xlsx";
		XLUtility xlutil = new XLUtility(petDataFilePath);
		
		int rownum = xlutil.getRowCount("updatedPetDetails"); 
		int colcount = xlutil.getCellCount("updatedPetDetails", 0); 
		
		String petData [][] = new String[rownum][colcount];
		for(int i=1; i<=rownum; i++) 
		{
			for(int j=0; j<colcount; j++) 
			{
				petData[i-1][j]= xlutil.getCellData("updatedPetDetails", i, j);
			}
		}
		return petData;
	}

}
