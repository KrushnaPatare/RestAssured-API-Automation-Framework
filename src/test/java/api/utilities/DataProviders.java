package api.utilities;

import java.io.IOException;
import org.testng.annotations.DataProvider;




public class DataProviders {
	
    private static final String petDataFilePath = PropertiesFile.getProperty("petDataExcel");
    		
    @DataProvider(name = "ExcelData")
    public static Object[][] getData(String sheetName) throws IOException 
    {
        XLUtility xlutil = new XLUtility(petDataFilePath);

        int rownum = xlutil.getRowCount(sheetName);
        int colcount = xlutil.getCellCount(sheetName, 0);

        String[][] excelData = new String[rownum][colcount];
        for (int i = 1; i <= rownum; i++) {
            for (int j = 0; j < colcount; j++) {
                excelData[i - 1][j] = xlutil.getCellData(sheetName, i, j);
            }
        }
        return excelData;
    }

    // Overloaded DataProvider for specific scenarios
    @DataProvider(name = "PetData")
    public static Object[][] getPetData() throws IOException 
    {
        return getData("petDetails");
    }

    @DataProvider(name = "UpdatedPetData")
    public static Object[][] getUpdatedPetData() throws IOException 
    {
        return getData("updatedPetDetails");
    }

    
    
    
}





