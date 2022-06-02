import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException; 


public class CatTree implements Iterable<CatInfo>{
	
	public CatNode root;
    
    public CatTree(CatInfo c) {
        this.root = new CatNode(c);
    }
    
    private CatTree(CatNode c) {
        this.root = c;
    }
    
    //takes a catinfo object, converts to a catnode and calls CatNode.addCat()
    public void addCat(CatInfo c)
    {
        this.root = root.addCat(new CatNode(c));
    }
    
    public void removeCat(CatInfo c)
    {
        this.root = root.removeCat(c);
    }
    
    public int mostSenior()
    {
        return root.mostSenior();
    }
    
    public int fluffiest() {
        return root.fluffiest();
    }
    
    public CatInfo fluffiestFromMonth(int month) {
        return root.fluffiestFromMonth(month);
    }
    
    public int hiredFromMonths(int monthMin, int monthMax) {
        return root.hiredFromMonths(monthMin, monthMax);
    }
    
    public int[] costPlanning(int nbMonths) {
        return root.costPlanning(nbMonths);
    }
    
    
    
    public Iterator<CatInfo> iterator()
    {
        return new CatTreeIterator();
    }
    
    
    class CatNode {
        
        CatInfo data;
        CatNode senior;
        CatNode same;
        CatNode junior;
        
        public CatNode(CatInfo data) {
            this.data = data;
            this.senior = null;
            this.same = null;
            this.junior = null;
        }
        
        public String toString() {
            String result = this.data.toString() + "\n";
            if (this.senior != null) {
                result += "more senior " + this.data.toString() + " :\n";
                result += this.senior.toString();
            }
            if (this.same != null) {
                result += "same seniority " + this.data.toString() + " :\n";
                result += this.same.toString();
            }
            if (this.junior != null) {
                result += "more junior " + this.data.toString() + " :\n";
                result += this.junior.toString();
            }
            return result;
        }
        
        //called on a cat b (root of tree). Add cat c as a branch off of b.
        public CatNode addCat(CatNode c) {
        	
        	// if c is less senior to b, make c part of the junior subtree
        	if(this.data.monthHired<c.data.monthHired) {
        		if (this.junior == null) {
        			this.junior = c;
        		} else {
        			this.junior.addCat(c);
        		}
        	} 
        	// if c has the same seniority as b, consider fur thickness
        	else if (this.data.monthHired==c.data.monthHired) {
        		 // if c has the same seniority as b, consider fur thickness
        		if(this.data.furThickness>c.data.furThickness) {
        			if(this.same == null) {
        				this.same=c;
        			} else {
        				this.same.addCat(c);
        			}
        			
        		} else if (this.data.furThickness<c.data.furThickness) {
        			//swap 
        			CatInfo temp = this.data;
        			this.data = c.data;
        			c.data=temp;
        			c.same=this.same;
        			this.same = c;
        		}
        	} 
        	// if c is more senior to b, make c part of the senior subtree
        	else if (this.data.monthHired>c.data.monthHired){
        		if (this.senior == null) {
        			this.senior = c;
        		} else {
        			this.senior.addCat(c);
        		}
        	}
        	
            return this;
        }
        
        
        public CatNode removeCat(CatInfo c) {
        	
        	// if node is the root, remove data and return this
        	if (this.data.equals(c)) {
	        	if(this.senior == null && this.junior == null &&  this.same == null ) {
	        	//	this.data = new CatInfo(null, 0, 0, 0, 0);
	        		this.data = null;
		        	return this;
	        	}
        	}
        	
        	// use helper method to keep track of previous easier
        	this.remove(c, null);
        	
        	return this;
        }
        
