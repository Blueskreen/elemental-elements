import java.util.*;

public class ChemicalEquation {
    
    private List<Molecule> reactants = new ArrayList<Molecule>();
    private List<Molecule> products = new ArrayList<Molecule>();
    
    private TreeMap<Molecule, Integer> balancedReactants = new TreeMap<Molecule, Integer>();
    private TreeMap<Molecule, Integer> balancedProducts = new TreeMap<Molecule, Integer>();
    
    public ChemicalEquation (ArrayList<Molecule> rectnts, ArrayList<Molecule> prdcts) {
        reactants = rectnts;
        products = prdcts;
        
        setBalancedData(balancedReactants, reactants);
        setBalancedData(balancedProducts, products);
        
        balanceEquation();
    }
    
    private void balanceEquation() {
        
        int currentIncrement = 1;
        int newNumber, otherSide, old, partialR, partialP, filledPartialR, runCount;
        boolean isBalanced = false;
        
        Set<Molecule> keys;
        Iterator<Molecule> itr;
        Molecule key;
        
        Set<Molecule> reactantsKeys = balancedReactants.keySet(), productsKeys = balancedProducts.keySet();
        
        while(!(sumBalancedAtoms(balancedReactants) == sumBalancedAtoms(balancedProducts) && !isBalanced)) {
            
            // try incrementing each individual molecule
            otherSide = sumBalancedAtoms(balancedProducts);
            for(Molecule m : reactantsKeys) {
                newNumber = sumBalancedAtoms(balancedReactants) + (m.getNumAtoms() * currentIncrement);
                if(newNumber == otherSide) {
                    isBalanced = true;
                    old = balancedReactants.remove(m);
                    balancedReactants.put(m, currentIncrement);
                }
            }
            otherSide = sumBalancedAtoms(balancedReactants);
            for(Molecule m : productsKeys) {
                newNumber = sumBalancedAtoms(balancedProducts) + (m.getNumAtoms() * currentIncrement);
                if(newNumber == otherSide) {
                    isBalanced = true;
                    old = balancedProducts.remove(m);
                    balancedProducts.put(m, currentIncrement);
                }
            }
            

            // try turning them on then off at the end
            keys = balancedReactants.keySet();
            itr = keys.iterator();
            partialR = sumBalancedAtoms(balancedReactants);
            partialP = sumBalancedAtoms(balancedProducts);
            runCount = 0;
            
            while(itr.hasNext()) {
                
                partialR += (balancedReactants.get(itr.next()).intValue() * currentIncrement);
                
                if(partialR == partialP) {
                    isBalanced = true;
                    itr = keys.iterator();
                    
                    for(int i = 0; i < runCount; i++) {
                        
                        key = itr.next();
                        old = balancedReactants.remove(key);
                        balancedReactants.put(key,  currentIncrement);
                        
                    }
                    break;
                }
                
                runCount++;
            }
            
            filledPartialR = partialR;
            partialR = sumBalancedAtoms(balancedReactants);
            keys = balancedProducts.keySet();
            itr = keys.iterator();
            runCount = 0;
            
            while(itr.hasNext()) {
                
                partialR += (balancedProducts.get(itr.next()).intValue() * currentIncrement);
                
                if(partialR == partialP) {
                    isBalanced = true;
                    itr = keys.iterator();
                    
                    for(int i = 0; i < runCount; i++) {
                        
                        key = itr.next();
                        old = balancedProducts.remove(key);
                        balancedProducts.put(key,  currentIncrement);
                        
                    }
                    break;
                }
                
                runCount++;
            }
            
            
            
            
            currentIncrement ++;
        }
        
    }
    
    private void setBalancedData(TreeMap<Molecule, Integer> balancer, List<Molecule> listIn) {
        
        Set<Molecule> keys;
        int currentAmt;
        
        
        for(Molecule m : listIn) {
            keys = balancer.keySet();
            
            if(keys.contains(m)) {
                currentAmt = balancer.remove(m).intValue();
                balancer.put(m, Integer.valueOf(currentAmt + 1));
            }
            else
                balancer.put(m, Integer.valueOf(1));
            
        }
    }
    
    private int sumAtoms(List<Molecule> msIn) {
        int result = 0;
        
        for(Molecule m : msIn) {
            result += m.getNumAtoms();
        }
        
        return result;
    }
    
    private int sumBalancedAtoms(TreeMap<Molecule, Integer> msIn) {
        int result = 0;
        
        Set<Molecule> keys = msIn.keySet();
        int repeat;
        
        for(Molecule m : keys) {
            repeat = msIn.get(m).intValue();
            
            for(int i = 0; i < repeat; i++) {
                result += m.getNumAtoms();
            }
            
        }
        
        return result;
    }
    
}
