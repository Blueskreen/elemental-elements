import java.util.List;
import java.util.Set;
import java.util.TreeMap;
public class Molecule {
    
    private List<Element> elements;
    private Element cation, anion;
    private String polarity, name;
    private double formulaWeight = 0;
    private int numAtoms = 0;
    
    
    private final static TreeMap<Double, String> POSSIBLE_POLARITIES = new TreeMap<Double, String>(makePolarityMap());
    /* a map that stores the values for polarity
     * Key: 1.8, 0.4, 0 these are the lowest possible values for each category of polarity
     * Value: Ionic, polar covalent, non-polar covalent
     */
    
    
    public Molecule(List<Element> eIn) throws IllegalArgumentException{
        if(eIn.isEmpty())
            throw new IllegalArgumentException("Cannot pass in an empty List");
        
        elements = eIn;
        
        cation = elements.get(0);
        anion = elements.get(0);
        
        // sorts elements so the least electronegitive are at the front
        //Collections.sort(elements);
        
        int currentCount;
        String currentSym, newSym;
        
        TreeMap <String, Integer> symbols = new TreeMap<String, Integer>();
        Set keys;
        
        for(Element e : elements) {
            
            if(e.getElectronegitivity() > anion.getElectronegitivity())
                anion = e;
            else if(e.getElectronegitivity() < cation.getElectronegitivity())
                cation = e;
            
            formulaWeight += e.getAtomicWeight();
            
            currentSym = e.getSymbol();
            
            keys = symbols.keySet();
            
            if(keys.contains(currentSym)) {
                currentCount = symbols.get(currentSym).intValue();
                symbols.remove(currentSym);
                symbols.put(currentSym, Integer.valueOf(currentCount + 1));
            }
            else
                symbols.put(currentSym, Integer.valueOf(1));
            
            numAtoms++;
                
        }
        
        name = "(";
        for(Element e : elements) {
            if(!name.contains(e.getSymbol()))
                name += e.getSymbol() + (symbols.get(e.getSymbol()) == 1 ? "" : symbols.get(e.getSymbol()));
        }
        name = name.stripTrailing();
        name += ")";
        
        double eMinusDiff = anion.getElectronegitivity() - cation.getElectronegitivity();
        Set<Double> polVals = POSSIBLE_POLARITIES.keySet();
        
        for(Double d : polVals) {
            if(eMinusDiff < d.doubleValue())
                break;
            
            polarity = POSSIBLE_POLARITIES.get(d);
        }
    }
    
    
    public String toString() {
        return name;
    }
    
    public String printDetailedInfo() {
        String result = "";
        
        result += name + ":\n";
        result += "    Molar Mass: " + formulaWeight + "g/mol\n";
        result += "    Estimated Polarity: " + polarity + "\n";
        
        return result;
    }
    
    
    // makes the map for electronegitivity
    private static TreeMap<Double, String> makePolarityMap(){
        TreeMap<Double, String> result = new TreeMap<Double, String>();
        String[] names = {"Ionic", "Polar Covalent", "Non-polar Covalent"};
        Double[] values = {Double.valueOf(1.8), Double.valueOf(0.4), Double.valueOf(0.0)};
        
        for(int i = 0; i < 3; i++) {
            result.put(values[i], names[i]);
        }
        
        return result;
    }

    public Element getCation() {
        return cation;
    }

    public void setCation(Element cation) {
        this.cation = cation;
    }
    
    public Element getAnion() {
        return anion;
    }
    public void setAnion(Element anion) {
        this.anion = anion;
    }

    public String getPolarity() {
        return polarity;
    }

    public void setPolarity(String polarity) {
        this.polarity = polarity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFormulaWeight() {
        return formulaWeight;
    }

    public void setFormulaWeight(double formulaWeight) {
        this.formulaWeight = formulaWeight;
    }
    
    public int getNumAtoms() {
        return numAtoms;
    }
    
    public void setNumAtoms(int numAtoms) {
        this.numAtoms = numAtoms;
    }
}