        public CatNode remove (CatInfo c, CatNode previous) {
        	if (this.data.equals(c)) {
        		// if has a same, replace this with this.same
        		if (this.same != null) {
            		CatNode tempSame = null;
            		try {
            			tempSame = this.same.same;
            			this.data = this.same.data;
            		}
            		catch (NullPointerException e) {

            		}
            		this.same=tempSame;
        			return this;
            	}
        		// if has a senior but no same, replace this with this.senior  
            	else if (this.same == null && this.senior != null) {
            		CatNode tempOldJunior = null;
            		CatNode tempJunior = null;
            		CatNode tempSenior = null;
            		CatNode tempSame = null;
            		// try to assign some variables 
            		try {
    	        		tempOldJunior = this.junior;
    	        		tempJunior = this.senior.junior;
    	        		tempSenior = this.senior.senior;
    	        		tempSame = this.senior.same;
            		} 
            		catch (NullPointerException e){
    	    			if (this.senior.junior == null) {
    	    				tempJunior = null;
    	    			}
    	    			 if (this.senior.senior == null) {
    	    				tempSenior = null;
    	    			} 
    	    			 if (this.senior.same == null) {
    	    				tempSame = null;
    	    			} 
    	    			 if (this.junior == null) {
    	    				tempOldJunior = null;
    	    			}
    	    		}
        			//add back in any cats under the junior side of the tree
        			//find last cat on the senior side
            		if(tempOldJunior!=null && tempJunior!= null) {
	        			tempJunior.insertJunior(tempOldJunior);
	        		} else if (tempOldJunior !=null && tempJunior ==null) {
	        			tempJunior = tempOldJunior;
	        		}
        			
        			this.data = this.senior.data;
            		this.junior = tempJunior;
            		this.senior = tempSenior;
            		this.same = tempSame;
        			return this;
            	}
        		// if has only a junior
            	else if (this.same == null && this.senior == null && this.junior!=null) {
            		CatNode tempJunior = null;
            		CatNode tempSenior = null;
            		CatNode tempSame = null;
            		try { 
            		tempJunior = this.junior.junior;
            		tempSenior = this.junior.senior;
            		tempSame = this.junior.same;
            		} 
            		catch (NullPointerException e){
            			if (this.junior.junior == null) {
            				tempJunior = null;
            			}
            			else if (this.junior.senior == null) {
            				tempSenior = null;
            			} 
            			else if (this.junior.same == null) {
            				tempSame = null;
            			}
            		}
            		this.data = this.junior.data;
            		this.junior = tempJunior;
            		this.senior = tempSenior;
            		this.same = tempSame;
        			return this;
            	} 
        		// if has none
            	else {
            		if(previous.same==this) {
            			previous.same = null;
            		} else if (previous.junior==this) {
            			previous.junior=null;
            		} else if (previous.senior == this) {
            			previous.senior =null;
            		}
            		return this;
            	}
        		
        	} else {
        		previous = this;
	        	if (this.data.monthHired< c.monthHired && this.junior!=null){
	        		return this.junior.remove(c, previous);
	        	} else if(this.data.monthHired> c.monthHired && this.senior!=null){
	        		return this.senior.remove(c, previous);
	        	} else if(this.data.monthHired==c.monthHired && this.same!=null) {
	        		return this.same.remove(c,previous);
	        	} else {
	        		return null;
        	}
        	}
        } 
        
        public void insertJunior(CatNode juniorToBeAdded) {
        	if(this.junior == null) {
        		this.junior = juniorToBeAdded;
        	} 
        	else {
        		this.junior.insertJunior(juniorToBeAdded);
        	}
        }
        
        public void insertSame(CatNode juniorToBeAdded) {
        	if(this.same == null) {
        		this.junior = juniorToBeAdded;
        	} else {
        		this.same.insertJunior(juniorToBeAdded);
        	}
        }
 
        public int mostSenior() {
        	if (this.senior==null) {
        		return this.data.monthHired;
        	} else if (this.senior!=null) {
        		return this.senior.mostSenior();
        	}
        	return 0;
        }
        
        //traverse the entire tree to find the fluffiest cat
        // only care about juniors and seniors because same will never have more fluffiness 
        public int fluffiest() {
        	//temp variable for fluffiest 
        	int fluffiness = this.fluff(this.data.furThickness);
    		
            return fluffiness;
        }
        
        public int fluff(int fluffy) {
        	// check if leaf
        	if (this.senior == null && this.junior == null) {
        		if (this.data.furThickness > fluffy) {
        			fluffy = this.data.furThickness;
        		}
        	}
        	
        	//check senior
    		if (this.senior != null) {
    			int tempFluffy = this.senior.fluff(fluffy);
    			if (this.data.furThickness > tempFluffy){
    				tempFluffy = this.data.furThickness;
    			} 
    			if(tempFluffy >fluffy) {
        			fluffy = tempFluffy;
    			}
    		}	
    		//check junior
    		if ( this.junior != null) { 
    			int tempFluffy = this.junior.fluff(fluffy);
    			if (this.data.furThickness > tempFluffy){
    				tempFluffy = this.data.furThickness;
    			} 
    			if(tempFluffy >fluffy) {
        			fluffy = tempFluffy;
    			}
    		}
    		return fluffy;
        }
        
