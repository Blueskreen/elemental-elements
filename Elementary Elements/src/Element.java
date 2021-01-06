
public class Element implements Comparable<Element>{
    private int protons, neutrons, electrons;
    private double atomicWeight, atomicRadius, electronegitivity;
    private String name, symbol, electronConfig;
    private int[] oxidationStates;
    
    // constants in amu
    public static final double NEUTRON_WEIGHT = 1.0086654, PROTON_WEIGHT = 1.0072766, ELECTRON_WEIGHT = 5.489e-4;
    
    /********** Constructors **********/
    
    public Element(String nIn, String sIn, int anIn, double awIn, double arIn, double eIn, int[] oxIn) {
        name = nIn;
        symbol = sIn;
        protons = anIn;
        electrons = anIn; // assuming neutral elements
        atomicWeight = awIn;
        neutrons = calculateNeutrons(awIn, anIn); // remember this always truncates
        atomicRadius = arIn;
        electronegitivity = eIn;
        oxidationStates = oxIn;
        electronConfig = calculateElectronConfig();
    }
    
    /*********** Helpers *************/
    
    // compares elements based on electronegitivity
    public int compareTo(Element other) {
        
        if(this.electronegitivity < other.getElectronegitivity())
            return -1;
        else if(this.electronegitivity > other.getElectronegitivity())
            return 1;
        return 0;
    }
    
    public boolean equals(Element other) {
        return this.protons == other.getProtons();
    }
    
    public String toString() {
        return symbol + " - " + protons + ": " + name;
    }
    
    public String printDetailedInfo() {
        String result ="";
        
        /* 
         * electronegitivity
         * oxidationStates;
         */
        
        result += name + ":\n";
        result += "    Number: " + protons + "\n";
        result += "    Symbol: " + symbol + "\n";
        result += "    Avg. Atmoic Weight: " + atomicWeight + " amu\n";
        result += "    Atomic Radius: " + atomicRadius + " nm\n";
        result += "    Electronegativity: " + electronegitivity + "\n";
        result += "    Electron configuration: " + electronConfig + "\n";
        
        return result;
    }
    
    // calculates and returns an element's electron configuration
    private String calculateElectronConfig() {
        String result = new String();
        
        int n = getQuantumNumber();
        int esPerS = 2, esPerP = 6, esPerD = 10, esPerF = 14;
        
        // format [He] 2S2 2P3 - no superscript
        
        // assuming a neutral element
        
        int esToAssign = protons; // assuming a neutral element
        int sShells = 0, pShells = 0, dShells = 0, fShells = 0;
        int esInFinalShell = 0;
        int currentN = 1;
        int elementN = getQuantumNumber();
        int assignedToShell = 0;
        
        while(esToAssign > 0 && currentN <= n) {
            
            // assign es to S shell
            if(currentN <= elementN) {
                // increment filled S shell
                sShells ++;
                while(assignedToShell < esPerS && esToAssign > 0) {
                    esToAssign --;
                    assignedToShell ++;
                }
                
                // append to the result string
                result += sShells + "S";
                
                // if this is the last shell
                if(esToAssign <= 0) {
                    esInFinalShell = assignedToShell;
                    result += esInFinalShell + " ";
                    break;
                }
                else
                    result += esPerS + " ";
                
                assignedToShell = 0;
            }
            
            // assign es to F shell, if one exists
            if(currentN - 5 > 0 && esToAssign > 0) {
                // increment F shells
                fShells++;
                while(assignedToShell < esPerF && esToAssign > 0) {
                    esToAssign --;
                    assignedToShell ++;
                }
                
                result += (fShells + 3) + "F";
                
                // if this is the last shell
                if(esToAssign <= 0) {
                    esInFinalShell = assignedToShell;
                    result += esInFinalShell + " ";
                    break;
                }
                else
                    result += esPerF + " ";
                assignedToShell = 0;
            }
            
            // assign es to D shell, if one exists
            if(currentN - 3 > 0 && esToAssign > 0) {
                // increment F shells
                dShells++;
                while(assignedToShell < esPerD && esToAssign > 0) {
                    esToAssign --;
                    assignedToShell ++;
                }
                
                result += (dShells+2) +"D";
                
                // if this is the last shell
                if(esToAssign <= 0) {
                    esInFinalShell = assignedToShell;
                    result += esInFinalShell + " ";
                    break;
                }
                else
                    result += esPerD + " ";
                assignedToShell = 0;
            }
            
            // assign es to P shell
            if(currentN - 1 > 0 && esToAssign > 0) {
                // increment F shells
                pShells++;
                
                while(assignedToShell < esPerP && esToAssign > 0) {
                    esToAssign --;
                    assignedToShell ++;
                }
                
                result += (pShells + 1) + "P";
                
                // if this is the last shell
                if(esToAssign <= 0) {
                    esInFinalShell = assignedToShell;
                    result += esInFinalShell + " ";
                    break;
                }
                else
                    result += esPerP + " ";
                assignedToShell = 0;
            }
            
            currentN ++;
            
        }
        
        // for generating the shorthand
        String[] nobleGasses = {"1S2 ", "[He] 2S2 2P6 ", "[Ne] 3S2 3P6 ", "[Ar] 4S2 3D10 4P6 ", 
                "[Kr] 5S2 4D10 5P6 ", "[Xe] 6S2 4F14 5D10 6P6 "};
        String ng = "";
        
        for(int g = 0; g < nobleGasses.length; g++) {
            
            ng = nobleGasses[g];
            
            if(result.length() > ng.length()) {
                
                if(result.substring(0, ng.length()).equals(ng)) {
                    switch(g) {
                        case 0:
                            result = "[He] " + result.substring(ng.length());
                            break;
                        case 1:
                            result = "[Ne] " + result.substring(ng.length());
                            break;
                        case 2:
                            result = "[Ar] " + result.substring(ng.length());
                            break;
                        case 3:
                            result = "[Kr] " + result.substring(ng.length());
                            break;
                        case 4:
                            result = "[Xe] " + result.substring(ng.length());
                            break;
                        case 5:
                            result = "[Rn] " + result.substring(ng.length());
                            break;
                        default:
                            break;
                    }
                }
                
            }
        }
        
        return result;
    }
    
