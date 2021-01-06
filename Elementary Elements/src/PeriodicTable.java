import java.util.*;
import java.io.File;


public class PeriodicTable {
    
    private static ArrayList<Element> pTable = new ArrayList<Element>();
    
    public static void createTableFromFile(String fileName) {
        
        Scanner fileIn;
        String eName, eSymbol;
        int eANumber;
        double eAWeight, eARadius, eElectronegitivity;
        List<Integer> eOxStates = new ArrayList<Integer>();
        int[] pEOxStates;
        
        
        // string placeholders because the file is dumb
        String sAWeight, sARadius, sEMinus, sOxState;
        
        try {
            // assuming that the file being passed in
            fileIn = new Scanner(new File(fileName));
            // skip the headers
            fileIn.nextLine();
            // change the delimiter so it will stop at commas not too
            fileIn.useDelimiter(",|\\s+"); // default is \\s+, so we're just changing it to look for , too
            
            // name, symbol, number, weight, radius, e-, oxidation states
            
            while(fileIn.hasNext()) {
                
                // read the element attributes from the file
                
                eName = fileIn.next();
                fileIn.next();// because there is a space...
                eSymbol = fileIn.next();
                eANumber = fileIn.nextInt();
                eAWeight = getNumberFromString(fileIn.next());
                eARadius = getNumberFromString(fileIn.next());
                eElectronegitivity = getNumberFromString(fileIn.next());
                
                sOxState = fileIn.next();
                if(!sOxState.equals("-")) {
                    eOxStates.add((int) getNumberFromString(sOxState));
                    while(fileIn.hasNextInt()) {
                        eOxStates.add(fileIn.nextInt());
                    }
                }
                else
                    eOxStates.add(0);
                
                pEOxStates = new int[eOxStates.size()];
                for(int i = 0; i < pEOxStates.length; i++) {
                    pEOxStates[i] = eOxStates.get(i);
                }
                // clear it for the next run
                eOxStates.clear();
                
                // make the element and add it to the list
                pTable.add(new Element(eName, eSymbol, eANumber, eAWeight, eARadius, eElectronegitivity, pEOxStates));
                
                // assuming at this point, the scanner is at the end of a line...
                fileIn.nextLine();
                
            }
            
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // method to get a number from the file being read when the string has more than just a number
    private static double getNumberFromString(String in) {
        double result = 0;
        
        // regex for it it has [ddd]
        if(in.matches("\\[\\d{3}\\]"))
            // split the string by [ or ] and return the middle string parsed as a double
            return Double.parseDouble(in.split("\\[|\\]")[1]);
        else if(in.equals("-"))
            // if it is a - return 0
            return 0;
        
        // OHERWISE: as long as the length is more than 0 (if everything went right this should never happen
        if(in.length() > 0)
            // set result to the passed in string parsed as a double
            result = Double.parseDouble(in);
        
        return result;
    }
    
    // method to print out the periodic table
    public static void printPeriodicTable() {
        
        /*  12345
         * 1╔═══╗
         * 2║He ║
         * 3╚═══╝
         */
        if(pTable.size() == 118) {
            // just some variables to help me keep track of things
            int spacesPerE = 5;
            int linesPerE = 4;
            int groups = 18;
            int periods = 9; // 9 because the lanthanides and actinides will be displayed on their own
            int [] ePerPeriod = {2, 8, 8, 18, 18, 18, 18, 14, 14};
            
            //
            int lastElement = 0;
            
            // easier to reference versions of the box making characters
            String ltc = "╔", r = "═", rtc = "╗",c = "║", rbc = "╝", lbc = "╚";
            
            // an array of string builders which will be used to create each period
            ArrayList<StringBuilder> period = new ArrayList<StringBuilder>();
            
            for(int p = 0; p < periods; p++) {
                // reset periods, and add empty StringBuilders
                period.clear();
                for(int i = 0; i < linesPerE; i++) {
                    period.add(new StringBuilder());
                }
                
                switch(p) {
                    case 0:
                        
                        // add boxes for elements
                        for(int j = 0; j < ePerPeriod[p]; j++) {
                            period.get(0).append(makeWrapper(ltc, r, rtc));
                            period.get(1).append(makeEText(pTable.get(lastElement).getSymbol(), c));
                            period.get(2).append(makeEText(Integer.toString(pTable.get(lastElement).getProtons()), c));
                            period.get(3).append(makeWrapper(lbc, r, rbc));
                            
                            lastElement ++;
                        }
                        
                        // add space
                        for(StringBuilder s : period) {
                            s.insert(spacesPerE, multiplyChar('\0', (groups-ePerPeriod[p])*spacesPerE));
                        }
                        
                        break;
                    case 1:
                        
                        // add boxes for elements
                        for(int j = 0; j < ePerPeriod[p]; j++) {
                            period.get(0).append(makeWrapper(ltc, r, rtc));
                            period.get(1).append(makeEText(pTable.get(lastElement).getSymbol(), c));
                            period.get(2).append(makeEText(Integer.toString(pTable.get(lastElement).getProtons()), c));
                            period.get(3).append(makeWrapper(lbc, r, rbc));
                            
                            lastElement ++;
                        }
                        
                        // add space
                        for(StringBuilder s : period) {
                            s.insert(spacesPerE*2, multiplyChar('\0', (groups-ePerPeriod[p])*spacesPerE));
                        }
                        
                        break;
                    case 2: // same as 1
                        
                        // add boxes for elements
                        for(int j = 0; j < ePerPeriod[p]; j++) {
                            period.get(0).append(makeWrapper(ltc, r, rtc));
                            period.get(1).append(makeEText(pTable.get(lastElement).getSymbol(), c));
                            period.get(2).append(makeEText(Integer.toString(pTable.get(lastElement).getProtons()), c));
                            period.get(3).append(makeWrapper(lbc, r, rbc));
                            
                            lastElement ++;
                        }
                        
                        // add space
                        for(StringBuilder s : period) {
                            s.insert(spacesPerE*2, multiplyChar('\0', (groups-ePerPeriod[p])*spacesPerE));
                        }
                        
                        break;
                    case 7:
                        // Lanthanides
                        lastElement = 57;
                        
                     // add boxes for elements
                        for(int j = 0; j < ePerPeriod[p]; j++) {
                            period.get(0).append(makeWrapper(ltc, r, rtc));
                            period.get(1).append(makeEText(pTable.get(lastElement).getSymbol(), c));
                            period.get(2).append(makeEText(Integer.toString(pTable.get(lastElement).getProtons()), c));
                            period.get(3).append(makeWrapper(lbc, r, rbc));
                            
                            lastElement ++;
                        }
                        
                        // add space
                        for(StringBuilder s : period) {
                            s.insert(0, multiplyChar('\0', 3*spacesPerE));
                        }
                        
                        break;
                    case 8:
                        
                     // Actinides
                        lastElement = 89;
                        
                     // add boxes for elements
                        for(int j = 0; j < ePerPeriod[p]; j++) {
                            period.get(0).append(makeWrapper(ltc, r, rtc));
                            period.get(1).append(makeEText(pTable.get(lastElement).getSymbol(), c));
                            period.get(2).append(makeEText(Integer.toString(pTable.get(lastElement).getProtons()), c));
                            period.get(3).append(makeWrapper(lbc, r, rbc));
                            
                            lastElement ++;
                        }
                        
                        // add space
                        for(StringBuilder s : period) {
                            s.insert(0, multiplyChar('\0', 3*spacesPerE));
                        }
                        
                        break;
                    default:
                        // add boxes for elements
                        for(int j = 0; j < ePerPeriod[p]; j++) {
                            
                            if(lastElement == 57)
                                lastElement = 71;
                            else if(lastElement == 89)
                                lastElement = 103;
                            
                            period.get(0).append(makeWrapper(ltc, r, rtc));
                            period.get(1).append(makeEText(pTable.get(lastElement).getSymbol(), c));
                            period.get(2).append(makeEText(Integer.toString(pTable.get(lastElement).getProtons()), c));
                            period.get(3).append(makeWrapper(lbc, r, rbc));
                            
                            lastElement ++;
                        }
                        
                        break;
                }
                
                // print out the result
                for(StringBuilder s : period) {
                    System.out.println(s.toString());
                }

            }
        }
        else
            System.out.println("Something went wrong- incorect number of elements");
    }
    
    private static String makeEText(String symbol, String bookend) {
        return bookend + String.format("%-"+3+"s", symbol) + bookend;
    }
    
    private static String makeWrapper(String lCorner, String middle, String rCorner) {
        
        return lCorner + multiplyChar(middle.charAt(0), 3) + rCorner;
    }
    
    private static String multiplyChar(char repeat, int numRepeat) {
        StringBuilder result = new StringBuilder();
        
        for(int i = 0; i < numRepeat; i++) {
            result.append(repeat);
        }
        
        return result.toString();
    }
    
    public static void printElements() {
        
        for(Element e : pTable) {
            System.out.print(e.getProtons() + ": " + e.getName() + " - " + e.getSymbol() + "\n");
            System.out.print("\tp: " + e.getProtons() + ", n: " + e.getNeutrons() + ", e: " + e.getElectrons());
            System.out.print(", w: " + e.getAtomicWeight());
            
            System.out.print("\nPossible Oxidation states: ");
            for(int i: e.getOxidationStates()) {
                System.out.print(i + ", ");
            }
            
            System.out.println("\n");
        }
        
    }
    
    public static ArrayList<Element> getElements() throws Exception{
        if(pTable.isEmpty())
            throw new Exception("pTable is empty");
        return pTable;
    }
    
    public static Element getElement(int atNum) throws Exception{
        if(pTable.isEmpty())
            throw new Exception("pTable is empty");
        return pTable.get(atNum-1);
    }
    
    public static Element getElement(String symbol) throws Exception{
        if(pTable.isEmpty())
            throw new Exception("pTable is empty");
        
        for(Element e : pTable) {
            if(e.getSymbol().equals(symbol))
                return e;
        }
        
        throw new Exception("Symbol not found");
    }
}