        //returns  the number of cats hired between the months given
        public int hiredFromMonths(int monthMin, int monthMax) {
        	if(monthMin>monthMax) {
        		return 0;
        	}
            //iterate through the tree and count up each time an eligible cat is found
        	int i = this.traversalHired(monthMin, monthMax, 0);
            return i; 
            
        }
		 
        public int traversalHired(int min, int max, int j) {
        	if (min <= this.data.monthHired && this.data.monthHired<= max) {
        			j++;
        	}
        	if (this.senior!=null){
				j = this.senior.traversalHired(min, max, j);
    		}
    		if (this.junior != null) {
    			j = this.junior.traversalHired(min, max, j);
    		}
    		if (this.same != null) {
    			j = this.same.traversalHired(min, max, j);
    		}
        	
			return j;
		 }
        
        // find the fluffiest cat that was hired in the month given
        public CatInfo fluffiestFromMonth(int month) {
        	int numberOfCats = this.hiredFromMonths(month, month);
        	if(numberOfCats == 0) {
        		return null;
        	}
        	CatInfo placeholder = new CatInfo(null, 0, 0, 0, 0);
        	CatNode temp = new CatNode(placeholder);
        	CatNode fluffiest = this.traversalFluffy(month, temp);
            return fluffiest.data; 
        }
        public CatNode traversalFluffy(int month, CatNode greatestFluff) {
        	if (month == this.data.monthHired && this.data.furThickness > greatestFluff.data.furThickness) {
        			greatestFluff = this;
        	}
        	if (this.senior!=null){
				greatestFluff = this.senior.traversalFluffy(month, greatestFluff);
    		}
    		if (this.junior != null) {
    			greatestFluff = this.junior.traversalFluffy(month, greatestFluff);
    		}
    		if (this.same != null) {
    			greatestFluff = this.same.traversalFluffy(month, greatestFluff);
    		}
        	
			return greatestFluff;
		 }
        
        // returns an int array with costs associated with each month as of march 2020(momth 243)
        public int[] costPlanning(int nbMonths) {
            // go through each cat, find expected time of grooming and how much their grooming costs
        	// add it up and then move on to next month
        	int[] costPlan = new int[nbMonths];
        	for(int i = 0; i<nbMonths; i++) {
        		int cost =  this.traversalCost(243+i, 0);
        		costPlan[i]=cost;
        	}
            return costPlan; 
        }
        
        public int traversalCost(int month, int totalCost) {
        	if (month == this.data.nextGroomingAppointment) {
        			totalCost = totalCost + this.data.expectedGroomingCost;
        	}
        	if (this.senior!=null){
    			totalCost = this.senior.traversalCost(month, totalCost);
    		}
    		if (this.junior != null) {
    			totalCost = this.junior.traversalCost(month, totalCost);
    		}
    		if (this.same != null) {
    			totalCost = this.same.traversalCost(month, totalCost);
    		}
        	
    		return totalCost;
    	 }
        
    }
   
    // note that this is within the cat tree and can access cat tree field therefor.
    private class CatTreeIterator implements Iterator<CatInfo> {
        CatNode current;
        ArrayList<CatNode> arrayOfCats;
    	int index = 0;
        // constructor
        // fills array of cats and sets current to first cat
        public CatTreeIterator() {
        	if(root == null) {
        		current = null;
        	}
 
        	ArrayList<CatNode> empty = new ArrayList<CatNode>();
        	arrayOfCats = this.traversalArray(empty, root);
        	
        	current = arrayOfCats.get(0);
        }
       
        // helper method to get a sorted arraylist of cats 
        public ArrayList<CatNode> traversalArray (ArrayList<CatNode> cats,CatNode cur) {
        	if (cur.junior == null && cur.senior == null && cur.same==null) {
        		cats.add(cur);
        	}
        	else {
    			if(cur.senior != null) {
    				cats = this.traversalArray(cats, cur.senior);
    			} 
    			
    			if (cur.same != null) {
    				cats = this.traversalArray(cats, cur.same);
    			} 
    			
    			cats.add(cur);
    			
    			if (cur.junior != null){
    				cats = this.traversalArray(cats, cur.junior);
    			}
    		}
    	
        	
    		return cats;
    	 }
        
        public CatInfo next(){
            if(index<arrayOfCats.size()) {
            	index++;
            	return arrayOfCats.get(index-1).data;
            }
            
            return null; 
        }
        
        public boolean hasNext() {
            if(index<arrayOfCats.size()) {
            	return true;
            }
            return false; 
        }
    }
    
}

