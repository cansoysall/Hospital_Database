import java.util.*;

public class HospitalDatabase {
	
	private Node root;
	
	/*This class is representing a node and its elements in the binary search tree*/
    private class Node {
        private String patientName;
        private String doctorName;
        
        private String memberName ;
        private String memberRole ;
        
        private int visitDay;
        private int visitMonth;
        private int visitYear;
        private Node left;
        private Node right;
        MemberTree members;

        /*This is the constructor for a patient node*/
        public Node(String patientName, String doctorName, int visitDay, int visitMonth, int visitYear) {
            this.patientName = patientName;
            this.doctorName = doctorName;
            this.visitDay = visitDay;
            this.visitMonth = visitMonth;
            this.visitYear = visitYear;
            this.members = new MemberTree(patientName);
        }
        /*And this is the constructor for a member node*/
        public Node(String memberName,String memberRole) {
        	this.memberName=memberName;
        	this.memberRole=memberRole;
            }
    }
    
/*We wrote this method to find a node in the BST by patient name, will be used when we call the methods with patient name*/
    public Node findNodebyName(String patientName) {
		return findNodebyName(root, patientName) ;
	}
	public Node findNodebyName(Node node, String patientName) {
		if (node == null) return null;
		if(patientName.compareTo(node.patientName)<0) return findNodebyName(node.left,patientName);
		else if(patientName.compareTo(node.patientName)>0) return findNodebyName(node.right,patientName);
		else return node;
	}
	/*This inner class that represents a BST for care team members of a patient*/
    public class MemberTree{
		private Node root2;
		public String header;
		public MemberTree(String a) {
   			header = a;
           }
		}
   
/*This method adds a patient to patients BST. It stores patient name, doctor name, visit day, visit month and visit year.*/
    public void addPatient(String patientName, String doctorName, int visitDay, int visitMonth, int visitYear) {
    	root=addPatient(root, patientName, doctorName, visitDay, visitMonth, visitYear);
    }
    private Node addPatient(Node node, String patientName, String doctorName, int visitDay, int visitMonth, int visitYear){
		if(node==null) {
			System.out.println("INFO: Patient " + patientName + " has been added");
			return new Node(patientName, doctorName, visitDay, visitMonth, visitYear);
		}
/*The 'comparison' method will compare the patient name we are holding and the patient name held by the current node.
This also means that we have used the patient's name as the key.*/	
		int comparison = patientName.compareTo(node.patientName);
		if (comparison==0) {
			System.out.println("ERROR: Patient " + patientName + " overwritten");
			node.doctorName = doctorName;
			node.visitDay = visitDay;
			node.visitMonth = visitMonth;
			node.visitYear = visitYear;
			return node; //Blocks to add the overwritten patient as a new node
		}
		
		if (comparison<0) {
			node.left = addPatient(node.left, patientName, doctorName, visitDay, visitMonth, visitYear);
		}
		else if(comparison>0) {
			node.right = addPatient(node.right, patientName, doctorName, visitDay, visitMonth, visitYear);
		}
		/*We didn't need any extra else statement, because we have already checked if a patientName already exists.*/
		
		return node;
    }
    
    
    /*This method removes the complete patient node with using patient name*/
    public void removePatient(String patientName) {
        root = removePatientRecursive(root, patientName);
    }

    private Node removePatientRecursive(Node node, String patientName) {
        if (node == null) {
            System.out.println("ERROR: Patient " + patientName + " not found");
            return node;
        }

        int comparison = patientName.compareTo(node.patientName);

        if (comparison < 0) {
            node.left = removePatientRecursive(node.left, patientName);
        } else if (comparison > 0) {
            node.right = removePatientRecursive(node.right, patientName);
        } else {

            /*When the node is a leaf*/
            if (node.left == null && node.right == null) {
                System.out.println("INFO: Patient " + patientName + " has been removed");
                return null;
            }

            /*When the node has one child*/
            if (node.left == null) {
                System.out.println("INFO: Patient " + patientName + " has been removed");
                return node.right;
            } else if (node.right == null) {
                System.out.println("INFO: Patient " + patientName + " has been removed");
                return node.left;
            }

            /*When the node has both left child and right child*/
            System.out.println("INFO: Patient " + patientName + " has been removed");
            Node successor = minValueNode(node.right);
            node.patientName = successor.patientName;
            node.doctorName = successor.doctorName;
            node.visitDay = successor.visitDay;
            node.visitMonth = successor.visitMonth;
            node.visitYear = successor.visitYear;

            node.right = removePatientRecursive(node.right, successor.patientName);
        }

        return node;
    }