    private int getQuantumNumber() {
        int result = 0;
        
        if(protons < 3)
            result = 1;
        else if(protons >= 3 && protons < 11)
            result = 2;
        else if(protons >= 11 && protons < 19)
            result = 3;
        else if(protons >= 19 && protons < 37)
            result = 4;
        else if(protons >= 37 && protons < 55)
            result = 5;
        else if(protons >= 55 && protons < 87)
            result = 6;
        else if(protons >= 87 && protons < 119)
            result = 7;
        else
            result = -1; // if it reaches this there was a problem
            
        return result;
    }
    
    private int calculateNeutrons(double aWeight, int numPtons) {
        int result = (int) ((atomicWeight - (protons*PROTON_WEIGHT))/NEUTRON_WEIGHT);
        
        // there are no centaurs in oxford
        if(result <= 0)
            result = 0;
            
        return result;
    }

    /******** Getters & Setters *******/
    
    public int[] getOxidationStates() {
        return oxidationStates;
    }

    public void setOxidationStates(int[] oxidationStates) {
        this.oxidationStates = oxidationStates;
    }

    public double getAtomicRadius() {
        return atomicRadius;
    }

    public void setAtomicRadius(double atomicRadius) {
        this.atomicRadius = atomicRadius;
    }

    public int getProtons() {
        return protons;
    }

    public void setProtons(int protons) {
        this.protons = protons;
    }

    public int getNeutrons() {
        return neutrons;
    }

    public void setNeutrons(int neutrons) {
        this.neutrons = neutrons;
    }

    public int getElectrons() {
        return electrons;
    }

    public void setElectrons(int electrons) {
        this.electrons = electrons;
    }

    public double getAtomicWeight() {
        return atomicWeight;
    }
    
    public String getElectronConfig() {
        return electronConfig;
    }
    
    public void setElectronConfig(String eCIn) {
        this.electronConfig = eCIn;
    }

    public void setAtomicWeight(double atomicWeight) {
        this.atomicWeight = atomicWeight;
    }

    public double getElectronegitivity() {
        return electronegitivity;
    }

    public void setElectronegitivity(double electronegitivity) {
        this.electronegitivity = electronegitivity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
