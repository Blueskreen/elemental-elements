import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class ElementaryElements {
    /*
     * to run from cmd:
     * cd to the workspace folder then run
     * java -Dfile.encoding=UTF-8 -classpath "C:\Users\Casey\eclipse-workspace\Elementary Elements\bin" src/ElementaryElements.java Elements2edited.csv
     */
    
    public static void main(String[] args) {
        
        PeriodicTable.createTableFromFile(args[0]);
        
        Scanner steve = new Scanner(System.in);
        
        testing();
        
        steve.close();
    }
    
    private static void testing() {
        
        PeriodicTable.printPeriodicTable();
        
        ArrayList<Element> elements = new ArrayList<Element>();
        
        try {
            elements = PeriodicTable.getElements();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        for(Element e : elements) {
            System.out.println(String.format("%-"+3+"s",e.getSymbol()) + ": " + e.getElectronConfig());
        }
        
        
        try {
            
            Molecule methane = new Molecule(getElementList(new int[] {6,1,1,1,1}));
            
            Molecule salt = new Molecule(getElementList(new Element[] {PeriodicTable.getElement("Na"), 
                    PeriodicTable.getElement("Cl")}));
            
            Molecule NaOH = new Molecule(getElementList(new Element[] {PeriodicTable.getElement("Na"), 
                    PeriodicTable.getElement("O"), PeriodicTable.getElement("H")}));
            
            Molecule HCl = new Molecule(getElementList(new Element[] {PeriodicTable.getElement("H"), 
                    PeriodicTable.getElement("Cl")}));
            
            //System.out.println("\n" + methane + "\n" + salt + "\n" + NaOH + "\n" + HCl + "\n");
            
            System.out.println(methane.printDetailedInfo());
            System.out.println(salt.printDetailedInfo());
            System.out.println(NaOH.printDetailedInfo());
            System.out.println(HCl.printDetailedInfo());
            
            
            System.out.println(PeriodicTable.getElement("H").printDetailedInfo());
            System.out.println(PeriodicTable.getElement("Rn").printDetailedInfo());
            System.out.println(PeriodicTable.getElement("C").printDetailedInfo());
            System.out.println(PeriodicTable.getElement("Co").printDetailedInfo());
            
        }
        catch(Exception e) {
            
        }
        
    }
    
    
    private static List<Element> getElementList(int[] elements) throws Exception{
        List<Element> result = new ArrayList<Element>();
        
        for(int e : elements) {
            result.add(PeriodicTable.getElement(e));
        }
        
        return result;
    }
    
    private static List<Element> getElementList(Element[] elements) throws Exception{
        List<Element> result = new ArrayList<Element>();
        
        for(Element e : elements) {
            result.add(PeriodicTable.getElement(e.getProtons()));
        }
        
        return result;
    }
    
}