    private Node minValueNode(Node node) { /*Being used by removePatient method*/
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
    
    private Node deleteMin(Node x) { /*Being used by removeMember*/
        if(x.left==null)return x.right;
        x.left = deleteMin(x.left);
        return x;
    }

    private Node min(Node a) { /*Being used by removeMember*/
        Node b = a ;
        if(b!=null) b.left = min(b.left);

        return b;
    }


/*This method adds a member and their role to specified patient' node. It uses findNodeByName method to find the 
patient' node. We had memberName and memberNode attributes inside our Node class, so basically this method assigns
the attributes that we use while calling the method to those attributes which are in the Node class.*/
    public void addMember(String patientName,String memberName,String memberRole) {
		Node patientnode = findNodebyName(patientName) ;
		if(patientnode!=null) {
			if (patientnode.members.root2 == null) {
				patientnode.members.root2 = new Node(memberName, memberRole);
			}
			else {
				patientnode.members.root2 = addMember(patientnode.members.root2,patientName,memberName,memberRole);
			}
			System.out.println("INFO: " + memberName + " has been added to the patient " + patientName);
		}
		else
			System.out.println("ERROR: Patient " + patientName + " does not exist");
	    }
      public Node addMember(Node membernode ,String patientName,String memberName,String memberRole) {
    	  if(membernode==null) { 
    		  return new Node(memberName,memberRole);
    	  }
    	  if(memberName.compareTo(membernode.memberName)<0) 
    		  membernode.left =addMember(membernode.left,patientName,memberName,memberRole);
    	  else if(memberName.compareTo(membernode.memberName)>0) 
    		  membernode.right = addMember(membernode.right,patientName,memberName,memberRole);
    	  
	    	return membernode;
	    }
      
      
      
      /*This method removes member node from the specified patient node. Again, it uses findNodeByName to find the
node of the given patient.*/
      public void removeMember(String patientName,String memberName){
          Node patientNode = findNodebyName(patientName);
          patientNode.members.root2 = removeMember(patientNode.members.root2,patientName,memberName) ;


      }
      public Node removeMember(Node membernode,String patientName,String memberName) {
          if(memberName.compareTo(membernode.memberName)<0) membernode.left =removeMember(membernode.left,patientName,memberName); 
          else if(memberName.compareTo(membernode.memberName)>0) membernode.right =removeMember(membernode.right,patientName,memberName);
          else {
              if(membernode.right==null) {
            	  System.out.println("INFO: " + memberName + " has been removed from the patient " + patientName);
            	  return membernode.left;
              }
              if(membernode.left==null) {
            	  System.out.println("INFO: " + memberName + " has been removed from the patient " + patientName);
            	  return membernode.right;
              }

              Node t = membernode;
              membernode = min(t.right) ;
              membernode.right = deleteMin(membernode.right);
              membernode.left = t.left; 
              System.out.println("INFO: " + memberName + " has been removed from the patient " + patientName);
          }

          return membernode ;
      }
      
      
    
      /*This method is responsible for displaying information about all the patients in the hospital database.
*It first checks if the 'root' node of the database is null. If it is, it prints "---null---", indicating that there 
are no patients in the database.
*If the 'root' node is not null, it creates an empty ArrayList named sortedPatients to store the patients in sorted order.
*It then calls the inorderTraversal method to traverse the database's binary search tree in ascending order.
*After traversing, it sorts the sortedPatients list based on the patients' visit year using a lambda expression.
*Finally, it iterates over the sorted list and prints the patient's name, visit year, and doctor's name for each patient.*/
    public void showAllPatients() {
    	if (root==null) {
    		System.out.println("---null---");
    	}
    	else {
    		List<Node> sortedPatients = new ArrayList<>();
            inorderTraversal(root, sortedPatients);
            sortedPatients.sort(Comparator.comparingInt(p -> p.visitYear));
            for (Node node : sortedPatients) {
                System.out.println(node.patientName + ", " + node.visitYear + ", " + node.doctorName);
            }
    	}
    }
/*This method recursively traverses the binary search tree starting from the given node in ascending order.
*During traversal, it adds each visited node to the sortedPatients list.*/
    private void inorderTraversal(Node node, List<Node> sortedPatients) {
        if (node != null) {
            inorderTraversal(node.left, sortedPatients);
            sortedPatients.add(node);
            inorderTraversal(node.right, sortedPatients);
        }
    }
    
/*This method takes the patient name as an input, and finds the corresponding node with the help of findNodebyName method.
If there is a patient with that name given, it prints that patients name, visit day, visit month, visit year and doctor name.
Basically, prints all information about the patient if they exist. If they don't exist, it prints "---null---"*/
    public void showPatient(String patientName) {
		Node patientnode = findNodebyName(patientName);
		if(patientnode!=null) {
			System.out.println(patientnode.patientName);
			System.out.println(patientnode.visitDay + "/" + patientnode.visitMonth + "/" + patientnode.visitYear);
			System.out.println(patientnode.doctorName);
			showPatient(patientnode.members.root2);
		}
		else {
			System.out.println("---null---");
		}
	}
	public void showPatient(Node membernode) {
		if(membernode !=null) {
			showPatient(membernode.left);
			System.out.println(membernode.memberName+", "+membernode.memberRole);
			showPatient(membernode.right);
		}
	}
    
    
/*This method prints all patients and their visit date(day/month) with taking visit year as input. It looks for all nodes 
in the BST till it finds a year value as same as the input given. If the year does not exist, then it prints "---null---"*/
	public void showPatients(int visitYear) {
        if(root==null) {
        	System.out.println("---null---");
        }
        else {
        	System.out.println(visitYear); /*Year values are being printed before patient names*/
        	showPatients(root, visitYear);
        }
    }
	public void showPatients(Node node, int visitYear) {
		if(node!=null) {
			showPatients(node.right, visitYear);
			if(node.visitYear==visitYear) {
				System.out.println(node.patientName + ", " + node.visitDay + "/" + node.visitMonth);
			}
			showPatients(node.left, visitYear);
		}
	}
	
/*This method prints doctor name first and then, all patient names and their visit dates(day/month/year) with taking 
doctor name as input. It looks for all nodes in the BST till it finds a doctor name as same as the input given. If the
doctor name does not have any patient, then it prints "---null---"*/
	public void showDoctorPatients(String doctorName) {
	    if (root == null) {
	        System.out.println("---null---");
	    } else {
	    	System.out.println(doctorName); /*Doctor names are being printed before patient names*/
	        showDoctorPatients(root, doctorName);
	    }
	}

	public void showDoctorPatients(Node node, String doctorName) {
		
	    if (node != null) {
	        showDoctorPatients(node.right, doctorName);
	        if (node.doctorName.equals(doctorName)) {
	        	
	            System.out.println(node.patientName+", "+node.visitDay + "/" + node.visitMonth +"/"+ node.visitYear);
	        }
	        showDoctorPatients(node.left, doctorName);
	    }
	}
	
	
	
    
    public static void main(String args[]) {
    	HospitalDatabase hd = new HospitalDatabase();
    	
    	hd.showAllPatients();
    	System.out.println();
    	hd.addPatient("Michael Johnson","Emma Thompson", 19, 12, 2022);
    	hd.addPatient("Ethan Lee", "Olivia Sanchez", 8, 9, 2020);
    	hd.addPatient("Noah Miller", "Olivia Sanchez", 27, 2, 2019);
    	hd.addPatient("Liam Davis", "Isabella Martinez", 3, 4, 2022);
    	hd.addPatient("Ava Taylor", "Isabella Martinez", 15, 5, 2024);
    	hd.addPatient("Mason Moore", "William Anderson", 7, 6, 2021);
    	hd.addPatient("Charlotte Garcia", "Lucas Lewis", 30, 10, 2023);
    	hd.addPatient("Noah Miller", "Olivia Sanchez", 27, 2, 2019);
    	System.out.println();
    	hd.showAllPatients();
    	System.out.println();
    	hd.removePatient("Ava Taylor");
    	System.out.println();
    	hd.showAllPatients();
    	System.out.println();
    	hd.showPatient("Michael Johnson");
    	System.out.println();
    	hd.addMember("Mason Moore", "Daniel Roberts", "Nurse");
    	hd.addMember ("Mason Moore", "Victoria Stewart", "Radiologist");
    	hd.addMember ("Mason Moore", "Tyler Campbell", "Medical Assistant");
    	hd.addMember ("Mason Moore", "Hannah Martin", "Paramedic");
    	hd.addMember ("Michael Johnson", "Jack Allen", "Patient Care Technician");
    	hd.addMember ("Michael Johnson", "Oliver Nelson", "Anesthesiologist");
    	hd.addMember ("Michael Johnson", "Sophia Rivera", "Pathologist");
    	hd.addMember ("Michael Johnson", "Evan Hall", "Laboratory Technician");
    	hd.addMember ("Michael Johnson", "Megan Price", "Nurse");
    	hd.addMember ("Ava Taylor", "Brianna Reed", "Dietitian");
    	hd.addMember ("Charlotte Garcia", "Oliver Nelson", "Anesthesiologist");
    	hd.addMember ("Charlotte Garcia", "Trevor Jenkins", "Medical Equipment Technician");
    	hd.addMember ("Charlotte Garcia", "Justin Flores", "Speech-Language Pathologist");
    	System.out.println();
    	hd.showPatient("Mason Moore");
    	System.out.println();
    	hd.showPatient("Michael Johnson");
    	System.out.println();
    	hd.removeMember("Michael Johnson", "Evan Hall");
    	System.out.println();
    	hd.showPatient("Michael Johnson");
    	System.out.println();
    	hd.showDoctorPatients("Olivia Sanchez");
    	System.out.println();
    	hd.showDoctorPatients("Emma Thompson");
    	System.out.println();
    	hd.showPatients(2022);
    }

}